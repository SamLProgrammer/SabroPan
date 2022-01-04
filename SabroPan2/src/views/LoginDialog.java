package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import controller.Actions;

public class LoginDialog extends JDialog {

	private JTextField userCodeField;
	private JPasswordField passwordField;
	private LabelPanel labelPanel;
	private JButton doneButton, cancelButton;
	private int width;
	private int height;

	public LoginDialog(ActionListener listener) {
		locate();
		initComponents(listener);
		setVisible(true);
	}

	private void locate() {
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(4 * width / 10, 6 * height / 12);
		setLocation(width / 2 - this.getWidth() / 2, height / 5);
	}

	private void initComponents(ActionListener listener) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/SabroPanLogo.jpeg")).getImage());

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridLayout(3, 1));
		containerPanel.setBorder(BorderFactory.createEmptyBorder(height / 20, width / 20, height / 20, width / 20));

		userCodeField = new JTextField();
		userCodeField.setBorder(BorderFactory.createEmptyBorder(height / 180, 0, height / 180, width / 100));
		passwordField = new JPasswordField();
		passwordField.setBorder(BorderFactory.createEmptyBorder(height / 180, 0, height / 180, width / 100));

		doneButton = new JButton("INGRESAR");
		doneButton.addActionListener(listener);
		doneButton.setActionCommand(Actions.LOGIN_BUTTON.name());
		doneButton.setHorizontalTextPosition(SwingConstants.CENTER);
		doneButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doneButton.setBackground(Color.decode("#A4FAB2"));

		cancelButton = new JButton("SALIR");
		cancelButton.addActionListener(listener);
		cancelButton.setActionCommand(Actions.LOGIN_CANCEL_BUTTON.name());
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.setBackground(Color.decode("#F65C3E"));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2, width / 80, 0));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(height / 40, width / 20, height / 40, width / 20));

		buttonsPanel.add(doneButton);
		buttonsPanel.add(cancelButton);

		Image img = new ImageIcon(getClass().getResource("/img/baker.png")).getImage();
		Image newimg = img.getScaledInstance(width / 40, height / 40, Image.SCALE_DEFAULT);
		Image img1 = new ImageIcon(getClass().getResource("/img/padlock.png")).getImage();
		Image newimg1 = img1.getScaledInstance(width / 40, height / 40, Image.SCALE_DEFAULT);

		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel(new ImageIcon(newimg));
		label.setBorder(BorderFactory.createEmptyBorder(height / 180, width / 100, height / 180, width / 100));
		p.setOpaque(false);

		label.setPreferredSize(new Dimension(label.getPreferredSize().width, userCodeField.getPreferredSize().height));

		p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		p.add(label, BorderLayout.WEST);
		p.add(userCodeField, BorderLayout.CENTER);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.decode("#ffffff"));
		p1.add(p);

		JPanel p2 = new JPanel(new BorderLayout());
		JLabel label1 = new JLabel(new ImageIcon(newimg1));
		label1.setBorder(BorderFactory.createEmptyBorder(height / 180, width / 100, height / 180, width / 100));
		p2.setOpaque(false);

		label1.setPreferredSize(
				new Dimension(label1.getPreferredSize().width, passwordField.getPreferredSize().height));

		p2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		p2.add(label1, BorderLayout.WEST);
		p2.add(passwordField, BorderLayout.CENTER);
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBackground(Color.decode("#ffffff"));
		p3.add(p2);

		labelPanel = new LabelPanel("/img/SabroPanLogo.jpeg", width / 15, height / 14);

		JLabel codeLabel = new JLabel("CODIGO USUARIO", SwingConstants.CENTER);
		JLabel passwordLabel = new JLabel("CONTRASEÑA", SwingConstants.CENTER);

		JPanel codePanel = new JPanel(new GridLayout(2, 1));
		JPanel passwordPanel = new JPanel(new GridLayout(2, 1));

		codePanel.add(codeLabel);
		codePanel.add(p1);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(p3);

		containerPanel.add(codePanel);
		containerPanel.add(passwordPanel);
		containerPanel.add(buttonsPanel);

		opaqueAll(containerPanel);
		add(containerPanel);
		add(labelPanel, BorderLayout.NORTH);
	}

	private void opaqueAll(JComponent component) {
		if (component instanceof JPanel) {
			component.setBackground(Color.decode("#ffffff"));
		}
		if (component.getComponents().length > 0) {
			for (int i = 0; i < component.getComponents().length; i++) {
				if (component.getComponent(i) instanceof JPanel) {
					opaqueAll((JComponent) component.getComponent(i));
				}
			}
		}
	}

	public String getUserPasswordFromField() {
		char[] passwordVector = passwordField.getPassword();
		String password = "";
		for (int i = 0; i < passwordVector.length; i++) {
			password += passwordVector[i];
		}
		return password;
	}
	
	public String getUserCodeFromField() {
		return userCodeField.getText();
	}
}
