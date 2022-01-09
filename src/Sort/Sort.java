/*
 * This program allows users to do multiple sorting algorithms
 * Author: Adhithya Ananthan Regina
 * Date: 4/4/21
 */
package Sort;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Sort {

	Scanner consoleInput = new Scanner(System.in);
	String input;
	Scanner fileInput;
	int[] inputArray;
	long startTime;
	
	public Sort() { //Constructor
		//User inputs a number for file to be used
		System.out.println("Enter a number for the input file.");
		System.out.println("1: input1.txt 2: input2.txt 3: input3.txt 4: input4.txt");
		input = consoleInput.nextLine();
		//verifies input is valid
		if (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
				&& input.charAt(0) != '3' && input.charAt(0) != '4') {
			System.out.println("Enter 1,2,3, or 4");
			while (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
					&& input.charAt(0) != '3' && input.charAt(0) != '4') {
				input = consoleInput.nextLine();
			}
		}
		//ensures file is found
		try {
			fileInput = new Scanner(new File("input" + input.charAt(0) + ".txt"));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		//takes in file input
		String infile = fileInput.nextLine();
		String[] inputStringArray = infile.split(",");
		inputArray = new int[inputStringArray.length];
		for (int i = 0; i < inputStringArray.length; i++) {
			inputArray[i] = Integer.parseInt(inputStringArray[i]);
			System.out.println(inputArray[i]);
		}
		//allows users to select sorting algorithm
		System.out.println("Enter a number for the sort you want to use.");
		System.out.println("1: Bubble 2: Selection 3: Table 4: Quick");
		input = consoleInput.nextLine();
		if (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
				&& input.charAt(0) != '3' && input.charAt(0) != '4') {
			System.out.println("Enter 1,2,3, or 4");
			while (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
					&& input.charAt(0) != '3' && input.charAt(0) != '4') {
				input = consoleInput.nextLine();
			}
		}
		//calculates time used for algorithm
		startTime = System.currentTimeMillis();
		//Bubble sort option
		if(input.contentEquals("1")) {
			inputArray = bubbleSort(inputArray);
		}
		//Selection sort option
		if(input.contentEquals("2")){
			inputArray = selectionSort(inputArray);
		}
		//Table sort option
		if(input.contentEquals("3")) {
			inputArray = tableSort(inputArray);
		}
		//Quick Sort Option
		if(input.contentEquals("4")) {
			inputArray = quickSort(inputArray, 0, inputArray.length - 1);
		}
		//Calculates total time
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Total Time:" + totalTime);
		//Prints output to file output.txt
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(new File("output.txt")));
			String output = "";
			for(int i = 0; i < inputArray.length; i++) {
				 output += inputArray[i] + ",";
			}
			output += "\nTotal Time:" + totalTime;
			pw.write(output);
			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(0);
		} 
	}
	
	public static void main(String[] args) {//Main string arguments
		new Sort();

	}
	//Compare each pair of numbers and move the larger to the right
	int[] bubbleSort(int[] array) {
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length - 1; i++) {
				//if the one on the left is larger
				if (array[i] > array[i+1]) {
					//swap
					int temp = array[i];
					array[i] =  array[i+1];
					array[i+1] = temp;
				}
			}
		}
		return array;
	}
	//Find the smallest and move it to the front
	int[] selectionSort(int[] array) {
		for (int j = 0; j < array.length; j++) {
			int smallestNumber = array[j];
			int smallestIndex = j;
			for (int i = j; i < array.length; i++) {
				if (array[i] < smallestNumber) {
					smallestNumber = array[i];
					smallestIndex = i;
				}
			}
			int temp = array[smallestIndex];
			array[smallestIndex] = array[j];
			array[j] = temp;
		}
		return array;
	}
	//Tally how often you see each number, print out that number of times
	int[] tableSort(int[] array) {
		int[] tally = new int[1001];
		for (int i = 0; i < array.length; i++) {
			tally[array[i]]++;
		}
		
		int count = 0;
		//i keeps track of the actual number
		for (int i = 0; i < tally.length; i++) {
			//j keeps track of how many times we've seen that number
			for (int j = 0; j < tally[i]; j++) {
				array[count] = i;
				count++;
			}
		}
		
		return array;
	}
	//sort numbers such that numbers on the left are less and numbers on the right are greater, does multiple partitions
	/*
	 * Code from GeeksforGeeks, solution by Ayush Choudhary
	 * Source: https://www.geeksforgeeks.org/quick-sort/
	 * Allows quickSort to be used over multiple partitions, source was also used for guidance 
	 */
	int[] quickSort(int[] array, int low, int high) {
		if (low < high) {
			int pivotPosition = partition(array, low, high);
			//does partitions multiple times till array is sorted
			quickSort(array, low, pivotPosition - 1);
			quickSort(array, pivotPosition + 1, high);
		}
		return array;
	}
	//method partitions the array at set pivot points
	public static int partition(int[] array, int low, int high) {
		//pivot point
		int pivot = array[low];
		//index of the larger element
		int i = (high + 1);
		//index of the smaller element
		for (int j = high; j >= low + 1; j--) {
			//if the array at the smaller element is greater than pivot
			if (array[j] > pivot) {
				//decrease larger element index
				i--;
				//swap array at i and j
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		//after going through set swap pivot with number before the final point swapped
		int temp = array[i-1];
		array[i-1] = array[low];
		array[low] = temp;
		return (i-1);
	}
	

}
