package eco.nis.models.dto.mlu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MLU_ReportDataItemDTO {

    private int dataType;
    private String content;

    @Override
    public int hashCode() {
        return Objects.hash(dataType, content);
    }
}
