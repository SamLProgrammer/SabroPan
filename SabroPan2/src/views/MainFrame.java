package views;

import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import models.Expenditure;
import models.Product;
import models.Tree;
import models.User;
import observers.SearchProductListener;
import observers.SearchUsersListener;
import observers.SpinnerListener;

public class MainFrame extends JFrame {

	private CardPanel cardPanel;

	public MainFrame(ActionListener listener, SearchProductListener searchProductEngine,
			SpinnerListener spinnerListener, SearchUsersListener searchUserEngine, boolean admin) {
		initComponents(listener, searchProductEngine, spinnerListener, searchUserEngine, admin);
		initProperties();
	}

	private void initComponents(ActionListener listener, SearchProductListener searchProductEngine,
			SpinnerListener spinnerListener, SearchUsersListener searchUserEngine, boolean admin) {
		cardPanel = new CardPanel(listener, searchProductEngine, spinnerListener, searchUserEngine, admin);
		add(cardPanel);
		setListenerRecursively(cardPanel, listener);
	}

	private void initProperties() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setIconImage(new ImageIcon(getClass().getResource("/img/SabroPanLogo.jpeg")).getImage());
		setVisible(true);
	}

	public void setListenerRecursively(JComponent component, ActionListener listener) {
		if (component instanceof JButton) {
			((JButton) component).addActionListener(listener);
		}
		if (component.getComponents().length > 0) {
			for (int i = 0; i < component.getComponents().length; i++) {
				if (component.getComponent(i) instanceof JComponent)
					setListenerRecursively((JComponent) component.getComponent(i), listener);
			}
		}
	}

	public void updateCardPanel(int panel) {
		cardPanel.updateCardPanel(panel);
	}

	public void updateUsersTable(Tree<User> usersTree) {
		cardPanel.updateUsersListPanel(usersTree);
	}

	public void updateProductsTable(CopyOnWriteArrayList<Product> productsList) {
		cardPanel.updateProductsListPanel(productsList);
	}

	public void updateProductsOnTableOnSell(CopyOnWriteArrayList<Product> filteredProductsList) {
		cardPanel.updateProductsOnTableOnSell(filteredProductsList);
	}

	public String getSelectedProductCodeFromTable() {
		return cardPanel.getSelectedProductCodeFromTable();
	}

	public void updateStockTable(CopyOnWriteArrayList<Product> productsList) {
		cardPanel.updateStockTable(productsList);
	}

	public String getOnSellProductCode() {
		return cardPanel.getOnSellProductCode();
	}

	public void turnOfProductsTableListener() {
		cardPanel.turnOfProductsTableListener();
	}

	public void turnOnProductsTableListener() {
		cardPanel.turnOnProductsTableListener();
	}

	public String getselectedItemCodeOnSell() {
		return cardPanel.getselectedItemCodeOnSell();
	}

	public void calculateSelectedItemTotalPrice(int amount) {
		cardPanel.calculateSelectedItemTotalPrice(amount);
	}

	public void updateSellTotalOnSellTablePanel() {
		cardPanel.updateSellTotalOnSellTablePanel();
	}

	public void updateItemsAmountOnSell() {
		cardPanel.updateItemsAmountOnSell();
	}

	public void submitSellItemsAmount() {
		cardPanel.submitSellItemsAmount();
	}

	public String getSelectedUserIdFromTable() {
		return cardPanel.getSelectedUserIdFromTable();
	}

	public void updateExpendituresTable(CopyOnWriteArrayList<Expenditure> expendituresList) {
		cardPanel.updateExpendituresTable(expendituresList);
	}

	public void addUserToUsersTable(User user) {
		cardPanel.addUserToUsersTable(user);
	}
}
