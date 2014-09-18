package com.apollo.training.set5.graph2;

import java.util.ArrayList;

public interface IntDirectionalWeightedGraph<N, E> {
	/**
	 * Adds a node to the graph.
	 * @param nodeValue the node to be added
	 */
	public void addNode(String nodeValue);
	/**
	 * Connects two nodes and input the weight of the edge.
	 * @param node1 the source node
	 * @param node2 the destination node
	 * @param edgeValue the weight of the edge
	 */
	public void connect(Node node1, Node node2, int edgeValue);
	/**
	 * List all the Nodes added in the graph.
	 * @return the ArrayList of nodes
	 */
	public ArrayList<Node> listAll();
	/**
	 * List all nodes directly connected to the specified node.
	 * @param node2 the node inputed
	 * @return the ArrayList of nodes connected to @param node2
	 */
	public ArrayList<Node> listConnected(Node node2);
	/**
	 * Gets the minimum total weight from source node to destination node
	 * @param node1 the source node
	 * @param node2 the destination node
	 * @return the total edge of the path from source node to destination node
	 */
	public int getMinimumTotalWeight(Node node1, Node node2);
	/**
	 * 
	 * Gets the maximum total weight from source node to destination node
	 * @param node1 the source node
	 * @param node2 the destination node
	 * @return the total edge of the path from source node to destination node
	 */
	public int getMaximumTotalWeight(Node node1, Node node2);
	/**
	 * Checks if the source node and the destination node inputed is unreachable
	 * @param node1 the source node
	 * @param node2 the destination node
	 * @return true is the path is unreachable
	 */
	public boolean isUnreachable(Node node1, Node node2);
}
