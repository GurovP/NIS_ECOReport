package eco.nis.models.dto.ddr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DDR_TimeDistributionDataItemDTO {

    private String timeDistributionUid;
    private long timestamp;
    private String name;
    private String content;

    @Override
    public int hashCode() {
        return Objects.hash(timeDistributionUid, timestamp, name, content);
    }
}
