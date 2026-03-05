package eco.nis.models.entities.ddr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "ddrdailytimedistributionrecord")
public class DDRDailyTimeDistributionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String timeDistributionUid;

    private long timestamp;

    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

    @ManyToOne
    @JoinColumn(name = "ddrdailytimedistributiondata_id")
    @JsonIgnore
    private DDRDailyTimeDistributionData parentDdrDailyTimeDistributionData;

    public DDRDailyTimeDistributionRecord() {
        timeDistributionUid = UUID.randomUUID().toString();
    }

    public DDRDailyTimeDistributionRecord(String timeDistributionUid, long timestamp, String name, String content, DDRDailyTimeDistributionData ddrDailyTimeDistributionData) {
        this.timeDistributionUid = timeDistributionUid;
        this.timestamp = timestamp;
        this.name = name;
        this.content = content;
        this.parentDdrDailyTimeDistributionData = ddrDailyTimeDistributionData;
    }

    public DDRDailyTimeDistributionRecord(DDRDailyTimeDistributionRecord other, DDRDailyTimeDistributionData parentDdrDailyTimeDistributionData) {

        setTimeDistributionUid(other.getTimeDistributionUid());
        setTimestamp(other.getTimestamp());
        setName(other.getName());
        setContent(other.getContent());

        this.parentDdrDailyTimeDistributionData = parentDdrDailyTimeDistributionData;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DDRDailyTimeDistributionData getParentDdrDailyTimeDistributionData() {
        return parentDdrDailyTimeDistributionData;
    }

    public void setParentDdrDailyTimeDistributionData(DDRDailyTimeDistributionData parentDdrDailyTimeDistributionData) {
        this.parentDdrDailyTimeDistributionData = parentDdrDailyTimeDistributionData;
    }

    public String getTimeDistributionUid() {
        return timeDistributionUid;
    }

    public void setTimeDistributionUid(String timeDistributionUid) {
        this.timeDistributionUid = timeDistributionUid;
    }
}
