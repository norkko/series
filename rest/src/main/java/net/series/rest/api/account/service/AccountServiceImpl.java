package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.exception.type.UsernameUnavailableException;
import net.series.rest.api.account.repository.AccountRepository;
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

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Account registerAccount(Account account) {
        if (findByUsername(account.getUsername()) != null) {
            throw new UsernameUnavailableException("Username already exists");
        }

        account.setPassword(encoder.encode(account.getPassword()));
        logger.info("Account saved " + account.getUsername());
        return accountRepository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account updateAccount(
            Authentication authentication,
            @RequestBody Account body) {
        int id = getId(authentication);

        Account account = findById(id);
        // Update fields from body

        logger.info("Account updated " + account.getUsername());
        return accountRepository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAccount(Authentication authentication) {
        int id = getId(authentication);

        // Remove foreign keys
        Account account = findById(id);
        account.getSeries().clear();
        account.getEpisodes().clear();
        accountRepository.save(account);

        accountRepository.deleteById(id);
        logger.info("Account removed " + authentication.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account findById(int id) {
        if (accountRepository.findById(id) == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return accountRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account findByUsername(String username) {
        // check if username is empty, illegalArgumentException
        return accountRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getAccount(Authentication authentication) {
        return accountRepository.findById(getId(authentication));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = findByUsername(username);
        return new User(account.getUsername(), account.getPassword(), getAuthorities());
    }

    /**
     * Creates an authority list containing only the role ROLE_USER
     *
     * @return account authorities
     */
    private static Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    /**
     * Helper method for finding account ids,
     * using an authorization header on requests
     *
     * @param authentication
     * @return found account id
     */
    private int getId(Authentication authentication) {
        return findByUsername(authentication.getName()).getId();
    }

}
