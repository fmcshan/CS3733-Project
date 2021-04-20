package edu.wpi.teamname.Database;

import edu.wpi.teamname.simplify.Requests;
import java.util.LinkedList;

public class AsynchronousQueue extends Thread {
    private static final AsynchronousQueue instance = new AsynchronousQueue();
    private LinkedList<AsynchronousTask> tasks = new LinkedList<AsynchronousTask>();
    private boolean processTasks = true;

    private AsynchronousQueue() {

    }

    public static synchronized AsynchronousQueue getInstance() {
        return instance;
    }

    public void handleRequests() {
        try {
            while (processTasks) {
                if (tasks.size() == 0) {
                    Thread.sleep(100);
                } else {
                    AsynchronousTask newTask = tasks.removeFirst();
                    System.out.println(newTask.get_url());
                    if (newTask.getRequestType().equals("GET")) {
                        Requests.get(newTask.get_url());
                    } else if (newTask.getRequestType().equals("POST")) {
                        if (newTask.getHeaders() == null) {
                            Requests.post(newTask.get_url());
                        } else {
                            Requests.post(newTask.get_url(), newTask.getHeaders());
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(AsynchronousTask task) {
        tasks.add(task);
    }

    public void stopProcessing() {
        processTasks = false;
    }

    public void run() {
        try {
            AsynchronousQueue.getInstance().handleRequests();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
