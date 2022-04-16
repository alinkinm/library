package ru.test.library.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.test.library.models.Edition;


public interface EditionRepository extends JpaRepository<Edition, Long> {
    Page<Edition> findAll(Pageable pageable);

    @Query(value = "select books_available from edition where id=?1", nativeQuery = true)
    int checkAvailability(long editionId);
}
