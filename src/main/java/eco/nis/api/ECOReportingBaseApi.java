package eco.nis.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eco.nis.models.ECOReportingServiceSettingsDTO;
import eco.nis.models.HealthDTO;
import eco.nis.models.VersionDTO;
import eco.nis.utilities.ObjectMapperUtility;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.get;

public abstract class ECOReportingBaseApi implements Closeable {

    protected final ECOReportingServiceSettingsDTO apiSettings;
    protected final String apiBasicAuthenticationHeader;
    protected final AsyncHttpClient httpClient;
    protected final ObjectMapper objectMapper = ObjectMapperUtility.getObjectMapper();

    public ECOReportingBaseApi(ECOReportingServiceSettingsDTO apiSettings) {

        this.apiSettings = apiSettings;
        String encoded = Base64.getEncoder()
                .encodeToString((apiSettings.getApiUser() + ':' + apiSettings.getApiPass()).getBytes(StandardCharsets.UTF_8));
        apiBasicAuthenticationHeader = "Basic " + encoded;
        httpClient = asyncHttpClient();
    }

    public ECOReportingServiceSettingsDTO getApiSettings() {
        return apiSettings;
    }

    protected abstract String getReportPath();

    public VersionDTO getVersion(String reportUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetVersionRequest());
        if (error != null)
            throw new Exception(error);

        return objectMapper.readValue(responseString.toString(), VersionDTO.class);
    }

    public HealthDTO getHealth(String reportUid) throws Exception {

        var responseString = new StringBuilder();
        var error = executeRequest(responseString, getGetHealthRequest());
        if (error != null)
            throw new Exception(error);

        return objectMapper.readValue(responseString.toString(), HealthDTO.class);
    }

    private Request getGetVersionRequest() {

        return get(apiSettings.getApiUrl() + getReportPath() + "/version")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    private Request getGetHealthRequest() {

        return get(apiSettings.getApiUrl() + getReportPath() + "/health")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .addHeader(HttpHeaders.AUTHORIZATION, apiBasicAuthenticationHeader)
                .build();
    }

    protected String executeRequest(StringBuilder responseString, Request request) throws ExecutionException, InterruptedException, IOException {

        var whenResponse = httpClient.executeRequest(request);
        int retCode;
        String statusText;
        try {

            var response = whenResponse.get(apiSettings.getTimeout(), TimeUnit.SECONDS);
            retCode = response.getStatusCode();
            statusText = response.getStatusText();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getResponseBodyAsStream()));
            while (reader.ready())
                responseString.append(reader.readLine());

        } catch (TimeoutException ignored) {

            retCode = 500;
            statusText = "Request timeout " + apiSettings.getTimeout();
        }

        return retCode != 200 ? String.format("Reporting api execute request error. Return code %s: %s. \\n%s", retCode, statusText, responseString) : null;
    }

    @Override
    public void close() throws IOException {

        if (httpClient != null && !httpClient.isClosed())
            httpClient.close();
    }
}
