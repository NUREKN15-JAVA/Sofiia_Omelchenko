package ua.nure.kn155.omelchenko;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import ua.nure.kn155.omelchenko.User;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	private final int DAY_OF_BIRTHD = 9;
	private final int YEAR_OF_BIRTHD = 1998;
	private final int CURRENT_YEAR = 2017;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		user = new User();
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTHD, Calendar.OCTOBER, DAY_OF_BIRTHD);
		dateOfBirthd = calendar.getTime();
	}

	@Test
	public void testGetFullName() {
		user.setFirstName("Sofiia");
		user.setLastName("Omelchenko");
		assertEquals("Omelchenko, Sofiia", user.getFullName());
	}

	@Test
	public void testGetFullNameWithoutFirstName() {
		user.setFirstName("Sofiia");
		try {
			user.getFullName();
			fail("IllegalStateException expecting");
		} catch (IllegalStateException e) {
			// TODO: handle exception
		}
	}

	@Test
	public void testGetAge() {
		user.setDateOfBirthd(dateOfBirthd);
		assertEquals(CURRENT_YEAR - YEAR_OF_BIRTHD, user.getAge());
	}
}
