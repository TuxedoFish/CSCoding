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
			if(firstTwoBits) { A_0.add(indexA); }
			else { A_1.add(indexA); }
		}
		//Loop through the integers corresponding to indices_B
		for(byte j=0; j<indices_B.size(); j++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			byte indexB = indices_B.get(j);
			boolean firstTwoBits = (portfolios[indexB]>>shift&1)==1;
			//Either a 1 or is not a 1 (a 0)
			if(firstTwoBits) { B_0.add(indexB); }
			else { B_1.add(indexB); }
		}

		//Since the inputs were passed such that A and B match
		//If we find matches BETWEEN A and B they are further matches i.e
		if(A_0.size()>0 && B_1.size()>0) { children[0] = new TwoBitSplit((byte) (this.depth+1)); childA = true; } 
		if(A_1.size()>0 && B_0.size()>0) { children[1] = new TwoBitSplit((byte) (this.depth+1)); childB = true; }

		//If no children we are at the end of our search
		bottom = !childA && !childB;

		if(bottom) {
			maxDepth = depth;
			return depth;
		} else {
			if(childA) { depthA = children[0].split(portfolios, A_0, B_1); }
			if(childB) { depthB = children[1].split(portfolios, A_1, B_0); }

			if(depthA>depthB) { return maxDepth = depthA; }
			else if (depthA == depthB) { return maxDepth = depthB = depthA; }
			else { return depthB; }
		} 
	}
	public int getDepth() {
		return maxDepth;
	}
	public ArrayList<Match> getPossibleMatches() {
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
			if(childA && depthA==maxDepth) { solutions.addAll(children[0].getPossibleMatches()); }
			if(childB && depthB==maxDepth) { solutions.addAll(children[1].getPossibleMatches()); }
		}
		
		return solutions;
	}

}