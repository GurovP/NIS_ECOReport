package eco.nis.api.mlu;

import com.fasterxml.jackson.core.JsonProcessingException;
import eco.nis.api.ECOReportingBaseApi;
import eco.nis.models.dto.ECOReportingServiceSettingsDTO;
import eco.nis.models.dto.StatusDTO;
import eco.nis.models.dto.mlu.MLU_ReportDataDTO;
import eco.nis.models.dto.mlu.MLU_ReportDescriptionDTO;
import org.asynchttpclient.Request;
import org.springframework.http.HttpHeaders;

import static org.asynchttpclient.Dsl.get;

public class ECOReportingMLUApi extends ECOReportingBaseApi {

    private static final String mluPath = "/api/mlu/v1";

    public ECOReportingMLUApi(ECOReportingServiceSettingsDTO apiSettings) {
        super(apiSettings);
    }

    @Override
    protected String getReportPath() {
        return mluPath;
    }

    public MLU_ReportDescriptionDTO[] listReports() throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getListReportsRequest());
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), MLU_ReportDescriptionDTO[].class);
    }

    public MLU_ReportDataDTO getReportData(String reportUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetReportRequest(reportUid));
        if (error != null) {
            throw new Exception(responseString.toString());
        }

        return objectMapper.readValue(responseString.toString(), MLU_ReportDataDTO.class);
    }

    private Request getListReportsRequest() {

        return get(apiSettings.getApiUrl() + mluPath + "/reports")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    private Request getGetReportRequest(String reportUid) throws JsonProcessingException {

        return get(apiSettings.getApiUrl() + mluPath + "/reportData/"+ reportUid)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }
}
