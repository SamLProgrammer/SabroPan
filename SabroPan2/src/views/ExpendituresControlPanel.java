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
import models.Expenditure;

public class ExpendituresControlPanel extends JPanel {

	private ExpendituresTablePanel ExpendituresTablePanel;
	private NiceButton backButton, addExpenditureButton;
	private JTextField searchField;

	public ExpendituresControlPanel(ActionListener listener) {
		initComponents(listener);
	}

	private void initComponents(ActionListener listener) {
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBorder(BorderFactory.createEmptyBorder(0, width / 50, 0, width / 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.decode("#ffe489"));

		ExpendituresTablePanel = new ExpendituresTablePanel(listener);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel titleLabel = new JLabel("Módulo de Gastos", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Roboto Mono", Font.BOLD, 40));

		searchField = new JTextField();
		searchField.setFont(new Font("Roboto Mono", Font.BOLD, height / 40));
		searchField.setBorder(BorderFactory.createEmptyBorder(height / 180, 0, height / 180, width / 100));
		backButton = new NiceButton("Regresar", "#000000", "#ffffff");
		backButton.setActionCommand(Actions.BACK_BUTTON.name());
		addExpenditureButton = new NiceButton("Agregar Gasto", "#000000", "#ffffff");
		addExpenditureButton.setActionCommand(Actions.ADD_EXPENDITURE_BUTTON.name());

		Image img = new ImageIcon(getClass().getResource("/img/Lens.png")).getImage();
		Image newimg = img.getScaledInstance(width / 40, height / 40, Image.SCALE_DEFAULT);

		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel(new ImageIcon(newimg));
		label.setBorder(BorderFactory.createEmptyBorder(height / 180, width / 100, height / 180, width / 100));
		p.setOpaque(false);

		label.setPreferredSize(new Dimension(label.getPreferredSize().width, searchField.getPreferredSize().height));

		p.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		p.add(label, BorderLayout.WEST);
		p.add(searchField, BorderLayout.CENTER);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.decode("#ffffff"));
		p1.add(p);

		gbc.insets = new Insets(height / 40, 0, 0, height / 40);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.6;
		gbc.gridwidth = 1;
		northPanel.add(backButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.6;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(titleLabel, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 0.6;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.CENTER;
		northPanel.add(addExpenditureButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		northPanel.add(p1, gbc);

		add(northPanel, BorderLayout.NORTH);
		add(ExpendituresTablePanel);
	}

	public void updateExpendituresListPanel(CopyOnWriteArrayList<Expenditure> productsList) {
		ExpendituresTablePanel.stopEditor();
		ExpendituresTablePanel.updateRowsTable(productsList);
	}

	public String getSelectedProductFromTable() {
		return ExpendituresTablePanel.getSelectedProductCode();
	}

	public void updateExpendituresTable(CopyOnWriteArrayList<Expenditure> expendituresList) {
		ExpendituresTablePanel.updateRowsTable(expendituresList);
	}
}
