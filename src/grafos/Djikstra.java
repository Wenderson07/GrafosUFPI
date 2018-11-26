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

	public void GetDistanceFromPoint(DjikstraListElement dj_originalgraph_node1)
			throws ValidationException, ConflictingNodeException, LinkException {
		// Creates a local variable for storing the visiting period
		float time = 0;
		// Starts the process with the first node.
		dj_originalgraph_node1.time = 0;
		dj_originalgraph_node1.visited = true;
		Node resultgraph_node1 = dj_originalgraph_node1.node.Clone();
		resultingGraph.Add(resultgraph_node1);
		visited_nodeList.add(dj_originalgraph_node1.node);

		// Adds the rest of nodes and link each of them
		while (GetVisitedNodeList().size() != djikstra_nodeList.size()) {
			for (Node n : visited_nodeList) {
				System.out.print("v.n.: " + n.key);
			}
			System.out.println();
			for (int i = (visited_nodeList.size() - 1); i >= 0; i--) {
				System.out.println("From: " + dj_originalgraph_node1.node.key);
				time = dj_originalgraph_node1.time;
				Entry<Node, Float> link_connection = dj_originalgraph_node1.node.getMinimalLinkForPrim(visited_nodeList);
				if (link_connection == null) {
					System.out.println("Got a null link");
					dj_originalgraph_node1 = Get(visited_nodeList.get(i).key);
					resultgraph_node1 = dj_originalgraph_node1.node.Clone();
					continue;
				} else {
					// Só crie a iteração. Se preocupe com os detalhes mais tarde.
					DjikstraListElement dj_originalgraph_node2 = this.Get(link_connection.getKey().key);
					Node resultgraph_node2 = dj_originalgraph_node2.node.Clone();
					resultingGraph.Add(resultgraph_node2);
					visited_nodeList.add(dj_originalgraph_node2.node);
			
					
					resultgraph_node1.link(resultgraph_node2, link_connection.getValue());
					
					dj_originalgraph_node1 = dj_originalgraph_node2;
					resultgraph_node1 = dj_originalgraph_node1.node.Clone();
				}

			}
		}
	}
}
