package eco.nis.repositories;

import eco.nis.models.entities.dvsd.DvsDTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DvsDTableRepository extends JpaRepository<DvsDTable, Long> {

    DvsDTable findByTableUid(String tableUid);

    @Query(value = "select t.tableUid from DvsDTable t")
    List<String> findAllTableUids();

    void deleteByTableUid(String tableUid);
}
