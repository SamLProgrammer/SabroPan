package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.Actions;

public class AddItemsDialog extends JDialog{
	
	private JLabel itemInfoLabel;
	private JTextField amountField;
	private JButton okButton, cancelButton;
	private int width, height;
	private String productCode, productName;
	
	public AddItemsDialog(ActionListener listener) {
		locate();
		initComponents(listener);
		setModal(true);
	}
	
	private void initComponents(ActionListener listener) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/SabroPanLogo.jpeg")).getImage());
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		itemInfoLabel = new JLabel("", SwingConstants.CENTER);
		itemInfoLabel.setOpaque(true);
		itemInfoLabel.setBackground(Color.decode("#FDD3CB"));
		
		JLabel amountLabel = new JLabel("Cantidad",SwingConstants.CENTER);
		amountLabel.setFont(new Font("Roboto Mono", Font.BOLD, height/25));
		amountLabel.setBackground(Color.decode("#ffe489"));
		amountLabel.setOpaque(true);
		amountLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		amountLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		amountField = new JTextField();
		amountField.setBorder(BorderFactory.createEmptyBorder(height/70, width/80, height/70, width/80));
		
		okButton = new JButton("Agregar");
		okButton.setFont(new Font("Roboto Mono", Font.BOLD, height / 40));
		okButton.addActionListener(listener);
		okButton.setActionCommand(Actions.DONE_ADD_ITEM_BUTTON.name());
		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		okButton.setBackground(Color.decode("#A4FAB2"));
		
		cancelButton = new JButton("Cancelar");
		cancelButton.setFont(new Font("Roboto Mono", Font.BOLD, height / 40));
		cancelButton.addActionListener(listener);
		cancelButton.setActionCommand(Actions.CANCEL_ADD_ITEM_BUTTON.name());
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.setBackground(Color.decode("#F65C3E"));
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, height/30, width/50));
		buttonsPanel.setBackground(Color.orange);
		JPanel amountFieldPanel = new JPanel(new BorderLayout());
	
		amountFieldPanel.add(amountLabel, BorderLayout.NORTH);
		amountFieldPanel.setBackground(Color.blue);
		amountFieldPanel.add(amountField);
		amountFieldPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),BorderFactory.createEmptyBorder(height/80, width/10, height/80, width/10)));
		amountField.setFont(new Font("Roboto Mono", Font.BOLD, height/15));
		
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		containerPanel.add(itemInfoLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		containerPanel.add(amountFieldPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.CENTER;
		containerPanel.add(buttonsPanel,gbc);
		
		add(containerPanel);
		opaqueAll(containerPanel);
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
	
	public void clean() {
		itemInfoLabel.setText("");
	}
	
	public void updateLabelInfo() {
		itemInfoLabel.setText("<html><p style =\"color: #333; text-align: center; font-family: Roboto Mono; "
				+ "font-size: 16px; font-weight: 700; letter-spacing: -.4px;\">"
				+ "Código: " + productCode +"<br>Producto: " + productName + "</br></p></html>");
	}
	
	private void locate() {
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(width/3, height/2);
		setLocation(width/2 - this.getWidth()/2, height/4);
	}
	
	public int getItemAmountFromField() {
		return Integer.valueOf(amountField.getText());
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
