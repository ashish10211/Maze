
package mazeGenerator;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;
/**
 * 
 * @author Ashish
 * Generating a perfect maze using Recursive Backtrack technique:
     ****************************************************************
     * 1:Randomly Select starting cell as CurrentCell
     * 2:Get List of all valid Neighbours of currentCell
     * 3:Now randomly select any one of them and remove wall
     * 4:Make that cell as currentCell now
     * 5:Repeat step2 and 3 until all cells of maze are visited
     *end
     * 
     ****************************************************************
     * @param maze The reference of Maze object to generate type of maze
 *
 */
public class RecursiveBacktrackerGenerator implements MazeGenerator {
private Maze tempMaze;
public boolean visited[][];
Cell currentCell;
ArrayList<Cell> hexCells=new ArrayList<>();
private HashSet<Cell> visitedHexCells=new HashSet<>();
Stack<Cell> visitedCells=new Stack<>();
ArrayList<Cell> tunnelCells=new ArrayList();
private boolean tunnel=false;
Random rand=new Random();
	@Override
	public void generateMaze(Maze maze) {
		tempMaze=maze;
		boolean isunvisitedNeighbour=true;
		visited=new boolean[maze.sizeR][maze.sizeC];
		int unvisitiedCells;
		int randomNeighbour;
		if(maze.type==maze.NORMAL){
			unvisitiedCells=maze.sizeR*maze.sizeC;
			currentCell=startingCell();
			visited[currentCell.r][currentCell.c]=true;
		    visitedCells.push(currentCell);
		    unvisitiedCells--;
		    
			  while(unvisitiedCells>0){
	
				  while(isunvisitedNeighbour){
					//getting neighbours list
					  ArrayList neighList=new ArrayList();
					  for(int i=0;i<maze.NUM_DIR;i++){
						  Cell neighbourCell = currentCell.neigh[i];
						  if(isIn(neighbourCell) && isVisitied(neighbourCell)){
							  neighList.add(i); 
						  }
					  }

					 if(neighList.size()>0){			   
						 int random=rand.nextInt(neighList.size());
						 randomNeighbour=(int) neighList.get(random);
						 //removing wall
						  currentCell.wall[randomNeighbour].present=false;
						  visitedCells.push(currentCell);
						  currentCell=currentCell.neigh[randomNeighbour];
						visited[currentCell.r][currentCell.c]=true;
							unvisitiedCells--;
					 }else{
						 isunvisitedNeighbour=false;
					 }
					
				  
			  }
				  if(visitedCells.size()>0){
					  //poping cell out
				  currentCell=visitedCells.pop();
				  }
				  isunvisitedNeighbour=true;
			 
		}
			 
		}else if(maze.type==maze.HEX){
		//geting number of cells use hexagon
            for (int i = 0; i < maze.sizeR; i++) {
                for (int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {
                    if (!isIn(i, j))
                        continue;
                    hexCells.add(maze.map[i][j]);
                }
            }

            unvisitiedCells = hexCells.size();
             int randomCell=rand.nextInt(hexCells.size());
                currentCell = hexCells.get(randomCell);
            visitedCells.add(currentCell);
            visitedHexCells.add(currentCell);
            unvisitiedCells--;
            while (unvisitiedCells > 0) {
          
                while (isunvisitedNeighbour) {

                    ArrayList<Integer> neighList = new ArrayList<>();
                    for (int i = 0; i < Maze.NUM_DIR; i++) {
                        Cell currentNeighbor = currentCell.neigh[i];
                        if ((isIn(currentNeighbor)) && (isVisitied(currentNeighbor))) {
                        	neighList.add(i);
                        }
                    }

                    if (neighList.size() > 0) {
                    	 int random=rand.nextInt(neighList.size());
						  randomNeighbour=(int) neighList.get(random);

                        currentCell.wall[randomNeighbour].present = false;
                       
                        currentCell = currentCell.neigh[randomNeighbour];
                        visitedCells.add(currentCell);
                        visitedHexCells.add(currentCell);
                        unvisitiedCells--;
                    } else {
                    	isunvisitedNeighbour = false;
                    }
                }

                if (visitedCells.size() > 0) {
                    currentCell = visitedCells.pop();
                }
                isunvisitedNeighbour = true;
            }
	}else if(maze.type==maze.TUNNEL){
		unvisitiedCells=maze.sizeR*maze.sizeC;
		currentCell=startingCell();
		visited[currentCell.r][currentCell.c]=true;
	    visitedCells.push(currentCell);
	    unvisitiedCells--;
	    
		  while(unvisitiedCells>0){

			  while(isunvisitedNeighbour){
				 
				  ArrayList neighList=new ArrayList();
				  for(int i=0;i<maze.NUM_DIR;i++){
					  Cell neighbourCell = currentCell.neigh[i];
					  if(isIn(neighbourCell) && isVisitied(neighbourCell)&&(!tunnelCells.contains(neighbourCell))){
						  neighList.add(i); 
					  }
				  }
				  //checking currentCell has tunnel or not 
				  if(currentCell.tunnelTo!=null && !visited[currentCell.tunnelTo.r][currentCell.tunnelTo.c]){
					  tunnelCells.add(currentCell);
					  tunnel=true;
					  currentCell=currentCell.tunnelTo;
					  visited[currentCell.r][currentCell.c]=true;
						unvisitiedCells--;
				  } 
					  if((neighList.size()>0)&&(currentCell.tunnelTo==null)){			   
					 int random=rand.nextInt(neighList.size());
					 randomNeighbour=(int) neighList.get(random);
						 currentCell.wall[randomNeighbour].present=false;
						 visitedCells.push(currentCell);
						  currentCell=currentCell.neigh[randomNeighbour];
						visited[currentCell.r][currentCell.c]=true;
						unvisitiedCells--;
					 		
				 }else{
					 isunvisitedNeighbour=false;
				 }
				
			  
		  }
			  if(visitedCells.size()>0){
			  currentCell=visitedCells.pop();	
			  }
			  isunvisitedNeighbour=true;		 
	}
	}
	
	}
	/**
	 * Randomly select cell from maze
	 * @return starting cell
	 */
	public Cell startingCell(){
		 int row=rand.nextInt(tempMaze.sizeR);
		  int col=rand.nextInt(tempMaze.sizeC);
		  Cell start=tempMaze.map[row][col];
		  return start;
	}
	/**
	 * To check if cell has been visited
	 * @param neighbourCell cell to check if visited
	 * @return boolean true or false
	 */
	private boolean isVisitied(Cell neighbourCell) {
		  if (tempMaze.type == tempMaze.HEX) {
			    return !visitedHexCells.contains(neighbourCell);
	        } else {
	            return !visited[neighbourCell.r][neighbourCell.c];
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
    

	

	    
}
		
		

