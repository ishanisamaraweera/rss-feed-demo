package com.ishanis.assignment.rssfeeddemo.schedulers;


import com.ishanis.assignment.rssfeeddemo.services.RSSItemsService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class RSSPullingScheduler {

    private final RSSItemsService rssItemsService;

    @Scheduled(fixedRate = 300_000)
    public void pullRSS() throws IOException {
        try {
            System.out.println("Polling the RSS feed");
            rssItemsService.processRssFeed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
