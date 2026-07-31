package eco.nis.models.dvsd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DvsD_DvsDTableDataDTO {

    private String tableUid;
    private List<DvsD_DvsDTableDataItemDTO> tableDatum;
}
