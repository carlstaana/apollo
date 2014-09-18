package com.apollo.training.set5.graph1;

/**
 * Class where the connection of two nodes and their edgeValue are stored.
 * @author apollo/carlstaana
 */
public class Connection extends DirectionalWeightedGraph {
	public Node node1;
	public Node node2;
	public int edgeValue;
	
	/**
	 * Sets the variables of the Connection class.
	 * @param node1 the source node
	 * @param node2 the node wherein node1 is pointed
	 * @param edgeValue the weight of the edge
	 */
	public void setConnection(Node node1, Node node2, int edgeValue) {
		this.node1 = node1;
		this.node2 = node2;
		this.edgeValue = edgeValue;
	}

}
