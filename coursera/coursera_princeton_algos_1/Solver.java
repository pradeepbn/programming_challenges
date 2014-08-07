public class Solver {
    private int moves;
    private int twinMoves;
    private boolean goal = false;
    private boolean twinGoal = false;
    private BoardSolver initialBoardSolver;
    private BoardSolver twinBoardSolver;

    private class BoardSolver {
        private BinarySearchST<Integer, Board> gameTree =
                                   new BinarySearchST<Integer, Board>();
        private MinPQ<Integer> neighborArbiter = new MinPQ<Integer>();
        private ResizingArrayStack<Board> searchBoardStack =
                            new ResizingArrayStack<Board>();
        private Board []prevBoard = new Board[2];
        private Board searchBoard;
        private int minPriority;
        private boolean goal;

        public BoardSolver(Board board) {
            int priority;
            prevBoard[0] = board;
            priority = board.manhattan();
            gameTree.put(priority, board);
            neighborArbiter.insert(priority);
        }

        public boolean isGoal() {
            int priority;
            int neighborCount;
            minPriority = neighborArbiter.delMin();
            searchBoard = gameTree.get(minPriority);
            goal = searchBoard.isGoal();
            prevBoard[1] = searchBoard;
            searchBoardStack.push(searchBoard);

            neighborArbiter = null;
            gameTree = null;
            neighborArbiter = new MinPQ<Integer>();
            gameTree = new BinarySearchST<Integer, Board>();
            neighborCount = 0;
            for (Board neighborBoard : searchBoard.neighbors()) {
                if (prevBoard[0].equals(neighborBoard) == true) {
                    //System.out.println("Equals");
                    continue;
                }
                if (neighborBoard != null) {
                    //System.out.println("4");
                    neighborCount++;
                    priority = neighborBoard.manhattan() + moves;
                    neighborArbiter.insert(priority);
                    gameTree.put(priority, neighborBoard);
                    //System.out.println(neighborBoard.toString());
                    //System.out.println(neighborBoard.hamming());
                } else {
                    //System.out.println("2");
                    break;
                }
            }
            prevBoard[0] = prevBoard[1];
            /*if (neighborCount == 0) {
                break;
            }*/
            return goal;
        }

        public Iterable<Board> solution() {
            return searchBoardStack;
        }
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        Board twinBoard = initial.twin();
        moves = 0;
        twinMoves = 0;
        goal = false;
        twinGoal = false;
        initialBoardSolver = new BoardSolver(initial);
        twinBoardSolver = new BoardSolver(twinBoard);
        //System.out.println(initial.toString());
        //System.out.println(twinBoard.toString());
        while (true) {
            goal = initialBoardSolver.isGoal();
            if (goal == true) {
                break;
            }
            moves++;
            twinGoal = twinBoardSolver.isGoal();
            if (twinGoal == true) {
                break;
            }
            twinMoves++;
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        //System.out.println("Goal " + goal + "TwinGoal " + twinGoal);
        if (goal == true) {
            return true;
        } else {
            return false;
        }
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (goal == true) {
            return moves;
        } else if (twinGoal == true) {
            return twinMoves;
        }
            return 0;
    }
    public Iterable<Board> solution() {
          // sequence of boards in a shortest solution; null if no solution
        if (goal == true) {
            return initialBoardSolver.solution();
        } else if (twinGoal == true) {
            return twinBoardSolver.solution();
        }
        return null;
    }

    /************Helper APIs********************/
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board.toString());
        }
    }
}
