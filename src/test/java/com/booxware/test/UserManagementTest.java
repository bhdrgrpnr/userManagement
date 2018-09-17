package com.booxware.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;


public class UserManagementTest {

    String userName1 = "bahadir";
    String mail1 = "bahadir@odamax.com";
    String pass1 = "mypass";
    String userName2 = "andy";
    String mail2 = "andy@tipico.com";
    String pass2 = "andyspass";
    String wrongPassword = "somewrongpassword";

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void test1(){

        AccountService accountService = AccountService.getInstance();

        Account newUser = accountService.register(userName1, mail1, pass1);
        assertNotNull(newUser);
        assertEquals(newUser.getUsername(), userName1);
        assertEquals(newUser.getEmail(), mail1);
        assertTrue(Arrays.equals(newUser.getEncryptedPassword(), Utils.hash(pass1)));
        assertNull(newUser.getLastLogin());
    }


    @Test
    public void test2(){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();

        AccountService accountService = AccountService.getInstance();

        accountService.register(userName1, mail1, pass1);
        accountService.register(userName2, mail2, pass2);

        Account loggerInUser1 = accountService.login(userName1, pass1);
        assertNotNull(loggerInUser1);
        Account loggerInUser2 = accountService.login(userName2, pass2);
        assertNotNull(loggerInUser2);


        assertTrue(accountService.hasLoggedInSince(oneMonthAgo, userName1));
        assertTrue(accountService.hasLoggedInSince(oneMonthAgo, userName2));
    }

    @Test
    public void test3(){

        expectedEx.expect(AccountServiceException.class);
        expectedEx.expectMessage(ErrorMessages.WRONG_PASSWORD);

        AccountService accountService = AccountService.getInstance();

        accountService.register(userName1, mail1, pass1);
        accountService.login(userName1, wrongPassword);

    }

    @Test
    public void test4() {

        expectedEx.expect(AccountServiceException.class);
        expectedEx.expectMessage(ErrorMessages.USER_NOT_FOUND);

        AccountService accountService = AccountService.getInstance();

        accountService.register(userName1, mail1, pass1);
        accountService.login(userName1, pass1);
        accountService.deleteAccount(userName1);

        accountService.login(userName1, pass1);
    }

    @Test
    public void test5() {

        AccountService accountService = AccountService.getInstance();

        accountService.register(userName1, mail1, pass1);
        Account loggedInUser = accountService.login(userName1, pass1);
        Date firstDate = loggedInUser.getLastLogin();
        Account loggedInUserAgain = accountService.login(userName1, pass1);

        assertFalse(firstDate.equals(loggedInUserAgain.getLastLogin()));

    }


}
