package ua.nure.kn155.omelchenko.web;

import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class DeleteServletTest extends MockServletTestCase {
	/**
	 * Constants ID, FIRSTNAME, LASTNAME, DATE are used for deleting existing users and for
	 * filling fields
	 */
	private static final long ID = 1000L;
	private static final String LASTNAME = "Watson";
	private static final String FIRSTNAME = "Jhon";
	private final Date DATE = new Date();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(DeleteServlet.class);
	}
	/**
	 * Testing the deleting of an existing user
	 */
	@Test
	public void testDelete() {
		User user = new User(ID, FIRSTNAME, LASTNAME, DATE);
		getMockUserDao().expect("delete", user);
		addRequestParameter("okButton", "Ok");
		doPost();
	}
}
