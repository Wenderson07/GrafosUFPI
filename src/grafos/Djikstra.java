package grafos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

public class Djikstra {
	public class DjikstraListElement {
		public Node node;
		public float time;
		public boolean visited = false;

		public DjikstraListElement(Node n) {
			this.node = n;
			this.visited = false;
			time = Float.NaN;
		}
	}

	public boolean executed = false;

	public Date startDate, endDate;
	public long timeDifference;

	public Graph originalGraph;
	public Graph resultingGraph;

	public ArrayList<DjikstraListElement> djikstra_nodeList;
	public ArrayList<Node> visited_nodeList;

	public Djikstra(Graph graph) {
		this.startDate = null;
		this.endDate = null;
		this.timeDifference = 0;
		this.originalGraph = graph;
		this.resultingGraph = null;
		this.djikstra_nodeList = new ArrayList<DjikstraListElement>();
		this.visited_nodeList = new ArrayList<Node>();
	}

	public DjikstraListElement Get(String key) {
		if (key == null || key.isEmpty()) {
			return null;
		}
		for (DjikstraListElement de : djikstra_nodeList) {
			if (de.node.key == key) {
				return de;
			}
		}
		return null;
	}

	public void Start(Node startingNode) throws ValidationException, ConflictingNodeException, LinkException {
		if (executed) {
			startDate = null;
			endDate = null;
			timeDifference = 0;
			resultingGraph = null;
			djikstra_nodeList = new ArrayList<DjikstraListElement>();
		}
		executed = true;
		startDate = new Date();
		resultingGraph = new Graph();

		// Cria uma lista com as referências de todos os nós do grafo original
		for (Node n : originalGraph.nodeList) {
			DjikstraListElement de = new DjikstraListElement(n);
			djikstra_nodeList.add(de);
		}
		// Inicia o processo a partir do nó inicial
		GetDistanceFromPoint(this.Get(startingNode.key));
		endDate = new Date();
	}

	public ArrayList<Node> GetVisitedNodeList() {
		return visited_nodeList;
	}

	public void GetDistanceFromPoint(DjikstraListElement startingNode)
			throws ValidationException, ConflictingNodeException, LinkException {
		// Creates a local variable for storing the visiting period
		float time = 0;
		// Starts the process with the first node.
		startingNode.time = 0;
		startingNode.visited = true;
		Node resultgraph_node1 = startingNode.node.Clone();
		resultingGraph.Add(resultgraph_node1);
		visited_nodeList.add(startingNode.node);

		// Adds the rest of nodes and link each of them
		while (GetVisitedNodeList().size() != djikstra_nodeList.size()) {
			for (int i = (visited_nodeList.size() - 1); i >= 0; i--) {
				Entry<Node, Float> newlink = startingNode.node.getMinimalLinkForPrim(GetVisitedNodeList());
				if (newlink == null) {
					// Decreases one step on the list.
					continue;
				} else {
					System.out.println("Got link: [" + resultgraph_node1.key + " , " + newlink.getKey().key + "]: " + newlink.getValue());
					// Gets the new node and clones it
					Node resultgraph_node2 = newlink.getKey().Clone();
					resultingGraph.Add(resultgraph_node2);
					System.out.println("\tInserted [" + newlink.getKey().key + "] on the resulting graph.");

					// Increases the local time of variable with link value and gets the time of
					// actual path
					DjikstraListElement e1 = Get(resultgraph_node1.key);
					time = e1.time;
					// E2 stores the new node reference
					DjikstraListElement e2 = Get(resultgraph_node2.key);
					e2.time = time += newlink.getValue();

					// Inserts the new node into the result graph, and links it with the previous
					// node
					System.out.print ("\tLinking...: [" + resultgraph_node1.key + ", " + resultgraph_node2.key + "]: " + newlink.getValue());
					resultgraph_node1.link(resultgraph_node2, newlink.getValue());
					System.out.println(" -- linked!");

					// Updates the cursor
					startingNode = Get(resultgraph_node2.key);
					startingNode.time = time;
					startingNode.visited = true;
					visited_nodeList.add(startingNode.node);
					resultgraph_node1 = startingNode.node.Clone();
					
					
				}
			}
		}
	}

}
