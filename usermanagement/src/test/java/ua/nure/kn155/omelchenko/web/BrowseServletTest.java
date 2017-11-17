package ua.nure.kn155.omelchenko.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class BrowseServletTest extends MockServletTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);

	}

	@Test
	public void testBrowse() {
		User user = new User(1000L, "Jhon", "Watson", new Date());
		List<User> list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();
		Collection<User> collection = (Collection<User>) getWebMockObjectFactory().getMockSession()
				.getAttribute("users");
		assertNotNull(collection);
		assertSame(list, collection);
	}

}
