package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {

	public ButtonRenderer() {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBorderPainted(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText((value == null) ? "" : value.toString());
//		setBackground(row % 2 == 0 ? Color.decode("#ffffff") : Color.decode("#ffffff"));
		setBackground(row % 2 == 0 ? Color.decode("#b06d81") : Color.decode("#cedd81"));
		return this;
	}

}
