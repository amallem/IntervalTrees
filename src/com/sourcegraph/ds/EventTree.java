package com.sourcegraph.ds;

import java.util.ArrayList;
import java.util.List;
import com.sourcegraph.ds.EventTreeNode.NodeColor;
import com.sourcegraph.ds.EventTreeNode.NodeDirection;

/**
 * Created by mallem on 9/25/16.
 */
public class EventTree {

    // Generic nullNode
    public static EventTreeNode nullNode = new EventTreeNode();

    private EventTreeNode root;

    public EventTree() {
        root = nullNode;
        root.setLeft(nullNode);
        root.setRight(nullNode);
        root.setParent(nullNode);
    }

    public EventTreeNode getRoot() {
        return root;
    }

    public void setRoot(EventTreeNode root) {
        this.root = root;
    }

    /**
     * Publicly exposed func to get list of overlapping events for a given time point.
     * @param time
     * @return
     */
    public List<Event> getOverlapEvents(int time) {
        List<Event> result = new ArrayList<>();
        searchTree(root, time, result);
        return result;
    }

    /**
     * Helper function to search for overlapping intervals in tree
     * @param root
     * @param time
     * @param result
     */
    private void searchTree(EventTreeNode root, int time, List<Event> result) {
        if(root == nullNode || time > root.getMaxRight()){
            return;
        }

        if(root.getLeft() != nullNode){
            searchTree(root.getLeft(), time, result);
        }

        if(root.getEvent().contains(time)) {
            result.add(root.getEvent());
        }

        if(time < root.getEvent().getStartTime()) {
            return;
        }

        if(root.getRight() != nullNode) {
            searchTree(root.getRight(), time, result);
        }
    }

    /**
     * Publicly exposed function to insert event into the tree.
     * Uses the helper function below.
     * @param event
     */
    public void insertEvent(Event event) {
        if(root == nullNode) {
            root = new EventTreeNode(event);;
            root.setColor(NodeColor.BLACK);
        } else {
            treeInsert(event, root);
        }
    }

    /**
     * Helper function to insert event in the tree.
     * @param event : Event being inserted
     * @param currNode
     */
    private void treeInsert(Event event, EventTreeNode currNode) {
        EventTreeNode temp;
        if(event.compareTo(currNode.getEvent()) <= 0) {
            if(currNode.getLeft() == nullNode){
                temp = new EventTreeNode(event);
                temp.setParent(currNode);
                temp.setColor(NodeColor.RED);
                currNode.setLeft(temp);
            } else {
                treeInsert(event, currNode.getLeft());
                return;
            }
        } else {
            if(currNode.getRight() == nullNode) {
                temp = new EventTreeNode(event);
                temp.setParent(currNode);
                temp.setColor(NodeColor.RED);
                currNode.setRight(temp);
            } else {
                treeInsert(event, currNode.getRight());
                return;
            }
        }

        temp.getParent().renewMaxRight();
        renewTree(temp);
        root.setColor(NodeColor.BLACK);
    }

    /**
     * Function to Rebalance Tree starting from @param curNode
     * @param curNode
     */
    private void renewTree(EventTreeNode curNode) {
        if(curNode.getParent() == nullNode || curNode.getParent().getColor() == NodeColor.BLACK) {
            return;
        }

        EventTreeNode uncle = curNode.getUncle();
        if(uncle != nullNode && uncle.getColor() == NodeColor.RED) {
            curNode.getParent().setColor(NodeColor.BLACK);
            uncle.setColor(NodeColor.BLACK);

            EventTreeNode grandpa = curNode.getGrandPa();
            if(grandpa != nullNode && !grandpa.isRoot()) {
                grandpa.setColor(NodeColor.RED);
                renewTree(grandpa);
            }
        } else {
            if(curNode.getParentDirection() == NodeDirection.LEFT
                    && curNode.getParent().getParentDirection() == NodeDirection.RIGHT) {
                rotateLeft(curNode.getParent());
                curNode = curNode.getLeft();
            } else if (curNode.getParentDirection() == NodeDirection.RIGHT
                    && curNode.getParent().getParentDirection() == NodeDirection.LEFT) {
                rotateRight(curNode.getParent());
                curNode = curNode.getRight();
            }
            curNode.getParent().setColor(NodeColor.BLACK);

            EventTreeNode grandpa = curNode.getGrandPa();
            if(grandpa == nullNode) {
                return;
            }
            grandpa.setColor(NodeColor.RED);

            if(curNode.getParentDirection() == NodeDirection.RIGHT) {
                rotateRight(grandpa);
            } else {
                rotateLeft(grandpa);
            }
        }
    }

    private void rotateRight(EventTreeNode curNode) {
        EventTreeNode base = curNode.getLeft();
        EventTreeNode parent = curNode.getParent();
        NodeDirection direction = curNode.getParentDirection();
        EventTreeNode tempTree = base.getRight();
        base.setRight(curNode);
        curNode.setParent(base);
        curNode.setLeft(tempTree);

        if(tempTree != nullNode){
            tempTree.setParent(curNode);
        }

        if(direction == NodeDirection.LEFT) {
            parent.setRight(base);
        } else if(direction == NodeDirection.RIGHT) {
            parent.setLeft(base);
        } else {
            root = base;
        }

        base.setParent(parent);
        base.renewMaxRight();
        curNode.renewMaxRight();
    }

    private void rotateLeft(EventTreeNode curNode) {
        EventTreeNode base = curNode.getRight();
        EventTreeNode parent = curNode.getParent();
        NodeDirection direction = curNode.getParentDirection();
        EventTreeNode tempTree = base.getLeft();
        base.setLeft(curNode);
        curNode.setParent(base);
        curNode.setRight(tempTree);

        if(tempTree != nullNode){
            tempTree.setParent(curNode);
        }

        if(direction == NodeDirection.LEFT) {
            parent.setRight(base);
        } else if(direction == NodeDirection.RIGHT) {
            parent.setLeft(base);
        } else {
            root = base;
        }

        base.setParent(parent);
        base.renewMaxRight();
        curNode.renewMaxRight();
    }

    /**
     * Resets root to point to nullNode.
     */
    public void clearTree(){
        root = nullNode;
        root.setLeft(nullNode);
        root.setRight(nullNode);
        root.setParent(nullNode);
    }

}
