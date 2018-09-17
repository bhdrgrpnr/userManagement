package com.booxware.test;

import java.util.Date;

public class UserManagement {

    public static void main(String args[]){

        AccountService accountService = AccountService.getInstance();

        Account newuUser = accountService.register("bahadir", "bahadir@odamax.com", "mypass");
        Account loggedInUser = accountService.login("bahadir", "mypass");
        accountService.hasLoggedInSince(new Date(), loggedInUser.getUsername());
        accountService.deleteAccount(loggedInUser.getUsername());

        loggedInUser = accountService.login("bahadir", "mypass");



    }


}
