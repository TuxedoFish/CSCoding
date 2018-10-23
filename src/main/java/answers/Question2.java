package answers;

import java.util.ArrayList;
import java.util.Arrays;

public class Question2 {

	public static int equallyBalancedCashFlow(int[] cashflowIn, int[] cashflowOut) {
		System.out.println("IN_SIZE" + cashflowIn.length + " OUT_SIZE : " + cashflowOut.length);
		
		Arrays.sort(cashflowIn);
		Arrays.sort(cashflowOut);
		
		System.out.println("MIN IN" + cashflowIn[0] + " MAX_IN : " + cashflowIn[cashflowIn.length]);
		System.out.println("MIN OUT" + cashflowIn[0] + " MAX_OUT : " + cashflowOut[cashflowOut.length]);
		
		ArrayList<Integer> cashIn = new ArrayList<Integer>();
		ArrayList<Integer> cashOut = new ArrayList<Integer>();
		
		if(cashflowIn.length>100) {
			int minVal = -1;
			for(int i=0; i<cashflowIn.length; i++) {
				int value = cashflowIn[i];
				if(value>minVal) {
					cashIn.add(value);
					minVal=value;
				}
			}
			System.out.println("SORTED SIZE IN : " + cashIn.size());
		}
		if(cashflowOut.length>100) {
			int minVal = -1;
			for(int i=0; i<cashflowOut.length; i++) {
				int value = cashflowOut[i];
				if(value>minVal) {
					cashIn.add(value);
					minVal=value;
				}
			}
			System.out.println("SORTED SIZE OUT : " + cashOut.size());
		}
		return -1;
	}

}
