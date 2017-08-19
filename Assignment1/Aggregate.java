/*Iain Emslie 200279047 June 10 2017*/
/*Some code from Bill Bird's example*/

import java.io.*;
import java.util.*;

public class Aggregate{
	
	public static void main(String[] args){
		
		/*Read in command line arguments*/
		//At least four arguments are needed
		if (args.length < 4){
			showUsage();
			return;
		}
		String agg_function = args[0];
		String agg_column = args[1];
		String csv_filename = args[2];
		String[] group_columns = new String[args.length - 3];
		for(int i = 3; i < args.length; i++)
			group_columns[i-3] = args[i];
		
		//If invalid function arguments are provided
		if (!agg_function.equals("count") && !agg_function.equals("sum") && !agg_function.equals("avg")){
			showUsage();
			return;
		}
		
		//Create 2D array to hold data from file
		String[][] arr2D;
		
		//If agg column is equal to a group column then exit
		for(int i=0;i<group_columns.length;i++){
			if(agg_column.equals(group_columns[i])){
				System.err.println("Error: Aggregation Column is the same as a group column");
				return;
			}
		}
		
		//Get names of columns from file
		String[] column_names = getHeader(csv_filename);
		
		//Find number of columns in header line
		if(column_names ==null){
			return;
		}
		
		int num_col = column_names.length;
		int num_group = group_columns.length;

		//Assign arr2D the data from the infile
		arr2D = readFile(csv_filename, num_col);	
		
		//outputToScreen(arr2D);
		
		//Get aggregation and group column (returns -1 if not found and exit program)
		int agg_index = getAggIndex(column_names, agg_column);
		if(agg_index==-1){
			System.err.println("Error: Invalid column name in arguments");
			return;
		}
		int[] groupIndexArr = getGroupIndex(column_names, group_columns);

		
		String[][] aggArr = makeAggArray(arr2D, group_columns, agg_index, groupIndexArr);
		
		
		try{
			for(int i=groupIndexArr.length-1;i>=0;i--)
			{
				Arrays.sort(aggArr, new ColumnComparator(i, SortingOrder.ASCENDING));
			}	
		}
		catch(NullPointerException n){
			System.err.println("Error: aggArr is null");
			return;
		}

		
		/*Call function depending on which function argument is provided*/
		if(agg_function.equals("count")){
			for (String s: group_columns)
			System.out.print(s+",");
			System.out.println("count("+agg_column+")");
			count(aggArr, num_group);
		}
		if(agg_function.equals("sum")){
			for (String s: group_columns)
			System.out.print(s+",");
			System.out.println("sum("+agg_column+")");
			sum(aggArr, num_group);
		}
		if(agg_function.equals("avg")){
			for (String s: group_columns)
			System.out.print(s+",");
			System.out.println("avg("+agg_column+")");
			avg(aggArr, num_group);
		}
	}
	
	public static String[] getHeader(String csv_filename){
		//Create bufferedreader to read in data from file
		BufferedReader br = null;
		//Open file with bufferedreader
		try{
			br = new BufferedReader(new FileReader(csv_filename));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return null;
		}	
		//Read in first line of file as header_line
		String header_line;
		try{
			header_line = br.readLine(); 
		} catch (IOException e){
			System.err.printf("Error reading file\n", csv_filename);
			return null;
		}
		if (header_line == null){
			System.err.printf("Error: CSV file %s has no header row\n", csv_filename);
			return null;
		}
		//Split the header_line string into an array of string values using a comma as the separator.
		String[] column_names = header_line.split(",");
		return column_names;
	}
	
	//Counts the number of times each aggregate element occurs
	public static void count(String[][] aggArr, int num_group){
		
		double num_counted = 0;
		
		for(int i=0;i<aggArr.length-1;i++){ //Look at each row
			num_counted++;
			for(int j=0;j<num_group;j++){	//Look at each element in the row
				if(!aggArr[i][j].equals(aggArr[i+1][j])){     //If each element doesn't match the one in the next column
					for(int k=0;k<num_group;k++){
						System.out.print(aggArr[i][k]+",");
					}
					System.out.printf("%.3f",num_counted);
					System.out.println();
					num_counted=0;
					break;
				} 
			}
		}
		for(int k=0;k<num_group;k++){
			System.out.print(aggArr[aggArr.length-1][k]+",");
		}
		System.out.printf("%.3f",num_counted+1);
	}
	
	//Adds up the value of the group columns for each aggregate element
	public static void sum(String[][] aggArr, int num_group){
		
		double sum_total = 0;
		
		for(int i=0;i<aggArr.length-1;i++){ //Look at each row
		sum_total = sum_total + Double.parseDouble(aggArr[i][num_group]);
			for(int j=0;j<num_group;j++){	//Look at each element in the row
				if(!aggArr[i][j].equals(aggArr[i+1][j])){     //If each element doesn't match the one in the next column
					for(int k=0;k<num_group;k++){
						System.out.print(aggArr[i][k]+",");
					}
					System.out.printf("%.3f",sum_total);
					System.out.println();
					sum_total=0;
					break;
				} 
			}
			
		}
		sum_total = sum_total + Double.parseDouble(aggArr[aggArr.length-1][num_group]);
		for(int k=0;k<num_group;k++){
			System.out.print(aggArr[aggArr.length-1][k]+",");
		}
		System.out.printf("%.3f",sum_total);
	}
	
