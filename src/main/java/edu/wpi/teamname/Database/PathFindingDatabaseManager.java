package edu.wpi.teamname.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathFindingDatabaseManager {
    final String DB_URL = "jdbc:derby:BWDB;"; //

    Connection connection;
    //Statement statement = null;
    private static PathFindingDatabaseManager instance= null;
   private PathFindingDatabaseManager(){connection = null;};
    public static void startDB(String username, String password){
        if(instance == null){
            instance= new PathFindingDatabaseManager();
          instance.createDB();
          instance.accessDB(username, password);

        }
        instance.createTables();
        instance.insertNodesIntoDatabase();
        instance.insertEdgesIntoDatabase();









         }

      private void createDB(){

          try {
              Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

          } catch (Exception e) {
              System.out.println("error registering the driver");
          }

          try{

              connection = DriverManager.getConnection("jdbc:derby:BWDB;");
            //  System.out.println("Database myDB created");
          }
          catch(SQLException e){
              System.out.println("connection failed");
              try{
              connection = DriverManager.getConnection("jdbc:derby:BWDB;create=true");}
              catch(SQLException i){e.printStackTrace();
              return;}


          }
      }

    private void accessDB(String username, String password){
            // System.out.println("-------Embedded Apache Derby Connection Testing --------");
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException e) {
                return;
            }

            // System.out.println("Apache Derby driver registered!");
            try {
                String dbConnect = DB_URL + "user=" + username + ";password=" + password + ";";
                connection = DriverManager.getConnection(dbConnect);
            } catch (SQLException e) {
                System.out.println("error logging user with password");
            }
            // System.out.println("Apache Derby connection established!");
        }






        public static PathFindingDatabaseManager getInstance() {
         return instance;
        }
        public void createTables() {
            try {
                Statement statement = connection.createStatement();
                try {
                    System.out.println("statement established");

                    dropNodeTable();

                    //  statement1.execute(
                    //      "CREATE TABLE navaaa(edge_ID varchar(45), xcoord integer, PRIMARY KEY(edge_ID))");


                    statement.execute("CREATE TABLE nodeTable(node_ID varchar(45) NOT NULL, x_coord integer NOT NULL, y_coord integer NOT NULL, " +
                            "floor varchar(45), " +
                            "building varchar(45)," +
                            "node_type varchar(45), " +
                            "long_name varchar(45), " +
                            "short_name varchar (45), " +
                            "PRIMARY KEY (node_ID))");
                    // statement.execute("CREATE NODETABLE(NODEID VARCHAR(45) NOT NULL, XCOORD INTEGER NOT NULL, YCOORD INTEGER NOT NULL, FLOOR INTEGER NOT NULL , BUILDING VARCHAR(45), NODETYPE VARCHAR(45), LONGNAME VARCHAR(45), SHORTNAME VARCHAR(45), PRIMARY KEY (NODEID))");
                    // System.out.println("NODETABLE CREATED");
                } catch (SQLException e) {
                    System.out.println("error generating NODETABLE");
                }

                  dropEdgeTable();

                try {


                    statement.execute("CREATE TABLE EDGETABLE(edge_ID varchar(45) NOT NULL, " +
                            "starting_Node varchar(45) NOT NULL, " +
                            "finishing_Node varchar(45) NOT NULL, " +
                            "PRIMARY KEY (edge_ID))");


//            statement.execute("CREATE TABLE edgeTable(edge_ID varchar(45) NOT NULL, " +
//                    "starting_Node varchar(45) NOT NULL, " +
//                    "finishing_Node varchar(45) NOT NULL, " +
//                    "PRIMARY KEY (edge_ID))");
                    System.out.println("EDGETABLE CREATED");
                } // +"FOREIGN KEY (starting_Node) REFERENCES nodeTable(node_ID),"
                //+ "FOREIGN KEY (finishing_Node) REFERENCES nodeTable(node_ID))"
                catch (SQLException e) {
                    System.out.println("Error creating EDGETABLE");
                }
            }
            catch (SQLException e){
                System.out.println("error generating statement");
            }
        }

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
    public void insertEdgesIntoDatabase() {
        List<List<String>> allEdgesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Edges.csv");
        //System.out.println(allEdgesData);
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        for (List<String> singleEdgeData : allEdgesData) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO EDGETABLE (edge_ID, starting_Node, finishing_Node) " +
                        "VALUES('"
                        + singleEdgeData.get(0)
                        + "','"
                        + singleEdgeData.get(1)
                        + "','"
                        + singleEdgeData.get(2) + "')");
//                System.out.println(singleEdgeData.get(0));
//                System.out.println(singleEdgeData.get(1));
//                System.out.println(singleEdgeData.get(2));
                //System.out.println(node);
            //    System.out.println("edge inserted into Database");

            } catch (SQLException e) {
                System.out.println(" error inserting edges into the database");
            }
        }
    }

        public ArrayList<List<String>> retrieveNodesFromDatabase(){
            ArrayList<List<String>> nodes = new ArrayList<>();
            try{

                Statement statement = connection.createStatement();

                ResultSet nodesData = statement.executeQuery("SELECT * FROM nodeTable");
               //System.out.println("working!!!");
            while(nodesData.next()){
                System.out.println(nodesData.getString("node_ID")
                        +"\t"+ nodesData.getString("x_coord")
                        +"\t"+ nodesData.getString("y_coord")
                        +"\t"+ nodesData.getString("floor")
                        +"\t"+ nodesData.getString("building")
                        +"\t"+ nodesData.getString("node_type")
                        +"\t"+ nodesData.getString("long_name")
                        +"\t"+ nodesData.getString("short_name"));

                System.out.print(nodesData.getString("x_coord"));
//
//                System.out.print(nodesData.getString("y_coord"));
//                System.out.print(nodesData.getString("floor"));
//                System.out.print(nodesData.getString("building"));
//                System.out.print(nodesData.getString("node_type"));
//                System.out.print(nodesData.getString("long_name"));
//                System.out.print(nodesData.getString("short_name"));
               // System.out.println(node);


            } }
            catch (SQLException e){}
            return nodes;


    }

    public ArrayList<List<String>> retrieveEdgesFromDatabase(){
        ArrayList<List<String>> edges = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            System.out.println("statement created");
            ResultSet edgesData = statement.executeQuery("SELECT * FROM EDGETABLE");
            // ResultSet edgesData = statement2.executeQuery("SELECT * FROM edgeTable");
            System.out.println("query exeuted");
            while(edgesData.next()){
               // List<String> edge = new ArrayList<>();
                System.out.println(edgesData.getString("edge_ID")
                        +"\t"+ edgesData.getString("starting_Node")
                        +"\t" + edgesData.getString("finishing_Node"));
               // System.out.println("we got into the printing statement");

            } }
        catch (SQLException e){
            System.out.println("error accessing edges");
            e.printStackTrace();
        }
        return edges;


    }
     public void dropNodeTable(){
         try{
             Statement statement = connection.createStatement();

             statement.execute("DROP TABLE nodeTable");
         }
         catch(SQLException s){}
     }

    public void dropEdgeTable(){
        try{
            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE EDGETABLE");
        }
        catch(SQLException s){
            System.out.println("error dropping edgeTable");
        }
    }
}