package answers;

import java.util.ArrayList;
import java.math.*;

import answers.Match;
import answers.TwoBitSplit;

public class Question1 {
	//Constants that define both the problem and the way I solve it
	public static byte NUMBER_OF_BITS = 16;
	public static byte BITS_OBSERVED = 1;
	private static int SMALL_ARRAY = 16;
	
	public static int bestMergedPortfolio(int[] portfolios) {
		//Edge case: if there is no portfolios or 1 portfolio "combining" 2 portfolios has no meaning so return 0
		if(portfolios.length<=1) {
			return 0;
		}
		//Logically brute force makes sense if we have a small array as it is very few operations n<=16
		//Logically brute force loops through N^2 times, whereas my method at each level loops through
		//up to but not necessarily N items and this could happen up to 16 times.
		if(portfolios.length<=SMALL_ARRAY) {
			int maxEvalBruteForce = 0;
			for(int i=0; i<portfolios.length; i++) {
				for(int j=0; j<portfolios.length; j++) {
					if(i!=j) {
						int value = (portfolios[i]&65535)^(portfolios[j]&65535);
						if(value > maxEvalBruteForce) {
							maxEvalBruteForce = value;
						}
					}
				}
			}
			return maxEvalBruteForce;
		}
		//Initialise shift
		byte shift = (byte)(NUMBER_OF_BITS-BITS_OBSERVED);
		byte depth=0;
		
		//Splits array into 1 and 0 as we know that these will be a match since index < 100 we can store as a byte
		ArrayList<Byte> M_0 = new ArrayList<Byte>(); //0
		ArrayList<Byte> M_1 = new ArrayList<Byte>(); //1

		ArrayList<Match> bestMatches = new ArrayList<Match>();
		boolean started = false;
		
		//I use bytes in the for loop as it is specified that the array is never bigger then 100 so this is acceptable
		//I will also use this in the splitted arrays as they will therefore always be smaller then 100
		
		//HENCE THIS SOLUTION IS INVALID IF N>128
		
		boolean isPattern = true;
		int currentDifferent = 0;
		int[] distinctIntegers = new int[5];
		
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
					
					if(currentDifferent<5) {
						boolean isDistinct = true;
						for(int k=0; k<4; k++) {
							if(distinctIntegers[k]==portfolios[i]) {
								isDistinct = false;
							}
						}
						if(isDistinct) {
							distinctIntegers[currentDifferent] = portfolios[i];
							currentDifferent ++;
							if(currentDifferent>=5) { isPattern = false; }
						}
					}
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
		
		//If there was only 5 distinct items it makes sense just to loop through those items to save time
		if(isPattern) {
			//Only 1 item so 0 is best
			if(currentDifferent == 1) { return 0; }
			//Only 2 items so must be the solution of them
			if(currentDifferent == 2) { return (distinctIntegers[0]&65535)^(distinctIntegers[1]&65535); }
			
			int maxEvalBruteForce = 0;
			for(int i=0; i<currentDifferent; i++) {
				for(int j=0; j<currentDifferent; j++) {
					if(i!=j) {
						int value = (distinctIntegers[i]&65535)^(distinctIntegers[j]&65535);
						if(value > maxEvalBruteForce) {
							maxEvalBruteForce = value;
						}
					}
				}
			}
			return maxEvalBruteForce;
		}

		byte startingDepth = depth;
		
		//Initialise the first nodes with 2 sets which are known to have matched
		TwoBitSplit child = new TwoBitSplit((byte) (depth+1));

		//Calculate which child (or both) has longest consecutive streak
		depth = child.split(portfolios, M_0, M_1);
		
		if(depth==16) {
			//We reached the depth of the final bit and there was a match so we have the solution already
			//We have 111... 16-START times
			return (int) (Math.pow(2, 16-startingDepth) - 1);
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
