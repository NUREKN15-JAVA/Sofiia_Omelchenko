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
	public User create(User user) throws DatabaseExeption;

	/**
	 * Update all attributes for instance user in db
	 * 
	 * @param user
	 *            must be exist in db
	 * @throws DatabaseExeption
	 */
	public void update(User user) throws DatabaseExeption;

	/**
	 * Delete user from db
	 * 
	 * @param user
	 *            must be exist in db
	 * @throws DatabaseExeption
	 */

	public void delete(User user) throws DatabaseExeption;

	/**
	 * Find a user in db by id
	 * 
	 * @param id
	 *            must be exist in db
	 * @return an User object representing user's copy from db with given id
	 *         otherwise null
	 * @throws DatabaseExeption
	 */
	public User find(Long id) throws DatabaseExeption;

	/**
	 * Find all users in db
	 * 
	 * @return a collection containing objects User with copies all users from db
	 *         otherwise return empty list
	 * @throws DatabaseExeption
	 */
	public Collection<User> findAll() throws DatabaseExeption;

	public void setConnectionFactory(ConnectionFactory connectionFactory);
}
