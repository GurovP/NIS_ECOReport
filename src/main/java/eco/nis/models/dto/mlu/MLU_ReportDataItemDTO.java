package eco.nis.models.dto.mlu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MLU_ReportDataItemDTO {

    private int dataType;
    private String content;
}
