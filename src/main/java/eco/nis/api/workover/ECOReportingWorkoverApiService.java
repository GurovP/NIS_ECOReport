package eco.nis.api.workover;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.workover.Workover_ReportDataDTO;
import eco.nis.models.dto.workover.Workover_ReportDescriptionDTO;
import eco.nis.models.entities.ECOReportingServiceSettings;
import eco.nis.repositories.ECOReportingServiceSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ECOReportingWorkoverApiService {

    private final ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository;

    public ECOReportingWorkoverApiService(ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository) {

        this.ecoReportingServiceSettingsRepository = ecoReportingServiceSettingsRepository;

    }

    public ResponseWrapper<Workover_ReportDescriptionDTO[]> listReports() {

        try {
            var ecoReportingWorkoverApi = getECOReportingWorkoverApi();
            if (ecoReportingWorkoverApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingWorkoverApi not defined");
                return new ResponseWrapper<>(new Workover_ReportDescriptionDTO[0], "ecoReportingWorkoverApi not defined");
            }
            Workover_ReportDescriptionDTO[] listReports = ecoReportingWorkoverApi.listReports();
            return new ResponseWrapper<>(listReports);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingWorkoverApiService.listReports", e);
            return new ResponseWrapper<>(new Workover_ReportDescriptionDTO[0], e.getMessage());
        }
    }

    public ResponseWrapper<Workover_ReportDataDTO> getReportData(String reportUid) {

        try {
            var ecoReportingWorkoverApi = getECOReportingWorkoverApi();
            if (ecoReportingWorkoverApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingWorkoverApi not defined");
                return new ResponseWrapper<>(null, "ecoReportingWorkoverApi not defined");
            }
            Workover_ReportDataDTO reportData = ecoReportingWorkoverApi.getReportData(reportUid);
            return new ResponseWrapper<>(reportData);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingWorkoverApiService.getReportData", e);
            return new ResponseWrapper<>(null, e.getMessage());
        }
    }

    private ECOReportingWorkoverApi _ecoReportingWorkoverApi;
    private synchronized ECOReportingWorkoverApi getECOReportingWorkoverApi() {

        ECOReportingServiceSettingsDTO apiSettings = getApiSettings();

        if (apiSettings == null) {
            this._ecoReportingWorkoverApi = null;
            return null;
        }

        if (this._ecoReportingWorkoverApi == null || !Objects.equals(this._ecoReportingWorkoverApi.getApiSettings(), apiSettings)) {
            this._ecoReportingWorkoverApi = new ECOReportingWorkoverApi(apiSettings);
        }

        return this._ecoReportingWorkoverApi;
    }

    private ECOReportingServiceSettingsDTO getApiSettings() {
        List<ECOReportingServiceSettings> settings = ecoReportingServiceSettingsRepository.findAll();
        if (!settings.isEmpty())
            return ECOReportingServiceSettingsDTO.fromModel(settings.get(0));
        return null;
    }
}
