package net.series.rest.account.repository;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    // use get stub method

    @Test
    public void shouldFindById() {
        Account account = new Account();
        account.setUsername("user");
        account.setPassword(encoder.encode("password"));

        accountRepository.save(account);

        Account found = accountRepository.findById(account.getId());
        assertEquals(found, account);
    }

    @Test
    public void shouldFindByUsername() {
        String username = "user";

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(encoder.encode("password"));

        accountRepository.save(account);

        Account found = accountRepository.findByUsername(username);
        assertEquals(found, account);
    }

    @Test
    public void shouldDeleteAccount() {
        Account account = new Account();
        account.setUsername("user");
        account.setPassword(encoder.encode("password"));
        accountRepository.save(account);

        assertNotNull(accountRepository.findById(account.getId()));

        accountRepository.deleteById(account.getId());

        assertNull(accountRepository.findById(account.getId()));
    }

}
