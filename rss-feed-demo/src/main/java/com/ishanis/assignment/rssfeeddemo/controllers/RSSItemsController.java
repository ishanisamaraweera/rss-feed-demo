package com.ishanis.assignment.rssfeeddemo.controllers;

import com.ishanis.assignment.rssfeeddemo.models.Item;
import com.ishanis.assignment.rssfeeddemo.repositories.RSSItemsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling HTTP requests related to RSS items.
 */
@RestController
@AllArgsConstructor
public class RSSItemsController {
    private final RSSItemsRepository rssItemRepository;

    /**
     * Retrieves the newest items based on publication date.
     *
     * @return Iterable of Item objects representing the newest items.
     */
    @GetMapping("/items")
    public Iterable<Item> getNewestItems() {
        return rssItemRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "publicationDate"))).getContent();
    }

    /**
     * Retrieves paginated items based on the specified parameters.
     *
     * @param page       Page number (default: 0)
     * @param size       Number of items per page (default: 10)
     * @param sortField  Field to sort by (default: publicationDate)
     * @param direction  Sort direction (default: desc)
     * @return Page of Item objects representing the paginated items.
     */
    @GetMapping("/items/paginated")
    public Page<Item> getPaginatedItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publicationDate") String sortField,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(sortDirection, sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return rssItemRepository.findAll(pageRequest);
    }
}