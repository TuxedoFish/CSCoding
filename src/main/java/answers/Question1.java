package answers;

import java.util.ArrayList;
import java.util.Arrays;
import java.math.*;

import answers.Match;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	//Ignore 1st bit as it -ve and +ve sign
	public static int NUMBER_OF_BITS = 31;
	public static int BITS_OBSERVED = 1;
	
	public static int bestMergedPortfolio(int[] portfolios) {
		//Edge case: size 1 or 0
		if(portfolios.length<=1) {
			return 0;
		}
		Arrays.sort(portfolios);

		int maxEvalBruteForce = 0;
		for(int i=portfolios.length-1; i>=0; i--) {
			for(int j=0; j<i; j++) {
				int value = (portfolios[i]&2147483647)^(portfolios[j]&2147483647);
				if(value > maxEvalBruteForce) {
					maxEvalBruteForce = value;
				}
			}
		}
		
		return maxEvalBruteForce;
	}

}