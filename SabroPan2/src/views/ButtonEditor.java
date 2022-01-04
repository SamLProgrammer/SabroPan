package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ButtonEditor extends DefaultCellEditor {

	protected JButton btn;
	private String lbl;
	private boolean clicked;

	public ButtonEditor(JTextField textField,String imgURL) {
		super(textField);
		btn = new JButton();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		Image img = new ImageIcon(getClass().getResource(imgURL)).getImage();
		Image newimg = img.getScaledInstance(width / 20, height / 20, Image.SCALE_DEFAULT);
		btn.setIcon(new ImageIcon(newimg));
//		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public JButton getBtn() {
		return btn;
	}

	public void setBtn(JButton btn) {
		this.btn = btn;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		lbl = (value == null) ? "" : value.toString();
		btn.setBackground(row % 2 == 0 ? Color.decode("#b06d81") : Color.decode("#cedd81"));
//		btn.setBackground(row % 2 == 0 ? Color.decode("#ffffff") : Color.decode("#ffffff"));
		btn.setText(lbl);
		clicked = true;
		return btn;
	}

	@Override
	public Object getCellEditorValue() {
		clicked = false;
		return new String(lbl);
	}

	@Override
	public boolean stopCellEditing() {
		clicked = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	public boolean isClicked() {
		return clicked;
	}
}
