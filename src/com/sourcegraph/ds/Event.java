package com.sourcegraph.ds;

/**
 * Created by mallem on 9/25/16.
 */
public class Event implements Comparable<Event>{

    private String name;

    private int startTime;

    private int endTime;

    public Event(String name, int startTime, int endTime){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName(){
        return this.name;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public int getEndTime(){
        return this.endTime;
    }

    public boolean contains(int time){
        return (startTime <= time && endTime > time);
    }

    @Override
    public int compareTo(Event event) {
        if(this.startTime < event.getStartTime()){
            return -1;
        }
        else if(this.startTime > event.getStartTime()){
            return 1;
        }
        else if(this.endTime < event.getEndTime()){
            return -1;
        }
        else if(this.endTime > event.getEndTime()){
            return 1;
        }
        else {
            return 0;
        }
    }
}
