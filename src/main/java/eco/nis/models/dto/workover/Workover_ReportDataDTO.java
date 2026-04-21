package eco.nis.models.dto.workover;

import eco.nis.models.dto.ddr.DDR_TimeDistributionDataItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workover_ReportDataDTO {

    private String reportUid;
    List<Workover_ReportDataItemDTO> reportDatum;
}
