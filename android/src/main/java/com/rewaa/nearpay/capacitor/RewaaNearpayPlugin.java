package com.rewaa.nearpay.capacitor;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.List;
import java.util.Locale;

import io.nearpay.sdk.Environments;
import io.nearpay.sdk.NearPay;
import io.nearpay.sdk.data.models.TransactionReceipt;
import io.nearpay.sdk.utils.enums.PurchaseFailure;
import io.nearpay.sdk.utils.enums.StatusCheckError;
import io.nearpay.sdk.utils.listeners.PurchaseListener;

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
  }

  @PluginMethod
  public void echo(PluginCall call) {
    String value = call.getString("value");

    JSObject ret = new JSObject();
    ret.put("value", implementation.echo(value));
    call.resolve(ret);
  }

 @PluginMethod
 public void initPayment(PluginCall call) {
  String jwt = call.getString("token");
     JSObject ret = new JSObject();
     if (!TextUtils.isEmpty(jwt)) {
         nearPay = new NearPay(mContext, jwt, Locale.getDefault(), Environments.SANDBOX);
         ret.put("status", true);
     } else {
         ret.put("status", false);
     }
     call.resolve(ret);
  }

    @PluginMethod
    public void purchase(PluginCall call) {
        Integer amount = call.getInt("amount");
        String crn = call.getString("crn");
        Boolean isReceiptUI = call.getBoolean("receiptUI");

        nearPay.purchase(amount, crn, isReceiptUI, new PurchaseListener() {
            @Override
            public void onPurchaseApproved(@Nullable TransactionReceipt transactionReceipt) {
                Log.i("onPurchaseApproved",transactionReceipt.toString());
                Log.i("getStatus_message",transactionReceipt.getStatus_message().toString());
                JSObject ret = new JSObject();
                ret.put("paymentStatus", true);
                ret.put("crn", transactionReceipt.getPayment_account_reference());
                ret.put("status_msg", transactionReceipt.getStatus_message());
                ret.put("tid", transactionReceipt.getTid());
                ret.put("is_approved", transactionReceipt.is_approved());
                call.resolve(ret);
            }

            @Override
            public void onPurchaseFailed(@NonNull PurchaseFailure purchaseFailure) {
                Log.e("onPurchaseFailed",purchaseFailure.toString());
                String type = "";
                if (purchaseFailure instanceof PurchaseFailure.GeneralFailure) {
                    Log.e("purchaseFailure","GeneralFailure");
                    type = "GeneralFailure";
                }
                else if (purchaseFailure instanceof PurchaseFailure.PurchaseDeclined){
                    Log.e("purchaseFailure","PurchaseDeclined");
                    type = "PurchaseDeclined";
                }
                else if (purchaseFailure instanceof PurchaseFailure.PurchaseRejected){
                    Log.e("purchaseFailure","PurchaseRejected");
                    type = "PurchaseRejected";
                }
                else if (purchaseFailure instanceof PurchaseFailure.AuthenticationFailed){
                    Log.e("purchaseFailure","AuthenticationFailed");
                    type = "AuthenticationFailed";
                }
                else if (purchaseFailure instanceof PurchaseFailure.InvalidStatus){
                    // you can get the status using the following code
                    List<StatusCheckError> status = ((PurchaseFailure.InvalidStatus) purchaseFailure).getStatus();
                    Log.e("purchaseFailure","InvalidStatus "+status.toString());
                    type = "InvalidStatus";
                }
                JSObject ret = new JSObject();
                ret.put("paymentStatus", false);
                ret.put("crn", crn);
                ret.put("error_type", type);
                call.resolve(ret);
            }
        });
    }

  public void sendEvent(JSObject data) {
    Log.e("percent", String.valueOf(data));
    notifyListeners("downloadProgressChange", data);
  }

}
