package grafos;

import java.util.Map.Entry;

public class LinkException extends Exception {
	Node n1, n2;
	float weight;
	public LinkException(Node n1, Node n2, float weight) {
		this.n1 = n1;
		this.n2 = n2;
		this.weight = weight;
	}
	
	public LinkException(Node n1, Entry<Node, Float> e) {
		this.n1 = n1;
		this.n2 = e.getKey();
		this.weight = e.getValue();
	}
	
}
