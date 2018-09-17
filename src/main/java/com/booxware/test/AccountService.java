package com.booxware.test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccountService implements AccountServiceInterface {


    static private Map<Integer, Account> onlineUsers = new HashMap<>();

    static UserRepository userRepository;

    private static AccountService instance = null;
    private AccountService() {
        // Exists only to defeat instantiation.
    }
    public static AccountService getInstance() {
        if(instance == null) {
            userRepository = UserRepository.getInstance();
            instance = new AccountService();
        }
        return instance;
    }


    @Override
    public Account login(String username, String password) {
        Account user = userRepository.findByName(username);
        if(user==null){
            throw new AccountServiceException(ErrorMessages.USER_NOT_FOUND);
        }
        if(!Arrays.equals(user.getEncryptedPassword(), Utils.hash(password))){
            throw new AccountServiceException(ErrorMessages.WRONG_PASSWORD);
        }

        Account accountOnline = onlineUsers.get(user.getId());
        if(accountOnline == null){
            user.setLastLogin(new Date());
            onlineUsers.put(user.getId(), user);
            return user;
        }
        else{
            accountOnline.setLastLogin(new Date());
            onlineUsers.put(user.getId(), accountOnline);
            return accountOnline;
        }
    }

    @Override
    public Account register(String username, String email, String password) {
        Account user = new Account();
        user.setEmail(email);
        user.setUsername(username);
        user.setEncryptedPassword(Utils.hash(password));

        return userRepository.save(user);
    }

    @Override
    public void deleteAccount(String username) {
        Account user = userRepository.findByName(username);
        if(user == null){
            throw new AccountServiceException(ErrorMessages.USER_NOT_FOUND);
        }
        onlineUsers.put(user.getId(), null);

        userRepository.delete(user);
    }

    @Override
    public boolean hasLoggedInSince(Date date, String username) {
        Account user = userRepository.findByName(username);
        if(user == null){
            throw new AccountServiceException(ErrorMessages.USER_NOT_FOUND);
        }

        return user.getLastLogin().after(date);
    }
}
