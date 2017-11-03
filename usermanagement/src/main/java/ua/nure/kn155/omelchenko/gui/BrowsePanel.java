package ua.nure.kn155.omelchenko.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DatabaseException;
import ua.nure.kn155.omelchenko.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {

	MainFrame parentFrame;
	private JPanel buttonPanel;
	private JScrollPane tablePanel;
	private JButton detailsButton;
	private JButton deleteButton;
	private JButton editButton;
	private JButton addButton;
	private JTable userTable;
	private UserTableModel model = null;

	public BrowsePanel(MainFrame frame) {
		parentFrame = frame;
		initialize();
	}

	private void initialize() {
		this.setName("browsePanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JButton getDetailsButton() {
		if (detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setText(Messages.getString("user_management.BrowsePanel1")); //$NON-NLS-1$
			detailsButton.setName("detailsButton"); //$NON-NLS-1$
			detailsButton.addActionListener(this);
			detailsButton.setActionCommand("details"); //$NON-NLS-1$
		}
		return detailsButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setName("deleteButton"); //$NON-NLS-1$
			deleteButton.setText(Messages.getString("user_management.BrowsePanel2")); //$NON-NLS-1$
			deleteButton.addActionListener(this);
			deleteButton.setActionCommand("delete"); //$NON-NLS-1$
		}
		return deleteButton;
	}

	private JButton getEditButton() {
		if (editButton == null) {
			editButton = new JButton();
			editButton.setName("editButton"); //$NON-NLS-1$
			editButton.setText(Messages.getString("user_management.BrowsePanel3")); //$NON-NLS-1$
			editButton.addActionListener(this);
			editButton.setActionCommand("edit"); //$NON-NLS-1$
		}
		return editButton;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText(Messages.getString("user_management.BrowsePanel4")); //$NON-NLS-1$
			addButton.setName("addButton"); //$NON-NLS-1$
			addButton.setActionCommand("add"); //$NON-NLS-1$
			addButton.addActionListener(this);
		}
		return addButton;
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getAddButton(), null);
			buttonPanel.add(getEditButton(), null);
			buttonPanel.add(getDeleteButton(), null);
			buttonPanel.add(getDetailsButton(), null);
		}
		return buttonPanel;
	}

	private JScrollPane getTablePanel() {
		if (tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if (userTable == null) {
			userTable = new JTable();
			userTable.setName("userTable"); //$NON-NLS-1$
			// userTable.setModel(new UserTableModel(new ArrayList<>()));
		}
		return userTable;
	}

	public void initTable() {
		try {
			model = new UserTableModel(parentFrame.getDao().findAll());
		} catch (Exception e) {
			model = new UserTableModel(new ArrayList<User>());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		getUserTable().setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("add".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
			this.setVisible(false);
			parentFrame.showAddPanel();
		}
	}

}
