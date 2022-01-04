package views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.Actions;

public class MainOptionsPanel extends JPanel {

	private NiceButton usersButton, sellsButton, productsButton, spendingButton, reportsButton;

	public MainOptionsPanel(boolean admin) {
		initComponents(admin);
	}

	private void initComponents(boolean admin) {
		initBorders();
		setBackground(Color.decode("#FFFFFF"));
		setLayout(new GridLayout(2, 1));
		initButtons();

		JPanel upperPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		upperPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.setLayout(new GridLayout(1, 2));

		upperPanel.add(usersButton);
		upperPanel.add(productsButton);
		upperPanel.add(sellsButton);

		bottomPanel.add(spendingButton);
		bottomPanel.add(reportsButton);
		
		if(!admin) {
			usersButton.setEnabled(false);
			reportsButton.setEnabled(false);
		}

		add(upperPanel);
		add(bottomPanel);
	}

	private void initBorders() {
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBorder(BorderFactory.createEmptyBorder(height / 10, width / 10, height / 10, width / 10));
	}

	private void initButtons() {
		usersButton = new NiceButton("/img/Users.png", "Control de Usuarios", "#000000", "#CFB0F7");
		usersButton.setActionCommand(Actions.MAIN_USERS_BUTTON.name());
		productsButton = new NiceButton("/img/Cutlery.png", "Control de Productos", "#000000", "#F7B0D6");
		productsButton.setActionCommand(Actions.MAIN_PRODUCTS_BUTTON.name());
		sellsButton = new NiceButton("/img/AddItemToSell.png", "Venta", "#000000", "#F7F7B0");
		sellsButton.setActionCommand(Actions.MAIN_SELL_BUTTON.name());
		spendingButton = new NiceButton("/img/Expenditure.png", "Gastos", "#000000", "#B0F7BA");
		spendingButton.setActionCommand(Actions.MAIN_EXPENDITURES_BUTTON.name());
		reportsButton = new NiceButton("/img/Graph.png", "Reportes", "#000000", "#F7BEB0");
		reportsButton.setActionCommand(Actions.MAIN_REPORTS_BUTTON.name());
	}

}
