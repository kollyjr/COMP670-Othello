import java.util.ArrayList;

public class GreggAI implements OthelloAI {

	int depth = 5;
	Boolean finalState = false; //need to figure out
    Boolean isBlack = null;
	
	OthelloGameState originalState;
	int originalStatesSize = 0;
	
	
	@Override
	public OthelloMove chooseMove(OthelloGameState state) {
		originalState = state.clone();
		isBlack = PlayerColor(state);
		ArrayList<OthelloGameState> originalStates = GeneratePossibleMoveStates(state);
		originalStatesSize = originalStates.size();
		int[][] originalStatesMoves = GenerateMoves(originalState);
		int[] stateValues = new int[originalStates.size()];
		for(int i = 0; i < originalStates.size(); i++) 
		{
			OthelloGameState fetchedState = originalStates.get(i);
			stateValues[i] = search(fetchedState, depth - 1);
		}
		OthelloMove bestMove = BestMove(stateValues, originalStatesMoves);
		return bestMove;
	}
	


	private int LargestNumPos(int[] stateValues) {
		int largest = -1;
		int pos = 0;
		for(int i = 0; i < stateValues.length; i++) 
		{
			if(largest == -1) 
			{
				largest = stateValues[i];
			}
			else 
			{
				if(largest < stateValues[i]) 
				{
					largest = stateValues[i];
					pos = i;
				}
			}
		}
		return pos;
	}

	private int[][] GenerateMoves(OthelloGameState state) {
		int position = 0;
		int[][] originalStatesMoves = new int[originalStatesSize][2];
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				//copying list of valid moves in the beginning
				OthelloGameState stateCopy = state.clone(); 
				if(stateCopy.isValidMove(i, j)) 
				{
					originalStatesMoves[position][0] = i;
					originalStatesMoves[position][1] = j;
					position++;
				}
			}
		}
		return originalStatesMoves;
	}

	private ArrayList<OthelloGameState> GeneratePossibleMoveStates(OthelloGameState state) {
		ArrayList<OthelloGameState> states = new ArrayList<OthelloGameState>();
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				//need to reset as the turn move forward
				//will only focus on current player 
				OthelloGameState stateCopy = state.clone(); 
				if(stateCopy.isValidMove(i, j)) 
				{
					stateCopy.makeMove(i, j);
					states.add(stateCopy);
				}
			}
		}
		return states;
	}

	private int search(OthelloGameState state, int depth) {
		ArrayList<OthelloGameState> states;
		int[] stateValues = null;
		int eval = 0;
		
		Boolean blackTurn = state.isBlackTurn();
		if (depth == 0 || finalState) 
		{
			int scoreDiff = state.getBlackScore() - state.getWhiteScore();
			
			if (scoreDiff == 0) 
			{
				eval = 0;
			}
			else if (scoreDiff > 0) 
			{
				if (state.isBlackTurn()) 
				{
					eval = 1;
				}
				else 
				{
					eval = -1;
				}
			}
			else if (scoreDiff < 0) 
			{
				if (state.isBlackTurn()) 
				{
					eval = -1;
				}
				else 
				{
					eval = 1;
				}
			}
		}
		else 
		{
			if ((blackTurn && isBlack) || (!blackTurn && !isBlack)) 
			{
				states = GeneratePossibleMoveStates(state); 
				stateValues = new int[states.size()];
				for(int i = 0; i < states.size(); i++) 
				{
					OthelloGameState fetchedState = states.get(i);
					stateValues[i] = search(fetchedState, depth - 1);
				}
				eval = LargestNum(stateValues);
			} 
			else 
			{
				states = GeneratePossibleMoveStates(state); 
				stateValues = new int[states.size()];
				for(int i = 0; i < states.size(); i++) 
				{
					OthelloGameState fetchedState = states.get(i);
					stateValues[i] = search(fetchedState, depth - 1);
				}
				eval = SmallestNum(stateValues);
			}
				
		}
		return eval;
	}



	private int LargestNum(int[] stateValues) {
		int largest = 2;
		for(int i = 0; i < stateValues.length; i++) 
		{
			if(largest == 100) 
			{
				largest = stateValues[i];
			}
			else 
			{
				if(largest < stateValues[i]) 
				{
					largest = stateValues[i];
				}
			}
		}
		return largest;
	}
	
	private int SmallestNum(int[] stateValues) {
		int smallest = 2;
		for(int i = 0; i < stateValues.length; i++) 
		{
			if(smallest == 2) 
			{
				smallest = stateValues[i];
			}
			else 
			{
				if(smallest < stateValues[i]) 
				{
					smallest = stateValues[i];
				}
			}
		}
		return smallest;
	}
	
	private OthelloMove BestMove(int[] stateValues, int[][] originalStatesMoves) {
		int pos = LargestNumPos(stateValues);
		OthelloMove move = new OthelloMove(originalStatesMoves[pos][0], originalStatesMoves[pos][1]);
		return move;
	}

	private Boolean PlayerColor(OthelloGameState state) {
		if (state.isBlackTurn()) {
			return true;
		}
		else {
			return false;
		}
		
	}

}