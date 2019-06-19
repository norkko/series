package net.series.rest.api.controller;

import net.series.rest.api.domain.Account;
import net.series.rest.api.domain.Series;
import net.series.rest.api.repository.AccountRepository;
import net.series.rest.api.repository.SeriesRepository;
import net.series.rest.http.Request;
import net.series.rest.http.Response;
import net.series.rest.http.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class Controller {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private Request request;

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Response searchSeries() {
        return request.send(new Url("Suits").toString());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response searchSeries(@PathVariable int id) {
        return request.send(new Url(id).toString());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void searchSeries(@RequestBody Account account) {
        accountRepository.save(account);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public List<Account> getAccount() {
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/current/{seriesId}/{id}", method = RequestMethod.POST)
    public void dsa(@PathVariable int seriesId, @PathVariable int id) {
        Account account = accountRepository.findById(id);
        Series series = new Series();
        series.setSeries(seriesId);
        series.setAccount(account);
        seriesRepository.save(series);
    }

    @RequestMapping(value = "/current/{id}", method = RequestMethod.POST)
    public List<Series> getSeries(@PathVariable int id) {
        return accountRepository.findById(id).getSeries();
    }
}
