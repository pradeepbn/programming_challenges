import java.util.Comparator;
import java.util.NoSuchElementException;

public class Board
{
    private int priority;
    private int moves;
    private int size;
    private int refBlockRow;
    private int refBlockCol;
    
    private BinarySearchST<Integer, Integer> initBST =
                                        new BinarySearchST<Integer, Integer>();
    private ResizingArrayStack<Board> neighborStack =
                new ResizingArrayStack<Board>();
    private int [][]boardBlocks;

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
        for (int key : initBST.keys()) {
            if (initBST.get(key) != key) {
                hammingPriority++;
            }
        }
        return hammingPriority;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattanPriority = 0;
        /*int boardValue;
        for (int k = 1; k < size * size; k++) {
            boardValue = boardBlocks[k];
            if (boardValue != k) {
                if ((boardValue - size) > 0) {
                    if ((boardValue - size) > size) {
                        while ((boardValue - size) > 0) {
                            manhattanPriority++;
                            boardValue = boardValue - size;
                        }
                        boardValue = boardValue + size;
                    }
                    while (boardValue-- != k) {
                    }
                }
            }
        }*/
        return 0;
    }

    public boolean isGoal() {
        // is this board the goal board?
        if (hamming() == 0) {
            return true;
        } else {
            return false;
        }
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
        int refIndex = initBST.get(0);
        int [][]neighborBlocks = new int[size][size];
        /* Create Neighbors and store it in the STACK */
        if (refBlockRow > 0) {
            //Up neighbor exists
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == (i + 1)) &&
                            (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i + 1][j];
                        neighborBlocks[i + 1][j] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow &&
                                 j == refBlockCol)) {
                        neighborBlocks = boardBlocks[i + 1][j];
                    }
                }
            }
            Board upBoard = new Board(neighborBlocks);
            neighborStack.push(upBoard);
        }

        //if ((initBST.get(0) + size) < size * size) {
        if (i < (size - 1)) {
            //bottom neighbor exist
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((refBlockRow == (i - size)) &&
                            (refBlockCol == j)) {
                        neighborBlocks[i][j] = boardBlocks[i - size][j];
                        neighborBlocks[i + 1][j] = boardBlocks[i][j];
                    } else if (!(i == refBlockRow &&
                                 j == refBlockCol)) {
                        neighborBlocks = boardBlocks[i + 1][j];
                    }
                }
            }
            Board upBoard = new Board(neighborBlocks);
            Board bottomBoard = new Board(null);
            bottomBoard.boardBlocks = true;
            bottomBoard.size = size;
            for (int key : initBST.keys()) {
                if (initBST.get(key) == (refIndex + size)) {
                    bottomBoard.initBST.put(0, initBST.get(key));
                    bottomBoard.initBST.put(key, refIndex);
                } else {
                    bottomBoard.initBST.put(key, initBST.get(key));
                }
            }
            neighborStack.push(bottomBoard);
        }

        //if (initBST.get(0) % size == 0) {
        if (j == 0) {
            //Only right neighbor exist
            Board rightBoard = new Board(null);
            rightBoard.boardBlocks = true;
            rightBoard.size = size;
            for (int key : initBST.keys()) {
                if (initBST.get(key) == (refIndex + 1)) {
                    rightBoard.initBST.put(0, initBST.get(key));
                    rightBoard.initBST.put(key, refIndex);
                } else {
                    rightBoard.initBST.put(key, initBST.get(key));
                }
            }
            neighborStack.push(rightBoard);
        //} else if (initBST.get(0) % (size - 1) == 0) {
        } else if (j == (size - 1)) {
            //Only left neighbor exist
            Board leftBoard = new Board(null);
            leftBoard.boardBlocks = true;
            leftBoard.size = size;
            for (int key : initBST.keys()) {
                if (initBST.get(key) == (refIndex - 1)) {
                    leftBoard.initBST.put(0, initBST.get(key));
                    leftBoard.initBST.put(key, refIndex);
                } else {
                    leftBoard.initBST.put(key, initBST.get(key));
                }
            }
            neighborStack.push(leftBoard);
        } else {
            //Both right and left neighbor exist
            Board rightBoard = new Board(null);
            Board leftBoard = new Board(null);
            rightBoard.boardBlocks = true;
            rightBoard.size = size;
            leftBoard.boardBlocks = true;
            leftBoard.size = size;

            for (int key : initBST.keys()) {
                if ((initBST.get(key) == (refIndex + 1))) {
                    rightBoard.initBST.put(0, initBST.get(key));
                    rightBoard.initBST.put(key, refIndex);
                } else {
                    rightBoard.initBST.put(key, initBST.get(key));
                } 
            }
            for (int key : initBST.keys()) {
                 if (initBST.get(key) == (refIndex - 1)) {
                    leftBoard.initBST.put(0, initBST.get(key));
                    leftBoard.initBST.put(key, refIndex);
                } else {
                    leftBoard.initBST.put(key, initBST.get(key));
                }
            }
            neighborStack.push(rightBoard);
            neighborStack.push(leftBoard);
        }
        return neighborStack;
    }

    public String toString() {
        // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        int []board = new int[size * size];
        s.append(size + "\n");
        for (int key : initBST.keys()) {
            board[initBST.get(key)] = key;
        }

        for (int i = 0; i < size * size; i++) {
            if (i % size == 0) {
                s.append("\n");
            }
            s.append(String.format("%2d ", board[i]));
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
        //System.out.println(initial.toString());
        //System.out.println(initial.hamming());
        //System.out.println(initial.isGoal());
        for (Board neighborBoard : initial.neighbors()) {
            if (neighborBoard != null) {
                //System.out.println(neighborBoard.toString());
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
