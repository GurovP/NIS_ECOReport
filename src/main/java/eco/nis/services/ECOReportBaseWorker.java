package eco.nis.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.entities.ECOReportingSyncLog;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.utilities.ObjectMapperUtility;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ECOReportBaseWorker<TReport, TReportDescriptionDTO> {

    protected final TransactionTemplate transactionTemplate;
    protected final ECOReportingSyncLogRepository ecoReportingSyncLogRepository;
    protected final Logger logger;

    private final AtomicBoolean inProcess = new AtomicBoolean(false);

    public ECOReportBaseWorker(PlatformTransactionManager transactionManager,
                               ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                               Logger logger) {

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.ecoReportingSyncLogRepository = ecoReportingSyncLogRepository;
        this.logger = logger;
    }

    protected abstract String getReportType();

    protected abstract ResponseWrapper<TReportDescriptionDTO[]> listReportsFromAPI();

    protected abstract List<String> allReportUidsInDB();

    protected abstract TReport findByReportUid(String reportUid);

    protected abstract String getReportDTOUid(TReportDescriptionDTO reportDTO);

    protected abstract String getReportUid(TReport report);

    protected abstract String addReport(TReportDescriptionDTO reportDescriptionDTO);

    protected abstract String updateReport(TReport report, TReportDescriptionDTO reportDescriptionDTO);

    protected abstract String deleteReport(String reportUid);

    public void pullECOReport() {

        if (inProcess.get()) {

            logger.info("pullECOReport " + getReportType() + " already inProcess. return");
            return;
        }

        try {

            inProcess.set(true);

            ResponseWrapper<TReportDescriptionDTO[]> reportDescriptionsWrapper = listReportsFromAPI();

            transactionTemplate.execute(status -> {

                ECOReportingSyncLog ecoReportingSyncLog = new ECOReportingSyncLog();
                ecoReportingSyncLog.setReportType(getReportType());

                if (reportDescriptionsWrapper.getError() != null) {
                    ecoReportingSyncLog.setStatus("ERROR:: " + reportDescriptionsWrapper.getError());
                }
                else {

                    var reportDescriptions = reportDescriptionsWrapper.getResult();

                    ecoReportingSyncLog.setReadFromApiCount(reportDescriptions.length);

                    List<String> allReportUids = allReportUidsInDB();

                    StringBuilder stringBuilder = new StringBuilder();

                    for (TReportDescriptionDTO reportDescriptionDTO : reportDescriptions) {

                        String reportDTOUid = getReportDTOUid(reportDescriptionDTO);

                        TReport report = findByReportUid(reportDTOUid);

                        if (report == null) {
                            String error = addReport(reportDescriptionDTO);
                            if (error == null) {
                                ecoReportingSyncLog.setAddedCount(ecoReportingSyncLog.getAddedCount() + 1);
                            }
                            else {
                                stringBuilder.append(reportDTOUid).append(" : ").append(error).append(". ");
                            }
                        } else {
                            String error = updateReport(report, reportDescriptionDTO);
                            if (error == null) {
                                ecoReportingSyncLog.setUpdatedCount(ecoReportingSyncLog.getUpdatedCount() + 1);
                            } else {
                                stringBuilder.append(reportDTOUid).append(" : ").append(error).append(". ");
                            }
                            allReportUids.remove(getReportUid(report));
                        }
                    }

                    for (String reportUid : allReportUids) {
                        String error = deleteReport(reportUid);
                        if (error == null) {
                            ecoReportingSyncLog.setDeletedCount(ecoReportingSyncLog.getDeletedCount() + 1);
                        }
                        else {
                            stringBuilder.append(reportUid).append(" : ").append(error).append(". ");
                        }
                    }

                    if (stringBuilder.isEmpty())
                        ecoReportingSyncLog.setStatus("OK");
                    else
                        ecoReportingSyncLog.setStatus("WARN:: " + stringBuilder);
                }

                ecoReportingSyncLogRepository.save(ecoReportingSyncLog);

                try {
                    NIS_ECOReportApplication.logger.info("ecoReportingSyncLog. {}",
                            ObjectMapperUtility.getObjectMapper().writeValueAsString(ecoReportingSyncLog));
                } catch (JsonProcessingException ignored) {
                }

                return null;
            });

        } catch (Throwable e) {

            logger.error("pullECOReport " + getReportType(), e);

        } finally {

            inProcess.set(false);
        }
    }
}
