package answers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Question5 {
	public static int LOOP_NUMBERS = 10;
	
	//Takes an array @allowedAllocations of numbers and a value @totalValue
	//Returns a number that represents shortest amount of numbers added together to make the value
	public static int shareExchange(int[] allowedAllocations, int totalValue) {
		//Edge cases
		if(allowedAllocations.length==0) { return 0; }
		if(allowedAllocations.length==1) {
			if(totalValue % allowedAllocations[0] == 0) { return totalValue/allowedAllocations[0]; } 
			else {return 0;}
		}
		
		//First we sort the array and remove any repetitions
		Arrays.sort(allowedAllocations);
		removeRepetitions(allowedAllocations);
		
		//Now we have sorted arrays
		int min = allowedAllocations[0]; 
		int max = allowedAllocations[allowedAllocations.length-1];
		//Arrays to store the results of selections
		ArrayList<Integer> selectionsTemp = new ArrayList<Integer>();
		int[] selectionsPerm = allowedAllocations;
		
		//Set up some variables we will loop over
		int numbersAdded = 0;
		boolean closeEnough = false;
		
		while(true) {
			//Logically if (totalValue >> maxValue) we only want to add very large values until we get pretty close
			if( max*(numbersAdded+5) > totalValue && allowedAllocations.length>5 ) { closeEnough = true; } 
			else { closeEnough = false; }
			
			if(closeEnough) {
				//Logic too loop through all possibilities
				for(int i=0; i<selectionsPerm.length; i++) {
					for(int j=0; j<allowedAllocations.length; j++) {
						int value = selectionsPerm[i] + allowedAllocations[j];
						//If the total is still below the max value we are still okay to add
						if(value<totalValue) { selectionsTemp.add(value); }
						//If it is equal to the value we have found our optimum route
						if(value==totalValue) { return numbersAdded + 1; }
					}
				}
			} else {
				for(int i=1; i<=LOOP_NUMBERS && i <= selectionsPerm.length; i++) {
					//Adds the LOOP_NUMBERS highest values to the highest other LOOP_NUMBERS values excluding maximum
					for(int j=1; j<=LOOP_NUMBERS && j <= allowedAllocations.length; j++) {
						selectionsTemp.add(selectionsPerm[selectionsPerm.length-i] + allowedAllocations[allowedAllocations.length-j]);
					}
				}
			}
			//If we have no options left then we may have missed an option or there are no possible options
			if(selectionsTemp.size()==0) { return 0; }
			//Stores the temporary numbers in a permanent array for the next check
			//Also clears the old array which now will have values added
			if(closeEnough) {
				//If it was close it will remove repitions as this slows it down very much
				//Since each step effectively squares the calculations being done
				Collections.sort(selectionsTemp);
				selectionsPerm = removeRepetitions(getIntegerArray(selectionsTemp));
			} else {
				selectionsPerm = getIntegerArray(selectionsTemp);
			}

			//Must clear the temporary numbers every time
			selectionsTemp.clear();
			numbersAdded ++;
		}
	}
	/*
	 * Takes an ArrayList  
	 * Creates an Array of defined size 
	 * Adds the contents of the ArrayList to the Array
	 */
	public static int[] getIntegerArray(ArrayList<Integer> array) {
		int[] out = new int[array.size()];
		for(int i=0; i<array.size(); i++) { out[i]=array.get(i); }
		return out;
	}
	/*
	 * Takes a sorted int[]
	 * Loops through the list deleting any repetitions
	 * Then gets it as an int[] and returns
	 */
	public static int[] removeRepetitions(int[] in) {
		ArrayList<Integer> out_list = new ArrayList<Integer>();
		int currentVal = -1;
		for(int i=0; i<in.length; i++) {
			int val = in[i];
			if(val != currentVal) { out_list.add(val); currentVal = val; }
		}
		return getIntegerArray(out_list);
		
	}
}
