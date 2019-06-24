package net.series.rest.api.account.repository;

import net.series.rest.api.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(AccountRepositoryTest.class);

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldFindById() {
        final Account account = getAccountStub();
        accountRepository.save(account);

        final Account found = accountRepository.findById(account.getId());
        assertEquals(found, account);
    }

    @Test
    public void shouldFindByUsername() {
        final Account account = getAccountStub();
        accountRepository.save(account);

        final Account found = accountRepository.findByUsername(account.getUsername());
        assertEquals(found, account);
    }

    @Test
    public void shouldDeleteAccount() {
        final Account account = getAccountStub();

        accountRepository.save(account);
        assertNotNull(accountRepository.findById(account.getId()));

        accountRepository.deleteById(account.getId());
        assertNull(accountRepository.findById(account.getId()));
    }

    private Account getAccountStub() {
        Account account = new Account();
        account.setUsername("user");
        account.setPassword(encoder.encode("password"));
        return account;
    }

}
