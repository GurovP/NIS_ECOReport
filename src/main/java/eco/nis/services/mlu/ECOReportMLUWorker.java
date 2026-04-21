package eco.nis.services.mlu;

import eco.nis.api.ResponseWrapper;
import eco.nis.api.mlu.ECOReportingMLUApiService;
import eco.nis.models.dto.mlu.MLU_ReportDataDTO;
import eco.nis.models.dto.mlu.MLU_ReportDataItemDTO;
import eco.nis.models.dto.mlu.MLU_ReportDescriptionDTO;
import eco.nis.models.entities.mlu.MLUDailyReport;
import eco.nis.models.entities.mlu.MLUDailyReportData;
import eco.nis.repositories.MLUDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.services.ECOReportBaseWorker;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

public class ECOReportMLUWorker extends ECOReportBaseWorker<MLUDailyReport, MLU_ReportDescriptionDTO> {

    private final String reportType = "MLU";

    private final ECOReportingMLUApiService mluApiService;
    private final MLUDailyReportRepository mluDailyReportRepository;

    public ECOReportMLUWorker(PlatformTransactionManager transactionManager,
                              ECOReportingMLUApiService mluApiService,
                              MLUDailyReportRepository mluDailyReportRepository,
                              ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                              Logger logger) {

        super(transactionManager, ecoReportingSyncLogRepository, logger);

        this.mluApiService = mluApiService;
        this.mluDailyReportRepository = mluDailyReportRepository;
    }

    @Override
    protected String getReportType() {
        return reportType;
    }

    @Override
    protected ResponseWrapper<MLU_ReportDescriptionDTO[]> listReportsFromAPI() {
        return mluApiService.listReports();
    }

    @Override
    protected List<String> allReportUidsInDB() {
        return mluDailyReportRepository.findAllReportUids();
    }

    @Override
    protected MLUDailyReport findByReportUid(String reportUid) {
        return mluDailyReportRepository.findByReportUid(reportUid);
    }

    @Override
    protected String getReportDTOUid(MLU_ReportDescriptionDTO reportDTO) {
        return reportDTO.getReportUid();
    }

    @Override
    protected String getReportUid(MLUDailyReport report) {
        return report.getReportUid();
    }

    @Override
    protected boolean needUpdateReport(MLUDailyReport report, MLU_ReportDescriptionDTO mluReportDescription) {
        return report.isApproved() != mluReportDescription.isApproved();
    }

    @Override
    protected String addReport(MLU_ReportDescriptionDTO mluReportDescription) {

        MLUDailyReport report = new MLUDailyReport();

        updateReportFields(report, mluReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            mluDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String updateReport(MLUDailyReport report, MLU_ReportDescriptionDTO mluReportDescription) {

        updateReportFields(report, mluReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            mluDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String deleteReport(String reportUid) {
        mluDailyReportRepository.deleteByReportUid(reportUid);
        return null;
    }

    private void updateReportFields (MLUDailyReport report, MLU_ReportDescriptionDTO mluReportDescription) {
        report.setReportUid(mluReportDescription.getReportUid());
        report.setReportDate(mluReportDescription.getReportDate());
        report.setReportNumber(mluReportDescription.getReportNumber());
        report.setDocumentCode(mluReportDescription.getDocumentCode());
        report.setComment(mluReportDescription.getComment());
        report.setApproved(mluReportDescription.isApproved());
        report.setLastApproveDate(mluReportDescription.getLastApproveDate());
        report.setLastApproveUser(mluReportDescription.getLastApproveUser());
        report.setFullDay(mluReportDescription.isFullDay());

        report.setWellNameLegal(mluReportDescription.getWellNameLegal());
        report.setWellField(mluReportDescription.getWellField());
        report.setWellCluster(mluReportDescription.getWellCluster());
        report.setWellCustomer(mluReportDescription.getWellCustomer());
        report.setRigName(mluReportDescription.getRigName());
        report.setProjectName(mluReportDescription.getProjectName());
        report.setProjectPhase(mluReportDescription.getProjectPhase());
    }

    private String updateReportData(MLUDailyReport report) {

        ResponseWrapper<MLU_ReportDataDTO> reportDataWrapper = mluApiService.getReportData(report.getReportUid());

        if (reportDataWrapper.getError() != null) {
            return reportDataWrapper.getError();
        }

        var reportData = reportDataWrapper.getResult();

        if (report.getReportDatum() == null) {
            report.setReportDatum(new ArrayList<>());
        }
        else {
            report.getReportDatum().clear();
        }

        for (MLU_ReportDataItemDTO mluReportDataItemDTO : reportData.getReportDatum()) {
            MLUDailyReportData mluDailyReportData = new MLUDailyReportData();

            mluDailyReportData.setDataType(mluReportDataItemDTO.getDataType());
            mluDailyReportData.setContent(mluReportDataItemDTO.getContent());

            mluDailyReportData.setReport(report);
            report.getReportDatum().add(mluDailyReportData);
        }

        return null;
    }
}
