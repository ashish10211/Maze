package mazeGenerator;

import java.util.*;

import maze.Cell;
import maze.Maze;
/**
 * 
 * @author Ashish
 * ********************************************************
 * 1:Select a cell currentCell randomly
 * 2:Get all the valid Neighbours of cell
 * 3:Now carve path to any Neighbour
 * 4:If we have to move back we are using combination of randomly selecting cell and latest cell
 * 5:Repeat until all cells are visited in maze
 * 
 * ********************************************************
 *
 */
public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	double threshold = 0.1;
	private Maze tempMaze;
	public boolean visited[][];
	Cell currentCell;
	int ranNeigh;
	Random rand=new Random();
	ArrayList<Cell> hexCells=new ArrayList<>();
	private HashSet<Cell> visitedHexCells=new HashSet<>();
	ArrayList<Cell> z=new ArrayList<>();
	@Override
	public void generateMaze(Maze maze) {
		tempMaze=maze;
		boolean isunvisitedNeighbour=true;
		visited=new boolean[maze.sizeR][maze.sizeC];
		int unvisitiedCells;
		int randomNeighbour;	
		if(maze.type==maze.NORMAL){
			unvisitiedCells=maze.sizeR*maze.sizeC;
			//adding cell to stack
			currentCell=startingCell();
			visited[currentCell.r][currentCell.c]=true;
		    z.add(currentCell);
		    unvisitiedCells--;
		    //run loop till z is empty
			  while(!z.isEmpty()){
	
				  while(isunvisitedNeighbour){
					  //getting all the neighbours
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
						  z.add(currentCell);
						  currentCell=currentCell.neigh[randomNeighbour];
						visited[currentCell.r][currentCell.c]=true;
							unvisitiedCells--;
					 }else{
						 isunvisitedNeighbour=false;
					 }
					
			  }
				  for(int j=0;j<z.size();j++){
					  if(currentCell.equals(z.get(j))){
						  //removing cell from Z
						  z.remove(j);
					  }
				  }
				  //choosing cell randomly and latest one
				  int choice=rand.nextInt(2)+1;
				  if(z.size()>0){
					  int ranSel=rand.nextInt(z.size());
					  switch(choice){
					  case 1:
						  currentCell=z.get(ranSel);
						  break;
					  case 2:
						  currentCell=z.get(z.size()-1);
						  break;
					  }
						 
				  }
						 
				  isunvisitedNeighbour=true;
			 
		}
			 
		}else if(maze.type==maze.HEX){
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
            z.add(currentCell);
            visitedHexCells.add(currentCell);
            unvisitiedCells--;
            while(!z.isEmpty()){
            	
				  while(isunvisitedNeighbour){
					 
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
						  currentCell.wall[randomNeighbour].present=false;
						  z.add(currentCell);
						  currentCell=currentCell.neigh[randomNeighbour];
						  visitedHexCells.add(currentCell);
							unvisitiedCells--;
					 }else{
						 isunvisitedNeighbour=false;
					 }
					
			  }
				  for(int j=0;j<z.size();j++){
					  if(currentCell.equals(z.get(j))){
						  z.remove(j);
					  }
				  }
				  //choosing cell randomly and latest one
				  int choice=rand.nextInt(2)+1;
				  if(z.size()>0){
					  int ranSel=rand.nextInt(z.size());
					  switch(choice){
					  case 1:
						  currentCell=z.get(ranSel);
						  break;
					  case 2:
						  currentCell=z.get(z.size()-1);
						  break;
					  }
						 
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
