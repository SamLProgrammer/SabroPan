package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import models.ExpendituresManager;
import models.MeasureType;
import models.Position;
import models.Product;
import models.ProductType;
import models.ProductsManager;
import models.SellHandler;
import models.Tree;
import models.User;
import models.UserState;
import models.UsersManager;
import observers.SearchProductListener;
import observers.SearchUsersListener;
import observers.SpinnerListener;
import observers.TablePrinter;
import persistence.Deco;
import views.AddExpenditureDialog;
import views.AddItemsDialog;
import views.AddProductDialog;
import views.AddUserDialog;
import views.LoginDialog;
import views.MainFrame;

public class Controller implements ActionListener {

	private MainFrame mf;
	private ProductsManager productsManager;
	private ExpendituresManager expendituresManager;
	private UsersManager usersManager;
	private LoginDialog loginDialog;
	private AddUserDialog addUserDialog;
	private AddItemsDialog addItemDialog;
	private AddProductDialog addProductDialog;
	private AddExpenditureDialog addExpenditureDialog;
	private SearchProductListener searchProductsEngine;
	private SearchUsersListener searchUserEngine;
	private SpinnerListener spinnerListener;
	private TablePrinter<User> usersTablePrinter;
	private SellHandler sellHandler;
	private Deco deco;

	public Controller() {
		deco = new Deco();
		usersTablePrinter = new TablePrinter<User>(this);
		usersManager = new UsersManager(usersTablePrinter);
		if(usersManager.getUsersTree().size() != 0) {
			loginDialog = new LoginDialog(this);
		}
		else {
			initComponents(true);
		}
	}
	
