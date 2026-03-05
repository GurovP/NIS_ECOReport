package eco.nis.api;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.models.DDR_ReportDataDTO;
import eco.nis.models.DDR_ReportDescriptionDTO;
import eco.nis.models.ECOReportingServiceSettingsDTO;
import eco.nis.models.entities.ECOReportingServiceSettings;
import eco.nis.repositories.ECOReportingServiceSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ECOReportingDDRApiService {

    private final ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository;

    public ECOReportingDDRApiService(ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository) {

        this.ecoReportingServiceSettingsRepository = ecoReportingServiceSettingsRepository;

    }

    public DDR_ReportDescriptionDTO[] listReports() {

        try {
            var ecoReportingDDRApi = getECOReportingDDRApi();
            if (ecoReportingDDRApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingDDRApi not defined");
                return new DDR_ReportDescriptionDTO[0];
            }
            return ecoReportingDDRApi.listReports();
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingDDRApiService.listReports", e);
        }

        return new DDR_ReportDescriptionDTO[0];
    }

    public DDR_ReportDataDTO getReportData(String reportUid) {

        try {
            var ecoReportingDDRApi = getECOReportingDDRApi();
            if (ecoReportingDDRApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingDDRApi not defined");
                return null;
            }
            return ecoReportingDDRApi.getReportData(reportUid);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingDDRApiService.getReportData", e);
        }

        return null;
    }

    private ECOReportingDDRApi _ecoReportingDDRApi;
    private synchronized ECOReportingDDRApi getECOReportingDDRApi() {

        ECOReportingServiceSettingsDTO apiSettings = getApiSettings();

        if (apiSettings == null) {
            this._ecoReportingDDRApi = null;
            return null;
        }

        if (this._ecoReportingDDRApi == null || !Objects.equals(this._ecoReportingDDRApi.getApiSettings(), apiSettings)) {
            this._ecoReportingDDRApi = new ECOReportingDDRApi(apiSettings);
        }

        return this._ecoReportingDDRApi;
    }

    private ECOReportingServiceSettingsDTO getApiSettings() {
        List<ECOReportingServiceSettings> settings = ecoReportingServiceSettingsRepository.findAll();
        if (!settings.isEmpty())
            return ECOReportingServiceSettingsDTO.fromModel(settings.get(0));
        return null;
    }
}
