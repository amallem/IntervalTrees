package com.sourcegraph;

import com.sourcegraph.ds.Event;
import com.sourcegraph.ds.EventTree;

import java.util.List;

/**
 * Created by mallem on 9/25/16.
 * This class abstracts out the underlying data structure used to address the problem.
 */
public class Calendar {

    EventTree tree;
    public Calendar(){
        tree = new EventTree();
    }

    public void query(float time){
        List<Event> res =  tree.getOverlapEvents(Math.round(time));
        System.out.print("Query " + time + ":");
        for(Event e : res) {
            System.out.print(" " + e.getName());
        }
        System.out.println();
    }

    public void add(Event e){
        tree.insertEvent(e);
        System.out.println("ADD " + e.getName() + " " + e.getStartTime() + " " + e.getEndTime());
    }

    public void clear(){
        tree.clearTree();
        System.out.println("CLEAR");
    }
}
