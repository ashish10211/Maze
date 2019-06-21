package mazeSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

/**@author sebytom
 * Implements WallFollowerSolver
 * ***********************************************************************
 * Selecting any  direction which solver will always follow
 * 1: Select right direction to follow
 * 2:Check validity of right Neighour
 * 3:for(i=0;maze.NUM_DIR;i++)
 *   if(List.get(i).contains(right))
 *   move(right)
 *   else
 *   come back and move to right
 *  end
 *  4: follow step 3
 *  until
 *  maze.entrance=maze.exit
 *  end
 * 
 * ***********************************************************************
 */

public class WallFollowerSolver implements MazeSolver {
	private Maze tempMaze;
	public boolean visited[][];
	Cell currentCell;
	boolean solved;
	boolean tunnel;
	ArrayList<Cell> hexCells=new ArrayList<>();
	private HashSet<Cell> visitedHexCells=new HashSet<>();
	Stack<Cell> visitedCells=new Stack<>();
	Random rand=new Random();
	int direction;
	ArrayList<Cell> tunnelCells=new ArrayList();
	@Override
	public void solveMaze(Maze maze) {
		tempMaze=maze;
		visited=new boolean[maze.sizeR][maze.sizeC];
		int unvisitiedCells;
		int randomNeighbour;
		int random;
		boolean isunvisitedNeighbour=true;
		if(maze.type==maze.NORMAL){
			unvisitiedCells=maze.sizeR*maze.sizeC;
			currentCell=maze.map[maze.entrance.r][maze.entrance.c];
			visitedCells.push(currentCell);
			visited[currentCell.r][currentCell.c]=true;
			unvisitiedCells--;
			Cell target=maze.map[maze.exit.r][maze.exit.c];
			int right=maze.NORTH;
			  maze.drawFtPrt(currentCell);
			while(currentCell!=target){
				  while(isunvisitedNeighbour){
					//getting list of Neighbours	 
					  ArrayList neighList=new ArrayList();
					  for(int i=0;i<maze.NUM_DIR;i++){
						  Cell neighbourCell = currentCell.neigh[i];
						  if(isIn(neighbourCell) && isVisitied(neighbourCell)&&(!currentCell.wall[i].present)){
							  neighList.add(i); 
						  }
					  }
				
					   if(neighList.size()>0){		
						 for(int j=0;j<neighList.size();j++){
							 if(neighList.contains(right)){
								 direction=2;
							 }else{
								 random=rand.nextInt(neighList.size());
								 direction=(int) neighList.get(random);
							 }
						 }
						 //Selecting direction to move
						 switch(direction){
						 case 0:
						 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
						 	 if(currentCell==target){
									solved=true;
									break;
								}
						     break;
						 case 2:
						 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
						 	 if(currentCell==target){
									solved=true;
									break;
								}
						     break;
						 case 3:
						 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
						 	 if(currentCell==target){
									solved=true;
									break;
								}
						     break;
						 case 5:
						 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
						 	 if(currentCell==target){
									solved=true;
									break;
								}
						     break;
						 }
						  maze.drawFtPrt(currentCell);
						  visitedCells.push(currentCell);
						 
						visited[currentCell.r][currentCell.c]=true;
							unvisitiedCells--;
					 }else{
						 isunvisitedNeighbour=false;
					 }
					
				  
			  }
				  if(currentCell==target){
						solved=true;
						break;
					}
				  if(visitedCells.size()>0){
				  currentCell=visitedCells.pop();
				  }
				  isunvisitedNeighbour=true;
		}if(currentCell==target){
			solved=true;
		}
			
		}else if(maze.type==maze.TUNNEL){
			unvisitiedCells=maze.sizeR*maze.sizeC;
				currentCell=maze.map[maze.entrance.r][maze.entrance.c];
				visitedCells.push(currentCell);
				visited[currentCell.r][currentCell.c]=true;
				unvisitiedCells--;
				Cell target=maze.map[maze.exit.r][maze.exit.c];
				int right=maze.NORTH;
				  maze.drawFtPrt(currentCell);
				while(currentCell!=target){
					while(isunvisitedNeighbour){
						
						  ArrayList neighList=new ArrayList();
						  for(int i=0;i<maze.NUM_DIR;i++){
							  Cell neighbourCell = currentCell.neigh[i];
							  if(isIn(neighbourCell) && isVisitied(neighbourCell)&&(!currentCell.wall[i].present)){
								  neighList.add(i); 
							  }
						  }
						  if(currentCell.tunnelTo!=null && !visited[currentCell.tunnelTo.r][currentCell.tunnelTo.c]&&(!tunnelCells.contains(currentCell))){
							  
							  tunnel=true;
							  currentCell=currentCell.tunnelTo;
							  maze.drawFtPrt(currentCell);
							  visited[currentCell.r][currentCell.c]=true;
							  visitedCells.push(currentCell);
							  currentCell=currentCell.tunnelTo;
							  tunnelCells.add(currentCell);
							
								unvisitiedCells--;
						  } 
						   if((neighList.size()>0)&&(currentCell.tunnelTo==null)){			   
						   	 for(int j=0;j<neighList.size();j++){
								 if(neighList.contains(right)){
									 direction=2;
								 }else{
									 random=rand.nextInt(neighList.size());
									 direction=(int) neighList.get(random);
								 }
							 }
							 switch(direction){
							 case 0:
							 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
							 	 if(currentCell==target){
										solved=true;
										break;
									}
							     break;
							 case 2:
							 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
							 	 if(currentCell==target){
										solved=true;
										break;
									}
							     break;
							 case 3:
							 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
							 	 if(currentCell==target){
										solved=true;
										break;
									}
							     break;
							 case 5:
							 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
							 	 if(currentCell==target){
										solved=true;
										break;
									}
							     break;
							 }
							  maze.drawFtPrt(currentCell);
							  visitedCells.push(currentCell);
							visited[currentCell.r][currentCell.c]=true;
								unvisitiedCells--;
						   			
						 }else{
							 isunvisitedNeighbour=false;
						 }
						   if(currentCell==target){
								solved=true;
								break;
							}
							 
					  
			}
					 if(visitedCells.size()>0){
						  currentCell=visitedCells.pop();	
						  }
						  isunvisitedNeighbour=true;
				
				}
				if(currentCell==target){
					solved=true;
				}
		}if(maze.type==maze.HEX){
			//getting Usefull cells from hex
			  for (int i = 0; i < maze.sizeR; i++) {
	                for (int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {
	                    if (!isIn(i, j))
	                        continue;
	                    hexCells.add(maze.map[i][j]);
	                }
	            }
			  unvisitiedCells = hexCells.size();
			  currentCell=maze.map[maze.entrance.r][maze.entrance.c];
				visitedCells.push(currentCell);
				 visitedHexCells.add(currentCell);
				unvisitiedCells--;
				Cell target=maze.map[maze.exit.r][maze.exit.c];
				int right=maze.NORTHEAST;
				  maze.drawFtPrt(currentCell);
					while(currentCell!=target){
						while(isunvisitedNeighbour){
							//Getting List if neighbours
							  ArrayList neighList=new ArrayList();
							  for(int i=0;i<maze.NUM_DIR;i++){
								  Cell neighbourCell = currentCell.neigh[i];
								  if(isIn(neighbourCell) && isVisitied(neighbourCell)&&(!currentCell.wall[i].present)){
									  neighList.add(i); 
								  }
							  }
	
							   if((neighList.size()>0)){			   
							   	 for(int j=0;j<neighList.size();j++){
									 if(neighList.contains(right)){
										 direction=1;
									 }else{
										 random=rand.nextInt(neighList.size());
										 direction=(int) neighList.get(random);
									 }
								 }
							   	 //Selecting direction to move
								 switch(direction){
								 case 0:
								 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
								 	 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 2:
								 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
								 	 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 3:
								 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
								 	 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 5:
								 	  currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
								 	 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 1:
									 currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
									 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 4:
									 currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
									 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 6:
									 currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
									 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								 case 7:
									 currentCell=maze.map[currentCell.r+maze.deltaR[direction]][currentCell.c+maze.deltaC[direction]];
									 if(currentCell==target){
											solved=true;
											break;
										}
								     break;
								     
								 }
								  maze.drawFtPrt(currentCell);
								  visitedCells.push(currentCell);
								  visitedHexCells.add(currentCell);
									unvisitiedCells--;
							   			
							 }else{
								 isunvisitedNeighbour=false;
							 }
							   if(currentCell==target){
									solved=true;
									break;
								}
								 
						  
				}
						 if(visitedCells.size()>0){
							  currentCell=visitedCells.pop();	
							  }
							  isunvisitedNeighbour=true;
					
					}
					if(currentCell==target){
						solved=true;
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
    /**
     * @return solved : true if maze is solved
     */
	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return solved;
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return 0;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
