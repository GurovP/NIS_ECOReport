package eco.nis.models.dto.workover;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workover_ReportDescriptionDTO {

    private String reportUid;
    private Long reportDate;
    private String reportNumber;
    private String documentCode;
    private String comment;
    private boolean approvedDSV;
    private Long lastApproveDSVDate;
    private String lastApproveDSVUser;
    private boolean approvedRTOC;
    private Long lastApproveRTOCDate;
    private String lastApproveRTOCUser;
    private String wellNumGovt;
    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String crewName;
    private String projectName;
    private int projectPhase;
    private Long workoverStartDate;
    private Long workoverEndDate;
}
