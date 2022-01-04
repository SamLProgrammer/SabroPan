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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import controller.Actions;

public class AddExpenditureDialog extends JDialog {

	private JTextField expenditurePriceField;
	private JTextPane expenditureReasonTextArea;
	private JButton doneButton, cancelButton;
	private int width, height;
	private LabelPanel labelPanel;

	public AddExpenditureDialog(ActionListener listener) {
		setTitle("¡AGREGAR GASTO!");
		locate();
		initComponents(listener);
	}

	private void initComponents(ActionListener listener) {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/SabroPanLogo.jpeg")).getImage());

		expenditureReasonTextArea = new JTextPane();
		expenditureReasonTextArea.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		expenditurePriceField = new JTextField();
		expenditurePriceField.setHorizontalAlignment(SwingConstants.CENTER);
		expenditurePriceField.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		
		JScrollPane scrollerPane = new JScrollPane();
		scrollerPane.setViewportView(expenditureReasonTextArea);

		JLabel productNameLabel = new JLabel("Motivo", SwingConstants.CENTER);
		productNameLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));
		JLabel productPriceLabel = new JLabel("Precio", SwingConstants.CENTER);
		productPriceLabel.setFont(new Font("Roboto Mono", Font.BOLD, height / 30));

		doneButton = new JButton("LISTO");
		doneButton.addActionListener(listener);
		doneButton.setActionCommand(Actions.DONE_ADD_EXPENDITURE_BUTTON.name());
		doneButton.setHorizontalTextPosition(SwingConstants.CENTER);
		doneButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doneButton.setBackground(Color.decode("#A4FAB2"));

		cancelButton = new JButton("CANCELAR");
		cancelButton.addActionListener(listener);
		cancelButton.setActionCommand(Actions.CANCEL_ADD_EXPENDITURE_BUTTON.name());
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelButton.setBackground(Color.decode("#F65C3E"));

		labelPanel = new LabelPanel("/img/Expenditure.png", width / 30, height / 12);
		labelPanel.setBackground(Color.decode("#FDD3CB"));
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridBagLayout());
		
		JPanel productNamePanel = new JPanel();
		productNamePanel.setLayout(new GridLayout(2, 1));
		productNamePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		
		JPanel productPricePanel = new JPanel();
		productPricePanel.setLayout(new GridLayout(2, 1));
		productPricePanel.setBorder(BorderFactory.createEmptyBorder(0, width / 20, 0, width / 20));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2, width / 80, 0));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(height / 40, width / 10, height / 40, width / 10));

		productNamePanel.add(productNameLabel);
		productNamePanel.add(scrollerPane);
		productPricePanel.add(productPriceLabel);
		productPricePanel.add(expenditurePriceField);
		buttonsPanel.add(doneButton);
		buttonsPanel.add(cancelButton);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		gbc.weighty = 3;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		containerPanel.add(productNamePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weighty = 0.5;
		containerPanel.add(productPricePanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		containerPanel.add(buttonsPanel, gbc);

		opaqueAll(containerPanel);
		add(containerPanel);
		add(labelPanel, BorderLayout.NORTH);
		requestFocus();
		requestFocus(true);
		requestFocusInWindow();
		requestFocusInWindow(true);
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
		setSize(width / 2, 6 * height / 10);
		setLocation(width / 2 - this.getWidth() / 2, height / 8);
	}

	public int getExpenditurePriceFromField() {
		return Integer.valueOf(expenditurePriceField.getText());
	}

	public String getExpenditureNameFromField() {
		return expenditureReasonTextArea.getText();
	}

	public void clean() {
		expenditureReasonTextArea.setText("");
		expenditurePriceField.setText("");
	}
}
