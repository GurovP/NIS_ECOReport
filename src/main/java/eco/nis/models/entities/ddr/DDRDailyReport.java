package eco.nis.models.entities.ddr;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "ddrdailyreport")
public class DDRDailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String reportUid;

    private long reportDate;

    private String reportNumber;

    private String documentCode;

    private String comment;

    private boolean approved;

    private Long lastApproveDate;

    private String lastApproveUser;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="report")
    private List<DDRDailyReportData> reportDatum = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ddrdailytimedistributiondata_id", referencedColumnName = "id")
    private DDRDailyTimeDistributionData ddrDailyTimeDistributionData;

    public DDRDailyReport() {
        reportUid = UUID.randomUUID().toString();
    }

    public DDRDailyReport(DDRDailyReport other, Map<String, String> uidMap) {
        reportUid = UUID.randomUUID().toString();

        fillUidMap(other, uidMap);

        reportDate = other.getReportDate();
        reportNumber = other.getReportNumber();
        documentCode = other.getDocumentCode();
        comment = other.getComment();
        approved = other.isApproved();
        lastApproveDate = other.getLastApproveDate();
        lastApproveUser = other.getLastApproveUser();

        copyReportData(other);
    }

    private void fillUidMap(DDRDailyReport other, Map<String, String> uidMap) {

        try {

            if (uidMap != null)
                uidMap.put(other.getReportUid(), reportUid);
            else
                reportUid = other.getReportUid();

        } catch (UnsupportedOperationException ignored) {

            if (uidMap.containsKey(other.getReportUid()))
                reportUid = uidMap.get(other.getReportUid());
        }
    }

    public void copyReportData(DDRDailyReport other) {

        this.reportDatum = new ArrayList<>();
        if (other.getReportDatum() != null)
            for (DDRDailyReportData otherData: other.getReportDatum())
                this.reportDatum.add(new DDRDailyReportData(otherData, this));

        if (other.getDdrDailyTimeDistributionData() != null)
            this.ddrDailyTimeDistributionData = new DDRDailyTimeDistributionData(other.getDdrDailyTimeDistributionData());
        else
            this.ddrDailyTimeDistributionData = new DDRDailyTimeDistributionData();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReportUid() {
        return reportUid;
    }

    public void setReportUid(String reportUid) {
        this.reportUid = reportUid;
    }

    public long getReportDate() {
        return reportDate;
    }

    public void setReportDate(long reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Long getLastApproveDate() {
        return lastApproveDate;
    }

    public void setLastApproveDate(Long lastApproveDate) {
        this.lastApproveDate = lastApproveDate;
    }

    public String getLastApproveUser() {
        return lastApproveUser;
    }

    public void setLastApproveUser(String lastApproveUser) {
        this.lastApproveUser = lastApproveUser;
    }

    public List<DDRDailyReportData> getReportDatum() {
        return reportDatum;
    }

    public void setReportDatum(List<DDRDailyReportData> reportDatum) {
        this.reportDatum = reportDatum;
    }

    public DDRDailyTimeDistributionData getDdrDailyTimeDistributionData() {
        return ddrDailyTimeDistributionData;
    }

    public void setDdrDailyTimeDistributionData(DDRDailyTimeDistributionData ddrDailyTimeDistributionData) {
        this.ddrDailyTimeDistributionData = ddrDailyTimeDistributionData;
    }
}

