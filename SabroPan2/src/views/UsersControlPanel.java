package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.Actions;
import models.Tree;
import models.User;
import observers.SearchUsersListener;

public class UsersControlPanel extends JPanel {

	private UsersListPanel usersListPanel;
	private NiceButton backButton, addUserButton;
	private JTextField searchField;

	public UsersControlPanel(ActionListener listener, SearchUsersListener searchUserEngine) {
		initComponents(listener, searchUserEngine);
	}

	private void initComponents(ActionListener listener, SearchUsersListener searchUserEngine) {
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBorder(BorderFactory.createEmptyBorder(0, width / 50, 0, width / 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.decode("#ffe489"));

		usersListPanel = new UsersListPanel(listener);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel titleLabel = new JLabel("Módulo de Usuarios", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Roboto Mono", Font.BOLD, 40));

		searchField = new JTextField("Buscar");
		searchField.setFont(new Font("Roboto Mono", Font.BOLD, 14));
		searchField.setBorder(BorderFactory.createEmptyBorder(height / 180, 0, height / 180, width / 100));
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				usersListPanel.turnOffMouseListener();
				searchUserEngine.filterUsers(searchField.getText());
			};
		});
		backButton = new NiceButton("Regresar", "#000000", "#ffffff");
		backButton.setActionCommand(Actions.BACK_BUTTON.name());
		addUserButton = new NiceButton("Agregar Usuario", "#000000", "#ffffff");
		addUserButton.setActionCommand(Actions.ADD_USER_BUTTON.name());

		Image img = new ImageIcon(getClass().getResource("/img/Lens.png")).getImage();
		Image newimg = img.getScaledInstance(width / 40, height / 40, Image.SCALE_DEFAULT);

		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel(new ImageIcon(newimg));
		label.setBorder(BorderFactory.createEmptyBorder(height / 180, width / 100, height / 180, width / 100));
		p.setOpaque(false);

		p.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		p.add(label, BorderLayout.WEST);
		p.add(searchField, BorderLayout.CENTER);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.decode("#ffffff"));
		p1.add(p);

		gbc.insets = new Insets(height / 40, 0, 0, height / 40);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.1;// aquí xd
		gbc.gridwidth = 1;
		northPanel.add(backButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.4;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(titleLabel, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 0.7;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.CENTER;
		northPanel.add(addUserButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
//		gbc.weighty = 1;
		northPanel.add(p1, gbc);

		add(northPanel, BorderLayout.NORTH);
		add(usersListPanel);
	}

	public void updateUsersListPanel(Tree<User> usersTree) {
		usersListPanel.updateRowsTable(usersTree);
		usersListPanel.turnOnMouseListener();
	}

	public String getSelectedUserIdFromTable() {
		return usersListPanel.getSelectedRowUserCode();
	}

	public void addUserToUsersTable(User user) {
		usersListPanel.addUserToTable(user);
	}
}
