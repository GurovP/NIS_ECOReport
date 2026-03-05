package eco.nis.repositories;

import eco.nis.models.entities.ECOReportingSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ECOReportingSyncLogRepository extends JpaRepository<ECOReportingSyncLog, Long> {
}
