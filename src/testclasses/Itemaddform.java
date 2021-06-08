package files;

import java.awt.EventQueue;
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

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Itemaddform {

	private JFrame frame;
	private JTextField textmarketlink;
	private JTextField textitemcost;
	private JTextField textitemquantity;
	private JTextField textexpectedvalue;

	/**Launch the application.*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Itemaddform window = new Itemaddform();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** Create the application.*/
	public Itemaddform() {
		initialize();
	}

	/**Initialize the contents of the frame.*/
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelmain = new JPanel();
		panelmain.setMaximumSize(new Dimension(9999, 9999));
		panelmain.setPreferredSize(new Dimension(700, 440));
		panelmain.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.getContentPane().add(panelmain, BorderLayout.CENTER);
		panelmain.setLayout(new BoxLayout(panelmain, BoxLayout.Y_AXIS));
		
		Box Boxitem = Box.createHorizontalBox();
		Boxitem.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxitem.setMinimumSize(new Dimension(40, 40));
		Boxitem.setMaximumSize(new Dimension(700, 300));
		Boxitem.setPreferredSize(new Dimension(40, 40));
		panelmain.add(Boxitem);
		
		JLabel lblitem = new JLabel("Item: ");
		lblitem.setMaximumSize(new Dimension(40, 20));
		lblitem.setFont(new Font("Arial", Font.PLAIN, 16));
		Boxitem.add(lblitem);
		
		JLabel lblitemname = new JLabel("'item'");
		lblitemname.setHorizontalTextPosition(SwingConstants.CENTER);
		lblitemname.setMaximumSize(new Dimension(600, 20));
		lblitemname.setFont(new Font("Arial", Font.PLAIN, 20));
		Boxitem.add(lblitemname);
		
		Box Boxmarketlink = Box.createHorizontalBox();
		Boxmarketlink.setMaximumSize(new Dimension(9999, 300));
		Boxmarketlink.setMinimumSize(new Dimension(40, 40));
		Boxmarketlink.setPreferredSize(new Dimension(40, 40));
		Boxmarketlink.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelmain.add(Boxmarketlink);
		
		JLabel lblmarketlink = new JLabel("Market item link:");
		lblmarketlink.setPreferredSize(new Dimension(105, 14));
		lblmarketlink.setMinimumSize(new Dimension(10, 14));
		lblmarketlink.setMaximumSize(new Dimension(105, 14));
		lblmarketlink.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxmarketlink.add(lblmarketlink);
		
		textmarketlink = new JTextField("Insert here the item's Steam Community Market link.");
		textmarketlink.setFont(new Font("Arial", Font.PLAIN, 14));
		textmarketlink.setForeground(Color.GRAY);
		textmarketlink.setMaximumSize(new Dimension(9999, 30));
		textmarketlink.setPreferredSize(new Dimension(570, 30));
		Boxmarketlink.add(textmarketlink);
		textmarketlink.setColumns(10);
		
		Box Boxcost = Box.createHorizontalBox();
		Boxcost.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxcost.setPreferredSize(new Dimension(40, 40));
		Boxcost.setMinimumSize(new Dimension(40, 40));
		Boxcost.setMaximumSize(new Dimension(9999, 300));
		panelmain.add(Boxcost);
		
		JLabel lblitemcost = new JLabel("Item cost: ");
		lblitemcost.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxcost.add(lblitemcost);
		
		textitemcost = new JTextField();
		textitemcost.setText("0");
		textitemcost.setPreferredSize(new Dimension(610, 30));
		textitemcost.setMaximumSize(new Dimension(9999, 30));
		textitemcost.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxcost.add(textitemcost);
		textitemcost.setColumns(10);
		
		Box Boxquantity = Box.createHorizontalBox();
		Boxquantity.setAlignmentX(Component.LEFT_ALIGNMENT);
		Boxquantity.setMaximumSize(new Dimension(9999, 300));
		Boxquantity.setMinimumSize(new Dimension(40, 40));
		Boxquantity.setPreferredSize(new Dimension(40, 40));
		panelmain.add(Boxquantity);
		
		JLabel lblitemquantity = new JLabel("Quantity: ");
		lblitemquantity.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxquantity.add(lblitemquantity);
		
		textitemquantity = new JTextField();
		textitemquantity.setText("0");
		textitemquantity.setPreferredSize(new Dimension(610, 30));
		textitemquantity.setMaximumSize(new Dimension(9999, 30));
		textitemquantity.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxquantity.add(textitemquantity);
		textitemquantity.setColumns(10);
		
		Box Boxexpectedvalue = Box.createHorizontalBox();
		Boxexpectedvalue.setPreferredSize(new Dimension(40, 40));
		Boxexpectedvalue.setMinimumSize(new Dimension(40, 40));
		Boxexpectedvalue.setMaximumSize(new Dimension(9999, 300));
		Boxexpectedvalue.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelmain.add(Boxexpectedvalue);
		
		JLabel lblitemexpectedvalue = new JLabel("Expected value: ");
		lblitemexpectedvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		Boxexpectedvalue.add(lblitemexpectedvalue);
		
		textexpectedvalue = new JTextField();
		textexpectedvalue.setText("0");
		textexpectedvalue.setPreferredSize(new Dimension(540, 30));
		textexpectedvalue.setFont(new Font("Arial", Font.PLAIN, 14));
		textexpectedvalue.setMaximumSize(new Dimension(9999, 30));
		Boxexpectedvalue.add(textexpectedvalue);
		textexpectedvalue.setColumns(10);
		
		ButtonGroup radiobuttons = new ButtonGroup();
		
		JPanel panelbuttons = new JPanel();
		panelbuttons.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelbuttons.setPreferredSize(new Dimension(500, 40));
		frame.getContentPane().add(panelbuttons, BorderLayout.SOUTH);
		panelbuttons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		final JButton btnaccept = new JButton("Accept");
		btnaccept.setHorizontalAlignment(SwingConstants.LEFT);
		panelbuttons.add(btnaccept);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(200, 0));
		panelbuttons.add(horizontalStrut);
		
		JButton btncancel = new JButton("Cancel");
		btncancel.setHorizontalAlignment(SwingConstants.RIGHT);
		btncancel.setInheritsPopupMenu(true);
		panelbuttons.add(btncancel);
		
		FocusAdapter setting_number = (new FocusAdapter(){
			public void focusGained(FocusEvent e) {
				JTextField source_textfield = (JTextField) e.getSource();
				if(source_textfield.getText().equals("0") | source_textfield.getText().equals("0.0")){
					source_textfield.setText("");
				}
			}
		});
		textitemcost.addFocusListener(setting_number);
		textitemquantity.addFocusListener(setting_number);
		textexpectedvalue.addFocusListener(setting_number);
		
		FocusAdapter doublecheck = (new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double number = 0;
				String mystring;
				JTextField get_source_textfield = (JTextField) e.getSource();
				mystring = get_source_textfield.getText();
				mystring = mystring.replace(',', '.');
				if(mystring.isEmpty()){
					mystring = "0";
				}else if(mystring.charAt(mystring.length() - 1) == '.'){
					mystring = mystring + '0';
				}
				if(mystring.charAt(0) == '.'){
					mystring = '0' + mystring;
				}
				get_source_textfield.setText(mystring);
				try{
					number = Double.parseDouble(get_source_textfield.getText());
				}catch(NumberFormatException ex){
					Component set_source_textfield = e.getComponent();
					if (set_source_textfield instanceof JTextField) {
				          ((JTextField)set_source_textfield).setText("0");
				          JOptionPane.showMessageDialog(null, "Please, only use numbers in this field.\nE.g.: 0.25 ");
				      }
				}finally{
					Component set_source_textfield = e.getComponent();
					if(number < 0){
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
		
		this.textitemquantity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int number = 0;
				if(textitemquantity.getText().isEmpty()){
					textitemquantity.setText("0");
				}
				try{
					number = Integer.parseInt(textitemquantity.getText());
				}catch(NumberFormatException ex){
					textitemquantity.setText("0");
				    JOptionPane.showMessageDialog(null, "Please, only use integer numbers equal or over zero in this field.\nE.g.: 10 ");
				}finally{
					if(number < 0){
						textitemquantity.setText("0");
						JOptionPane.showMessageDialog(null, "Please, only use integer numbers equal or over zero in this field.\nE.g.: 10 ", "Error", JOptionPane.OK_OPTION);
						//UIManager.put("OptionPane.okButtonText", "Text I want");
					}
				}
			}
		});
		
		this.textmarketlink.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try{
					if(!textmarketlink.getText().substring(0, 35).equals("steamcommunity.com/market/listings/")){
						if(!textmarketlink.getText().substring(0, 43).equals("https://steamcommunity.com/market/listings/")){
							textmarketlink.setForeground(Color.BLACK);
							textmarketlink.setText("");
						}
					}
				}catch(StringIndexOutOfBoundsException ex){
					textmarketlink.setForeground(Color.BLACK);
					textmarketlink.setText("");
				}
			}
		});
		
		this.textmarketlink.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try{
					if(textmarketlink.getText().equals("Insert here the item's Steam Community Market link.")){
					}else if(textmarketlink.getText().substring(0, 35).equals("steamcommunity.com/market/listings/")){
						textmarketlink.setText("https://" + textmarketlink.getText());
						textmarketlink.setForeground(Color.GREEN);
					}else if(textmarketlink.getText().substring(0, 43).equals("https://steamcommunity.com/market/listings/")){
						textmarketlink.setForeground(Color.GREEN);
					}else{
						textmarketlink.setForeground(Color.RED);
						textmarketlink.setText("Wrong input, paste here the item's link to its Steam Community Market page.");
					}
				}catch(StringIndexOutOfBoundsException ex){
					if(textmarketlink.getText().equals("")){
						textmarketlink.setForeground(Color.GRAY);
						textmarketlink.setText("Insert here the item's Steam Community Market link by pasting it.");
					}else{
						textmarketlink.setForeground(Color.RED);
						textmarketlink.setText("Wrong input, just paste here the item's link to its Steam Community Market page.");
					}
				}
			}
		});
		
		btnaccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if(textmarketlink.getForeground() == Color.GREEN){
					double item_cost = Double.parseDouble(textitemcost.getText());
					double item_expected_value = Double.parseDouble(textexpectedvalue.getText());
					
					if(item_cost > item_expected_value){
						Object[] options = {"Go back", "Skip"};
						
						JOptionPane.showOptionDialog(frame,
							    "The item's cost is higher than its expected value.",
							    "Warning",
							    JOptionPane.YES_NO_OPTION,
							    JOptionPane.WARNING_MESSAGE,
							    null,     
							    options,  
							    options[0]);
						
						
					}else{
						//ASDConfirmshenanigansacaahahsadasdas
					}
				}else{
					textmarketlink.setForeground(Color.RED);
				}
				}
		});
		
		
		
		
		
		
		
		
		
		
	}

}
