import java.util.Comparator;
import java.util.NoSuchElementException;
import java.lang.*;

public class Board
{
    private int moves;
    final private int size;
    private int refBlockRow;
    private int refBlockCol;
    
    //private BinarySearchST<Integer, Integer> initBST =
    //                                    new BinarySearchST<Integer, Integer>();
    private ResizingArrayStack<Board> neighborStack =
                new ResizingArrayStack<Board>();
    final private int [][]boardBlocks;

    /*private class BoardPriority implements Comparator<Board>
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
    }*/

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        size = blocks.length;
        boardBlocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                boardBlocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    refBlockRow = i;
                    refBlockCol = j;
                }
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
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                if ((i == size - 1)
                   && (j == size - 1)) {
                    break;
                }

                if (boardBlocks[i][j] != ((i * size) + (j + 1))) {
					//System.out.println(boardBlocks[i][j] + " " + ((i * size) + (j + 1)));
					hammingPriority++;
				}
			}
		}
        return hammingPriority;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattanPriority = 0;
        int refValueRow, refValueCol;
        int boardValue;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardValue = boardBlocks[i][j];
                //if (boardValue !=
                 //       (i * size) + j + 1) {
                if (boardValue != 0) {
                    //System.out.println(boardValue + " " + ((i * size) + j + 1));
                    refValueRow = (int) Math.ceil((double) boardValue / (double)size) - 1;
                    refValueRow = refValueRow < 0 ? 0 : refValueRow;
                    refValueCol = (boardValue - 1 - (refValueRow * size));
                    refValueCol = (refValueCol < 0) ? 0 : refValueCol;
                    //System.out.println(refValueRow + " " + refValueCol);
                    manhattanPriority += Math.abs(refValueRow - i)
                                        + Math.abs(refValueCol - j);
                    //System.out.println("P" + manhattanPriority);
                    //System.out.println();
                }
            }
        }
        return manhattanPriority;
    }

    public boolean isGoal() {
        // is this board the goal board?
        //if (hamming() == 0) {
        if (manhattan() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
		int [][]twinBlocks = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				twinBlocks[i][j] = boardBlocks[i][j];
			}
		}
		for (int i = 0; i < size; i++) {
			if (i != refBlockRow) {
				twinBlocks[i][0] = boardBlocks[i][1];
				twinBlocks[i][1] = boardBlocks[i][0];
				break;
			}
		}

        Board twinBoard = new Board(twinBlocks);
        return twinBoard;
    }

    public boolean equals(Object y) {
        // does this board equal y?
		Board comparedObject = (Board) y;
		boolean isEquals = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (comparedObject.boardBlocks[i][j]
					   	!= this.boardBlocks[i][j]) {
					isEquals = false;
				}
			}
		}
        return isEquals;
    }
    
    public Iterable<Board> neighbors() {
        // all neighboring boards
        int [][]neighborBlocks = new int[size][size];
        /* Create Neighbors and store it in the STACK */
        if (refBlockRow > 0) {
            //Up neighbor exists
            //System.out.println("Up neighbor exist");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == i) &&
                            (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i - 1][j];
                        neighborBlocks[i - 1][j] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow &&
                                 j == refBlockCol)) {
                        neighborBlocks[i][j] = boardBlocks[i][j];
                    }
                }
            }
            Board upBoard = new Board(neighborBlocks);
			//System.out.println(upBoard.toString());
            neighborStack.push(upBoard);
        }

        //if ((initBST.get(0) + size) < size * size) {
        if (refBlockRow < (size - 1)) {
            //bottom neighbor exist
            //System.out.println("Bottom neighbor exist");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == i) &&
                            (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i + 1][j];
                        neighborBlocks[i + 1][j] = boardBlocks[i][j];
                    } else if (!((i == refBlockRow
                              || i == (refBlockRow + 1))
                              && j == refBlockCol)) {
                        neighborBlocks[i][j] = boardBlocks[i][j];
                    }
                    //System.out.println(neighborBlocks[i][j]);
                }
            }
            Board bottomBoard = new Board(neighborBlocks);
			//System.out.println(bottomBoard.toString());
            neighborStack.push(bottomBoard);
        }

        //if (initBST.get(0) % size == 0) {
        if (refBlockCol == 0) {
            //Only right neighbor exist
            //System.out.println("Right neighbor exist");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == i)
                        && (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i][j + 1];
                        neighborBlocks[i][j + 1] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow
                              && (j == refBlockCol
                              || j == (refBlockCol + 1)))) {
                        neighborBlocks[i][j] = boardBlocks[i][j];
                    }
                }
            }
            Board rightBoard = new Board(neighborBlocks);
			//System.out.println(rightBoard.toString());
            neighborStack.push(rightBoard);
        //} else if (initBST.get(0) % (size - 1) == 0) {
        } else if (refBlockCol == (size - 1)) {
            //Only left neighbor exist
            //System.out.println("Left neighbor exist");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == i)
                        && (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i][j - 1];
                        neighborBlocks[i][j - 1] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow
                              && j == refBlockCol
                              || j == (refBlockCol - 1))) {
                        neighborBlocks[i][j] = boardBlocks[i][j];
                    }
                }
            }
            Board leftBoard = new Board(neighborBlocks);
			//System.out.println(leftBoard.toString());
            neighborStack.push(leftBoard);
        } else {
            //Both right and left neighbor exist
            //System.out.println("Both right and left neighbor exist");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == i)
                        && (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i][j + 1];
                        neighborBlocks[i][j + 1] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow
                              && j == refBlockCol
                              || j == (refBlockCol + 1))) {
                        neighborBlocks[i][j] = boardBlocks[i][j];
                    }
                }
            }
            Board rightBoard = new Board(neighborBlocks);
			//System.out.println(rightBoard.toString());
            neighborStack.push(rightBoard);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == i)
                        && (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i][j - 1];
                        neighborBlocks[i][j - 1] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow
                              && j == refBlockCol
                              || j == (refBlockCol - 1))) {
                        neighborBlocks[i][j] = boardBlocks[i][j];
                    }
                }
            }
            Board leftBoard = new Board(neighborBlocks);
			//System.out.println(leftBoard.toString());
            neighborStack.push(leftBoard);
        }
        return neighborStack;
    }

    public String toString() {
        // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //System.out.println(boardBlocks[i][j]);
                s.append(String.format("%2d ", boardBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.toString());
        System.out.println(initial.hamming());
        //System.out.println(initial.isGoal());
        for (Board neighborBoard : initial.neighbors()) {
            if (neighborBoard != null) {
                System.out.println(initial.equals(neighborBoard));
				System.out.println(neighborBoard.toString());
                //System.out.println(neighborBoard.hamming());
            } else {
                break;
            }
        }

        // solve the puzzle
        /*Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        } */
    }
}
