package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import fi.netum.csc.domain.UserResultKeyword;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        if (aoeSearchParameters.getFilters().isEmpty()) {
            throw new BadRequestAlertException("Search filters cannot be empty", "AoeSearchParameters", "empty");
        }
        return aoeService.doSearch(aoeSearchParameters);
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
     * GET getRecommendations
     * @return SearchResults
     */
    @GetMapping("/get-recommendations")
    public SearchResults getRecommendations() throws IOException, InterruptedException {
        Optional<User> optionalUser = this.userService.getUserWithAuthorities();
        User user  = optionalUser.get();
        log.debug("REST request recommendations to user: {}", user);

        Page<SearchSettingDTO> searchSettingDTO = searchSettingService.findAllByUser(user, PageRequest.of(0,1));
        String educationUuid = searchSettingDTO.get().findFirst().get().getEducationLevelCodeSet().getCodeId();
        Page<UserResultKeywordDTO> userResultKeywords = userResultKeywordService.findAllByUser(user, PageRequest.of(0,1));
        String resultKeywords = userResultKeywords.get().findFirst().toString();
        List<Filter> filters = List.of(new Filter("educationalLevels", List.of(
            educationUuid)));

        Paging paging = new Paging(0, 10, "relevance");
        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, resultKeywords, paging);

        return aoeService.doSearch(aoeSearchParameters);
    }
}
