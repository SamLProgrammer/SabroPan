package views;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import observers.SpinnerListener;

public class SpinnerEditor extends DefaultCellEditor {
	JSpinner spinner;
	JSpinner.DefaultEditor editor;
	JTextField textField;
	private SpinnerListener spinnerListener;
	boolean valueSet;

	public SpinnerEditor(SpinnerListener spinnerListener) {
		super(new JTextField());
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.spinnerListener = spinnerListener;
		spinner = new JSpinner();
		editor = ((JSpinner.DefaultEditor) spinner.getEditor());
		textField = editor.getTextField();
		textField.setFont(new Font("Roboto Mono", Font.BOLD, height/35));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent fe) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (valueSet) {
							textField.setCaretPosition(1);
						}
					}
				});
			}

			public void focusLost(FocusEvent fe) {
			}
		});
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				stopCellEditing();
			}
		});
		setSpinnerListener();
		initKeyListener(spinnerListener);
	}
	
	private void setSpinnerListener() {
		for (int i = 0; i < spinner.getComponents().length; i++) {
			if (spinner.getComponent(i) instanceof JButton) {
				spinner.getComponent(i).addMouseListener(initSpinnerListener(spinnerListener));
			}
		}
	}
	
	private MouseAdapter initSpinnerListener(SpinnerListener spinnerListener) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				spinnerListener.calculateItemTotalPrice((int)spinner.getValue());
			}
		};
	}
	
	
	private void initKeyListener(SpinnerListener spinnerListener) {
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				spinner.setValue(Integer.parseInt(textField.getText()));
				spinnerListener.calculateItemTotalPrice((int)spinner.getValue());
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null)
			spinner.setValue(0);
		else {
			spinner.setValue(value);
		}
		return spinner;
	}

	public boolean isCellEditable(EventObject eo) {
		if (eo instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) eo;
			System.err.println("key event: " + ke.getKeyChar());
			textField.setText(String.valueOf(ke.getKeyChar()));
			valueSet = true;
		} else {
			valueSet = false;
		}
		return true;
	}

	public Object getCellEditorValue() {
		return spinner.getValue();
	}

//	public boolean stopCellEditing() {
//		try {
//			editor.commitEdit();
//			spinner.commitEdit();
//		} catch (java.text.ParseException e) {
//			JOptionPane.showMessageDialog(null, "Invalid value, discarding.");
//		}
//		return super.stopCellEditing();
//	}
}