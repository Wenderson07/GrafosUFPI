package grafos;

import java.util.Random;

public class GraphGenerator {
	public static Graph FullConnectedGenerator(int size, int max) {
		// Allocates.
		Graph result = new Graph();
		float[][] matrix = new float[size][size];
		
		// Create empty nodes
//		System.out.println("Creating nodes...");
		for (int i = 0; i < size; i++) {
//			System.out.println("Creating nodes... (" + (i+1) + "/" + size + ")");
			Node n = result.CreateEmptyNode();
			try {
				result.Add(n);
			} catch (ValidationException | ConflictingNodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("Created empty node [" + n.key + "].");
		}
		// Attribute connection weights
		for (int i = 0; i < size; i++) {
//			System.out.println("Attributing connection weights (" + (i+1) + "/" + size + ")");
			Node n = result.nodeList.get(i);
			for (int j = i; j < size; j++) {
//				System.out.println("Attributing connection weights from [" + n.key + "] (" + (j+1) + "/" + size + ")");
				if (i == j) {
					matrix [i][j] = 0;
					continue;
				} else {
					Random r = new Random();
					matrix [i][j] = r.nextFloat() % max;
					matrix [j][i] = matrix[i][j];
				}
			}
		}
		
		// Link
		for (int i = 0; i < size; i++) {			
			Node n = result.nodeList.get(i);
//			System.out.println("Linking from [" + n.key + "]... (" + (i+1) + "/" + size + ")");
			for (int j = i; j < size; j++) {
//				System.out.println("Operating link from [" + n.key + "]... (" + (j-i+1) + "/" + (size-i) + ")");
				if (i == j) {
//					System.out.println("\tSkipping operation [" + (i+1) +"] to [" + (j+1) + "]...");
					continue;
				}
				try {
//					System.out.println("\tGetting element reference from graph...: " + (j+1));
					Node m = result.nodeList.get(j);
					
//					System.out.print("\tLinking element...: ");
//					System.out.print((n.key) + ", ");
//					System.out.print((m.key) + ", ");
//					System.out.println((matrix[i][j]));
					n.link(m, matrix[i][j]);
//					System.out.println("\tLinking element...: " + (n.key) + ", " + (m.key) + ", " + (matrix[j][i]));
					m.link(n, matrix[j][i]);
//					System.out.println("\tSucessful!");
				} catch (Exception e) {
//					System.out.println("[!]\tAn error has ocurred here!");
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
