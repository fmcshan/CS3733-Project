package edu.wpi.teamname.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableCreator {
    Connection connection = null;
    //Statement statement = null;
     TableCreator() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        } catch (Exception e) {
            System.out.println("error registering the driver");
        }


        try{
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true;");
            System.out.println("Database myDB created");
        }
        catch(SQLException e){
            System.out.println("connection failed");
            e.printStackTrace();
            return;
        }
        try{Statement statement1 = connection.createStatement();
            System.out.println("statement established");
            dropNodeTable();
          //  statement1.execute(
              //      "CREATE TABLE navaaa(edge_ID varchar(45), xcoord integer, PRIMARY KEY(edge_ID))");


            statement1.execute("CREATE TABLE nodeTable(node_ID varchar(45) NOT NULL, x_coord integer NOT NULL, y_coord integer NOT NULL, floor varchar(45), building varchar(45),node_type varchar(45), long_name varchar(45), short_name varchar (45), PRIMARY KEY (node_ID))");
       // statement.execute("CREATE NODETABLE(NODEID VARCHAR(45) NOT NULL, XCOORD INTEGER NOT NULL, YCOORD INTEGER NOT NULL, FLOOR INTEGER NOT NULL , BUILDING VARCHAR(45), NODETYPE VARCHAR(45), LONGNAME VARCHAR(45), SHORTNAME VARCHAR(45), PRIMARY KEY (NODEID))");
            System.out.println("NODETABLE CREATED");
        }


        catch (SQLException e){
            System.out.println("error generating NODETABLE");
        }
    }
    int i =1;
    public void insertNodesIntoDatabase(){
        List<List<String>> allNodesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Nodes.csv");
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);


        for( List<String> singleNodeData : allNodesData){
           // System.out.println(singleNodeData);
          //  System.out.println(i + " "+singleNodeData.get(0));
        //    i++;
           try {
               Statement statement = connection.createStatement();
               statement.executeUpdate("INSERT INTO nodeTable (node_ID, x_coord, y_coord, floor, building, node_type, long_name,short_name) " +
                       "VALUES('"
                       +
                       singleNodeData.get(0)
                       + "',"
                       +singleNodeData.get(1)
                       +" ,"
                       + singleNodeData.get(2)
                       +",'"
                       + singleNodeData.get(3)
                       + "','"
                       +  singleNodeData.get(4)
                       +"','"
                       + singleNodeData.get(5)
                       +"','"
                       + singleNodeData.get(6)
                       +
                       "','"
                       + singleNodeData.get(7)+"')");


//               statement.execute("INSERT INTO navaaa (edge_ID, xcoord) " +
//                       "VALUES('"+ singleNodeData.get(0)+"'" +
//                       ",1023)");

           } catch (SQLException e){
               System.out.println("error inserting nodes into the database");
e.printStackTrace();
           }
        }
    }
    public void insertEdgesIntoDatabase(){
        List<List<String>> allEdgesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Edges.csv");
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        for( List<String> singleEdgeData : allEdgesData){
            try {Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO nodeTable (NODEID, XCOORD, YCOORD, FLOOR, BUILDING, NODETYPE, LONGNAME,SHORTNAME) " +
                        "VALUES('"
                        + singleEdgeData.get(0)
                        + "','"
                        + singleEdgeData.get(1)
                        +"','"
                        + singleEdgeData.get(2)+"')");

            } catch (SQLException e){
                System.out.println(" error inserting edges into the database");
            }
        }}
        public ArrayList<List<String>> retrieveNodesFromDatabase(){
            ArrayList<List<String>> nodes = new ArrayList<>();
            try{
                Statement statement = connection.createStatement();
                ResultSet nodesData = statement.executeQuery("SELECT * FROM nodeTable");
            while(nodesData.next()){
            List<String> node = new ArrayList<>();
            node.add(nodesData.getString("node_ID"));

            node.add(nodesData.getString("x_coord"));

            node.add(nodesData.getString("y_coord"));
            node.add(nodesData.getString("floor"));
            node.add(nodesData.getString("building"));
            node.add(nodesData.getString("node_type"));
            node.add(nodesData.getString("long_name"));
            node.add(nodesData.getString("short_name"));
               // System.out.println(node);

            nodes.add(node);
            } }
            catch (SQLException e){}
            return nodes;


    }
     public void dropNodeTable(){
         try{
             Statement temp = connection.createStatement();

             temp.execute("DROP TABLE nodeTable");
         }
         catch(SQLException s){}
     }
}