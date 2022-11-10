import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
/**
 * @author Dr. Trytten and Umar M. Mian
 * @version 1.0
 */
public class Project10
{

	public static void main(String[] args) throws FileNotFoundException
	{
		final int MAX_ROWS = 100;

		String[] rowNames = new String[MAX_ROWS];
		
		//TODO Store the names of the columns in a perfect size array
		String[] colNames = {"Jan", "Feb", "Mar", "Apr"};
		
		//TODO Create a two dimensional array to store the data. This array is both oversize and perfect size.  
		// The columns are perfect size
		// The rows are oversize
		double[][] data = new double[MAX_ROWS][4];
		int rowSize;

		
		String fileName;
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter the file name:");
		fileName = keyboard.nextLine();
		
		//TODO Call the readFile method
		rowSize = readFile(rowNames, data, colNames.length, fileName);
		
		//TODO Call the performOperations method that handles most of the user interaction
		rowSize = performOperations(rowNames, rowSize, colNames, data, keyboard);
		
		//TODO Call the writeFile method
		writeFile(rowNames, rowSize, colNames.length, data, fileName);
		
		keyboard.close();
	}
	
	/** This method does most of the user input and calls the other methods.
	 * 
	 * @param rows The names of the rows in an oversize array.
	 * @param rowSize The number of rows in the oversize array (also the number of rows is data).
	 * @param cols The names of the columns in a perfect size array.
	 * @param data The amount spent on each category in each month. Data is oversize in rows, and perfect size in columns.
	 * @param input The Scanner that is connected to the console for user input/output.
	 * @return The number of rows that are in the rows and data array at the end of the method.
	 */
	// This method is longer than is ideal.  Since it deals with input and output, this is OK.
	public static int performOperations(String[] rows, int rowSize, String[] cols, double[][] data, Scanner input)
	{
		int menuChoice = getMenuChoice(input);
		
		while (menuChoice != 6)
		{
			double total = 0.0;
			if (menuChoice==1)
			{
				System.out.println("Enter the month");
				String month = input.nextLine();
				//TODO Call the findMonthlyTotal method
				total = findMonthlyTotal(cols, data, rowSize, month);

				System.out.println("The total is " + total);
			}
			else if (menuChoice==2)
			{
				System.out.println("Enter the item");
				String item = input.nextLine();
				//TODO Call the findItemTotal method
				total = findItemTotal(rows, rowSize, cols.length, data, item);

				System.out.println("The total is " + total);
			}
			else if (menuChoice==3)  // grand total
			{
				//TODO Call the findGrandTotal method
				total = findGrandTotal(data, rowSize, cols.length);

				System.out.println("The total is " + total);
			}
			else if (menuChoice==4)
			{
				System.out.println("Enter the lower amount");
				double lower = input.nextDouble();
				System.out.println("Enter the upper amount");
				double upper = input.nextDouble();
				//TODO Call the findRangeTotal method
				total = findRangeTotal(data, rowSize, cols.length, lower, upper);

				System.out.println("The total is " + total);
			}
			else if (menuChoice == 5)
			{
				System.out.println("What is the item?");
				String item = input.nextLine();
				System.out.println("Which month?");
				String month = input.nextLine();
				System.out.println("What is the amount");
				double amount = input.nextDouble();
				input.nextLine();
				
				//TODO Call the addToData method
				rowSize = addToData(rows, rowSize, cols, data, item, month, amount);
			}

			// Priming read
			menuChoice = getMenuChoice(input);
		}
		return rowSize;
	}
	
