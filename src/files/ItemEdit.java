package files;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ItemEdit {
	
	private JFrame frame = new JFrame();
	private JFrame mainform_frame;
	private JPanel panel_itemname = new JPanel();
	private JPanel panel_center = new JPanel();
	private JPanel panel_iteminfo = new JPanel();
	private JPanel panel_buttons = new JPanel();
	private JButton btn_accept = new JButton("Accept");
	private JButton btn_cancel = new JButton("Cancel");
	private JLabel lbl_itemicon = new JLabel();
	private JLabel lbl_itemname = new JLabel();
	private JLabel lbl_cost = new JLabel("Cost: ");
	private JLabel lbl_quantity = new JLabel("Quantity: ");
	private JLabel lbl_expectedvalue = new JLabel("Expected value: ");
	private JLabel lbl_totalcost = new JLabel();
	private JLabel lbl_totalvalue = new JLabel();
	private JLabel lbl_returns = new JLabel();
	private JTextField txt_cost = new JTextField();
	private JTextField txt_quantity = new JTextField();
	private JTextField txt_expectedvalue = new JTextField();
	private Component label_separator1 = Box.createVerticalStrut(7);
	private Component label_separator2 = Box.createVerticalStrut(7);
	private Component name_separator = Box.createHorizontalStrut(5);
	private Component button_separator = Box.createHorizontalStrut(35);
	private ImageIcon item_icon = null;
	private String item_name = "";
	private double item_cost = 0;
	private double item_expectedvalue = 0;
	private double item_price = 0;
	private int item_quantity = 0;
	private int item_table_row = 0;
	private boolean task_done = false;
	
	public ItemEdit() {
		frame.setBounds(100, 100, 400, 350);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new BoxLayout(panel_center, BoxLayout.Y_AXIS));
		
		panel_itemname.setPreferredSize(new Dimension(200, 25));
		lbl_itemname.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_itemname.add(lbl_itemicon);
		panel_itemname.add(name_separator);
		panel_itemname.add(lbl_itemname);
		panel_itemname.setLayout(new GridBagLayout());
		frame.getContentPane().add(panel_itemname, BorderLayout.NORTH);
		
		Box box_cost = Box.createHorizontalBox();
		box_cost.setMaximumSize(new Dimension(500, 60));
		txt_cost.setMaximumSize(new Dimension(500, 30));
		lbl_cost.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_cost.setFont(new Font("Arial", Font.PLAIN, 14));
		box_cost.add(lbl_cost);
		box_cost.add(txt_cost);
		panel_center.add(box_cost);
		
		Box box_quantity = Box.createHorizontalBox();
		box_quantity.setMaximumSize(new Dimension(500, 60));
		txt_quantity.setMaximumSize(new Dimension(500, 30));
		lbl_quantity.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_quantity.setFont(new Font("Arial", Font.PLAIN, 14));
		box_quantity.add(lbl_quantity);
		box_quantity.add(txt_quantity);
		panel_center.add(box_quantity);
		
		Box box_expectedvalue = Box.createHorizontalBox();
		box_expectedvalue.setMaximumSize(new Dimension(500, 60));
		txt_expectedvalue.setMaximumSize(new Dimension(500, 30));
		lbl_expectedvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_expectedvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		box_expectedvalue.add(lbl_expectedvalue);
		box_expectedvalue.add(txt_expectedvalue);
		panel_center.add(box_expectedvalue);
		
		panel_iteminfo.setBorder(new LineBorder(Color.GRAY, 2, true));
		panel_iteminfo.setLayout(new BoxLayout(panel_iteminfo, BoxLayout.Y_AXIS));
		lbl_totalcost.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl_totalvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl_returns.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_iteminfo.add(lbl_totalcost);
		panel_iteminfo.add(label_separator1);
		panel_iteminfo.add(lbl_totalvalue);
		panel_iteminfo.add(label_separator2);
		panel_iteminfo.add(lbl_returns);
		
		Box box_paneliteminfo = Box.createHorizontalBox();
		box_paneliteminfo.add(panel_iteminfo);
		panel_center.add(box_paneliteminfo);
		
		panel_buttons.setPreferredSize(new Dimension(200, 30));
		panel_buttons.add(btn_accept);
		panel_buttons.add(button_separator);
		panel_buttons.add(btn_cancel);
		panel_buttons.setLayout(new GridBagLayout());
		frame.getContentPane().add(panel_buttons, BorderLayout.SOUTH);
		
		//Events
		txt_cost.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double number = 0;
				String last_number = Double.toString(item_cost);
				boolean is_input_valid = false;
				
				txt_cost.setText(txt_cost.getText().replace(',', '.'));
				
				if(txt_cost.getText().isEmpty()) {
					txt_cost.setText(last_number);
				}
				
				if( txt_cost.getText().charAt(txt_cost.getText().length() - 1) == '.' ) {
					txt_cost.setText( txt_cost.getText().substring(0, txt_cost.getText().length() - 1) );
				}
				
				if( txt_cost.getText().charAt(0) == '.' ) {
					txt_cost.setText('0' + txt_cost.getText());
				}
				
				try{
					number = Double.parseDouble(txt_cost.getText());
					
					is_input_valid = true;
					
					if(number < 0){
						txt_cost.setText(last_number);
						is_input_valid = false;
					    JOptionPane.showMessageDialog(null, "Please, only use values equal or over zero in this field.\\nE.g.: 35");
					} else {
						item_cost = truncate(number);
						txt_cost.setText(Double.toString(item_cost));
					}
					
				} catch(NumberFormatException ex) {
					txt_cost.setText(last_number);
				    JOptionPane.showMessageDialog(null, "Please, only use numbers in this field.\\nE.g.: 0.25 ");
				}
				
				if(is_input_valid) {
					update_total_cost();
					update_returns();
				}
			}
		});
		
		txt_quantity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int number = 0;
				String last_number = Integer.toString(item_quantity);
				boolean is_input_valid = false;
				
				if(txt_quantity.getText().isEmpty()) {
					txt_quantity.setText(last_number);
				}
				
				try{
					number = Integer.parseInt(txt_quantity.getText());
					
					is_input_valid = true;
					
					if(number < 0){
						txt_quantity.setText(last_number);
						is_input_valid = false;
					    JOptionPane.showMessageDialog(null, "Please, only use integer numbers equal or over zero in this field.\nE.g.: 10 ");
					} else {
						item_quantity = number;
					}
					
				} catch(NumberFormatException ex) {
					txt_quantity.setText(last_number);
				    JOptionPane.showMessageDialog(null, "Invalid input:\nOnly use integer values between 0 and 2147483647 are accepted.\nE.g.: 10 ");
				} 
				
				if(is_input_valid) {
					update_total_cost();
					update_total_value();
					update_returns();
				}
			}
		});
		
		txt_expectedvalue.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double number = 0;
				String last_number = Double.toString(item_expectedvalue);
				
				txt_expectedvalue.setText(txt_expectedvalue.getText().replace(',', '.'));
				
				if(txt_expectedvalue.getText().isEmpty()) {
					txt_expectedvalue.setText(last_number);
				}
				
				if( txt_expectedvalue.getText().charAt(txt_expectedvalue.getText().length() - 1) == '.' ) {
					txt_expectedvalue.setText( txt_expectedvalue.getText().substring(0, txt_expectedvalue.getText().length() - 1) );
				}
				
				if( txt_expectedvalue.getText().charAt(0) == '.' ) {
					txt_expectedvalue.setText('0' + txt_expectedvalue.getText());
				}
				
				try{
					number = Double.parseDouble(txt_expectedvalue.getText());
					
					if(number < 0){
						txt_expectedvalue.setText(last_number);
					    JOptionPane.showMessageDialog(null, "Please, only use values equal or over zero in this field.\\nE.g.: 35");
					} else if( !last_number.equals(txt_expectedvalue.getText()) ) {
						item_expectedvalue = add_tax(number);
						txt_expectedvalue.setText(Double.toString(item_expectedvalue));
					}
					
				} catch(NumberFormatException ex) {
					txt_expectedvalue.setText(last_number);
				    JOptionPane.showMessageDialog(null, "Please, only use numbers in this field.\\nE.g.: 0.25 ");
				}
			}
		});
		
		btn_accept.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				boolean can_continue = false;
				
				try {
					Double.parseDouble(txt_cost.getText());
					Integer.parseInt(txt_quantity.getText());
					Double.parseDouble(txt_expectedvalue.getText());
					can_continue = true;
					
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "One or more inputs are incorrect, re-enter new values please.");
				}
				
				if(can_continue) {
					task_done = true;
					close_form();
				}
			}
		});
		
		btn_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				close_form();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	close_form();
            }
        });
	}
	
	public void set_form_visible() {
		mainform_frame.setEnabled(false);
		mainform_frame.setVisible(false);
		
		task_done = false;
		lbl_itemicon.setIcon(item_icon);
		lbl_itemname.setText(item_name);
		txt_cost.setText(Double.toString(item_cost));
		txt_expectedvalue.setText(Double.toString(item_expectedvalue));
		txt_quantity.setText(Integer.toString(item_quantity));
		update_total_cost();
		update_total_value();
		update_returns();
		
		frame.setVisible(true);
		frame.requestFocus();
	}
	
	private void close_form() {
		mainform_frame.setEnabled(true);
		mainform_frame.setVisible(true);
		frame.setVisible(false);
	}
	
	private void update_total_cost() {
		double total_cost = (item_cost * item_quantity);
		total_cost = truncate(total_cost);
		lbl_totalcost.setText("Total cost: " + total_cost);
	}
	
	private void update_total_value() {
		double total_value = ((item_price * 0.8695) * item_quantity);
		total_value = truncate(total_value);
		lbl_totalvalue.setText("Total value: " + total_value);
	}
	
	private void update_returns() {
		double returns = ( ((item_price * 0.8695) * item_quantity) - (item_cost * item_quantity) );
		returns =  truncate(returns);
		if(returns >= 0) {
			lbl_returns.setText("Returns: " + returns);
		} else {
			lbl_returns.setText("Losses: " + returns);
		}
	}
	
	private double truncate(double value) {
		//Leaves only two decimals without rounding anything
		return (Math.floor(value * 100) / 100);
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
	
	//Getters
	public boolean get_task_done() {
		return task_done;
	}
	
	public int get_item_table_row() {
		return item_table_row;
	}
	
	public String get_item_name() {
		return item_name;
	}
	
	public double get_item_cost() {
		return item_cost;
	}
	
	public double get_item_expectedvalue() {
		return item_expectedvalue;
	}
	
	public int get_item_quantity() {
		return item_quantity;
	}
	
	//Setters
	public void set_mainform_frame(JFrame frame) {
		mainform_frame = frame;
	}
	
	public void set_item_table_row(int table_row) {
		item_table_row = table_row;
	}
	
	public void set_item_icon(Object icon) {
		item_icon = (ImageIcon) icon;
	}
	
	public void set_item_name(String name) {
		item_name = name;
	}
	
	public void set_item_cost(double cost) {
		item_cost = cost;
	}
	
	public void set_item_price(double price) {
		item_price = price;
	}
	
	public void set_item_expectedvalue(double expectedvalue) {
		item_expectedvalue = expectedvalue;
	}
	
	public void set_item_quantity(int quantity) {
		item_quantity = quantity;
	}
	
}
