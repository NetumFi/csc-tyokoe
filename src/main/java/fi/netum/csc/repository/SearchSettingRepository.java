package fi.netum.csc.repository;

import fi.netum.csc.domain.SearchSetting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SearchSetting entity.
 */
@Repository
public interface SearchSettingRepository extends JpaRepository<SearchSetting, Long> {
    default Optional<SearchSetting> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SearchSetting> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SearchSetting> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct searchSetting from SearchSetting searchSetting left join fetch searchSetting.user",
        countQuery = "select count(distinct searchSetting) from SearchSetting searchSetting"
    )
    Page<SearchSetting> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct searchSetting from SearchSetting searchSetting left join fetch searchSetting.user")
    List<SearchSetting> findAllWithToOneRelationships();

    @Query("select searchSetting from SearchSetting searchSetting left join fetch searchSetting.user where searchSetting.id =:id")
    Optional<SearchSetting> findOneWithToOneRelationships(@Param("id") Long id);
}
