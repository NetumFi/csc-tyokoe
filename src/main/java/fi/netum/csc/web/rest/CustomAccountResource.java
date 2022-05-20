package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import fi.netum.csc.repository.UserRepository;
import fi.netum.csc.service.ReadingListService;
import fi.netum.csc.service.SearchHistoryService;
import fi.netum.csc.service.SearchSettingService;
import fi.netum.csc.service.UserService;
import fi.netum.csc.service.dto.ReadingListDTO;
import fi.netum.csc.service.dto.SearchHistoryDTO;
import fi.netum.csc.service.dto.SearchSettingDTO;
import fi.netum.csc.service.dto.UserDTO;
import fi.netum.csc.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class CustomAccountResource {
    private final Logger log = LoggerFactory.getLogger(CustomAccountResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SearchSettingService searchSettingService;
    private final ReadingListService readingListService;
    private final SearchHistoryService searchHistoryService;

    public CustomAccountResource(UserRepository userRepository, UserService userService, SearchSettingService searchSettingService, ReadingListService readingListService, SearchHistoryService searchHistoryService) {
        this.userRepository = userRepository;
        this.userService = userService;

        this.searchSettingService = searchSettingService;
        this.readingListService = readingListService;
        this.searchHistoryService = searchHistoryService;
    }


    @GetMapping("/account/searchsettings")
    public ResponseEntity<SearchSettingDTO> getCurrentUserSearchSettings() {

        Optional<User> optionalUser = userService.getUserWithAuthorities();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            log.info("User: {} -> id: {}", user, user.getId());
            Optional<SearchSettingDTO> searchSettingDTO = searchSettingService.findByUser(user);

            log.info("searchSetting: {}", searchSettingDTO);
            return ResponseUtil.wrapOrNotFound(searchSettingDTO);
        }
        throw new BadRequestAlertException("User is not logged in", "SEARCH_SETTING", "");
    }

    @PostMapping("/account/readinglist")
    public ResponseEntity<ReadingListDTO> createReadingList(@RequestBody ReadingListDTO readingListDTO) throws URISyntaxException {
        log.debug("REST request to save ReadingList : {}", readingListDTO);
        if (readingListDTO.getId() != null) {
            throw new BadRequestAlertException("A new readingList cannot already have an ID", "READING_LIST", "idexists");
        }

        Optional<User> optionalUser = this.userService.getUserWithAuthorities();
        if( optionalUser.isPresent() ) {
            User user = optionalUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());

            readingListDTO.setUser(userDTO);
            readingListDTO.setCreated(Instant.now());
            ReadingListDTO result = readingListService.save(readingListDTO);
            return ResponseEntity
                .created(new URI("/api/reading-lists/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, "READING_LIST", result.getId().toString()))
                .body(result);
        }
        throw new BadRequestAlertException("User is not logged in", "SEARCH_SETTING", "");
    }

    @GetMapping("/account/searchhistory")
    public ResponseEntity<List<SearchHistoryDTO>> getCurrentUserSearchHistory(@org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Page<SearchHistoryDTO> page = searchHistoryService.findAllByUser(user, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

        throw new BadRequestAlertException("User is not logged in", "SEARCH_SETTING", "");
    }

    @PostMapping("/account/searchhistory")
    public ResponseEntity<SearchHistoryDTO> addSearchHistory(@RequestBody SearchHistoryDTO searchHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save ReadingList : {}", searchHistoryDTO);
        if (searchHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new searchhistory cannot already have an ID", "SEARCH_HISTORY", "idexists");
        }

        Optional<User> optionalUser = this.userService.getUserWithAuthorities();
        if( optionalUser.isPresent() ) {
            User user = optionalUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            searchHistoryDTO.setUser(userDTO);

            SearchHistoryDTO result = searchHistoryService.save(searchHistoryDTO);
            return ResponseEntity
                .created(new URI("/api/search-history/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, "SEARCH_HISTORY", result.getId().toString()))
                .body(result);
        }
        throw new BadRequestAlertException("User is not logged in", "SEARCH_SETTING", "");
    }

}
