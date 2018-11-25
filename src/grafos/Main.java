package grafos;

import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date d1 = new Date();
		Graph newGraph = GraphGenerator.FullConnectedGenerator(10000, 20);
		Date d2 = new Date();
		newGraph.Print();
		Date d3 = new Date();
		
		System.out.println("Generation time: " + (d2.getTime() - d1.getTime()) + " ms");
		System.out.println("Printing time: " + (d3.getTime() - d2.getTime()) + " ms");
		System.out.println("Full time: " + (d3.getTime() - d1.getTime()) + " ms");
		
	}
}
