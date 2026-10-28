package eco.nis.models.dto.ddr;

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
public class DDR_ReportDataDTO {

    private String reportUid;
    List<DDR_ReportDataItemDTO> reportDatum;
    List<DDR_TimeDistributionDataItemDTO> timeDistributionDatum;

    @Override
    public int hashCode() {
        List<Object> objects = new ArrayList<>();
        objects.add(reportUid);
        objects.addAll(reportDatum.stream().sorted(Comparator.comparingInt(DDR_ReportDataItemDTO::getDataType)).toList());
        objects.addAll(timeDistributionDatum.stream().sorted(Comparator.comparingLong(DDR_TimeDistributionDataItemDTO::getTimestamp)).toList());
        return Objects.hash(objects);
    }
}
