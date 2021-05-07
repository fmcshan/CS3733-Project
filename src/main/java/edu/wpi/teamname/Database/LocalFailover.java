package edu.wpi.teamname.Database;

import java.io.FileReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class LocalFailover {
    private static final LocalFailover instance = new LocalFailover();

    public static synchronized LocalFailover getInstance() {
        return instance;
    }

    private LocalFailover() {

    }

    private boolean failedOver = false;
    private JsonObject db;

    public boolean hasFailedOver() {
        return failedOver;
    }

    public void failOver() {
        System.out.println("Unable to establish connection to the rest API. Switching to local failover with reduced functionality.");
        System.out.println("** CREDENTIALS ARE NOT ENCRYPTED IN THE LOCAL FAILOVER DB **");
        this.failedOver = true;

        loadJson();

        System.out.println("==== LOCAL FAILOVER ====");
        System.out.println("Reduced functionality: ");
        System.out.println("   Authentication");
        System.out.println("   Chatbot");
        System.out.println("   Slightly degraded performance\n");
        System.out.println("Credentials: ");
        System.out.println("   Username: admin@admin.com");
        System.out.println("   Password: password\n");

    }

    // Load JSON
    private void loadJson() {
        try {
            FileReader jsonFile = new FileReader(getClass().getResource("/edu/wpi/teamname/failover.json").toURI().toString());
            JsonElement jsonElem = JsonParser.parseReader(jsonFile);
            this.db = jsonElem.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Parse nodes from JSON
    private void parseNodes() {
        System.out.println(db.get("nodes").getAsJsonArray());;
    }

    // Parse edges from JSON

    // Parse employees from JSON

    // Parse requests from JSON

    // Save arraylist of nodes to JSON

    // Save arraylist of edges to JSON

    // Save employees to JSON

    // Save requests to JSON

}
