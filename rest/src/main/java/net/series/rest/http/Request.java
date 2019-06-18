package net.series.rest.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    @Bean
    public void test() {
        Url query = new Url("suits");
        logger.info("" + query.toString());

        Url episode = new Url(37680, 1, 2);
        logger.info("" + episode.toString());

        Url season = new Url(37680, 1);
        logger.info("" + season.toString());

        Url url = new Url(37680);
        logger.info("" + url.toString());
    }
}
