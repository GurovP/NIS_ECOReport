package eco.nis.api.ddr;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.dto.ddr.DDR_ReportDataDTO;
import eco.nis.models.dto.ddr.DDR_ReportDescriptionDTO;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
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

    public ResponseWrapper<DDR_ReportDescriptionDTO[]> listReports() {

        try {
            var ecoReportingDDRApi = getECOReportingDDRApi();
            if (ecoReportingDDRApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingDDRApi not defined");
                return new ResponseWrapper<>(new DDR_ReportDescriptionDTO[0], "ecoReportingDDRApi not defined");
            }
            DDR_ReportDescriptionDTO[] listReports = ecoReportingDDRApi.listReports();
            return new ResponseWrapper<>(listReports);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingDDRApiService.listReports", e);
            return new ResponseWrapper<>(new DDR_ReportDescriptionDTO[0], e.getMessage());
        }
    }

    public ResponseWrapper<DDR_ReportDataDTO> getReportData(String reportUid) {

        try {
            var ecoReportingDDRApi = getECOReportingDDRApi();
            if (ecoReportingDDRApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingDDRApi not defined");
                return new ResponseWrapper<>(null, "ecoReportingDDRApi not defined");
            }
            DDR_ReportDataDTO reportData = ecoReportingDDRApi.getReportData(reportUid);
            return new ResponseWrapper<>(reportData);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingDDRApiService.getReportData", e);
            return new ResponseWrapper<>(null, e.getMessage());
        }
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
