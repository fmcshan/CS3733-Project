package edu.wpi.teamname.Database;

import org.json.JSONObject;

public class AsynchronousTask {
    private String _url;
    private JSONObject headers;
    private String requestType;

    public AsynchronousTask(String _url, String requestType) {
        this._url = _url;
        this.requestType = requestType;
    }

    public AsynchronousTask(String _url, JSONObject headers, String requestType) {
        this._url = _url;
        this.headers = headers;
        this.requestType = requestType;
    }


    public String get_url() {
        return _url;
    }

    public JSONObject getHeaders() {
        return headers;
    }

    public String getRequestType() {
        return requestType;
    }
}
