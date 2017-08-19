/* PixelVertex.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Vertex Data Structure


   B. Bird - 07/03/2017
*/

import java.awt.Color;

public class PixelVertex{

	//Coordinates for this vertex
	private int x;
	private int y;
	//Value of the colour for this vertex
	private Color col;
	
	public boolean visited;
	//An array to keep track of the neighbours of this vertex
	private PixelVertex[] neighbours = new PixelVertex[4];
	//Count of the number of adjacent vertices
	private int neighbourCount = 0;
	
	/*Constructor*/
	public PixelVertex(int x, int y, Color col){
		this.x = x;
		this.y = y;
		this.col = col;
		this.visited = false;
		//Set the initial values for each item of the neighbours list to null
		for(int i=0;i<neighbours.length;i++){
			neighbours[i] = null;
		}
	}
	
	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return x;
	}
	
	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return y;
	}
	
	public Color getCol(){
		return col;
	}
	
	public boolean getVisited(){
		return visited;
	}
	
	public void setVisited(boolean val){
		visited = val;
	}
	
	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	*/
	public PixelVertex[] getNeighbours(){
		return neighbours;
	}
	
	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
		neighbourCount++;
		neighbours[neighbourCount-1] = newNeighbour;
	}
	
	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
		for(int i=0;i<neighbours.length;i++){
			if(neighbours[i]==neighbour){
				neighbours[i]=null;
				neighbourCount--;
			}
		}
	}
	
	/* getDegree()
	   Return the degree of this vertex.
	*/
	public int getDegree(){
		return neighbourCount;
	}
	
	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){
		for(int i=0;i<neighbours.length;i++){
			if(neighbours[i]==otherVertex){
				return true;
			}
		}
		return false;
	}
	
	/* allNeighboursVisited(otherVertex)
	   Return true if all of the neighbours of otherVertex have been visited
	   and false otherwise.
	*/
	public boolean allNeighboursVisited(){
		for(int i=0;i<neighbourCount;i++){
			if(neighbours[i].getVisited() == false){
				return false;
			}
		}
		return true;
	}
	
	/* printVertex()
		Prints out the x, y, and colour values for each vertex
	*/
	public void printVertex(){
		System.out.println("X: "+x+" Y: "+y+" Colour: "+col);
	}
	
}