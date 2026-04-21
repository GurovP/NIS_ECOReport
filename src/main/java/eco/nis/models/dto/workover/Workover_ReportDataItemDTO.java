package eco.nis.models.dto.workover;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workover_ReportDataItemDTO {

    private int dataType;
    private String content;
}
