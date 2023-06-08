package com.ishanis.assignment.rssfeeddemo.services;

import com.ishanis.assignment.rssfeeddemo.models.Item;
import com.ishanis.assignment.rssfeeddemo.repositories.RSSItemsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class RSSItemsServiceTest {

    private RSSItemsService rssItemsService;

    @Mock
    private RSSItemsRepository rssItemsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rssItemsService = new RSSItemsService(rssItemsRepository);
    }

    @Test
    void processRssFeed_ShouldSaveItems() throws IOException {
        // Arrange
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setTitle("Item 1");
        item1.setId("1");
        item1.setDescription("Description 1");
        items.add(item1);

        Item item2 = new Item();
        item2.setTitle("Item 2");
        item2.setId("2");
        item2.setDescription("Description 2");
        items.add(item2);

        String rssFeedUrl = "https://feeds.simplecast.com/qm_9xx0g";

        Document doc = mock(Document.class);
        Element itemElement1 = mock(Element.class);
        when(itemElement1.text()).thenReturn("Item 1");

        Element itemElement2 = mock(Element.class);
        when(itemElement2.text()).thenReturn("Item 2");

        when(doc.select("item")).thenReturn(new Elements(itemElement1,itemElement2));

        when(Jsoup.connect(rssFeedUrl).parser(Parser.xmlParser()).get()).thenReturn(doc);
        when(rssItemsRepository.saveAll(items)).thenReturn(items);

        // Act
        rssItemsService.processRssFeed();

        // Assert
        verify(rssItemsRepository, times(1)).deleteAllById(anyList());
        verify(rssItemsRepository, times(1)).saveAll(items);
    }
}

