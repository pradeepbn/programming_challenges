import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private int moves;
    private boolean goal = false;
    private boolean twinGoal = false;
    private BoardSolver initialBoardSolver;
    private BoardSolver twinBoardSolver;

    private class BoardSolver {
		private int count;
		public int finalMovesCount;
        private final Comparator<TreeSet> PRIORITY_ORDER = new PriorityOrder();
        private class PriorityOrder implements Comparator<TreeSet> {
            public int compare(TreeSet p, TreeSet q) {
                if (p.manhattanPriority < q.manhattanPriority) {
                    return -1;
                } else if (p.manhattanPriority > q.manhattanPriority) {
                    return 1;
                } else {
					if (p.b.manhattan() < q.b.manhattan()) {
						return -1;
					} else if (p.b.manhattan() > q.b.manhattan()) {
						return 1;
					} else {
						return 0;
					}
                }
            }
        }
        //private BinarySearchST<Integer, Board> gameTree =
        //                           new BinarySearchST<Integer, Board>();
        private MinPQ<TreeSet> neighborArbiter = new MinPQ<TreeSet>(PRIORITY_ORDER);
        private SET<TreeSet> setPQ = new SET<TreeSet>();
        private TreeSet []prevBoard = new TreeSet[2];
        //private Board []prevBoard = new Board[2];
        private Board searchBoard;
        private boolean goal;
		private TreeSet minPrioritySet;

        private class TreeSet implements Comparable<TreeSet> {
            public Board b;
			//public int hammingPriority;
			public int manhattanPriority;
			//public int priority;
            public int moves;
            public TreeSet(Board board, int moves) {
                this.moves = moves;
                b = board;
				//this.hammingPriority = b.hamming() + moves;
				this.manhattanPriority = b.manhattan() + moves;
				//this.priority = this.manhattanPriority;
            }

            public int compareTo(TreeSet that) {
                //System.out.println("In compareTo method "
                //                + " " + this.manhattanPriority
                //                + " " + this.b.manhattan()
                //                + " " + that.manhattanPriority );
                if (this.b.equals(that.b)) {
                    return 0;
                }
                //if (this.b.manhattan() < that.b.manhattan()) {
                //    return -1;
                //} else if (this.b.manhattan() > that.b.manhattan()) {
                //    return 1;
                //} else {
                //    return -1;
                //}
                if (this.moves < that.moves) {
                    return -1;
                } else if (this.moves > that.moves) {
                    return 1;
                } else {
                    return 0;
                }
            }

            /*public boolean equals(Object y) {
                boolean isEquals = false;
                if ( !(y instanceof TreeSet) ) return false;
                TreeSet comparedObject = (TreeSet) y;

                if (this.moves == comparedObject.moves) {
                    isEquals = true;
                }

                return isEquals; 
            }*/
        }

        public BoardSolver(Board board) {
            //int priority;
            //[]prevBoard = new TreeSet[2];
		
			count = 1;
            prevBoard[0] = new TreeSet(board, moves);
            //prevBoard[0].moves = moves;
            //prevBoard[0] = board;
            //priority = prevBoard[0].b.manhattan() + moves;
            //gameTree.put(priority, board);
            //System.out.println("Priority:" + priority);
            //System.out.println(board.toString());
			//minPrioritySet = prevBoard[0];
            setPQ.add(prevBoard[0]);
            neighborArbiter.insert(prevBoard[0]);
            //neighborArbiter.insert(priority);
        }

        public boolean isGoal() {
            //int priority;
			Board minPriorityBoard;
            //int minPriority;

            //while (true) {
            //    minPrioritySet = neighborArbiter.delMin();
            //    if (!setPQ.contains(minPrioritySet)) {
            //        setPQ.add(minPrioritySet);
            //        break;
            //    }
            //}
			//minPrioritySet = setPQ.min();
            //setPQ.delete(minPrioritySet);
            minPrioritySet = neighborArbiter.delMin();
            //minPrioritySet = neighborArbiter.min();
			//neighborArbiter.insert(minPrioritySet);
            moves = minPrioritySet.moves;

			//setPQ.delete(minPrioritySet);
            //if (setPQ.contains(minPrioritySet) && prevBoard[1] != null) {
            //    setPQ.delete(prevBoard[1]);
            //    setPQ.delete(minPrioritySet);
            //}
            setPQ.add(minPrioritySet);

            searchBoard = minPrioritySet.b;
            goal = searchBoard.isGoal();
            if (goal == true) {
                finalMovesCount = moves;
            }
            //searchBoardStack.push(searchBoard);
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
					//setPQ.add(new TreeSet(neighborBoard, moves));
                    //priority = neighborBoard.manhattan() + moves;
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
            //MinPQ<Board> stackElements = new MinPQ<Board>(PRIORITY_ORDER);
         	ResizingArrayQueue<Board> searchBoardQueue =
								new ResizingArrayQueue<Board>();
            for (TreeSet setElement : setPQ) {
			//Iterator<TreeSet> Iter = neighborArbiter.iterator();
			//TreeSet setElement;
			//while (Iter.hasNext()) {
			//	setElement = Iter.next();
                //System.out.println(setElement.manhattanPriority);
                //System.out.println(setElement.moves);
                //System.out.println(setElement.b.toString());
				searchBoardQueue.enqueue(setElement.b);
                //searchBoardStack.push(setElement.b);
            }
            return searchBoardQueue;
			//return neighborArbiter.iterator();
        }

		public void printNeigbhorArbiter() {
			Iterator iter = neighborArbiter.iterator();
			TreeSet set;
			System.out.println("Step " + count);
			while(iter.hasNext()) {
				set = (TreeSet)iter.next();
				System.out.println("Priority " + set.manhattanPriority);
				System.out.println("Moves " + set.moves);
				System.out.println("Manhattan " + set.b.manhattan());
				System.out.println();

			}
			System.out.println("-------------");
			count++;
		}
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        moves = 0;
		Board twinBoard = initial.twin();
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
			//initialBoardSolver.printNeigbhorArbiter();
            twinGoal = twinBoardSolver.isGoal();
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
            return initialBoardSolver.finalMovesCount;
        } else if (twinGoal == true) {
			return -1;
            //return twinBoardSolver.finalMovesCount;
        }
            return 0;
    }
    public Iterable<Board> solution() {
          // sequence of boards in a shortest solution; null if no solution
        if (goal == true) {
            return initialBoardSolver.solution();
        } else if (twinGoal == true) {
			return null;
            //return twinBoardSolver.solution();
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
            solver.solution();
            //for (Board board : solver.solution())
            //    StdOut.println(board.toString());
        }
    }
}
