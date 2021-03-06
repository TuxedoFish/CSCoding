package answers;

import java.util.ArrayList;

public class Question4 {

	public static int selectionFailedTradedesks(String[][] rows, int numberMachines) {
		//Non sensical to act on empty data
		if(rows.length == 0)  { return 0; }
		//Obtains the length of a single floor
		int floorLength = rows[0].length;
		if(floorLength == 0)  { return 0; }
		
		//Variables to define the selection array for each floor
		int noPermutations = floorLength - numberMachines + 1;
		//Converts 2 to 11 and 3 to 111 etc...
		int numberMachinesBinary = (int) (Math.pow(2, numberMachines) - 1);
		
		ArrayList<Integer> options = new ArrayList<Integer>();
		//Now using that selection array it is possible to loop through all of the computers
		for(int i=0; i<rows.length; i++) {
			for(int j=0; j<noPermutations; j++) {
				int element = 0;
				boolean exists = true;
				for(int k=0; k<floorLength && exists; k++) {
					if( ((numberMachinesBinary<<j)>>(k)&1) == 1 ) {
						//This row[i][k] should be in the element
						if(rows[i][k].equals("X")) { exists = false; }
						else { element += Integer.valueOf(rows[i][k]); }
					}
				}
				//If we went through a permutation with no Xs then add it to possible options
				if(exists) { options.add(element); }
			}
		}
		
		if(options.size()==0) { return 0; }
		
		//Loops through all remaining options looking for smallest option
		int minSize = 0;
		for(int i=0; i<options.size(); i++) {
			if(minSize == 0 || options.get(i)<minSize) { minSize = options.get(i); }
		}
		return minSize;
	}

}
