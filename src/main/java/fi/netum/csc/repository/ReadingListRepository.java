package fi.netum.csc.repository;

import fi.netum.csc.domain.ReadingList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReadingList entity.
 */
@Repository
public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {
    @Query("select readingList from ReadingList readingList where readingList.user.login = ?#{principal.username}")
    List<ReadingList> findByUserIsCurrentUser();

    default Optional<ReadingList> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ReadingList> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ReadingList> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct readingList from ReadingList readingList left join fetch readingList.user",
        countQuery = "select count(distinct readingList) from ReadingList readingList"
    )
    Page<ReadingList> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct readingList from ReadingList readingList left join fetch readingList.user")
    List<ReadingList> findAllWithToOneRelationships();

    @Query("select readingList from ReadingList readingList left join fetch readingList.user where readingList.id =:id")
    Optional<ReadingList> findOneWithToOneRelationships(@Param("id") Long id);
}
