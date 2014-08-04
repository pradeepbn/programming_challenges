public class Solver {
	private ResizingArrayStack<Board> searchBoardStack =
						new ResizingArrayStack<Board>();
	private int moves;
    public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		boolean goal = false;
		int priority = initial.hamming() + moves;
		int minPriority;
		int neighborCount;
		Board searchBoard;
	 	BinarySearchST<Integer, Board> gameTree = new BinarySearchST<Integer, Board>();
		MinPQ<Integer> neighborArbiter = new MinPQ<Integer>();
		moves = 0;
		gameTree.put(priority, initial);
		neighborArbiter.insert(priority);
		System.out.println("1");
		while (true) {
			System.out.println("2");
			minPriority = neighborArbiter.delMin();
			searchBoard = gameTree.get(minPriority).Board(null);
			searchBoardStack.push(searchBoard);
			goal = searchBoard.isGoal();
			if (goal) {
				break;
			}
			System.out.println("3");
			moves++;
			neighborArbiter = new MinPQ<Integer>();
			gameTree = new BinarySearchST<Integer, Board>();
			neighborCount = 0;
			for (Board neighborBoard : searchBoard.neighbors()) {
				if (neighborBoard != null) {
					System.out.println("4");
					neighborCount++;
					priority = searchBoard.hamming() + moves;
					neighborArbiter.insert(priority);
					gameTree.put(priority, neighborBoard);
					//System.out.println(neighborBoard.toString());
					//System.out.println(neighborBoard.hamming());
				} else {
					System.out.println("2");
					break;
				}
			}
			if (neighborCount == 0) {
				break;
			}
		}
	}

    public boolean isSolvable() {
		// is the initial board solvable?
		return true;
	}
    public int moves() {
		// min number of moves to solve initial board; -1 if no solution
		return moves;
	}
    public Iterable<Board> solution() {
  		// sequence of boards in a shortest solution; null if no solution
		return searchBoardStack;
	}

	/************Helper APIs********************/

	private int [][] toBlocks(BinarySearchST<Integer, Integer> bST) {
		int size = bST.size() / 2;	
		int [][]blocks = new int[size][size];
		int refIndex;// = initBST.get(0);
		int i;// = refIndex / size;
		int j;// = refIndex - (i * size);

		for (int key : bST.keys()) {
			refIndex = bST.get(key);
			i = refIndex / size;
			j = refIndex - (i * size);
			blocks[i][j] = key;
		}
	}
	
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
