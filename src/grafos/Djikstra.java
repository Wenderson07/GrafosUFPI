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
	
	public ArrayList<DjikstraListElement> nodeList;
	
	public Djikstra(Graph graph) {
		this.startDate = null;
		this.endDate = null;
		this.timeDifference = 0;
		this.originalGraph = graph;
		this.resultingGraph = null;
		this.nodeList = new ArrayList<DjikstraListElement>();
	}
	
	public DjikstraListElement Get(String key) {
		if (key == null || key.isEmpty()) {
			return null;
		}
		for (DjikstraListElement de : nodeList) {
			if (de.node.key == key) 
			{
				return de;
			}
		}
		return null;
	}
	
	public void Start(Node startingNode) {
		if (executed) {
			startDate = null;
			endDate = null;
			timeDifference = 0;
			resultingGraph = null;
			nodeList = new ArrayList<DjikstraListElement>();
		}
		executed = true;
		startDate = new Date();
		resultingGraph = new Graph();
		
		// Cria uma lista com as referências de todos os nós do grafo original
		for (Node n : originalGraph.nodeList) {
			DjikstraListElement de = new DjikstraListElement(n);
			nodeList.add(de);
		}
		// Inicia o processo a partir do nó inicial
		GetDistanceFromPoint(this.Get(startingNode.key));
		endDate = new Date();
	}
	
	public ArrayList<Node> GetVisitedNodeList() {
		ArrayList<Node> list = new ArrayList<Node>();
		for (DjikstraListElement de : nodeList) {
			if (de.visited) {
				list.add(de.node);
			}
		}
		return list;
	}
	
	public void GetDistanceFromPoint(DjikstraListElement startingNode) throws ValidationException, ConflictingNodeException {
		// Creates a local variable for storing the visiting period
		float time = 0;
		// Starts the process with the first node.
		startingNode.time = 0;
		startingNode.visited = true;
		resultingGraph.Add(startingNode.node);
		
		// Adds the rest of nodes
		while (GetVisitedNodeList().size() != nodeList.size()) {
			Entry<Node, Float> link = startingNode.node.getMinimalLinkForPrim(GetVisitedNodeList());
			if (link == null) {
				
			}
		}
	}
	
	
}
