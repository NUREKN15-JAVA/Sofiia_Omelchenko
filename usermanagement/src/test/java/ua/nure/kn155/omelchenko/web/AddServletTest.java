package ua.nure.kn155.omelchenko.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class AddServletTest extends MockServletTestCase {
	/**
	 * Constants FIRSTNAME, LASTNAME, DATE are used for creating new users and for
	 * filling fields
	 */
	private final String LASTNAME = "Watson";
	private final String FIRSTNAME = "Jhon";
	private final Date DATE = new Date();

	private final User USER = new User(FIRSTNAME, LASTNAME, DATE);
	private final User ADDED_USER = new User(1000L, FIRSTNAME, LASTNAME, DATE);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	/**
	 * Testing the addition of a new user
	 */
	@Test
	public void testAdd() {
		getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
		addRequestParameter("okButton", "Ok");
		doPost();
	}

	/**
	 * Testing the addition of a new user without first name
	 */
	@Test
	public void testAddEmptyFirstName() {
		getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
	}

	/**
	 * Testing the addition of a new user without last name
	 */
	@Test
	public void testAddEmptyLastName() {
		getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
	}

	/**
	 * Testing the addition of a new user without DateOfBirth
	 */
	@Test
	public void testAddEmptyDateOfBirth() {
		getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", "");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
	}

	/**
	 * Testing the addition of a new user with incorrect DateOfBirth
	 */
	@Test
	public void testAddInvalidDate() {
		getMockUserDao().expectAndReturn("create", USER, ADDED_USER);
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", "123");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull(errorMessage);
	}
}
