package eco.nis.api.workover;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.api.ECOReportingBaseApi;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.StatusDTO;
import eco.nis.models.dto.workover.Workover_ReportDataDTO;
import eco.nis.models.dto.workover.Workover_ReportDescriptionDTO;
import org.asynchttpclient.Request;
import org.springframework.http.HttpHeaders;

import static org.asynchttpclient.Dsl.get;

public class ECOReportingWorkoverApi extends ECOReportingBaseApi {

    private static final String workoverPath = "/api/workover/v1";

    public ECOReportingWorkoverApi(ECOReportingServiceSettingsDTO apiSettings) {
        super(apiSettings);
    }

    @Override
    protected String getReportPath() {
        return workoverPath;
    }

    public Workover_ReportDescriptionDTO[] listReports() throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getListReportsRequest());
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), Workover_ReportDescriptionDTO[].class);
    }

    public Workover_ReportDataDTO getReportData(String reportUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetReportRequest(reportUid));
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), Workover_ReportDataDTO.class);
    }

    private Request getListReportsRequest() {

        return get(apiSettings.getApiUrl() + workoverPath + "/reports")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    private Request getGetReportRequest(String reportUid) throws JsonProcessingException {

        return get(apiSettings.getApiUrl() + workoverPath + "/reportData/"+ reportUid)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }
}
