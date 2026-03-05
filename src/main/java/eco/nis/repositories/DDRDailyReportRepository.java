package eco.nis.repositories;

import eco.nis.models.entities.ddr.DDRDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DDRDailyReportRepository extends JpaRepository<DDRDailyReport, Long> {

    DDRDailyReport findByReportUid(String reportUid);

    @Query(value = "select r.reportUid from DDRDailyReport r")
    List<String> findAllReportUids();

    void deleteByReportUid(String reportUid);
}
