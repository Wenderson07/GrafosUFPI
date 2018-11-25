package grafos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Node {
	public String key;
	public String data;
	public LinkedHashMap<Node, Float> linkedNodes;
		
	public Node(String key) {
		// O construtor já garante que a criação do objeto atenda os padrões de validação
		this.key = key;
		data = "Random graph data generated at " + Date.from(Instant.now()).toString() + " within following random content: " + (String.valueOf(Date.from(Instant.now()).getTime()).hashCode());
		linkedNodes = new LinkedHashMap<>();
	}
	
	public Node(String key, String data) {
		// O segundo construtor permite a inserção de um texto específico no campo de dados.
		this.key = key;
		this.data = data;
		linkedNodes = new LinkedHashMap<>();
	}
	
	public boolean Validate() {
		// Valida a chave.
		if (this == null || this.key == null) {
			return false;
		}
		return true;
	}
	
	public void link(Node NodeToLink, float weight) throws LinkException {
		// Garante que o link não seja invalido.
		if (!this.Validate() || !NodeToLink.Validate() || weight < 0) {
			System.out.println("\t\t[!] Link Validation error!");
			throw new LinkException(this, NodeToLink, weight);
		}
		// Garante que o link não ocorra com si mesmo.
		if (NodeToLink == this || this.key == NodeToLink.key) {
			System.out.println("\t\t[!] Link Duplication error!");
			throw new LinkException(this, NodeToLink, weight);
		}
		// Garante que o link já não se repita na lista dos nós conectados.
		for (Node n : linkedNodes.keySet()) {
			if (n.key == NodeToLink.key) {
				System.out.println("\t\t[!] Link Conflicting Error!");
				throw new LinkException(this, NodeToLink, weight);
			}
		}
		
		// Insere o link, após todas as verificações.
//		System.out.println("\t\t[.] Link inserted.");
		linkedNodes.put(NodeToLink, weight);
	}
	
	// Recebe um Link, mas evitando valor em que o link possua um nó com a chave especificada
	public Entry<Node, Float> getMinimalLink(String key) {
		Entry<Node, Float> min = null;
		float minweight = Float.MAX_VALUE;
		
		for (Entry<Node, Float> e : linkedNodes.entrySet()) {
			// Verifica se o nó é o não-desejado
			if (e.getKey().key == key) {
				continue;
			} else {
				if (e.getValue() < minweight) {
					// Atualiza o valor do link de menor peso
					minweight = e.getValue();
					min = e;
				}
			}
		}
		
		return min;
	}
	
	// Recebe um Link, mas evitando valores em que o link possua um nó com a chave especificada através de um parâmetro de ArrayList
		public Entry<Node, Float> getMinimalLink(ArrayList<Node> array) {
			Entry<Node, Float> min = null;
			float minweight = Float.MAX_VALUE;
			
			for (Entry<Node, Float> e : linkedNodes.entrySet()) {
				// Verifica se o nó é o não-desejado
				try {
					for (Node n : array) {
						if (e.getKey().key == n.key) {
							throw new ConflictingNodeException();
						}
					}
				} catch (ConflictingNodeException cne) {
					continue;
				}
				// Atualiza o valor do link de menor peso
				if (e.getValue() < minweight) {
					minweight = e.getValue();
					min = e;
				}
			}
			
			return min;
		}
		
		public Node Clone() {
			Node a = new Node(this.key);
			a.data = this.data;
			return a;
		}
}
