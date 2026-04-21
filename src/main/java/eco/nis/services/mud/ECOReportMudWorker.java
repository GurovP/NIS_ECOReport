package eco.nis.services.mud;

import eco.nis.api.ResponseWrapper;
import eco.nis.api.mud.ECOReportingMudApiService;
import eco.nis.models.dto.mud.Mud_ReportDataDTO;
import eco.nis.models.dto.mud.Mud_ReportDataItemDTO;
import eco.nis.models.dto.mud.Mud_ReportDescriptionDTO;
import eco.nis.models.entities.mud.MudDailyReport;
import eco.nis.models.entities.mud.MudDailyReportData;
import eco.nis.repositories.MudDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.services.ECOReportBaseWorker;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

public class ECOReportMudWorker extends ECOReportBaseWorker<MudDailyReport, Mud_ReportDescriptionDTO> {

    private final String reportType = "Mud";

    private final ECOReportingMudApiService mudApiService;
    private final MudDailyReportRepository mudDailyReportRepository;

    public ECOReportMudWorker(PlatformTransactionManager transactionManager,
                              ECOReportingMudApiService mudApiService,
                              MudDailyReportRepository mudDailyReportRepository,
                              ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                              Logger logger) {

        super(transactionManager, ecoReportingSyncLogRepository, logger);

        this.mudApiService = mudApiService;
        this.mudDailyReportRepository = mudDailyReportRepository;
    }

    @Override
    protected String getReportType() {
        return reportType;
    }

    @Override
    protected ResponseWrapper<Mud_ReportDescriptionDTO[]> listReportsFromAPI() {
        return mudApiService.listReports();
    }

    @Override
    protected List<String> allReportUidsInDB() {
        return mudDailyReportRepository.findAllReportUids();
    }

    @Override
    protected MudDailyReport findByReportUid(String reportUid) {
        return mudDailyReportRepository.findByReportUid(reportUid);
    }

    @Override
    protected String getReportDTOUid(Mud_ReportDescriptionDTO reportDTO) {
        return reportDTO.getReportUid();
    }

    @Override
    protected String getReportUid(MudDailyReport report) {
        return report.getReportUid();
    }

    @Override
    protected boolean needUpdateReport(MudDailyReport report, Mud_ReportDescriptionDTO mudReportDescription) {
        return report.isApproved() != mudReportDescription.isApproved();
    }

    @Override
    protected String addReport(Mud_ReportDescriptionDTO mudReportDescription) {

        MudDailyReport report = new MudDailyReport();

        updateReportFields(report, mudReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            mudDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String updateReport(MudDailyReport report, Mud_ReportDescriptionDTO mudReportDescription) {

        updateReportFields(report, mudReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            mudDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String deleteReport(String reportUid) {
        mudDailyReportRepository.deleteByReportUid(reportUid);
        return null;
    }

    private void updateReportFields (MudDailyReport report, Mud_ReportDescriptionDTO mudReportDescription) {
        report.setReportUid(mudReportDescription.getReportUid());
        report.setReportDate(mudReportDescription.getReportDate());
        report.setReportNumber(mudReportDescription.getReportNumber());
        report.setDocumentCode(mudReportDescription.getDocumentCode());
        report.setComment(mudReportDescription.getComment());
        report.setApproved(mudReportDescription.isApproved());
        report.setLastApproveDate(mudReportDescription.getLastApproveDate());
        report.setLastApproveUser(mudReportDescription.getLastApproveUser());

        report.setWellNameLegal(mudReportDescription.getWellNameLegal());
        report.setWellField(mudReportDescription.getWellField());
        report.setWellCluster(mudReportDescription.getWellCluster());
        report.setWellCustomer(mudReportDescription.getWellCustomer());
        report.setRigName(mudReportDescription.getRigName());
        report.setProjectName(mudReportDescription.getProjectName());
        report.setProjectPhase(mudReportDescription.getProjectPhase());
    }

    private String updateReportData(MudDailyReport report) {

        ResponseWrapper<Mud_ReportDataDTO> reportDataWrapper = mudApiService.getReportData(report.getReportUid());

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

        for (Mud_ReportDataItemDTO mudReportDataItemDTO : reportData.getReportDatum()) {
            MudDailyReportData mudDailyReportData = new MudDailyReportData();

            mudDailyReportData.setDataType(mudReportDataItemDTO.getDataType());
            mudDailyReportData.setContent(mudReportDataItemDTO.getContent());

            mudDailyReportData.setReport(report);
            report.getReportDatum().add(mudDailyReportData);
        }

        return null;
    }
}
