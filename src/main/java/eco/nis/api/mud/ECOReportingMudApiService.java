package eco.nis.api.mud;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.mud.Mud_ReportDataDTO;
import eco.nis.models.dto.mud.Mud_ReportDescriptionDTO;
import eco.nis.models.entities.ECOReportingServiceSettings;
import eco.nis.repositories.ECOReportingServiceSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ECOReportingMudApiService {

    private final ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository;

    public ECOReportingMudApiService(ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository) {

        this.ecoReportingServiceSettingsRepository = ecoReportingServiceSettingsRepository;

    }

    public ResponseWrapper<Mud_ReportDescriptionDTO[]> listReports() {

        try {
            var ecoReportingMudApi = getECOReportingMudApi();
            if (ecoReportingMudApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingMudApi not defined");
                return new ResponseWrapper<>(new Mud_ReportDescriptionDTO[0], "ecoReportingMudApi not defined");
            }
            Mud_ReportDescriptionDTO[] listReports = ecoReportingMudApi.listReports();
            return new ResponseWrapper<>(listReports);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingMudApiService.listReports", e);
            return new ResponseWrapper<>(new Mud_ReportDescriptionDTO[0], e.getMessage());
        }
    }

    public ResponseWrapper<Mud_ReportDataDTO> getReportData(String reportUid) {

        try {
            var ecoReportingMudApi = getECOReportingMudApi();
            if (ecoReportingMudApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingMudApi not defined");
                return new ResponseWrapper<>(null, "ecoReportingMudApi not defined");
            }
            Mud_ReportDataDTO reportData = ecoReportingMudApi.getReportData(reportUid);
            return new ResponseWrapper<>(reportData);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingMudApiService.getReportData", e);
            return new ResponseWrapper<>(null, e.getMessage());
        }
    }

    private ECOReportingMudApi _ecoReportingMudApi;
    private synchronized ECOReportingMudApi getECOReportingMudApi() {

        ECOReportingServiceSettingsDTO apiSettings = getApiSettings();

        if (apiSettings == null) {
            this._ecoReportingMudApi = null;
            return null;
        }

        if (this._ecoReportingMudApi == null || !Objects.equals(this._ecoReportingMudApi.getApiSettings(), apiSettings)) {
            this._ecoReportingMudApi = new ECOReportingMudApi(apiSettings);
        }

        return this._ecoReportingMudApi;
    }

    private ECOReportingServiceSettingsDTO getApiSettings() {
        List<ECOReportingServiceSettings> settings = ecoReportingServiceSettingsRepository.findAll();
        if (!settings.isEmpty())
            return ECOReportingServiceSettingsDTO.fromModel(settings.get(0));
        return null;
    }
}
