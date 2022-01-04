package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import controller.Actions;
import models.Product;

public class StockProductsTable extends JPanel {

	JTable dataTable;
	DefaultTableModel model;
	private ButtonRenderer buttonRenderer;
	private ButtonEditor addItemButton;
	private MouseMotionListener mmListener;
	private int selectedRow;

	public StockProductsTable(ActionListener listener) {
		setLayout(new BorderLayout());
		String[] headers = { "CÓDIGO", "NOMBRE", "PRECIO", "EXISTENCIAS", "AÑADIR A CARRO" };

		model = new DefaultTableModel(null, headers) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 4;
			}
		};

		dataTable = new JTable(model);

		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		Image img = new ImageIcon(getClass().getResource("/img/share.png")).getImage();
		Image newimg = img.getScaledInstance(width / 24, height / 24, Image.SCALE_DEFAULT);

		buttonRenderer = new ButtonRenderer();
		buttonRenderer.setIcon(new ImageIcon(newimg));
		addItemButton = new ButtonEditor(new JTextField(), "/img/share.png");
		addItemButton.getBtn().setActionCommand(Actions.ADD_ITEM_TO_CAR.name());
		addItemButton.getBtn().addActionListener(listener);

		JScrollPane scrollerTable = new JScrollPane();
		scrollerTable.setViewportView(dataTable);
		scrollerTable.getViewport().setBackground(Color.decode("#faafa0"));
		((DefaultTableCellRenderer) dataTable.getDefaultRenderer(Object.class)).setOpaque(false);

		dataTable.setRowHeight(height / 10);
		dataTable.setForeground(Color.decode("#000000"));
		dataTable.setFont(new Font("Roboto Mono", Font.BOLD, 20));
		dataTable.setBackground(Color.decode("#faafa0"));
		dataTable.setShowVerticalLines(false);
		dataTable.setSelectionForeground(Color.decode("#000000"));
		turnOnMouseListener();

		Font f = new Font("Roboto Mono", Font.BOLD, width / 80);
		JTableHeader header = dataTable.getTableHeader();
		TableColumnModel colMod = header.getColumnModel();
		TableColumn tabCol;
		colMod.getColumn(1).setMinWidth(width / 8);
		colMod.getColumn(0).setMaxWidth(width / 8);
		colMod.getColumn(2).setMaxWidth(width / 16);
		for (int i = 0; i < colMod.getColumnCount(); i++) {
			tabCol = colMod.getColumn(i);
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

	public void turnOnMouseListener() {
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		mmListener = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int column = dataTable.columnAtPoint(e.getPoint());
				int row = dataTable.rowAtPoint(e.getPoint());
				if ((column == 4) && (row != -1)) {
					setCursor(handCursor);
					dataTable.editCellAt(row, column);
					selectedRow = row;
				} else {
					setCursor(defaultCursor);
				}
			}
		};
		dataTable.addMouseMotionListener(mmListener);
		dataTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(defaultCursor);
			}
		});
	}

	public void updateRowsTable(CopyOnWriteArrayList<Product> productsList) {
		stopEditor();
		model.setRowCount(0);
		for (Product product : productsList) {
			model.addRow(product.dataAsVectorToStock());
			dataTable.getColumnModel().getColumn(4).setCellRenderer(buttonRenderer);
			dataTable.getColumnModel().getColumn(4).setCellEditor(addItemButton);
		}
	}
	
	public void stopEditor() {
		if(dataTable.getCellEditor()!= null) {
		dataTable.getCellEditor().stopCellEditing();
		} 
	}

	public String getSelectedProductCode() {
		return (String) model.getValueAt(selectedRow, 0);
	}

	public int getSelectedRow() {
		return dataTable.getSelectedRow();
	}

}