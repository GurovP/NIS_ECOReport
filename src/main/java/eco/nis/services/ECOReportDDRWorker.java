package eco.nis.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ECOReportingDDRApiService;
import eco.nis.models.DDR_ReportDataDTO;
import eco.nis.models.DDR_ReportDataItemDTO;
import eco.nis.models.DDR_ReportDescriptionDTO;
import eco.nis.models.DDR_TimeDistributionDataItemDTO;
import eco.nis.models.entities.ECOReportingSyncLog;
import eco.nis.models.entities.ddr.DDRDailyReport;
import eco.nis.models.entities.ddr.DDRDailyReportData;
import eco.nis.models.entities.ddr.DDRDailyTimeDistributionData;
import eco.nis.models.entities.ddr.DDRDailyTimeDistributionRecord;
import eco.nis.repositories.DDRDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.utilities.ObjectMapperUtility;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ECOReportDDRWorker {

    private final TransactionTemplate transactionTemplate;
    private final ECOReportingDDRApiService ddrApiService;
    private final DDRDailyReportRepository ddrDailyReportRepository;
    private final ECOReportingSyncLogRepository ecoReportingSyncLogRepository;
    private final Logger logger;

    private final AtomicBoolean inProcess = new AtomicBoolean(false);

    public ECOReportDDRWorker(PlatformTransactionManager transactionManager,
                              ECOReportingDDRApiService ddrApiService,
                              DDRDailyReportRepository ddrDailyReportRepository,
                              ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                              Logger logger) {

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.ddrApiService = ddrApiService;
        this.ddrDailyReportRepository = ddrDailyReportRepository;
        this.ecoReportingSyncLogRepository = ecoReportingSyncLogRepository;
        this.logger = logger;
    }

    public void pullECOReportDDR() {

        if (inProcess.get()) {

            logger.info("pullECOReportDDR already inProcess. return");
            return;
        }

        try {

            inProcess.set(true);

            DDR_ReportDescriptionDTO[] ddrReportDescriptions = ddrApiService.listReports();

            transactionTemplate.execute(status -> {

                ECOReportingSyncLog ecoReportingSyncLog = new ECOReportingSyncLog();
                ecoReportingSyncLog.setReportType("DDR");
                ecoReportingSyncLog.setReadFromApiCount(ddrReportDescriptions.length);

                List<String> allReportUids = ddrDailyReportRepository.findAllReportUids();

                for (DDR_ReportDescriptionDTO ddrReportDescription : ddrReportDescriptions) {

                    DDRDailyReport report = ddrDailyReportRepository.findByReportUid(ddrReportDescription.getReportUid());

                    if (report == null) {
                        addReport(ddrReportDescription);
                        ecoReportingSyncLog.setAddedCount(ecoReportingSyncLog.getAddedCount() + 1);
                    }
                    else {
                        if (report.isApproved() != ddrReportDescription.isApproved()) {
                            updateReport(report, ddrReportDescription);
                            ecoReportingSyncLog.setUpdatedCount(ecoReportingSyncLog.getUpdatedCount() + 1);
                        }
                        allReportUids.remove(report.getReportUid());
                    }
                }


                for (String reportUid : allReportUids) {
                    deleteReport(reportUid);
                }
                ecoReportingSyncLog.setDeletedCount(allReportUids.size());

                ecoReportingSyncLogRepository.save(ecoReportingSyncLog);

                try {
                    NIS_ECOReportApplication.logger.info("pullECOReportDDR. ecoReportingSyncLog. {}",
                            ObjectMapperUtility.getObjectMapper().writeValueAsString(ecoReportingSyncLog));
                } catch (JsonProcessingException ignored) {
                }

                return null;
            });

        } catch (Throwable e) {

            logger.error("pullECOReportDDR", e);

        } finally {

            inProcess.set(false);
        }
    }

    private void addReport(DDR_ReportDescriptionDTO ddrReportDescription) {

        DDRDailyReport report = new DDRDailyReport();

        updateReportFields(report, ddrReportDescription);

        updateReportData(report);

        ddrDailyReportRepository.save(report);
    }

    private void updateReport(DDRDailyReport report, DDR_ReportDescriptionDTO ddrReportDescription) {

        updateReportFields(report, ddrReportDescription);

        updateReportData(report);

        ddrDailyReportRepository.save(report);
    }

    private void deleteReport(String reportUid) {
        ddrDailyReportRepository.deleteByReportUid(reportUid);
    }

    private void updateReportFields (DDRDailyReport report, DDR_ReportDescriptionDTO ddrReportDescription) {
        report.setReportUid(ddrReportDescription.getReportUid());
        report.setReportDate(ddrReportDescription.getReportDate());
        report.setReportNumber(ddrReportDescription.getReportNumber());
        report.setDocumentCode(ddrReportDescription.getDocumentCode());
        report.setComment(ddrReportDescription.getComment());
        report.setApproved(ddrReportDescription.isApproved());
        report.setLastApproveDate(ddrReportDescription.getLastApproveDate());
        report.setLastApproveUser(ddrReportDescription.getLastApproveUser());
    }

    private void updateReportData(DDRDailyReport report) {

        DDR_ReportDataDTO reportData = ddrApiService.getReportData(report.getReportUid());

        if (report.getReportDatum() == null) {
            report.setReportDatum(new ArrayList<>());
        }
        else {
            report.getReportDatum().clear();
        }

        for (DDR_ReportDataItemDTO ddrReportDataItemDTO : reportData.getReportDatum()) {
            DDRDailyReportData ddrDailyReportData = new DDRDailyReportData();

            ddrDailyReportData.setDataType(ddrReportDataItemDTO.getDataType());
            ddrDailyReportData.setContent(ddrReportDataItemDTO.getContent());

            ddrDailyReportData.setReport(report);
            report.getReportDatum().add(ddrDailyReportData);
        }


        if (report.getDdrDailyTimeDistributionData() == null) {
            report.setDdrDailyTimeDistributionData(new DDRDailyTimeDistributionData());
        }

        if (report.getDdrDailyTimeDistributionData().getTimeDistributionRecords() == null) {
            report.getDdrDailyTimeDistributionData().setTimeDistributionRecords(new ArrayList<>());
        }
        else {
            report.getDdrDailyTimeDistributionData().getTimeDistributionRecords().clear();
        }

        for (DDR_TimeDistributionDataItemDTO ddrTimeDistributionDataItemDTO : reportData.getTimeDistributionDatum()) {
            DDRDailyTimeDistributionRecord record = new DDRDailyTimeDistributionRecord();

            record.setTimestamp(ddrTimeDistributionDataItemDTO.getTimestamp());
            record.setName(ddrTimeDistributionDataItemDTO.getName());
            record.setContent(ddrTimeDistributionDataItemDTO.getContent());
            record.setTimeDistributionUid(ddrTimeDistributionDataItemDTO.getTimeDistributionUid());

            record.setParentDdrDailyTimeDistributionData(report.getDdrDailyTimeDistributionData());
            report.getDdrDailyTimeDistributionData().getTimeDistributionRecords().add(record);
        }
    }
}
