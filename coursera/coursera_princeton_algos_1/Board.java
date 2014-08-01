import java.util.Comparator;

public class Board
{
	private int priority;
	private int moves;
	public int size;
	
	public BoardPriority PRIORITY_ORDER = new BoardPriority();
	public int []boardBlocks;

	private class BoardPriority implements Comparator<Board>
	{
		public int compare(Board b, Board c) {
			if ((b.hamming() + b.moves) < (c.hamming() + c.moves)) {
				return 1;
			} else if ((b.hamming() + b.moves) > (c.hamming() + c.moves)) {
				return -1;
			} else {
				return 0;
			}
		}
	}


    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
		size = blocks.length;// / 2;
		boardBlocks = new int[size];

		for (int i = 0; i < size; i++) {
			for (int j = 0 ; j < size; j++) {
				boardBlocks[(i * size) + j] = blocks[i][j];
			}
		}
    }
                                           
    public int dimension() {
        // board dimension N
		return size;
    }

    public int hamming() {
        // number of blocks out of place
		int hammingPriority = 0;
		for (int k = 0; k < size * size; k++) {
			if (boardBlocks[k] != (k + 1)) {
				hammingPriority++;
			}
		}
		return hammingPriority;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
		return 0;
    }
    public boolean isGoal() {
        // is this board the goal board?
		return true;
    }
    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
		return null;
    }
    public boolean equals(Object y) {
        // does this board equal y?
		return true;
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
		return null;
    }
    public String toString() {
        // string representation of the board (in the output format specified below)
		return null;
    }
}
