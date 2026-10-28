package eco.nis.models.entities.workover;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "workoverdailyreport")
public class WorkoverDailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String reportUid;
    private Long reportDate;
    private String reportNumber;
    private String documentCode;
    private String comment;
    private boolean approvedDSV;
    private Long lastApproveDSVDate;
    private String lastApproveDSVUser;
    private boolean approvedRTOC;
    private Long lastApproveRTOCDate;
    private String lastApproveRTOCUser;
    private String wellNumGovt;
    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String crewName;
    private String projectName;
    private int projectPhase;
    private Long workoverStartDate;
    private Long workoverEndDate;

    private long reportDataHashCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="report")
    private List<WorkoverDailyReportData> reportDatum = new ArrayList<>();


    public WorkoverDailyReport() {
        reportUid = UUID.randomUUID().toString();
    }

    public WorkoverDailyReport(WorkoverDailyReport other, Map<String, String> uidMap) {
        reportUid = UUID.randomUUID().toString();

        fillUidMap(other, uidMap);

        reportUid = other.getReportUid();
        reportDate = other.getReportDate();
        reportNumber = other.getReportNumber();
        documentCode = other.getDocumentCode();
        comment = other.getComment();
        approvedDSV = other.isApprovedDSV();
        lastApproveDSVDate = other.getLastApproveDSVDate();
        lastApproveDSVUser = other.getLastApproveDSVUser();
        approvedRTOC = other.isApprovedRTOC();
        lastApproveRTOCDate = other.getLastApproveRTOCDate();
        lastApproveRTOCUser = other.getLastApproveRTOCUser();
        wellNumGovt = other.getWellNumGovt();
        wellNameLegal = other.getWellNameLegal();
        wellField = other.getWellField();
        wellCluster = other.getWellCluster();
        wellCustomer = other.getWellCustomer();
        rigName = other.getRigName();
        crewName = other.getCrewName();
        projectName = other.getProjectName();
        projectPhase = other.getProjectPhase();
        workoverStartDate = other.getWorkoverStartDate();
        workoverEndDate = other.getWorkoverEndDate();

        reportDataHashCode = other.getReportDataHashCode();

        copyReportData(other);
    }

    private void fillUidMap(WorkoverDailyReport other, Map<String, String> uidMap) {

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

    public void copyReportData(WorkoverDailyReport other) {

        this.reportDatum = new ArrayList<>();
        if (other.getReportDatum() != null)
            for (WorkoverDailyReportData otherData: other.getReportDatum())
                this.reportDatum.add(new WorkoverDailyReportData(otherData, this));
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

    public Long getReportDate() {
        return reportDate;
    }

    public void setReportDate(Long reportDate) {
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

    public boolean isApprovedDSV() {
        return approvedDSV;
    }

    public void setApprovedDSV(boolean approvedDSV) {
        this.approvedDSV = approvedDSV;
    }

    public Long getLastApproveDSVDate() {
        return lastApproveDSVDate;
    }

    public void setLastApproveDSVDate(Long lastApproveDSVDate) {
        this.lastApproveDSVDate = lastApproveDSVDate;
    }

    public String getLastApproveDSVUser() {
        return lastApproveDSVUser;
    }

    public void setLastApproveDSVUser(String lastApproveDSVUser) {
        this.lastApproveDSVUser = lastApproveDSVUser;
    }

    public boolean isApprovedRTOC() {
        return approvedRTOC;
    }

    public void setApprovedRTOC(boolean approvedRTOC) {
        this.approvedRTOC = approvedRTOC;
    }

    public Long getLastApproveRTOCDate() {
        return lastApproveRTOCDate;
    }

    public void setLastApproveRTOCDate(Long lastApproveRTOCDate) {
        this.lastApproveRTOCDate = lastApproveRTOCDate;
    }

    public String getLastApproveRTOCUser() {
        return lastApproveRTOCUser;
    }

    public void setLastApproveRTOCUser(String lastApproveRTOCUser) {
        this.lastApproveRTOCUser = lastApproveRTOCUser;
    }

    public String getWellNumGovt() {
        return wellNumGovt;
    }

    public void setWellNumGovt(String wellNumGovt) {
        this.wellNumGovt = wellNumGovt;
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

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
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

    public Long getWorkoverStartDate() {
        return workoverStartDate;
    }

    public void setWorkoverStartDate(Long workoverStartDate) {
        this.workoverStartDate = workoverStartDate;
    }

    public Long getWorkoverEndDate() {
        return workoverEndDate;
    }

    public void setWorkoverEndDate(Long workoverEndDate) {
        this.workoverEndDate = workoverEndDate;
    }

    public List<WorkoverDailyReportData> getReportDatum() {
        return reportDatum;
    }

    public void setReportDatum(List<WorkoverDailyReportData> reportDatum) {
        this.reportDatum = reportDatum;
    }

    public long getReportDataHashCode() {
        return reportDataHashCode;
    }

    public void setReportDataHashCode(long reportDataHashCode) {
        this.reportDataHashCode = reportDataHashCode;
    }
}

