
public class GreggAI implements OthelloAI {

	int depth = 2;
	Boolean finalState = false;
	Boolean notExhuasted = true;
	@Override
	public OthelloMove chooseMove(OthelloGameState state) {
		OthelloMove bestMove = null;
		int number = search(state, depth);
		return bestMove;
	}
	private int search(OthelloGameState state, int depth) {
		if (depth == 0 || finalState) 
		{
			
			
		}
		else 
		{
			if (state.isBlackTurn()) {
				while (notExhuasted) {
					
				}
			}
		}
		return 0;
	}

}