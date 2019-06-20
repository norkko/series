package net.series.rest.api.account.service.impl;

import net.series.rest.api.account.domain.Account;
import net.series.rest.api.account.repository.AccountRepository;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.episode.repository.EpisodeRepository;
import net.series.rest.api.exception.type.NotFoundException;
import net.series.rest.api.exception.type.UsernameAlreadyExistException;
import net.series.rest.api.series.Series;
import net.series.rest.api.series.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AccountServiceImpl implements AccountService{

    // todo move appropriate methods to episode&series service/controller

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Account account) {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new UsernameAlreadyExistException("Username exists");
        }
        logger.info("saving account " + account.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public Account findById(int id) {
        if (accountRepository.findById(id) == null) {
            throw new NotFoundException("Account not found");
        }
        return accountRepository.findById(id);
    }

    public void saveSeries(Series body, int id) {
        logger.info(String.format("saving series %s for %s", body.getSeries(), id));
        Account account = findById(id);
        Series series = new Series();
        series.setSeries(body.getSeries());
        series.setAccount(account);
        seriesRepository.save(series);
    }

    public List<Series> getSeries(int id) {
        logger.info(String.format("fetching series for %s", id));
        return findById(id).getSeries();
    }

    @Override
    public void saveSeason(Episode body, int id) {
        // todo
    }

    public void saveEpisode(Episode body, int id) {
        logger.info(String.format("saving episode %s/%s for %s ", body.getSeason(), body.getEpisode(), id));
        Account account = findById(id);
        Episode episode = new Episode();
        episode.setSeries(body.getSeries());
        episode.setEpisode(body.getEpisode());
        episode.setSeason(body.getSeason());
        episode.setAccount(account);
        episodeRepository.save(episode);
    }

    public List<Episode> getEpisodesForSpecificSeries(int id, int seriesId) {
        logger.info(String.format("fetching episodes from %s for %s", seriesId, id));
        List<Episode> episodes = findById(id).getEpisodes();
        episodes.removeIf(item -> item.getSeries() != seriesId);
        return episodes;
    }

    @Override
    public List<Episode> getEpisodes(int id) throws NotFoundException {
        logger.info(String.format("fetching episodes for %s", id));
        return findById(id).getEpisodes();
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByUsername(username);
        return new User(account.getUsername(), account.getPassword(), getAuthorities());
    }

    private static Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
}
