package com.example.marvelcharacters.AppUtils;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.marvelcharacters.MainActivity;

import org.json.JSONObject;

public class CustomWebService extends AQuery {

    private boolean serviceRuns;
    private MainActivity context;
    private CONSTANTS.SERVICE_TYPE service_type;
    private boolean canceled;
    private int timeOut;

    public CustomWebService(MainActivity context, CONSTANTS.SERVICE_TYPE service_type, int timeOut) {
        super(context);
        this.context = context;
        this.service_type = service_type;
        serviceRuns = false;
        this.timeOut = timeOut;

    }

    public void get(String url) {
        if (serviceRuns) {
            return;
        }
        canceled = false;
        WebServiceCallback callback = new WebServiceCallback();
        callback.timeout(timeOut);
        serviceRuns = true;
        this.ajax(url, JSONObject.class, callback);
    }

    private class WebServiceCallback extends AjaxCallback<JSONObject> {

        @Override
        public void callback(String url, JSONObject object, AjaxStatus status) {
            super.callback(url, object, status);
            serviceRuns = false;
            if (canceled) {
                canceled = false;
                return;
            }
            context.onCustomServiceResponse(status, object, service_type);
        }
    }

    @Override
    public AQuery ajaxCancel() {
        canceled = true;
        serviceRuns = false;
        return super.ajaxCancel();

    }
}
