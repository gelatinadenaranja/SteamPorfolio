package files;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ItemTable {
	
	private JTable main_data_container;
	private DefaultTableModel table_model;
	private ItemEdit item_editor;
	private int profit_mode;
	
	public ItemTable() {
		item_editor = new ItemEdit();
		table_model = new DefaultTableModel();
		
    	main_data_container = new JTable(table_model) {
			private static final long serialVersionUID = 1L;
			
			public boolean getScrollableTracksViewportWidth() {
				   return getPreferredSize().width < getParent().getWidth();
			 }
			 
			 public void changeSelection( int row, int col, boolean toggle, boolean expand ) {
			     if(col == 1) {
			    	 super.changeSelection( row, col, true, expand );
			     }
			}
			
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				switch(column) {
				case 0:
					return ImageIcon.class;
				
				default:
					return String.class;
				}
           }
		};
    	
		table_model.addColumn("");
		table_model.addColumn("Item");
		table_model.addColumn("Quantity");
		table_model.addColumn("Cost");
		table_model.addColumn("Profit (%)");
		table_model.addColumn("<html>Expected value<br>(Tax)");
		table_model.addColumn("<html>Lowest price<br>(Tax)");
		table_model.addColumn("<html>Median price<br>(Tax)");
    	table_model.addColumn("Volume");
		
		main_data_container.setFocusable(true);
		main_data_container.setRowSelectionAllowed(false);
		main_data_container.getTableHeader().setReorderingAllowed(false);
		main_data_container.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		main_data_container.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		main_data_container.setRowHeight(30);
		main_data_container.getColumnModel().getColumn(0).setMinWidth(32);
		main_data_container.getColumnModel().getColumn(0).setMaxWidth(32);
		main_data_container.getColumnModel().getColumn(0).setResizable(false);
		main_data_container.getColumnModel().getColumn(1).setMinWidth(320);
		main_data_container.getColumnModel().getColumn(2).setMinWidth(70);
		main_data_container.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer());
		main_data_container.getColumnModel().getColumn(3).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer());
		main_data_container.getColumnModel().getColumn(4).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer());
		main_data_container.getColumnModel().getColumn(5).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer());
		main_data_container.getColumnModel().getColumn(6).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(6).setCellRenderer(new CustomCellRenderer());
		main_data_container.getColumnModel().getColumn(7).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(7).setCellRenderer(new CustomCellRenderer());
		main_data_container.getColumnModel().getColumn(8).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(8).setCellRenderer(new CustomCellRenderer());
		
		//Events
		@SuppressWarnings("serial")
		Action view_item_info = new AbstractAction() { //Column button event. Open form for item edits
		    public void actionPerformed(ActionEvent e) {
		    	int selected_row = Integer.valueOf(e.getActionCommand());
		    	item_editor.set_item_icon(main_data_container.getValueAt(selected_row, 0));
		    	item_editor.set_item_name(table_model.getValueAt(selected_row, 1).toString());
		    	item_editor.set_item_cost(Double.parseDouble(table_model.getValueAt(selected_row, 3).toString()));
		    	item_editor.set_item_expectedvalue(Double.parseDouble(table_model.getValueAt(selected_row, 5).toString()));
		    	item_editor.set_item_price(Double.parseDouble(table_model.getValueAt(selected_row, 6).toString()));
		    	item_editor.set_item_quantity(Integer.parseInt(table_model.getValueAt(selected_row, 2).toString()));
		    	item_editor.set_table_row_num(selected_row);
		    	item_editor.set_form_visible();
		    }
		};
		@SuppressWarnings("unused")
		ButtonColumn btn_col = new ButtonColumn(main_data_container, view_item_info, 1);
	}
	
	@SuppressWarnings("serial")
	private class CustomCellRenderer extends DefaultTableCellRenderer {
		
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if ((column%2) == 0) {
				setBackground(Color.WHITE);
			} else {
				setBackground( new Color(191, 205, 219) ); //Make a bit lighter
			}
			
			String text_value = value.toString();
			if( (text_value == null) || (text_value.length() < 0) ) {
				return this;
			}
			
			if( column == 4 ) {
				if(text_value.charAt(0) == '-') {
					setForeground(Color.RED);
				} else {
					setForeground(Color.GREEN); //Change to a darker tone
				}
				
			} else if( (column == 6) || (column == 7) || (column == 8) ) {
				if(text_value.charAt(0) == '!') {
					setForeground(Color.YELLOW); //Change to a darker tone
				}
			}
			
	        return this;
	    }
	}
	
	public void add_new_row(ImageIcon icon, String name, String quantity, String cost, String profit, String expected_value, 
			                String lowest_price, String median_price, String volume) {
		table_model.addRow(new Object[] {icon, name, quantity, cost, profit, expected_value, lowest_price, median_price, volume});
	}
	
	public void remove_row(int row) {
		table_model.removeRow(row);
	}
	
	public void refresh_item_data(int row, String new_quantity, String new_cost, String new_profit, String new_expectedvalue) {
		table_model.setValueAt(new_quantity, row, 2);
		table_model.setValueAt(new_cost, row, 3);
		table_model.setValueAt(new_profit , row, 4);
		table_model.setValueAt(new_expectedvalue, row, 5);
	}
	
	public void refresh_item_market_data(int row, String new_profit, String new_lowest_price, String new_median_price, String new_volume) {
		table_model.setValueAt(new_profit, row, 4);
		table_model.setValueAt(new_lowest_price, row, 6);
		table_model.setValueAt(new_median_price, row, 7);
		table_model.setValueAt(new_volume, row, 8);
	}
	
	public int apply_item_edits() { //Returns the edited row number
		int row_num = item_editor.get_table_row_num();
		double new_cost = item_editor.get_item_cost();
		double new_expectedvalue = item_editor.get_item_expectedvalue();
		int new_quantity = item_editor.get_item_quantity();
		String new_profit = profit_calc( new_cost, table_model.getValueAt(row_num, 6).toString() );
		
		refresh_item_data(row_num,
				Integer.toString(new_quantity),
				Double.toString(new_cost),
				new_profit,
				Double.toString(new_expectedvalue));
		
		return row_num;
	}
	
	public int find_item_row(String item) {
		for(int i = 0; i < table_model.getRowCount(); i++) {
			if( table_model.getValueAt(i, 1).toString().equals(item) ) {
				return i;
			}
		}
		
		return -1;
	}
	
	private String profit_calc(double cost, String price_param) {
		if(cost == 0){
			return "Infinite";
		}
		
		double profit_value;
		double price;
		
		price = Double.parseDouble(price_param);
		
		cost = add_tax(cost);
		
		switch(profit_mode){
		case 0:
			profit_value =  ((price * 100) / cost) - 100;
			profit_value = truncate(profit_value);
			return String.valueOf(profit_value) + '%';
		default:
			JOptionPane.showMessageDialog(null, "An invalid profit display mode has been selected.\nSwitched to default.");
			profit_mode = 0;
			profit_value =  ((price * 100) / cost) - 100;
			profit_value = truncate(profit_value);
			return String.valueOf(profit_value) + '%';
		}
	}
	
	private double add_tax(double val) {
		//Adds Valve's 5% and the game developer's 10% cut from the items sold on the Steam Communitty Market.
		double gametax = truncate(val * 0.1);
		double steamtax = truncate(val * 0.05);
		
		if(gametax < 0.01) {
			gametax = 0.01;
		}
		
		if(steamtax < 0.01) {
			steamtax = 0.01;
		}
		
		val = (gametax + steamtax + val);
		
		return truncate(val);
	}
	
	private double truncate(double value) {
		//Leaves only two decimals without rounding anything
		return (Math.floor(value * 100) / 100);
	}
	
	//Getters
	public JTable get_jtable() {
		return main_data_container;
	}
	
	public Object get_cell_value(int row, int col) {
		return table_model.getValueAt(row, col);
	}
	
	public int get_row_count() {
		return table_model.getRowCount();
	}
	
	public boolean get_item_got_edited() {
		return item_editor.get_task_done();
	}
	
	//Setters
	public void set_mainform_frame(JFrame frame) {
		item_editor.set_mainform_frame(frame);
	}
	
	public void set_item_got_edited() {
		item_editor.set_task_completed();
	}
	
	
}
