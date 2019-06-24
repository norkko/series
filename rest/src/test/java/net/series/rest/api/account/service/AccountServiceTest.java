package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldFindByUsername() {
        final Account account = new Account();
        account.setUsername("user");
        account.setPassword(encoder.encode("password"));

        when(accountService.findByUsername(account.getUsername())).thenReturn(account);

        Account found = accountService.findByUsername(account.getUsername());
        assertEquals(account, found);
    }

    @Test
    public void shouldRegisterAccount() {
        final Account account = new Account();
        account.setUsername("user");
        account.setPassword(encoder.encode("password"));

        accountService.registerAccount(account);
        verify(accountRepository, times(1)).save(account);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenIdNotInUse() {
        accountService.findById(-1);
    }

}
