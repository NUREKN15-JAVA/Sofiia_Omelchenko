package ua.nure.kn155.omelchenko.db;

import java.sql.Connection;
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
			User createdUser;
			createdUser = dao.create(user);
			assertNotNull(createdUser);
			assertNotNull(createdUser.getId());
			assertEquals(user.getFirstName(), createdUser.getFirstName());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			fail(e.toString());
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
}
