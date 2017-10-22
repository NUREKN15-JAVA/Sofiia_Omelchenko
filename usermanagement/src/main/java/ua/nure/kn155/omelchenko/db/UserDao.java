package ua.nure.kn155.omelchenko.db;

import java.util.Collection;

import ua.nure.kn155.omelchenko.User;

/*
 * Написать спецификации ко всем методам
 */
public interface UserDao {
	/**
	 * 
	 * @author София 
	 * add user in db
	 * @param user
	 *            all field of user must be field with exception of id, id must be
	 *            null
	 * @return copy of user with id from db
	 * 			any trouble with db yield exception  DatabaseExeption
	 */
	public User create(User user) throws DatabaseExeption;
/**
 * 
 * @param user must be exist in db
 * @throws DatabaseExeption
 */
	public void update(User user) throws DatabaseExeption;

	public void delete(User user) throws DatabaseExeption;
/**
 * 
 * @param id must be exist
 * @return
 * @throws DatabaseExeption
 */
	public User find(Long id) throws DatabaseExeption;

	public Collection<User> findAll() throws DatabaseExeption;
	public void setConnectionFactory(ConnectionFactory connectionFactory);
} 
