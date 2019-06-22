package net.series.rest.api.account.service.impl;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.repository.AccountRepository;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.exception.type.NotFoundException;
import net.series.rest.api.exception.type.UsernameAlreadyExistException;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Component
public class AccountServiceImpl implements AccountService  {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Account registerAccount(Account account) {
        if (findByUsername(account.getUsername()) != null) {
            throw new UsernameAlreadyExistException("Username already exists");
        }
        logger.info("saving account " + account.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(
            Authentication authentication,
            @RequestBody Account body) {
        int id = findByUsername(authentication.getName()).getId();
        Account account = findById(id);
        logger.info("updating account ", account.getUsername());
        // update fields from body
        return accountRepository.save(account);
    }

    @Override
    public void removeAccount(Authentication authentication) {
        int id = findByUsername(authentication.getName()).getId();
        logger.info("removing account", authentication.getName());

        // remove foreign keys
        Account account = findById(id);
        account.getSeries().clear();
        account.getEpisodes().clear();
        accountRepository.save(account);

        // remove account
        accountRepository.deleteById(id);
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
    public Account save(Account account) {
        return accountRepository.save(account);
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
