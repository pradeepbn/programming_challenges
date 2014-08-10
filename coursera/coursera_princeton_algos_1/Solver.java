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
        private SET<TreeSet> setPQ = new SET<TreeSet>();
        private ResizingArrayStack<Board> searchBoardStack =
                            new ResizingArrayStack<Board>();
        private TreeSet []prevBoard = new TreeSet[2];
        private Board searchBoard;
        private boolean goal;
		private TreeSet minPrioritySet;

        private class TreeSet Implements comparable<TreeSet> {
            public Board b;
			private int hammingPriority;
			private int manhattanPriority;
			private int priority;
            public TreeSet(Board board) {
                b = board;
				this.hammingPriority = b.hamming();
				this.manhattanPriority = b.manhattan();
				this.priority = b.manhattan();
            }

            public int compareTo(TreeSet that) {
                if (this.manhattanPriority < that.manhattanPriority) {
                    return -1;
                } else if (this.manhattanPriority < that.manhattanPriority) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        public BoardSolver(Board board) {
            int priority;
		
            priority = prevBoard[0].b.priority + moves;
            gameTree.put(priority, board);
            prevBoard[0] = new TreeSet(board);
			minPrioritySet = prevBoard[0];
            setPQ.add(prevBoard[0]);
            //neighborArbiter.insert(priority);
        }

        public boolean isGoal() {
            int priority;
			Board minPriorityBoard;

           // minPriority = neighborArbiter.delMin();
			minPriority = gameTree.min();
			minPriorityBoard = gameTree.get(minPriority);
            setPQ.add(new TreeSet(minPriorityBoard));

			minPrioritySet = setPQ.min();
			searchBoard = minPrioritySet.b;
            goal = searchBoard.isGoal();
            prevBoard[1] = minPrioritySet;

            gameTree = null;
            gameTree = new BinarySearchST<Integer, Board>();
            for (Board neighborBoard : searchBoard.neighbors()) {
                if (prevBoard[0].b.equals(neighborBoard) == true) {
                    //System.out.println("Equals");
                    continue;
                }
                if (neighborBoard != null) {
                    //System.out.println("4");
                    priority = neighborBoard.manhattan() + moves;
                    //neighborArbiter.insert(priority);
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
