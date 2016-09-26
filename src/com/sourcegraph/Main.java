package com.sourcegraph;

import com.sourcegraph.ds.Event;

import java.io.*;
import java.util.Scanner;

/**
 * Created by mallem on 9/25/16.
 */
public class Main {

    public static void main(String[] args) {
        // The name of the file to open.
        String fileName = args[0];

        // This will reference one line at a time
        String line;

        Calendar cal = new Calendar();

        try {
            Scanner input = new Scanner(new File(fileName));
            while(input.hasNextLine()) {
                line = input.nextLine();
                if(line.length() > 0) {
                    String[] vars = line.split(" ");
                    switch(vars[0]) {
                        case "ADD":
                            cal.add(new Event(vars[1], Integer.parseInt(vars[2]), Integer.parseInt(vars[3])));
                            break;
                        case "QUERY":
                            cal.query(Float.parseFloat(vars[1]));
                            break;
                        case "CLEAR":
                            cal.clear();
                            break;
                        default:
                            System.out.println("Unrecognized Command : " + vars[0]);
                            break;
                    }
                } else {
                    System.out.println();
                }

            }
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }

    }
}
