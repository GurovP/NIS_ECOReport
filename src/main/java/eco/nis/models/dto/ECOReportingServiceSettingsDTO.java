package eco.nis.models.dto;

import eco.nis.models.entities.ECOReportingServiceSettings;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ECOReportingServiceSettingsDTO {

    private String apiUrl;
    private String apiUser;
    private String apiPass;
    private int timeout;

    public static ECOReportingServiceSettingsDTO fromModel(ECOReportingServiceSettings settings) {
        ECOReportingServiceSettingsDTO dto = new ECOReportingServiceSettingsDTO();

        dto.setApiUrl(settings.getApiUrl());
        dto.setApiUser(settings.getApiUser());
        dto.setApiPass(settings.getApiPass());
        dto.setTimeout(settings.getTimeout());

        return dto;
    }
}
