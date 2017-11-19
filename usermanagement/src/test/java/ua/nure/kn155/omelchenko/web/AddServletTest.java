package ua.nure.kn155.omelchenko.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class AddServletTest extends MockServletTestCase {
	private static final String USER_LASTNAME = "Watson";
	private static final String USER_NAME = "Jhon";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	@Test
	public void testAdd() {
		Date date = new Date();
		User user = new User(USER_NAME, USER_LASTNAME, date);
		User addedUser = new User(1000L,USER_NAME, USER_LASTNAME, date);
		getMockUserDao().expectAndReturn("create", user, addedUser);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	@Test
	public void testAddEmptyFirstName() {
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
	
	@Test
	public void testAddEmptyLastName() {
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}

	@Test
	public void testAddEmptyDateOfBirth() {
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
	
	@Test
	public void testAddInvalidDate() {
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", "18.5.12");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
}
