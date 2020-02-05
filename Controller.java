// The Stock program is following the MVC design template and this is our controller object.
// The main functionality for buying and selling the stocks are in this controller object.
// This is the ONLY file you may edit

import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;;

public class Controller {
	
	private static Scanner input = new Scanner(System.in);

	public class StockTransactions{
		private int totalNumOfStock;
		private LinkedList<Stock> transactions;

		public StockTransactions(){
			this.totalNumOfStock = 0;
			transactions = new LinkedList<Stock>();
		}

		public void increaseInventory(int newlyBoughtStocks){
			this.totalNumOfStock += newlyBoughtStocks;
		}

		public void decreaseInventory(int newlySoldStocks){
			this.totalNumOfStock -= newlySoldStocks;
		}

		public void addPurchase(Stock newPurchase){
			this.transactions.push(newPurchase);
		}

		public void popTop(){
			this.transactions.pop();
		}

		public void dequeFront(){
			this.transactions.removeLast();
		}

		public Stock peekHead(){
			return this.transactions.peek();
		}

		public Stock peekTail(){
			return this.transactions.peekLast();
		}

		public int getTotalNumOfStocks(){
			return this.totalNumOfStock;
		}

		public LinkedList<Stock> getTransactions(){
			return this.transactions;
		}
	}
	
	public Controller() {
		HashMap<String, StockTransactions> availableStocks= new HashMap<String, StockTransactions>();

		int controlNum, quantity;
		String selectedStock;
		double price;
		
		do {
			selectedStock = getStockChoice();
			
			if(selectedStock.equals("quit"))
				break;
			
			StockTransactions stock;
			//check if a transaction obj exists
			if( availableStocks.containsKey(selectedStock)){
				stock = availableStocks.get(selectedStock);
			}
			else{
				stock = new StockTransactions();
				availableStocks.put(selectedStock, stock);
			}
			
			System.out.print("Input 1 to buy, 2 to sell: ");
			int tempcontrolNum = input.nextInt();
			controlNum = validateControlChoice(tempcontrolNum);

			quantity = getQuantity(selectedStock, controlNum);

			// validate if they're selling more stocks than they have
			if(controlNum == 2){
				if(stock.getTotalNumOfStocks() < quantity){
					System.out.printf("You don't have %d %s stocks. You have %d stocks. Try again", quantity, selectedStock, stock.getTotalNumOfStocks());
					break;
				}	
			}

			System.out.print("At what price: ");
			double tempPrice = input.nextDouble();

			//validating price to sell or buy at
			while( tempPrice <= 0){
				System.out.println("Price can't be negative. Please enter a valid price: ");
				tempPrice = input.nextDouble();
			}
			price = tempPrice;

			if(controlNum == 1) {// buying 
				Controller.buyStock(stock, selectedStock, quantity, price);
			}
			
			else {// selling
				System.out.print("Press 1 for LIFO accounting, 2 for FIFO accounting: ");
				tempcontrolNum = input.nextInt();
				controlNum = validateControlChoice(tempcontrolNum);

				if(controlNum == 1) {// if LIFO 	
					Controller.sellLIFO(stock, quantity, price);
				}
				else { // if FIFO
					Controller.sellFIFO(stock, quantity, price);
				}
			}
			
		} while(true);
		input.close();
	}

	public static void buyStock(StockTransactions stock, String name, int quantity, double price) {
		Stock temp = new Stock(name,quantity,price);
		stock.addPurchase(temp);
		stock.increaseInventory(quantity);
		
		System.out.printf("You bought %d shares of %s stock at $%.2f per share %n", quantity, name, price);

	}

	public static void sellLIFO(StockTransactions stock, int numToSell, double price) {
	    // You need to write the code to sell the stock using the LIFO method (Stack)
		// You also need to calculate the profit/loss on the sale
		// averaging the prices on the last numToSell# shares from given list stack

	    double total = 0; // this variable will store the total after the sale
		double profit = 0; // the price paid minus the sale price, negative # means a loss
		int remainder = numToSell;
		
		System.out.printf("\nWe're selling %d stocks", numToSell);
		
		while(remainder > stock.peekHead().getQuantity()){
			total = (stock.peekHead().getQuantity() * stock.peekHead().getPrice() ) + total;
			remainder -= stock.peekHead().getQuantity();
			stock.decreaseInventory(stock.peekHead().getQuantity());
			stock.popTop();
		}
		stock.peekHead().setQuantity(stock.peekHead().getQuantity() - remainder);
		total += (remainder * stock.peekHead().getPrice()); 
		stock.decreaseInventory(remainder);

		System.out.printf("\nremainder is: %d ", remainder);
		System.out.println("\nThe quantity of the top node is : " + stock.peekHead().getQuantity());

		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, stock.peekHead().getName(), price);
		
		profit =(numToSell * price) - total;

	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
	
	public static void sellFIFO(StockTransactions stock, int numToSell, double price) {
	    // You need to write the code to sell the stock using the FIFO method (Queue)
	    // You also need to calculate the profit/loss on the sale
	    double total = 0; // this variable will store the total after the sale
		double profit = 0; // the price paid minus the sale price, negative # means a loss
		int remainder = numToSell;
		
		System.out.printf("\nWe're selling %d stocks", numToSell);
		
		while(remainder > stock.peekTail().getQuantity()){
			total = (stock.peekTail().getQuantity() * stock.peekTail().getPrice() ) + total;
			remainder -= stock.peekTail().getQuantity();
			stock.decreaseInventory(stock.peekTail().getQuantity());
			stock.dequeFront();
		}
		stock.peekTail().setQuantity(stock.peekTail().getQuantity() - remainder);
		total += (remainder * stock.peekTail().getPrice()); 
		
		stock.decreaseInventory(remainder);

		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, stock.peekTail().getName(), price);
		profit = (numToSell * price) - total;
		System.out.printf("You made $%.2f on the sale %n", profit);
	}

	public static String getStockChoice(){
		String message = "Enter the name of stock you want to buy or \"Quit \" to terminate the program: ";
		System.out.print(message);
		String tempstockSelect = input.next().toLowerCase();

		//Validating stock input for letters only
		while( !tempstockSelect.matches("[a-zA-Z]+") ){
			System.out.println("Invalid input. " + message);
			tempstockSelect = input.next().toLowerCase();
		}
		return tempstockSelect;
	}
	
	public static int validateControlChoice(int choice){
		while(choice < 1 || choice > 2){
			System.out.println("Invalid input. Please enter 1 or 2: ");
			choice = input.nextInt();
		}
		return choice;
	}

	public static int getQuantity(String selectedStock, int controlNum){
		System.out.print("How many stocks: ");
		int tempquantity = input.nextInt();

		// Validate for negative or 0 numbers
		while(tempquantity < 1){
			System.out.println("Quantity can't be negative or zero. Enter how many stocks: ");
			tempquantity = input.nextInt();
		}
		return tempquantity;
	}
}
