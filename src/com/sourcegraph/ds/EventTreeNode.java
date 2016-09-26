package com.sourcegraph.ds;

/**
 * Created by mallem on 9/25/16.
 */
public class EventTreeNode {

    private EventTreeNode left;

    private EventTreeNode right;

    private EventTreeNode parent;

    private int maxRight;

    private Event event;

    private NodeColor color;

    public EventTreeNode(){
        left = right = parent = EventTree.nullNode;
        color = NodeColor.BLACK;
    }

    public EventTreeNode(Event event){
        left = right = parent = EventTree.nullNode;
        color = NodeColor.BLACK;
        maxRight = event.getEndTime();
        this.event = event;
    }

    public boolean isRoot(){
        return (parent == EventTree.nullNode);
    }

    public EventTreeNode getInorderSuccessor(){
        if(right == EventTree.nullNode) {
            return EventTree.nullNode;
        } else {
            EventTreeNode temp = right;
            while(temp.left != EventTree.nullNode){
                temp = temp.left;
            }
            return temp;
        }
    }

    public EventTreeNode getUncle() {
        EventTreeNode grandpa = getGrandPa();

        if(grandpa != EventTree.nullNode) {
            if(parent == grandpa.getLeft()) {
                return grandpa.getRight();
            } else {
                return grandpa.getLeft();
            }
        }

        return EventTree.nullNode;
    }

    public EventTreeNode getGrandPa() {
        if(parent != EventTree.nullNode) {
            return parent.getParent();
        }

        return EventTree.nullNode;
    }

    public NodeDirection getParentDirection(){
        if(parent == EventTree.nullNode){
            return NodeDirection.NONE;
        }
        return (parent.getLeft() == this)?NodeDirection.RIGHT:NodeDirection.LEFT;
    }

    public void renewMaxRight() {
        int max = event.getEndTime();

        if(right != EventTree.nullNode) {
            if(right.getMaxRight() > max) {
                max = right.getMaxRight();
            }
        }

        if(left != EventTree.nullNode) {
            if(left.getMaxRight() > max) {
                max = left.getMaxRight();
            }
        }

        maxRight = max;

        if(parent != EventTree.nullNode) {
            parent.renewMaxRight();
        }
    }

    public void setLeft(EventTreeNode left){
        this.left = left;
    }

    public void setRight(EventTreeNode right){
        this.right = right;
    }

    public void setParent(EventTreeNode parent){
        this.parent = parent;
    }

    public void setMaxRight(int maxRight){
        this.maxRight = maxRight;
    }

    public void setColor(NodeColor color) {
        this.color = color;
    }

    public NodeColor getColor() {
        return color;
    }

    public Event getEvent() {
        return event;
    }

    public EventTreeNode getLeft(){
        return this.left;
    }

    public EventTreeNode getRight(){
        return this.right;
    }

    public EventTreeNode getParent(){
        return this.parent;
    }

    public int getMaxRight(){
        return this.maxRight;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public enum NodeColor {
        RED, BLACK
    }

    public enum NodeDirection {
        LEFT,RIGHT,NONE
    }
}
