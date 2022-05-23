package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import fi.netum.csc.repository.ReadingListRepository;
import fi.netum.csc.service.ReadingListService;
import fi.netum.csc.service.UserService;
import fi.netum.csc.service.dto.ReadingListDTO;
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

import static fi.netum.csc.security.SecurityUtils.hasCurrentUserAnyOfAuthorities;

/**
 * REST controller for managing {@link fi.netum.csc.domain.ReadingList}.
 */
@RestController
@RequestMapping("/api")
public class ReadingListResource {

    private final Logger log = LoggerFactory.getLogger(ReadingListResource.class);

    private static final String ENTITY_NAME = "readingList";
    private final UserService userService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReadingListService readingListService;

    private final ReadingListRepository readingListRepository;

    public ReadingListResource(ReadingListService readingListService, ReadingListRepository readingListRepository, UserService userService) {
        this.readingListService = readingListService;
        this.readingListRepository = readingListRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /reading-lists} : Create a new readingList.
     *
     * @param readingListDTO the readingListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new readingListDTO, or with status {@code 400 (Bad Request)} if the readingList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reading-lists")
    public ResponseEntity<ReadingListDTO> createReadingList(@RequestBody ReadingListDTO readingListDTO) throws URISyntaxException {
        log.debug("REST request to save ReadingList : {}", readingListDTO);
        if (readingListDTO.getId() != null) {
            throw new BadRequestAlertException("A new readingList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReadingListDTO result = readingListService.save(readingListDTO);
        return ResponseEntity
            .created(new URI("/api/reading-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reading-lists/:id} : Updates an existing readingList.
     *
     * @param id the id of the readingListDTO to save.
     * @param readingListDTO the readingListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated readingListDTO,
     * or with status {@code 400 (Bad Request)} if the readingListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the readingListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reading-lists/{id}")
    public ResponseEntity<ReadingListDTO> updateReadingList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReadingListDTO readingListDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReadingList : {}, {}", id, readingListDTO);
        if (readingListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, readingListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!readingListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReadingListDTO result = readingListService.update(readingListDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, readingListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reading-lists/:id} : Partial updates given fields of an existing readingList, field will ignore if it is null
     *
     * @param id the id of the readingListDTO to save.
     * @param readingListDTO the readingListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated readingListDTO,
     * or with status {@code 400 (Bad Request)} if the readingListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the readingListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the readingListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reading-lists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReadingListDTO> partialUpdateReadingList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReadingListDTO readingListDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReadingList partially : {}, {}", id, readingListDTO);
        if (readingListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, readingListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!readingListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReadingListDTO> result = readingListService.partialUpdate(readingListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, readingListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reading-lists} : get all the readingLists.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of readingLists in body.
     */
    @GetMapping("/reading-lists")
    public ResponseEntity<List<ReadingListDTO>> getAllReadingLists(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ReadingLists");

        List<String> authorities = this.userService.getAuthorities();
        Optional<User> optionalUser = this.userService.getUserWithAuthorities();
        Page<ReadingListDTO> page;
        User user  = optionalUser.get();
        boolean isAdminRole  = hasCurrentUserAnyOfAuthorities("ROLE_ADMIN");

        if (eagerload) {
            if( isAdminRole ) {
                page = readingListService.findAllWithEagerRelationships(pageable);
            }
            else {
                page = readingListService.findAllByUserWithEagerRelationships(user, pageable);
            }
        } else {
            if (isAdminRole) {
                page = readingListService.findAll(pageable);
            } else {
                page = readingListService.findAllByUser(user, pageable);
            }

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reading-lists/:id} : get the "id" readingList.
     *
     * @param id the id of the readingListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the readingListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reading-lists/{id}")
    public ResponseEntity<ReadingListDTO> getReadingList(@PathVariable Long id) {
        log.debug("REST request to get ReadingList : {}", id);
        Optional<ReadingListDTO> readingListDTO = readingListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(readingListDTO);
    }

    /**
     * {@code DELETE  /reading-lists/:id} : delete the "id" readingList.
     *
     * @param id the id of the readingListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reading-lists/{id}")
    public ResponseEntity<Void> deleteReadingList(@PathVariable Long id) {
        log.debug("REST request to delete ReadingList : {}", id);
        readingListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
