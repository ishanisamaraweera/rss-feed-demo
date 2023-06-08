package com.ishanis.assignment.rssfeeddemo.services;

import com.ishanis.assignment.rssfeeddemo.models.Item;
import com.ishanis.assignment.rssfeeddemo.repositories.RSSItemsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RSSItemsService {

    @Value("${app.rss.feed.url}")
    private final String RSS_FEED_URL = "https://feeds.simplecast.com/qm_9xx0g";

    private final RSSItemsRepository rssItemsRepository;


    /**
     * Processes the RSS feed and saving the items to the repository.
     */
    public void processRssFeed() {
        List<Item> items = parseItems();
        List<String> idList = items
                .stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        rssItemsRepository.deleteAllById(idList);

        rssItemsRepository.saveAll(items);
    }

    /**
     * Parses the RSS feed and extracts the items.
     *
     * @return List of Item objects representing the parsed items.
     */
    private List<Item> parseItems() {
        List<Item> items = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(RSS_FEED_URL).parser(Parser.xmlParser()).get();
            for (Element itemelement : doc.select("item")) {
                Item item = new Item();
                item.setTitle(itemelement.select("title").first().text());
                item.setId(itemelement.select("guid").first().text());
                item.setDescription(itemelement.select("description").first().text());
                item.setPublicationDate(itemelement.select("pubDate").first().text());
                items.add(item);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return items;
    }
}
