import java.util.Comparator;

public class Solver {
    private int finalMovesCount;
    private int moves;
    private int twinMoves;
    private boolean goal = false;
    private boolean twinGoal = false;
    private BoardSolver initialBoardSolver;
    private BoardSolver twinBoardSolver;

    private class BoardSolver {
        private final Comparator<TreeSet> PRIORITY_ORDER = new PriorityOrder();
        private class PriorityOrder implements Comparator<TreeSet> {
            public int compare(TreeSet p, TreeSet q) {
                if (p.manhattanPriority < q.manhattanPriority) {
                    return -1;
                } else if (p.manhattanPriority > q.manhattanPriority) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        private BinarySearchST<Integer, Board> gameTree =
                                   new BinarySearchST<Integer, Board>();
        private MinPQ<TreeSet> neighborArbiter = new MinPQ<TreeSet>(PRIORITY_ORDER);
        private SET<TreeSet> setPQ = new SET<TreeSet>();
        private ResizingArrayStack<Board> searchBoardStack =
                            new ResizingArrayStack<Board>();
        private TreeSet []prevBoard = new TreeSet[2];
        //private Board []prevBoard = new Board[2];
        private Board searchBoard;
        private boolean goal;
		private TreeSet minPrioritySet;

        private class TreeSet implements Comparable<TreeSet> {
            public Board b;
			private int hammingPriority;
			public int manhattanPriority;
			private int priority;
            public int moves;
            public TreeSet(Board board, int moves) {
                this.moves = moves;
                b = board;
				this.hammingPriority = b.hamming() + moves;
				this.manhattanPriority = b.manhattan() + moves;
				this.priority = this.manhattanPriority;
            }

            public int compareTo(TreeSet that) {
                //System.out.println("In compareTo method "
                //                + " " + this.manhattanPriority
                //                + " " + this.b.manhattan()
                //                + " " + that.manhattanPriority );
                //System.out.println(this.b.toString());
                //System.out.println(that.b.toString());
                //System.out.println("--------------------");
                if (this.b.equals(that.b) == true) {
                    //System.out.println("0");
                    return 0;
                }
                //if (this.manhattanPriority < that.manhattanPriority) {
                if (this.b.manhattan() < that.b.manhattan()) {
                    //System.out.println("<");
                    return -1;
                //} else if (this.manhattanPriority > that.manhattanPriority) {
                } else if (this.b.manhattan() > that.b.manhattan()) {
                    //System.out.println("<");
                    return 1;
                } else {
                    //if (this.b.manhattan() < that.b.manhattan()) {
                    //    return -1;
                    //} else if (this.b.manhattan() > that.b.manhattan()) {
                    //    return 1;
                    //} else {
                    //    //System.out.println("0");
                    //    return 0;
                    //}
                    return -1;
                }
            }

            public boolean equals(Object y) {
                if ( !(y instanceof TreeSet) ) return false;
                TreeSet comparedObject = (TreeSet) y;
                return this.b.equals(comparedObject.b);
            }
        }

        public BoardSolver(Board board) {
            int priority;
            //[]prevBoard = new TreeSet[2];
		
            prevBoard[0] = new TreeSet(board, moves);
            //prevBoard[0].moves = moves;
            //prevBoard[0] = board;
            priority = prevBoard[0].b.manhattan() + moves;
            gameTree.put(priority, board);
            //System.out.println("Priority:" + priority);
            //System.out.println(board.toString());
			//minPrioritySet = prevBoard[0];
            //setPQ.add(prevBoard[0]);
            neighborArbiter.insert(prevBoard[0]);
            //neighborArbiter.insert(priority);
        }

        public boolean isGoal() {
            int priority;
			Board minPriorityBoard;
            int minPriority;

            //while (true) {
            //    minPrioritySet = neighborArbiter.delMin();
            //    if (!setPQ.contains(minPrioritySet)) {
            //        setPQ.add(minPrioritySet);
            //        break;
            //    }
            //}
            minPrioritySet = neighborArbiter.delMin();

            searchBoard = minPrioritySet.b;
            moves = minPrioritySet.moves;
            goal = searchBoard.isGoal();
            if (goal == true) {
                finalMovesCount = moves;
            }
            searchBoardStack.push(searchBoard);
            //System.out.println("Seach Board Priority "
            //                    + minPrioritySet.manhattanPriority
            //                    + " Moves " + moves);
            //System.out.println(searchBoard.toString());
            //System.out.println("Neighbors");
            prevBoard[1] = minPrioritySet;
            moves++;
            for (Board neighborBoard : searchBoard.neighbors()) {
                if (prevBoard[0].b.equals(neighborBoard) == true) {
                    continue;
                }
                if (neighborBoard != null) {
                    neighborArbiter.insert(new TreeSet(neighborBoard, moves));
                    priority = neighborBoard.manhattan() + moves;
                    //System.out.println("Priority:" + neighborBoard.manhattan()
                    //                    + " Moves:" + moves);
                    //System.out.println(neighborBoard.toString());
                } else {
                    break;
                }
            }
            prevBoard[0] = prevBoard[1];
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
        //twinBoardSolver = new BoardSolver(twinBoard);
        //System.out.println(initial.toString());
        //System.out.println(twinBoard.toString());
        while (true) {
            goal = initialBoardSolver.isGoal();
            if (goal == true) {
                break;
            }
            //twinGoal = twinBoardSolver.isGoal();
            if (twinGoal == true) {
                break;
            }
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
            return finalMovesCount;
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
