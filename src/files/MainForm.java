package files;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.UncheckedIOException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainForm {
	
	//GUI misc
	private JFrame frame;
	private JTable main_data_container;
	private DefaultTableModel table_model;
	private static Additempopup additempopup_object;
	private static ItemEdit itemedit_object;
	private ButtonColumn btn_col;
	
	//Settings variables
	private static Settings setting;
	private static int profit_mode;
	private static int currency;
	
	//Data usage variables
	private static Dbconnector database_manager;
	private static Map<String, String> itemname_id_list; //Used for removing and updating items. Format is ("Item Name", "Item ID").
	
	
	/*Launch the application.*/
	public static void main(String[] args) {
		additempopup_object = new Additempopup();
		
		itemedit_object = new ItemEdit();
		
		itemname_id_list = new TreeMap<String, String>();
		
		setting = new Settings();
		profit_mode = setting.get_profit_mode();
		currency = setting.get_currency();
		
		database_manager = new Dbconnector();
		database_manager.create_tables();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*Create the application.*/
	public MainForm() {
		initialize();
	}
	
	/*Initialize the contents of the frame*/
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.control);
		frame.setBounds(100, 100, 1055, 500);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		frame.setJMenuBar(menuBar);
		
		JMenu mnItem = new JMenu("Item");
		mnItem.setForeground(Color.BLACK);
		menuBar.add(mnItem);
		
		JMenu mnSetting = new JMenu("Settings");
		mnSetting.setForeground(Color.BLACK);
		menuBar.add(mnSetting);
		
		JMenuItem mntmAddItem = new JMenuItem("Add item");
		mntmAddItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmAddItem.setIcon(null);
		mnItem.add(mntmAddItem);
		
		JMenuItem mntmRemoveItem = new JMenuItem("Remove item");
		mntmRemoveItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mnItem.add(mntmRemoveItem);
		
		JMenuItem mntmChangeCurrency = new JMenuItem("Change currency");
		mntmChangeCurrency.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSetting.add(mntmChangeCurrency);
		
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton btn_refresh = new JButton("Refresh");
		btn_refresh.setForeground(Color.BLACK);
		menuBar.add(btn_refresh);
		
		JPanel panelinfo = new JPanel();
		panelinfo.setPreferredSize(new Dimension(10, 40));
		panelinfo.setBackground(SystemColor.controlShadow);
		frame.getContentPane().add(panelinfo, BorderLayout.SOUTH);
		panelinfo.setLayout(new GridLayout(1, 0, 0, 0));
		
		final JLabel lblCurrency = new JLabel("Currency: " + setting.get_currency_name());
		lblCurrency.setBorder(new LineBorder(Color.BLACK, 2));
		lblCurrency.setForeground(Color.BLACK);
		lblCurrency.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblCurrency);
		
		final JLabel lblKeyprice = new JLabel("Key price: ");
		lblKeyprice.setBorder(new LineBorder(Color.BLACK, 2));
		lblKeyprice.setBackground(SystemColor.menu);
		lblKeyprice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblKeyprice.setForeground(Color.BLACK);
		panelinfo.add(lblKeyprice);
		
		JLabel lblTotalcost = new JLabel("Total cost: ");
		lblTotalcost.setBorder(new LineBorder(Color.BLACK, 2));
		lblTotalcost.setForeground(Color.BLACK);
		lblTotalcost.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblTotalcost);
		
		JLabel lblTotalvalue = new JLabel("Total value: ");
		lblTotalvalue.setBorder(new LineBorder(Color.BLACK, 2));
		lblTotalvalue.setForeground(Color.BLACK);
		lblTotalvalue.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblTotalvalue);
		
		JLabel lblTotalprofit = new JLabel("Profit: ");
		lblTotalprofit.setBorder(new LineBorder(Color.BLACK, 2));
		lblTotalprofit.setForeground(Color.BLACK);
		lblTotalprofit.setFont(new Font("Arial", Font.PLAIN, 12));
		panelinfo.add(lblTotalprofit);
		
		table_model = new DefaultTableModel();
		table_model.addColumn("");
		table_model.addColumn("Item");
		table_model.addColumn("Quantity");
		table_model.addColumn("Cost");
		table_model.addColumn("Profit (%)");
		table_model.addColumn("<html>Expected value<br>(Tax)");
		table_model.addColumn("<html>Lowest price<br>(Tax)");
		table_model.addColumn("<html>Median price<br>(Tax)");
    	table_model.addColumn("Volume");
    	
		main_data_container = new JTable(table_model) {
			 public boolean getScrollableTracksViewportWidth() {
				   return getPreferredSize().width < getParent().getWidth();
			 }
			 
			 public void changeSelection( int row, int col, boolean toggle, boolean expand ) {
			     if( col == 1) {
			    	 super.changeSelection( row, col, true, expand );
			     }
			}
			 
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				if(column == 0){
					return ImageIcon.class;
				}
				return getValueAt(0, column).getClass();
            }
		};
		
		main_data_container.setFocusable(true);
		main_data_container.setRowSelectionAllowed(false);
		main_data_container.getTableHeader().setReorderingAllowed(false);
		main_data_container.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		main_data_container.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		main_data_container.setRowHeight(30);
		
		Action view_item_info = new AbstractAction() { //Column button event
		    public void actionPerformed(ActionEvent e) {
		    	int selected_item_row = Integer.valueOf(e.getActionCommand());
		    	itemedit_object.set_item_icon(main_data_container.getValueAt(selected_item_row, 0));
		    	itemedit_object.set_item_name(table_model.getValueAt(selected_item_row, 1).toString());
		    	itemedit_object.set_item_cost(Double.parseDouble(table_model.getValueAt(selected_item_row, 3).toString()));
		    	itemedit_object.set_item_expectedvalue(Double.parseDouble(table_model.getValueAt(selected_item_row, 5).toString()));
		    	itemedit_object.set_item_price(Double.parseDouble(table_model.getValueAt(selected_item_row, 6).toString()));
		    	itemedit_object.set_item_quantity(Integer.parseInt(table_model.getValueAt(selected_item_row, 2).toString()));
		    	itemedit_object.set_item_table_row(selected_item_row);
		    	itemedit_object.set_form_visible();
		    }
		};
		
		btn_col = new ButtonColumn(main_data_container, view_item_info, 1);
		
		main_data_container.getColumnModel().getColumn(0).setMinWidth(32);
		main_data_container.getColumnModel().getColumn(0).setMaxWidth(32);
		main_data_container.getColumnModel().getColumn(0).setResizable(false);
		main_data_container.getColumnModel().getColumn(1).setMinWidth(320);
		main_data_container.getColumnModel().getColumn(2).setMinWidth(70);
		main_data_container.getColumnModel().getColumn(3).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(4).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(5).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(6).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(7).setMinWidth(100);
		main_data_container.getColumnModel().getColumn(8).setMinWidth(100);
		
		JScrollPane scroll_pane = new JScrollPane (main_data_container);
		scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll_pane);
		scroll_pane.setColumnHeader(new JViewport() {
	        @Override public Dimension getPreferredSize() {
	          Dimension dim = new Dimension(0,40);
	          return dim;
	        }
	    });
		
		additempopup_object.set_main_frame_source(frame);
		
		itemedit_object.set_mainform_frame(frame);
		
		//After all the UI elements have been initialized, retrieve saved data from db and insert it in the JTable.
		ArrayList<ArrayList<String>> table_row_list = new ArrayList<ArrayList<String>>();
		table_row_list = get_database_data(); //Get data from db and store it into lists.
		
		for(ArrayList<String> element : table_row_list) { //Read the list and add the table rows with the obtained data.
			ImageIcon icon;
			try{
				if(!(element.get(0) == null)) {
					icon = new ImageIcon(ImageIO.read(new File(element.get(0)))); //Get the item icon through the icon URL.
				} else {
					icon = get_default_icon();
				}
			} catch( IOException NullPointerException) {
				icon = get_default_icon();
			}
			
			//Add the new row.
			table_model.addRow(new Object[] { icon,
					element.get(1),
					element.get(2),
					element.get(3),
					element.get(4),
					element.get(5),
					element.get(6),
					element.get(7),
					element.get(8)});
			
			//Fill the itemname_id_list map
			itemname_id_list.put(element.get(1), element.get( element.size() - 1 ));
		}
		
		//Events
		frame.addWindowListener(new WindowAdapter() { //App close
			
            public void windowClosing(WindowEvent e) {
            	setting.save_settings();
            }
        });
		
		frame.addComponentListener(new ComponentAdapter() { //Frame made visible
			
			   public void componentShown(ComponentEvent e) {
				   
				   if( additempopup_object.get_task_done() ) {
					   add_new_item();
				   }
				   
				   if( itemedit_object.get_task_done() ) {
					   update_item();
				   }
			   }
		});
		
		mntmAddItem.addMouseListener(new MouseAdapter() { //Add item
			
			public void mousePressed(MouseEvent e) {
				additempopup_object.reset_fields();
				additempopup_object.set_pop_up_visible();
			}
		});
		
		mntmRemoveItem.addMouseListener(new MouseAdapter() { //Remove item
			
			public void mousePressed(MouseEvent e) {
				
				//The JOptionPane returns null if the user clicked "Cancel", closed the dialog or triggered this event while the JTable was empty.
				String selected_item = (String) JOptionPane.showInputDialog(
				                    frame,
				                    "Select which item you want to remove:",
				                    "Remove item",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    itemname_id_list.keySet().toArray(),
				                    null);
				
				if ((selected_item != null) && (selected_item.length() > 0)) {
					String id;
					boolean success;
					
					id = itemname_id_list.get(selected_item);
					
					success = database_manager.delete_table_row(id);
					
					if(success) {
						int row_index;
						
						for(row_index=0; row_index < table_model.getRowCount(); row_index++) {
							if(table_model.getValueAt(row_index, 1).equals(selected_item)) {
								break;
							}
						}
						
						table_model.removeRow(row_index);
						itemname_id_list.remove(selected_item);
					}
				}
			}
		});
		
		mntmChangeCurrency.addMouseListener(new MouseAdapter() { //Change currency
			
			public void mousePressed(MouseEvent e) {
				String current_currency_name = setting.get_currency_name();
				String new_currency_name;
				
				setting.change_currency(frame);
				
				new_currency_name = setting.get_currency_name();
				
				if((new_currency_name != null) && (!new_currency_name.equals(current_currency_name))) {
					currency = setting.get_currency();
					lblCurrency.setText("Currency: " + setting.get_currency_name());
					JOptionPane.showMessageDialog(frame, "Click the Refresh button for change to make effect.");
				}
			}
		});
		
		btn_refresh.addMouseListener(new MouseAdapter() { //Refresh item market data.
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				ArrayList<String[]> market_data = new ArrayList<String[]>(); //Usage: (hash_name, game_id, lowest_price, median_price, volume)
				
				String hash_name;
				String game_id;
				String item_id;
				String lowest_price = "";
				String median_price = "";
				String volume = "";
				double item_cost = 0;
				String item_name = "";
				int item_row;
				Boolean match;
				
				ResultSet data;
				
				data = database_manager.get_refresh_data();
				
				try {
					if(data.next()) {
						do {
							item_row = -1;
							
							hash_name = data.getString("market_hash_name");
							game_id = data.getString("game_id");
							item_id = data.getString("id");
							
							
							//Check if data has been gotten.
							match = false;
							for(String[] element : market_data) {
								if(element[0].equals(hash_name) && element[1].equals(game_id)) {
									match = true;
									lowest_price = element[2];
									median_price = element[3];
									volume = element[4];
									break;
								}
							}
							
							//Get market data if it wasn't in the list.
							if(match == false) {
								String[] item_market_data;
								
								item_market_data = get_item_market_data(game_id, hash_name);
								lowest_price = item_market_data[0];
								median_price = item_market_data[1];
								volume = item_market_data[2];
								
								//Store data
								market_data.add(new String[] {hash_name, game_id, lowest_price, median_price, volume});
							}
							
							//Find the item's name in the itemname_id_list map by id. (This is the name used in the JTable)
							for(Map.Entry<String, String> map : itemname_id_list.entrySet()) {
								if(item_id.equals(map.getValue())) {
									item_name = map.getKey();
									break;
								}
							}
							
							//Find the item's row.
							int index;
							for(index = 0; index < table_model.getRowCount(); index++) {
								if(item_name.equals(table_model.getValueAt(index, 1))) {
									item_row = index;
									break;
								}
							}
							
							//Calculate new profit value.
							try {
								item_cost = Double.parseDouble(table_model.getValueAt(item_row, 3).toString());
							} catch(NumberFormatException ex) {
								item_cost = 0;
							}
							String item_profit = profit_calc(item_cost, lowest_price);
							
							//Insert the new data into the cells.
							if (item_row > -1) {
								table_model.setValueAt(item_profit, item_row, 4);
								table_model.setValueAt(lowest_price, item_row, 6);
								table_model.setValueAt(median_price, item_row, 7);
								table_model.setValueAt(volume, item_row, 8);
							}
						} while(data.next());
					}
					data.close();
				} catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "Something happened while trying to read data from the database.\n" + ex.toString());
				}
			}
		});
		
		
	}
	
	private void add_new_item() { //Add item to JTable and save data.
		int item_id;
		String item_name;
		int item_name_count;
		String item_market_link;
		String item_game_id;
		String item_market_hash_name;
		double item_cost;
		int item_quantity;
		double item_expected_value;
		double item_expected_value_tax;
		ImageIcon item_icon;
		String icon_URL;
		String[] item_market_data;
		String item_lowest_price = "";
		String item_median_price = "";
		String item_volume = "";
		String item_profit = "";
		
		//Get pop-up form data.
		item_name = additempopup_object.get_item_name();
		
		item_name_count = database_manager.get_new_name_count_val(item_name);
		
		item_market_link = additempopup_object.get_item_market_link();
		
		item_game_id = additempopup_object.get_item_game_id();
		
		item_market_hash_name = additempopup_object.get_item_market_hash_name();
		
		item_cost = additempopup_object.get_item_cost();
		
		item_quantity = additempopup_object.get_item_quantity();
		
		item_expected_value = truncate(additempopup_object.get_item_expected_value());
		
		item_expected_value_tax = add_tax(item_expected_value);
		
		icon_URL = System.getProperty("user.home") + "\\SteamPortfolio\\icons\\" + item_market_hash_name + ".png";
		
		if(additempopup_object.get_item_icon() == null) {
			item_icon = get_default_icon();
		} else {
			item_icon = new ImageIcon(additempopup_object.get_item_icon());
			
			try {
				ImageIO.write(additempopup_object.get_item_icon(), "png", new File(icon_URL));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Couldn't save item icon.\n" + e1.toString());
				icon_URL = null;
			}
		}
		
		//Get the item's market data from the JSON.
		item_market_data = get_item_market_data(item_game_id, item_market_hash_name);
		
		item_lowest_price = item_market_data[0];
		
		item_median_price = item_market_data[1];
		
		item_volume = item_market_data[2];
		
		item_profit = profit_calc(item_cost, item_lowest_price);
		
		//Insert new data in the database and get the item's id if successful.
		item_id = database_manager.insert_new_item(item_name, item_name_count, item_market_link, item_game_id, item_market_hash_name, icon_URL, item_cost, item_quantity, item_expected_value, item_lowest_price, item_median_price, item_volume);
		
		//If returned value from insert_new_item() is -1 that means the new data couldn't be inserted into the db.
		//And thus data won't neither be saved nor displayed on the JTable.
		if(item_id != -1) {
			item_name = (item_name_count > 0) ? item_name + " (" + item_name_count + ")" : item_name;
			
			itemname_id_list.put(item_name, Integer.toString(item_id));
			
			table_model.addRow(new Object[] { item_icon,
					item_name,
					item_quantity,
					item_cost,
					item_profit,
					item_expected_value_tax,
					item_lowest_price,
					item_median_price,
					item_volume});
		}
	}
	
	private void update_item() {
		String id = "";
		String name = itemedit_object.get_item_name();
		double new_cost = itemedit_object.get_item_cost();
		double new_expectedvalue = itemedit_object.get_item_expectedvalue();
		int new_quantity = itemedit_object.get_item_quantity();
		boolean success;
		int table_row_num = itemedit_object.get_item_table_row();
		
		//Getting the item's id by its name in the map.
		for(Map.Entry<String, String> map : itemname_id_list.entrySet()) {
			if( name.equals(map.getKey()) ) {
				id = map.getValue();
				break;
			}
		}
		
		//Updating database row.
		success = database_manager.update_item_data( id, Double.toString(new_cost), Integer.toString(new_quantity), Double.toString(new_expectedvalue) );
		
		//Update JTable on success.
		if(success) {
			String new_profit = profit_calc( new_cost, table_model.getValueAt(table_row_num, 6).toString() );
			
			table_model.setValueAt(new_quantity, table_row_num, 2);
			table_model.setValueAt(new_cost, table_row_num, 3);
			table_model.setValueAt(new_profit , table_row_num, 4);
			table_model.setValueAt(new_expectedvalue, table_row_num, 5);
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
	
	private ImageIcon get_default_icon() {
		return new ImageIcon(getClass().getResource("/images/Default_Item_Icon.png"));
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
			JOptionPane.showMessageDialog(null, "An invalid profit display mode has been selected.\nSwitching to default.");
			profit_mode = 0;
			profit_value =  ((price * 100) / cost) - 100;
			profit_value = truncate(profit_value);
			return String.valueOf(profit_value) + '%';
		}
	}
	
	private static String get_clean_value(String value) {
		int index;
		
		for(index = 0; index < value.length(); index++) {
			if(value.charAt(index) == ',') {
				value = value.replace(".", "");
				value = value.replace(",", ".");
				break;
			}
		}
		value = value.replaceAll("[^\\d.]", "");
		
		return value;
	}
	
	private static String[] get_item_market_data(String game_id, String market_hash_name) {
		String link =("https://steamcommunity.com/market/priceoverview/?currency=" + currency + "&appid=" + game_id + "&market_hash_name=" + market_hash_name);
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = null;
		String json;
		String[] values = {"0", "0", "0"};;
		
		//Getting the JSON
		try {
			json = Jsoup.connect(link).ignoreContentType(true).execute().body();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't get the item's market data.\n" + e.toString());
			return values;
		} catch(UncheckedIOException e1) {
			JOptionPane.showMessageDialog(null, "Connection problem:\n" + e1.toString());
			return values;
		}
		
		//Parsing and using it
		try {
			obj = (JSONObject) jsonparser.parse(json);
		} catch (ParseException e) {
			e.printStackTrace();
			return values;
		}
		
		if(obj.get("success").toString().equals("false")) {
			JOptionPane.showMessageDialog(null, "Couldn't get the item's market data.");
			values[0] = "None";
			values[1] = "None";
			values[2] = "None";
			return values;
		}
		
		if(obj.get("lowest_price") != null){
			values[0] = obj.get("lowest_price").toString();
			values[0] = get_clean_value(values[0]);
		}
		
		if(obj.get("median_price") != null){
			values[1] = obj.get("median_price").toString();
			values[1] = get_clean_value(values[1]);
		}
		
		if(obj.get("volume") != null){
			values[2] = obj.get("volume").toString();
		}
		
		return values;
	}
	
	private ArrayList<ArrayList<String>> get_database_data() {
		//For now, the columns last_lowest_price, last_median_price and last_volume won't be used.
		//NEED FIELDS: iconURL, id, name, quantity, cost, expected_value, game_id and market_hash_name.
		
		ArrayList<ArrayList<String>> row_list = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		
		ArrayList<String[]> stored_market_data = new ArrayList<String[]>(); //Usage: (market_hash_name, game_id, lowest_price, median_price, volume)
		
		int id;
		String name;
		int name_count;
		String iconURL;
		int quantity;
		double cost;
		String profit;
		double expected_value;
		String game_id;
		String market_hash_name;
		String[] market_data;
		String lowest_price = "";
		String median_price = "";
		String volume = "";
		
		ResultSet data = database_manager.query_data();
		
		try {
			if(data.next()) {
				do {
					//Read the contents of the db row.
					iconURL = data.getString("iconURL");
					name = data.getString("name");
					name_count = data.getInt("name_count");
					quantity = data.getInt("quantity");
					cost = data.getDouble("cost");
					expected_value = data.getDouble("expected_value");
					id = data.getInt("id");
					game_id = data.getString("game_id");
					market_hash_name = data.getString("market_hash_name");
					
					//Check if the current market_hash_name and game_id had already been used.
					//If so just loop through stored data until a match is found and grab the stored data.
					boolean found_match = false;
					for(String[] element : stored_market_data) {
						if(element[0].equals(market_hash_name) && element[1].equals(game_id)) {
							found_match = true;
							lowest_price = element[2];
							median_price = element[3];
							volume = element[4];
							break;
						}
					}
					
					//Get item market data if it hasn't already been gotten.
					if(found_match == false) {
						market_data = get_item_market_data(game_id, market_hash_name);
						lowest_price = market_data[0];
						median_price = market_data[1];
						volume = market_data[2];
						
						//Store the new data
						stored_market_data.add(new String[] {market_hash_name, game_id, lowest_price, median_price, volume});
					}
					
					profit = profit_calc(cost, lowest_price);
					expected_value = add_tax(expected_value);
					
					//Modify name according to name_count
					name = (name_count > 0) ? name + " (" + name_count + ")" : name;
					
					//Add everything to a list which will contain the values of a table row.
					row = new ArrayList<String>();
					row.add(iconURL);
					row.add(name);
					row.add(Integer.toString(quantity));
					row.add(Double.toString(cost));
					row.add(profit);
					row.add(Double.toString(expected_value));
					row.add(lowest_price);
					row.add(median_price);
					row.add(volume);
					row.add(Integer.toString(id));
					
					row_list.add(row);
				} while (data.next());
			}
			data.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Something happened while reading the data from the database.\n" + e.toString());
			e.printStackTrace();
		}
		
		return row_list;
	}
	
}
