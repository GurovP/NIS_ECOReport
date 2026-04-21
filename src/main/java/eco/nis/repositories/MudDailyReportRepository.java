package eco.nis.repositories;

import eco.nis.models.entities.mud.MudDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MudDailyReportRepository extends JpaRepository<MudDailyReport, Long> {

    MudDailyReport findByReportUid(String reportUid);

    @Query(value = "select r.reportUid from MudDailyReport r")
    List<String> findAllReportUids();

    void deleteByReportUid(String reportUid);
}
