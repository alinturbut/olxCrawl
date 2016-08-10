package com.alinturbut.olxCrawl.controller;

import com.alinturbut.olxCrawl.crawler.JsoupOlxCrawler;
import com.alinturbut.olxCrawl.crawler.OlxCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MyCrawlController {
    @Autowired
    private JsoupOlxCrawler jsoupOlxCrawler;

    @Scheduled(fixedDelay = 600000)
    private void scheduleCrawlTask() throws Exception {
        jsoupCrawling();
    }

    @RequestMapping(value = "/startCrawling")
    public void startCrawling() throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("http://olx.ro/auto-masini-moto-ambarcatiuni/autoturisme/q-audi-a5/");

        controller.start(OlxCrawler.class, numberOfCrawlers);
    }

    @RequestMapping(value = "/jsoupCrawl")
    public ResponseEntity<String> jsoupCrawling() {
        return new ResponseEntity<String>(jsoupOlxCrawler.crawlOlx(), HttpStatus.OK);
    }
}
