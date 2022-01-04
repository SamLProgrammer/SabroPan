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

public class ProductsListTablePanel extends JPanel {

	JTable dataTable;
	DefaultTableModel model;
	private ButtonRenderer addItemButtonRenderer;
	private ButtonEditor addItemButton;
	private ButtonRenderer editProductButtonRenderer;
	private ButtonEditor editProductButton;
	private MouseMotionListener mmListener;
	private int selectedRow;
	boolean flag;

	public ProductsListTablePanel(ActionListener listener) {
		setLayout(new BorderLayout());
		Object[] headers = { "CÓDIGO", "NOMBRE", "MEDIDA", "PRECIO", "TIPO", "UNIDADES RESTANTES",
				"ACTIVAR / INACTIVAR", "AGREGAR UNIDADES", "EDITAR PRODUCTO" };

		model = new DefaultTableModel(null, headers) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 6 || columnIndex == 7 || columnIndex == 8;
			}
		};

		dataTable = new JTable(model);
		dataTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		Image img = new ImageIcon(getClass().getResource("/img/Supply.png")).getImage();
		Image newimg = img.getScaledInstance(width / 24, height / 24, Image.SCALE_DEFAULT);

		Image img2 = new ImageIcon(getClass().getResource("/img/editProduct.png")).getImage();
		Image newimg2 = img2.getScaledInstance(width / 24, height / 24, Image.SCALE_DEFAULT);

		addItemButtonRenderer = new ButtonRenderer();
		addItemButtonRenderer.setIcon(new ImageIcon(newimg));
		addItemButton = new ButtonEditor(new JTextField(), "/img/Supply.png");
		addItemButton.getBtn().setActionCommand(Actions.ADD_ITEM_BUTTON.name());
		addItemButton.getBtn().addActionListener(listener);

		editProductButtonRenderer = new ButtonRenderer();
		editProductButtonRenderer.setIcon(new ImageIcon(newimg2));
		editProductButton = new ButtonEditor(new JTextField(), "/img/editProduct.png");
		editProductButton.getBtn().setActionCommand(Actions.EDIT_PRODUCT_BUTTON.name());
		editProductButton.getBtn().addActionListener(listener);

		JScrollPane scrollerTable = new JScrollPane();
		scrollerTable.setViewportView(dataTable);
		scrollerTable.getViewport().setBackground(Color.decode("#ffffff"));
		((DefaultTableCellRenderer) dataTable.getDefaultRenderer(Object.class)).setOpaque(false);

		dataTable.setRowHeight(height / 10);
		dataTable.setForeground(Color.decode("#000000"));
		dataTable.setFont(new Font("Roboto Mono", Font.BOLD, 20));
		dataTable.setBackground(Color.decode("#ffffff"));
//		dataTable.setShowVerticalLines(false);
		dataTable.setSelectionForeground(Color.decode("#000000"));
		dataTable.setSelectionBackground(null);
		turnOnMouseListener();

		Font f = new Font("Roboto Mono", Font.BOLD, 21);
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
				c.setBackground(row % 2 == 0? Color.decode("#b06d81") : Color.decode("#cedd81"));
//				c.setBackground(row % 2 == 0 ? Color.decode("#ffffff") : Color.decode("#ffffff"));
				return c;
			}
		});

		header.setFont(f);
		header.setBackground(Color.decode("#870A30"));
		header.setForeground(Color.decode("#FFFFFF"));

		add(scrollerTable);
	}

	public void turnOffMouseListener() {
		dataTable.removeMouseMotionListener(mmListener);
	}

	public void turnOnMouseListener() {
		dataTable.setRowSelectionAllowed(true);
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		mmListener = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int column = dataTable.columnAtPoint(e.getPoint());
				int row = dataTable.rowAtPoint(e.getPoint());
				if ((column == 0 || column == 7 || column == 8) && (row != -1)) {
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
			model.addRow(product.dataAsVector());
		}

		dataTable.getColumnModel().getColumn(8).setCellRenderer(editProductButtonRenderer);
		dataTable.getColumnModel().getColumn(8).setCellEditor(editProductButton);
		dataTable.getColumnModel().getColumn(7).setCellRenderer(addItemButtonRenderer);
		dataTable.getColumnModel().getColumn(7).setCellEditor(addItemButton);

		if (!flag) {
			dataTable.getColumnModel().getColumn(6).setCellRenderer(new CheckRenderer());
			dataTable.getColumnModel().getColumn(6).setCellEditor(new CheckEditor());
			dataTable.moveColumn(6, 0);
			flag = true;
		}
	}

	public void stopEditor() {
		if (dataTable.getCellEditor() != null) {
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