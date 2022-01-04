package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.Actions;
import models.MeasureType;
import models.ProductType;

public class AddProductDialog extends JDialog {

	private JTextField productNameField, productPriceField, measureField;
	private JComboBox<ProductType> productTypeBox;
	private MyCheckBox poundsCheckBox, kilogramsCheckBox, gramsCheckBox, miligramsCheckBox, litersCheckBox,
			miliLitersCheckBox;
	private MyCheckBox[] checkBoxVector = new MyCheckBox[6];
	private JButton doneButton, cancelButton;
	private int width, height;
	private LabelPanel labelPanel;
	private String productCodeOnEditing;

	public AddProductDialog(ActionListener listener) {
		setTitle("¡AGREGAR PRODUCTO!");
		locate();
		initComponents(listener);
	}

	private void initComponents(ActionListener listener) {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/SabroPanLogo.jpeg")).getImage());

		productNameField = new JTextField();
		productNameField.setHorizontalAlignment(SwingConstants.CENTER);
		productNameField.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		productPriceField = new JTextField();
		productPriceField.setHorizontalAlignment(SwingConstants.CENTER);
		productPriceField.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		measureField = new JTextField();
		measureField.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		measureField.setHorizontalAlignment(SwingConstants.CENTER);
		measureField.setEnabled(false);

		JLabel productNameLabel = new JLabel("Nombre", SwingConstants.CENTER);
		productNameLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		JLabel productPriceLabel = new JLabel("Precio", SwingConstants.CENTER);
		productPriceLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		JLabel productTypeLabel = new JLabel("Tipo", SwingConstants.CENTER);
		productTypeLabel.setFont(new Font("Roboto MOno", Font.BOLD, height / 30));
		JLabel measureLabel = new JLabel("Medida", SwingConstants.CENTER);
		measureLabel.setFont(new Font("Roboto MOno", Font.BOLD, height / 30));
		JLabel measureTypeLabel = new JLabel("Tipo de Medida", SwingConstants.CENTER);
		measureTypeLabel.setFont(new Font("Roboto MOno", Font.BOLD, height / 30));

		productTypeBox = new JComboBox<ProductType>(ProductType.values());
		productTypeBox.setBackground(Color.decode("#ffffff"));
		productTypeBox.setFont(new Font("Roboto MOno", Font.BOLD, height / 40));
		((JLabel) productTypeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		productTypeBox.setFocusable(false);
		productTypeBox.setOpaque(false);

		gramsCheckBox = new MyCheckBox("g (gramos)", MeasureType.GRAMOS.name());
		poundsCheckBox = new MyCheckBox("lb (libras)", MeasureType.LIBRAS.name());
		kilogramsCheckBox = new MyCheckBox("Kg (Kilogramos)", MeasureType.KILOGRAMOS.name());
		miligramsCheckBox = new MyCheckBox("mg (miligramos)", MeasureType.MILIGRAMOS.name());
		litersCheckBox = new MyCheckBox("L (litros)", MeasureType.LITROS.name());
		miliLitersCheckBox = new MyCheckBox("mL (mililitros)", MeasureType.MILILITROS.name());

		initCheckBoxesEventsListener();

		doneButton = new JButton("LISTO");
		doneButton.addActionListener(listener);
		doneButton.setActionCommand(Actions.DONE_ADD_PRODUCT_BUTTON.name());
		doneButton.setHorizontalTextPosition(SwingConstants.CENTER);
		doneButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doneButton.setBackground(Color.decode("#A4FAB2"));

		cancelButton = new JButton("CANCELAR");
		cancelButton.addActionListener(listener);
		cancelButton.setActionCommand(Actions.CANCEL_ADD_PRODUCT_BUTTON.name());
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.setBackground(Color.decode("#F65C3E"));

		labelPanel = new LabelPanel("/img/Cutlery.png", width / 30, height / 12);
		labelPanel.setBackground(Color.decode("#FDD3CB"));
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridLayout(5, 1));
		JPanel productNamePanel = new JPanel();
		productNamePanel.setLayout(new GridLayout(2, 1));
		productNamePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 10, 0, width / 10));
		JPanel productPricePanel = new JPanel();
		productPricePanel.setLayout(new GridLayout(2, 1));
		productPricePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 10, 0, width / 10));
		JPanel productTypePanel = new JPanel();
		productTypePanel.setLayout(new GridLayout(2, 1));
		productTypePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 10, 0, width / 10));
		JPanel containerMeasurePanel = new JPanel();
		containerMeasurePanel.setLayout(new GridLayout(1, 2));
		containerMeasurePanel
				.setBorder(BorderFactory.createEmptyBorder(height / 40, width / 10, height / 40, width / 10));
		JPanel measurePanel = new JPanel();
		measurePanel.setLayout(new GridLayout(2, 1));
		JPanel measureTypePanel = new JPanel();
		measureTypePanel.setLayout(new GridLayout(2, 3));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2, width / 80, 0));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(height / 40, width / 10, height / 40, width / 10));

		productNamePanel.add(productNameLabel);
		productNamePanel.add(productNameField);
		productPricePanel.add(productPriceLabel);
		productPricePanel.add(productPriceField);
		productTypePanel.add(productTypeLabel);
		productTypePanel.add(productTypeBox);
		measurePanel.add(measureLabel);
		measurePanel.add(measureField);
		measureTypePanel.add(miligramsCheckBox);
		measureTypePanel.add(gramsCheckBox);
		measureTypePanel.add(poundsCheckBox);
		measureTypePanel.add(kilogramsCheckBox);
		measureTypePanel.add(litersCheckBox);
		measureTypePanel.add(miliLitersCheckBox);
		containerMeasurePanel.add(measurePanel);
		containerMeasurePanel.add(measureTypePanel);
		buttonsPanel.add(doneButton);
		buttonsPanel.add(cancelButton);

		containerPanel.add(productNamePanel);
		containerPanel.add(productPricePanel);
		containerPanel.add(productTypePanel);
