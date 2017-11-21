package ua.nure.kn155.omelchenko.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class EditServletTest extends MockServletTestCase {
	/**
	 * Constants ID, FIRSTNAME, LASTNAME, DATE are used for editing users and
	 * for filling fields
	 */
	private static final long ID = 1000L;
	private static final String LASTNAME = "Watson";
	private static final String FIRSTNAME = "Jhon";
	private final Date DATE = new Date();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}

	/**
	 * Testing the editing of a real user
	 */
	@Test
	public void testEdit() {
		User user = new User(ID, FIRSTNAME, LASTNAME, DATE);
		getMockUserDao().expect("update", user);
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
		addRequestParameter("okButton", "Ok");
		doPost();
	}

	/**
	 * Testing the editing of a real user without first name
	 */
	@Test
	public void testEditEmptyFirstName() {
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}

	/**
	 * Testing the editing of a real user without last name
	 */
	@Test
	public void testEditEmptyLastName() {
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}

	/**
	 * Testing the editing of a real user without DateOfBirth
	 */
	@Test
	public void testEditEmptyDateOfBirth() {
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("The session didn't return an error message", errorMessage);
	}

	/**
	 * Testing the editing of a real user with incorrect DateOfBirth
	 */
	@Test
	public void testEditInvalidDate() {
		addRequestParameter("firstName", FIRSTNAME);
		addRequestParameter("lastName", LASTNAME);
		addRequestParameter("dateOfBirth", "123");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
}
