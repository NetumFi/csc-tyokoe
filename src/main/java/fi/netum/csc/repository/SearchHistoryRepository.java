package fi.netum.csc.repository;

import fi.netum.csc.domain.SearchHistory;
import java.util.List;
import java.util.Optional;

import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.SearchHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SearchHistory entity.
 */
@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    @Query("select searchHistory from SearchHistory searchHistory where searchHistory.user.login = ?#{principal.username}")
    List<SearchHistory> findByUserIsCurrentUser();

    default Optional<SearchHistory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SearchHistory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SearchHistory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct searchHistory from SearchHistory searchHistory left join fetch searchHistory.user",
        countQuery = "select count(distinct searchHistory) from SearchHistory searchHistory"
    )
    Page<SearchHistory> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        value = "select distinct searchHistory from SearchHistory searchHistory left join fetch searchHistory.user where searchHistory.user =:user",
        countQuery = "select count(distinct searchHistory) from SearchHistory searchHistory where searchHistory.user = :user"
    )
    Page<SearchHistory> findAllByUserWithToOneRelationships(@Param("user") Pageable pageable);

    @Query("select distinct searchHistory from SearchHistory searchHistory left join fetch searchHistory.user")
    List<SearchHistory> findAllWithToOneRelationships();

    @Query("select searchHistory from SearchHistory searchHistory left join fetch searchHistory.user where searchHistory.id =:id")
    Optional<SearchHistory> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(value="select searchHistory from SearchHistory searchHistory where searchHistory.user =:user",
        countQuery = "select count(distinct searchHistory) from SearchHistory searchHistory where searchHistory.user =:user")
    Page<SearchHistory> findAllByUser(@Param("user") User user, Pageable pageable);

    @Query(value="select searchHistory from SearchHistory searchHistory left join fetch searchHistory.user where searchHistory.user =:user",
        countQuery = "select count(distinct searchHistory) from SearchHistory searchHistory where searchHistory.user =:user")
    Page<SearchHistory> findAllByUserWithEagerRelationships(@Param("user") User user, Pageable pageable);
}
