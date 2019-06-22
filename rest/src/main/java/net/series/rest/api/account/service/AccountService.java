package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {

    /**
     *
     * @param account
     */
    Account registerAccount(Account account);

    /**
     *
     * @param authentication
     */
    Account updateAccount(Authentication authentication, Account body);

    /**
     *
     * @param authentication
     */
    void removeAccount(Authentication authentication);

    /**
     *
     * @param id
     */
    Account findById(int id);

    /**
     *
     * @param username
     */
    Account findByUsername(String username);

}
