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
import models.Tree;
import models.User;

public class UsersListPanel extends JPanel {

	JTable dataTable;
	DefaultTableModel model;
	private ButtonRenderer editButtonRenderer;
	private ButtonEditor editUserButton;
	private ButtonRenderer removeButtonRenderer;
	private ButtonEditor removeUserButton;
	private int selectedRow;
	private MouseMotionListener mmListener;

	public UsersListPanel(ActionListener listener) {
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		setLayout(new BorderLayout());
		String[] headers = {  "DOCUMENTO", "NOMBRE", "APELLIDO", "ESTADO", "CARGO", "EDITAR", "BORRAR" };
		model = new DefaultTableModel(null, headers) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 5 || columnIndex == 6;
			}
		};

		dataTable = new JTable(model);

		Image img = new ImageIcon(getClass().getResource("/img/editUser.png")).getImage();
		Image newimg = img.getScaledInstance(width / 24, height / 24, Image.SCALE_DEFAULT);
		Image img1 = new ImageIcon(getClass().getResource("/img/Trash.png")).getImage();
		Image newimg1 = img1.getScaledInstance(width / 24, height / 24, Image.SCALE_DEFAULT);

		editButtonRenderer = new ButtonRenderer();
		editButtonRenderer.setIcon(new ImageIcon(newimg));
		editUserButton = new ButtonEditor(new JTextField(), "/img/editUser.png");
		editUserButton.getBtn().setActionCommand(Actions.EDIT_USER_BUTTON.name());
		editUserButton.getBtn().addActionListener(listener);
		
		removeButtonRenderer = new ButtonRenderer();
		removeButtonRenderer.setIcon(new ImageIcon(newimg1));
		removeUserButton = new ButtonEditor(new JTextField(), "/img/Trash.png");
		removeUserButton.getBtn().setActionCommand(Actions.DELETE_USER_BUTTON.name());
		removeUserButton.getBtn().addActionListener(listener);

		JScrollPane scrollerTable = new JScrollPane();
		scrollerTable.setViewportView(dataTable);
		scrollerTable.getViewport().setBackground(Color.decode("#faafa0"));
		((DefaultTableCellRenderer) dataTable.getDefaultRenderer(Object.class)).setOpaque(false);

		dataTable.setRowHeight(height / 10);
		dataTable.setForeground(Color.decode("#000000"));
		dataTable.setFont(new Font("Roboto Mono", Font.BOLD, 20));
		dataTable.setBackground(Color.decode("#FF8000"));
		dataTable.setShowVerticalLines(false);
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
				c.setBackground(row % 2 == 0 ? Color.decode("#b06d81") : Color.decode("#cedd81"));
				return c;
			}
		});
		
		turnOnMouseListener();

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
				if ((column == 5 || column == 6) && (row != -1)) {
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

	public void updateRowsTable(Tree<User> usersTree) {
		stopEditor();
		model.setRowCount(0);
		usersTree.sendDataToTable();
	}
	
	public void addUserToTable(User user) {
		model.addRow(user.dataAsVector());
		dataTable.getColumnModel().getColumn(5).setCellRenderer(editButtonRenderer);
		dataTable.getColumnModel().getColumn(5).setCellEditor(editUserButton);
		dataTable.getColumnModel().getColumn(6).setCellRenderer(removeButtonRenderer);
		dataTable.getColumnModel().getColumn(6).setCellEditor(removeUserButton);
	}
	
	public void turnOffMouseListener() {
		dataTable.removeMouseMotionListener(mmListener);
	}

	public void stopEditor() {
		if (dataTable.getCellEditor() != null) {
			dataTable.getCellEditor().stopCellEditing();
		}
	}

	public String getSelectedRowUserCode() {
		return (String) dataTable.getValueAt(selectedRow, 0);
	}

}