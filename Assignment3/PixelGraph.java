/* PixelGraph.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Graph Data Structure

   B. Bird - 07/03/2017
*/ 

import java.awt.Color;

public class PixelGraph{


	//Create 2D array of PixelVertices
	PixelVertex[][] graph;

	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour 
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image. 
	*/
	public PixelGraph(Color[][] imagePixels){
		
		//Create a graph of the same size as the image 
		graph = new PixelVertex[imagePixels.length][imagePixels[0].length];
		//Create a Pixel Vertex called temp used for each Pixel Vertex to be created
		PixelVertex temp;
		//Create a colour variable to hold the colour of each pixel in the array
		Color col;
		
		//Loop through imagePixels array and assign each index to a PixelVertex 
		//Using the same x,y coordinates and passing the colour to the vertex
		for(int i=0;i<imagePixels.length;i++){
			for(int j=0;j<imagePixels[i].length;j++){
				col = imagePixels[i][j];
				temp = new PixelVertex(i,j, col);
				graph[i][j] = temp;
			}
		}
		
		//Look through each vertex in the graph. If adjacent vertices are of the same colour
		//then add these vertices to the neighbour list for each PixelVertex
		for(int i=0;i<graph.length;i++){ //Row 
			for(int j=0;j<graph[i].length;j++){ //Column
					if(j<graph[i].length-1){ //If not last item in row then look forward
						if(graph[i][j].getCol().equals(graph[i][j+1].getCol())){
							graph[i][j].addNeighbour(graph[i][j+1]);
						}
					}
					if(j>0){ //If not first item in row then look back
						if(graph[i][j].getCol().equals(graph[i][j-1].getCol())){
							graph[i][j].addNeighbour(graph[i][j-1]);
						}
					}
					if(i<graph.length-1){ //If not last column then look down
						if(graph[i][j].getCol().equals(graph[i+1][j].getCol())){
							graph[i][j].addNeighbour(graph[i+1][j]);
						}
					}
					if(i>0){ //If not first column then look up
						if(graph[i][j].getCol().equals(graph[i-1][j].getCol())){
							graph[i][j].addNeighbour(graph[i-1][j]);
						}
					}					
			}
		}		
	}
	
	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object 
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the 
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){
		return graph[x][y];
	}
	
	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getWidth(){
		return graph.length;
	}
	
	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getHeight(){
		return graph[0].length;
	}
	
}