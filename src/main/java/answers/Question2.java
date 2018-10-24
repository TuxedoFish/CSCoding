package answers;

import java.util.ArrayList;
import java.util.Arrays;

public class Question2 {

	public static int equallyBalancedCashFlow(int[] cashflowIn, int[] cashflowOut) {
		System.out.println("IN_SIZE : " + cashflowIn.length + " OUT_SIZE : " + cashflowOut.length);
		
		Arrays.sort(cashflowIn);
		Arrays.sort(cashflowOut);
		
		System.out.println("MIN IN : " + cashflowIn[0] + " MAX_IN : " + cashflowIn[cashflowIn.length-1]);
		System.out.println("MIN OUT : " + cashflowOut[0] + " MAX_OUT : " + cashflowOut[cashflowOut.length-1]);
		
		//Check that none of the arrays contain the same values
		int j=0;
		for(int i=0; i<cashflowIn.length; i++) {
			while(j<cashflowOut.length && cashflowOut[j]<cashflowIn[i]) {
				if(cashflowIn[i]==cashflowOut[j]) {return 0;}
				j++;
			}
		}
		//We now know roughly how dense the arrays are
		float densityIn = (cashflowIn[cashflowIn.length-1]-cashflowIn[0])/cashflowIn.length;
		float densityOut = (cashflowOut[cashflowOut.length-1]-cashflowOut[0])/cashflowOut.length;

		System.out.println("IN_DENSITY : " + densityIn + " OUT_DENSITY : " + densityOut);
		
		return -1;
	}

}
