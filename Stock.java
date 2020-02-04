// This is the Stock object that represents the concept of a stock
// This object is finished and has passed all testing.
// Do not make any changes to this object, its perfect as-is.

public class Stock {
	
	private String name;
	private int quantity;
	private double price;
	
	public Stock() {
		this.name = "undefined";
		this.quantity = 0;
		this.price = 0.0;
	}

	public Stock(String name, int quantity, double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Stock(String name) {
		this.name = name;
		this.quantity = 0;
		this.price = 0.0;
	}

	public Stock(int quantity) {
		this.name = "undefined";
		this.quantity = quantity;
		this.price = 0.0;
	}
	
	public Stock(double price) {
		this.name = "undefined";
		this.quantity = 0;
		this.price = price;
	}

	public Stock(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
		this.price = 0.0;
	}

	public Stock(String name, double price) {
		this.name = name;
		this.quantity = 0;
		this.price = price;
	}

	public Stock(int quantity, double price) {
		this.name = "undefined";
		this.quantity = quantity;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString() {
		return "Stock: " + this.getName() + "  Quantity: " + this.getQuantity() + "  Price: " + this.getPrice();
	}
}
