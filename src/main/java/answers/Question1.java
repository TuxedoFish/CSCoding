package answers;

import java.util.ArrayList;
import java.util.Arrays;
import java.math.*;

import answers.Match;
import answers.TwoBitSplit;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	public static int NUMBER_OF_BITS = 16;
	public static int BITS_OBSERVED = 1;
	
	public static int bestMergedPortfolio(int[] portfolios) {
		System.out.println(portfolios.length);
		Arrays.sort(portfolios);
		System.out.println("MIN : " + portfolios[0] + " : MAX : " + portfolios[portfolios.length-1]);
		//Edge case: size 1 or 0
		if(portfolios.length<=1) {
			return 0;
		}

		int maxEvalBruteForce = 0;
		for(int i=1; i<portfolios.length; i++) {
			for(int j=0; j<i; j++) {
				int value = (portfolios[i]&65535)^(portfolios[j]&65535);
				if(value > maxEvalBruteForce) {
					maxEvalBruteForce = value;
				}
			}
		}
		
		return maxEvalBruteForce;
	}

}