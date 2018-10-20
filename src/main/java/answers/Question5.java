package answers;

import java.util.ArrayList;
import java.util.Arrays;

public class Question5 {

	public static int shareExchange(int[] allowedAllocations, int totalValue) {
		//Having it in size order will help us massively
		Arrays.sort(allowedAllocations);
		
		//Now we define an array that will hold no duplicates or values higher then totalVal
		ArrayList<Integer> allocs = new ArrayList<Integer>();
		//Puts only unique values and values lower then the total into an arraylist (still sorted)
		int minimumValue = -1;
		int trueMin;
		for(int i=0; i<allowedAllocations.length; i++) {
			if(allowedAllocations[i]>minimumValue && allowedAllocations[i]<totalValue) {
				allocs.add(allowedAllocations[i]);
				if(minimumValue==-1) { trueMin = allowedAllocations[i]; }
				minimumValue=allowedAllocations[i];
				//Trivial Solution if totalValue is contained within the allowedAllocations of 1
			} else if (allowedAllocations[i]==totalValue) { return 1; }
		}
		
		//Now that we have a sorted array we want to 
		for(int i=allocs.size()-1; i>=0; i--) {
			
		}
		
		// TODO Auto-generated method stub
		return -1;
	}

}
