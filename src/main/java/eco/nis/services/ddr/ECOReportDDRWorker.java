package eco.nis.services.ddr;

import eco.nis.api.ddr.ECOReportingDDRApiService;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.dto.ddr.DDR_ReportDataDTO;
import eco.nis.models.dto.ddr.DDR_ReportDataItemDTO;
import eco.nis.models.dto.ddr.DDR_ReportDescriptionDTO;
import eco.nis.models.dto.ddr.DDR_TimeDistributionDataItemDTO;
import eco.nis.models.entities.ddr.DDRDailyReport;
import eco.nis.models.entities.ddr.DDRDailyReportData;
import eco.nis.models.entities.ddr.DDRDailyTimeDistributionData;
import eco.nis.models.entities.ddr.DDRDailyTimeDistributionRecord;
import eco.nis.repositories.DDRDailyReportRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.services.ECOReportBaseWorker;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

public class ECOReportDDRWorker extends ECOReportBaseWorker<DDRDailyReport, DDR_ReportDescriptionDTO> {

    private final String reportType = "DDR";

    private final ECOReportingDDRApiService ddrApiService;
    private final DDRDailyReportRepository ddrDailyReportRepository;

    public ECOReportDDRWorker(PlatformTransactionManager transactionManager,
                              ECOReportingDDRApiService ddrApiService,
                              DDRDailyReportRepository ddrDailyReportRepository,
                              ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                              Logger logger) {

        super(transactionManager, ecoReportingSyncLogRepository, logger);

        this.ddrApiService = ddrApiService;
        this.ddrDailyReportRepository = ddrDailyReportRepository;
    }

    @Override
    protected String getReportType() {
        return reportType;
    }

    @Override
    protected ResponseWrapper<DDR_ReportDescriptionDTO[]> listReportsFromAPI() {
        return ddrApiService.listReports();
    }

    @Override
    protected List<String> allReportUidsInDB() {
        return ddrDailyReportRepository.findAllReportUids();
    }

    @Override
    protected DDRDailyReport findByReportUid(String reportUid) {
        return ddrDailyReportRepository.findByReportUid(reportUid);
    }

    @Override
    protected String getReportDTOUid(DDR_ReportDescriptionDTO reportDTO) {
        return reportDTO.getReportUid();
    }

    @Override
    protected String getReportUid(DDRDailyReport report) {
        return report.getReportUid();
    }

    @Override
    protected boolean needUpdateReport(DDRDailyReport report, DDR_ReportDescriptionDTO ddrReportDescription) {
        return report.isApproved() != ddrReportDescription.isApproved();
    }

    @Override
    protected String addReport(DDR_ReportDescriptionDTO ddrReportDescription) {

        DDRDailyReport report = new DDRDailyReport();

        updateReportFields(report, ddrReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            ddrDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String updateReport(DDRDailyReport report, DDR_ReportDescriptionDTO ddrReportDescription) {

        updateReportFields(report, ddrReportDescription);

        String error = updateReportData(report);

        if (error == null) {
            ddrDailyReportRepository.save(report);
        }

        return error;
    }

    @Override
    protected String deleteReport(String reportUid) {
        ddrDailyReportRepository.deleteByReportUid(reportUid);
        return null;
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

        report.setWellNameLegal(ddrReportDescription.getWellNameLegal());
        report.setWellField(ddrReportDescription.getWellField());
        report.setWellCluster(ddrReportDescription.getWellCluster());
        report.setWellCustomer(ddrReportDescription.getWellCustomer());
        report.setRigName(ddrReportDescription.getRigName());
        report.setProjectName(ddrReportDescription.getProjectName());
        report.setProjectPhase(ddrReportDescription.getProjectPhase());
    }

    private String updateReportData(DDRDailyReport report) {

        ResponseWrapper<DDR_ReportDataDTO> reportDataWrapper = ddrApiService.getReportData(report.getReportUid());

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

        return null;
    }
}
