package files;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLHandshakeException;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//Watch out for out of range numeric inputs

public class Additempopup {
	private JFrame frame = new JFrame();
	private JPanel panelmain = new JPanel();
	private Box Boxitem = Box.createHorizontalBox();
	private JLabel lblitem = new JLabel();
	private JLabel lblitemname = new JLabel();
	private Box Boxmarketlink = Box.createHorizontalBox();
	private JLabel lblmarketlink = new JLabel();
	private JTextField textmarketlink = new JTextField();
	private String last_valid_link;
	private Box Boxcost = Box.createHorizontalBox();
	private JLabel lblitemcost = new JLabel();
	private JTextField textitemcost = new JTextField();
	private Box Boxquantity = Box.createHorizontalBox();
	private JLabel lblitemquantity = new JLabel();
	private JTextField textitemquantity = new JTextField();
	private Box Boxexpectedvalue = Box.createHorizontalBox();
	private JLabel lblitemexpectedvalue = new JLabel();
	private JTextField textexpectedvalue = new JTextField();
	private Box Boxexpectedvaluekey;
	private JPanel panelbuttons = new JPanel();
	private JButton btnaccept = new JButton();
	private Component horizontalStrut;
	private JButton btncancel = new JButton();
	private JFrame main_frame = new JFrame();
	private JTextField add_new_item_trigger;
	private JTextField last_focused_field;
	private BufferedImage item_icon;
	
	public Additempopup() {
		java.lang.System.setProperty("https.protocols", "TLSv1.2");
		//java.lang.System.setProperty("Dhttps.cipherSuites", "TLS_RSA_WITH_AES_256_CBC_SHA256");
		
		frame.setBounds(100, 100, 700, 440);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelmain.setMaximumSize(new Dimension(9999, 9999));
		panelmain.setPreferredSize(new Dimension(700, 400));
		panelmain.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.getContentPane().add(panelmain, BorderLayout.CENTER);
		panelmain.setLayout(new BoxLayout(panelmain, BoxLayout.Y_AXIS));
		
		Boxitem.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxitem.setMinimumSize(new Dimension(40, 40));
		Boxitem.setMaximumSize(new Dimension(700, 300));
		Boxitem.setPreferredSize(new Dimension(40, 40));
		panelmain.add(Boxitem);
		
		lblitem.setMaximumSize(new Dimension(40, 20));
		lblitem.setFont(new Font("Arial", Font.PLAIN, 16));
		lblitem.setText("Item: ");
		Boxitem.add(lblitem);
		
		lblitemname.setHorizontalTextPosition(SwingConstants.CENTER);
		lblitemname.setMaximumSize(new Dimension(600, 20));
		lblitemname.setFont(new Font("Arial", Font.PLAIN, 16));
		lblitemname.setText("");
		Boxitem.add(lblitemname);
		
		Boxmarketlink.setMaximumSize(new Dimension(9999, 300));
		Boxmarketlink.setMinimumSize(new Dimension(40, 40));
		Boxmarketlink.setPreferredSize(new Dimension(40, 40));
		Boxmarketlink.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelmain.add(Boxmarketlink);
		
		lblmarketlink.setPreferredSize(new Dimension(106, 14));
		lblmarketlink.setMinimumSize(new Dimension(10, 14));
		lblmarketlink.setMaximumSize(new Dimension(106, 14));
		lblmarketlink.setFont(new Font("Arial", Font.PLAIN, 14));
		lblmarketlink.setText("Market item link: ");
		Boxmarketlink.add(lblmarketlink);
		
		textmarketlink.setFont(new Font("Arial", Font.PLAIN, 14));
		textmarketlink.setMaximumSize(new Dimension(9999, 30));
		textmarketlink.setPreferredSize(new Dimension(570, 30));
		Boxmarketlink.add(textmarketlink);
		textmarketlink.setColumns(10);
		
		Boxcost.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxcost.setPreferredSize(new Dimension(40, 40));
		Boxcost.setMinimumSize(new Dimension(40, 40));
		Boxcost.setMaximumSize(new Dimension(9999, 300));
		panelmain.add(Boxcost);
		
		lblitemcost.setFont(new Font("Arial", Font.PLAIN, 14));
		lblitemcost.setText("Item cost: ");
		Boxcost.add(lblitemcost);
		
		textitemcost.setText("0");
		textitemcost.setPreferredSize(new Dimension(610, 30));
		textitemcost.setMaximumSize(new Dimension(9999, 30));
		textitemcost.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxcost.add(textitemcost);
		textitemcost.setColumns(10);
		
		Boxquantity.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxquantity.setMaximumSize(new Dimension(9999, 300));
		Boxquantity.setMinimumSize(new Dimension(40, 40));
		Boxquantity.setPreferredSize(new Dimension(40, 40));
		panelmain.add(Boxquantity);
		
		lblitemquantity.setFont(new Font("Arial", Font.PLAIN, 14));
		lblitemquantity.setText("Quantity: ");
		Boxquantity.add(lblitemquantity);
		
		textitemquantity.setText("0");
		textitemquantity.setPreferredSize(new Dimension(610, 30));
		textitemquantity.setMaximumSize(new Dimension(9999, 30));
		textitemquantity.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxquantity.add(textitemquantity);
		textitemquantity.setColumns(10);
		
		Boxexpectedvalue.setPreferredSize(new Dimension(40, 40));
		Boxexpectedvalue.setMinimumSize(new Dimension(40, 40));
		Boxexpectedvalue.setMaximumSize(new Dimension(9999, 300));
		Boxexpectedvalue.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelmain.add(Boxexpectedvalue);
		
		lblitemexpectedvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		lblitemexpectedvalue.setText("Expected value: ");
		Boxexpectedvalue.add(lblitemexpectedvalue);
		
		textexpectedvalue.setText("0");
		textexpectedvalue.setPreferredSize(new Dimension(540, 30));
		textexpectedvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		textexpectedvalue.setMaximumSize(new Dimension(9999, 30));
		Boxexpectedvalue.add(textexpectedvalue);
		textexpectedvalue.setColumns(10);
		
		Boxexpectedvaluekey = Box.createHorizontalBox();
		Boxexpectedvaluekey.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxexpectedvaluekey.setMaximumSize(new Dimension(9999, 30));
		Boxexpectedvaluekey.setMinimumSize(new Dimension(40, 40));
		Boxexpectedvaluekey.setPreferredSize(new Dimension(4, 40));
		
		panelbuttons.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelbuttons.setPreferredSize(new Dimension(500, 40));
		frame.getContentPane().add(panelbuttons, BorderLayout.SOUTH);
		panelbuttons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnaccept.setHorizontalAlignment(SwingConstants.LEFT);
		btnaccept.setText("Accept");
		panelbuttons.add(btnaccept);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(200, 0));
		panelbuttons.add(horizontalStrut);
		
