package eco.nis.models.entities.ddr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
@Table(name = "ddrdailyreportdata")
public class DDRDailyReportData {

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
    private DDRDailyReport report;

    public DDRDailyReportData() {
    }

    public DDRDailyReportData(DDRDailyReportData otherData, DDRDailyReport report) {
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

    public DDRDailyReport getReport() {
        return report;
    }

    public void setReport(DDRDailyReport report) {
        this.report = report;
    }

}

