package eco.nis.models.dto.mlu;

import eco.nis.models.dto.ddr.DDR_ReportDataItemDTO;
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
public class MLU_ReportDataDTO {

    private String reportUid;
    List<MLU_ReportDataItemDTO> reportDatum;
}
