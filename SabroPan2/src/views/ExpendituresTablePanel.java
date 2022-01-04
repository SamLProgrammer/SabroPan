package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import models.Expenditure;

public class ExpendituresTablePanel extends JPanel {

	JTable dataTable;
	DefaultTableModel model;
	private int selectedRow;

	public ExpendituresTablePanel(ActionListener listener) {
		setLayout(new BorderLayout());
		Object[] headers = { "MOTIVO", "PRECIO", "FECHA" };

		model = new DefaultTableModel(null, headers) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 3 || columnIndex == 5;
			}
		};

		dataTable = new JTable(model);

		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		JScrollPane scrollerTable = new JScrollPane();
		scrollerTable.setViewportView(dataTable);
		scrollerTable.getViewport().setBackground(Color.decode("#faafa0"));
		((DefaultTableCellRenderer) dataTable.getDefaultRenderer(Object.class)).setOpaque(false);

		dataTable.setRowHeight(height / 15);
		dataTable.setForeground(Color.decode("#000000"));
		dataTable.setFont(new Font("Roboto Mono", Font.BOLD, 20));
		dataTable.setBackground(Color.decode("#faafa0"));
		dataTable.setShowVerticalLines(false);
		dataTable.setSelectionForeground(Color.decode("#000000"));

//		turnOnMouseListener();
		Font f = new Font("Roboto Mono", Font.BOLD, width / 80);
		JTableHeader header = dataTable.getTableHeader();
		TableColumnModel colMod = header.getColumnModel();
		TableColumn tabCol;
		for (int i = 0; i < colMod.getColumnCount(); i++) {
			tabCol = colMod.getColumn(0);
			tabCol.setResizable(true);
		}

		dataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				c.setBackground(row % 2 == 0 ? Color.decode("#b06d81") : Color.decode("#cedd81"));
				return c;
			}
		});

		header.setFont(f);
		header.setBackground(Color.decode("#faafa0"));
		header.setForeground(Color.decode("#FFFFFF"));

		add(scrollerTable);
	}

	public void updateRowsTable(CopyOnWriteArrayList<Expenditure> expendituresList) {
		model.setRowCount(0);
		for (Expenditure expenditure : expendituresList) {
			model.addRow(expenditure.dataAsVector());
		}
	}

//	public void turnOnMouseListener() {
//		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
//		Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
//		mmListener = new MouseMotionAdapter() {
//			public void mouseMoved(MouseEvent e) {
//				int column = dataTable.columnAtPoint(e.getPoint());
//				int row = dataTable.rowAtPoint(e.getPoint());
//				if ((column == 5 || column == 3) && (row != -1)) {
//					setCursor(handCursor);
//					dataTable.editCellAt(row, column);
//					selectedRow = row;
//				} else {
//					setCursor(defaultCursor);
//				}
//			}
//		};
//		dataTable.addMouseMotionListener(mmListener);
//		dataTable.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseExited(MouseEvent e) {
//				setCursor(defaultCursor);
//			}
//		});
//	}

	public String getSelectedProductCode() {
		return (String) model.getValueAt(selectedRow, 0);
	}

	public void stopEditor() {
		if (dataTable.getCellEditor() != null) {
			dataTable.getCellEditor().stopCellEditing();
		}
	}

	public int calculateSellTotal() {
		int total = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			total += (int) dataTable.getValueAt(i, 4);
		}
		return total;
	}

	public int getSelectedRow() {
		return dataTable.getSelectedRow();
	}

	public int getSelectedProductPrice() {
		return Integer.valueOf((String) model.getValueAt(selectedRow, 2));
	}

	public void calculateItemTotalPrice(int amount) {
		model.setValueAt(amount * getSelectedProductPrice(), selectedRow, 4);
	}
}