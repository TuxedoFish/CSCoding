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
		//Edge case: if there is no portfolios or 1 portfolio "combining" 2 portfolios has no meaning so return 0
		if(portfolios.length<=1) {
			return 0;
		}
		//Initialise shift
		byte shift = (byte)(NUMBER_OF_BITS-BITS_OBSERVED);
		byte depth=0;
		
		//Splits array into 1 and 0 as we know that these will be a match since index < 100 we can store as a byte
		ArrayList<Byte> M_0 = new ArrayList<Byte>(); //0
		ArrayList<Byte> M_1 = new ArrayList<Byte>(); //1

		ArrayList<Match> bestMatches = new ArrayList<Match>();
		boolean started = false;
		
		while(!started) {
			for(byte i=0; i<portfolios.length; i++) {
				//In case the input has incorrectly contained signed ints
				//If done here no need to do it elsewhere as all signed ints
				//will be ignored in further steps
				if(portfolios[i]>=0) {
					//Take first 2 elements and place into the 4 arrays to pass into child node
					//Since we are evaluating ONE BIT we can store as a boolean saving space
					boolean firstTwoBits = (portfolios[i]>>shift&1)==1;
					if(firstTwoBits) { M_1.add(i); }
					else { M_0.add(i); }
				}
			} 
			if(M_0.size()==0 || M_1.size()==0) {
				//No matches found for the digit hence all must be 1 or all must 0 (unlikely) but a possibility
				if(depth<=15) {
					depth ++;
					shift = (byte) (NUMBER_OF_BITS - (BITS_OBSERVED*(depth+1)));
					M_0.clear(); M_1.clear();
				} else {
					//Went all the way down the chain and found 0 matches
					return 0;
				}
			} else {
				started = true;
			}
		}

		//Initialise the first nodes with 2 sets which are known to have matched
		TwoBitSplit child = new TwoBitSplit((byte) (depth+1));

		//Calculate which child (or both) has longest consecutive streak
		depth = child.split(portfolios, M_0, M_1);

		if(depth==15) {
			//We reached the depth of the final bit and there was a match so we have the solution already
			//We have 111... 16 times
			return (int) (Math.pow(2, 16) - 1);
		}
		
		bestMatches = child.getPossibleMatches(portfolios, M_0, M_1);
	
		//Loop over last solutions to find best one
		int maxEval = 0;
		//Here loop must be INTEGER as can have more values then 128!!!
		for(int i=0; i<bestMatches.size(); i++) {
			//ERROR HERE: had been comparing the INDEXES not the values at those indexes so was getting
			//Strange values when i debugged with a random data set, has been fixed now!
			
			//Potential edge case error: if they sendc 16 bit signed integers since ints in java are 32 bit
			//We cannot be sure that the bits 16-32 are "safe" hence by &65535 we only get the value of the 
			//the right most 16 bits hence our evaluated answer is never > 65535
			
			int eval = (portfolios[bestMatches.get(i).getA()]&65535)^(portfolios[bestMatches.get(i).getB()]&65535);
			if( eval > maxEval) { maxEval=eval; }
		}

		return maxEval;
	}

}
