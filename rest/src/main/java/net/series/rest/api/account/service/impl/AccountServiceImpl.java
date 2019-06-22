package net.series.rest.api.account.service.impl;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.repository.AccountRepository;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.exception.type.NotFoundException;
import net.series.rest.api.exception.type.UsernameAlreadyExistException;
import net.series.rest.api.series.Series;
import net.series.rest.api.series.repository.SeriesRepository;
import net.series.rest.api.series.service.SeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AccountServiceImpl implements AccountService  {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SeriesService seriesService;

    @Override
    public Account registerAccount(Account account) {
        if (findByUsername(account.getUsername()) != null) {
            throw new UsernameAlreadyExistException("Username exists");
        }

        logger.info("saving account " + account.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);

        return account;
    }

    @Override
    public Account findById(int id) {
        if (accountRepository.findById(id) == null) {
            throw new NotFoundException("Account not found");
        }

        return accountRepository.findById(id);
    }

    @Override
    public Account findByUsername(String username) {
        try {
            return accountRepository.findByUsername(username);
        } catch (Exception e) {
            logger.info("account is null");
            return null;
        }
    }

    @Override
    public void updateAccount(Authentication authentication) {

    }

    @Override
    public void removeAccount(Authentication authentication) {
        int id = findByUsername(authentication.getName()).getId();

        // remove foreign keys
        Account account = findById(id);
        account.getSeries().clear();
        account.getEpisodes().clear();
        accountRepository.save(account);

        // remove account
        accountRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = findByUsername(username);
        return new User(account.getUsername(), account.getPassword(), getAuthorities());

    }

    private static Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
}
