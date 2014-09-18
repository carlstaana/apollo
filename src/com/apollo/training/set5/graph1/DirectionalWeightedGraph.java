package com.apollo.training.set5.graph1;

import java.util.ArrayList;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;

/**
 * The main class wherein the implementation of the interface is placed.
 * @author apollo/carlstaana
 *
 */
public class DirectionalWeightedGraph implements IntDirectionalWeightedGraph {
	LinkedList nodeList = new LinkedList();
	LinkedList connectionList = new LinkedList();

	public void addNode(String nodeValue) {
		// check if input is null
		if (nodeValue == null) {
			try {
				throw new NamingException("You have entered null");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		// check if the node already exists
		ListIterator iter = nodeList.listIterator();
		while (iter.hasNext()) {
			Node checkNode = (Node) iter.next();
			if (checkNode.getNodeValue().equalsIgnoreCase(nodeValue)) {
				try {
					throw new NameAlreadyBoundException(nodeValue + " already exists");
				} catch (NameAlreadyBoundException e) {
					e.printStackTrace();
				}
			}
		}
		// after passing validation
		Node node = new Node();
		node.setNodeValue(nodeValue);
		nodeList.addFirst(node);
	}

	public void connect(Node node1, Node node2, int edgeValue) {
		// check if inputs are null
		if (node1 == null || node2 == null || edgeValue == 0) {
			throw new NullPointerException("Input is NULL");
		}
		// check if node1 and node2 already exists (inverted)
		ListIterator iter = connectionList.listIterator();
		while (iter.hasNext()) {
			Connection con = (Connection) iter.next();
			if (con.node1.equals(node2) && con.node2.equals(node1)) {
				try {
					throw new NameAlreadyBoundException("The graph is only unidirectional");
				} catch (NameAlreadyBoundException e) {
					e.printStackTrace();
				}
			}
		}
		// after passing validation
		Connection con = new Connection();
		con.setConnection(node1, node2, edgeValue);
		connectionList.addFirst(con);
	}

	public ArrayList<Node> listAll() {
		ListIterator iter = nodeList.listIterator();
		ArrayList<Node> allNodes = new ArrayList<Node>();
		int count = 0;
		System.out.println("\nAll Nodes:");
		while (iter.hasNext()) {
			Node tempNode = (Node) iter.next();
			allNodes.add(tempNode);
			System.out.println("[" + count + "]" + " "
					+ tempNode.getNodeValue());
			count++;
		}
		return allNodes;
	}

	public ArrayList<Node> listConnected(Node node2) {
		ListIterator connIter = connectionList.listIterator();
		ArrayList<Node> connectedNodes = new ArrayList<Node>();
		int count = 0;
		System.out.println("\nAll nodes connected to Node "
				+ node2.getNodeValue());
		while (connIter.hasNext()) {
			Connection tempConn = (Connection) connIter.next();
			if (tempConn.node2.equals(node2)) {
				connectedNodes.add(tempConn.node1);
				System.out.println("[" + count + "]" + " "
						+ tempConn.node1.getNodeValue() + "\tEdge Value: "
						+ tempConn.edgeValue);
				count++;
			}
		}
		return connectedNodes;
	}

	public int getMinimumTotalWeight(Node node1, Node node2) {
		ListIterator connIter = connectionList.listIterator();
		ArrayList<Node> nodePath = new ArrayList<Node>();
		ArrayList<Integer> edgeValuePath = new ArrayList<Integer>();
		Node tempNode = node1;
		boolean gettingFirstMinimum = false;
		int minimum = 0;


		// get the possible paths
		while (connIter.hasNext()) {
			Connection tempConn = (Connection) connIter.next();
			if (tempNode.equals(tempConn.node1)) {
				// get the second node and its edge value and save into an
				// arraylist
				nodePath.add(tempConn.node2);
				edgeValuePath.add(tempConn.edgeValue);
			}
		}

		for (int i = 0; i < nodePath.size(); i++) {
			tempNode = nodePath.get(i);
			if (tempNode != node2) {
				connIter = connectionList.listIterator();
				while (connIter.hasNext()) {
					Connection tempConn = (Connection) connIter.next();
					if (tempNode.equals(tempConn.node1)) {
						if (node2.equals(tempConn.node2)) {
							nodePath.set(i, tempConn.node2);
							edgeValuePath.set(i, edgeValuePath.get(i)
									+ tempConn.edgeValue);
						} else {
							nodePath.add(tempConn.node2);
							edgeValuePath.add(tempConn.edgeValue);
						}
					}
				}
			}
		}

		for (int i = 0; i < nodePath.size(); i++) {
			if (nodePath.get(i).equals(node2)) {
				if (!gettingFirstMinimum) {
					minimum = edgeValuePath.get(i);
					gettingFirstMinimum = true;
				} else {
					if (edgeValuePath.get(i) < minimum) {
						minimum = edgeValuePath.get(i);
					}
				}
			}
		}

		return minimum;
	}

	public int getMaximumTotalWeight(Node node1, Node node2) {
		ListIterator connIter = connectionList.listIterator();
		ArrayList<Node> nodePath = new ArrayList<Node>();
		ArrayList<Integer> edgeValuePath = new ArrayList<Integer>();
		Node tempNode = node1;
		int tempEdge = 0;
		boolean gettingFirstMaximum = false;
		int maximum = 0;

		// get the possible paths
		while (connIter.hasNext()) {
			Connection tempConn = (Connection) connIter.next();
			if (tempNode.equals(tempConn.node1)) {
				// get the second node and its edge value and save into an
				// arraylist
				nodePath.add(tempConn.node2);
				edgeValuePath.add(tempConn.edgeValue);
			}
		}

		for (int i = 0; i < nodePath.size(); i++) {
			tempNode = nodePath.get(i);
			tempEdge = edgeValuePath.get(i);
			if (tempNode != node2) {
				connIter = connectionList.listIterator();
				while (connIter.hasNext()) {
					Connection tempConn = (Connection) connIter.next();
					if (tempNode.equals(tempConn.node1)) {
						if (node2.equals(tempConn.node2)) {
							nodePath.set(i, tempConn.node2);
							edgeValuePath.set(i, tempConn.edgeValue + tempEdge);
						} else {
							nodePath.add(tempConn.node2);
							edgeValuePath.add(tempConn.edgeValue + tempEdge);
						}
					}
				}
			}
		}

		for (int i = 0; i < nodePath.size(); i++) {
			if (nodePath.get(i).equals(node2)) {
				if (!gettingFirstMaximum) {
					maximum = edgeValuePath.get(i);
					gettingFirstMaximum = true;
				} else {
					if (edgeValuePath.get(i) > maximum) {
						maximum = edgeValuePath.get(i);
					}
				}
			}
		}

		return maximum;
	}

	public boolean isUnreachable(Node node1, Node node2) {
		ListIterator connIter = connectionList.listIterator();
		ArrayList<Node> nodePath = new ArrayList<Node>();
		Node tempNode = node1;
		
		// get the possible paths
		while (connIter.hasNext()) {
			Connection tempConn = (Connection) connIter.next();
			if (tempNode.equals(tempConn.node1)) {
				// get the second node and its edge value and save into an
				// arraylist
				if (node2.equals(tempConn.node2)) {
					return false;
				} else {
					nodePath.add(tempConn.node2);
				}
				
			}
			
			
		}
		
		for (int i = 0; i < nodePath.size(); i++) {
			connIter = connectionList.listIterator();
			tempNode = nodePath.get(i);
			while (connIter.hasNext()) {
				Connection tempConn = (Connection) connIter.next();
				if (tempNode.equals(tempConn.node1)) {
					nodePath.add(tempConn.node2);
				}
			}
		}

		if (nodePath.contains(node2)) {
			return false;
		} else {
			return true;
		}
		
	}

}
