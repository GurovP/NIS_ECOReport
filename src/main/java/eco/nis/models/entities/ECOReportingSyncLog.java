package eco.nis.models.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ecoreportingsynclog")
public class ECOReportingSyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String timeSync = new Date().toString();
    private String reportType;
    private int readFromApiCount;
    private int addedCount;
    private int updatedCount;
    private int deletedCount;

    public ECOReportingSyncLog() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimeSync() {
        return timeSync;
    }

    public void setTimeSync(String timeSync) {
        this.timeSync = timeSync;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getReadFromApiCount() {
        return readFromApiCount;
    }

    public void setReadFromApiCount(int readFromApiCount) {
        this.readFromApiCount = readFromApiCount;
    }

    public int getAddedCount() {
        return addedCount;
    }

    public void setAddedCount(int addedCount) {
        this.addedCount = addedCount;
    }

    public int getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(int updatedCount) {
        this.updatedCount = updatedCount;
    }

    public int getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(int deletedCount) {
        this.deletedCount = deletedCount;
    }
}
