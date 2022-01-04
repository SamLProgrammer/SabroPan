package views;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class CheckEditor extends DefaultCellEditor implements TableCellRenderer  {
 
    private JComponent component;
    private boolean value = false; // valor de la celda
 
    /** Constructor de clase */
    public CheckEditor() {
        super( new JCheckBox() );
        JCheckBox chkBox= new JCheckBox();
        chkBox.setHorizontalAlignment(SwingConstants.CENTER);
        component = chkBox;
    }
 
    /** retorna valor de celda */
    @Override
    public Object getCellEditorValue() {
        return ((JCheckBox)component).isSelected();
    }
 
    /** Segun el valor de la celda selecciona/deseleciona el JCheckBox */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //Color de fondo en modo edicion
        ( (JCheckBox) component).setBackground(row % 2 == 0 ? Color.decode("#b06d81") : Color.decode("#cedd81"));
        ( (JCheckBox) component).setCursor(new Cursor(Cursor.HAND_CURSOR));
        JCheckBox xd = (JCheckBox)component;
        scaleCheckBoxIcon(xd);
        if(value != null) {
        boolean b = ((Boolean) value).booleanValue();
        ( (JCheckBox) component).setSelected( b );
        }
        return ( (JCheckBox) component);
    }
    
    public void scaleCheckBoxIcon(JCheckBox checkbox){
        boolean previousState = checkbox.isSelected();
        checkbox.setSelected(false);
        FontMetrics boxFontMetrics =  checkbox.getFontMetrics(checkbox.getFont());
        Icon boxIcon = UIManager.getIcon("CheckBox.icon");
        BufferedImage boxImage = new BufferedImage(
            boxIcon.getIconWidth(), boxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics graphics = boxImage.createGraphics();
        try{
            boxIcon.paintIcon(checkbox, graphics, 0, 0);
        }finally{
            graphics.dispose();
        }
        ImageIcon newBoxImage = new ImageIcon(boxImage);
        Image finalBoxImage = newBoxImage.getImage().getScaledInstance(
            boxFontMetrics.getHeight()*13/10, boxFontMetrics.getHeight()*13/10, Image.SCALE_SMOOTH
        );
        checkbox.setIcon(new ImageIcon(finalBoxImage));

        checkbox.setSelected(true);
        Icon checkedBoxIcon = UIManager.getIcon("CheckBox.icon");
        BufferedImage checkedBoxImage = new BufferedImage(
            checkedBoxIcon.getIconWidth(),  checkedBoxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics checkedGraphics = checkedBoxImage.createGraphics();
        try{
            checkedBoxIcon.paintIcon(checkbox, checkedGraphics, 0, 0);
        }finally{
            checkedGraphics.dispose();
        }
        ImageIcon newCheckedBoxImage = new ImageIcon(checkedBoxImage);
        Image finalCheckedBoxImage = newCheckedBoxImage.getImage().getScaledInstance(
            boxFontMetrics.getHeight()*13/10, boxFontMetrics.getHeight()*13/10, Image.SCALE_SMOOTH
        );
        checkbox.setSelectedIcon(new ImageIcon(finalCheckedBoxImage));
        checkbox.setSelected(false);
        checkbox.setSelected(previousState);
    }
 
    /** cuando termina la manipulacion de la celda */
    @Override
    public boolean stopCellEditing() {
        value = ((Boolean)getCellEditorValue()).booleanValue() ;
        ((JCheckBox)component).setSelected( value );
        return super.stopCellEditing();
    }
 
    /** retorna componente */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         if (value == null)
            return null;
         return ( (JCheckBox) component );
    }
 
}
