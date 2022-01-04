package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.Actions;
import models.Position;
import models.UserState;

public class AddUserDialog extends JDialog {

	private JTextField userDocumentField, userNameField, lastNameField;
	private JPasswordField passwordField;
	private JComboBox<UserState> userStateBox;
	private JComboBox<Position> positionBox;
	private JButton doneButton, cancelButton;
	private int width, height;
	private LabelPanel labelPanel;

	public AddUserDialog(ActionListener listener) {
		setTitle("AGREGAR USUARIO");
		locate();
		initComponents(listener);
	}

	private void initComponents(ActionListener listener) {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/SabroPanLogo.jpeg")).getImage());

		userDocumentField = new JTextField();
		userNameField = new JTextField();
		lastNameField = new JTextField();
		passwordField = new JPasswordField();

		JLabel documentLabel = new JLabel("(*)Documento", SwingConstants.CENTER);
		documentLabel.setFont(new Font("Roboto Mono", Font.BOLD, 12));
		JLabel userNameLabel = new JLabel("(*)Nombre", SwingConstants.CENTER);
		userNameLabel.setFont(new Font("Roboto Mono", Font.BOLD, 12));
		JLabel lastNameLabel = new JLabel("(*)Apellido", SwingConstants.CENTER);
		lastNameLabel.setFont(new Font("Roboto Mono", Font.BOLD, 12));
		JLabel passwordLabel = new JLabel("(*)Contraseña*", SwingConstants.CENTER);
		passwordLabel.setFont(new Font("Roboto Mono", Font.BOLD, 12));
		JLabel userStateLabel = new JLabel("(*)Estado*", SwingConstants.CENTER);
		userStateLabel.setFont(new Font("Roboto Mono", Font.BOLD, 12));
		JLabel positionLabel = new JLabel("(*)Cargo*", SwingConstants.CENTER);
		positionLabel.setFont(new Font("Roboto Mono", Font.BOLD, 12));

		userStateBox = new JComboBox<UserState>(UserState.values());
		userStateBox.setBackground(Color.decode("#ffffff"));
		userStateBox.setOpaque(false);
		positionBox = new JComboBox<Position>(Position.values());
		positionBox.setBackground(Color.decode("#ffffff"));
		positionBox.setOpaque(false);

		doneButton = new JButton("LISTO");
		doneButton.addActionListener(listener);
		doneButton.setActionCommand(Actions.DONE_ADD_USER_BUTTON.name());
		doneButton.setHorizontalTextPosition(SwingConstants.CENTER);
		doneButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doneButton.setBackground(Color.decode("#A4FAB2"));

		cancelButton = new JButton("CANCELAR");
		cancelButton.addActionListener(listener);
		cancelButton.setActionCommand(Actions.CANCEL_ADD_USER_BUTTON.name());
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.setBackground(Color.decode("#F65C3E"));

		labelPanel = new LabelPanel("/img/Users.png", width / 30, height / 12);
		labelPanel.setBackground(Color.decode("#FDD3CB"));
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridLayout(7, 1));
		JPanel documentPanel = new JPanel();
		documentPanel.setLayout(new GridLayout(2, 1));
		documentPanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		JPanel userNamePanel = new JPanel();
		userNamePanel.setLayout(new GridLayout(2, 1));
		userNamePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setLayout(new GridLayout(2, 1));
		lastNamePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new GridLayout(2, 1));
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		JPanel statePanel = new JPanel();
		statePanel.setLayout(new GridLayout(2, 1));
		statePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridLayout(2, 1));
		positionPanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2, width / 80, 0));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(height / 80, width / 10, height / 80, width / 10));

		documentPanel.add(documentLabel);
		documentPanel.add(userDocumentField);
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameField);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		statePanel.add(userStateLabel);
		statePanel.add(userStateBox);
		positionPanel.add(positionLabel);
		positionPanel.add(positionBox);
		buttonsPanel.add(doneButton);
		buttonsPanel.add(cancelButton);

		containerPanel.add(documentPanel);
		containerPanel.add(userNamePanel);
		containerPanel.add(lastNamePanel);
		containerPanel.add(passwordPanel);
		containerPanel.add(positionPanel);
		containerPanel.add(statePanel);
		containerPanel.add(buttonsPanel);

		opaqueAll(containerPanel);
		add(containerPanel);
		add(labelPanel, BorderLayout.NORTH);
	}

	private void opaqueAll(JComponent component) {
		if (component instanceof JPanel) {
			component.setBackground(Color.decode("#faafa0"));
		}
		if (component.getComponents().length > 0) {
			for (int i = 0; i < component.getComponents().length; i++) {
				if (component.getComponent(i) instanceof JPanel) {
					opaqueAll((JComponent) component.getComponent(i));
				}
			}
		}
	}

	private void locate() {
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(width / 2, 9 * height / 10);
		setLocation(width / 2 - this.getWidth() / 2, height / 50);
	}

	public String getUserNameFromField() {
		return userNameField.getText();
	}

	public String getUserDocumentFromField() {
		return userDocumentField.getText();
	}

	public String getUserLastNameFromField() {
		return lastNameField.getText();
	}

	public String getUserPasswordFromField() {
		char[] passwordVector = passwordField.getPassword();
		String password = "";
		for (int i = 0; i < passwordVector.length; i++) {
			password += passwordVector[i];
		}
		return password;
	}

	public UserState getUserStateFromField() {
		return (UserState) userStateBox.getSelectedItem();
	}

	public Position getUserPositionFromField() {
		return (Position) positionBox.getSelectedItem();
	}
	
	public void setDocumentFieldValue(String string) {
		userDocumentField.setText(string);
	}
	public void setUserNameFieldValue(String string) {
		userNameField.setText(string);
	}
	public void setLastNameFieldValue(String string) {
		lastNameField.setText(string);
	}
	
	public void setPasswordFieldValue(String string) {
		passwordField.setText(string);
	}
	
	public void setUserStateBoxValue(UserState userState) {
		switch (userState) {
		case ACTIVE:
			userStateBox.setSelectedItem(UserState.ACTIVE);
			break;
		case INACTIVE:
			userStateBox.setSelectedItem(UserState.INACTIVE);
			break;
		}
	}
	
	public void setPositionBoxValue(Position position) {
		switch (position) {
		case ADMIN:
			positionBox.setSelectedItem(Position.ADMIN);
			break;
		case SELLER:
			positionBox.setSelectedItem(Position.SELLER);
			break;
		}
	}
	
	public void setMode(boolean flag) {
		if(flag) {
			userDocumentField.setEnabled(true);
			setTitle("AGREGAR USUARIO");
			doneButton.setActionCommand(Actions.DONE_ADD_USER_BUTTON.name());
		}
		else {
			userDocumentField.setEnabled(false);
			setTitle("EDITAR USUARIO");
			doneButton.setActionCommand(Actions.DONE_EDIT_USER_BUTTON.name());
		}
	}

	public void clean() {
		userDocumentField.setText("");
		userNameField.setText("");
		lastNameField.setText("");
		passwordField.setText("");
		userStateBox.setSelectedIndex(0);
		positionBox.setSelectedIndex(0);
	}
}
