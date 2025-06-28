package Frames;

public class CartItem {
    private String productName;
    private double unitPrice;
    private int quantity;

    public CartItem(String productName, double unitPrice, int quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
    public double getSubtotal() {
        return this.unitPrice * this.quantity;
    }

    
    public String toString() {
        return String.format("%d x %s - $%.2f", quantity, productName, getSubtotal());
    }
}