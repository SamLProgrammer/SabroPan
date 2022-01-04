package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.Actions;
import models.Product;
import observers.SearchProductListener;
import observers.SpinnerListener;

public class SellsControlPanel extends JPanel {

	private ProductsSellsTablePanel sellsTablePanel;
	private StockProductsTable stockProductsTable;
	private JTextField searchField;
	private NiceButton sellButton, backButton;
	private JLabel totalPriceLabel;

	public SellsControlPanel(ActionListener listener, SearchProductListener searchProductEngine,
			SpinnerListener spinnerListener) {
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		setBorder(BorderFactory.createEmptyBorder(0, width / 50, 0, width / 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.decode("#ffe489"));

		sellsTablePanel = new ProductsSellsTablePanel(listener, spinnerListener);
		stockProductsTable = new StockProductsTable(listener);

		sellButton = new NiceButton("VENDER","#000000", "#ffffff");
		sellButton.addActionListener(listener);
		sellButton.setActionCommand(Actions.CONFIRM_SELL_BUTTON.name());
		sellButton.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		
		backButton = new NiceButton("Regresar","#000000", "#ffffff");
		backButton.addActionListener(listener);
		backButton.setActionCommand(Actions.BACK_BUTTON.name());
		backButton.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(sellButton);

		JPanel leftTablePanel = new JPanel(new GridBagLayout());
		leftTablePanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();

		searchField = new JTextField();
		searchField.setFont(new Font("Roboto Mono", Font.BOLD, height / 40));
		searchField.setBorder(BorderFactory.createEmptyBorder(height / 180, 0, height / 180, width / 100));
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				searchProductEngine.filterProductsOnStock(searchField.getText());
			};
		});

		Image img = new ImageIcon(getClass().getResource("/img/Lens.png")).getImage();
		Image newimg = img.getScaledInstance(width / 40, height / 40, Image.SCALE_DEFAULT);

		totalPriceLabel = new JLabel("TOTAL:");
		totalPriceLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 20));

		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel(new ImageIcon(newimg));
		label.setBorder(BorderFactory.createEmptyBorder(height / 180, width / 100, height / 180, width / 100));
		p.setOpaque(false);

		Image img2 = new ImageIcon(getClass().getResource("/img/AddItemToSell.png")).getImage();
		Image newimg2 = img2.getScaledInstance(width / 10, height / 10, Image.SCALE_DEFAULT);

		JLabel topSellsTableLabel = new JLabel(new ImageIcon(newimg2), SwingConstants.CENTER);
		topSellsTableLabel.setText("VENTA");
		topSellsTableLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		topSellsTableLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		topSellsTableLabel.setVerticalTextPosition(SwingConstants.BOTTOM);

		label.setPreferredSize(new Dimension(label.getPreferredSize().width, searchField.getPreferredSize().height));

		p.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		p.add(label, BorderLayout.WEST);
		p.add(searchField, BorderLayout.CENTER);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.decode("#ffffff"));
		p1.add(p);

		JLabel titleLabel = new JLabel("Módulo de Productos", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 15));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		gbc.insets = new Insets(height / 40, 0, 0, height / 40);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.6;
		gbc.weighty = 0.1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		leftTablePanel.add(p1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		leftTablePanel.add(topSellsTableLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.6;
		gbc.weighty = 1;
		gbc.gridheight = 4;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		leftTablePanel.add(stockProductsTable, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.gridwidth = 1;
		leftTablePanel.add(sellsTablePanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.weightx = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.1;
		leftTablePanel.add(buttonPanel, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weightx = 0.2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(height / 20, 0, 0, 0);
		leftTablePanel.add(backButton, gbc);

		gbc.insets = new Insets(height / 40, 0, 0, height / 40);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		buttonPanel.add(totalPriceLabel, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.2;
		gbc.weighty = 1;
		buttonPanel.add(sellButton, gbc);

		add(leftTablePanel, BorderLayout.CENTER);
	}

	public void updateStockTable(CopyOnWriteArrayList<Product> filteredProductsList) {
		stockProductsTable.updateRowsTable(filteredProductsList);
	}

	public void updateProductsTableOnSell(CopyOnWriteArrayList<Product> filteredProductsList) {
		sellsTablePanel.updateRowsTable(filteredProductsList);
	}

	public String getOnSellProductCode() {
		return stockProductsTable.getSelectedProductCode();
	}

	public String getselectedItemCodeOnSell() {
		return sellsTablePanel.getSelectedProductCode();
	}

	public void calculateSelectedItemTotalPrice(int amount) {
		sellsTablePanel.calculateItemTotalPrice(amount);
		updateSellTotalOnSellTablePanel();
	}

	public void updateSellTotalOnSellTablePanel() {
		totalPriceLabel.setText("TOTAL: " + sellsTablePanel.calculateSellTotal());
	}

	public void submitSellItemsAmount() {
		sellsTablePanel.submitSellItemsAmount();
	}
}
