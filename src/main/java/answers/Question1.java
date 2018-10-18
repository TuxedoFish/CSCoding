package answers;

import java.util.ArrayList;
import java.math.*;

import answers.Match;
import answers.TwoBitSplit;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	public static int NUMBER_OF_BITS = 16;
	public static int BITS_OBSERVED = 2;
	public static int INT_MASK = 3;
	
	
	public static int bestMergedPortfolio(int[] portfolios) {
		//Initialise shift
		int shift = NUMBER_OF_BITS - BITS_OBSERVED;
		
		//For first, third etc
		ArrayList<Integer> M_00 = new ArrayList<Integer>(); //0
		ArrayList<Integer> M_01 = new ArrayList<Integer>(); //1
		ArrayList<Integer> M_10 = new ArrayList<Integer>(); //2
		ArrayList<Integer> M_11 = new ArrayList<Integer>(); //3
		
		//Loop through the whole list of ints
		for(int i=0; i<portfolios.length; i++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			int firstTwoBits = portfolios[i]>>shift&INT_MASK;
			if(firstTwoBits == 0) { M_00.add(i); }
			if(firstTwoBits == 1) { M_01.add(i); }
			if(firstTwoBits == 2) { M_10.add(i); }
			if(firstTwoBits == 3) { M_11.add(i); }
		}

		//Initialise the first nodes with 2 sets which are known to have matched
		TwoBitSplit childA = new TwoBitSplit(1, M_00, M_11);
		TwoBitSplit childB = new TwoBitSplit(1, M_01, M_10);

		//Calculate which child (or both) has longest consecutive streak
		int maxDepthA = childA.split(portfolios);
		int maxDepthB = childB.split(portfolios);

		int maxDepth = Math.max(maxDepthA, maxDepthB);

		ArrayList<Match> bestMatches = new ArrayList<Match>();

		if(maxDepthA==maxDepth) { bestMatches.addAll(childA.getPossibleMatches()); }
		if(maxDepthB==maxDepth) { bestMatches.addAll(childB.getPossibleMatches()); }

		//Loop over last solutions to find best one
		int maxEval = 0;
		for(int i=0; i<bestMatches.size(); i++) {
			int eval = bestMatches.get(i).getA()^bestMatches.get(i).getB();
			if( eval > maxEval) { maxEval=eval; }
		}

		return maxEval;
	}

}
