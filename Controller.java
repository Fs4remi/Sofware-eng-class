// The Stock program is following the MVC design template and this is our controller object.
// The main functionality for buying and selling the stocks are in this controller object.
// This is the ONLY file you may edit

/**
 * stock obj has {String name, int quantity, double price}
 * Controller()
 * buyStock(stock List, name, quantity, price)
 * sellLIFO(stock List, numbertosell)
 * sellFIFO(stock List, numbertosell)
 */

import java.util.LinkedList;
import java.util.Scanner;

public class Controller {
	
	private static Scanner input = new Scanner(System.in);
	private static int numOfAmazonStocks = 0;
	private static int numOfGoogleStocks = 0;
	
	public Controller() {
		LinkedList<Stock> googList = new LinkedList<Stock>();
		LinkedList<Stock> amazList = new LinkedList<Stock>();

		int controlNum, quantity;
		String selectedStock;
		double price;
		
		do {
			selectedStock = getStockChoice();
			/**
			 * ext, change the user input, so the user enters the stock 
			 * name they wish to buy or sell instead of just choosing between Google and Amazon. 
			 */
			if(selectedStock.equals("quit"))
				break;
			
			System.out.print("Input 1 to buy, 2 to sell: ");
			int tempcontrolNum = input.nextInt();
			controlNum = validateControlChoice(tempcontrolNum);

			quantity = getQuantity(selectedStock, controlNum);

			if(quantity == -1)
				break;

			System.out.print("At what price: ");
			double tempPrice = input.nextDouble();

			//validating price to sell or buy at
			while( tempPrice < 0){
				System.out.println("Price can't be negative. Please enter a valid price: ");
				tempPrice = input.nextDouble();
			}
			price = tempPrice;

			if(controlNum == 1) {// buying 
				if(selectedStock.equals("google")) {//buying x# of GOOGLE'S stock for $7
					Controller.buyStock(googList, "Google", quantity, price);
				}
				else
					Controller.buyStock(amazList, "Amazon", quantity, price);
			}
			
			else {// selling
				System.out.print("Press 1 for LIFO accounting, 2 for FIFO accounting: ");
				tempcontrolNum = input.nextInt();
				controlNum = validateControlChoice(tempcontrolNum);

				if(controlNum == 1) {// if LIFO 					
					if(selectedStock.equals("google")){
						Controller.sellLIFO(googList, quantity, price);
					}
					else{// if amazon
						Controller.sellLIFO(amazList, quantity, price);
					}
				}
				else { // if FIFO
					if(selectedStock.equals("google")) 
						Controller.sellFIFO(googList, quantity, price);
					else // if amazon
						Controller.sellFIFO(amazList, quantity, price);
				}
			}
			
		} while(true);
		input.close();
	}
	
	// LinkedList<Stock>
	public static void buyStock(LinkedList<Stock> list, String name, int quantity, double price) {
		Stock temp = new Stock(name,quantity,price);
		list.push(temp);
		System.out.printf("You bought %d shares of %s stock at $%.2f per share %n", quantity, name, price);

		if( name.equals("amazon") )
			numOfAmazonStocks += quantity;
		else
			numOfGoogleStocks += quantity;
	}
	
	public static void sellLIFO(LinkedList<Stock> list, int numToSell, double price) {
	    // You need to write the code to sell the stock using the LIFO method (Stack)
		// You also need to calculate the profit/loss on the sale
		// averaging the prices on the last numToSell# shares from given list stack

	    double total = 0; // this variable will store the total after the sale
		double profit = 0; // the price paid minus the sale price, negative # means a loss
		int remainder = numToSell;

		//for iteration
		int currentQuantity;
		double curentPrice;
		
		System.out.printf("\nWe're selling %d stocks", numToSell);

		while(remainder != 0){
			currentQuantity = list.peek().getQuantity();
			curentPrice = list.peek().getPrice();

			if(currentQuantity <= remainder){
				total = (currentQuantity * curentPrice ) + total;
				remainder -= currentQuantity;
				list.pop();
				System.out.println("\nPopped a node :> ");
			}
			else{
				total = total +(remainder * curentPrice);
				list.peek().setQuantity(currentQuantity - remainder);
				System.out.println("\nChanged the quantity of a node :> ");
				remainder = 0;
			}
			System.out.printf("\nremainder is: %d ", remainder);
			System.out.println("\nThe quantity of the top node is : " + list.peek().getQuantity());
		}

		//																				the name of head's name
		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, list.element().getName(), total/numToSell);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
	
	public static void sellFIFO(LinkedList<Stock> list, int numToSell, double price) {
	    // You need to write the code to sell the stock using the FIFO method (Queue)
	    // You also need to calculate the profit/loss on the sale
	    double total = 0; // this variable will store the total after the sale
		double profit = 0; // the price paid minus the sale price, negative # means a loss
		
		System.out.println(list.peek().toString());

		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, list.element().getName(), total/numToSell);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}

	public static String getStockChoice(){
		String message = "Enter \"Google\" for Google stock, \"Amazon\" for Amazon stock, or \"Quit \" to terminate the program: ";
		System.out.print(message);
		String tempstockSelect = input.next().toLowerCase();

		//Validating stock input
		while( !tempstockSelect.equals("google") && !tempstockSelect.equals("amazon") && !tempstockSelect.equals("quit")){
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

		// validate if they're selling more stocks than they have
		if(controlNum == 2){
			if(selectedStock.equals("amazon")){
				if(tempquantity > numOfAmazonStocks){
					System.out.printf("You don't have %d %s stocks. You have %d stocks. Try again", tempquantity,selectedStock, numOfAmazonStocks);
					return -1;
				}
			}
			else{
				if(tempquantity > numOfGoogleStocks){
					System.out.printf("You don't have %d %s stocks. You have %d stocks. Try again", tempquantity, selectedStock, numOfGoogleStocks);
					return -1;
				}
			}	
		}
		return tempquantity;
	}
}
