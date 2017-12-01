package ua.nure.kn155.omelchenko.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.util.Messages;

public class UserTableModel extends AbstractTableModel {
	private List<User> users = null;
	private final String[] COLUMN_NAMES = { Messages.getString("user_management.UserTableModel.0"), //$NON-NLS-1$
			Messages.getString("user_management.UserTableModel.1"), //$NON-NLS-1$
			Messages.getString("user_management.UserTableModel.2") }; //$NON-NLS-1$
	private final Class[] COLUMN_CLASSES = { Long.class, String.class, String.class };

	public UserTableModel(Collection<User> users) {
		this.users = new ArrayList<User>(users);
	}

	public void addUsers(Collection<User> users) {
		this.users.addAll(users);
	}

	public void clearUsers() {
		this.users = new ArrayList<User>();
	}

	public int getRowCount() {
		return users.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
}
