package com.apollo.training.set5.graph1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DirectionalWeightedGraphTests {
	DirectionalWeightedGraph dwg;
	String value1;
	int edgeValue;
	Node node1, node2, node3, node4, node5, node6;
	
	public void setup() {
		dwg = new DirectionalWeightedGraph();
		// add a node
		value1 = "A";
		dwg.addNode(value1);
		node1 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "B";
		dwg.addNode(value1);
		node2 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "C";
		dwg.addNode(value1);
		node3 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "D";
		dwg.addNode(value1);
		node4 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "E";
		dwg.addNode(value1);
		node5 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "F";
		dwg.addNode(value1);
		node6 = (Node) dwg.nodeList.getFirst();
		// connect Node A to Node B
		edgeValue = 15;
		dwg.connect(node1, node2, edgeValue);
		// connect Node A to Node D
		edgeValue = 7;
		dwg.connect(node1, node4, edgeValue);
		// connect Node A to Node C
		edgeValue = 8;
		dwg.connect(node1, node3, edgeValue);
		// connect Node C to Node B
		edgeValue = 10;
		dwg.connect(node3, node2, edgeValue);
		// connect Node D to Node B
		edgeValue = 9;
		dwg.connect(node4, node2, edgeValue);
		// connect Node B to Node E
		edgeValue = 21;
		dwg.connect(node2, node5, edgeValue);
		// connect Node C to Node E
		edgeValue = 19;
		dwg.connect(node3, node5, edgeValue);
		// connect Node D to Node E
		edgeValue = 20;
		dwg.connect(node4, node5, edgeValue);
		// connect Node C to Node F
		edgeValue = 25;
		dwg.connect(node3, node6, edgeValue);
		// connect Node E to Node F
		edgeValue = 27;
		dwg.connect(node5, node6, edgeValue);
	}
	
	@Test
	public void testAddNode() {
		dwg = new DirectionalWeightedGraph();
		// add a node
		value1 = "A";
		dwg.addNode(value1);
		node1 = (Node) dwg.nodeList.getFirst();
		String actual = node1.getNodeValue();
		assertEquals("A", actual);
		// add another node
		value1 = "B";
		dwg.addNode(value1);
		node2 = (Node) dwg.nodeList.getFirst();
		actual = node2.getNodeValue();
		assertEquals("B", actual);
	}

	@Test
	public void testConnectNodes() throws Exception {
		dwg = new DirectionalWeightedGraph();
		// add a node
		value1 = "A";
		dwg.addNode(value1);
		node1 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "B";
		dwg.addNode(value1);
		node2 = (Node) dwg.nodeList.getFirst();
		// connect two nodes
		edgeValue = 10;
		dwg.connect(node1, node2, edgeValue);
		Connection con1 = (Connection) dwg.connectionList.getFirst();
		assertEquals("A", con1.node1.getNodeValue());
		assertEquals("B", con1.node2.getNodeValue());
		assertEquals(10, con1.edgeValue);
	}

	@Test
	public void testListAllNodes() throws Exception {
		dwg = new DirectionalWeightedGraph();
		// add a node
		value1 = "A";
		dwg.addNode(value1);
		node1 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "B";
		dwg.addNode(value1);
		node2 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "C";
		dwg.addNode(value1);
		node3 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "D";
		dwg.addNode(value1);
		node4 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "E";
		dwg.addNode(value1);
		node5 = (Node) dwg.nodeList.getFirst();
		// add another node
		value1 = "F";
		dwg.addNode(value1);
		node6 = (Node) dwg.nodeList.getFirst();
		ArrayList<Node> allNodes = dwg.listAll();
		assertEquals(allNodes.get(0), node6);
		assertEquals(allNodes.get(1), node5);
		assertEquals(allNodes.get(2), node4);
		assertEquals(allNodes.get(3), node3);
		assertEquals(allNodes.get(4), node2);
		assertEquals(allNodes.get(5), node1);
	}

	@Test
	public void testListConnectedNodes() throws Exception {
		setup();
		
		ArrayList<Node> allNodes = dwg.listConnected(node2); // return connected
																// nodes
		// all connected nodes to Node B must be: Node D, Node C, Node A
		assertTrue(allNodes.contains(node4)); // Node D
		assertTrue(allNodes.contains(node3)); // Node C
		assertTrue(allNodes.contains(node1)); // Node A
	}
	
	@Test
	public void testGetMinimumTotalWeight() throws Exception {
		setup();
		
		int actual = dwg.getMinimumTotalWeight(node1, node6);
		assertEquals(33, actual);
	}
	
	@Test
	public void testGetMaximumTotalWeight() throws Exception {
		setup();
		
		int actual = dwg.getMaximumTotalWeight(node1, node6);
		assertEquals(66, actual);
	}
	
	@Test
	public void testIsUnreachable() throws Exception {
		setup();
		
		boolean actual = dwg.isUnreachable(node1, node6);
		assertEquals(false, actual);
	}
	
}
