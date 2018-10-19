package answers;

import java.util.ArrayList;
import answers.Match;

public class TwoBitSplit {
	//A array only matches with B so we need to differentiate them
	private ArrayList<Byte> A_0 = new ArrayList<Byte>(); //0
	private ArrayList<Byte> A_1 = new ArrayList<Byte>(); //1

	//B array only matches with A so we need to differentiate them
	private ArrayList<Byte> B_0 = new ArrayList<Byte>(); //0
	private ArrayList<Byte> B_1= new ArrayList<Byte>(); //1
	
	private TwoBitSplit[] children;

	private boolean childA, childB, bottom = false;
	private byte depthA, depthB = 0;
	private byte maxDepth, depth;


	public TwoBitSplit(byte depth) {
		this.depth = depth;
		children = new TwoBitSplit[2];
	}
	public byte split(int[] portfolios, ArrayList<Byte> indices_A, ArrayList<Byte> indices_B) {
		//Initialise shift
		byte shift = (byte) (Question1.NUMBER_OF_BITS - (Question1.BITS_OBSERVED*(depth+1)));
		//Loop through the integers corresponding to indices_A 
		for(byte i=0; i<indices_A.size(); i++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			byte indexA = indices_A.get(i);
			boolean firstTwoBits = (portfolios[indexA]>>shift&1)==1;
			//Either a 1 or is not a 1 (a 0)
			if(firstTwoBits) { A_1.add(indexA); }
			else { A_0.add(indexA); }
		}
		//Loop through the integers corresponding to indices_B
		for(byte j=0; j<indices_B.size(); j++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			byte indexB = indices_B.get(j);
			boolean firstTwoBits = (portfolios[indexB]>>shift&1)==1;
			//Either a 1 or is not a 1 (a 0)
			if(firstTwoBits) { B_1.add(indexB); }
			else { B_0.add(indexB); }
		}

		//Since the inputs were passed such that A and B match
		//If we find matches BETWEEN A and B they are further matches i.e
		if(A_0.size()>0 && B_1.size()>0) { children[0] = new TwoBitSplit((byte) (this.depth+1)); childA = true; } 
		if(A_1.size()>0 && B_0.size()>0) { children[1] = new TwoBitSplit((byte) (this.depth+1)); childB = true; }

		//If no children we are at the end of our search
		bottom = (!childA && !childB) || (depth==15);

		if(bottom) {
			maxDepth = depth;
			return depth;
		} else {
			if(childA) { depthA = children[0].split(portfolios, A_0, B_1); }
			if(childB) { depthB = children[1].split(portfolios, A_1, B_0); }

			if(depthA>depthB) { return maxDepth = depthA; }
			else if (depthA == depthB) { return maxDepth = depthB = depthA; }
			else { return maxDepth=depthB; }
		} 
	}
	public int getDepth() {
		return maxDepth;
	}
	public ArrayList<Match> getPossibleMatches(int []portfolios, ArrayList<Byte> indexesA, ArrayList<Byte> indexesB) {
		ArrayList<Match> solutions = new ArrayList<Match>();

		if(bottom) {
			//return all of my matches as I am the lowest consecutive string of numbers
			//I also know there is no more matching ! so only 2 possibilities
			for(int i=0; i<A_0.size(); i++) {
				for(int j=0; j<B_0.size(); j++) {
					solutions.add(new Match(A_0.get(i), B_0.get(j)));
				}
			}
			for(int i=0; i<A_1.size(); i++) {
				for(int j=0; j<B_1.size(); j++) {
					solutions.add(new Match(A_1.get(i), B_1.get(j)));
				}
			}
		} else {
			//otherwise collate all other results
			if(childA && depthA==maxDepth) { solutions.addAll(children[0].getPossibleMatches(portfolios, A_0, B_1)); }
			if(childB && depthB==maxDepth) { solutions.addAll(children[1].getPossibleMatches(portfolios, A_1, B_0)); }
		}
		
		//If our consecutive run returns VERY many values then we want to simplify this process a little bit
		//So we skip out 1 bit and start looking for the next consecutive string of 1s when we compare
		if(solutions.size() > 20 && bottom) {
			solutions = getNextConsecutiveRun(portfolios, solutions, indexesA, indexesB);
		}
		
		return solutions;
	}
	public ArrayList<Match> getNextConsecutiveRun(int[] portfolios, ArrayList<Match> solutions, 
			ArrayList<Byte> indexesA, ArrayList<Byte> indexesB) {
		//This function will skip out one bit in order to find the next consecutive run of bits 
		//Applies to the case where very many of the portfolios give the same string of 1s
		boolean foundArray = false;
		//skipping one bit which we know has no more consecutive but this strand must be best...
		byte mSkipTo = (byte)(depth + 1);
		
		TwoBitSplit skipChild; 
		
		byte skipDepth = 0;
		ArrayList<Match> newSolutions = new ArrayList<Match>();
		
		while(!foundArray) {
			skipChild = new TwoBitSplit(mSkipTo);
		
			skipDepth = skipChild.split(portfolios, indexesA, indexesB);
			
			if(skipDepth!=(byte)mSkipTo) {
				//We managed to find a better match
				//otherwise collate all other results
				newSolutions.addAll(skipChild.getPossibleMatches(portfolios, indexesA, indexesB));
				if(newSolutions.size() == 0) {
					return solutions;
				} else {
					return newSolutions;
				}
			} else {
				mSkipTo ++;
				if(mSkipTo>15) {
					return solutions;
				}
			}
		}
		System.out.println("ERROR :  at line 129 of TwoBitSplit Should have already sent solutions");
		return null;
	}

}