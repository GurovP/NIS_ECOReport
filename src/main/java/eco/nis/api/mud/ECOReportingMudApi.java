package eco.nis.api.mud;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.api.ECOReportingBaseApi;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.StatusDTO;
import eco.nis.models.dto.mud.Mud_ReportDataDTO;
import eco.nis.models.dto.mud.Mud_ReportDescriptionDTO;
import org.asynchttpclient.Request;
import org.springframework.http.HttpHeaders;

import static org.asynchttpclient.Dsl.get;

public class ECOReportingMudApi extends ECOReportingBaseApi {

    private static final String mudPath = "/api/mud/v1";

    public ECOReportingMudApi(ECOReportingServiceSettingsDTO apiSettings) {
        super(apiSettings);
    }

    @Override
    protected String getReportPath() {
        return mudPath;
    }

    public Mud_ReportDescriptionDTO[] listReports() throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getListReportsRequest());
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), Mud_ReportDescriptionDTO[].class);
    }

    public Mud_ReportDataDTO getReportData(String reportUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetReportRequest(reportUid));
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), Mud_ReportDataDTO.class);
    }

    private Request getListReportsRequest() {

        return get(apiSettings.getApiUrl() + mudPath + "/reports")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    private Request getGetReportRequest(String reportUid) throws JsonProcessingException {

        return get(apiSettings.getApiUrl() + mudPath + "/reportData/"+ reportUid)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }
}
