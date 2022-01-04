package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LabelPanel extends JPanel {

	public LabelPanel(String imgURL, int iconWidth, int iconHeight) {
		setBackground(Color.decode("#ffffff"));
		setLayout(new BorderLayout());
		initComponents(imgURL, iconWidth, iconHeight);
	}

	private void initComponents(String imgURL, int iconWidth, int iconHeight) {
		Image img = new ImageIcon(getClass().getResource(imgURL)).getImage();
		Image scaled = img.getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
		JLabel label = new JLabel(new ImageIcon(scaled));
		label.setForeground(Color.decode("#A42626"));
		label.setVerticalTextPosition(SwingConstants.NORTH);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		add(label);
	}
}
