package net.series.rest.api.account.controller;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST)
    public Account registerAccount(
            @Valid @RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @RequestMapping(
            value = "/update",
            method = RequestMethod.PUT)
    public Account updateAccount(
            Authentication authentication,
            @RequestBody Account body) {
        return accountService.updateAccount(authentication, body);
    }

    @RequestMapping(
            value = "/remove",
            method = RequestMethod.DELETE)
    public void removeAccount(
            Authentication authentication) {
        accountService.removeAccount(authentication);
    }

    @RequestMapping(
            value = "/current",
            method = RequestMethod.GET)
    public Account getAccount(
            Authentication authentication) {
        return accountService.getAccount(authentication);
    }

}
