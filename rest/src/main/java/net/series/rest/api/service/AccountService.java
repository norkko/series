package net.series.rest.api.service;

import net.series.rest.api.domain.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    void save(Account account);
}