	//Averages the value of the group columns for each aggregate element
	public static void avg(String[][] aggArr, int num_group){
		
		double sum_total = 0;
		double num_counted = 0;
		
		for(int i=0;i<aggArr.length-1;i++){ //Look at each row
		num_counted++;
		sum_total = sum_total + Double.parseDouble(aggArr[i][num_group]);
			for(int j=0;j<num_group;j++){	//Look at each element in the row
				if(!aggArr[i][j].equals(aggArr[i+1][j])){     //If each element doesn't match the one in the next column
					for(int k=0;k<num_group;k++){
						System.out.print(aggArr[i][k]+",");
					}
					System.out.printf("%.3f",sum_total/num_counted);
					System.out.println();
					sum_total=0;
					num_counted=0;
					break;
				} 
			}
			
		}
		sum_total = sum_total + Double.parseDouble(aggArr[aggArr.length-1][num_group]);
		for(int k=0;k<num_group;k++){
			System.out.print(aggArr[aggArr.length-1][k]+",");
		}
		num_counted++;
		System.out.printf("%.3f",sum_total/num_counted);
	}
	
	//Read in data from .csv file and copy into 2D array
	public static String[][] readFile(String csv_filename, int num_col){
		
		//Get number of rows in the .csv file (-1 for header row)
		int num_row_in_file = getNumRows(csv_filename)-1;
		int num_row=0;
		String row;
		String[] row_parts = new String[num_col];
		String[][] arr2D = new String[num_row_in_file][num_col];

		//Create bufferedreader to read in data from file
		BufferedReader br = null;
		//Open file with bufferedreader
		try{
			br = new BufferedReader(new FileReader(csv_filename));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return null;
		}		
		//Get rid of header row
		try{
			row = br.readLine();
		} catch(IOException e){
				System.err.printf("Error reading file\n", csv_filename);
				return null;
		}
			
		//Copy each element to the 2D array
		for(int i=0;i<num_row_in_file;i++){
			try{
				row = br.readLine();
				row_parts = row.split(",");
				for(int j=0;j<num_col;j++){ 
					arr2D[num_row][j] = row_parts[j];	
					//System.err.print(arr2D[num_row][j]+" ");
				}
				//System.err.println();
				num_row++;
			}catch(IOException e){
				System.err.printf("Error reading file\n", csv_filename);
				return null;
			}
		}
		return arr2D;	
	}

	//Print the 2D array to screen
	public static void outputToScreen(String[][] arr2D){	
		for(int i=0;i<arr2D.length;i++){
			for(int j=0;j<arr2D[i].length;j++){
				System.err.print(arr2D[i][j]);
				if(j!=arr2D[i].length-1){
					System.err.print(",");
				}
			}
			System.err.println();
		}
	}
	
	public static String[][] makeAggArray(String[][] arr2D, String[] group_columns, int agg_index, int[] groupIndexArr){
		
		//Get the number of group_columns
		int num_group_col = group_columns.length;
		
		try{
			Double.parseDouble(arr2D[0][agg_index]);
		} catch(NumberFormatException n){
			System.err.println("Error: Aggregate column is not a double");
			return null;
		}
		
		//Construct a new array to hold the aggregations and groups (+1 is for agg column)
		String[][] reduced_arr = new String[arr2D.length][num_group_col+1];	
		
		//Make a new aggregate array of multiple columns
		for(int i=0;i<groupIndexArr.length;i++){
			for(int j=0;j<arr2D.length;j++){
			reduced_arr[j][i] = arr2D[j][groupIndexArr[i]];
			}
		}
		//Write the aggregate column
		for(int i=0;i<arr2D.length;i++){
			reduced_arr[i][num_group_col] = arr2D[i][agg_index];
		}
		
		//outputToScreen(reduced_arr);

		return reduced_arr;
	}
	
	//Finds the index in the header row of the column to aggregate
	public static int getAggIndex(String[] column_names, String agg_column){
		for(int i=0;i<column_names.length;i++){
			if(agg_column.equals(column_names[i])){
				return i;
			}
		}
		return -1;
	}
	
	//Finds the index in the header row of the grouping column
	public static int[] getGroupIndex(String[] column_names, String[] group_columns){
		
		//Array which will contain the index of each group column
		int[] groupIndexArr = new int[group_columns.length];
		
		//Compare each group name to each column name in header and store index in groupIndexArr
		for(int i=0;i<group_columns.length;i++){
			for(int j=0;j<column_names.length;j++){
				if(group_columns[i].equals(column_names[j])){
					groupIndexArr[i] = j;
				}
			}
		}
		
		return groupIndexArr;
	}
	
	//Reads each row in the .csv file and returns the number of rows counted
	public static int getNumRows(String csv_filename){
		String row;
		int num_row_in_file=0;
		//Create bufferedreader to read in csv file
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(csv_filename));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return -1;
		}
		//Find num of rows in file
		try{
			while((row = br.readLine())!= null){
				num_row_in_file++;
			}
			row = br.readLine(); 
		} catch (IOException e){
			System.err.printf("Error reading file\n");
			return -1;
		}
		return num_row_in_file;
	}
	
	//Returns an error message if incorrect command line arguments provided
	public static void showUsage(){
		System.err.printf("Usage: java Aggregate <function> <aggregation column> <csv file> <group column 1> <group column 2> ...\n");
		System.err.printf("Where <function> is one of \"count\", \"sum\", \"avg\"\n");	
	}
}

//Code from http://www.java67.com/2014/08/4-examples-to-sort-array-in-java.html for 2D array comparison
enum SortingOrder{
    ASCENDING, DESCENDING;
};

class ColumnComparator implements Comparator<Comparable[]> {
    private final int iColumn;
    private final SortingOrder order;

    public ColumnComparator(int column, SortingOrder order) {
        this.iColumn = column;
        this.order = order;
    }

    @Override
    public int compare(Comparable[] c1, Comparable[] c2) {
        int result = c1[iColumn].compareTo(c2[iColumn]);
        return order==SortingOrder.ASCENDING ? result : -result;
    }
}

