public class AlphaBetaPruning {

    // Function to start Alpha-Beta Pruning
    public static int alphaBetaPruning(int depth, int nodeIndex, boolean isMaximizingPlayer,
                                       int[] values, int alpha, int beta, int maxDepth) {
        // Base Case: If we reach the maximum depth
        if (depth == maxDepth) {
            return values[nodeIndex];
        }

        if (isMaximizingPlayer) {
            int best = Integer.MIN_VALUE;

            // Recur for left and right child nodes
            for (int i = 0; i < 2; i++) {
                int value = alphaBetaPruning(depth + 1, nodeIndex * 2 + i, false, values, alpha, beta, maxDepth);
                best = Math.max(best, value);
                alpha = Math.max(alpha, best);

                // Alpha Beta Pruning
                if (beta <= alpha) {
                    break;
                }
            }
            return best;

        } else {
            int best = Integer.MAX_VALUE;

            // Recur for left and right child nodes
            for (int i = 0; i < 2; i++) {
                int value = alphaBetaPruning(depth + 1, nodeIndex * 2 + i, true, values, alpha, beta, maxDepth);
                best = Math.min(best, value);
                beta = Math.min(beta, best);

                // Alpha Beta Pruning
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        }
    }

    public static void main(String[] args) {
        // Leaf node values for the game tree
        int[] values = {3, 5, 6, 9, 1, 2, 0, -1};
        int maxDepth = (int)(Math.log(values.length) / Math.log(2));

        // Call Alpha-Beta Pruning
        int result = alphaBetaPruning(0, 0, true, values, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth);

        System.out.println("The optimal value is: " + result);
    }
}
