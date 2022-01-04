package views;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;
import models.Expenditure;
import models.Product;
import models.Tree;
import models.User;
import observers.SearchProductListener;
import observers.SearchUsersListener;
import observers.SpinnerListener;

public class CardPanel extends JPanel {

	private MainPanel mainPanel;
	private UsersControlPanel usersControlPanel;
	private ProductsControlPanel productsControlPanel;
	private SellsControlPanel sellsControlPanel;
	private ExpendituresControlPanel expendituresControlPanel;
	private CardLayout cl;

	public CardPanel(ActionListener listener, SearchProductListener searchProductEngine, SpinnerListener spinnerListener, SearchUsersListener searchUserEngine, boolean admin) {
		cl = new CardLayout();
		setLayout(cl);
		mainPanel = new MainPanel(admin);
		usersControlPanel = new UsersControlPanel(listener, searchUserEngine);
		productsControlPanel = new ProductsControlPanel(listener, searchProductEngine);
		expendituresControlPanel = new ExpendituresControlPanel(listener);
		sellsControlPanel = new SellsControlPanel(listener, searchProductEngine, spinnerListener);
		add(mainPanel, "0");
		add(usersControlPanel, "1");
		add(productsControlPanel, "2");
		add(sellsControlPanel, "3");
		add(expendituresControlPanel, "4");
//		allWhite(this);
	}

	public void updateCardPanel(int panel) {
		switch (panel) {
		case 0:
			cl.show(this, "0");
			break;
		case 1:
			cl.show(this, "1");
			break;
		case 2:
			cl.show(this, "2");
			break;
		case 3:
			cl.show(this, "3");
			break;
		case 4:
			cl.show(this, "4");
			break;
		}
	}

	public void updateUsersListPanel(Tree<User> usersTree) {
		usersControlPanel.updateUsersListPanel(usersTree);
	}

	public void updateProductsListPanel(CopyOnWriteArrayList<Product> productsList) {
		productsControlPanel.updateProductsListPanel(productsList);
	}

	public String getSelectedProductCodeFromTable() {
		return productsControlPanel.getSelectedProductFromTable();
	}

	public void updateProductsOnTableOnSell(CopyOnWriteArrayList<Product> filteredProductsList) {
		sellsControlPanel.updateProductsTableOnSell(filteredProductsList);
	}

	public void updateStockTable(CopyOnWriteArrayList<Product> productsList) {
		sellsControlPanel.updateStockTable(productsList);
	}

	public String getOnSellProductCode() {
		return sellsControlPanel.getOnSellProductCode();
	}

	public void turnOfProductsTableListener() {
		productsControlPanel.turnOfProductsTableListener();
	}

	public void turnOnProductsTableListener() {
		productsControlPanel.turnOnProductsTableListener();
	}

	public String getselectedItemCodeOnSell() {
		return sellsControlPanel.getselectedItemCodeOnSell();
	}

	public void calculateSelectedItemTotalPrice(int amount) {
		sellsControlPanel.calculateSelectedItemTotalPrice(amount);
	}

	public void updateSellTotalOnSellTablePanel() {
		sellsControlPanel.updateSellTotalOnSellTablePanel();
	}

	public void updateItemsAmountOnSell() {
		sellsControlPanel.submitSellItemsAmount();
	}

	public void submitSellItemsAmount() {
		sellsControlPanel.submitSellItemsAmount();
	}

	public String getSelectedUserIdFromTable() {
		return usersControlPanel.getSelectedUserIdFromTable();
	}

	public void updateExpendituresTable(CopyOnWriteArrayList<Expenditure> expendituresList) {
		expendituresControlPanel.updateExpendituresTable(expendituresList);
	}

	public void addUserToUsersTable(User user) {
		usersControlPanel.addUserToUsersTable(user);
	}

}
