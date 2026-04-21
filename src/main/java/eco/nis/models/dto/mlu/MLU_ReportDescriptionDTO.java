package eco.nis.models.dto.mlu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MLU_ReportDescriptionDTO {

    private String reportUid;
    private Long reportDate;
    private String reportNumber;
    private String documentCode;
    private String comment;
    private boolean approved;
    private Long lastApproveDate;
    private String lastApproveUser;
    private boolean fullDay;
    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String projectName;
    private int projectPhase;
}
