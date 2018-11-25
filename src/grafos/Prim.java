package grafos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

public class Prim {
	// Controla a redefinição dos valores em caso de uma re-execução do algoritmo.
	public boolean executed = false;
	
	public Date startDate, endDate;
	public long timeDifference;
	
	public Graph originalGraph;
	public Graph resultingGraph;
	
	public ArrayList<Node> nodeList;
	
	public Prim(Graph graph) throws InvalidPrimGraphException {
		if (!Validate(graph)) {
			throw new InvalidPrimGraphException();
		}
		startDate = null;
		endDate = null;
		timeDifference = 0;
		originalGraph = graph;
		resultingGraph = null;
		nodeList = new ArrayList<Node>();
	}
	
	public static boolean Validate(Graph graph) {
		// Verifica se o gráfico é vazio
		if (graph.nodeList.isEmpty()) {
			return false;
		}
		// Verifica se o grafo é conectado
		for (Node n : graph.nodeList) {
			// Verifica se cada nó do grafo possui pelo menos uma conexão
			if (n.linkedNodes.isEmpty()) {
				return false;
			}
		}
		// Por padrão, a criação do link do nó do grafo já possui a especificação do peso (valoramento) da aresta. Não há necessidade de verificação.
		return true;
	}
	
	// Caso queira que a escolha do nó inicial seja feita automaticamente, defina um parâmetro nulo.
	public void Start(Node startingNode) throws ValidationException, ConflictingNodeException, LinkException {
		if (executed) {
			startDate = null;
			endDate = null;
			timeDifference = 0;
			resultingGraph = null;
			nodeList = new ArrayList<Node>();
		}
		executed = true;
		startDate = new Date();
		resultingGraph = new Graph();
		// Caso queira que a escolha do nó inicial seja feita automaticamente, defina um parâmetro nulo.
		try {
			GetMinimalSpanningTreePrim(startingNode);
		} catch (ValidationException | ConflictingNodeException | LinkException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		endDate = new Date();
	}
	
	// Recebe a árvore geradora mínima através do algoritmo de Prim
	public Graph OldGetMinimalSpanningTreePrim(Node startingNode) throws ValidationException, ConflictingNodeException, LinkException {
		// Recebe um nó qualquer...
		if (startingNode == null) {
			Random r = new Random();
			startingNode = originalGraph.nodeList.get(r.nextInt(originalGraph.nodeList.size()));
		}
		
		// Insere o primeiro nó no grafo final.
		try {
			resultingGraph.Add(startingNode);
		} catch (ValidationException | ConflictingNodeException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		Node n1 = resultingGraph.Get(startingNode.key);
		Entry<Node, Float> e1 = n1.getMinimalLink(nodeList);
		
		// "Recursivamente", executa o algoritmo de Prim.
		while (nodeList != originalGraph.nodeList || e1 != null) {
			// Insere o novo nó
			System.out.println("Inserting new node...");
			resultingGraph.Add(e1.getKey());
			// Recebe o parâmetro do novo nó
			System.out.println("Receiving new parameter...");
			Node n2 = resultingGraph.Get(e1.getKey().key);
			// Faz a conexão no grafo resultante
			System.out.println("Linking into new graph...");
			n1.link(n2, e1.getValue());
			
			try {
				// Realiza a busca pelo novo link de menor peso em n2
				System.out.println("Searching for new link within node list...");
				e1 = originalGraph.Get(n2.key).getMinimalLink(nodeList);
				// Ao falhar em encontrar, procurar a partir dos elementos já inseridos no novo grafo
				if (e1 == null) {
					System.out.println("Not found. Retrying with earlier node...");
					int count = nodeList.size();
					while (e1 == null) {
						count -= 1;
						if (count < 0) {
							throw new PrimEndException();
						}
						n2 = nodeList.get(count - 1);
						e1 = originalGraph.Get(n2.key).getMinimalLink(nodeList);
					}
				}
			} catch (PrimEndException pee) {
				System.out.println("End.");
				break;
			}
		}
		return resultingGraph;
	}
	
	public Graph GetMinimalSpanningTreePrim(Node startingNode) throws ValidationException, ConflictingNodeException, LinkException {
		// Recebe um nó qualquer.
		Node original_graph_node1 = startingNode;
		if (original_graph_node1 == null) {
			System.out.println("Getting Random Node...");
			original_graph_node1 = originalGraph.getRandomNode();
			System.out.println("Got node [" + original_graph_node1.key + "].");
		}
		// Insere esse nó no grafo e na lista de nós Prim.
		System.out.println("Inserting random node on Prim Visit List...");
		nodeList.add(original_graph_node1);		
		System.out.println("Node [" + original_graph_node1.key + "] inserted on Prim Visit List...");
		Node resulting_graph_node1 = original_graph_node1.Clone();
		resultingGraph.Add(resulting_graph_node1);
		System.out.println("Node [" + resulting_graph_node1.key + "] cloned and inserted on resulting graph.");
		
		System.out.println("[.] Entered onto loopzone.");
		while (nodeList.size() != originalGraph.nodeList.size()) {
			int listsize = nodeList.size();
			System.out.println("List size: " + listsize);
			for (int i = (listsize - 1); i >= 0; i--) {
//				for (Node n : resultingGraph.nodeList) {
//					System.out.print(n.key + " ");
//				}
//				System.out.println();
//				for (Node n : nodeList) {
//					System.out.print(n.key + " ");
//				}
//				System.out.println();
				
				System.out.println("Operating with node [" + nodeList.get(i).key + "], loop index " + i + "...");
				Entry<Node, Float> link = original_graph_node1.getMinimalLink(nodeList);
				if (link == null) {
					continue;
				} else {
					// Reference and Clone
					System.out.println("\tGot link: " + original_graph_node1.key + ", " + link.getKey().key + ", " + link.getValue());
					Node original_graph_node2 = link.getKey();
					Node resulting_graph_node2 = original_graph_node2.Clone();
					System.out.println("\tNode [" + resulting_graph_node2.key + "] cloned.");
					// Insert the node on the new graph
					System.out.println("\tInserting new node: [" + resulting_graph_node2.key + "] on " + resultingGraph.name);
					resultingGraph.Add(resulting_graph_node2);
					System.out.println("\tNode [" + resulting_graph_node2.key + "] cloned and inserted on resulting graph.");
					// Link them
					resulting_graph_node1.link(resulting_graph_node2, link.getValue());
					System.out.println("\tNode [" + resulting_graph_node1.key + "] linked with [" + resulting_graph_node2.key + "] with weight " + link.getValue() + ".");
					// Link them also in reverse Way
					resulting_graph_node2.link(resulting_graph_node1, link.getValue());
					System.out.println("\tNode [" + resulting_graph_node2.key + "] linked with [" + resulting_graph_node1.key + "] with weight " + link.getValue() + ".");
					
					// Check nodelist for add
					for (Node n : nodeList) {
						if (n.key == original_graph_node1.key) {
							original_graph_node1 = original_graph_node2;
							resulting_graph_node1 = original_graph_node1.Clone();
							break;
						}
					}
					nodeList.add(original_graph_node1);
					original_graph_node1 = original_graph_node2;
					resulting_graph_node1 = original_graph_node1.Clone();
					break;
					
					
				}
			}
		}
		
		return resultingGraph;
	}
}
