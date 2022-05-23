package fi.netum.csc.repository;

import fi.netum.csc.domain.Note;
import java.util.List;
import java.util.Optional;

import fi.netum.csc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Note entity.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select note from Note note where note.user.login = ?#{principal.username}")
    List<Note> findByUserIsCurrentUser();

    default Optional<Note> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Note> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Note> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct note from Note note left join fetch note.user",
        countQuery = "select count(distinct note) from Note note"
    )
    Page<Note> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct note from Note note left join fetch note.user")
    List<Note> findAllWithToOneRelationships();

    @Query("select note from Note note left join fetch note.user where note.id =:id")
    Optional<Note> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        value = "select distinct note from Note note where note.user = :user ",
        countQuery = "select count(distinct note) from Note note where note.user = :user"
    )
    Page<Note> findAllByUser(@Param("user") User user, Pageable pageable);
    @Query(
        value = "select distinct note from Note note left join fetch note.user where note.user = :user ",
        countQuery = "select count(distinct note) from Note note where note.user = :user"
    )
    Page<Note> findAllByUserWithEagerRelationships(@Param("user") User user, Pageable pageable);
}
