package eco.nis.repositories;

import eco.nis.models.entities.mlu.MLUDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MLUDailyReportRepository extends JpaRepository<MLUDailyReport, Long> {

    MLUDailyReport findByReportUid(String reportUid);

    @Query(value = "select r.reportUid from MLUDailyReport r")
    List<String> findAllReportUids();

    void deleteByReportUid(String reportUid);
}
