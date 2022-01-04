package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class NiceButton extends JButton {

	Image img;

	public NiceButton(String iconUrl, String title, String ForegroundcolorCode, String BackgroundColorCode) {
		img = new ImageIcon(getClass().getResource(iconUrl)).getImage();
		setLayout(new BorderLayout());
		setBackground(Color.decode(BackgroundColorCode));
		setForeground(Color.decode(ForegroundcolorCode));
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setFont(new Font("Oswald", Font.BOLD, 15));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setFocusPainted(false);
		setText(title);
		initThis();
	}

	public NiceButton(String title, String ForegroundcolorCode, String BackgroundColorCode) {
		setLayout(new BorderLayout());
		setBackground(Color.decode(BackgroundColorCode));
		setForeground(Color.decode(ForegroundcolorCode));
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setFont(new Font("Oswald", Font.BOLD, 15));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setFocusPainted(false);
		setText(title);
		initThis();
	}

	private void initThis() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (img != null) {
					JButton btn = (JButton) e.getComponent();
					Dimension size = btn.getSize();
					Insets insets = btn.getInsets();
					size.width -= insets.left + insets.right;
					size.height -= insets.top + insets.bottom + 15;
					if (size.width > size.height) {
						size.width = -1;
					} else {
						size.height = -1;
					}
					Image scaled = img.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
					btn.setIcon(new ImageIcon(scaled));
				}
			}
		});
	}
}
