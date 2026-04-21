package eco.nis.api.ddr;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.api.ECOReportingBaseApi;
import eco.nis.models.dto.ddr.DDR_ReportDataDTO;
import eco.nis.models.dto.ddr.DDR_ReportDescriptionDTO;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.StatusDTO;
import org.asynchttpclient.Request;
import org.springframework.http.HttpHeaders;

import static org.asynchttpclient.Dsl.*;

public class ECOReportingDDRApi extends ECOReportingBaseApi {

    private static final String ddrPath = "/api/ddr/v1";

    public ECOReportingDDRApi(ECOReportingServiceSettingsDTO apiSettings) {
        super(apiSettings);
    }

    @Override
    protected String getReportPath() {
        return ddrPath;
    }

    public DDR_ReportDescriptionDTO[] listReports() throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getListReportsRequest());
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), DDR_ReportDescriptionDTO[].class);
    }

    public DDR_ReportDataDTO getReportData(String reportUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetReportRequest(reportUid));
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), DDR_ReportDataDTO.class);
    }

    private Request getListReportsRequest() {

        return get(apiSettings.getApiUrl() + ddrPath + "/reports")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    private Request getGetReportRequest(String reportUid) throws JsonProcessingException {

        return get(apiSettings.getApiUrl() + ddrPath + "/reportData/"+ reportUid)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }
}
