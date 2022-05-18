package fi.netum.csc.repository;

import fi.netum.csc.domain.Age;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Age entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgeRepository extends JpaRepository<Age, Long> {}
