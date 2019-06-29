package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends UserDetailsService {

    /**
     * Checks if the given username already exists
     * Encodes the password
     * Saves the account
     *
     * @param account
     */
    Account registerAccount(Account account);

    /**
     * Gets account id using authorization header
     * Finds account with account id
     * Updates account fields and saves it
     *
     * @param authentication
     */
    Account updateAccount(Authentication authentication, Account body);

    /**
     * Gets account id using authorization header
     * Clears all series and episodes to remove any used foreign keys
     * Removes the account
     *
     * @param authentication
     */
    void removeAccount(Authentication authentication);

    /**
     * Finds account by given id
     *
     * @param id
     */
    Account findById(int id);

    /**
     * Finds account by given username
     *
     * @param username
     */
    Account findByUsername(String username);

    /**
     * Helper method for saving an account,
     * used when removing series or episodes
     * for an account
     *
     * @param account
     * @return the account
     */
    Account save(Account account);

    /**
     * Finds the current account and returns it
     *
     * @param authentication
     * @return corresponding account
     */
    Account getAccount(Authentication authentication);

}
