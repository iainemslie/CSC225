/* A5Algorithms.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Image Algorithms


   B. Bird - 07/03/2017
*/ 

import java.awt.Color;
import java.util.*;

public class A3Algorithms{
	
	
	public static PixelVertex visited;
	public static PixelVertex neighbours[];
	public static PixelVertex temp;
	public static PixelVertex temp2;
	public static PixelVertex previous;
		
	/* FloodFillDFS(v, viewer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/ //Based on an example from http://bit.ly/2eYrQ0T
	public static void FloodFillDFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){
		Stack <PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);
		
		v.setVisited(true);
		while(S.isEmpty() == false){	
			temp = S.peek();
			S.pop();
			
			neighbours = temp.getNeighbours();
			viewer.setPixel(temp.getX(), temp.getY(), fillColour);
			for(int i = 0;i<temp.getDegree();i++){
				temp2 = neighbours[i];
				if(temp2.getVisited() == false){
					S.push(temp2);
					temp2.setVisited(true);
				}	
			}
		}
	}

	
	/* FloodFillBFS(v, viewer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){
		LinkedList<PixelVertex> Q = new LinkedList<PixelVertex>();	
		Q.addLast(v);
		
		v.setVisited(true);
		while(Q.isEmpty() == false){
			temp = Q.peekFirst();
			Q.removeFirst();
			
			neighbours = temp.getNeighbours();
			viewer.setPixel(temp.getX(), temp.getY(), fillColour);
			for(int i = 0;i<temp.getDegree();i++){
				temp2 = neighbours[i];
				if(temp2.getVisited() == false){
					temp2.setVisited(true);
					Q.addLast(temp2);
				}	
			}
		}
	}
	/* OutlineRegionDFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		Stack <PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);
		
		v.setVisited(true);
		while(S.isEmpty() == false){	
			temp = S.peek();
			S.pop();
			
			neighbours = temp.getNeighbours();
			if(temp.getDegree() < 4){
				viewer.setPixel(temp.getX(), temp.getY(), outlineColour);
			}
			for(int i = 0;i<temp.getDegree();i++){
				temp2 = neighbours[i];
				if(temp2.getVisited() == false){
					S.push(temp2);
					temp2.setVisited(true);
				}	
			}
		}
	}
	
	/* OutlineRegionBFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		LinkedList<PixelVertex> Q = new LinkedList<PixelVertex>();	
		Q.addLast(v);
		
		v.setVisited(true);
		while(Q.isEmpty() == false){
			temp = Q.peekFirst();
			Q.removeFirst();
			
			neighbours = temp.getNeighbours();
			if(temp.getDegree() < 4){
				viewer.setPixel(temp.getX(), temp.getY(), outlineColour);
			}
			for(int i = 0;i<temp.getDegree();i++){
				temp2 = neighbours[i];
				if(temp2.getVisited() == false){
					temp2.setVisited(true);
					Q.addLast(temp2);
				}	
			}
		}
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		/* Your code here */
		int componentCount = 0;
		
		for(int i=0;i<G.getWidth();i++){
			for(int j=0;j<G.getHeight();j++){
				temp = G.getPixelVertex(i,j);
				if(temp.getVisited() == false){
					DFS(temp);
					componentCount++;
				}
			}	
		}
		
		return componentCount;
	}
	
	public static void DFS(PixelVertex v){
		Stack <PixelVertex> S = new Stack<PixelVertex>();
		S.push(v);
		
		v.setVisited(true);
		while(S.isEmpty() == false){	
			temp = S.peek();
			S.pop();
			
			neighbours = temp.getNeighbours();
			for(int i = 0;i<temp.getDegree();i++){
				temp2 = neighbours[i];
				if(temp2.getVisited() == false){
					S.push(temp2);
					temp2.setVisited(true);
				}	
			}
		}
	}
}