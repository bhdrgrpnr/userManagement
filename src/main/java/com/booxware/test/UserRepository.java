package com.booxware.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserRepository implements PersistenceInterface {

    private int sequence=0;
                //UserId
    private Map<Integer, Account> userMap = new HashMap<>();

    private static UserRepository instance = null;
    private UserRepository() {
        // Exists only to defeat instantiation.
    }
    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }


    //the better would be returning id on new created account
    @Override
    public Account save(Account account) {
        if( account.getId()==0){
            sequence++;
            account.setId(sequence);
        }
        userMap.put(account.getId(), account);
        return account;
    }

    @Override
    public Account findById(long id) {
        return userMap.get(id);
    }

    //no other way that
    @Override
    public Account findByName(String name) {

        for(Account account : userMap.values()){
            if(account.getUsername().equals(name)){
                return account;
            }
        }

        return null;
    }

    @Override
    public void delete(Account account) {

        Iterator<Account> iterator = userMap.values().iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId() == account.getId()){
                iterator.remove();
                break;
            }
        }
    }
}
