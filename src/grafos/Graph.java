package grafos;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;

public class Graph {
	public String name;
	public ArrayList<Node> nodeList;
	
	// Cria um grafo, cujo nome se refere a data em que foi criado.
	public Graph() {
		this.name = "Graph " + Date.from(Instant.now()).toString();
		nodeList = new ArrayList<Node>();
	}
	
	// Cria um grafo com o nome especificado.
	public Graph(String name) {
		this.name = name;
		nodeList = new ArrayList<Node>();
	}
	
	// Recebe o elemento na lista de nós
	public Node Get(String key) throws InvalidParameterException {
		// Validação da chave no parâmetro
		if (key == null || key.isEmpty()) {
			throw new InvalidParameterException();
		}
		// Busca do elemento na lista de nós
		for (Node n : nodeList) {
			if (key == n.key) {
				return n;
			}
		}
		// Retorna um elemento nulo caso não seja encontrado
		return null;
	}
	
	// Adiciona o elemento na lista de nós
	public void Add(Node n) throws ValidationException, ConflictingNodeException {
		// Validação do novo elemento
		if (!n.Validate()) {
			throw new ValidationException();
		}
		// Verificação de conflitos com o novo elemento
		for (Node node : nodeList) {
			if (node.key == n.key) {
				throw new ConflictingNodeException();
			}
		}
		
		nodeList.add(n);
	}
	
	// Cria um novo nó, cuja chave é o número de elementos da lista.
	public Node CreateEmptyNode() {
		Node n = new Node(String.valueOf(nodeList.size() + 1));
		return n;
	}
	
	public void Print() {
		if (nodeList.isEmpty()) {
			System.out.println("Empty Graph.");
		}
		for (Node n : nodeList) {
			System.out.print("[" + n.key + "]: ");
			if (n.linkedNodes.isEmpty()) {
				System.out.print("No connections.");
			}
			for (Entry<Node, Float> e : n.linkedNodes.entrySet()) {
				System.out.print("(" + e.getKey().key + ", " + e.getValue() + ");");
			}
			System.out.print('\n');
		}
	}
	
	public Node getRandomNode() {
		Node n;
		Random r = new Random();
		n = nodeList.get(r.nextInt(nodeList.size()));
		return n;
	}
}
