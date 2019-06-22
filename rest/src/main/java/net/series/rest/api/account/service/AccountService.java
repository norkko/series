package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public interface AccountService extends UserDetailsService {
    Account registerAccount(Account account);
    Account findById(int id);
    Account findByUsername(String username);
    void updateAccount(Authentication authentication);
    void removeAccount(Authentication authentication);
}
