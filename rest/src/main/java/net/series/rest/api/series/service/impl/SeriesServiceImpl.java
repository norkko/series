package net.series.rest.api.series.service.impl;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.series.Series;
import net.series.rest.api.series.repository.SeriesRepository;
import net.series.rest.api.series.service.SeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeriesServiceImpl implements SeriesService {

    private static final Logger logger = LoggerFactory.getLogger(SeriesServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private SeriesRepository seriesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSeries(Authentication authentication, Series series) {
        int id = getId(authentication);

        series.setAccount(accountService.findById(id));
        seriesRepository.save(series);

        logger.info("Series saved " + series.getSeries());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSeries(Authentication authentication, int seriesId) {
        int id = getId(authentication);

        Account account = accountService.findById(id);
        account.getSeries().removeIf(series -> series.getId() == seriesId);
        accountService.save(account);

        logger.info("Series removed" + seriesId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Series> getSeries(Authentication authentication) {
        int id = getId(authentication);

        logger.info("Series fetched");
        return accountService.findById(id).getSeries();
    }

    /**
     * Helper method for finding account ids,
     * using an authorization header on requests
     *
     * @param authentication
     * @return found account id
     */
    private int getId(Authentication authentication) {
        return accountService.findByUsername(authentication.getName()).getId();
    }

}
