package ua.nure.kn155.omelchenko.gui;

import java.awt.Component;
import java.awt.Container;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.MockDaoFactory;
import ua.nure.kn155.omelchenko.util.Messages;

public class MainFrameTest extends JFCTestCase {

	private static final int REAL_USER_LIST_POSITION = 0;
	private static final int LASTNAME_COLUMN = 2;
	private static final int FIRSTNAME_COLUMN = 1;
	private static final int ID_COLUMN = 0;

	private Container mainFrame;
	private Mock mockUserDao;
	private List<User> users;
	/**
	 * Constants FIRSTNAME, LASTNAME, NOW are used for creating new users and for
	 * filling fields in userTable
	 * REAL_USER_LIST_POSITION contains position of REAL_USER in the users list
	 */
	private final String FIRSTNAME = "Steven";
	private final String LASTNAME = "Strange";
	private final Date NOW = new Date();

	/**
	 * Constant REAL_USER is an available instance of class User in list user
	 * EXPECTED_USER and ADDED_USER are used in testAddUser() UPDATED_USER is used
	 * in testEditUser()
	 */
	private final User REAL_USER = new User(new Long(500), "Klark", "Kent", NOW);
	private final User ADDED_USER = new User(FIRSTNAME, LASTNAME, NOW);
	private final User EXPECTED_USER = new User(new Long(1), FIRSTNAME, LASTNAME, NOW);
	private final User UPDATED_USER = new User(new Long(500), "Max", "Kent", NOW);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			users = new ArrayList<User>();
			users.add(REAL_USER);
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			mockUserDao.expectAndReturn("findAll", users);
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@Override
	protected void tearDown() throws Exception {
		mainFrame.setVisible(false);
		JFCTestHelper.cleanUp(this);
		mockUserDao.verify();
		super.tearDown();
	}

	private Component find(Class<?> componentClass, String name) {
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull(component);
		return component;
	}

