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
			getMinimalSpanningTreePrim(startingNode);
		} catch (ValidationException | ConflictingNodeException | LinkException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		endDate = new Date();
	}
	
	// Recebe a árvore geradora mínima através do algoritmo de Prim
	public Graph getMinimalSpanningTreePrim(Node startingNode) throws ValidationException, ConflictingNodeException, LinkException {
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
			resultingGraph.Add(e1.getKey());
			// Recebe o parâmetro do novo nó
			Node n2 = resultingGraph.Get(e1.getKey().key);
			// Faz a conexão no grafo resultante
			n1.link(n2, e1.getValue());
			
			try {
				// Realiza a busca pelo novo link de menor peso em n2
				e1 = originalGraph.Get(n2.key).getMinimalLink(nodeList);
				// Ao falhar em encontrar, procurar a partir dos elementos já inseridos no novo grafo
				if (e1 == null) {
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
				break;
			}
		}
		return resultingGraph;
	}
}
