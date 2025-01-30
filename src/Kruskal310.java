import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.awt.Color;

import javax.swing.JPanel;


/**
*  Simulation of Kruskal algorithm.
*  
*/
class Kruskal310 implements ThreeTenAlg {
	/**
	*  The graph the algorithm will run on.
	*/
	Graph<GraphNode, GraphEdge> graph;
	
	/**
	*  The priority queue of edges for the algorithm.
	*/
	WeissBST<GraphEdge> pqueue;
	
	/**
	*  The subgraph of the MST in construction.
	*/
	private Graph310 markedGraph;
	
	/**
	*  Whether or not the algorithm has been started.
	*/
	private boolean started = false;
	
	/**
	*  The color when a node has "no color".
	*/
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	
	/**
	*  The color when an edge has "no color".
	*/
	public static final Color COLOR_NONE_EDGE = Color.BLACK;
		
	/**
	*  The color when a node is inactive.
	*/
	public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;

	/**
	*  The color when an edge is inactive.
	*/
	public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;
	
	/**
	*  The color when a node is highlighted.
	*/
	public static final Color COLOR_HIGHLIGHT = new Color(255,204,51);
	
	/**
	*  The color when a node is in warning.
	*/
	public static final Color COLOR_WARNING = new Color(255,51,51);

	/**
	*  The color when a node/edge is selected and added to MST.
	*/
	public static final Color COLOR_SELECTED = Color.BLUE;
			
	/**
	*  {@inheritDoc}
	*/
	public EdgeType graphEdgeType() {
		return EdgeType.UNDIRECTED;
	}
	
