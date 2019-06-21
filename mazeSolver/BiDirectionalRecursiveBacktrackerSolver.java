package mazeSolver;

import java.util.HashSet;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

/**
 * @author Ashish
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 * ****************************************************************
 *1:Select startCell from entry point and add to startStack
 *2:Select endCell from exit point and add to endStack
 *3:Now get all valid Neighbours of Cells
 *4:Move to those neighbours and mark then visited
 *5:make those neighbours new exit and entry Cells
 *6:Repeat step 3 , 4 and 5 until either entry cell is in exitStack or exit cell in entryStack
 *end
 *
 * ****************************************************************
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {
	private boolean endMeet = false;
	private Maze tempMaze;
	private HashSet<Cell> startVisited= new HashSet<Cell>();
	private HashSet<Cell> endVisited= new HashSet<Cell>();
	Cell entranceCell;
	Cell exitCell;
	Stack<Cell> start = new Stack<Cell>();
	Stack<Cell> end = new Stack<Cell>();
	@Override
	public void solveMaze(Maze maze) 
	{
		this.tempMaze = maze;
		entranceCell=maze.map[maze.entrance.r][maze.entrance.c];
		exitCell=maze.map[maze.exit.r][maze.exit.c];
		startVisited.add(entranceCell);
		endVisited.add(exitCell);
		//adding cell to starting stack
		start.add(maze.entrance);
		//adding cell to ending stack
		end.add(maze.exit);
		if(maze.entrance.equals(maze.exit))
		{
			endMeet = true;
			maze.drawFtPrt(maze.entrance);
			startVisited.add(maze.entrance);
		}
		//running loop till both ends meet
		while(!endMeet)
		{
			entranceCell = start.pop();
			//printing the path of currentCell
			maze.drawFtPrt(entranceCell);
			startVisited.add(entranceCell);

           //Traversal from start point
			for (int i = 0; i < Maze.NUM_DIR; i++) 
			{
                Cell currentNeighbor = entranceCell.neigh[i];
  
                if((endVisited.contains(currentNeighbor)) && (!entranceCell.wall[i].present))
                {
                	endMeet = true;
                	break;
                }
                else if((end.contains(currentNeighbor)) && (!entranceCell.wall[i].present))
                {
                	if(!endVisited.contains(currentNeighbor))
                	{
                		startVisited.add(currentNeighbor);
                    	maze.drawFtPrt(currentNeighbor);
                	}	
                	endMeet = true;
                	break;
                }

                else if ((isIn(currentNeighbor)) && (!start.contains(currentNeighbor)) 
                		&& (!entranceCell.wall[i].present) && (!startVisited.contains(currentNeighbor))) 
                {
                    start.add(currentNeighbor);
                }
            }
            //checking for tunnel
			if(entranceCell.tunnelTo != null)
			{
				Cell tunnelNeighbor = entranceCell.tunnelTo;
				
				if((end.contains(tunnelNeighbor)) || endVisited.contains(tunnelNeighbor))
                {
					if(!endVisited.contains(tunnelNeighbor))
					{
						maze.drawFtPrt(tunnelNeighbor);
						startVisited.add(tunnelNeighbor);
					}
						
                	endMeet = true;
                	break;
                }
				else if ((isIn(tunnelNeighbor)) && (!start.contains(tunnelNeighbor)) 
                		&& (!startVisited.contains(tunnelNeighbor))) 
                {
                    start.add(tunnelNeighbor);
                }
			}

			if(endMeet == true)
				break;

			exitCell = end.pop();

			maze.drawFtPrt(exitCell);

			endVisited.add(exitCell);

			//Traversal from exit side

			for (int i = 0; i < Maze.NUM_DIR; i++) 
			{	
                Cell currentNeighbor = exitCell.neigh[i];
            
                 if((startVisited.contains(currentNeighbor)) && (!exitCell.wall[i].present))
                {
                	endMeet = true;
                	break;
                }
                else  if((start.contains(currentNeighbor)) && (!exitCell.wall[i].present))
                {
                	if(!startVisited.contains(currentNeighbor))
                	{
                		endVisited.add(currentNeighbor);
                    	maze.drawFtPrt(currentNeighbor);
                	}	
                	endMeet = true;
                	break;
                }
                else if ((isIn(currentNeighbor)) && (!end.contains(currentNeighbor))
                		&& (!exitCell.wall[i].present) && (!endVisited.contains(currentNeighbor))) 
                {
                    end.add(currentNeighbor);
                }
            }
			//checking tunnel conditions
			if(exitCell.tunnelTo != null)
			{
	
				Cell tunnelNeighbor = exitCell.tunnelTo;
				if((start.contains(tunnelNeighbor)) || startVisited.contains(tunnelNeighbor))
                {
					if(!startVisited.contains(tunnelNeighbor))
					{
						maze.drawFtPrt(tunnelNeighbor);
						endVisited.add(tunnelNeighbor);
					}		
                	endMeet = true;
                	break;
                }
				else if ((!end.contains(tunnelNeighbor)) 
                		&& (!endVisited.contains(tunnelNeighbor))) 
                {
                    end.add(tunnelNeighbor);
                }
			}
	//stopping if ends are meeting
			if(endMeet == true)
				break;
		}
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
 
/**
 * @return endMeet : true if ends of both path meet
 */
	 
	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return endMeet;
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return 0;
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
