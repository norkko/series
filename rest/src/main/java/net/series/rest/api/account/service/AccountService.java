package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public interface AccountService extends UserDetailsService {
    Account save(Account account);
    Account findById(int id);
    Account findByUsername(String username);
    // todo update account
    // todo remove account
}
