package fi.netum.csc.web.rest;

import fi.netum.csc.repository.EducationLevelCodeSetRepository;
import fi.netum.csc.service.EducationLevelCodeSetService;
import fi.netum.csc.service.dto.EducationLevelCodeSetDTO;
import fi.netum.csc.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fi.netum.csc.domain.EducationLevelCodeSet}.
 */
@RestController
@RequestMapping("/api")
public class EducationLevelCodeSetResource {

    private final Logger log = LoggerFactory.getLogger(EducationLevelCodeSetResource.class);

    private static final String ENTITY_NAME = "educationLevelCodeSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationLevelCodeSetService educationLevelCodeSetService;

    private final EducationLevelCodeSetRepository educationLevelCodeSetRepository;

    public EducationLevelCodeSetResource(
        EducationLevelCodeSetService educationLevelCodeSetService,
        EducationLevelCodeSetRepository educationLevelCodeSetRepository
    ) {
        this.educationLevelCodeSetService = educationLevelCodeSetService;
        this.educationLevelCodeSetRepository = educationLevelCodeSetRepository;
    }

    /**
     * {@code POST  /education-level-code-sets} : Create a new educationLevelCodeSet.
     *
     * @param educationLevelCodeSetDTO the educationLevelCodeSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationLevelCodeSetDTO, or with status {@code 400 (Bad Request)} if the educationLevelCodeSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/education-level-code-sets")
    public ResponseEntity<EducationLevelCodeSetDTO> createEducationLevelCodeSet(
        @RequestBody EducationLevelCodeSetDTO educationLevelCodeSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EducationLevelCodeSet : {}", educationLevelCodeSetDTO);
        if (educationLevelCodeSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new educationLevelCodeSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationLevelCodeSetDTO result = educationLevelCodeSetService.save(educationLevelCodeSetDTO);
        return ResponseEntity
            .created(new URI("/api/education-level-code-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /education-level-code-sets/:id} : Updates an existing educationLevelCodeSet.
     *
     * @param id the id of the educationLevelCodeSetDTO to save.
     * @param educationLevelCodeSetDTO the educationLevelCodeSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationLevelCodeSetDTO,
     * or with status {@code 400 (Bad Request)} if the educationLevelCodeSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationLevelCodeSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/education-level-code-sets/{id}")
    public ResponseEntity<EducationLevelCodeSetDTO> updateEducationLevelCodeSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EducationLevelCodeSetDTO educationLevelCodeSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EducationLevelCodeSet : {}, {}", id, educationLevelCodeSetDTO);
        if (educationLevelCodeSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationLevelCodeSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationLevelCodeSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EducationLevelCodeSetDTO result = educationLevelCodeSetService.update(educationLevelCodeSetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationLevelCodeSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /education-level-code-sets/:id} : Partial updates given fields of an existing educationLevelCodeSet, field will ignore if it is null
     *
     * @param id the id of the educationLevelCodeSetDTO to save.
     * @param educationLevelCodeSetDTO the educationLevelCodeSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationLevelCodeSetDTO,
     * or with status {@code 400 (Bad Request)} if the educationLevelCodeSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the educationLevelCodeSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the educationLevelCodeSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/education-level-code-sets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EducationLevelCodeSetDTO> partialUpdateEducationLevelCodeSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EducationLevelCodeSetDTO educationLevelCodeSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EducationLevelCodeSet partially : {}, {}", id, educationLevelCodeSetDTO);
        if (educationLevelCodeSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationLevelCodeSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationLevelCodeSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EducationLevelCodeSetDTO> result = educationLevelCodeSetService.partialUpdate(educationLevelCodeSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationLevelCodeSetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /education-level-code-sets} : get all the educationLevelCodeSets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationLevelCodeSets in body.
     */
    @GetMapping("/education-level-code-sets")
    public ResponseEntity<List<EducationLevelCodeSetDTO>> getAllEducationLevelCodeSets(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EducationLevelCodeSets");
        Page<EducationLevelCodeSetDTO> page = educationLevelCodeSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /education-level-code-sets/:id} : get the "id" educationLevelCodeSet.
     *
     * @param id the id of the educationLevelCodeSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationLevelCodeSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/education-level-code-sets/{id}")
    public ResponseEntity<EducationLevelCodeSetDTO> getEducationLevelCodeSet(@PathVariable Long id) {
        log.debug("REST request to get EducationLevelCodeSet : {}", id);
        Optional<EducationLevelCodeSetDTO> educationLevelCodeSetDTO = educationLevelCodeSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationLevelCodeSetDTO);
    }

    /**
     * {@code DELETE  /education-level-code-sets/:id} : delete the "id" educationLevelCodeSet.
     *
     * @param id the id of the educationLevelCodeSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/education-level-code-sets/{id}")
    public ResponseEntity<Void> deleteEducationLevelCodeSet(@PathVariable Long id) {
        log.debug("REST request to delete EducationLevelCodeSet : {}", id);
        educationLevelCodeSetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
