package eco.nis.models.dto.mlu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MLU_ReportDataDTO {

    private String reportUid;
    List<MLU_ReportDataItemDTO> reportDatum;

    @Override
    public int hashCode() {
        List<Object> objects = new ArrayList<>();
        objects.add(reportUid);
        objects.addAll(reportDatum.stream().sorted(Comparator.comparingInt(MLU_ReportDataItemDTO::getDataType)).toList());
        return Objects.hash(objects);
    }
}
