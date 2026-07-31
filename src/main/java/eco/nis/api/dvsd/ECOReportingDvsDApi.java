package eco.nis.api.dvsd;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.api.ECOReportingBaseApi;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dvsd.DvsD_DvsDTableDataDTO;
import eco.nis.models.dvsd.DvsD_DvsDTableDescriptionDTO;
import org.asynchttpclient.Request;
import org.springframework.http.HttpHeaders;

import static org.asynchttpclient.Dsl.get;

public class ECOReportingDvsDApi extends ECOReportingBaseApi {

    private static final String dvsdPath = "/api/dvsd/v1";

    public ECOReportingDvsDApi(ECOReportingServiceSettingsDTO apiSettings) {
        super(apiSettings);
    }

    @Override
    protected String getReportPath() {
        return dvsdPath;
    }

    public DvsD_DvsDTableDescriptionDTO[] listTables() throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getListTablesRequest());
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), DvsD_DvsDTableDescriptionDTO[].class);
    }

    public DvsD_DvsDTableDataDTO getTableData(String tableUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetTableRequest(tableUid));
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), DvsD_DvsDTableDataDTO.class);
    }

    private Request getListTablesRequest() {

        return get(apiSettings.getApiUrl() + dvsdPath + "/tables")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    private Request getGetTableRequest(String tableUid) throws JsonProcessingException {

        return get(apiSettings.getApiUrl() + dvsdPath + "/tableData/"+ tableUid)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }
}
