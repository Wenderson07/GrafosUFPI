package grafos;

import java.awt.dnd.DnDConstants;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import grafos.Djikstra.DjikstraController.DjikstraNodeWrapper;

public class Djikstra {
	public boolean executed = false;
	public Date startDate = null, endDate = null;
	public Graph originalGraph = null, resultingGraph = null;
	
	public class DjikstraNode {
		public String key;
		public Node originalNode;
		public Node resultingNode;
		public boolean visited;
		public float time;
		
		public DjikstraNode(Node original) {
			this.key = original.key;
			this.originalNode = original;
			this.resultingNode = null;
			this.visited = false;
			this.time = Float.NaN;
		}
		
		public void Visit() {
			this.visited = true;
		}
		
		public void Clone() {
			return originalNode.Clone();
		}
	}
	
	ArrayList<DjikstraNode> visited_nodeList;
	
	public Djikstra() {
		
	}
}
