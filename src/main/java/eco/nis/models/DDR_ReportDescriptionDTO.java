package eco.nis.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DDR_ReportDescriptionDTO {

    private String reportUid;
    private Long reportDate;
    private String reportNumber;
    private String documentCode;
    private String comment;
    private boolean approved;
    private Long lastApproveDate;
    private String lastApproveUser;
    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String projectName;
    private int projectPhase;
}
