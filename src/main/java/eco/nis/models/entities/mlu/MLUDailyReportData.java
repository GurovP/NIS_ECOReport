package eco.nis.models.entities.mlu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "mludailyreportdata")
public class MLUDailyReportData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int dataType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @JsonIgnore
    private MLUDailyReport report;

    public MLUDailyReportData() {
    }

    public MLUDailyReportData(MLUDailyReportData otherData, MLUDailyReport report) {
        this.dataType = otherData.getDataType();
        this.content = otherData.getContent();
        this.report = report;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MLUDailyReport getReport() {
        return report;
    }

    public void setReport(MLUDailyReport report) {
        this.report = report;
    }

}

