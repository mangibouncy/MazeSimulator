public class HanselAndGretel {

    /**
     * Reads the maze file and returns a 2D boolean array.
     *
     * The file is expected to have an integer representing the dimension (dim)
     * of the maze, followed by dim*dim booleans which represent maze cells.
     * A true value indicates an explorable cell, while false indicates a wall.
     *
     * @param file the file path of the maze input file
     * @return a 2D boolean array representing the maze layout
     */
    public static boolean[][] readMaze(String file) { //PROVIDED BY INSTRUCTOR
        // DO NOT MODIFY THIS METHOD
        StdIn.setFile(file);
        int dim = StdIn.readInt();
        boolean[][] maze = new boolean[dim][dim];
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                maze[row][col] = StdIn.readBoolean();
            }
        }
        return maze;
    } //returns maze that you will use in the other methods

    /**
     * Recursively traverses the maze from the current position (row, col)
     * attempting to find a path to the exit (bottom-right corner).
     *
     * The method checks if the current cell is the exit. Otherwise, it attempts
     * to move to an adjacent cell (right, left, down, up) if that cell is open
     * and not yet visited. If no valid adjacent move is available, it calls
     * backtrack to search for alternative paths.
     *
     * @param maze        a 2D boolean maze where true indicates an open cell and false indicates a wall
     * @param breadcrumbs a 2D boolean array tracking visited positions
     * @param x         the current row in the maze
     * @param y         the current column in the maze
     * @return an integer array containing the coordinates {row, col} of the exit if found,
     *         or the starting cell if no exit exists
     */
    public static int[] takeStep(boolean[][] maze, boolean[][] breadcrumbs, int row, int col) { //LOGIC DONE BY ME
        
        boolean isFinishCell = row == maze.length-1 && col == maze[row].length-1;
        breadcrumbs[row][col] = true;

        if (isFinishCell){
            return new int[] {row,col};
        }
        if (maze[0][0] == false){
            
            return new int[] {row,col};
        }
        if (col + 1 < maze[0].length && maze[row][col+1] && !breadcrumbs[row][col+1]){
            breadcrumbs[row][col+1] = true;
            return takeStep(maze, breadcrumbs, row, col+1);
        }
        if (col - 1 >= 0 && maze[row][col-1] && !breadcrumbs[row][col-1]){
            breadcrumbs[row][col-1] = true;
            return takeStep(maze, breadcrumbs, row, col-1);
        }
        if (row + 1 < maze.length && maze[row+1][col] && !breadcrumbs[row+1][col]){
            breadcrumbs[row+1][col] = true;
            return takeStep(maze, breadcrumbs, row+1, col);
        }
        if (row - 1 >= 0 && maze[row-1][col] && !breadcrumbs[row-1][col]){
            breadcrumbs[row-1][col] = true;
            return takeStep(maze, breadcrumbs, row-1, col);
        }

        return backtrack(maze, breadcrumbs, row, col);
    }    
        
    /**
     * Backtracks from a dead-end to a previously visited cell that might have unexplored
     * neighbors. In the backtracking process, the current cell is marked as unvisited and
     * blocked, to ensure it is not considered again in the path search.
     *
     * The method checks the four adjacent cells (right, left, down, up) for visited cells,
     * and returns from the first valid backtracking move. If backtracking reaches the start
     * (and no moves remain), it outputs that no exit exists.
     *
     * @param maze        a 2D boolean maze where true indicates an open cell and false indicates a wall
     * @param breadcrumbs a 2D boolean array tracking visited positions
     * @param row         the current row in the maze
     * @param col         the current column in the maze
     * @return an integer array containing the current position {row, col} after backtracking,
     *         or {0, 0} if no exit can be found
     */
    public static int[] backtrack(boolean[][] maze, boolean[][] breadcrumbs, int row, int col) { //LOGIC DONE BY ME
        
        boolean isFinishCell = row == breadcrumbs.length-1 && col == breadcrumbs[row].length-1;

        if (isFinishCell == true){
            breadcrumbs[row][col] = true;
            return new int[] {row,col};
        }
        if (col + 1 < breadcrumbs[0].length && breadcrumbs[row][col+1] && maze[row][col+1]){
            breadcrumbs[row][col] = false;
            maze[row][col] = false;
            return takeStep(maze, breadcrumbs, row, col+1);
        }
        if (col - 1 >= 0 && breadcrumbs[row][col-1] && maze[row][col-1]){
            
                breadcrumbs[row][col] = false;
                maze[row][col] = false;
            
            return takeStep(maze, breadcrumbs, row, col-1);
        }
        if (row + 1 < breadcrumbs.length && breadcrumbs[row+1][col] && maze[row+1][col]){
            breadcrumbs[row][col] = false; 
            maze[row][col] = false;         
            return takeStep(maze, breadcrumbs, row+1, col);
        }
        if (row - 1 >= 0 && breadcrumbs[row-1][col] && maze[row-1][col]){
  
                breadcrumbs[row][col] = false;
                maze[row][col] = false;
                
            
            return takeStep(maze, breadcrumbs, row-1, col);
        }
        if (row == 0 && col == 0){
            return new int[] {row,col};
        }
        return takeStep(maze, breadcrumbs, row, col);
    }


    /**
     * Writes the visited maze to the specified output file.
     *
     * The output file will begin with the maze's dimension (i.e., the number of rows)
     * followed by the 2D representation of the visited cells. Each cell is separated by
     * a space, and each row is written on a new line.
     *
     * @param visited a 2D boolean array where each element indicates whether
     *                the corresponding cell was visited (true) or not (false)
     * @param file    the file path where the visited maze should be written
     */
    public static void writeVisitedMaze(boolean[][] visited, String file) { //LOGIC DONE BY ME
        
        StdOut.setFile(file);
        int dim = visited.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                StdOut.print(visited[i][j] + " ");
            }
            StdOut.println();
        }
    }

    /**
     * The main entry point for the Hansel and Gretel maze traversal program.
     * 
     * This method:
     * 1. Reads a maze from the specified input file
     * 2. Attempts to find a path from the top-left cell (0,0) to the bottom-right cell
     * 3. Tracks which cells were visited during the traversal
     * 4. Writes the visited cells to the specified output file
     *
     * The program expects exactly two command-line arguments:
     * - The path to the input file containing the maze definition
     * - The path to the output file where the visited cells will be written
     *
     * @param args command line arguments containing input and output file paths
     */
    public static void main(String[] args) { //LOGIC DONE BY ME
        // We expect two arguments: the input file and the output file
        // This prevents your program from crashing when there is a mismatch in the number of arguments
        // DO NOT MODIFY THIS CONDITIONAL STATEMENT
        if (args.length != 2) {
            System.out.println("Usage: java HanselAndGretelReference <inputfile> [outputfile]");
            return;
        }

        boolean[][] maze = readMaze(args[0]);
        StdOut.print(maze[0][0]);
        boolean[][] visitedArray = new boolean[maze.length][maze[0].length];
        
        if (maze[0][0] == false){
            writeVisitedMaze(visitedArray, args[1]);
        }
        else{
            takeStep(maze, visitedArray, 0, 0);
            writeVisitedMaze(visitedArray, args[1]);
        }

        
    }
}
