package fi.netum.csc.repository;

import fi.netum.csc.domain.UserResultKeyword;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserResultKeyword entity.
 */
@Repository
public interface UserResultKeywordRepository extends JpaRepository<UserResultKeyword, Long> {
    default Optional<UserResultKeyword> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UserResultKeyword> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UserResultKeyword> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct userResultKeyword from UserResultKeyword userResultKeyword left join fetch userResultKeyword.user",
        countQuery = "select count(distinct userResultKeyword) from UserResultKeyword userResultKeyword"
    )
    Page<UserResultKeyword> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct userResultKeyword from UserResultKeyword userResultKeyword left join fetch userResultKeyword.user")
    List<UserResultKeyword> findAllWithToOneRelationships();

    @Query(
        "select userResultKeyword from UserResultKeyword userResultKeyword left join fetch userResultKeyword.user where userResultKeyword.id =:id"
    )
    Optional<UserResultKeyword> findOneWithToOneRelationships(@Param("id") Long id);
}
