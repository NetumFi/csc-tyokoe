package fi.netum.csc.repository;

import fi.netum.csc.domain.EducationLevelCodeSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EducationLevelCodeSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationLevelCodeSetRepository extends JpaRepository<EducationLevelCodeSet, Long> {}
