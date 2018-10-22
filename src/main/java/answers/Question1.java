package answers;

import java.util.ArrayList;
import java.math.*;

import answers.Match;
import answers.TwoBitSplit;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	public static int NUMBER_OF_BITS = 16;
	public static int BITS_OBSERVED = 1;
	
	public static int bestMergedPortfolio(int[] portfolios) {
		float startTime = System.nanoTime();
		//Edge case: if there is no portfolios or 1 portfolio "combining" 2 portfolios has no meaning so return 0
		if(portfolios.length<=1) {
			return 0;
		}

		int maxEvalBruteForce = 0;
		for(int i=0; i<portfolios.length; i++) {
			for(int j=0; j<portfolios.length; j++) {
				if(i!=j && (portfolios[i]&65535)>0 && (portfolios[j]&65535)>0) {
					int value = (portfolios[i]&65535)^(portfolios[j]&65535);
					if(value > maxEvalBruteForce) {
						maxEvalBruteForce = value;
					}
				}
			}
		}
		float finishTime = System.nanoTime();
		System.out.println( "ANSWER" + maxEvalBruteForce );
		System.out.println("IN: " + (finishTime - startTime));
		
		return maxEvalBruteForce;
	}

}