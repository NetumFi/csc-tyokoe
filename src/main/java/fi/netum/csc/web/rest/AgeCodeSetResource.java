package fi.netum.csc.web.rest;

import fi.netum.csc.repository.AgeCodeSetRepository;
import fi.netum.csc.service.AgeCodeSetService;
import fi.netum.csc.service.dto.AgeCodeSetDTO;
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
 * REST controller for managing {@link fi.netum.csc.domain.AgeCodeSet}.
 */
@RestController
@RequestMapping("/api")
public class AgeCodeSetResource {

    private final Logger log = LoggerFactory.getLogger(AgeCodeSetResource.class);

    private static final String ENTITY_NAME = "ageCodeSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgeCodeSetService ageCodeSetService;

    private final AgeCodeSetRepository ageCodeSetRepository;

    public AgeCodeSetResource(AgeCodeSetService ageCodeSetService, AgeCodeSetRepository ageCodeSetRepository) {
        this.ageCodeSetService = ageCodeSetService;
        this.ageCodeSetRepository = ageCodeSetRepository;
    }

    /**
     * {@code POST  /age-code-sets} : Create a new ageCodeSet.
     *
     * @param ageCodeSetDTO the ageCodeSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ageCodeSetDTO, or with status {@code 400 (Bad Request)} if the ageCodeSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/age-code-sets")
    public ResponseEntity<AgeCodeSetDTO> createAgeCodeSet(@RequestBody AgeCodeSetDTO ageCodeSetDTO) throws URISyntaxException {
        log.debug("REST request to save AgeCodeSet : {}", ageCodeSetDTO);
        if (ageCodeSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new ageCodeSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgeCodeSetDTO result = ageCodeSetService.save(ageCodeSetDTO);
        return ResponseEntity
            .created(new URI("/api/age-code-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /age-code-sets/:id} : Updates an existing ageCodeSet.
     *
     * @param id the id of the ageCodeSetDTO to save.
     * @param ageCodeSetDTO the ageCodeSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ageCodeSetDTO,
     * or with status {@code 400 (Bad Request)} if the ageCodeSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ageCodeSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/age-code-sets/{id}")
    public ResponseEntity<AgeCodeSetDTO> updateAgeCodeSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AgeCodeSetDTO ageCodeSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AgeCodeSet : {}, {}", id, ageCodeSetDTO);
        if (ageCodeSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageCodeSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageCodeSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgeCodeSetDTO result = ageCodeSetService.update(ageCodeSetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageCodeSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /age-code-sets/:id} : Partial updates given fields of an existing ageCodeSet, field will ignore if it is null
     *
     * @param id the id of the ageCodeSetDTO to save.
     * @param ageCodeSetDTO the ageCodeSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ageCodeSetDTO,
     * or with status {@code 400 (Bad Request)} if the ageCodeSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ageCodeSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ageCodeSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/age-code-sets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgeCodeSetDTO> partialUpdateAgeCodeSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AgeCodeSetDTO ageCodeSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgeCodeSet partially : {}, {}", id, ageCodeSetDTO);
        if (ageCodeSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageCodeSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageCodeSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgeCodeSetDTO> result = ageCodeSetService.partialUpdate(ageCodeSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageCodeSetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /age-code-sets} : get all the ageCodeSets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ageCodeSets in body.
     */
    @GetMapping("/age-code-sets")
    public ResponseEntity<List<AgeCodeSetDTO>> getAllAgeCodeSets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AgeCodeSets");
        Page<AgeCodeSetDTO> page = ageCodeSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /age-code-sets/:id} : get the "id" ageCodeSet.
     *
     * @param id the id of the ageCodeSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ageCodeSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/age-code-sets/{id}")
    public ResponseEntity<AgeCodeSetDTO> getAgeCodeSet(@PathVariable Long id) {
        log.debug("REST request to get AgeCodeSet : {}", id);
        Optional<AgeCodeSetDTO> ageCodeSetDTO = ageCodeSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ageCodeSetDTO);
    }

    /**
     * {@code DELETE  /age-code-sets/:id} : delete the "id" ageCodeSet.
     *
     * @param id the id of the ageCodeSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/age-code-sets/{id}")
    public ResponseEntity<Void> deleteAgeCodeSet(@PathVariable Long id) {
        log.debug("REST request to delete AgeCodeSet : {}", id);
        ageCodeSetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
