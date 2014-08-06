public class Solver {
	private ResizingArrayStack<Board> searchBoardStack =
						new ResizingArrayStack<Board>();
	private ResizingArrayStack<Board> twinSearchBoardStack =
						new ResizingArrayStack<Board>();
	private int moves;
	private int twinMoves;
	private boolean goal = false;
	private boolean twinGoal = false;

	private class BoardSolver {
	 	private BinarySearchST<Integer, Board> gameTree = 
		 				 		 new BinarySearchST<Integer, Board>();
		private MinPQ<Integer> neighborArbiter = new MinPQ<Integer>();
		private Board []prevBoard = new Board[2];
		private Board searchBoard;
		private int priority;
		private int minPriority;
		private bool goal;

		public BoardSolver(Board board) {
			prevBoard[0] = board;
			priority = board.hamming();
			gameTree.put(priority, board);
			neighborArbiter.insert(priority);
		}

		public boolean isGoal() {
			minPriority = neighborArbiter.delMin();
			searchBoard = gameTree.get(minPriority);
			goal = searchBoard.isGoal();
			prevBoard[1] = searchBoard;
			searchBoardStack.push(searchBoard);
		}
	}

    public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		Board twinBoard = initial.twin();
		moves = 0;
		twinMoves = 0;
		goal = false;
		twinGoal = false;
		int priority = initial.hamming() + moves;
		int twinPriority = twinBoard.hamming() + twinMoves;
		int minPriority;
		int neighborCount;
		int twinNeighborCount;
		Board searchBoard;
		Board twinSearchBoard;
		while (true) {
			//System.out.println("Processing Initial");
			minPriority = neighborArbiter.delMin();
			searchBoard = gameTree.get(minPriority);
			goal = searchBoard.isGoal();
			prevBoard[1] = searchBoard;
			searchBoardStack.push(searchBoard);
			//System.out.println(searchBoard.toString());
			if (goal == true) {
				break;
			}
			moves++;
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
					priority = searchBoard.hamming() + moves;
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
			if (neighborCount == 0) {
				break;
			}

			//System.out.println("Processing Twin");
			minPriority = twinNeighborArbiter.delMin();
			twinSearchBoard = twinGameTree.get(minPriority);
			//System.out.println(twinSearchBoard.toString());
			twinGoal = twinSearchBoard.isGoal();
			twinPrevBoard[1] = twinSearchBoard;
			twinSearchBoardStack.push(twinSearchBoard);
			if (twinGoal == true) {
				break;
			}
			//System.out.println("3");
			/* Initial Board processing */
			/* Twin Board processing */
			twinMoves++;
			twinNeighborArbiter = new MinPQ<Integer>();
			twinGameTree = new BinarySearchST<Integer, Board>();
			twinNeighborCount = 0;
			for (Board neighborBoard : twinSearchBoard.neighbors()) {
				if (twinPrevBoard[0].equals(neighborBoard) == true) {
					//System.out.println("Equals");
					//System.out.println(twinPrevBoard[0].toString());
					//System.out.println("Neighbor");
					//System.out.println(neighborBoard.toString());
					continue;
				}
				if (neighborBoard != null) {
					//System.out.println("4");
					twinNeighborCount++;
					twinPriority = twinSearchBoard.hamming() + twinMoves;
					twinNeighborArbiter.insert(twinPriority);
					twinGameTree.put(twinPriority, neighborBoard);
					//System.out.println(neighborBoard.toString());
					//System.out.println(neighborBoard.hamming());
				} else {
					//System.out.println("2");
					break;
				}
			}
			twinPrevBoard[0] = twinPrevBoard[1];
			if (twinNeighborCount == 0) {
				break;
			}
		}
	}

    public boolean isSolvable() {
		// is the initial board solvable?
		System.out.println("Goal " + goal + "TwinGoal " + twinGoal);
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
            return searchBoardStack;
        } else if (twinGoal == true) {
            return twinSearchBoardStack;
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