	/** Update an existing row/column or add another row to the rows and data arrays.
	 * 
	 * @param rows The names of the rows (oversize array)
	 * @param rowSize The number of rows in the rows and data arrays that have been used.
	 * @param cols The  names of the columns (perfect size array).
	 * @param data The data that is stored for each row and column.
	 * @param item The item to be added to the array. This may go in an existing row or may form a new rows if item is
	 * not contained in rows.
	 * @param month The name of the column to be updated.
	 * @param amount The amount to be added to the existing element, or stored in the new element.
	 * @return The number of rows that have been used in rows and data.  This could be the same as rowSize, or may be
	 * one larger if the row name is not already in the rows array.
	 */
	public static int addToData(String[] rows, int rowSize, String[] cols, double[][] data, String item, String month, double amount)
	{	
		//TODO Write the body of this method
		int itemIndex = -1;
		int monthIndex = -1;

		for (int i = 0; i < rowSize; i++) {
			if (rows[i].equals(item)) {
				itemIndex = i;
			}
		}

		for (int i = 0; i < cols.length; i++) {
			if (cols[i].equals(month) ) {
				monthIndex = i;
			}
		}

		if (itemIndex > -1 && monthIndex > -1) {
			data[itemIndex][monthIndex] += amount;
		}
		else {
			rows[rowSize] = item;
			data[rowSize][monthIndex] = amount;
			rowSize++;
		}

		return rowSize;
	}
	

	/** Find a given label in an array. The array may be either oversize or
	 * perfect size. In other words, this method may be used with both rows and cols.
	 * @param array The array to be searched. This array is not sorted and should not be sorted.
	 * @param arraySize The number of elements used in the array.
	 * @param label The label to find in the array.
	 * @return The index in the array where label was found, or -1 if label was not found. 
	 */
	// This method is written in such a way that it can be used for either an
	// overisze or perfect size array
	public static int findLabelIndex(String[] array, int arraySize, String label)
	{
		int index = -1;

		for (index = 0; index < arraySize; index++) {
			if (array[index].equals(label)) {
				return index;
			}
		}
		return index;
	}
	
	/** Find the total of the data elements for a given month.
	 * 
	 * @param cols The names of the columns (the months).
	 * @param data The data stored in each row and column.
	 * @param rowSize The number of rows in the data array.
	 * @param month The month where the data is sought.
	 * @return The sum of the values in the data array, in the column with label month.
	 */
	public static double findMonthlyTotal(String[] cols, double[][]data, int rowSize, String month)
	{
		double monthTotal = 0.0;
		int index = findLabelIndex(cols, cols.length, month);

		for (int i = 0; i < rowSize; i++) {
			monthTotal += data[i][index];
		}
		return monthTotal;
	}

	
	/** Find the total of all values in the data array between 0 and rowSize for all columns.
	 * 
	 * @param data An array of data.
	 * @param rowSize The number of rows that were used in the array.
	 * @param colSize The number of columns in the array.
	 * @return The sum of the values in all of the data array between indices 0,0 and rowsSize, colSize.
	 */
	public static double findGrandTotal(double[][]data, int rowSize, int colSize) {
		//TODO Write the body of this method
		double grandTotal = 0;
		for (int rows = 0; rows < rowSize; ++rows) { // rows/columns makes it easier to read what you're doing.
			for (int columns = 0; columns < colSize; ++columns) {
				grandTotal += data[rows][columns];
			}
		}
		return grandTotal;
	}
	
	/** Find the sum of all of the values in the array that are between lowerAmount and upperAmount (both inclusive).
	 * 
	 * @param data The array of data.
	 * @param rowSize The number of rows in the array that have been used.
	 * @param colSize The number of columns in the array that have been used.
	 * @param lowerAmount The lower bound of values to include (inclusive).
	 * @param upperAmount The upper bound of values to include (inclusive).
	 * @return
	 */
	public static double findRangeTotal(double[][]data, int rowSize, int colSize, double lowerAmount, double upperAmount)
	{
		double rangeTotal = 0.0;

		for (int rowsIndex = 0; rowsIndex < rowSize; rowsIndex++) {
			for (int colIndex = 0; colIndex < colSize; colIndex++) {
				if (data[rowsIndex][colIndex] >= lowerAmount && data[rowsIndex][colIndex] <= upperAmount) {
					rangeTotal += data[rowsIndex][colIndex];
				}
			}
		}

		return rangeTotal;
	}
	
