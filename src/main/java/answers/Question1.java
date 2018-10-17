package answers;

import java.util.ArrayList;
import java.Math;

import answers.Match;
import answers.TwoBitSplit;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	public static int NUMBER_OF_BITS = 16;
	public static int BITS_OBSERVED = 2;
	public static byte BYTE_MASK = 0x07;
	
	//For first, third etc
	private ArrayList<Integer> 0_00 = new ArrayList<Integer>; //0
	private ArrayList<Integer> 0_01 = new ArrayList<Integer>; //1
	private ArrayList<Integer> 0_10 = new ArrayList<Integer>; //2
	private ArrayList<Integer> 0_11 = new ArrayList<Integer>; //3
	
	public static int bestMergedPortfolio(int[] portfolios) {
		//Initialise shift
		int shift = NUMBER_OF_BITS - BITS_OBSERVED;
		
		//Loop through the whole list of ints
		for(int i=0; i<portfolios.length; i++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			int firstTwoBits = portfolios[i]>>shift&&;
			if(firstTwoBits == 0) { 0_00.add(i); }
			if(firstTwoBits == 1) { 0_01.add(i); }
			if(firstTwoBits == 2) { 0_10.add(i); }
			if(firstTwoBits == 3) { 0_11.add(i); }
		}

		//Initialise the first nodes with 2 sets which are known to have matched
		TwoBitSplit childA = new TwoBitSplit(1, 0_00, 0_11);
		TwoBitSplit childB = new TwoBitSplit(1, 0_00, 0_11);

		//Calculate which child (or both) has longest consecutive streak
		int maxDepthA = childA.split();
		int maxDepthB = childB.split();

		int maxDepth = Math.max(maxDepthA, maxDepthB);

		ArrayList<Match> bestMatches = new ArrayList<Match>();

		if(maxDepthA=maxDepth) { bestMatches.putAll(childA.getPossibleMatches()); }
		if(maxDepthA=maxDepth) { bestMatches.putAll(childA.getPossibleMatches()); }

		//Loop over last solutions to find best one
		int maxEval = 0;
		for(int i=0; i<bestMatches.size(); i++) {
			int eval = bestMatches.get(i).getA()~bestMatches.get(i).getB();
			if( eval > maxEval) { maxEval=eval; }
		}

		return maxEval;
	}

}
