package answers;

import java.util.ArrayList;
import java.util.Arrays;

public class Question5 {
	public static int shareExchange(int[] allowedAllocations, int totalValue) {
		//Trivial solution if only one value in array
		if(allowedAllocations.length==1) { return totalValue/allowedAllocations[0]; }
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

		ArrayList<Integer> nextValuesA = new ArrayList<Integer>();
		ArrayList<Integer> nextValuesB = new ArrayList<Integer>();
		
		Integer[] nextValuesAArray = null; Integer[] nextValuesBArray = null;
		boolean isA = true;
		
		//Now that we have a sorted array we want to look for 2 perfect matches but also store any combination 
		//of 2 allocations so we can then check those on the next round
		boolean nextStage = false;
		for(int i=allocs.size()-1; i>=0; i--) {
			//Only keeps going until the value would be greater then totalValue or not
			//possible to make even with smallest value
			boolean limitReached = false;
			for(int j=0; j<allocs.size() && !limitReached; j++) {
				int combinedValue = allocs.get(i) + allocs.get(j);
				//We only had to combine 2 in order to get the best match hence best match at this stage
				if(combinedValue==totalValue) { return 2; } 
				else if(combinedValue==totalAchievableValue) { nextStage = true; } 
				else if(combinedValue<totalAchievableValue) { nextValuesA.add(combinedValue); }
				
				if(combinedValue>totalAchievableValue) { limitReached = true; }
			}
		}
		//Here we reached the value that is one min value away from the true value so we know it will be one level away
		if(nextStage) {return 3;}
		
		//We want a sorted array of all the next items to save time for our logic
		nextValuesAArray = nextValuesA.toArray(new Integer[nextValuesA.size()]);
		Arrays.sort(nextValuesAArray);
		//We then delete repetitions and use this
		nextValuesA = removeRepetions(nextValuesBArray);

		int numbersAdded = 2;
		//Now we need to calculate the logic for going even deeper
		while(true) {
			if(isA) {
				for(int i=nextValuesAArray.length-1; i>0; i--) {
					boolean limitReached = false;
					for(int j=0; j<allocs.size() && !limitReached; j++) {
						int combinedValue = nextValuesA.get(i) + allocs.get(j);
						if(combinedValue==totalValue) { return numbersAdded + 1; } 
						else if(combinedValue==totalAchievableValue) { nextStage = true; } 
						else if(combinedValue<totalAchievableValue) { nextValuesB.add(combinedValue); }
						
						if(combinedValue>totalAchievableValue) { limitReached = true; }
					}
				}
			} else {
				for(int i=nextValuesB.size()-1; i>0; i--) {
					boolean limitReached = false;
					for(int j=0; j<allocs.size() && !limitReached; j++) {
						int combinedValue = nextValuesB.get(i) + allocs.get(j);
						if(combinedValue==totalValue) { return numbersAdded + 1; } 
						else if(combinedValue==totalAchievableValue) { nextStage = true; } 
						else if(combinedValue<totalAchievableValue) { nextValuesA.add(combinedValue); }
						
						if(combinedValue>totalAchievableValue) { limitReached = true; }
					}
				}
			}
			numbersAdded ++;
			if(nextStage) {
				return numbersAdded + 1;
			}
			if(isA) { 
				nextValuesA.clear();
				//We want a sorted array of all the next items to save time for our logic
				nextValuesBArray = nextValuesB.toArray(new Integer[nextValuesB.size()]);
				Arrays.sort(nextValuesBArray);
				//We then delete repetitions and use this
				nextValuesB = removeRepetions(nextValuesBArray);
			} else { 
				nextValuesB.clear(); 
				//We want a sorted array of all the next items to save time for our logic
				nextValuesAArray = nextValuesA.toArray(new Integer[nextValuesA.size()]);
				Arrays.sort(nextValuesAArray);
				//We then delete repetitions and use this
				nextValuesA = removeRepetions(nextValuesBArray);
			}
			isA = !isA;
		}
	}
	/*
	 * A function for removing any repetions in an array of already summed values
	 */
	public static ArrayList<Integer> removeRepetions(Integer[] array) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		//Delete repetitions 
		int minimumValue = -1;
		for(int i=0; i<array.length; i++) {
			if(array[i]>minimumValue) {
				returnList.add(array[i]);
				minimumValue=array[i];
			} 
		}
		return returnList;
	}

}
