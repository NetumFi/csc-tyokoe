package fi.netum.csc.web.rest;

import fi.netum.csc.repository.UserResultKeywordRepository;
import fi.netum.csc.service.UserResultKeywordService;
import fi.netum.csc.service.dto.UserResultKeywordDTO;
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
 * REST controller for managing {@link fi.netum.csc.domain.UserResultKeyword}.
 */
@RestController
@RequestMapping("/api")
public class UserResultKeywordResource {

    private final Logger log = LoggerFactory.getLogger(UserResultKeywordResource.class);

    private static final String ENTITY_NAME = "userResultKeyword";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserResultKeywordService userResultKeywordService;

    private final UserResultKeywordRepository userResultKeywordRepository;

    public UserResultKeywordResource(
        UserResultKeywordService userResultKeywordService,
        UserResultKeywordRepository userResultKeywordRepository
    ) {
        this.userResultKeywordService = userResultKeywordService;
        this.userResultKeywordRepository = userResultKeywordRepository;
    }

    /**
     * {@code POST  /user-result-keywords} : Create a new userResultKeyword.
     *
     * @param userResultKeywordDTO the userResultKeywordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userResultKeywordDTO, or with status {@code 400 (Bad Request)} if the userResultKeyword has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-result-keywords")
    public ResponseEntity<UserResultKeywordDTO> createUserResultKeyword(@RequestBody UserResultKeywordDTO userResultKeywordDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserResultKeyword : {}", userResultKeywordDTO);
        if (userResultKeywordDTO.getId() != null) {
            throw new BadRequestAlertException("A new userResultKeyword cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserResultKeywordDTO result = userResultKeywordService.save(userResultKeywordDTO);
        return ResponseEntity
            .created(new URI("/api/user-result-keywords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-result-keywords/:id} : Updates an existing userResultKeyword.
     *
     * @param id the id of the userResultKeywordDTO to save.
     * @param userResultKeywordDTO the userResultKeywordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userResultKeywordDTO,
     * or with status {@code 400 (Bad Request)} if the userResultKeywordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userResultKeywordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-result-keywords/{id}")
    public ResponseEntity<UserResultKeywordDTO> updateUserResultKeyword(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserResultKeywordDTO userResultKeywordDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserResultKeyword : {}, {}", id, userResultKeywordDTO);
        if (userResultKeywordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userResultKeywordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userResultKeywordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserResultKeywordDTO result = userResultKeywordService.update(userResultKeywordDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userResultKeywordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-result-keywords/:id} : Partial updates given fields of an existing userResultKeyword, field will ignore if it is null
     *
     * @param id the id of the userResultKeywordDTO to save.
     * @param userResultKeywordDTO the userResultKeywordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userResultKeywordDTO,
     * or with status {@code 400 (Bad Request)} if the userResultKeywordDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userResultKeywordDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userResultKeywordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-result-keywords/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserResultKeywordDTO> partialUpdateUserResultKeyword(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserResultKeywordDTO userResultKeywordDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserResultKeyword partially : {}, {}", id, userResultKeywordDTO);
        if (userResultKeywordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userResultKeywordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userResultKeywordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserResultKeywordDTO> result = userResultKeywordService.partialUpdate(userResultKeywordDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userResultKeywordDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-result-keywords} : get all the userResultKeywords.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userResultKeywords in body.
     */
    @GetMapping("/user-result-keywords")
    public ResponseEntity<List<UserResultKeywordDTO>> getAllUserResultKeywords(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserResultKeywords");
        Page<UserResultKeywordDTO> page;
        if (eagerload) {
            page = userResultKeywordService.findAllWithEagerRelationships(pageable);
        } else {
            page = userResultKeywordService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-result-keywords/:id} : get the "id" userResultKeyword.
     *
     * @param id the id of the userResultKeywordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userResultKeywordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-result-keywords/{id}")
    public ResponseEntity<UserResultKeywordDTO> getUserResultKeyword(@PathVariable Long id) {
        log.debug("REST request to get UserResultKeyword : {}", id);
        Optional<UserResultKeywordDTO> userResultKeywordDTO = userResultKeywordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userResultKeywordDTO);
    }

    /**
     * {@code DELETE  /user-result-keywords/:id} : delete the "id" userResultKeyword.
     *
     * @param id the id of the userResultKeywordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-result-keywords/{id}")
    public ResponseEntity<Void> deleteUserResultKeyword(@PathVariable Long id) {
        log.debug("REST request to delete UserResultKeyword : {}", id);
        userResultKeywordService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
