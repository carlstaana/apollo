package com.apollo.training.set5.graph1;

/**
 * Class that holds the name/value of the Node.
 * @author apollo/carlstaana
 *
 */
public class Node extends DirectionalWeightedGraph {
	private String nodeValue;
	
	/**
	 * Gets the node value of the Node class.
	 * @return the nodeValue
	 */
	public String getNodeValue() {
		return nodeValue;
	}

	/**
	 * Replaces the nodeValue to a new nodeValue.
	 * @param nodeValue the value to be replaced
	 */
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

}
