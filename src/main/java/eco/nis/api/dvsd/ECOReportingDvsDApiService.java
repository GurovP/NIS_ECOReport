package eco.nis.api.dvsd;

import eco.nis.NIS_ECOReportApplication;
import eco.nis.api.ResponseWrapper;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dvsd.DvsD_DvsDTableDataDTO;
import eco.nis.models.dvsd.DvsD_DvsDTableDescriptionDTO;
import eco.nis.models.entities.ECOReportingServiceSettings;
import eco.nis.repositories.ECOReportingServiceSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ECOReportingDvsDApiService {

    private final ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository;

    public ECOReportingDvsDApiService(ECOReportingServiceSettingsRepository ecoReportingServiceSettingsRepository) {

        this.ecoReportingServiceSettingsRepository = ecoReportingServiceSettingsRepository;

    }

    public ResponseWrapper<DvsD_DvsDTableDescriptionDTO[]> listTables() {

        try {
            var ecoReportingDvsDApi = getECOReportingDvsDApi();
            if (ecoReportingDvsDApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingDvsDApi not defined");
                return new ResponseWrapper<>(new DvsD_DvsDTableDescriptionDTO[0], "ecoReportingDvsDApi not defined");
            }
            DvsD_DvsDTableDescriptionDTO[] listTables = ecoReportingDvsDApi.listTables();
            return new ResponseWrapper<>(listTables);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingDvsDApiService.listTables", e);
            return new ResponseWrapper<>(new DvsD_DvsDTableDescriptionDTO[0], e.getMessage());
        }
    }

    public ResponseWrapper<DvsD_DvsDTableDataDTO> getTableData(String tableUid) {

        try {
            var ecoReportingDvsDApi = getECOReportingDvsDApi();
            if (ecoReportingDvsDApi == null) {
                NIS_ECOReportApplication.logger.warn("ecoReportingDvsDApi not defined");
                return new ResponseWrapper<>(null, "ecoReportingDvsDApi not defined");
            }
            DvsD_DvsDTableDataDTO reportData = ecoReportingDvsDApi.getTableData(tableUid);
            return new ResponseWrapper<>(reportData);
        } catch (Exception e) {
            NIS_ECOReportApplication.logger.error("ECOReportingDvsDApiService.getTableData", e);
            return new ResponseWrapper<>(null, e.getMessage());
        }
    }

    private ECOReportingDvsDApi _ecoReportingDvsDApi;
    private synchronized ECOReportingDvsDApi getECOReportingDvsDApi() {

        ECOReportingServiceSettingsDTO apiSettings = getApiSettings();

        if (apiSettings == null) {
            this._ecoReportingDvsDApi = null;
            return null;
        }

        if (this._ecoReportingDvsDApi == null || !Objects.equals(this._ecoReportingDvsDApi.getApiSettings(), apiSettings)) {
            this._ecoReportingDvsDApi = new ECOReportingDvsDApi(apiSettings);
        }

        return this._ecoReportingDvsDApi;
    }

    private ECOReportingServiceSettingsDTO getApiSettings() {
        List<ECOReportingServiceSettings> settings = ecoReportingServiceSettingsRepository.findAll();
        if (!settings.isEmpty())
            return ECOReportingServiceSettingsDTO.fromModel(settings.get(0));
        return null;
    }
}
