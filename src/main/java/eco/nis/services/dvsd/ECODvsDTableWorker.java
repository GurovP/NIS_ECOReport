package eco.nis.services.dvsd;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.api.ResponseWrapper;
import eco.nis.api.dvsd.ECOReportingDvsDApiService;
import eco.nis.models.dvsd.DvsD_DvsDTableDataDTO;
import eco.nis.models.dvsd.DvsD_DvsDTableDataItemDTO;
import eco.nis.models.dvsd.DvsD_DvsDTableDescriptionDTO;
import eco.nis.models.entities.dvsd.DvsDTable;
import eco.nis.repositories.DvsDTableRepository;
import eco.nis.repositories.ECOReportingSyncLogRepository;
import eco.nis.services.ECOReportBaseWorker;
import eco.nis.utilities.ObjectMapperUtility;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ECODvsDTableWorker extends ECOReportBaseWorker<DvsDTable, DvsD_DvsDTableDescriptionDTO> {

    private final String reportType = "DvsD";

    private final ECOReportingDvsDApiService dvsDApiService;
    private final DvsDTableRepository dvsDTableRepository;

    public ECODvsDTableWorker(PlatformTransactionManager transactionManager,
                              ECOReportingDvsDApiService dvsDApiService,
                              DvsDTableRepository dvsDTableRepository,
                              ECOReportingSyncLogRepository ecoReportingSyncLogRepository,
                              Logger logger) {

        super(transactionManager, ecoReportingSyncLogRepository, logger);

        this.dvsDApiService = dvsDApiService;
        this.dvsDTableRepository = dvsDTableRepository;
    }

    @Override
    protected String getReportType() {
        return reportType;
    }

    @Override
    protected ResponseWrapper<DvsD_DvsDTableDescriptionDTO[]> listReportsFromAPI() {
        return dvsDApiService.listTables();
    }

    @Override
    protected List<String> allReportUidsInDB() {
        return dvsDTableRepository.findAllTableUids();
    }

    @Override
    protected DvsDTable findByReportUid(String reportUid) {
        return dvsDTableRepository.findByTableUid(reportUid);
    }

    @Override
    protected String getReportDTOUid(DvsD_DvsDTableDescriptionDTO reportDTO) {
        return reportDTO.getTableUid();
    }

    @Override
    protected String getReportUid(DvsDTable report) {
        return report.getTableUid();
    }

    private boolean needUpdateReport(DvsDTable report, DvsD_DvsDTableDescriptionDTO ddrReportDescription) {
        return !Objects.equals(report.getLastUpdated(), ddrReportDescription.getLastUpdated());
    }

    @Override
    protected String addReport(DvsD_DvsDTableDescriptionDTO ddrReportDescription) {

        DvsDTable report = new DvsDTable();

        updateTableFields(report, ddrReportDescription);

        String error = updateTableData(report);

        if (error == null) {
            dvsDTableRepository.save(report);
        }

        return error;
    }

    @Override
    protected String updateReport(DvsDTable report, DvsD_DvsDTableDescriptionDTO ddrReportDescription) {

        if (!needUpdateReport(report, ddrReportDescription))
            return null;

        updateTableFields(report, ddrReportDescription);

        String error = updateTableData(report);

        if (error == null) {
            dvsDTableRepository.save(report);
        }

        return error;
    }

    @Override
    protected String deleteReport(String reportUid) {
        dvsDTableRepository.deleteByTableUid(reportUid);
        return null;
    }

    private void updateTableFields(DvsDTable report, DvsD_DvsDTableDescriptionDTO dvsdTableDescription) {
        report.setTableUid(dvsdTableDescription.getTableUid());
        report.setLastUpdated(dvsdTableDescription.getLastUpdated());
        report.setStartDate(dvsdTableDescription.getStartDate());

        report.setWellNameLegal(dvsdTableDescription.getWellNameLegal());
        report.setWellField(dvsdTableDescription.getWellField());
        report.setWellCluster(dvsdTableDescription.getWellCluster());
        report.setWellCustomer(dvsdTableDescription.getWellCustomer());
        report.setRigName(dvsdTableDescription.getRigName());
        report.setProjectName(dvsdTableDescription.getProjectName());
        report.setProjectPhase(dvsdTableDescription.getProjectPhase());
    }

    private String updateTableData(DvsDTable table) {

        ResponseWrapper<DvsD_DvsDTableDataDTO> tableDataWrapper = dvsDApiService.getTableData(table.getTableUid());

        if (tableDataWrapper.getError() != null) {
            return tableDataWrapper.getError();
        }

        var tableData = tableDataWrapper.getResult();

        List<DvsD_DvsDTableDataItemDTO> items = tableData.getTableDatum().stream().sorted(Comparator.comparing(DvsD_DvsDTableDataItemDTO::getTaskOrder)).toList();

        try {
            table.setContent(ObjectMapperUtility.getObjectMapper().writeValueAsString(items));
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

        return null;
    }
}
