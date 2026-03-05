package eco.nis.models.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "ecoreportingservicesettings")
public class ECOReportingServiceSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String apiUrl;
    private String apiUser;
    private String apiPass;
    private int timeout;

    public ECOReportingServiceSettings() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiPass() {
        return apiPass;
    }

    public void setApiPass(String apiPass) {
        this.apiPass = apiPass;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
