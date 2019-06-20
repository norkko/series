package net.series.rest.api.account.controller;

import net.series.rest.api.account.domain.Account;
import net.series.rest.api.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST)
    public void register(
            @RequestBody Account account) {
        accountService.save(account);
    }

}
