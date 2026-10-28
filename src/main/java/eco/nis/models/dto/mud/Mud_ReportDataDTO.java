package eco.nis.models.dto.mud;

import eco.nis.models.dto.mlu.MLU_ReportDataItemDTO;
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
public class Mud_ReportDataDTO {

    private String reportUid;
    List<Mud_ReportDataItemDTO> reportDatum;

    @Override
    public int hashCode() {
        List<Object> objects = new ArrayList<>();
        objects.add(reportUid);
        objects.addAll(reportDatum.stream().sorted(Comparator.comparingInt(Mud_ReportDataItemDTO::getDataType)).toList());
        return Objects.hash(objects);
    }
}