	/**
	*  {@inheritDoc}
	*/
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		pqueue = null;	
		markedGraph = new Graph310();	
	}
	
	/**
	*  {@inheritDoc}
	*/
	public boolean isStarted() {
		return started;
	}
	
	/**
	*  {@inheritDoc}
	*/
	public void cleanUpLastStep() {
		// Unused. Required by the interface.		
	}
	
	
	/**
	*  {@inheritDoc}
	*/
	public void start() {
		started = true;
		Set310<GraphEdge> edges = (Set310<GraphEdge>)graph.getEdges();
		pqueue = new WeissBST<GraphEdge>();
		
		for (GraphEdge edge : edges) {
			pqueue.insert(edge);
		}
		
		highlightNext();
	}
	
	/**
	* Method that visually shows which edge the program is on and will be evaluating.
	*/
	public void highlightNext(){
		GraphEdge smallestEdge = pqueue.findMin();
		smallestEdge.setColor(COLOR_HIGHLIGHT);
		
	}

	/**
	*  {@inheritDoc}
	*/
	public void finish() {
		for (GraphEdge edge : pqueue.values()) {
			edge.setColor(COLOR_INACTIVE_EDGE);
		}
		
		Set310<GraphNode> nodes = (Set310<GraphNode>)graph.getVertices();
		for (GraphNode node : nodes) {
			if (node.getColor().equals(COLOR_NONE_NODE)) {
				node.setColor(COLOR_WARNING);
			}
		}
	}
	
	/**
	*  {@inheritDoc}
	*/
	public boolean setupNextStep() {
		if (pqueue.size() == 0) {
			return false;
		}
		return true;
		
	}
	
	/**
	*  {@inheritDoc}
	*/
	public void doNextStep() {
		GraphEdge minEdge = pqueue.findMin();
		pqueue.removeMin();
		
		Pair<GraphNode> connectingNodes = graph.getEndpoints(minEdge);
		GraphNode node1 = connectingNodes.getFirst();
		GraphNode node2 = connectingNodes.getSecond();
		if (graph.containsVertex(node1) && graph.containsVertex(node2)) {
			Set310<GraphNode> nodes = new Set310<GraphNode>();
			nodes.add(node1);
			if (checkCyclicalness(node1, node2, nodes)) {
				minEdge.setColor(COLOR_INACTIVE_EDGE);
			}
			else {
				markedGraph.addEdge(minEdge, node1, node2);
				minEdge.setColor(COLOR_SELECTED);
				node1.setColor(COLOR_SELECTED);
				node2.setColor(COLOR_SELECTED);
			}
		}
		
		if (setupNextStep()) {
			highlightNext();
		}
		else {
			finish();
		}
	}
	
	/**
	* Recursive helper method that checks if two nodes are reachable from the graph they're in.
	* @param currentNode The node the method is currently at 
	* @param unwantedNode The node the method is checking for 
	* @param visitedNodes A set of nodes that have previously been accessed
	* @return Whether the nodes can reach each other or not
	*/
	private boolean checkCyclicalness(GraphNode currentNode, GraphNode unwantedNode, Set310<GraphNode> visitedNodes) {
		boolean result = false;
		Set310<Boolean> booleans = new Set310<Boolean>(); 
		
		if (currentNode == unwantedNode) {
			return true;
		}
		
		for (GraphNode node : graph.getNeighbors(currentNode)) {
			if (graph.findEdge(currentNode, node).getColor().equals(COLOR_SELECTED) && !visitedNodes.contains(node)) {
				visitedNodes.add(node);
				booleans.add(checkCyclicalness(node, unwantedNode, visitedNodes));
			}
		}
		
		if (booleans.isEmpty()) {
			return false;
		}
		for (Boolean b : booleans) {
			result = result || b.booleanValue();
		}
		return result;
	}

	/**
	* Method that allows coders to test this classes code.
	* @param args Command line arguments given to the method
	*/
	public static void main(String[] args){

		Graph310 graph = new Graph310();
		Kruskal310 kruskal = new Kruskal310();
		
		GraphNode[] nodes = {
			new GraphNode(0), 
			new GraphNode(1)
		};

		GraphEdge[] edges = {
			new GraphEdge(0)
		};
		
		
		// a graph of two nodes, one edge
		graph.addVertex(nodes[0]);
		graph.addVertex(nodes[1]);
		graph.addEdge(edges[0], nodes[0], nodes[1]);
		
		kruskal.reset(graph);
		while (kruskal.step()) {} //execution of all steps

		
		if (nodes[0].getColor()==COLOR_SELECTED && nodes[1].getColor()==COLOR_SELECTED &&
			edges[0].getColor()==COLOR_SELECTED && kruskal.pqueue.size()==0){
			System.out.println("Yay1!");
		}
		
		//start over with a new graph
		graph = new Graph310();
		GraphNode[] nodes2 = {
			new GraphNode(0), 
			new GraphNode(1), 
			new GraphNode(2), 
			new GraphNode(3), 
			new GraphNode(4), 
			new GraphNode(5)
		};

		GraphEdge[] edges2 = {
			new GraphEdge(0,7), //id, weight
			new GraphEdge(1,1), 
			new GraphEdge(2,19), 
			new GraphEdge(3,3), 
			new GraphEdge(4,16), 
			new GraphEdge(5,2), 
			new GraphEdge(6,9)
		};
		
		
		graph.addVertex(nodes2[0]);
		graph.addVertex(nodes2[1]);
		graph.addVertex(nodes2[2]);
		graph.addVertex(nodes2[3]);
		graph.addVertex(nodes2[4]);
		graph.addVertex(nodes2[5]);

		graph.addEdge(edges2[0], nodes2[2], nodes2[0]); 
		graph.addEdge(edges2[1], nodes2[3], nodes2[1]); 
		graph.addEdge(edges2[2], nodes2[1], nodes2[5]); 
		graph.addEdge(edges2[3], nodes2[3], nodes2[2]); 
		graph.addEdge(edges2[4], nodes2[2], nodes2[5]); 
		graph.addEdge(edges2[5], nodes2[3], nodes2[0]); 
		graph.addEdge(edges2[6], nodes2[0], nodes2[5]); 

		kruskal.reset(graph);
		while (kruskal.step()) {} //execution of all steps

		//edges 1,3,5,6 selected, nodes 0,1,2,3,5 selected
		if (nodes2[4].getColor()==COLOR_WARNING && nodes2[0].getColor()==COLOR_SELECTED &&
			edges2[1].getColor()==COLOR_SELECTED && edges2[0].getColor()==COLOR_INACTIVE_EDGE){
			System.out.println("Yay2!");
		}	
	}

}