import java.util.HashMap;

public class HenryAI implements OthelloAI {

    private int depth = 0;
    private OthelloCell AIColor = null;
    private HashMap<Integer, Integer> possibleMoves = new HashMap<Integer, Integer>();

    @Override
    public OthelloMove chooseMove(OthelloGameState state) {
        possibleMoves.clear();

        // if we don't know what color we are, verify our color.
        if (AIColor == null) {
            AIColor = state.isBlackTurn() ? OthelloCell.BLACK : OthelloCell.WHITE;
        }

        // generate a list of used cells, saves computation time
        for (int i = 0; i < 8 * 8; i++) {
            int x = Math.abs(i / 8);
            int y = (i % 8);

            if (state.isValidMove(x, y) == false) {
                continue;
            }

            possibleMoves.put(i, i);
        }

        // from the list of possible moves generate the best move
        return selectMove(state);
    };

    private OthelloMove selectMove(OthelloGameState state) {

        int score = 0;
        OthelloMove move = null;

        for (Integer i : possibleMoves.keySet()) {
            int x = Math.abs(i / 8);
            int y = (i % 8);

            OthelloGameState clone = state.clone();
            int current_score = AIColor == OthelloCell.BLACK ? clone.getBlackScore() : clone.getWhiteScore();
            clone.makeMove(x, y);
            int updated_score = AIColor == OthelloCell.BLACK ? clone.getBlackScore() : clone.getWhiteScore();
            if (updated_score - current_score >= score) {
                score = updated_score - current_score;
                move = new OthelloMove(x, y);
            }

        }

        return move;
    }

}
