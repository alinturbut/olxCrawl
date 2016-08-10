# Olx Crawler
A small crawler for OLX queries as their notification system is not working properly

### Configuration
- in src/main/resource/application.properties modify spring.mail.username to your google email address
- in src/main/resource/application.properties modify spring.mail.password to your password
- in src/main/java/com/alinturbut/olxCrawl/crawler/JsoupOlxCrawler.java modify CRAWL_URL to the query url of your need as in the given example in code.

### Usage
After the project is configured to your needs, you just have to run `gradlew bootRun` in the root folder of the project.

The application queries every 1 minute on OLX and if it finds any change (add an item or removed one) in the query, it will send you an email on your google email address configured in application.properties.

