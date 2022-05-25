package fi.netum.csc.web.rest;

import fi.netum.csc.service.AoeService;
import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.ItemMetadata;
import fi.netum.csc.service.dto.aoe.SearchResults;
import fi.netum.csc.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final AoeService aoeService;

    public SearchResource(AoeService aoeService) {
        this.aoeService = aoeService;
    }
    /**
     * POST doSearch
     * @param aoeSearchParameters
     * @return SearchResults
     */
    @PostMapping("/do-search")
    public SearchResults doSearch(@RequestBody AoeSearchParameters aoeSearchParameters) throws BadRequestAlertException, IOException, InterruptedException {
        log.debug("REST request to search: {}", aoeSearchParameters);
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
     * GET getMetadata for array of ids
     * @param ids
     * @return ItemMetadata
     */
    @GetMapping("/get-metadatas/{ids}")
    public List<ItemMetadata> getMetadatas(@PathVariable ArrayList<String> ids) {
        log.debug("REST request metadata to ids: {}", ids);
        if (ids == null) {
            throw new BadRequestAlertException("Ids cannot be empty", "ids", "empty");
        }

        return ids.stream().map(i -> {
            try {
                return aoeService.getMetadata(i);
            } catch (Exception e) {
                log.warn("Metadatan hakeminen epäonnistui, id: " + i, e);
                Thread.currentThread().interrupt();
            }
            return null;
        }).collect(Collectors.toList());
    }
}
