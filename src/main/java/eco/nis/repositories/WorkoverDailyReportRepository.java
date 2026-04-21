package eco.nis.repositories;

import eco.nis.models.entities.workover.WorkoverDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoverDailyReportRepository extends JpaRepository<WorkoverDailyReport, Long> {

    WorkoverDailyReport findByReportUid(String reportUid);

    @Query(value = "select r.reportUid from WorkoverDailyReport r")
    List<String> findAllReportUids();

    void deleteByReportUid(String reportUid);
}
