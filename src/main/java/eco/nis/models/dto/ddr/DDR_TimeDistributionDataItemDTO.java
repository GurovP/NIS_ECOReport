package eco.nis.models.dto.ddr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DDR_TimeDistributionDataItemDTO {

    private String timeDistributionUid;
    private long timestamp;
    private String name;
    private String content;
}