	/** Find the sum of all of the values in the data array with row label item.
	 * 
	 * @param rows The names of the rows.
	 * @param rowSize The number of rows in rows and data that were used.
	 * @param colSize The number of cols in data.
	 * @param data The data array.
	 * @param item The name of the row that should be summed.
	 * @return The sum of the values in the row that is labeled item.
	 */
	public static double findItemTotal(String[] rows, int rowSize, int colSize, double[][]data, String item)
	{
		double itemTotal = 0.0;
		int itemIndex = -1;

		for (int index = 0; index < rowSize; ++index) {
			if (item.equals(rows[index])) {
				itemIndex = index;
			}
		}

		if (itemIndex > -1) {
			for (int index = 0; index < colSize; index++) {
				itemTotal += data[itemIndex][index];
			}
		}
		return itemTotal;
	}
	
	/** Show the menu to the user and get a choice from the menu.
	 * 
	 * @param stdin The Scanner that is hooked up to the console.
	 * @return The number of the menu item selected.
	 */
	public static int getMenuChoice(Scanner stdin)
	{
		System.out.println("Choose one of the following options:");
		System.out.println("1. Find monthly total");
		System.out.println("2. Find item total");
		System.out.println("3. Find grand total");
		System.out.println("4. Find range total");
		System.out.println("5. Add to table");
		System.out.println("6. Exit program");
		
		int choice = stdin.nextInt();
		stdin.nextLine();
		
		while (choice < 0 || choice > 6)
		{
			System.out.println("Please enter a value from 1 to 6");
			choice = stdin.nextInt();
			stdin.nextLine();
		}
		
		return choice;
	}
	
	/** Read the contents of a file with the given fileName into the rows and data array. The format of the file is as follows:
	 * The number of rows <newline>
	 * For each rows in the data array:
	 * The row header then colSize double values, ending in a newline character.
	 * 
	 * @param rows The oversize array that contains the names of the rows. This array must be constructed
	 * before the method is called.
	 * @param data The array that contains a two-dimensional group of values. This array  must be constructed before the
	 * method is called.
	 * @param colSize The number of columns in data.
	 * @param fileName The name of the file that contains the data.
	 * @return The number of rows that were read in from the file.
	 * @throws FileNotFoundException If the file with the given fileName cannot be found in the Project directory.
	 */
	public static int readFile(String[] rows, double[][] data, int colSize, String fileName) throws FileNotFoundException
	{
		Scanner file = new Scanner(new File(fileName));
		
		int index;
		int size = file.nextInt();
		file.nextLine(); // get rid of newline character
		
		for (index=0; index < size && file.hasNextLine() && index < rows.length; ++index)
		{
			// Each row has a header and then twelve double values to be read in
			rows[index] = file.next();
			for (int count=0; count < colSize; ++count)  // explain that test in handout
			{
				data[index][count] = file.nextDouble();
			}
			file.nextLine(); // get rid of that pesky newline
		}
		
		file.close();
		return index;
	}
	
	/** Write the rows and data array out to a file with the given name.
	 * 
	 * @param rows The array that contains the name of the rows.
	 * @param rowSize The number of rows that are used (both in rows and data).
	 * @param colSize The number of columns that are needed.
	 * @param data A two-dimensional array that contains the amount spent in that category/month.
	 * @param fileName The name of the file to write.
	 * @throws FileNotFoundException If it is impossible to write to this file.
	 */
	public static void writeFile(String[] rows, int rowSize, int colSize, double[][] data, String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);

		pw.write(rowSize + "\n");
		for (int row = 0; row < rowSize; ++row) {
			pw.write(rows[row] + " ");
			for (int col = 0; col < colSize; ++col) {
				pw.write(data[row][col] + " ");
			}
			pw.write("\n");
		}

		pw.close();
	}
}

