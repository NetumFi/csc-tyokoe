package fi.netum.csc.repository;

import fi.netum.csc.domain.AgeCodeSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgeCodeSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgeCodeSetRepository extends JpaRepository<AgeCodeSet, Long> {}
