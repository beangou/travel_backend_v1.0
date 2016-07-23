package utils;

import play.api.mvc.ResponseHeader;
import play.http.HttpEntity;
import play.mvc.Result;

import java.util.*;

/**
 * Created by beangou on 16/7/23.
 */
public class ResultView extends Result {
    private Object data;
    private String responseCode;
    private String responseMsg;


    public ResultView(ResponseHeader header, HttpEntity body) {
        super(header, body);
    }

    public ResultView(int status, Optional<String> reasonPhrase, Map<String, String> headers, HttpEntity body) {
        super(status, reasonPhrase, headers, body);
    }

    public ResultView(int status, Map<String, String> headers, HttpEntity body) {
        super(status, headers, body);
    }

    public ResultView(int status, Map<String, String> headers) {
        super(status, headers);
    }

    public ResultView(int status, HttpEntity body) {
        super(status, body);
    }

    public ResultView(int status) {
        super(status);
    }

    public static ResultView getSuccessView(Object data) {
        ResultView view = new ResultView(200);
        view.setResponseCode("00");
        view.setResponseMsg("SUCCESS");
        view.setData(data);
        return view;
    }

    public static Map<String, Object> getFailureView(Object data, String code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("responseCode", code);
        resultMap.put("responseMsg", msg);
        resultMap.put("data", data);
        return resultMap;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
