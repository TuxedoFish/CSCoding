package answers;

import java.util.ArrayList;
import java.math.*;

import answers.Match;

public class TwoBitSplit {
	private int depth;
	
	//Only used if in lowest depth situation
	private ArrayList<Integer> indicesA = new ArrayList<Integer>(); 
	private ArrayList<Integer> indicesB = new ArrayList<Integer>(); 

	//A array only matches with B so we need to differentiate them
	private ArrayList<Integer> A_00 = new ArrayList<Integer>(); //0
	private ArrayList<Integer> A_01 = new ArrayList<Integer>(); //1
	private ArrayList<Integer> A_10 = new ArrayList<Integer>(); //2
	private ArrayList<Integer> A_11 = new ArrayList<Integer>(); //3

	//B array only matches with A so we need to differentiate them
	private ArrayList<Integer> B_00 = new ArrayList<Integer>(); //0
	private ArrayList<Integer> B_01 = new ArrayList<Integer>(); //1
	private ArrayList<Integer> B_10 = new ArrayList<Integer>(); //2
	private ArrayList<Integer> B_11 = new ArrayList<Integer>(); //3

	private TwoBitSplit[] children;

	private boolean childA, childB, childC, childD, bottom = false;
	private int depthA, depthB, depthC, depthD = 0;
	private int maxDepth;

	public TwoBitSplit(int depth) {
		this.depth = depth;
		children = new TwoBitSplit[4];
	}
	public int split(int[] portfolios, ArrayList<Integer> indices_A, ArrayList<Integer> indices_B) {
		//Initialise shift
		int shift = Question1.NUMBER_OF_BITS - (Question1.BITS_OBSERVED*(depth+1));
		//Loop through the integers corresponding to indices_A 
		for(int i=0; i<indices_A.size(); i++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			int indexA = indices_A.get(i);
			int firstTwoBits = portfolios[indexA]>>shift&Question1.INT_MASK;
		
			if(firstTwoBits == 0) { A_00.add(indexA); }
			if(firstTwoBits == 1) { A_01.add(indexA); }
			if(firstTwoBits == 2) { A_10.add(indexA); }
			if(firstTwoBits == 3) { A_11.add(indexA); }
		}
		//Loop through the integers corresponding to indices_B
		for(int i=0; i<indices_B.size(); i++) {
			//Take first 2 elements and place into the 4 arrays to pass into child node
			int indexB = indices_B.get(i);
			int firstTwoBits = portfolios[indexB]>>shift&Question1.INT_MASK;
		
			if(firstTwoBits == 0) { B_00.add(indexB); }
			if(firstTwoBits == 1) { B_01.add(indexB); }
			if(firstTwoBits == 2) { B_10.add(indexB); }
			if(firstTwoBits == 3) { B_11.add(indexB); }
		}

		//Since the inputs were passed such that A and B match
		//If we find matches BETWEEN A and B they are further matches i.e
		if(A_00.size()>0 && B_11.size()>0) { children[0] = new TwoBitSplit(this.depth+1); childA = true; } 
		if(A_01.size()>0 && B_10.size()>0) { children[1] = new TwoBitSplit(this.depth+1); childB = true; }
		if(A_11.size()>0 && B_00.size()>0) { children[2] = new TwoBitSplit(this.depth+1); childC = true; }
		if(A_10.size()>0 && B_01.size()>0) { children[3] = new TwoBitSplit(this.depth+1); childD = true; }

		//If no children we are at the end of our search
		bottom = !childA && !childB && !childC && !childD;

		if(bottom) {
			maxDepth = depth;
			this.indicesA = indicesA;
			this.indicesB = indicesB;
			return depth;
		} else {
			if(childA) { depthA = children[0].split(portfolios, A_00, B_11); }
			if(childB) { depthB = children[1].split(portfolios, A_01, B_10); }
			if(childC) { depthC = children[2].split(portfolios, A_11, B_00); }
			if(childD) { depthD = children[3].split(portfolios, A_10, B_01); }

			maxDepth = Math.max(Math.max(depthA, depthB), Math.max(depthC, depthD));
			return maxDepth;
		}
	}
	public int getDepth() {
		return maxDepth;
	}
	public ArrayList<Match> getPossibleMatches() {
		ArrayList<Match> solutions = new ArrayList<Match>();

		if(bottom) {
			//return all of my matches as I am the lowest consecutive string of numbers
			for(int i=0; i<indicesA.size(); i++) {
				for(int j=0; j<indicesB.size(); j++) {
					solutions.add(new Match(indicesA.get(i), indicesB.get(j)));
				}
			}
		} else {
			//otherwise collate all other results
			if(childA && children[0].getDepth()==maxDepth) { solutions.addAll(children[0].getPossibleMatches()); }
			if(childB && children[1].getDepth()==maxDepth) { solutions.addAll(children[1].getPossibleMatches()); }
			if(childC && children[2].getDepth()==maxDepth) { solutions.addAll(children[2].getPossibleMatches()); }
			if(childD && children[3].getDepth()==maxDepth) { solutions.addAll(children[3].getPossibleMatches()); }
		}
		return solutions;
	}

}