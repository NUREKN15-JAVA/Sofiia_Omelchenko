package ua.nure.kn155.omelchenko.gui;

import java.awt.Component;
import java.awt.Container;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.MockDaoFactory;
import ua.nure.kn155.omelchenko.util.Messages;

public class MainFrameTest extends JFCTestCase {

	private Container mainFrame;
	private Mock mockUserDao;
	private List<User> users;
	private final String firstName = "Steven";
	private final String lastName = "Strange";
	private final Date now = new Date();
	private User addedUser = new User(firstName, lastName, now);
	private User expectedUser = new User(new Long(1), firstName, lastName, now);
	private final User realUser = new User(new Long(500), "Klark", "Kent", new Date());

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			users = new ArrayList<User>();
			users.add(realUser);
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();	
			//users = Collections.singletonList(expectedUser);
			mockUserDao.expectAndReturn("findAll",users);
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@Override
	protected void tearDown() throws Exception {
		mockUserDao.verify();
		mainFrame.setVisible(false);
		JFCTestHelper.cleanUp(this);
		super.tearDown();

	}

	private Component find(Class<?> componentClass, String name) {
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull(component);
		return component;
	}

	/**
	 * create constants for numbers
	 * 
	 */
	public void testBrowseControl() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("user_management.UserTableModel.0"), table.getColumnName(0));
		assertEquals(Messages.getString("user_management.UserTableModel.1"), table.getColumnName(1));
		assertEquals(Messages.getString("user_management.UserTableModel.2"), table.getColumnName(2));
		assertEquals(1,table.getRowCount());
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}

	/**
	 * add test cancel Button 
	 * add delete Button (with warning window (find warning
	 * window with helper)) 
	 * add Edit Button 
	 * add Detail 
	 * summary 6 methods
	 * 
	 * 
	 * 
	 */
	public void testAddUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton addButton = (JButton) find(JButton.class, "addButton");
		mockUserDao.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		find(JPanel.class, "addPanel");
		fillField(firstName, lastName, now);

		find(JButton.class, "cancelButton");
		JButton okButton = (JButton) find(JButton.class, "okButton");
		mockUserDao.expectAndReturn("create", addedUser, expectedUser);
		
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(2, table.getRowCount());
		//mockUserDao.verify();
	}

//	public void testCancelUser() {
//		String firstName = "Steven";
//		String lastName = "Strange";
//		Date now = new Date();
//
//		User user = new User(firstName, lastName, now);
//		User expectedUser = new User(new Long(1), firstName, lastName, now);
//
//		mockUserDao.expectAndReturn("create", user, expectedUser);
//
//		ArrayList<User> users = new ArrayList<User>(this.users);
//		users.add(expectedUser);
//		mockUserDao.expectAndReturn("findAll", users);
//
//		JTable table = (JTable) find(JTable.class, "userTable");
//		assertEquals(1, table.getRowCount());
//
//		JButton addButton = (JButton) find(JButton.class, "addButton");
//		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
//
//		find(JPanel.class, "addPanel");
//		fillField(firstName, lastName, now);
//
//		find(JButton.class, "cancelButton");
//		JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
//		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
//
//		find(JPanel.class, "browsePanel");
//		table = (JTable) find(JTable.class, "userTable");
//		assertEquals(2, table.getRowCount());
//		mockUserDao.verify();
//	}

	private void fillField(String firstName, String lastName, Date now) {
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.mm.dd");
		String data = dataFormat.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, data));
	}

}
