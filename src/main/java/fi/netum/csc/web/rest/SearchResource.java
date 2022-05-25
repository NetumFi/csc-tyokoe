package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import fi.netum.csc.service.AoeService;
import fi.netum.csc.service.SearchSettingService;
import fi.netum.csc.service.UserResultKeywordService;
import fi.netum.csc.service.UserService;
import fi.netum.csc.service.dto.SearchSettingDTO;
import fi.netum.csc.service.dto.UserResultKeywordDTO;
import fi.netum.csc.service.dto.aoe.*;
import fi.netum.csc.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SearchResource controller
 */
@RestController
@RequestMapping("/api/search")
public class SearchResource {

    private final Logger log = LoggerFactory.getLogger(SearchResource.class);
    private final UserService userService;
    private final UserResultKeywordService userResultKeywordService;
    private final SearchSettingService searchSettingService;

    private final AoeService aoeService;

    public SearchResource(AoeService aoeService, UserService userService, UserResultKeywordService userResultKeywordService,
                          SearchSettingService searchSettingService) {

        this.aoeService = aoeService;
        this.userService = userService;
        this.userResultKeywordService = userResultKeywordService;
        this.searchSettingService = searchSettingService;
    }
    /**
     * POST doSearch
     * @param aoeSearchParameters
     * @return SearchResults
     */
    @PostMapping("/do-search")
    public SearchResults doSearch(@RequestBody AoeSearchParameters aoeSearchParameters) throws BadRequestAlertException, IOException, InterruptedException {
        log.debug("REST request to search: {}", aoeSearchParameters);
        Optional<User> optionalUser = this.userService.getUserWithAuthorities();
        User user  = null;
        if (optionalUser.isPresent()) user = optionalUser.get();
        return aoeService.doSearch(aoeSearchParameters, user);
    }

    /**
     * GET getMetadata
     * @param id
     * @return ItemMetadata
     */
    @GetMapping("/get-metadata/{id}")
    public ItemMetadata getMetadata(@PathVariable String id) throws IOException, InterruptedException {
        log.debug("REST request metadata to id: {}", id);
        if (StringUtils.isEmpty(id)) {
            throw new BadRequestAlertException("Id cannot be empty", "id", "empty");
        }

        return aoeService.getMetadata(id);
    }

    /**
     * GET getMetadata for array of ids
     * @param ids
     * @return ItemMetadata
     */
    @GetMapping("/get-metadatas/{ids}")
    public List<ItemMetadata> getMetadatas(@PathVariable ArrayList<String> ids) throws IOException, InterruptedException {
        log.debug("REST request metadata to ids: {}", ids);
        if (ids == null) {
            throw new BadRequestAlertException("Ids cannot be empty", "ids", "empty");
        }

        return ids.stream().map(i -> {
            try {
                return aoeService.getMetadata(i);
            } catch (Exception e) {
                log.warn("Metadatan hakeminen epäonnistui, id: " + i, e);
            }
            return null;
        }).collect(Collectors.toList());
    }

    /**
     * GET getRecommendations
     * @return SearchResults
     */
    @GetMapping("/get-recommendations")
    public SearchResults getRecommendations() throws IOException, InterruptedException {
        Optional<User> optionalUser = this.userService.getUserWithAuthorities();
        User user  = null;

        if (optionalUser.isPresent()) user = optionalUser.get();
        log.debug("REST request recommendations to user: {}", user);

        String educationUuid = "";
        List<Filter> filters = null;
        Page<SearchSettingDTO> searchSettingDTO = searchSettingService.findAllByUser(user, PageRequest.of(0,1));
        if (searchSettingDTO != null && searchSettingDTO.get().findFirst().isPresent()) {
            educationUuid = searchSettingDTO.get().findFirst().get().getEducationLevelCodeSet().getCodeId();
            filters = List.of(new Filter("educationalLevels", List.of(
                educationUuid)));
        }
        Page<UserResultKeywordDTO> userResultKeywords = userResultKeywordService.findAllByUser(user, PageRequest.of(0,1));
        UserResultKeywordDTO resultKeywords = userResultKeywords.get().findFirst().get();

        Paging paging = new Paging(0, 10, "relevance");
        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, resultKeywords.getResultKeyword(), paging);

        return aoeService.doSearch(aoeSearchParameters, user);
    }
}
