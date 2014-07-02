//import algs4.*;
public class Percolation {
    private int count;
    private QuickFindUF quickFind;
    private int[] topRowId;
    private int[] elements;
    
    public Percolation(int N) {              // create N-by-N grid, with all sites blocked
        quickFind = new QuickFindUF(N * N);
        count     = N;
        topRowId = new int[N];
        elements = new int[N * N];
        for (int i = 0; i < N; i++) {
            topRowId[i] = -1 * i - 1; // Initialize to the block state; value = -1 and is valid only for TOP row
        }
        for (int j = 0; j < N * N; j++) {
            elements[j] = -1;
        }
    }
    
    public void open(int i, int j) {        // open site (row i, column j) if it is not already
        int gridId;
        int finalCountValue = (count * count) - 1;
        if (i < 1 && i > count && j < 1 && j > count) {           
            throw new IndexOutOfBoundsException();
        } else {
            if (i == 3 && j == 11) {
                assert Boolean.TRUE;
            }
            gridId = (i - 1) * count + (j - 1);
            elements[gridId] = 0;
             /* if (gridId + 1 == 46) {
                    assert Boolean.TRUE;
                } else if (gridId - 1 == 46) {
                    assert Boolean.TRUE;
                } else if (gridId - count == 46) {
                    assert Boolean.TRUE;
                } else if (gridId + count == 46) {
                    assert Boolean.TRUE;
                } else if (gridId == 46) {
                    assert Boolean.TRUE;
                }*/
            
            //System.out.print(i + " " + j);
            if (gridId < (count - 1)) {
                // Top row boundary
                //System.out.print(" Top" + topRowId[gridId] + " " + quickFind.find(gridId));
                //quickFind.union(topRowId[gridId], quickFind.find(gridId));
                topRowId[gridId] = quickFind.find(gridId);
                if (quickFind.find(gridId + count) != (gridId + count)) {
                    quickFind.union((gridId + count), gridId);
                }
            } else if ((gridId > 0) && 
                       (gridId % count == 0) && 
                       (gridId < (finalCountValue - count))) {
                // left boundary
                //System.out.print(" Left");
                if (quickFind.find(gridId) == gridId) {
                    // Open it
                    if ((gridId - count) < (count - 1)) {
                        // If the above element is the top most row
                        if (topRowId[gridId - count] == (gridId - count)) { //check if top row element is connected
                            quickFind.union(gridId, (gridId - count));
                        } else if (quickFind.find(gridId + 1) != (gridId + 1)) { //check if the right neighbor element is connected 
                            quickFind.union(gridId, gridId + 1);
                        } else if (((gridId + count) <= finalCountValue) && 
                                   (quickFind.find(gridId + count) != (gridId + count))) { // check if the bottom element is connected
                            quickFind.union(gridId, (gridId + count));
                        } else {
                        //topRowId[gridId] = quickFind.find(gridId);
                            if (elements[gridId - count] == 0) {
                                quickFind.union(gridId, (gridId - count));
                            } else if (elements[gridId + 1] == 0) {
                                quickFind.union(gridId, (gridId + 1));
                            }
                        }
                    
                        if ((quickFind.find(gridId + count) != (gridId + count)) ||
                            (elements[gridId + count] == 0)) {
                            //Connect to the bottom neighbor
                            //quickFind.union(gridId, (gridId - count));
                            quickFind.union((gridId + count), gridId);   
                            
                        }
                    } else if (quickFind.find(gridId + 1) != (gridId + 1)) {
                        //Connect to the right neighbor
                        quickFind.union(gridId, (gridId + 1));
                    } else {
                        //topRowId[gridId] = quickFind.find(gridId);
                        if (elements[gridId - count] == 0) {
                            quickFind.union(gridId, (gridId - count));
                        } else if (elements[gridId + 1] == 0) {
                            quickFind.union(gridId, (gridId + 1));
                        }
                    }
                    
                    if ((quickFind.find(gridId + count) != (gridId + count)) ||
                               (elements[gridId + count] == 0)) {
                        //Connect to the bottom neighbor
                        //quickFind.union(gridId, (gridId - count));
                        quickFind.union((gridId + count), gridId);   
                        
                    }
                }  
            } else if ((gridId > (count - 1)) && 
                       ((gridId + 1) % count == 0) &&
                       (gridId < finalCountValue)) {
                // right boundary
                //System.out.print(" Right");
                   if ((gridId - count) < (count - 1)) {
                        // If the above element is the top most row
                        if (topRowId[gridId - count] == (gridId - count)) { //check if top row element is connected
                            quickFind.union(gridId, (gridId - count));
                        } else if (quickFind.find(gridId - 1) != (gridId - 1)) { //check if the left neighbor element is connected 
                                quickFind.union(gridId, gridId - 1);
                        } else if (((gridId + count) <= finalCountValue) && 
                                   (quickFind.find(gridId + count) != (gridId + count))) { // check if the bottom element is connected
                            quickFind.union(gridId, (gridId + count));
                        } else { //connected to the blocked top element
                            //topRowId[gridId] = quickFind.find(gridId);
                            if (elements[gridId - count] == 0) {
                                quickFind.union(gridId, (gridId - count));
                            } else if (elements[gridId - 1] == 0) {
                                quickFind.union(gridId, (gridId - 1));
                            } 
                        }
                        
                        if ((quickFind.find(gridId + count) != (gridId + count)) ||
                            (elements[gridId + count] == 0)) {
                            //Connect to the bottom neighbor
                            //quickFind.union(gridId, (gridId - count));
                            quickFind.union((gridId + count), gridId);   
                            
                        }
                    } else if (quickFind.find(gridId - 1) != (gridId - 1)) {
                        //Connect to the right neighbor
                        quickFind.union(gridId, (gridId - 1));
                    } else if (quickFind.find(gridId + count) != (gridId + count)) {
                        //Connect to the bottom neighbor
                        quickFind.union(gridId, (gridId - count));
                        quickFind.union((gridId + count), gridId);                        
                    } else {
                        //topRowId[gridId] = quickFind.find(gridId);
                        if (elements[gridId - count] == 0) {
                            quickFind.union(gridId, (gridId - count));
                        } else if (elements[gridId - 1] == 0) {
                            quickFind.union(gridId, (gridId - 1));
                        } 
                    }
                    
                    if ((quickFind.find(gridId + count) != (gridId + count)) ||
                               (elements[gridId + count] == 0)) {
                        //Connect to the bottom neighbor
                        //quickFind.union(gridId, (gridId - count));
                        quickFind.union((gridId + count), gridId);   
                        
                    }
            } else if ((gridId <= finalCountValue) &&
                       (gridId >= (finalCountValue - count))) {
                //last row boundary
                //System.out.print(" Last");
                if ((gridId - count) < (count - 1)) {
                    // If the above element is the top most row
                        if (topRowId[gridId - count] == (gridId - count)) { //check if top row element is connected
                            quickFind.union(gridId, (gridId - count));
                            
                        } else if (((gridId + 1) <= finalCountValue) &&
                                   (quickFind.find(gridId + 1) != (gridId + 1))) { //check if the right neighbor element is connected 
                                quickFind.union(gridId, gridId + 1);
                        } else if (((gridId - 1) > (finalCountValue - count)) &&
                                   (quickFind.find(gridId - 1) != (gridId - 1))) { //check if the left neighbor element is connected 
                                quickFind.union(gridId, gridId - 1);
                        } else { //connected to the blocked top element
                            //topRowId[gridId] = quickFind.find(gridId);
                            if (elements[gridId - count] == 0) {
                                quickFind.union(gridId, (gridId - count));
                            } else if (elements[gridId + 1] == 0) {
                                quickFind.union(gridId, (gridId + 1));
                            } else if (elements[gridId - 1] == 0) {
                                quickFind.union(gridId, (gridId - 1));
                            } 
                        }                   
                } else if (((gridId + 1) <= finalCountValue) &&
                           (quickFind.find(gridId + 1) != (gridId + 1))) { //check if the right neighbor element is connected 
                    quickFind.union(gridId, gridId + 1);
                } else if (((gridId - 1) > (finalCountValue - count)) &&
                                   (quickFind.find(gridId - 1) != (gridId - 1))) { //check if the left neighbor element is connected 
                    quickFind.union(gridId, gridId - 1);
                } else {
                        //topRowId[gridId] = quickFind.find(gridId);
                        if (elements[gridId - count] == 0) {
                            quickFind.union(gridId, (gridId - count));
                        } else if (elements[gridId + 1] == 0) {
                            quickFind.union(gridId, (gridId + 1));
                        } else if (elements[gridId - 1] == 0) {
                                quickFind.union(gridId, (gridId - 1));
                        } 
                }                     
            } else {
                // everywhere else
                //System.out.print(" Else where");
              
                if ((gridId - count) < (count - 1)) {
                    // If the above element is the top most row
                        if (topRowId[gridId - count] == (gridId - count)) { //check if top row element is connected
                            quickFind.union(gridId, (gridId - count));
                            if ((elements[gridId + 1] == 0) &&
                                quickFind.find(gridId + 1) > ) {
                                quickFind.union(gridId, (gridId - count))
                            } else if () {
                            } else if () {
                            }
                        } else if (((gridId + 1) <= finalCountValue) &&
                                   (quickFind.find(gridId + 1) != (gridId + 1))) { //check if the right neighbor element is connected 
                                quickFind.union(gridId, gridId + 1);
                        } else if (//((gridId - 1) > (finalCountValue - count)) &&
                                   (quickFind.find(gridId - 1) != (gridId - 1))) { //check if the left neighbor element is connected 
                                quickFind.union(gridId, gridId - 1);
                        } else if (quickFind.find(gridId + count) != (gridId + count)) {
                            //Connect to the bottom neighbor
                            quickFind.union(gridId, (gridId - count));
                            quickFind.union((gridId + count), gridId);                        
                        } else { //connected to the blocked top element
                            //topRowId[gridId] = quickFind.find(gridId);
                            if (elements[gridId - count] == 0) {
                                quickFind.union(gridId, (gridId - count));
                            } else if (elements[gridId + 1] == 0) {
                                quickFind.union(gridId, (gridId + 1));
                            } else if (elements[gridId - 1] == 0) {
                                quickFind.union(gridId, (gridId - 1));
                            } 
                        }                    
                } 
                
                if (quickFind.find(gridId - count) != (gridId - count)) {
                    //connect to the top
                    quickFind.union(gridId, gridId - count);
                } else if ((quickFind.find(gridId + 1) != (gridId + 1))) { //check if the right neighbor element is connected 
                    quickFind.union(gridId, gridId + 1);
                } else if ((quickFind.find(gridId - 1) != (gridId - 1))) { //check if the left neighbor element is connected 
                    quickFind.union(gridId, gridId - 1);
                } else { //connected to the blocked top element
                        //topRowId[gridId] = quickFind.find(gridId);
                            if (elements[gridId - count] == 0) {
                                quickFind.union(gridId, (gridId - count));
                            } else if (elements[gridId + 1] == 0) {
                                quickFind.union(gridId, (gridId + 1));
                            } else if (elements[gridId - 1] == 0) {
                                quickFind.union(gridId, (gridId - 1));
                            } 
                }
                
                 if ((quickFind.find(gridId + count) != (gridId + count)) ||
                               (elements[gridId + count] == 0)) {
                        //Connect to the bottom neighbor
                        //quickFind.union(gridId, (gridId - count));
                        quickFind.union((gridId + count), gridId);   
                        
                    }      
            }
            
            //System.out.println(quickFind.find(46));
            if (quickFind.find(46) != 46) {
                assert Boolean.TRUE;
            }
        }
    }
    
