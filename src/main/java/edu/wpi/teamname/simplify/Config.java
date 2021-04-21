package edu.wpi.teamname.simplify;

public class Config {
    private static final Config instance = new Config();

    private String environment = "production";

    private Config() {

    }

    public static synchronized Config getInstance() {
        return instance;
    }

    /*
    Environments:
        debug
        staging
        production
     */
    public void setEnv(String _env) {
        this.environment = _env;
    }

    public String getServerUrl() {
        switch (this.environment.toLowerCase()) {
            case "dev":
                return "http://localhost:8000";
            case "staging":
                return "https://software-eng-3733-staging-znhzj.ondigitalocean.app";
            default: // Prod
                return "https://software-eng-3733-prod-zk2pg.ondigitalocean.app";
        }
    }

    public String getSocketUrl() {
        switch (this.environment.toLowerCase()) {
            case "dev":
                return "ws://localhost:8000";
            case "staging":
                return "wss://software-eng-3733-staging-znhzj.ondigitalocean.app";
            default: // Prod
                return "wss://software-eng-3733-prod-zk2pg.ondigitalocean.app";
        }
    }

}