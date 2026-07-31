package eco.nis.models.dvsd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DvsD_DvsDTableDescriptionDTO {

    private String tableUid;
    private Long lastUpdated;
    private Long startDate;
    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String projectName;
    private int projectPhase;
}
