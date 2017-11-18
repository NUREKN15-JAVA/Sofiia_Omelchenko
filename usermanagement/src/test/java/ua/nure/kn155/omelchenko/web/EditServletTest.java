package ua.nure.kn155.omelchenko.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class EditServletTest extends MockServletTestCase {
	private static final String USER_ID = "1000";
	private static final String USER_LASTNAME = "Watson";
	private static final String USER_NAME = "Jhon";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}

	@Test
	public void testEdit() {
		Date date = new Date();
		User user = new User(1000L, USER_NAME, USER_LASTNAME, date);
		getMockUserDao().expect("update", user);
		addRequestParameter("id", USER_ID);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	@Test
	public void testEditEmptyFirstName() {
		addRequestParameter("id", USER_ID);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
	
	@Test
	public void testEditEmptyLastName() {
		addRequestParameter("id", USER_ID);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(new Date()));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}

	@Test
	public void testEditEmptyDateOfBirth() {
		addRequestParameter("id", USER_ID);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}
	
	@Test
	public void testEditInvalidDate() {
		addRequestParameter("id", USER_ID);
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", "18.5.12");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
}
