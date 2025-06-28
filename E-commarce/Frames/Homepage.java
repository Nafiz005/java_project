package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Homepage extends JFrame implements ActionListener {
    JLabel welcomeLabel, bgImageLabel, billingLabel, nameLabel, addressLabel, phoneLabel, cartLabel, paymentLabel, productSelectLabel, totalBillTitleLabel, totalBillLabel;
    JTextField nameField, addressField, phoneField;
    JTextArea cartArea;
    JButton logoutButton, clearCartButton, confirmOrderButton, updatePriceButton, deleteButton;
    JPanel Panel, billingPanel;
    JRadioButton onlinePayment, cashOnDelivery;
    ButtonGroup paymentGroup;
    JComboBox<String> productSelector;
    ImageIcon icon, bgImage;
    JFrame loginFrame;
    JPanel[] productPanels;
    JLabel[] productImages;
    JLabel[] productNamesLabels;
    JLabel[] productPriceLabels;
    JButton[] addToCartButtons;
    JButton[] incrementButtons;
    JButton[] decrementButtons;
    JLabel[] quantityLabels;
    String[] productNames;
    String[] productPrices;
    String[] imagePaths;
    ImageIcon[] productIcons;
    int[] productQuantities;


    private double cartTotal = 0.0;

    
    private static final String ORDERS_DIRECTORY_PATH = "orders";


    public Homepage(String username, JFrame loginFrame) {
        super("Homepage");
        this.loginFrame = loginFrame;
        setSize(1250, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupIcon();
        Panel = new JPanel();
        Panel.setLayout(null);

        bgImage = new ImageIcon("./images/Green.jpg");
        bgImageLabel = new JLabel(bgImage);
        bgImageLabel.setBounds(0, 0, 1250, 30);
        Panel.add(bgImageLabel);

        welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setBounds(10, 5, 150, 20);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        bgImageLabel.add(welcomeLabel);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(1155, 5, 80, 20);
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.addActionListener(this);
        Panel.add(logoutButton);

        billingPanel = new JPanel();
        billingPanel.setLayout(null);
		
        billingPanel.setBounds(0, 50, 300, 600);
        billingPanel.setBackground(Color.WHITE);


        billingLabel = new JLabel("Billing Information");
        billingLabel.setBounds(10, 10, 200, 20);
        billingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        billingPanel.add(billingLabel);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 40, 80, 20);
        billingPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(100, 40, 180, 25);
        billingPanel.add(nameField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 75, 80, 20);
        billingPanel.add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(100, 75, 180, 25);
        billingPanel.add(addressField);

        phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 110, 80, 20);
        billingPanel.add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(100, 110, 180, 25);
        billingPanel.add(phoneField);

        cartLabel = new JLabel("Cart Items:");
        cartLabel.setBounds(10, 145, 80, 20);
        billingPanel.add(cartLabel);
        cartArea = new JTextArea();
        cartArea.setEditable(false);
        JScrollPane cartScroll = new JScrollPane(cartArea);
        cartScroll.setBounds(100, 145, 180, 150);
        billingPanel.add(cartScroll);
        
        totalBillTitleLabel = new JLabel("Total Bill:");
        totalBillTitleLabel.setBounds(10, 300, 80, 20);
        totalBillTitleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        billingPanel.add(totalBillTitleLabel);

        totalBillLabel = new JLabel("$0.00");
        totalBillLabel.setBounds(100, 300, 180, 20);
        totalBillLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalBillLabel.setForeground(Color.BLUE);
        billingPanel.add(totalBillLabel);

        paymentLabel = new JLabel("Payment Method:");
        paymentLabel.setBounds(10, 335, 120, 20);
        billingPanel.add(paymentLabel);

        onlinePayment = new JRadioButton("Online Payment");
        cashOnDelivery = new JRadioButton("Cash on Delivery");
        paymentGroup = new ButtonGroup();
        paymentGroup.add(onlinePayment);
        paymentGroup.add(cashOnDelivery);
        onlinePayment.setBounds(10, 355, 130, 20);
        cashOnDelivery.setBounds(150, 355, 130, 20);
        billingPanel.add(onlinePayment);
        billingPanel.add(cashOnDelivery);


        productSelectLabel = new JLabel("Manage Product:");
        productSelectLabel.setBounds(10, 390, 120, 20);
        productSelectLabel.setFont(new Font("Arial", Font.BOLD, 12));
        billingPanel.add(productSelectLabel);

        productSelector = new JComboBox<>();
        productSelector.setBounds(120, 390, 170, 25);
        billingPanel.add(productSelector);

        updatePriceButton = new JButton("Update Price");
        updatePriceButton.setBounds(10, 425, 120, 30);
        updatePriceButton.setBackground(new Color(70, 130, 180));
        updatePriceButton.setForeground(Color.WHITE);
        updatePriceButton.addActionListener(this);
        billingPanel.add(updatePriceButton);

        deleteButton = new JButton("Delete Product");
        deleteButton.setBounds(150, 425, 140, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);
        billingPanel.add(deleteButton);

        clearCartButton = new JButton("Clear Cart");
        clearCartButton.setBounds(10, 470, 120, 30);
        clearCartButton.addActionListener(this);
        billingPanel.add(clearCartButton);

        confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setBounds(150, 470, 140, 30);
        confirmOrderButton.addActionListener(this);
        billingPanel.add(confirmOrderButton);

        Panel.add(billingPanel);

        loadProducts();
        loadImages();
        showProducts();

        add(Panel);
        setVisible(true);
    }
    
    private void updateTotalBill() {
        totalBillLabel.setText(String.format("$%.2f", cartTotal));
    }

       
    private void loadProducts() {
        this.productNames = new String[]{
                "Walton Air Conditioner", "Asus Zenbook 14", "Dellxps13", "Hpspectre",
                "Laptop_Asus", "ThinkpadX1", "Toothbrush", "Gucci"
        };
        this.productPrices = new String[]{
                "$1250.00", "$949.99", "$890.50", "$1320.00",
                "$1650.00", "$1350.75", "$29.00", "$150.00"
        };
        this.imagePaths = new String[]{
                "./images/Ac_1.jpg", "./images/Asus-zenbook-14.jpg", "./images/Dellxps13.jpg", "./images/Hpspectre.jpg",
                "./images/Laptop_asus.jpg", "./images/Thinkpadx1.jpg", "./images/Toothbrush.jpg", "./images/Gucci.jpg"
        };
        productQuantities = new int[productNames.length];
    }

    private void loadImages() {
        productIcons = new ImageIcon[imagePaths.length];
        for (int i = 0; i <imagePaths.length; i++) {
            if (imagePaths[i] != null ) {
                File imgFile = new File(imagePaths[i]);
                if (imgFile.exists()) {
                    productIcons[i] = new ImageIcon(imagePaths[i]);
                } else {
                    System.out.println("Image file not found: " +imagePaths[i]);
                }
            } else {
                System.out.println("Image path is null or empty for product index " + i);
                
            }
        }
    }	

    private void setupIcon() {
        icon = new ImageIcon("./images/ShopIcon.jpg");
        if (new File("./images/ShopIcon.jpg").exists()) {
            setIconImage(icon.getImage());
        } else {
            System.out.println("ShopIcon.jpg not found at ./images/ShopIcon.jpg. Icon will not be set.");
        }
    }

    private void showProducts() {
        int Products = productNames.length;
        productPanels = new JPanel[Products];
        productImages = new JLabel[Products];
        productNamesLabels = new JLabel[Products];
        productPriceLabels = new JLabel[Products];
        addToCartButtons = new JButton[Products];
        incrementButtons = new JButton[Products];
        decrementButtons = new JButton[Products];
        quantityLabels = new JLabel[Products];

        int productsPerRow = 4;
        int panelWidth = 200;
        int panelHeight = 250;
        int horizontalGap = 20;
        int verticalGap = 20;
        int X = 320;
        int Y = 50;

        for (int i = 0; i < Products; i++) {
            int row = i / productsPerRow;
            int col = i % productsPerRow;
            int xPosition = X + col * (panelWidth + horizontalGap);
            int yPosition = Y + row * (panelHeight + verticalGap);
            productPanels[i] = createProductPanel(i, xPosition, yPosition);
            this.Panel.add(productPanels[i]);
        }
        productSelector.setModel(new DefaultComboBoxModel<>(productNames));
        
        System.out.println(Products + " products displayed.");
    }
	
    JPanel createProductPanel(int i, int x, int y) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(x, y, 200, 250); 
        productPanel.setBackground(new Color(220, 220, 220));

        Image img =  productIcons[i].getImage();
        if (img != null) {
            Image scaledImg = img.getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            productImages[i] = new JLabel(new ImageIcon(scaledImg));
        } else {
            productImages[i] = new JLabel("No Image");
            productImages[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
        productImages[i].setBounds(25, 10, 150, 120);
        productPanel.add(productImages[i]);

        String nameText; 

        if (productNames != null && i < productNames.length) {
          nameText = productNames[i];
        } 
		else 
		{
            nameText = "";
        }
        productNamesLabels[i] = new JLabel(nameText);
        productNamesLabels[i].setBounds(10, 140, 180, 20);
        productNamesLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
        productPanel.add(productNamesLabels[i]);
		
        String priceText; 

        if (productPrices != null && i < productPrices.length) {
        
		   priceText = productPrices[i]; 
        } 
		else {
            priceText = "";
        }
        productPriceLabels[i] = new JLabel(priceText);
        productPriceLabels[i].setBounds(10, 160, 180, 20);
        productPriceLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
        productPriceLabels[i].setFont(new Font("Arial", Font.BOLD, 14));
        productPanel.add(productPriceLabels[i]);

        
        decrementButtons[i] = new JButton("-");
        decrementButtons[i].setBounds(35, 185, 45, 25);
        decrementButtons[i].addActionListener(this);
        productPanel.add(decrementButtons[i]);

        quantityLabels[i] = new JLabel(String.valueOf(productQuantities[i]), SwingConstants.CENTER);
        quantityLabels[i].setBounds(80, 185, 40, 25);
        quantityLabels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
        productPanel.add(quantityLabels[i]);

        incrementButtons[i] = new JButton("+");
        incrementButtons[i].setBounds(120, 185, 45, 25);
        incrementButtons[i].addActionListener(this);
        productPanel.add(incrementButtons[i]);
		
        addToCartButtons[i] = new JButton("Add to Cart");
        addToCartButtons[i].setBounds(25, 215, 150, 25); 
        addToCartButtons[i].setBackground(new Color(70, 130, 180));
        addToCartButtons[i].setForeground(Color.WHITE);
        addToCartButtons[i].addActionListener(this);
        productPanel.add(addToCartButtons[i]);

        return productPanel;
    }
	
	
	private void refreshProductDisplay() 
    {
        if (productPanels != null) {
            for (int i = 0; i < productPanels.length; i++) {
                JPanel p = productPanels[i]; 
                if (p != null) {
                    this.Panel.remove(p);
                }
            }
        }
        loadImages();
        showProducts();
        System.out.println("Product display refreshed.");
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == logoutButton) {
            setVisible(false);
            loginFrame.setVisible(true);
        } else if (source == clearCartButton) {
            cartArea.setText("");
            nameField.setText("");
            addressField.setText("");
            phoneField.setText("");
            paymentGroup.clearSelection();
            cartTotal = 0.0;
            updateTotalBill();
            for (int i = 0; i < productQuantities.length; i++) {
                productQuantities[i] = 0;
                if (quantityLabels != null) {
                    quantityLabels[i].setText("0");
                }
            }
        } else if (source == confirmOrderButton) {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            String payment = onlinePayment.isSelected() ? "Online Payment" : (cashOnDelivery.isSelected() ? "Cash on Delivery" : "Not selected");

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || payment.equals("Not selected") || cartArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields, select a payment method, and add items to your cart.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String cartItems = cartArea.getText();
                String totalBillText = String.format("$%.2f", cartTotal);
                String confirmationMessage = "Order Confirmed!\n\n" + "Name: " + name + "\n" + "Address: " + address + "\n" + "Phone: " + phone + "\n" + "Payment: " + payment + "\n\n" + "Items:\n" + cartItems + "\nTotal Bill: " + totalBillText;
                JOptionPane.showMessageDialog(this, confirmationMessage, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
                saveOrderToFile(name, address, phone, payment, cartItems, cartTotal);
                clearCartButton.doClick();
            }
		} 
		else if (source == updatePriceButton) {
            int selectedIndex = productSelector.getSelectedIndex();
            if (selectedIndex != -1) {
                String currentPrice = productPrices[selectedIndex];
                String newPrice = JOptionPane.showInputDialog(this, "Enter new price for " + productNames[selectedIndex] + ":", currentPrice);
                if (newPrice != null && !newPrice.trim().isEmpty()) {
                    productPrices[selectedIndex] = newPrice.trim();
                    refreshProductDisplay();
                    JOptionPane.showMessageDialog(this, "Price updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (source == deleteButton) {
            int selectedIndex = productSelector.getSelectedIndex();
            if (selectedIndex != -1) {
                int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + productNames[selectedIndex] + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    int newSize = productNames.length - 1;
                    String[] newNames = new String[newSize];
                    String[] newPrices = new String[newSize];
                    String[] newPaths = new String[newSize];
                    int[] newQuantities = new int[newSize];
                    int newArrayIndex = 0;
                    for (int i = 0; i < productNames.length; i++) {
                        if (i == selectedIndex) continue;
                        newNames[newArrayIndex] = productNames[i];
                        newPrices[newArrayIndex] = productPrices[i];
                        newPaths[newArrayIndex] = imagePaths[i];
                        newQuantities[newArrayIndex] = productQuantities[i];
                        newArrayIndex++;
                    }
                    productNames = newNames;
                    productPrices = newPrices;
                    imagePaths = newPaths;
                    productQuantities = newQuantities;
                    refreshProductDisplay();
                    JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            for (int i = 0; i < incrementButtons.length; i++) {
                if (source == incrementButtons[i]) {
                    productQuantities[i]++;
                    quantityLabels[i].setText(String.valueOf(productQuantities[i]));
                    return; 
                }
            }
            for (int i = 0; i < decrementButtons.length; i++) {
                if (source == decrementButtons[i]) {
                    if (productQuantities[i] > 0) {
                        productQuantities[i]--;
                        quantityLabels[i].setText(String.valueOf(productQuantities[i]));
                    }
                    return; 
                }
            }
            for (int i = 0; i < addToCartButtons.length; i++) {
                if (source == addToCartButtons[i]) {
                    int quantity = productQuantities[i];
                    if (quantity == 0) {
                        JOptionPane.showMessageDialog(this, "Please select a quantity greater than 0.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return; 
                    }
                    try {
                        String priceString = productPrices[i].replaceAll("[^\\d.]", "");
                        double priceValue = Double.parseDouble(priceString);
                        double lineTotal = priceValue * quantity;
                        cartArea.append(String.format("%s x %d - $%.2f\n", productNames[i], quantity, lineTotal));
						
                        cartTotal += lineTotal;
                        updateTotalBill();
                        productQuantities[i] = 0;
                        quantityLabels[i].setText("0");

                    } catch (NumberFormatException nfe) {
                        System.out.println("Could not parse price for item: " + productNames[i]);
                        JOptionPane.showMessageDialog(this, "Error processing price for " + productNames[i], "Price Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
    
    private void saveOrderToFile(String name, String address, String phone, String paymentMethod, String cartItems, double totalBill) {
        File orders = new File(ORDERS_DIRECTORY_PATH);
        String Name = name.replaceAll("%s, %s", "");
        String time= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Order_" + Name+ ".txt";
        File orderFile = new File(orders, fileName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(orderFile))) {
            writer.println("ORDER CONFIRMATION");
            writer.println("====================");
            writer.println("Order Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            writer.println("\nCustomer Information:\n---------------------");
            writer.println("Name: " + name);
            writer.println("Address: " + address);
            writer.println("Phone: " + phone);
            writer.println("\nPayment Method:\n---------------");
            writer.println(paymentMethod);
            writer.println("\nPurchased Items:\n----------------");
            writer.println(cartItems);
            writer.println("\n----------------");
            writer.println("TOTAL BILL: " + String.format("$%.2f", totalBill));
            writer.println("\nThank you for your order!");

            System.out.println("Order details saved to: " + orderFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving order details: " + e.getMessage(), "File Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}