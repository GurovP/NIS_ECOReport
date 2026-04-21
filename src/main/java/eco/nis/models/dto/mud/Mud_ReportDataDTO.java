package eco.nis.models.dto.mud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mud_ReportDataDTO {

    private String reportUid;
    List<Mud_ReportDataItemDTO> reportDatum;
}
