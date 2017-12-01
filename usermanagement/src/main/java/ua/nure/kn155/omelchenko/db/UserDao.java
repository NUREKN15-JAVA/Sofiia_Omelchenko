package ua.nure.kn155.omelchenko.db;

import java.util.Collection;

import ua.nure.kn155.omelchenko.User;

public interface UserDao {
	/**
	 * 
	 * Add a user in db
	 * 
	 * @param user
	 *            all field of user must be field with exception of id, id must be
	 *            null
	 * @return an User object - copy of user with id from db any trouble with db
	 *         yield exception DatabaseExeption
	 */
	public User create(User user) throws DatabaseException;

	/**
	 * Update all attributes for instance user in db
	 * 
	 * @param user
	 *            must be exist in db
	 * @throws DatabaseException
	 */
	public void update(User user) throws DatabaseException;

	/**
	 * Delete user from db
	 * 
	 * @param user
	 *            must be exist in db
	 * @throws DatabaseException
	 */

	public void delete(User user) throws DatabaseException;

	/**
	 * Find a user in db by id
	 * 
	 * @param id
	 *            must be exist in db
	 * @return an User object representing user's copy from db with given id
	 *         otherwise null
	 * @throws DatabaseException
	 */
	public User find(Long id) throws DatabaseException;

	/**
	 * Find all users in db
	 * 
	 * @return a collection containing objects User with copies all users from db
	 *         otherwise return empty list
	 * @throws DatabaseException
	 */
	public Collection<User> findAll() throws DatabaseException;

	public void setConnectionFactory(ConnectionFactory connectionFactory);
	
	public Collection<User> find(String firstName, String lastName) throws DatabaseException;
}
