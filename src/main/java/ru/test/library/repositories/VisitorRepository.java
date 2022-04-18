package ru.test.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.test.library.models.Visitor;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query(value = "select * from operation where edition_id=?1 and status=0", nativeQuery = true)
    List<Visitor> getKeepersByBookId(long bookId);
}
