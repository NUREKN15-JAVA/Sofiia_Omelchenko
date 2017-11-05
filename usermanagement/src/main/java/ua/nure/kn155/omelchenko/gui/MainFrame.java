package ua.nure.kn155.omelchenko.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.UserDao;
import ua.nure.kn155.omelchenko.util.Messages;

public class MainFrame extends JFrame {

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 500;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private AddPanel addPanel;
	private UserDao dao;
	private EditPanel editPanel;

	public MainFrame() {
		super();
		initialize();

	}

	private void initialize() {
		dao = DaoFactory.getInstance().getUserDao();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("user_management.MainFrame1")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
	}

	private BrowsePanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
			browsePanel.setName("browsePanel");
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}

	private JPanel getContentPanel() {
		contentPanel = new JPanel();
		return contentPanel;
	}

	public void showAddPanel() {
		showPanel(getAddPanel());
	}

	public void showBrowsPanel() {
		showPanel(getBrowsePanel());
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
			addPanel.setName("addPanel");
		}
		return addPanel;
	}

	public UserDao getDao() {
		return dao;
	}

	private JPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this);
		}
		((EditPanel) editPanel).resetFields();
		return editPanel;
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
	}

	public User getSelectedUser() {
		return ((BrowsePanel) browsePanel).getSelectedUser();
	}

	public void showEditPanel() {
		showPanel(getEditPanel());
	}

}
