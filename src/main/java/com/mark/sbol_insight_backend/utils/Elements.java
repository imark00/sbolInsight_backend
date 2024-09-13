package com.mark.sbol_insight_backend.utils;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Elements {
    private final List<Node> nodeList = new ArrayList<>();
    // private final List<Edge> edges = new ArrayList<>();

    private final List<HashMap<String, Object>> nodes = new ArrayList<>();
    private final List<HashMap<String, Object>> edges = new ArrayList<>();

    private int nodeCount = 0;
    private int edgeCount = 0;

    public void addNode(Object n, String nName) {
        Node node = new Node();
        HashMap<String, Object> map = new HashMap<>();

        if (n instanceof Resource data && !nodeExists(nName)) {
            node.setId("n" + ++nodeCount);
            node.setName(nName);
            node.setURI(data.toString());

            nodeList.add(node);
            // nodes.add(new Node(++nodeCount, nodeName));

            map.put("data", node);
            nodes.add(map);

        } else if (n instanceof RDFNode d && !nodeExists(nName)) {
            node.setId("n" + ++nodeCount);
            node.setName(nName);
            node.setURI(d.toString());

            nodeList.add(node);
            // nodes.add(new Node(++nodeCount, nodeName));

            map.put("data", node);
            nodes.add(map);
        }

    }

    public void addEdge(String edgeLabel, String sNodeName, String tNodeName) {
        HashMap<String, Object> map = new HashMap<>();
        
        String sourceNodeId = getNodeIdByName(sNodeName);
        String targetNodeId = getNodeIdByName(tNodeName);

        if (!Objects.equals(sourceNodeId, targetNodeId)) {
            Edge edge = new Edge();
            edge.setId("e" + ++edgeCount);
            edge.setLabel(edgeLabel);
            edge.setSource(sourceNodeId);
            edge.setTarget(targetNodeId);

            // edges.add(new Edge(++edgeCount, edgeLabel, sourceNodeId, targetNodeId));
            map.put("data", edge);
            edges.add(map);
        }

    }

    public String getNodeIdByName(String nodeName) {
        for (Node n : nodeList) {
            if (n.getName().equals(nodeName)) {
                return n.getId();
            }
        }
        return "";
    }

    public HashMap<String, Object> getElements() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("nodes", nodes);
        data.put("edges", edges);
        return data;
    }

//    public List
//
//     const elements = [
//    { data: { id: 'one', label: 'Node 1' }, position: { x: 0, y: 0 } },
//    { data: { id: 'two', label: 'Node 2' }, position: { x: 100, y: 0 } },
//    { data: { source: 'one', target: 'two', label: 'Edge from Node1 to Node2' } }
//    ];

    private boolean nodeExists(String name) {
        for (Node n : nodeList) {
            if (n.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
