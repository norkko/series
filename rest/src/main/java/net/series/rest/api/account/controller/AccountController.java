package net.series.rest.api.account.controller;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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