    public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
        int gridId;
        if (i < 1 && i > count && j < 1 && j > count) {           
            throw new IndexOutOfBoundsException();
        } else {
            gridId = (i - 1) * count + (j - 1);
            if (gridId <= count - 1) {
                if (topRowId[gridId] == gridId) {
                    return true;
                } else {
                    return false;
                }
            }
            if (elements[gridId] == 0) {
                return true;
            } else {
                return false;
            }
            /*if (quickFind.find(gridId) != gridId) {
                if (gridId == 46) {
                    System.out.println("Culprit:" + quickFind.find(gridId));
                }
                return true;
            } else {
                return false;
            }*/         
        }
    }
    public boolean isFull(int i, int j) {    // is site (row i, column j) full?
        int gridId;
        int connectedId;
        if (i < 1 && i > count && j < 1 && j > count) {           
            throw new IndexOutOfBoundsException();
        } else {
            gridId = (i - 1) * count + (j - 1);
            if (elements[gridId] == -1) {
                return false;
            }
            connectedId = quickFind.find(gridId);
            if (i == 18 && j == 5)
                System.out.println(connectedId);
            for (int k = 0; k < count; k++) {
                if (topRowId[k] == connectedId) {
                    return true;
                }
            }   
            return false;
        }
    }
    public boolean percolates() {            // does the system percolate?
        // Implement the connection and determine the full sites
        
        return false;
    }
    public static void main(String[] args){
        In in = new In("/home/pbaligan/Projects/programming_challenges/coursera/coursera_princeton_algos_1/input20.txt");      // input file
        //In test = new In("/home/pbaligan/Projects/programming_challenges/coursera/coursera_princeton_algos_1/input20.txt");
        int N = in.readInt();         // N-by-N percolation system
        Percolation perc = new Percolation(N);
        int[] grid = new int[N * N];
        
        for (int n = 0; n < (N * N); n++) {
            grid[n] = 0;
        }
        
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            grid[(i - 1) * N + (j - 1)] = 1;
        }
        //N = test.readInt();
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j < N; j++) {
                if (grid[(i - 1) * N + (j - 1)] == 1) {
                    System.out.println(i + "," + j + " " + perc.isOpen(i, j) + " " + "True");
                } else if (perc.isOpen(i, j)) {
                    System.out.println(i + "," + j + " " + grid[(i - 1) * N + (j - 1)] + " " + "False");
                }
            }
        }
        /*while (!test.isEmpty()) {
            int i = test.readInt();
            int j = test.readInt();
            if (perc.isOpen(i, j)) {
                System.out.println("True");
            } else {
                System.out.println("False" + " " + i + " " + j);
            }
        }*/
    }
}
