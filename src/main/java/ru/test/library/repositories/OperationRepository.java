package ru.test.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.test.library.models.Operation;
import ru.test.library.models.Visitor;

import java.time.Instant;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Transactional
    @Modifying
    @Query(value="update edition set books_available = books_available-1 where id=?1", nativeQuery=true)
    void minusBooksAvailable(long editionId);

    @Transactional
    @Modifying
    @Query(value="update edition set books_available = books_available+1 where id=?1", nativeQuery=true)
    void plusBooksAvailable(long editionId);

    @Query(value="select * from operation where edition_id=?1 and visitor_id=?2 and status=?3", nativeQuery=true)
    List<Operation> getByVisitorAndEditionId(long editionId, long visitorId, int status);

    @Query(value = "select * from operation where visitor_id=?1", nativeQuery = true)
    List<Operation> findAllByVisitorId(long visitorId);

    @Query(value = "select visitor_id from operation where edition_id=?1 and status=0", nativeQuery = true)
    List<Visitor> getKeepersByBookId(long bookId);

    @Query(value = "select * from operation where time >?1 and time < ?2", nativeQuery = true)
    List<Operation> getStatistics(Instant start, Instant finish);
}
