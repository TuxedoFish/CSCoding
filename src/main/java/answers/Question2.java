package answers;

import java.util.ArrayList;
import java.util.Arrays;

public class Question2 {
	
	public static int equallyBalancedCashFlow(int[] cashflowIn, int[] cashflowOut) {
		//Grab the subsets
		int[] possibilitiesIn = getSubsets(cashflowIn);
		int[] possibilitiesOut = getSubsets(cashflowOut);
		//Sort the arrays on size
		Arrays.sort(possibilitiesIn);
		Arrays.sort(possibilitiesOut);
		
		System.out.println("IN SUBSETS : " + possibilitiesIn.length + " OUT SUBSETS : " + possibilitiesOut.length);
		
		int j = 1;
		int minValue=Math.min(possibilitiesIn[1], possibilitiesOut[1]);
		for(int i=1; i<possibilitiesIn.length; i++) {
			while(j<possibilitiesOut.length && possibilitiesOut[j]<possibilitiesIn[i]+minValue) {
				int difference = Math.abs(possibilitiesOut[j]-possibilitiesIn[i]);
				if(difference<minValue) { minValue = difference; }
				j++;
			}
		}
		
		return minValue;
	}
	
	//Get all subsets of given set[] 
    public static int[] getSubsets(int[] set) 
    { 
        int n = set.length; 
        int[] subsets = new int[(int) Math.pow(2, set.length)];
  
        //Run a loop for printing all 2^n 
        //subsets one by one 
        for (int i = 0; i < (1<<n); i++) { 
            // Print current subset 
            for (int j = 0; j < n; j++) {
                //(1<<j) is a number with jth bit 1 
                //so when we 'and' them with the 
                //subset number we get which numbers 
                //are present in the subset and which 
                //are not 
                if ((i & (1 << j)) > 0) { subsets[i] += set[j]; }
            }
        } 
        return subsets;
    } 

}
