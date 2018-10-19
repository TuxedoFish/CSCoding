package answers;

import java.util.ArrayList;
import java.math.*;

import answers.Match;
import answers.TwoBitSplit;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	public static byte NUMBER_OF_BITS = 16;
	public static byte BITS_OBSERVED = 1;
	
	public static int bestMergedPortfolio(int[] portfolios) {
		//Initialise shift
		byte shift = (byte)(NUMBER_OF_BITS-BITS_OBSERVED);
		byte depth=0;
		
		//Splits array into 1 and 0 as we know that these will be a match since index < 100 we can store as a byte
		ArrayList<Byte> M_0 = new ArrayList<Byte>(); //0
		ArrayList<Byte> M_1 = new ArrayList<Byte>(); //1

		//Only starts splitting when it finds some matches with a comparison value of 1 at the left most digit
		boolean started = false;
		//Loop through the whole list of ints
		while(!started) {
			for(byte i=0; i<portfolios.length; i++) {
				//Take first 2 elements and place into the 4 arrays to pass into child node
				//Since we are evaluating ONE BIT we can store as a boolean saving space
				boolean firstTwoBits = (portfolios[i]>>shift&1)==1;
				if(firstTwoBits) { M_0.add(i); }
				else { M_1.add(i); }
			} 
			if(M_0.size()==0 || M_1.size()==0) {
				//No matches found for the digit hence all must be 1 or all must 0 (unlikely) but a possibility
				depth ++;
				shift = (byte) (NUMBER_OF_BITS - (BITS_OBSERVED*(depth+1)));
				M_0.clear(); M_1.clear();
			} else {
				started = true;
			}
		}

		//Initialise the first nodes with 2 sets which are known to have matched
		TwoBitSplit child = new TwoBitSplit((byte) (depth+1));

		//Calculate which child (or both) has longest consecutive streak
		child.split(portfolios, M_0, M_1);

		ArrayList<Match> bestMatches = child.getPossibleMatches();

		//Loop over last solutions to find best one
		int maxEval = 0;
		for(byte i=0; i<bestMatches.size(); i++) {
			int eval = portfolios[bestMatches.get(i).getA()]^portfolios[bestMatches.get(i).getB()];
			if( eval > maxEval) { maxEval=eval; }
		}

		return maxEval;
	}

}
