package ua.nure.kn155.omelchenko.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class HsqlUserDaoTest extends DatabaseTestCase {
	private static final Long FIND_ID = 1L;
	private static final Long DELETE_ID = 2L;
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;

	/**
	 * написать остальные методы для тестирования update, .... (min=4) поменять
	 * junit на 3й
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	public Connection createConnection() throws DatabaseExeption {
		return null;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
	}

	@Test
	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName("Klark");
			user.setLastName("Kent");
			user.setDateOfBirthd(new Date());
			assertNull(user.getId());
			User createdUser = dao.create(user);
			assertNotNull(createdUser);
			assertNotNull(createdUser.getId());
			assertNotNull(createdUser.getFullName());
			assertNotNull(createdUser.getDateOfBirthd());
//			assertEquals(user.getId(), createdUser.getId());
			assertEquals(user.getFullName(), createdUser.getFullName());
			assertEquals(user.getDateOfBirthd(), createdUser.getDateOfBirthd());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testDelete() {
		try {
			User user = dao.find(DELETE_ID);
			assertNotNull("User is null", user);
 			dao.delete(user);
 			User deletedUser = dao.find(DELETE_ID);
			assertNull("User wasn't deleted", deletedUser);
			dao.create(user);
		} catch (DatabaseExeption e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindAll() {
		Collection<User> collection;
		try {
			collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size.", 2, collection.size());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFind() {
		User user = new User();
		try {
			User findedUser = dao.find(FIND_ID);
			assertNotNull("User is null", user);
			assertEquals("Different IDs",FIND_ID, findedUser.getId());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
		}

	}

}
