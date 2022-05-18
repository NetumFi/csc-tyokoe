package fi.netum.csc.web.rest;

import fi.netum.csc.repository.AgeRepository;
import fi.netum.csc.service.AgeService;
import fi.netum.csc.service.dto.AgeDTO;
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
 * REST controller for managing {@link fi.netum.csc.domain.Age}.
 */
@RestController
@RequestMapping("/api")
public class AgeResource {

    private final Logger log = LoggerFactory.getLogger(AgeResource.class);

    private static final String ENTITY_NAME = "age";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgeService ageService;

    private final AgeRepository ageRepository;

    public AgeResource(AgeService ageService, AgeRepository ageRepository) {
        this.ageService = ageService;
        this.ageRepository = ageRepository;
    }

    /**
     * {@code POST  /ages} : Create a new age.
     *
     * @param ageDTO the ageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ageDTO, or with status {@code 400 (Bad Request)} if the age has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ages")
    public ResponseEntity<AgeDTO> createAge(@RequestBody AgeDTO ageDTO) throws URISyntaxException {
        log.debug("REST request to save Age : {}", ageDTO);
        if (ageDTO.getId() != null) {
            throw new BadRequestAlertException("A new age cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgeDTO result = ageService.save(ageDTO);
        return ResponseEntity
            .created(new URI("/api/ages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ages/:id} : Updates an existing age.
     *
     * @param id the id of the ageDTO to save.
     * @param ageDTO the ageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ageDTO,
     * or with status {@code 400 (Bad Request)} if the ageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ages/{id}")
    public ResponseEntity<AgeDTO> updateAge(@PathVariable(value = "id", required = false) final Long id, @RequestBody AgeDTO ageDTO)
        throws URISyntaxException {
        log.debug("REST request to update Age : {}, {}", id, ageDTO);
        if (ageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgeDTO result = ageService.update(ageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ages/:id} : Partial updates given fields of an existing age, field will ignore if it is null
     *
     * @param id the id of the ageDTO to save.
     * @param ageDTO the ageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ageDTO,
     * or with status {@code 400 (Bad Request)} if the ageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgeDTO> partialUpdateAge(@PathVariable(value = "id", required = false) final Long id, @RequestBody AgeDTO ageDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Age partially : {}, {}", id, ageDTO);
        if (ageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgeDTO> result = ageService.partialUpdate(ageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ages} : get all the ages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ages in body.
     */
    @GetMapping("/ages")
    public ResponseEntity<List<AgeDTO>> getAllAges(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ages");
        Page<AgeDTO> page = ageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ages/:id} : get the "id" age.
     *
     * @param id the id of the ageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ages/{id}")
    public ResponseEntity<AgeDTO> getAge(@PathVariable Long id) {
        log.debug("REST request to get Age : {}", id);
        Optional<AgeDTO> ageDTO = ageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ageDTO);
    }

    /**
     * {@code DELETE  /ages/:id} : delete the "id" age.
     *
     * @param id the id of the ageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ages/{id}")
    public ResponseEntity<Void> deleteAge(@PathVariable Long id) {
        log.debug("REST request to delete Age : {}", id);
        ageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
