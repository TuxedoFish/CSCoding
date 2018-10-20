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
		int trueMin = 0;
		for(int i=0; i<allowedAllocations.length; i++) {
			if(allowedAllocations[i]>minimumValue && allowedAllocations[i]<totalValue) {
				allocs.add(allowedAllocations[i]);
				if(minimumValue==-1) { trueMin = allowedAllocations[i]; }
				minimumValue=allowedAllocations[i];
				//Trivial Solution if totalValue is contained within the allowedAllocations of 1
			} else if (allowedAllocations[i]==totalValue) { return 1; }
		}
		
		int totalAchievableValue = totalValue-trueMin;
		
		//Now that we have a sorted array we want to look for 2 perfect matches but also store any combination 
		//of 2 allocations so we can then check those on the next round
		boolean nextStage = false;
		ArrayList<Integer> nextValues = new ArrayList<Integer>();
		for(int i=allocs.size()-1; i>=0; i--) {
			//Only keeps going until the value would be greater then totalValue or not
			//possible to make even with smallest value
			boolean limitReached = false;
			for(int j=0; j<allocs.size() && !limitReached; j++) {
				int combinedValue = allocs.get(i) + allocs.get(j);
				//We only had to combine 2 in order to get the best match hence best match at this stage
				if(combinedValue==totalValue) { return 2; } 
				else if(combinedValue==totalAchievableValue) { nextStage = true; } 
				else if(combinedValue<totalAchievableValue) { nextValues.add(combinedValue); }
			}
		}
		//Here we reached the value that is one min value away from the true value so we know it will be one level away
		if(nextStage) {return 3;}
		
		//Now we need to calculate the logic for going even deeper
		
		// TODO Auto-generated method stub
		return -1;
	}

}
