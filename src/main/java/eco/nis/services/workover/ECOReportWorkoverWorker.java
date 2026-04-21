package eco.nis.services.workover;

import eco.nis.api.ResponseWrapper;
import eco.nis.api.workover.ECOReportingWorkoverApiService;
import eco.nis.models.dto.workover.Workover_ReportDataDTO;
import eco.nis.models.dto.workover.Workover_ReportDataItemDTO;
import eco.nis.models.dto.workover.Workover_ReportDescriptionDTO;
import eco.nis.models.entities.workover.WorkoverDailyReport;
import eco.nis.models.entities.workover.WorkoverDailyReportData;
import eco.nis.repositories.WorkoverDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.services.ECOReportBaseWorker;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

public class ECOReportWorkoverWorker extends ECOReportBaseWorker<WorkoverDailyReport, Workover_ReportDescriptionDTO> {

    private final String reportType = "Workover";

    private final ECOReportingWorkoverApiService workoverApiService;
    private final WorkoverDailyReportRepository workoverDailyReportRepository;

    public ECOReportWorkoverWorker(PlatformTransactionManager transactionManager,
                                   ECOReportingWorkoverApiService workoverApiService,
                                   WorkoverDailyReportRepository workoverDailyReportRepository,
                                   ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                                   Logger logger) {

        super(transactionManager, ecoReportingSyncLogRepository, logger);

        this.workoverApiService = workoverApiService;
        this.workoverDailyReportRepository = workoverDailyReportRepository;
    }

    @Override
    protected String getReportType() {
        return reportType;
    }

    @Override
    protected ResponseWrapper<Workover_ReportDescriptionDTO[]> listReportsFromAPI() {
        return workoverApiService.listReports();
    }

    @Override
    protected List<String> allReportUidsInDB() {
        return workoverDailyReportRepository.findAllReportUids();
    }

    @Override
    protected WorkoverDailyReport findByReportUid(String reportUid) {
        return workoverDailyReportRepository.findByReportUid(reportUid);
    }

    @Override
    protected String getReportDTOUid(Workover_ReportDescriptionDTO reportDTO) {
        return reportDTO.getReportUid();
    }

    @Override
    protected String getReportUid(WorkoverDailyReport report) {
        return report.getReportUid();
    }

    @Override
    protected boolean needUpdateReport(WorkoverDailyReport report, Workover_ReportDescriptionDTO workoverReportDescription) {
        return report.isApprovedDSV() != workoverReportDescription.isApprovedDSV() ||
                report.isApprovedRTOC() != workoverReportDescription.isApprovedRTOC();
    }

    @Override
    protected String addReport(Workover_ReportDescriptionDTO workoverReportDescription) {

        WorkoverDailyReport report = new WorkoverDailyReport();

        updateReportFields(report, workoverReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            workoverDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String updateReport(WorkoverDailyReport report, Workover_ReportDescriptionDTO workoverReportDescription) {

        updateReportFields(report, workoverReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            workoverDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String deleteReport(String reportUid) {
        workoverDailyReportRepository.deleteByReportUid(reportUid);
        return null;
    }

    private void updateReportFields (WorkoverDailyReport report, Workover_ReportDescriptionDTO workoverReportDescription) {
        report.setReportUid(workoverReportDescription.getReportUid());
        report.setReportDate(workoverReportDescription.getReportDate());
        report.setReportNumber(workoverReportDescription.getReportNumber());
        report.setDocumentCode(workoverReportDescription.getDocumentCode());
        report.setComment(workoverReportDescription.getComment());
        report.setApprovedDSV(workoverReportDescription.isApprovedDSV());
        report.setLastApproveDSVDate(workoverReportDescription.getLastApproveDSVDate());
        report.setLastApproveDSVUser(workoverReportDescription.getLastApproveDSVUser());
        report.setApprovedRTOC(workoverReportDescription.isApprovedRTOC());
        report.setLastApproveRTOCDate(workoverReportDescription.getLastApproveRTOCDate());
        report.setLastApproveRTOCUser(workoverReportDescription.getLastApproveRTOCUser());
        report.setWellNumGovt(workoverReportDescription.getWellNumGovt());
        report.setWellNameLegal(workoverReportDescription.getWellNameLegal());
        report.setWellField(workoverReportDescription.getWellField());
        report.setWellCluster(workoverReportDescription.getWellCluster());
        report.setWellCustomer(workoverReportDescription.getWellCustomer());
        report.setRigName(workoverReportDescription.getRigName());
        report.setCrewName(workoverReportDescription.getCrewName());
        report.setProjectName(workoverReportDescription.getProjectName());
        report.setProjectPhase(workoverReportDescription.getProjectPhase());
        report.setWorkoverStartDate(workoverReportDescription.getWorkoverStartDate());
        report.setWorkoverEndDate(workoverReportDescription.getWorkoverEndDate());

        report.setWellNameLegal(workoverReportDescription.getWellNameLegal());
        report.setWellField(workoverReportDescription.getWellField());
        report.setWellCluster(workoverReportDescription.getWellCluster());
        report.setWellCustomer(workoverReportDescription.getWellCustomer());
        report.setRigName(workoverReportDescription.getRigName());
        report.setProjectName(workoverReportDescription.getProjectName());
        report.setProjectPhase(workoverReportDescription.getProjectPhase());
    }

    private String updateReportData(WorkoverDailyReport report) {

        ResponseWrapper<Workover_ReportDataDTO> reportDataWrapper = workoverApiService.getReportData(report.getReportUid());

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

        for (Workover_ReportDataItemDTO workoverReportDataItemDTO : reportData.getReportDatum()) {
            WorkoverDailyReportData workoverDailyReportData = new WorkoverDailyReportData();

            workoverDailyReportData.setDataType(workoverReportDataItemDTO.getDataType());
            workoverDailyReportData.setContent(workoverReportDataItemDTO.getContent());

            workoverDailyReportData.setReport(report);
            report.getReportDatum().add(workoverDailyReportData);
        }

        return null;
    }
}
