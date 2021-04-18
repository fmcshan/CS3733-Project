package edu.wpi.teamname.simplify;

import org.apache.http.Header;
import org.json.JSONObject;

public class Response {
    public int status_code;
    public Header[] headers;
    public String text;

    public Response(int status_code, Header[] headers, String text) {
        this.status_code = status_code;
        this.headers = headers;
        this.text = text;
    }

    public Response(int status_code, Header[] headers) {
        this.status_code = status_code;
        this.headers = headers;
    }

    public Response(int status_code) {
        this.status_code = 500;
    }

    public JSONObject json() {
        assert(this.text != null);
        return new JSONObject(this.text);
    }
}
