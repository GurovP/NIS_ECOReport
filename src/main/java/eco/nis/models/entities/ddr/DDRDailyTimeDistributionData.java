package eco.nis.models.entities.ddr;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ddrdailytimedistributiondata")
public class DDRDailyTimeDistributionData {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;

        @OneToMany(mappedBy = "parentDdrDailyTimeDistributionData", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<DDRDailyTimeDistributionRecord> timeDistributionRecords = new ArrayList<>();

        public DDRDailyTimeDistributionData() {
        }

        public DDRDailyTimeDistributionData(DDRDailyTimeDistributionData other) {
                if (other.getTimeDistributionRecords() != null) {
                        this.timeDistributionRecords = other.getTimeDistributionRecords().stream().map(o -> new DDRDailyTimeDistributionRecord(o, this)).collect(Collectors.toList());
                }
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public List<DDRDailyTimeDistributionRecord> getTimeDistributionRecords() {
                return timeDistributionRecords;
        }

        public void setTimeDistributionRecords(List<DDRDailyTimeDistributionRecord> timeDistributionRecords) {
                this.timeDistributionRecords = timeDistributionRecords;
        }
}
