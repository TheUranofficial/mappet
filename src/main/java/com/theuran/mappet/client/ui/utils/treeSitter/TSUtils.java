package com.theuran.mappet.client.ui.utils.treeSitter;

import org.treesitter.TSNode;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//public class TSUtils {
//    private static final List<TSNode> nodes = new ArrayList<>();
//
//    public static TSNode strict2NearestFindNodeByType(TSNode node, int row, int column, TSNodeType nodeType) {
//        List<TSNode> nodes = TSUtils.findNodes(node, row, column);
//
//        if (nodes.isEmpty()) {
//            return null;
//        }
//
//        TSNode nearest = nodes.get(0);
//
//        if (!nearest.getType().equalsIgnoreCase(nodeType.getName())) {
//            nearest = null;
//        }
//
//        int minDistance = Integer.MAX_VALUE;
//
//        for (TSNode TSNode : nodes) {
//            int startRow = TSNode.getStartPoint().getRow();
//            int startColumn = TSNode.getStartPoint().getColumn();
//
//            int distance = Math.abs(startRow - row) + Math.abs(startColumn - column);
//
//            if (distance < minDistance && containsStrict2(TSNode, row, column) && TSNode.getType().equalsIgnoreCase(nodeType.getName())) {
//                minDistance = distance;
//                nearest = TSNode;
//            }
//        }
//
//        return nearest;
//    }
//
//    public static TSNode strictNearestFindNodeByType(TSNode node, int row, int column, TSNodeType nodeType) {
//        List<TSNode> nodes = TSUtils.findNodes(node, row, column);
//
//        if (nodes.isEmpty()) {
//            return null;
//        }
//
//        TSNode nearest = nodes.get(0);
//
//        if (!nearest.getType().equalsIgnoreCase(nodeType.getName())) {
//            nearest = null;
//        }
//
//        int minDistance = Integer.MAX_VALUE;
//
//        for (TSNode TSNode : nodes) {
//            int startRow = TSNode.getStartPoint().getRow();
//            int startColumn = TSNode.getStartPoint().getColumn();
//
//            int distance = Math.abs(startRow - row) + Math.abs(startColumn - column);
//
//            if (distance < minDistance && containsStrict(TSNode, row, column) && TSNode.getType().equalsIgnoreCase(nodeType.getName())) {
//                minDistance = distance;
//                nearest = TSNode;
//            }
//        }
//
//        return nearest;
//    }
//
//    public static TSNode nearestFindNodeByType(TSNode node, int row, int column, TSNodeType nodeType) {
//        List<TSNode> nodes = TSUtils.findNodes(node, row, column);
//
//        if (nodes.isEmpty()) {
//            return null;
//        }
//
//        TSNode nearest = nodes.get(0);
//
//        if (!nearest.getType().equalsIgnoreCase(nodeType.getName())) {
//            nearest = null;
//        }
//
//        int minDistance = Integer.MAX_VALUE;
//
//        for (TSNode TSNode : nodes) {
//            int startRow = TSNode.getStartPoint().getRow();
//            int startColumn = TSNode.getStartPoint().getColumn();
//
//            int distance = Math.abs(startRow - row) + Math.abs(startColumn - column);
//
//            if (distance < minDistance && contains(TSNode, row, column) && TSNode.getType().equalsIgnoreCase(nodeType.getName())) {
//                minDistance = distance;
//                nearest = TSNode;
//            }
//        }
//
//        return nearest;
//    }
//
//    public static List<TSNode> findNodesByType(TSNode node, int row, int column, TSNodeType nodeType) {
//        nodes.clear();
//        findNodeRecursiveByType(node, row, column, nodeType);
//        return nodes;
//    }
//
//    private static void findNodeRecursiveByType(TSNode node, int row, int column, TSNodeType nodeType) {
//        if (contains(node, row, column) && node.getType().equalsIgnoreCase(nodeType.getName())) {
//            nodes.add(node);
//        }
//
//        for (int i = 0; i < node.getChildCount(); i++) {
//            TSNode child = node.getChild(i);
//            findNodeRecursiveByType(child, row, column, nodeType);
//        }
//    }
//
//    public static List<TSNode> findNodes(TSNode node, int row, int column) {
//        nodes.clear();
//        findNodeRecursive(node, row, column);
//        return nodes;
//    }
//
//    private static void findNodeRecursive(TSNode node, int row, int column) {
//        if (contains(node, row, column)) {
//            nodes.add(node);
//        }
//
//        for (int i = 0; i < node.getChildCount(); i++) {
//            TSNode child = node.getChild(i);
//            findNodeRecursive(child, row, column);
//        }
//    }
//
//    public static String getIdentifierText(String text, TSNode identifier) {
//        int start = identifier.getStartByte();
//        int end = identifier.getEndByte();
//
//        if (start < 0 || end > text.getBytes(StandardCharsets.UTF_8).length) {
//            return null;
//        }
//
//        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
//        return new String(bytes, start, end - start, StandardCharsets.UTF_8);
//    }
//
//    private static boolean contains(TSNode node, int row, int column) {
//        int sr = node.getStartPoint().getRow();
//        int sc = node.getStartPoint().getColumn();
//        int er = node.getEndPoint().getRow();
//        int ec = node.getEndPoint().getColumn();
//
//        if (row >= sr || row < er) return true;
//        if (column >= sc || column < ec) return true;
//
//        return false;
//    }
//
//    private static boolean containsStrict(TSNode node, int row, int column) {
//        int er = node.getEndPoint().getRow();
//        int ec = node.getEndPoint().getColumn();
//        int sc = node.getStartPoint().getColumn();
//
//        if (row != er) return false;
//        if (column < sc) return false;
//
//        return true;
//    }
//
//    private static boolean containsStrict2(TSNode node, int row, int column) {
//        int er = node.getEndPoint().getRow();
//        int ec = node.getEndPoint().getColumn();
//        int sc = node.getStartPoint().getColumn();
//
//        if (row != er) return false;
//        if (column < sc && column > ec) return false;
//
//        return true;
//    }
//}
