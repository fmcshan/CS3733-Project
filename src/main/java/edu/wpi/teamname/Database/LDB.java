package edu.wpi.teamname.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LDB {
    public static void main(String args[]){
        List<String> commandLineInput = new ArrayList<>();
        for (int i = 0; i< args.length ; i++) {
            commandLineInput.add(args[i]);
            //System.out.println(commandLineInput);
        }
        int menuInput =0;
        if(args.length<2|| args.length >3){
            System.out.println("please run the program with at most 3 values. enter the first value as the username. The second as the password and the third as the menu option");
            System.exit(0);
        }

        if (args.length >2) {
            menuInput= Integer.parseInt(commandLineInput.get(2));
          //  System.out.println("This is the menu Input: "+menuInput);
        }
       // System.out.println(commandLineInput.get(0));
        //System.out.println(commandLineInput.get(2));

        PathFindingDatabaseManager.startDB(commandLineInput.get(0), commandLineInput.get(1));

        Scanner in = new Scanner(System.in);
        String nodeID = "";

        switch (menuInput) {
            case 0:
                printOptions();
                System.exit(0);
                return;
            case 1:
                PathFindingDatabaseManager.getInstance().retrieveNodesFromDatabase();
                return;
            case 2:
                System.out.println("enter node ID");
                nodeID = in.nextLine();
                System.out.println("New x-coordinate: ");
                String newXCoord = in.nextLine();
                System.out.println("New y-coordinate: ");
                String newYCoord = in.nextLine();
                return;
            case 3:
                System.out.println("enter node ID");
                nodeID = in.nextLine();
                System.out.println("New longName: ");
                String newLongName = in.nextLine();

                return;
            case 4:
                // display edges;
                PathFindingDatabaseManager.getInstance().retrieveEdgesFromDatabase();

            case 5:
                System.exit(0);
                return;
            default:
                System.exit(0);
                return;

        }


    }
    public static void printOptions()
    {
        System.out.println();
        System.out.println("--------------------");
        System.out.println();
        System.out.println(
                "1 \u002D Node Information\n"
                        + "2 \u002D Update Node Coordinate\n"
                        + "3 \u002D Update Node Location LongName\n"
                        + "4 \u002D Edge Information"
                        + "5 \u002D Exit Program");
        System.out.println();
        System.out.println("--------------------");
    }
}



