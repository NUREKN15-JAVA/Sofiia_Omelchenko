package ua.nure.kn155.omelchenko.gui;

import javax.swing.JPanel;
import ua.nure.kn155.omelchenko.User;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * DetailsPanel display all information about selected user
 * @author София
 *
 */

public class DetailsPanel extends JPanel implements ActionListener {

	private MainFrame parentFrame;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton okButton;
	private JLabel dateOfBirthdLabel;
	private JLabel lastNameLabel;
	private JLabel firstNameLabel;
	private final User user;
	private final String dateOfBirthd;
	private JLabel idLabel;
	
	public DetailsPanel(MainFrame mainFrame) {
		parentFrame = mainFrame;
		user = parentFrame.getSelectedUser();
		dateOfBirthd = DateFormat.getDateInstance().format(user.getDateOfBirthd());
		initialize();
	}

	private void initialize() {
		this.setName("detailsPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);

	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton());
		}
		return buttonPanel;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(4, 1));
			fieldPanel.add(getIdLabel());
			fieldPanel.add(getFirstNameLabel());
			fieldPanel.add(getLastNameLabel());
			fieldPanel.add(getDateOfBirthLabel());
		}
		return fieldPanel;
	}

	private JLabel getDateOfBirthLabel() {
		if (dateOfBirthdLabel == null) {
			dateOfBirthdLabel = new JLabel();
			dateOfBirthdLabel.setName("dateOfBirthdLabel"); //$NON-NLS-1$
			dateOfBirthdLabel.setText(dateOfBirthd);
		}
		return dateOfBirthdLabel;
	}

	private JLabel getLastNameLabel() {
		if (lastNameLabel == null) {
			lastNameLabel = new JLabel();
			lastNameLabel.setName("lastNameLabel"); //$NON-NLS-1$
			lastNameLabel.setText(user.getLastName());
		}
		return lastNameLabel;
	}

	private JLabel getFirstNameLabel() {
		if (firstNameLabel == null) {
			firstNameLabel = new JLabel();
			firstNameLabel.setName("firstNameLabel"); //$NON-NLS-1$
			firstNameLabel.setText(user.getFirstName());
		}
		return firstNameLabel;
	}
	private JLabel getIdLabel() {
		if (idLabel == null) {
			idLabel = new JLabel();
			idLabel.setName("idLabel"); //$NON-NLS-1$
			idLabel.setText(String.valueOf(user.getId()));
		}
		return idLabel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equalsIgnoreCase(e.getActionCommand())) {
			this.setVisible(false);
		}
		this.setVisible(false);
		parentFrame.showBrowsPanel();
	}

}
