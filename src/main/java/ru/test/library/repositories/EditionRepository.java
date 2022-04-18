package ru.test.library.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.test.library.models.Edition;

import java.util.List;


public interface EditionRepository extends JpaRepository<Edition, Long> {

    @Transactional
    @Modifying
    @Query(value = "update edition set document = to_tsvector(name || ' ' || description) where id=?1", nativeQuery = true)
    void updateTsVector(long id);

    
    Page<Edition> findAll(Pageable pageable);

    @Query(value = "select books_available from edition where id=?1", nativeQuery = true)
    int checkAvailability(long editionId);

    @Query(value = "select *\n" +
            "from edition\n" +
            "where document @@ to_tsquery(?1)", nativeQuery = true)
    List<Edition> customSearch(String line);


}
