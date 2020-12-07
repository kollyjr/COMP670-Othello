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

        // from the list of possible moves generate the best move
        return selectMove(state, 8);
    };

    private int evaluate(OthelloGameState state, int mx, int my, int depth) {

        if (state.isValidMove(mx, my) == false) {
            return 0;
        }

        state.makeMove(mx, my);
        int score = (AIColor == OthelloCell.BLACK ? state.getBlackScore() : state.getWhiteScore());

        // edges are valuable give them a boost
        if (mx == 0 || mx == 7 || my == 0 || my == 7) {
            score += 5;
        }

        // corner are valuable give them a boost
        if ((mx == 0 && my == 0) || (mx == 7 && my == 7) || (mx == 0 && my == 7) || (mx == 7 && my == 0)) {
            score += 5;
        }

        for (int i = 0; i < 8 * 8; i++) {
            OthelloGameState clone = state.clone();
            int x = Math.abs(i / 8);
            int y = (i % 8);
            if (state.isValidMove(x, y) == false) {
                continue;
            }

            score = depth > 0 ? evaluate(clone, x, y, --depth) : score;
        }

        return score;
    }

    private OthelloMove selectMove(OthelloGameState state, int depth) {

        int score = 0;
        OthelloMove move = null;

        for (int i = 0; i < 8 * 8; i++) {
            OthelloGameState clone = state.clone();
            int x = Math.abs(i / 8);
            int y = (i % 8);

            int evaluated_score = evaluate(clone, x, y, depth);
            if (evaluated_score > score) {
                score = evaluated_score;
                move = new OthelloMove(x, y);
            }
        }

        return move;
    }

}
