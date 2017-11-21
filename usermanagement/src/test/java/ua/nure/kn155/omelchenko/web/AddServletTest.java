package ua.nure.kn155.omelchenko.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class AddServletTest extends MockServletTestCase {
	private static final String USER_LASTNAME = "Watson";
	private static final String USER_NAME = "Jhon";
	private Date date = new Date();
	private User user = new User(USER_NAME, USER_LASTNAME, date);
	private User addedUser = new User(1000L,USER_NAME, USER_LASTNAME, date);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	@Test
	public void testAdd() {
		getMockUserDao().expectAndReturn("create", user, addedUser);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	@Test
	public void testAddEmptyFirstName() {
		getMockUserDao().expectAndReturn("create", user, addedUser);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
	
	@Test
	public void testAddEmptyLastName() {
		getMockUserDao().expectAndReturn("create", user, addedUser);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}

	@Test
	public void testAddEmptyDateOfBirth() {
		getMockUserDao().expectAndReturn("create", user, addedUser);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", "");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
	
	@Test
	public void testAddInvalidDate() {
		getMockUserDao().expectAndReturn("create", user, addedUser);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", "18.5.12");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
}
