# olxCrawl
A small crawler for OLX queries as their notification system is not working

In order to make it working you have to follow these steps

-> in src/main/resource/application.properties modify spring.mail.username to your google email address
-> in src/main/resource/application.properties modify spring.mail.password to your password
-> in src/main/java/com/alinturbut/olxCrawl/crawler/OlxCrawler.java modify CRAWL_URL to the query url of your need as in the given example in code.