	private void initComponents(boolean admin) {
		searchProductsEngine = new SearchProductListener(this);
		spinnerListener = new SpinnerListener(this);
		searchUserEngine = new SearchUsersListener(this);
		productsManager = new ProductsManager(deco);
		expendituresManager = new ExpendituresManager(deco);
		sellHandler = new SellHandler();
		addUserDialog = new AddUserDialog(this);
		addProductDialog = new AddProductDialog(this);
		addItemDialog = new AddItemsDialog(this);
		addExpenditureDialog = new AddExpenditureDialog(this);
		mf = new MainFrame(this, searchProductsEngine, spinnerListener, searchUserEngine, admin);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Actions.valueOf(e.getActionCommand())) {
		case MAIN_USERS_BUTTON:
			updateUsersTable(usersManager.getUsersTree());
			updateCardPanel(1);
			break;
		case MAIN_PRODUCTS_BUTTON:
			updateProductsTable(productsManager.getProductsList());
			updateCardPanel(2);
			break;
		case MAIN_EXPENDITURES_BUTTON:
			mf.updateExpendituresTable(expendituresManager.getExpendituresList());
			updateCardPanel(4);
			break;
		case MAIN_SELL_BUTTON:
			updateStockTable(productsManager.getProductsList());
			updateCardPanel(3);
			break;
		case MAIN_REPORTS_BUTTON:
			break;
		case ADD_USER_BUTTON:
			addUserDialog.clean();
			addUserDialog.setMode(true);
			addUserDialog.setVisible(true);
			break;
		case BACK_BUTTON:
			updateCardPanel(0);
			break;
		case DONE_ADD_USER_BUTTON:
			addNewUser();
			addUserDialog.setVisible(false);
			break;
		case CANCEL_ADD_USER_BUTTON:
			addUserDialog.setVisible(false);
			break;
		case ADD_PRODUCT_BUTTON:
			addProductDialog.clean();
			addProductDialog.setVisible(true);
			break;
		case DONE_ADD_PRODUCT_BUTTON:
			addNewProduct();
			addProductDialog.setVisible(false);
			break;
		case CANCEL_ADD_PRODUCT_BUTTON:
			addProductDialog.setVisible(false);
			break;
		case ADD_ITEM_BUTTON:
			addItemDialog.clean();
			addItemDialog.setProductCode(mf.getSelectedProductCodeFromTable());
			addItemDialog.setProductName(productsManager.findProduct(mf.getSelectedProductCodeFromTable()).getName());
			addItemDialog.updateLabelInfo();
			addItemDialog.setVisible(true);
			break;
		case CANCEL_ADD_ITEM_BUTTON:
			addItemDialog.setVisible(false);
			break;
		case DONE_ADD_ITEM_BUTTON:
			addItemsToProduct();
			break;
		case ADD_ITEM_TO_CAR:
			addProductToSellTable();
			break;
		case CONFIRM_SELL_BUTTON:
			sellProducts();// error
			break;
		case REMOVE_PRODUCT_FROM_SELL:
			removeProductFromSell();
			break;
		case EDIT_USER_BUTTON:
			prepareEditUser();
			break;
		case EDIT_PRODUCT_BUTTON:
			prepareEditProduct();
			break;
		case DONE_EDIT_PRODUCT_BUTTON:
			editProduct();
			break;
		case DONE_EDIT_USER_BUTTON:
			editUser();
			break;
		case ADD_EXPENDITURE_BUTTON:
			addExpenditureDialog.setVisible(true);
			break;
		case CANCEL_ADD_EXPENDITURE_BUTTON:
			addExpenditureDialog.setVisible(false);
			break;
		case DONE_ADD_EXPENDITURE_BUTTON:
			System.out.println("yes");
			expendituresManager.addExpenditure(expendituresManager.createExpenditure(addExpenditureDialog.getExpenditureNameFromField(), addExpenditureDialog.getExpenditurePriceFromField()));
			mf.updateExpendituresTable(expendituresManager.getExpendituresList());
			addExpenditureDialog.setVisible(false);
			break;
		case LOGIN_BUTTON:
			validLogin();
			break;
		case LOGIN_CANCEL_BUTTON:
			loginDialog.dispose();
			break;
		case DELETE_USER_BUTTON:
			removeUser();
			break;
		}
	}

	private void removeUser() {
		usersManager.removeUser(mf.getSelectedUserIdFromTable());
		mf.updateUsersTable(usersManager.getUsersTree());
	}

	private void validLogin() {
		System.out.println("aja");
		if(usersManager.validUserLogin(loginDialog.getUserCodeFromField(),
				loginDialog.getUserPasswordFromField())) {
			switch (usersManager.findUser(loginDialog.getUserCodeFromField()).getPosition()) {
			case ADMIN:
				initComponents(true);
				System.out.println("admin");
				break;
			case SELLER:
				initComponents(false);
				System.out.println("seller");
				break;
			}
		}
		loginDialog.dispose();
	}

	private void editUser() {
		usersManager.editUser(addUserDialog.getUserDocumentFromField(), addUserDialog.getUserNameFromField(),
				addUserDialog.getUserLastNameFromField(), 
				addUserDialog.getUserPasswordFromField(),
				addUserDialog.getUserStateFromField(),
				addUserDialog.getUserPositionFromField());
		mf.updateUsersTable(usersManager.getUsersTree());
		addUserDialog.clean();
		addUserDialog.setVisible(false);
		addUserDialog.setModal(true);
	}

	private void prepareEditUser() {
		addUserDialog.setMode(false);
		User user = usersManager.findUser(mf.getSelectedUserIdFromTable());
		addUserDialog.setDocumentFieldValue(user.getId());
		addUserDialog.setUserNameFieldValue(user.getName());
		addUserDialog.setLastNameFieldValue(user.getLastName());
		addUserDialog.setUserStateBoxValue(user.getUserState());
		addUserDialog.setPositionBoxValue(user.getPosition());
		addUserDialog.setPasswordFieldValue(user.getPassword());
		addUserDialog.setVisible(true);
	}

	private void editProduct() {
		productsManager.editProduct(addProductDialog.getProductCodeOnEditing(),
				addProductDialog.getProductNameFromField(), addProductDialog.getProductPriceFromField(),
				addProductDialog.getProductMeasureFromField(), addProductDialog.getMeasureTypeSelected(),
				addProductDialog.getProductTypeFromField());
		mf.updateProductsTable(productsManager.getProductsList());
		addProductDialog.clean();
		addProductDialog.setVisible(false);
		addProductDialog.setMode(true);
	}

	private void prepareEditProduct() {
		addProductDialog.setMode(false);
		Product product = productsManager.findProduct(mf.getSelectedProductCodeFromTable());
		addProductDialog.setProductCodeOnEditing(product.getId());
		addProductDialog.setNameFieldValue(product.getName());
		addProductDialog.setPriceFieldValue(product.getPrice());
		addProductDialog.setMeasureFieldValue(product.getMeasure());
		addProductDialog.setMeasureTypeFieldValue(product.getMeasureType());
		addProductDialog.setProductTypeValue(product.getProductType());
		addProductDialog.setVisible(true);
	}

	private void removeProductFromSell() {
		sellHandler.removeProductOnSell(getSelectedItemCodeFromSellTable());
		mf.submitSellItemsAmount();
		mf.updateProductsOnTableOnSell(sellHandler.getProductsOnSellList());
		mf.updateSellTotalOnSellTablePanel();
	}

	private void sellProducts() {
		mf.updateItemsAmountOnSell();
		for (Product product : sellHandler.getProductsOnSellList()) {
			productsManager.sellItem(product.getId(), product.getItemsList().size());
		}
		System.out.println(sellHandler.getBillStructureFromActualSell());
		sellHandler = new SellHandler();
		mf.updateStockTable(productsManager.getProductsList());
		mf.updateProductsOnTableOnSell(sellHandler.getProductsOnSellList());
		mf.updateSellTotalOnSellTablePanel();
	}

	private void addProductToSellTable() {
		sellHandler.addProductOnSell(productsManager.copyProduct(productsManager.findProduct(getOnSellProductCode())));
		mf.submitSellItemsAmount();
		mf.updateProductsOnTableOnSell(sellHandler.getProductsOnSellList());
	}

	private String getOnSellProductCode() {
		return mf.getOnSellProductCode();
	}

	private void addNewProduct() {
		productsManager
				.addProduct(productsManager.createProduct(getProductNameFromDialog(), getProductMeasureFromDialog(),
						getProductMeasureTypeFromDialog(), getProducPriceFromDialog(), getProductTypeFromField()));
		updateProductsTable(productsManager.getProductsList());
	}

	private void addNewUser() {
		usersManager.addUser(usersManager.createUser(getUserDocumentFromDialog(), getUserNameFromDialog(),
				getUserLastNameFromDialog(), getUserPasswordFromDialog(), getUserStateFromDialog(),
				getUserPositionFromDialog()));
		updateUsersTable(usersManager.getUsersTree());
	}

	private void addItemsToProduct() {
		productsManager.addItem(getSelectedProductCodeFromTable(), addItemDialog.getItemAmountFromField());
		addItemDialog.setVisible(false);
		addItemDialog.clean();
		mf.updateProductsTable(productsManager.getProductsList());
	}

	private void updateCardPanel(int panel) {
		mf.updateCardPanel(panel);
	}

	private void updateStockTable(CopyOnWriteArrayList<Product> productsList) {
		mf.updateStockTable(productsList);
	}

	private void updateUsersTable(Tree<User> usersTree) {
		mf.updateUsersTable(usersTree);
	}

	private void updateProductsTable(CopyOnWriteArrayList<Product> productsList) {
		mf.updateProductsTable(productsList);
	}

	public String getUserDocumentFromDialog() {
		return addUserDialog.getUserDocumentFromField();
	}

	public String getUserNameFromDialog() {
		return addUserDialog.getUserNameFromField();
	}

	public String getUserLastNameFromDialog() {
		return addUserDialog.getUserLastNameFromField();
	}

	public String getUserPasswordFromDialog() {
		return addUserDialog.getUserPasswordFromField();
	}

	public UserState getUserStateFromDialog() {
		return addUserDialog.getUserStateFromField();
	}

	public Position getUserPositionFromDialog() {
		return addUserDialog.getUserPositionFromField();
	}

	public String getProductNameFromDialog() {
		return addProductDialog.getProductNameFromField();
	}

	public int getProducPriceFromDialog() {
		return addProductDialog.getProductPriceFromField();
	}

	public float getProductMeasureFromDialog() {
		return addProductDialog.getProductMeasureFromField();
	}

	public MeasureType getProductMeasureTypeFromDialog() {
		return addProductDialog.getMeasureTypeSelected();
	}

	public ProductType getProductTypeFromField() {
		return addProductDialog.getProductTypeFromField();
	}

	public String getSelectedProductCodeFromTable() {
		return mf.getSelectedProductCodeFromTable();
	}

	public void filterProducts(String subString) {
		updateProductsTable(productsManager.filteredProductsList(subString));
	}

	public void filterProductsOnStock(String subString) {
		updateStockTable(productsManager.filteredProductsList(subString));
	}

	public void calculateOnSellItemTotalPrice(int amount) {
		mf.calculateSelectedItemTotalPrice(amount);
	}

	private String getSelectedItemCodeFromSellTable() {
		return mf.getselectedItemCodeOnSell();
	}

	public int getExistencesFromProduct(String productCode) {
		return productsManager.findProduct(productCode).getItemsList().size();
	}

	public void updateSellTotalOnSellTablePanel() {
		mf.updateSellTotalOnSellTablePanel();
	}

	public void updateProductOnSellItemsAmount(String code, String amount) {
		sellHandler.updateItemAmountOnSell(code, Integer.parseInt(amount));
	}

	public void filterUsers(String subString) {
		mf.updateUsersTable(usersManager.filteredProductsList(subString));
	}
	
	public void updateExpendituresTable() {
		mf.updateExpendituresTable(expendituresManager.getExpendituresList());
	}
	
	public void addUserToUsersTable(User user) {
		mf.addUserToUsersTable(user);
	}
}
