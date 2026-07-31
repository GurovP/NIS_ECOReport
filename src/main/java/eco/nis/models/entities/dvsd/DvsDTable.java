package eco.nis.models.entities.dvsd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "dvsdtable")
public class DvsDTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String tableUid;
    private Long lastUpdated;
    private Long startDate;
    private String wellNameLegal;
    private String wellField;
    private String wellCluster;
    private String wellCustomer;
    private String rigName;
    private String projectName;
    private int projectPhase;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", columnDefinition = "jsonb")
    private String content;

    public DvsDTable() {
        tableUid = UUID.randomUUID().toString();
    }

    public DvsDTable(DvsDTable other, Map<String, String> uidMap) {
        tableUid = UUID.randomUUID().toString();

        fillUidMap(other, uidMap);

        lastUpdated = other.getLastUpdated();
        startDate = other.getStartDate();
        wellNameLegal = other.getWellNameLegal();
        wellField = other.getWellField();
        wellCluster = other.getWellCluster();
        wellCustomer = other.getWellCustomer();
        rigName = other.getRigName();
        projectName = other.getProjectName();
        projectPhase = other.getProjectPhase();
        content = other.getContent();
    }

    private void fillUidMap(DvsDTable other, Map<String, String> uidMap) {

        try {

            if (uidMap != null)
                uidMap.put(other.getTableUid(), tableUid);
            else
                tableUid = other.getTableUid();

        } catch (UnsupportedOperationException ignored) {

            if (uidMap.containsKey(other.getTableUid()))
                tableUid = uidMap.get(other.getTableUid());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableUid() {
        return tableUid;
    }

    public void setTableUid(String tableUid) {
        this.tableUid = tableUid;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