	public void testBrowseControl() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("user_management.UserTableModel.0"), table.getColumnName(ID_COLUMN));
		assertEquals(Messages.getString("user_management.UserTableModel.1"), table.getColumnName(FIRSTNAME_COLUMN));
		assertEquals(Messages.getString("user_management.UserTableModel.2"), table.getColumnName(LASTNAME_COLUMN));
		assertEquals(1, table.getRowCount());
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}

	/**
	 * Test for class AddPanel. Create new user and add it to userTable.
	 */
	public void testAddUser() {
		ArrayList<User> users = new ArrayList<User>(this.users);

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		mockUserDao.expectAndReturn("findAll", users);
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		find(JPanel.class, "addPanel");
		fillField(FIRSTNAME, LASTNAME, NOW);

		find(JButton.class, "cancelButton");
		JButton okButton = (JButton) find(JButton.class, "okButton");

		mockUserDao.expectAndReturn("create", ADDED_USER, EXPECTED_USER);
		users.add(EXPECTED_USER);

		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(2, table.getRowCount());
		mockUserDao.verify();
	}

	/**
	 * Test for class AddPanel. Cancellation of new user creation.
	 */

	public void testCancelAddUser() {
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		find(JPanel.class, "addPanel");
		find(JButton.class, "okButton");

		JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

		DialogFinder dialogFinder = new DialogFinder("Cancel confirm");
		dialogFinder.findAll();

		AbstractButtonFinder abf = new AbstractButtonFinder("Yes");
		JButton yesButton = (JButton) abf.find();
		getHelper().enterClickAndLeave(new MouseEventData(this, yesButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		mockUserDao.verify();
	}

	/**
	 * Test for class EditPanel. Changing user's data and store it.
	 */
	public void testEditUser() {
		ArrayList<User> updatedUsers = new ArrayList<User>();
		updatedUsers.add(UPDATED_USER);
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		mockUserDao.expectAndReturn("find", REAL_USER.getId(), users.get(REAL_USER_LIST_POSITION));
		mockUserDao.expect("update", UPDATED_USER);

		JButton editButton = (JButton) find(JButton.class, "editButton");

		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

		find(JPanel.class, "editPanel");
		fillField(UPDATED_USER.getFirstName(), UPDATED_USER.getLastName(), UPDATED_USER.getDateOfBirthd());
		find(JButton.class, "cancelButton");

		mockUserDao.expectAndReturn("findAll", users);

		JButton okButton = (JButton) find(JButton.class, "okButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		assertEquals(UPDATED_USER.getId(), table.getValueAt(0, 0));
		assertEquals(UPDATED_USER.getFirstName(), table.getValueAt(0, 1));
		assertEquals(UPDATED_USER.getLastName(), table.getValueAt(0, 2));
		mockUserDao.verify();
	}

	/**
	 * Test for class EditPanel. Cancellation of user's data editing.
	 */
	public void testCancelEditUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton addButton = (JButton) find(JButton.class, "editButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

		find(JPanel.class, "editPanel");
		find(JButton.class, "okButton");

		JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

		DialogFinder dialogFinder = new DialogFinder("Cancel confirm");
		dialogFinder.findAll();

		mockUserDao.expectAndReturn("findAll", users);

		AbstractButtonFinder abf = new AbstractButtonFinder("Yes");
		JButton yesButton = (JButton) abf.find();
		getHelper().enterClickAndLeave(new MouseEventData(this, yesButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		assertEquals(REAL_USER.getId(), table.getValueAt(0, 0));
		assertEquals(REAL_USER.getFirstName(), table.getValueAt(0, 1));
		assertEquals(REAL_USER.getLastName(), table.getValueAt(0, 2));
		mockUserDao.verify();
	}

	/**
	 * Test for deleteButton on the BrowsePane. Delete selected user from userTable.
	 */
	public void testDeleteUser() {
		ArrayList<User> deleteUsers = new ArrayList<>();
		deleteUsers.add(REAL_USER);

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		mockUserDao.expectAndReturn("find", REAL_USER.getId(), deleteUsers.get(REAL_USER_LIST_POSITION));
		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");

		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

		DialogFinder dialogFinder = new DialogFinder("Delete confirm");
		dialogFinder.findAll();

		mockUserDao.expect("delete", deleteUsers.get(REAL_USER_LIST_POSITION));
		mockUserDao.expectAndReturn("findAll", deleteUsers);

		AbstractButtonFinder abf = new AbstractButtonFinder("Yes");
		JButton yesButton = (JButton) abf.find();

		deleteUsers.remove(REAL_USER);

		getHelper().enterClickAndLeave(new MouseEventData(this, yesButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(0, table.getRowCount());
		mockUserDao.verify();
	}

	/**
	 * Test for deleteButton on the BrowsePane. Cancel selected user deletion from
	 * user table.
	 */
	public void testCancelDeleteUser() {
		ArrayList<User> deleteUsers = new ArrayList<>();
		deleteUsers.add(REAL_USER);

		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");

		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

		DialogFinder dialogFinder = new DialogFinder("Delete confirm");
		dialogFinder.findAll();

		AbstractButtonFinder abf = new AbstractButtonFinder("No");
		JButton noButton = (JButton) abf.find();

		getHelper().enterClickAndLeave(new MouseEventData(this, noButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		mockUserDao.verify();

	}

	/**
	 * test for DetailsPanel. Viewing user data.
	 */
	public void testDetailsUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());

		JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		mockUserDao.expectAndReturn("find", REAL_USER.getId(), users.get(REAL_USER_LIST_POSITION));

		getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

		find(JPanel.class, "detailsPanel");
		JLabel idLabel = (JLabel) find(JLabel.class, "idLabel");
		JLabel firstNameLabel = (JLabel) find(JLabel.class, "firstNameLabel");
		JLabel lastNameLabel = (JLabel) find(JLabel.class, "lastNameLabel");
		JLabel dateOfBirthLabel = (JLabel) find(JLabel.class, "dateOfBirthdLabel");
		DateFormat formatter = DateFormat.getDateInstance();

		assertEquals(REAL_USER.getId(), Long.valueOf(idLabel.getText()));
		assertEquals(REAL_USER.getFirstName(), firstNameLabel.getText());
		assertEquals(REAL_USER.getLastName(), lastNameLabel.getText());
		assertEquals(formatter.format(REAL_USER.getDateOfBirthd()), dateOfBirthLabel.getText());

		JButton okButton = (JButton) find(JButton.class, "okButton");
		mockUserDao.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
		mockUserDao.verify();

	}

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
