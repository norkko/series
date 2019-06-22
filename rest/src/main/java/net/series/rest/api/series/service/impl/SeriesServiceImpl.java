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

import javax.validation.ConstraintViolationException;
import java.util.List;

@Component
public class SeriesServiceImpl implements SeriesService {

    private static final Logger logger = LoggerFactory.getLogger(SeriesServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private SeriesRepository seriesRepository;

    // dont allow duplicate
    public Series saveSeries(Authentication authentication, Series series) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("saving series %s for %s", series.getSeries(), id));
        series.setAccount(accountService.findById(id));
        return seriesRepository.save(series);
    }

    @Override
    public void removeSeries(Authentication authentication, int seriesId) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        Account account = accountService.findById(id);
        account.getSeries().removeIf(series -> series.getId() == seriesId);
        accountService.save(account);
    }

    @Override
    public List<Series> getSeries(Authentication authentication) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("fetching series for %s", id));
        return accountService.findById(id).getSeries();
    }

}
