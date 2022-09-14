package com.rewaa.nearpay.capacitor;

import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.List;

import io.nearpay.sdk.Configuration;
import io.nearpay.sdk.Environments;
import io.nearpay.sdk.NearPay;
import io.nearpay.sdk.utils.enums.StatusCheck;
import io.nearpay.sdk.utils.listeners.CheckStatusListener;

@CapacitorPlugin(name = "RewaaNearpay")
public class RewaaNearpayPlugin extends Plugin {

    private RewaaNearpay implementation = new RewaaNearpay();
    private Context mContext;
    private String TAG = "RewaaNearpayPlugin";
    private NearPay nearPay;


    @Override
    public void load() {
        super.load();
        this.mContext = getContext();
        nearPay = new NearPay(
                mContext,
                Environments.SANDBOX,
                new Configuration(true, true, true));
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void checkStatus(PluginCall call) {
        nearPay.checkStatus(new CheckStatusListener() {
            @Override
            public void onStatusEvaluated(List<? extends StatusCheck> list) {
                // you will have to convert status to string status.toString(.
                // write your code here.
                call.resolve((JSObject) list);
            }
        });
    }
}
