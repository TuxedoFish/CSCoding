package answers;

import java.util.ArrayList;
import java.util.Arrays;

public class Question5 {
	public static int shareExchange(int[] allowedAllocations, int totalValue) {
		//Trivial solution if only one value in array
		if(allowedAllocations.length==0) { return 0; }
		if(allowedAllocations.length==1) { return totalValue/allowedAllocations[0]; }
		//Having it in size order will help us massively
		Arrays.sort(allowedAllocations);
		System.out.println("ARRAY SIZE : " + allowedAllocations.length);
		
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
		int totalMinValue = totalValue-allocs.get(allocs.size()-1);
		
		int maxValue = allocs.get(allocs.size()-1);
		float density = (maxValue-trueMin)/allocs.size();
		int loopUntil = 0;
		if(totalValue>maxValue && allocs.size()>20) {
			if((int)(allocs.size()*0.1)<10) {
				loopUntil = (int) (allocs.size()*0.9);
			} else {
				loopUntil = allocs.size()-10;
			}
		}
		System.out.println ("MIN : " + trueMin + ", MAX : " + maxValue + ", DENSITY : " + density);
		
		ArrayList<Integer> nextValuesA = new ArrayList<Integer>();
		ArrayList<Integer> nextValuesB = new ArrayList<Integer>();
		
		ArrayList<Integer> numbersSeen = new ArrayList<>();
		
		Integer[] nextValuesAArray = null; Integer[] nextValuesBArray = null;
		boolean isA = true;
		
		//Now that we have a sorted array we want to look for 2 perfect matches but also store any combination 
		//of 2 allocations so we can then check those on the next round
		boolean nextStage = false;
		for(int i=allocs.size()-1; i>=loopUntil; i--) {
			//Only keeps going until the value would be greater then totalValue or not
			//possible to make even with smallest value
			boolean limitReached = false;
			for(int j=0; j<allocs.size()-loopUntil && !limitReached; j++) {
				int combinedValue = allocs.get(i) + allocs.get(j);
				//We only had to combine 2 in order to get the best match hence best match at this stage
				if(combinedValue==totalValue) {return 2;} 
				else if(combinedValue==totalAchievableValue) { nextStage = true; } 
				if(combinedValue>totalValue) { limitReached=true; }
				else if(combinedValue<totalAchievableValue) { nextValuesA.add(combinedValue);} 
			}
		}
		
		//We want a sorted array of all the next items to save time for our logic
		nextValuesAArray = nextValuesA.toArray(new Integer[nextValuesA.size()]);
		Arrays.sort(nextValuesAArray);
		nextValuesA=removeRepetions(nextValuesAArray);
		
		//Stops us going down routes we have already seen
		numbersSeen.addAll(nextValuesA);
		Integer[] numbersSeenArray = numbersSeen.toArray(new Integer[numbersSeen.size()]);
		Arrays.sort(numbersSeenArray);
		numbersSeen.addAll(Arrays.asList(numbersSeenArray));
		
		//Here we reached the value that is one min value away from the true value so we know it will be one level away
		if(nextStage) {return 3;}
		
		int numbersAdded = 2;
		//Now we need to calculate the logic for going even deeper
		boolean stopped = false;
		while(!stopped) {
			loopUntil = 0;
			boolean bigEnough = false;
			if(totalValue>maxValue*(numbersAdded+1) && allocs.size()>20) {
				loopUntil = allocs.size()-2;
				bigEnough = true;
			}
			if(isA) {
				for(int i=nextValuesA.size()-1; i>=0; i--) {
					boolean limitReached = false;
					if(!bigEnough) {
						for(int j=0; j<allocs.size()&&!limitReached; j++) {
							int combinedValue = nextValuesA.get(i) + allocs.get(j);
							if(combinedValue==totalValue) {return numbersAdded+1;} 
							else if(combinedValue>totalValue) { limitReached=true; }
							else if(combinedValue==totalAchievableValue) { nextStage = true; } 
							else if(combinedValue<totalAchievableValue) { nextValuesB.add(combinedValue);}
						}
					} else {
						for(int j=(allocs.size()-1); j>loopUntil; j--) {
							int combinedValue = nextValuesA.get(i) + allocs.get(j);
							if(combinedValue<totalAchievableValue) { nextValuesB.add(combinedValue); }
							if(combinedValue==totalAchievableValue) { nextStage = true; } 
						}
					}
				}
			} else {
				for(int i=nextValuesB.size()-1; i>=0; i--) {
					boolean limitReached = false;
					if(!bigEnough) {
						for(int j=0; j<allocs.size()&&!limitReached; j++) {
							int combinedValue = nextValuesB.get(i) + allocs.get(j);
							if(combinedValue==totalValue) { return numbersAdded+1; } 
							else if(combinedValue==totalAchievableValue) { nextStage = true; }
							else if(combinedValue>totalValue) { limitReached=true; }
							else if(combinedValue<totalAchievableValue) { nextValuesA.add(combinedValue);}
						}
					} else {
						for(int j=(allocs.size()-1); j>loopUntil;j--) {
							int combinedValue = nextValuesB.get(i) + allocs.get(j);
							if(combinedValue<totalAchievableValue) { nextValuesA.add(combinedValue); }
							if(combinedValue==totalAchievableValue) { nextStage = true; } 
						}
					}
				}
			}
			numbersAdded ++;
			if(totalValue<=maxValue*(numbersAdded+1) && allocs.size()>20) {
				bigEnough = false;
			}
			if(nextStage) {return numbersAdded+1;}
			if(isA) { 
				nextValuesA.clear();
				if(nextValuesB.size()==0) {return 0;}
				if(!bigEnough) {
					//We want a sorted array of all the next items to save time for our logic
					nextValuesBArray = nextValuesB.toArray(new Integer[nextValuesB.size()]);
					Arrays.sort(nextValuesBArray);
					nextValuesB = removeRepetions(nextValuesBArray);
					if(nextValuesB.size()==0) {stopped = true;}//Stops us going down routes we have already seen
					nextValuesB = removeSeenValues(nextValuesB, numbersSeen);
					numbersSeen.addAll(nextValuesB);
					numbersSeenArray = numbersSeen.toArray(new Integer[numbersSeen.size()]);
					Arrays.sort(numbersSeenArray);
					numbersSeen = removeRepetions(numbersSeenArray);
				}
			} else { 
				nextValuesB.clear(); 
				if(nextValuesA.size()==0) {return 0;}
				if(!bigEnough) {
					//We want a sorted array of all the next items to save time for our logic
					nextValuesAArray = nextValuesA.toArray(new Integer[nextValuesA.size()]);
					Arrays.sort(nextValuesAArray);
					nextValuesA = removeRepetions(nextValuesAArray);
					if(nextValuesA.size()==0) {stopped = true;}
					//Stops us going down routes we have already seen
					nextValuesA = removeSeenValues(nextValuesA, numbersSeen);
					numbersSeen.addAll(nextValuesA);
					numbersSeenArray = numbersSeen.toArray(new Integer[numbersSeen.size()]);
					Arrays.sort(numbersSeenArray);
					numbersSeen = removeRepetions(numbersSeenArray);
				}
			}
			isA = !isA;
		}
		//Would never reach here
		return 1;
	}
	
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
	
	public static ArrayList<Integer> removeSeenValues(ArrayList<Integer> toRemove, ArrayList<Integer> seenValues) {
		int j=0;
		for(int i=0; i<toRemove.size(); i++) {
			while(j<seenValues.size()-1 && toRemove.get(i)>=seenValues.get(j)) {
				if(toRemove.get(i).equals(seenValues.get(j))) {toRemove.remove(i);}
				j++;
			}
		}
		return toRemove;
	}
}
