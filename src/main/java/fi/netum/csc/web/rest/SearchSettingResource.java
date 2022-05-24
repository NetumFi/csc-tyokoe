package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import fi.netum.csc.repository.SearchSettingRepository;
import fi.netum.csc.service.SearchSettingService;
import fi.netum.csc.service.UserService;
import fi.netum.csc.service.dto.SearchSettingDTO;
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
 * REST controller for managing {@link fi.netum.csc.domain.SearchSetting}.
 */
@RestController
@RequestMapping("/api")
public class SearchSettingResource {

    private final Logger log = LoggerFactory.getLogger(SearchSettingResource.class);

    private static final String ENTITY_NAME = "searchSetting";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SearchSettingService searchSettingService;

    private final SearchSettingRepository searchSettingRepository;
    private SecurityUtilComponent securityUtilComponent;

    public SearchSettingResource(SearchSettingService searchSettingService, SearchSettingRepository searchSettingRepository, SecurityUtilComponent securityUtilComponent) {
        this.searchSettingService = searchSettingService;
        this.searchSettingRepository = searchSettingRepository;
        this.securityUtilComponent = securityUtilComponent;
    }

    /**
     * {@code POST  /search-settings} : Create a new searchSetting.
     *
     * @param searchSettingDTO the searchSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new searchSettingDTO, or with status {@code 400 (Bad Request)} if the searchSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/search-settings")
    public ResponseEntity<SearchSettingDTO> createSearchSetting(@RequestBody SearchSettingDTO searchSettingDTO) throws URISyntaxException {
        log.debug("REST request to save SearchSetting : {}", searchSettingDTO);
        if (searchSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new searchSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }

        searchSettingDTO = (SearchSettingDTO) securityUtilComponent.verifyOwner(searchSettingDTO);

        SearchSettingDTO result = searchSettingService.save(searchSettingDTO);
        return ResponseEntity
            .created(new URI("/api/search-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /search-settings/:id} : Updates an existing searchSetting.
     *
     * @param id               the id of the searchSettingDTO to save.
     * @param searchSettingDTO the searchSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated searchSettingDTO,
     * or with status {@code 400 (Bad Request)} if the searchSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the searchSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/search-settings/{id}")
    public ResponseEntity<SearchSettingDTO> updateSearchSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SearchSettingDTO searchSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SearchSetting : {}, {}", id, searchSettingDTO);
        if (searchSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, searchSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!searchSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        searchSettingDTO = (SearchSettingDTO) securityUtilComponent.verifyOwner(searchSettingDTO);

        SearchSettingDTO result = searchSettingService.update(searchSettingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, searchSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /search-settings/:id} : Partial updates given fields of an existing searchSetting, field will ignore if it is null
     *
     * @param id               the id of the searchSettingDTO to save.
     * @param searchSettingDTO the searchSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated searchSettingDTO,
     * or with status {@code 400 (Bad Request)} if the searchSettingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the searchSettingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the searchSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/search-settings/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<SearchSettingDTO> partialUpdateSearchSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SearchSettingDTO searchSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SearchSetting partially : {}, {}", id, searchSettingDTO);
        if (searchSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, searchSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!searchSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SearchSettingDTO> result = searchSettingService.partialUpdate(searchSettingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, searchSettingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /search-settings} : get all the searchSettings.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of searchSettings in body.
     */
    @GetMapping("/search-settings")
    public ResponseEntity<List<SearchSettingDTO>> getAllSearchSettings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of SearchSettings");
        Page<SearchSettingDTO> page;
        boolean isAdminRole = hasCurrentUserAnyOfAuthorities("ROLE_ADMIN");
        User user = securityUtilComponent.getLoggedUser();
        if (eagerload) {
            if (isAdminRole) {
                page = searchSettingService.findAllWithEagerRelationships(pageable);
            } else {
                page = searchSettingService.findAllByUserWithEagerRelationships(user, pageable);
            }
        } else {
            if (isAdminRole) {
                page = searchSettingService.findAll(pageable);
            } else {
                page = searchSettingService.findAllByUser(user, pageable);
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /search-settings/:id} : get the "id" searchSetting.
     *
     * @param id the id of the searchSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the searchSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/search-settings/{id}")
    public ResponseEntity<SearchSettingDTO> getSearchSetting(@PathVariable Long id) {
        log.debug("REST request to get SearchSetting : {}", id);
        Optional<SearchSettingDTO> searchSettingDTO = searchSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(searchSettingDTO);
    }

    /**
     * {@code DELETE  /search-settings/:id} : delete the "id" searchSetting.
     *
     * @param id the id of the searchSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/search-settings/{id}")
    public ResponseEntity<Void> deleteSearchSetting(@PathVariable Long id) {
        log.debug("REST request to delete SearchSetting : {}", id);
        searchSettingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
