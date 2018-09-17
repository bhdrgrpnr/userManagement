package com.booxware.test;

/**
 * Persistence can be very simple, for example an in memory hash map.
 * 
 */
public interface PersistenceInterface {

	// I changed the interface, not it returns the new created object with new id;
	//because consumers of repository needs to know the id of newly created object
	public Account save(Account account);

	public Account findById(long id);

	public Account findByName(String name);

	public void delete(Account account);

}
