package mazeGenerator;

import static maze.Maze.HEX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import maze.Cell;
import maze.Maze;

/**
 * @author Ashish
 * Generate maze with modified prim's algorithm
 * ********************************************************
 * 1:Select cell randomly and add to set Z
 * 2:Add all Neighbours of cell to set F
 * 3:Select adjacent Cells from z and F randomly
 * 4:carve path from z to f
 * 5:Repeat steps 2,3 and 4 until all cells are visited
 * end
 * 
 * ********************************************************
 *
 * 
 */
public class ModifiedPrimsGenerator implements MazeGenerator {

    private Maze tempMaze;
    private ArrayList<Cell> adjacentCells = new ArrayList<>();
    ArrayList<Cell> z = new ArrayList<>();
    ArrayList<Cell> f = new ArrayList<>();
    Random rand = new Random();
    Cell fCell;
    Cell zCell;
    ArrayList<Cell> hexCells=new ArrayList<>();
	private HashSet<Cell> visitedHexCells=new HashSet<>();
    @Override
    public void generateMaze(Maze maze) {
        tempMaze = maze;
        Cell currentCell = null;
         int totalCells;
        if ((maze.type == Maze.NORMAL)) {
            totalCells = maze.sizeR * maze.sizeC;
            //Selecting random Cell
           currentCell=startingCell();
            z.add(currentCell);
        while (z.size()!=totalCells) {
        	 for (int i = 0; i < Maze.NUM_DIR; i++) {
		            Cell currentNeighbor = currentCell.neigh[i];
		            if ((isIn(currentNeighbor)) && (!f.contains(currentNeighbor)) && (!z.contains(currentNeighbor))) {
		            	//adding all neighbours in F
		                f.add(currentNeighbor);
		                
		            }
		        }
        	 int rand1=rand.nextInt(f.size());
        	 //Selecting random cell from F
	            fCell=f.get(rand1);
	            f.remove(fCell);
	            adjacentCells.clear();
     
            for (int i = 0; i < z.size(); i++) {
	            Cell tempCell = z.get(i);
	            if (isAdjacent(tempCell, fCell)) {
	            	adjacentCells.add(tempCell);
	            }
	        }
          
            int rand2=rand.nextInt(adjacentCells.size());
            zCell=adjacentCells.get(rand2);

            for (int i = 0; i < Maze.NUM_DIR; i++) {
	            Cell currentNeighbor = zCell.neigh[i];
	            if ((isIn(currentNeighbor)) && (currentNeighbor == fCell)) {
	            	zCell.wall[i].present = false;
	            }
	        }
            z.add(fCell);
            currentCell = fCell; 
        }
        }else if (maze.type==maze.HEX){
        	//getting all usefull cells in hexagon
        	for (int i = 0; i < maze.sizeR; i++) {
                for (int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {
                    if (!isIn(i, j))
                        continue;
                    hexCells.add(maze.map[i][j]);
                }
            }

             totalCells = hexCells.size();
             int randomCell=rand.nextInt(hexCells.size());
                currentCell = hexCells.get(randomCell);
            z.add(currentCell);
            visitedHexCells.add(currentCell);
            while (z.size()!=totalCells) {
           	 for (int i = 0; i < Maze.NUM_DIR; i++) {
   		            Cell currentNeighbor = currentCell.neigh[i];
   		            if ((isIn(currentNeighbor)) && (!f.contains(currentNeighbor)) && (!z.contains(currentNeighbor))) {
   		                f.add(currentNeighbor);
   		            }
   		        }
           	 //randomly selecting cell from F
           	 int rand1=rand.nextInt(f.size());
   	            fCell=f.get(rand1);
   	            //removing selected Cell from F
   	            f.remove(fCell);
   	            adjacentCells.clear();
        
               for (int i = 0; i < z.size(); i++) {
   	            Cell tempCell = z.get(i);
   	            if (isAdjacent(tempCell, fCell)) {
   	            	//adding all adjacent Cells to list
   	            	adjacentCells.add(tempCell);
   	            }
   	        }
             
               int rand2=rand.nextInt(adjacentCells.size());
              //selecting cell from list of adjacent Cells
               zCell=adjacentCells.get(rand2);

               for (int i = 0; i < Maze.NUM_DIR; i++) {
   	            Cell currentNeighbor = zCell.neigh[i];
   	            if ((isIn(currentNeighbor)) && (currentNeighbor == fCell)) {
   	            	zCell.wall[i].present = false;
   	            }
   	        }
               z.add(fCell);
               currentCell = fCell; 
           }
        }
    } // end of generateMaze()

    /**
     * Select random cell from maze
     * @return cell random
     */
    public Cell startingCell(){
		 int row=rand.nextInt(tempMaze.sizeR);
		  int col=rand.nextInt(tempMaze.sizeC);
		  Cell start=tempMaze.map[row][col];
		  return start;
	}

    /**
     * Check if two cells are adjacents to each other
     * @param zCell
     * @param fCell
     * @return
     */
    private boolean isAdjacent(Cell zCell, Cell fCell) {
        for (int i = 0; i < Maze.NUM_DIR; i++) {
            Cell currentNeighbor = zCell.neigh[i];
            if (currentNeighbor == fCell) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a cell is in the maze
     *
     * @param row the row of the cell to check
     * @param column the column of the cell to check
     * @return weather the cell is in the maze
     */
	protected boolean isIn(int r, int c) {
		if(tempMaze.type==tempMaze.HEX){
			 return r >= 0 && r < tempMaze.sizeR && c >= (r + 1) / 2 && c < tempMaze.sizeC + (r + 1) / 2;
		}
		return r >= 0 && r < tempMaze.sizeR && c >= 0 && c < tempMaze.sizeC;
	}
    /**
     * Check whether the cell is in the maze.
     *
     * @param cell The cell being checked.
     * @return True if in the maze. Otherwise false.
     */
    private boolean isIn(Cell cell) {
        return cell != null && isIn(cell.r, cell.c);
    }
    

} // end of class ModifiedPrimsGenerator
