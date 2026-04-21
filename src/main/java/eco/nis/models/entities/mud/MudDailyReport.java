package eco.nis.models.entities.mud;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "muddailyreport")
public class MudDailyReport {

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

    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String projectName;
    private int projectPhase;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="report")
    private List<MudDailyReportData> reportDatum = new ArrayList<>();

    public MudDailyReport() {
        reportUid = UUID.randomUUID().toString();
    }

    public MudDailyReport(MudDailyReport other, Map<String, String> uidMap) {
        reportUid = UUID.randomUUID().toString();

        fillUidMap(other, uidMap);

        reportDate = other.getReportDate();
        reportNumber = other.getReportNumber();
        documentCode = other.getDocumentCode();
        comment = other.getComment();
        approved = other.isApproved();
        lastApproveDate = other.getLastApproveDate();
        lastApproveUser = other.getLastApproveUser();

        wellNameLegal = other.getWellNameLegal();
        wellField = other.getWellField();
        wellCluster = other.getWellCluster();
        wellCustomer = other.getWellCustomer();
        rigName = other.getRigName();
        projectName = other.getProjectName();
        projectPhase = other.getProjectPhase();

        copyReportData(other);
    }

    private void fillUidMap(MudDailyReport other, Map<String, String> uidMap) {

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

    public void copyReportData(MudDailyReport other) {

        this.reportDatum = new ArrayList<>();
        if (other.getReportDatum() != null)
            for (MudDailyReportData otherData: other.getReportDatum())
                this.reportDatum.add(new MudDailyReportData(otherData, this));
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

    public String getWellNameLegal() {
        return wellNameLegal;
    }

    public void setWellNameLegal(String wellNameLegal) {
        this.wellNameLegal = wellNameLegal;
    }

    public String getWellField() {
        return wellField;
    }

    public void setWellField(String wellField) {
        this.wellField = wellField;
    }

    public String getWellCluster() {
        return wellCluster;
    }

    public void setWellCluster(String wellCluster) {
        this.wellCluster = wellCluster;
    }

    public String getWellCustomer() {
        return wellCustomer;
    }

    public void setWellCustomer(String wellCustomer) {
        this.wellCustomer = wellCustomer;
    }

    public String getRigName() {
        return rigName;
    }

    public void setRigName(String rigName) {
        this.rigName = rigName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(int projectPhase) {
        this.projectPhase = projectPhase;
    }

    public List<MudDailyReportData> getReportDatum() {
        return reportDatum;
    }

    public void setReportDatum(List<MudDailyReportData> reportDatum) {
        this.reportDatum = reportDatum;
    }
}

