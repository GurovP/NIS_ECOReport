package eco.nis.models.dvsd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DvsD_DvsDTableDataItemDTO {

    private int taskOrder;
    private String description;
    private double duration;
    private Double durationFact;
    private double welldepth;
    private String comment;
}
