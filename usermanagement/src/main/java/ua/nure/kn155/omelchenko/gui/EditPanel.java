package ua.nure.kn155.omelchenko.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DatabaseException;
import ua.nure.kn155.omelchenko.util.Messages;

public class EditPanel extends JPanel implements ActionListener {

	private MainFrame parentFrame;
	private JPanel buttonPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JPanel fieldPanel;
	private JTextField dateOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private Color bgColor;
	private User user;

	public EditPanel(MainFrame mainFrame) {
		parentFrame = mainFrame;
		user = parentFrame.getSelectedUser();
		initialize();
	}

	private void initialize() {
		this.setName("editPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Messages.getString("user_management.AddPanel4")); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("user_management.AddPanel5")); //$NON-NLS-1$
			cancelButton.setName("cancelButton"); //$NON-NLS-1$
			cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
			//firstNameField.setText(user.getFirstName());
		}
		return firstNameField;
	}

	private JTextField getDateOfBirthField() {
		if (dateOfBirthField == null) {
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$

			//DateFormat format = DateFormat.getDateInstance();
			// SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.mm.dd");
			// dateOfBirthField.setText(dataFormat.parse(format.format(user.getDateOfBirthd())));
			//dateOfBirthField.setText(format.format(user.getDateOfBirthd()));
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
			//lastNameField.setText(user.getLastName());
		}
		return lastNameField;
	}

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, Messages.getString("user_management.AddPanel1"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("user_management.AddPanel2"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("user_management.AddPanel3"), getDateOfBirthField()); //$NON-NLS-1$

		}
		return fieldPanel;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel();
		label.setText(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equalsIgnoreCase(e.getActionCommand())) {
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.mm.dd");
			try {
				user.setDateOfBirthd(dataFormat.parse(getDateOfBirthField().getText()));
			} catch (Exception e1) {
				getDateOfBirthField().setBackground(Color.RED);
				return;
			}
			try {
				parentFrame.getDao().update(user);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		this.setVisible(false);
		parentFrame.showBrowsPanel();

	}

}
