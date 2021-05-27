package files;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 *  Ty Rob Camick for the class
 *  Source: http://tips4java.wordpress.com/2009/07/12/table-button-column/
 */
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Action action;
	private Border originalBorder;
	private Border focusBorder;
	private JButton renderButton;
	private JButton editButton;
	private Object editorValue;
	private boolean isButtonColumnEditor;
	
	public ButtonColumn(JTable table, Action action, int column) {
		this.table = table;
		this.action = action;
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted( false );
		editButton.addActionListener( this );
		originalBorder = editButton.getBorder();
		
		setFocusBorder(new LineBorder(Color.BLUE));
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
		table.addMouseListener(this);
	}


	/**Get foreground color of the button when the cell has focus*/
	public Border getFocusBorder() {
		return focusBorder;
	}
	
	/**The foreground color of the button when the cell has focus*/
	public void setFocusBorder(Border focusBorder) {
		editButton.setBorder( focusBorder );
	}
	
	/**The mnemonic to activate the button when the cell has focus*/
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			editButton.setText( "" );
			editButton.setIcon( null );
		}else if (value instanceof Icon) {
			editButton.setText( "" );
			editButton.setIcon( (Icon)value );
		}else {
			editButton.setText( value.toString() );
			editButton.setIcon( null );
		}

		this.editorValue = value;
		return editButton;
	}

	public Object getCellEditorValue() {
		return editorValue;
	}
	
	//Implement TableCellRenderer interface
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
	 		renderButton.setBackground(table.getSelectionBackground());
		}else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}
		
		if (hasFocus) {
			renderButton.setBorder( focusBorder );
		}else {
			renderButton.setBorder( originalBorder );
		}
		
		//renderButton.setText((value == null) ? "" : value.toString());
		if (value == null) {
			renderButton.setText( "" );
			renderButton.setIcon( null );
		} else if (value instanceof Icon) {
			renderButton.setText( "" );
			renderButton.setIcon( (Icon)value );
		}else {
			renderButton.setText( value.toString() );
			renderButton.setIcon( null );
		}

		return renderButton;
	}
	
	//Implement ActionListener interface
	/*The button has been pressed. Stop editing and invoke the custom Action*/
	public void actionPerformed(ActionEvent e) {
		int row = table.convertRowIndexToModel(table.getEditingRow());
		fireEditingStopped();
		
		// Invoke the Action
		ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
		action.actionPerformed(event);
	}
	
	// Implement MouseListener interface
	/*
	 *  When the mouse is pressed the editor is invoked. If you then drag
	 *  the mouse to another cell before releasing it, the editor is still
	 *  active. Make sure editing is stopped when the mouse is released.
	 */
    public void mousePressed(MouseEvent e){
    	if (table.isEditing() &&  table.getCellEditor() == this) {
    		isButtonColumnEditor = true;
    	}
    }

    public void mouseReleased(MouseEvent e) {
    	if (isButtonColumnEditor && table.isEditing()) {
    		table.getCellEditor().stopCellEditing();
    	}
    	
		isButtonColumnEditor = false;
    }
    
    public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}