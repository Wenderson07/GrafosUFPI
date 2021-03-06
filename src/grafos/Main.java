package grafos;

import java.security.InvalidParameterException;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date d1 = new Date();
		Graph newGraph = GraphGenerator.FullConnectedGenerator(5, 20);
		Date d2 = new Date();
		newGraph.Print();
		Date d3 = new Date();

		System.out.println("Geração de Grafo Original");
		System.out.println("Generation time: " + (d2.getTime() - d1.getTime()) + " ms");
		System.out.println("Printing time: " + (d3.getTime() - d2.getTime()) + " ms");
		System.out.println("Full time: " + (d3.getTime() - d1.getTime()) + " ms");
		System.out.println("Geração de Grafo Prim");
//		try {
//			d1 = new Date();
//			Prim prim = new Prim(newGraph);
//			prim.Start(newGraph.nodeList.get(0));
//			d2 = new Date();
//
////			prim.resultingGraph.Print();
//
//			System.out.println("Geração de Grafo Prim");
//			System.out.println("Prim time: " + (d2.getTime() - d1.getTime()) + " ms");
//
////			prim.resultingGraph.Print();
//
//		} catch (InvalidPrimGraphException | ValidationException | ConflictingNodeException | LinkException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Djikstra dj = new Djikstra(newGraph);
		try {
			dj.Start();
		} catch (InvalidParameterException | ValidationException | ConflictingNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
}