//		containerPanel.add(measurePanel);
		containerPanel.add(containerMeasurePanel);
		containerPanel.add(buttonsPanel);

		opaqueAll(containerPanel);
		add(containerPanel);
		add(labelPanel, BorderLayout.NORTH);
		requestFocus();
		requestFocus(true);
		requestFocusInWindow();
		requestFocusInWindow(true);
	}

	private void initCheckBoxesEventsListener() {
		checkBoxVector[0] = miligramsCheckBox;
		checkBoxVector[1] = gramsCheckBox;
		checkBoxVector[2] = kilogramsCheckBox;
		checkBoxVector[3] = poundsCheckBox;
		checkBoxVector[4] = litersCheckBox;
		checkBoxVector[5] = miliLitersCheckBox;
		for (int i = 0; i < checkBoxVector.length; i++) {
			checkBoxVector[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					byte selectedItems = 0;
					for (int j = 0; j < checkBoxVector.length; j++) {
						if (checkBoxVector[j] != (MyCheckBox) e.getSource()) {
							checkBoxVector[j].setSelected(false);
						}
						if (checkBoxVector[j].isSelected()) {
							selectedItems++;
						}
					}
					if (selectedItems > 0) {
						measureField.setEnabled(true);
					} else {
						measureField.setText("0");
						measureField.setEnabled(false);
					}
				}
			});
		}
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

	public int getProductPriceFromField() {
		return Integer.valueOf(productPriceField.getText());
	}

	public String getProductNameFromField() {
		return productNameField.getText();
	}

	public ProductType getProductTypeFromField() {
		return (ProductType) productTypeBox.getSelectedItem();
	}

	public float getProductMeasureFromField() {
		return Float.parseFloat(measureField.getText());
	}

	public MeasureType getMeasureTypeSelected() {
		MeasureType mt = MeasureType.NO_REGISTRA;
		for (int i = 0; i < checkBoxVector.length; i++) {
			if (checkBoxVector[i].isSelected()) {
				mt = MeasureType.valueOf(checkBoxVector[i].getName());
			}
		}
		return mt;
	}

	public void clean() {
		productNameField.setText("");
		productPriceField.setText("");
		productTypeBox.setSelectedIndex(0);
		measureField.setText("0");
		for (int i = 0; i < checkBoxVector.length; i++) {
			checkBoxVector[i].setSelected(false);
		}
	}

	public void setNameFieldValue(String string) {
		productNameField.setText(string);
	}

	public void setPriceFieldValue(int price) {
		productPriceField.setText(String.valueOf(price));
	}

	public void setMeasureFieldValue(float measure) {
		measureField.setText(String.valueOf(measure));
	}

	public void setMeasureTypeFieldValue(MeasureType measureType) {
		switch (measureType) {
		case GRAMOS:
			gramsCheckBox.setSelected(true);
			measureField.setEnabled(true);
			break;
		case KILOGRAMOS:
			kilogramsCheckBox.setSelected(true);
			measureField.setEnabled(true);
			break;
		case LIBRAS:
			poundsCheckBox.setSelected(true);
			measureField.setEnabled(true);
			break;
		case LITROS:
			litersCheckBox.setSelected(true);
			measureField.setEnabled(true);
			break;
		case MILIGRAMOS:
			miligramsCheckBox.setSelected(true);
			measureField.setEnabled(true);
			break;
		case MILILITROS:
			miliLitersCheckBox.setSelected(true);
			measureField.setEnabled(true);
			break;
		case NO_REGISTRA:
			measureField.setEnabled(false);
			for (int i = 0; i < checkBoxVector.length; i++) {
				checkBoxVector[i].setSelected(false);
			}
			break;
		}
	}

	public void setProductTypeValue(ProductType productType) {
		switch (productType) {
		case ARTICULO:
			productTypeBox.setSelectedItem(ProductType.ARTICULO);
			break;
		case BEBIDA:
			productTypeBox.setSelectedItem(ProductType.BEBIDA);
			break;
		case COMESTIBLE:
			productTypeBox.setSelectedItem(ProductType.COMESTIBLE);
			break;
		}
	}
	
	public void setProductCodeOnEditing(String code) {
		productCodeOnEditing = code;
	}

	public void setMode(boolean flag) {
		if (flag) {
			setTitle("AGREGAR PRODUCTO");
			doneButton.setActionCommand(Actions.DONE_ADD_PRODUCT_BUTTON.name());
		} else {
			setTitle("EDITAR PRODUCTO");
			doneButton.setActionCommand(Actions.DONE_EDIT_PRODUCT_BUTTON.name());
		}
	}
	
	public String getProductCodeOnEditing() {
		return productCodeOnEditing;
	}
}
