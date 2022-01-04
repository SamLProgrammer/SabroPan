package views;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class CheckRenderer extends JCheckBox implements TableCellRenderer {

	private JComponent component = new JCheckBox();

	public CheckRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		((JCheckBox) component).setBackground(row % 2 == 0 ? Color.decode("#b06d81") : Color.decode("#cedd81"));
//		((JCheckBox) component).setBackground(row % 2 == 0 ? Color.decode("#ffffff") : Color.decode("#ffffff"));
		((JCheckBox) component).setHorizontalAlignment(SwingConstants.CENTER);
		if (value != null) {
			boolean b = ((Boolean) value).booleanValue();
			((JCheckBox) component).setSelected(b);
		}
		return ((JCheckBox) component);
	}

}
