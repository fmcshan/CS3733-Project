package edu.wpi.teamname.Database;

import javax.sound.midi.Soundbank;

public class LocalFailover {
    private static final LocalFailover instance = new LocalFailover();

    public static synchronized LocalFailover getInstance() {
        return instance;
    }

    private LocalFailover() {

    }

    private boolean failedOver = false;

    public boolean hasFailedOver() {
        return failedOver;
    }

    public void failOver() {
        System.out.println("Unable to establish connection to the rest API. Switching to local failover with reduced functionality.\n");

        System.out.println("==== LOCAL FAILOVER ====");
        System.out.println("Reduced functionality: ");
        System.out.println("   Authentication");
        System.out.println("   Chatbot");
        System.out.println("   Slightly degraded performance\n");
        System.out.println("Credentials: ");
        System.out.println("   Username: admin@admin.com");
        System.out.println("   Password: password\n");
        this.failedOver = true;
    }

    // Load JSON

    // Parse nodes from JSON

    // Parse edges from JSON

    // Parse employees from JSON

    // Parse requests from JSON

    // Save arraylist of nodes to JSON

    // Save arraylist of edges to JSON

    // Save employees to JSON

    // Save requests to JSON

}
