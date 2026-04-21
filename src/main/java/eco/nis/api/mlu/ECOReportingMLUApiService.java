package eco.nis.api.mlu;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.mlu.MLU_ReportDataDTO;
import eco.nis.models.dto.mlu.MLU_ReportDescriptionDTO;
import eco.nis.models.entities.ECOReportingServiceSettings;
import eco.nis.repositories.ECOReportingServiceSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ECOReportingMLUApiService {

    private final ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository;

    public ECOReportingMLUApiService(ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository) {

        this.ecoReportingServiceSettingsRepository = ecoReportingServiceSettingsRepository;

    }

    public ResponseWrapper<MLU_ReportDescriptionDTO[]> listReports() {

        try {
            var ecoReportingMLUApi = getECOReportingMLUApi();
            if (ecoReportingMLUApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingMLUApi not defined");
                return new ResponseWrapper<>(new MLU_ReportDescriptionDTO[0], "ecoReportingMLUApi not defined");
            }
            MLU_ReportDescriptionDTO[] listReports = ecoReportingMLUApi.listReports();
            return new ResponseWrapper<>(listReports);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingMLUApiService.listReports", e);
            return new ResponseWrapper<>(new MLU_ReportDescriptionDTO[0], e.getMessage());
        }
    }

    public ResponseWrapper<MLU_ReportDataDTO> getReportData(String reportUid) {

        try {
            var ecoReportingMLUApi = getECOReportingMLUApi();
            if (ecoReportingMLUApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingMLUApi not defined");
                return new ResponseWrapper<>(null, "ecoReportingMLUApi not defined");
            }
            MLU_ReportDataDTO reportData = ecoReportingMLUApi.getReportData(reportUid);
            return new ResponseWrapper<>(reportData);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingMLUApiService.getReportData", e);
            return new ResponseWrapper<>(null, e.getMessage());
        }
    }

    private ECOReportingMLUApi _ecoReportingMLUApi;
    private synchronized ECOReportingMLUApi getECOReportingMLUApi() {

        ECOReportingServiceSettingsDTO apiSettings = getApiSettings();

        if (apiSettings == null) {
            this._ecoReportingMLUApi = null;
            return null;
        }

        if (this._ecoReportingMLUApi == null || !Objects.equals(this._ecoReportingMLUApi.getApiSettings(), apiSettings)) {
            this._ecoReportingMLUApi = new ECOReportingMLUApi(apiSettings);
        }

        return this._ecoReportingMLUApi;
    }

    private ECOReportingServiceSettingsDTO getApiSettings() {
        List<ECOReportingServiceSettings> settings = ecoReportingServiceSettingsRepository.findAll();
        if (!settings.isEmpty())
            return ECOReportingServiceSettingsDTO.fromModel(settings.get(0));
        return null;
    }
}
