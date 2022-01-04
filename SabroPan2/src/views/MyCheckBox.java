package views;

import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class MyCheckBox extends JCheckBox{

	public MyCheckBox(String text, String name) {
		setText(text);
		setName(name);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setFont(new Font("Roboto Mono", Font.BOLD, 12));
		setOpaque(false);
	}
}