		btncancel.setHorizontalAlignment(SwingConstants.RIGHT);
		btncancel.setText("Cancel");
		panelbuttons.add(btncancel);
		
		add_new_item_trigger = new JTextField();
		add_new_item_trigger.setVisible(false);
		
		main_frame.setVisible(false);
		
		last_focused_field = new JTextField();
		
		last_valid_link = "";
		
		item_icon = null;
		
		frame.setVisible(false);
		
		//EVENTS
		FocusAdapter setting_number = (new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				JTextField source_textfield = (JTextField) e.getSource();
				if(source_textfield.getText().equals("0") || source_textfield.getText().equals("0.0")) {
					source_textfield.setText("");
				}
				last_focused_field = (JTextField) e.getComponent();
			}
		});
		textitemcost.addFocusListener(setting_number);
		textitemquantity.addFocusListener(setting_number);
		textexpectedvalue.addFocusListener(setting_number);
		
		FocusAdapter doublecheck = (new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double number = 0;
				String field_content;
				JTextField source_textfield = (JTextField) e.getSource();
				
				field_content = source_textfield.getText();
				field_content = field_content.replace(',', '.');
				
				if(field_content.isEmpty()) {
					field_content = "0";
				}else if(field_content.charAt(field_content.length() - 1) == '.') {
					field_content = field_content.substring(0, field_content.length() - 1);
				}
				
				if(field_content.charAt(0) == '.') {
					field_content = '0' + field_content;
				}
				
				source_textfield.setText(field_content);
				
				try{
					number = Double.parseDouble(source_textfield.getText());
				}catch(NumberFormatException ex){
					Component set_source_textfield = e.getComponent();
					if (set_source_textfield instanceof JTextField) {
				          ((JTextField)set_source_textfield).setText("0");
				          JOptionPane.showMessageDialog(null, "Please, only use numbers in this field.\nE.g.: 0.25 ");
				    }
				}finally{
					Component set_source_textfield = e.getComponent();
					if(number < 0) {
						textitemquantity.setText("0");
						
					    JOptionPane.showMessageDialog(null, "Please, only use values equal or over zero in this field.\nE.g.: 2.5 ");
					    
					    if (set_source_textfield instanceof JTextField) {
					          ((JTextField)set_source_textfield).setText("0");
					    }
					}
			    }
			}
		});
		
		textitemcost.addFocusListener(doublecheck);
		textexpectedvalue.addFocusListener(doublecheck);
		
		textitemquantity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int number = 0;
				
				if(textitemquantity.getText().isEmpty()) {
					textitemquantity.setText("0");
				}
				
				try{
					number = Integer.parseInt(textitemquantity.getText());
				}catch(NumberFormatException ex){
					textitemquantity.setText("0");
				    JOptionPane.showMessageDialog(null, "Invalid input:\nOnly use integer values between 0 and 2147483647 are accepted.\nE.g.: 10 ");
				}finally{
					if(number < 0){
						textitemquantity.setText("0");
					    JOptionPane.showMessageDialog(null, "Please, only use integer numbers equal or over zero in this field.\nE.g.: 10 ");
					}
				}
			}
		});
		
		textmarketlink.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try{
					if(!(Color.GREEN == textmarketlink.getForeground())){
						textmarketlink.setForeground(Color.BLACK);
						textmarketlink.setText("");
					}
				}catch(StringIndexOutOfBoundsException ex){
					textmarketlink.setForeground(Color.BLACK);
					textmarketlink.setText("");
				}
			}
		});
		
		textmarketlink.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try{
					if(textmarketlink.getText().substring(0, 35).equals("steamcommunity.com/market/listings/")) {
						textmarketlink.setText("https://" + textmarketlink.getText());
						textmarketlink.setForeground(Color.GREEN);
					}else if(textmarketlink.getText().substring(0, 43).equals("https://steamcommunity.com/market/listings/")){
						textmarketlink.setForeground(Color.GREEN);
					}else{
						textmarketlink.setForeground(Color.RED);
						textmarketlink.setText("Wrong input, paste here the item's link to its Steam Community Market page. (Ctrl + V)");
					}
				}catch(StringIndexOutOfBoundsException ex){
					if(textmarketlink.getText().equals("")){
						textmarketlink.setForeground(Color.GRAY);
						textmarketlink.setText("Insert here the item's Steam Community Market link by pasting it. (Ctrl + V)");
					}else{
						textmarketlink.setForeground(Color.RED);
						textmarketlink.setText("Wrong input, just paste here the item's link to its Steam Community Market page. (Ctrl + V)");
					}
				}
				
				if(Color.GREEN == textmarketlink.getForeground()) {
					if(!(last_valid_link.equals(textmarketlink.getText()))) {
						get_item_name_image();
					}
				}
			}
		});
		
		btnaccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				double item_cost;
				double item_expected_value;
				
				if(last_focused_field.getText().equals("")) {
					last_focused_field.setText("0");
				}
				
				try{
					Integer.parseInt(textitemquantity.getText());
					item_cost = Double.parseDouble(textitemcost.getText());
					item_expected_value = Double.parseDouble(textexpectedvalue.getText());
				} catch(NumberFormatException ex) {
					return;
				}
				
				boolean can_continue = false;
				
				if(item_cost > item_expected_value) {
					Object[] options = {"Go back", "Skip"};
					
					int chosen_option = JOptionPane.showOptionDialog(frame,
						    "The item's cost is higher than its expected value.", 
						    "Warning", 
						    JOptionPane.YES_NO_OPTION, 
						    JOptionPane.WARNING_MESSAGE, 
						    null, 
						    options, 
						    options[0]);
					
					if(chosen_option == 0) {
						textexpectedvalue.requestFocus();
						textexpectedvalue.setText("0");
					} else if(chosen_option == 1) {
						can_continue = true;
					}
				}else{
					can_continue = true;
				}
				
				if(can_continue && textmarketlink.getForeground() == Color.GREEN) {
					if(!(last_valid_link.equals(textmarketlink.getText()))) {
						get_item_name_image();
						return;
					}
					trigger_add_new_item();
				}
			}
		});
		
		btncancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				reset_fields();
				main_frame.setEnabled(true);
				frame.setVisible(false);
			}
		});
		
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
				reset_fields();
				main_frame.setEnabled(true);
				frame.setVisible(false);
            }
        });
	}
	
	public void reset_fields() {
		textmarketlink.setForeground(Color.GRAY);
		textmarketlink.setText("Insert here the item's Steam Community Market link by pasting it. (Ctrl + V)");
		last_valid_link = "";
		textitemcost.setText("0");
		textitemquantity.setText("0");
		textexpectedvalue.setText("0");
		lblitemname.setText("");
	}
	
	public void trigger_add_new_item() {
		main_frame.setEnabled(true);
		frame.setVisible(false);
		add_new_item_trigger.setVisible(true);
		add_new_item_trigger.requestFocus();
	}
	
	public void set_pop_up_visible() {
		main_frame.setEnabled(false);
		frame.setVisible(true);
		frame.requestFocus();
	}
	
	public void set_main_frame_source(JFrame p_frame) {
		main_frame = p_frame;
	}
	
	public void set_add_new_item_trigger(JTextField p_textfield) {
		add_new_item_trigger = p_textfield;
	}
	
	private void get_item_name_image() {
		Document steam_market_item_site;
		Elements item_html_elem;
		Elements item_image_html_elem;
		String item_name = "";
		String item_image_source = "";
		int i;
		
		frame.setTitle("Loading...");
		try{
			steam_market_item_site = Jsoup.connect(textmarketlink.getText() + "?l=english").get();
			
			//Item's name
			item_html_elem = steam_market_item_site.getElementsByClass("market_listing_nav");
			item_name = item_html_elem.text();
			
			//Item's image link
			item_image_html_elem = steam_market_item_site.getElementsByClass("market_listing_largeimage");
			item_image_source = item_image_html_elem.select("img").attr("src").toString(); //Item's image source URL
			item_image_source = item_image_source.replace("360fx360f", "30fx30f"); //Item's image size change
			
			//Getting the image:
			URLConnection connection = new URL(item_image_source).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			item_icon = ImageIO.read(connection.getInputStream());
		}catch(SSLHandshakeException e){
			item_icon = null;
		}catch (IOException e) {
			textmarketlink.setText("Something happened, try pasting here the item's Steam Market link again. (Ctrl + V)");
			textmarketlink.setForeground(Color.RED);
			this.last_valid_link = "";
			item_name = "";
			item_icon = null;
			JOptionPane.showMessageDialog(null, "Error:\n" + e.toString());
		}
		
		try{
			for(i = 0; i < item_name.length(); i++) {
				if(item_name.charAt(i) == '>') {
					lblitemname.setText(item_name.substring(i + 2, item_name.length()));
				}
			}
		}catch(StringIndexOutOfBoundsException ex){
			//Do nothing
		}
		
		last_valid_link = textmarketlink.getText();
		frame.setTitle("");
	}
	
	private String name_shortener(String name) {
		name = name.replace("StatTrak™", "ST")
		    .replace("Strange", "ST")
		    .replace("Unusual", "Unu")
		    .replace("Vintage", "Vin")
		    .replace("Genuine", "Gen")
		    .replace("Operation", "Op")
		    .replace("Factory New", "FN")
		    .replace("Minimal Wear", "MW")
		    .replace("Field-Tested", "FT")
		    .replace("Well-Worn", "WW")
		    .replace("Battle-Scarred", "BS")
		    .replace("Natus Vincere", "NaVi");
		return name;
	}
	
	public String get_item_name() {
		return name_shortener(lblitemname.getText());
	}
	
	public String get_item_market_link() {
		return textmarketlink.getText();
	}
	
	public String get_item_game_id() {
		//Gets the number in quotation marks in the example link:
		//Example: https://steamcommunity.com/market/listings/"730"/AK-47%20%7C%20Redline%20%28Field-Tested%29
		
		String link = textmarketlink.getText();
		link = link.replace("https://steamcommunity.com/market/listings/", "");
		int i = 0;
		
		while(link.charAt(i) != '/') {
			i++;
		}
		
		return link.substring(0, i);
	}
	
	public String get_item_market_hash_name() {
		//Gets the string in quotation marks in the example link:
		//Example: https://steamcommunity.com/market/listings/322330/"BACKPACK_CATCOON"?l=spanish
		
		String link = textmarketlink.getText();
		int i = 0;
		int last_slash_char = -1;
		int first_question_mark_char = -1; //This represents the index in which filters start, which we don't want in the resultant string.
		
		for(i = 0; i < link.length(); i++) {
			if(link.charAt(i) == '/') {
				last_slash_char = i;
			}
			
			if(link.charAt(i) == '?') {
				first_question_mark_char = i;
			}
		}
		
		if(first_question_mark_char == -1) { //If there wasn't any filer in the link use the string's last character.
			first_question_mark_char = link.length();
		}
		
		return link.substring(last_slash_char + 1, first_question_mark_char);
	}
	
	public double get_item_cost() {
		return Double.parseDouble(textitemcost.getText());
	}
	
	public int get_item_quantity() {
		return Integer.parseInt(textitemquantity.getText());
	}
	
	public double get_item_expected_value() {
		return Double.parseDouble(textexpectedvalue.getText());
	}
	
	public BufferedImage get_item_icon() {
		//May return null
		return item_icon;
	}
}
