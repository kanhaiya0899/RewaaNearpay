package com.rewaa.nearpay.capacitor;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.nearpay.sdk.Environments;
import io.nearpay.sdk.NearPay;
import io.nearpay.sdk.data.models.ReconciliationReceipt;
import io.nearpay.sdk.data.models.TransactionReceipt;
import io.nearpay.sdk.utils.ReceiptUtilsKt;
import io.nearpay.sdk.utils.enums.AuthenticationData;
import io.nearpay.sdk.utils.enums.GetDataFailure;
import io.nearpay.sdk.utils.enums.LogoutFailure;
import io.nearpay.sdk.utils.enums.NetworkConfiguration;
import io.nearpay.sdk.utils.enums.PurchaseFailure;
import io.nearpay.sdk.utils.enums.ReconcileFailure;
import io.nearpay.sdk.utils.enums.RefundFailure;
import io.nearpay.sdk.utils.enums.ReversalFailure;
import io.nearpay.sdk.utils.enums.SetupFailure;
import io.nearpay.sdk.utils.enums.StatusCheckError;
import io.nearpay.sdk.utils.enums.TransactionData;
import io.nearpay.sdk.utils.listeners.BitmapListener;
import io.nearpay.sdk.utils.listeners.GetTransactionListener;
import io.nearpay.sdk.utils.listeners.LogoutListener;
import io.nearpay.sdk.utils.listeners.PurchaseListener;
import io.nearpay.sdk.utils.listeners.ReconcileListener;
import io.nearpay.sdk.utils.listeners.RefundListener;
import io.nearpay.sdk.utils.listeners.ReversalListener;
import io.nearpay.sdk.utils.listeners.SetupListener;

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
  public void initNearpay(PluginCall call) {
    String jwt = call.getString("token");
    boolean isProd = call.getBoolean("isProd");
    AuthenticationData jwtData =  new AuthenticationData.Jwt(jwt);
    JSObject ret = new JSObject();
    if (!TextUtils.isEmpty(jwt)) {
      if (isProd) {
        nearPay = new NearPay.Builder()
          .context(mContext)
          .authenticationData(jwtData)
          .environment(Environments.PRODUCTION)
          .locale(Locale.getDefault())
          .networkConfiguration(NetworkConfiguration.DEFAULT)
          .build();
      } else {
        nearPay = new NearPay.Builder()
          .context(mContext)
          .authenticationData(jwtData)
          .environment(Environments.SANDBOX)
          .locale(Locale.getDefault())
          .networkConfiguration(NetworkConfiguration.DEFAULT)
          .build();
      }
      if(nearPay != null)
        ret.put("status", true);
      else
        ret.put("status", false);
    } else {
      ret.put("status", false);
    }
    call.resolve(ret);
  }


  @PluginMethod
  public void setupNearpay(PluginCall call) {
    String jwt = call.getString("token");
    nearPay.setup(new SetupListener() {
      @Override
      public void onSetupCompleted() {
        Log.i("onSetupCompleted","setup is done successfully");
        JSObject ret = new JSObject();
        ret.put("isSetupComplete", true);
        ret.put("status_msg", "setup is done successfully");
        call.resolve(ret);
      }

      @Override
      public void onSetupFailed(@NonNull SetupFailure setupFailure) {
        String type = "InvalidStatus";
        if(setupFailure!=null){
          int onSetupFailed = Log.e("onSetupFailed", String.valueOf(setupFailure));
          if (setupFailure instanceof SetupFailure.AlreadyInstalled) {
            // when the payment plugin is already installed  .
            type = "AlreadyInstalled";
          }
          else if (setupFailure instanceof SetupFailure.NotInstalled){
            // when the installtion failed .
            type = "NotInstalled";
          }
          else if (setupFailure instanceof SetupFailure.AuthenticationFailed){
            // when the Authentication Failed.
            type = "AuthenticationFailed";
            nearPay.updateAuthentication(new AuthenticationData.Jwt(jwt));
          }
        }

        JSObject ret = new JSObject();
        ret.put("isSetupComplete", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void purchase(PluginCall call) {
    Integer amount = call.getInt("amount");
    Boolean enableReceiptUi = false;
    Boolean enableReversal = false;
    Boolean enableUiDismiss = true;
    UUID transactionId = UUID.fromString(call.getString("transactionUUID"));
    Long amt = new Long(amount);
    String crn = call.getString("crn");
    String jwt = call.getString("token");
    Long finishTimeOut = 2L;

    nearPay.purchase(amt, crn, enableReceiptUi, enableReversal, finishTimeOut, transactionId, enableUiDismiss, new PurchaseListener() {
      @Override
      public void onPurchaseApproved(@NonNull TransactionData transactionData) {
        JSObject ret = new JSObject();
        if(transactionData!=null && transactionData.getReceipts()!=null && transactionData.getReceipts().size()>0 && transactionData.getReceipts().get(0)!=null){
            TransactionReceipt transactionReceipt = transactionData.getReceipts().get(0);
            ret.put("paymentStatus", true);
            ret.put("crn", transactionReceipt.getPayment_account_reference());
            ret.put("reference_retrieval", transactionReceipt.getRetrieval_reference_number());
            ret.put("uuid", transactionReceipt.getTransaction_uuid());
            ret.put("status_msg", transactionReceipt.getStatus_message());
            ret.put("tid", transactionReceipt.getTid());
            ret.put("is_approved", transactionReceipt.is_approved());
            ret.put("purchaseReceipt", transactionReceipt.getQr_code());
            call.resolve(ret);
        }else{
          ret.put("paymentStatus", false);
          ret.put("error_type", "NoDataFound");
          call.resolve();
        }

      }

      @Override
      public void onPurchaseFailed(@NonNull PurchaseFailure purchaseFailure) {
        String type = "InvalidStatus";
        if(purchaseFailure!=null){
          if (purchaseFailure instanceof PurchaseFailure.GeneralFailure) {
            Log.e("purchaseFailure","GeneralFailure");
            type = "GeneralFailure";
          } else if (purchaseFailure instanceof PurchaseFailure.PurchaseDeclined){
            Log.e("purchaseFailure","PurchaseDeclined");
            type = "PurchaseDeclined";
          } else if (purchaseFailure instanceof PurchaseFailure.PurchaseRejected){
            Log.e("purchaseFailure","PurchaseRejected");
            type = "PurchaseRejected";
          } else if (purchaseFailure instanceof PurchaseFailure.AuthenticationFailed){
            Log.e("purchaseFailure","AuthenticationFailed");
            type = "AuthenticationFailed";
            nearPay.updateAuthentication(new AuthenticationData.Jwt(jwt));
          } else if (purchaseFailure instanceof PurchaseFailure.InvalidStatus){
            // you can get the status using the following code
            if(((PurchaseFailure.InvalidStatus) purchaseFailure).getStatus()!=null){
              List<StatusCheckError> status = ((PurchaseFailure.InvalidStatus) purchaseFailure).getStatus();
              Log.e("purchaseFailure","InvalidStatus "+status.toString());
            }
          }
        }
        JSObject ret = new JSObject();
        ret.put("paymentStatus", false);
        ret.put("crn", crn);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void reconcile(PluginCall call) {
    Boolean enableReceiptUi = false;
    String jwt = call.getString("token");
    Long finishTimeOut = 2L; // Add the number of seconds
    String adminPin = "0000"; //[optional] when you add the admin pin here , the UI for admin pin won't be shown.
    Boolean enableUiDismiss = true ;// [optional] it will allow you to control dismissing the UI
    UUID reconcileId = UUID.fromString(call.getString("reconcileId"));

    nearPay.reconcile(reconcileId, enableReceiptUi, adminPin, finishTimeOut,enableUiDismiss, new ReconcileListener() {
      @Override
      public void onReconcileFinished(@Nullable ReconciliationReceipt reconciliationReceipt) {
        if(reconciliationReceipt!=null){
          ReceiptUtilsKt.toImage(reconciliationReceipt, mContext, 512, 14, new BitmapListener() {
            @Override
            public void result(@Nullable Bitmap bitmap) {
              ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
              bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
              byte[] byteArray = byteArrayOutputStream .toByteArray();
              String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
              JSObject ret = new JSObject();
              ret.put("reconcileStatus", true);
              ret.put("reconciliationReceipt", reconciliationReceipt.getQr_code());
              ret.put("base64", encoded);
              call.resolve(ret);
            }
          });
        }else{
          JSObject ret = new JSObject();
          ret.put("reconcileStatus", false);
          ret.put("error_type", "NoDataFound");
          call.resolve(ret);
        }
      }

      @Override
      public void onReconcileFailed(@NonNull ReconcileFailure reconcileFailure) {
        Log.e("reconcileFailure",reconcileFailure.toString());
        String type = "";
        if (reconcileFailure instanceof ReconcileFailure.FailureMessage) {
          Log.e("reconcileFailure","GeneralFailure");
          type = "GeneralFailure";
        } else if (reconcileFailure instanceof ReconcileFailure.AuthenticationFailed){
          Log.e("reconcileFailure","AuthenticationFailed");
          type = "AuthenticationFailed";
          nearPay.updateAuthentication(new AuthenticationData.Jwt(jwt));
        } else if (reconcileFailure instanceof ReconcileFailure.InvalidStatus){
          Log.e("reconcileFailure","InvalidStatus");
          type = "InvalidStatus";
        } else if (reconcileFailure instanceof ReconcileFailure.GeneralFailure){
          Log.e("reconcileFailure","GeneralFailure");
          type = "GeneralFailure";
        }
        JSObject ret = new JSObject();
        ret.put("reconcileStatus", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void refund(PluginCall call) {
    Integer amount = call.getInt("amount");
    String jwt = call.getString("token");
    String transactionReferenceRetrievalNumber = call.getString("transactionReferenceRetrievalNumber");
    Integer customerReferenceNumber = call.getInt("customerReferenceNumber");
    Boolean enableReceiptUi = false;

    Boolean enableReversal = /*call.getBoolean("enableReversal")*/false; // it will allow you to enable or disable the reverse button
    Boolean enableEditableRefundAmountUi = /*call.getBoolean("enableEditableRefundAmountUi")*/false; // [optional] true will enable the ui and false will disable
    Long finishTimeOut = 2L; // Add the number of seconds

    UUID transactionId = /*UUID.fromString("Transaction Id")*/java.util.UUID.randomUUID(); // [optional] You can add your UUID here which allows you to ask about the transaction again using the same UUID
    String adminPin = "0000"; //[optional] when you add the admin pin here , the UI for admin pin won't be shown.
    Boolean enableUiDismiss = /*call.getBoolean("enableUiDismiss")*/true;// [optional] it will allow you to control dismissing the UI

    nearPay.refund(amount, transactionReferenceRetrievalNumber, String.valueOf(customerReferenceNumber), enableReceiptUi, enableReversal, enableEditableRefundAmountUi, finishTimeOut, transactionId, adminPin, enableUiDismiss, new RefundListener() {
      @Override
      public void onRefundApproved(@NonNull TransactionData transactionData) {
        JSObject ret = new JSObject();
        ret.put("refundStatus", true);
        ret.put("refundReceipt", transactionData.getReceipts().get(0).getQr_code());
        call.resolve(ret);
      }

      @Override
      public void onRefundFailed(@NonNull RefundFailure refundFailure) {
        Log.e("RefundFailure", refundFailure.toString());
        String type = "";
        if (refundFailure instanceof RefundFailure.RefundDeclined) {
          Log.e("refundFailure", "RefundDeclined");
          type = "RefundDeclined";
        } else if (refundFailure instanceof RefundFailure.RefundRejected) {
          Log.e("refundFailure", "RefundRejected");
          type = "RefundRejected";
        } else if (refundFailure instanceof RefundFailure.AuthenticationFailed) {
          Log.e("refundFailure", "AuthenticationFailed");
          type = "AuthenticationFailed";
          nearPay.updateAuthentication(new AuthenticationData.Jwt(jwt));
        } else if (refundFailure instanceof RefundFailure.GeneralFailure) {
          Log.e("refundFailure", "GeneralFailure");
          type = "GeneralFailure";
        } else if (refundFailure instanceof RefundFailure.InvalidStatus) {
          Log.e("refundFailure", "InvalidStatus");
          type = "InvalidStatus";
        }
        JSObject ret = new JSObject();
        ret.put("refundStatus", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void reverse(PluginCall call) {
    String transactionUuid = call.getString("transactionUuid");
    Boolean enableReceiptUi = false;
    String jwt = call.getString("token");
    Long finishTimeOut = 60L; // Add the number of seconds
    Boolean enableUiDismiss = true ;// [optional] it will allow you to control dismissing the UI
    nearPay.reverse(transactionUuid,  enableReceiptUi, finishTimeOut,enableUiDismiss, new ReversalListener() {
      @Override
      public void onReversalFinished(@NonNull TransactionData transactionData) {
        JSObject ret = new JSObject();
        ret.put("reverseStatus", true);
        call.resolve(ret);
      }

      @Override
      public void onReversalFailed(@NonNull ReversalFailure reversalFailure) {
        String type = "InvalidStatus";
        if(reversalFailure!=null){
          Log.e("reversalFailure", reversalFailure.toString());
          if (reversalFailure instanceof ReversalFailure.AuthenticationFailed) {
            Log.e("reversalFailure", "AuthenticationFailed");
            type = "AuthenticationFailed";
            nearPay.updateAuthentication(new AuthenticationData.Jwt(jwt));
          } else if (reversalFailure instanceof ReversalFailure.GeneralFailure) {
            Log.e("reversalFailure", "GeneralFailure");
            type = "GeneralFailure";
          } else if (reversalFailure instanceof ReversalFailure.FailureMessage) {
            Log.e("reversalFailure", "FailureMessage");
            type = "FailureMessage";
          } else if (reversalFailure instanceof ReversalFailure.InvalidStatus) {
            Log.e("reversalFailure", "InvalidStatus");
          }
        }
        JSObject ret = new JSObject();
        ret.put("reverseStatus", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void getTransactionByUUID(PluginCall call) {
    String transactionUUID = call.getString("transactionUUID");
    String jwt = call.getString("token");
    nearPay.getTransactionByUuid(transactionUUID, new GetTransactionListener() {

      @Override
      public void onSuccess(@NonNull TransactionData transactionData) {
        if(transactionData!=null){
          JSObject ret = new JSObject();
          Gson gson = new Gson();
          ret.put("transactionData", gson.toJson(transactionData));
          call.resolve(ret);
        }else{
          JSObject ret = new JSObject();
          ret.put("error_type", "NoDataFound");
          call.resolve(ret);
        }
      }

      @Override
      public void onFailure(@NonNull GetDataFailure getDataFailure) {
        String type = "InvalidStatus";
        if(getDataFailure!=null){
          if ( getDataFailure instanceof GetDataFailure.GeneralFailure){
            // when there is general error .
            type = "GeneralFailure";
          }
          else if ( getDataFailure instanceof GetDataFailure.AuthenticationFailed ){
            // when the Authentication is failed
            // You can use the following method to update your JWT
            type = "AuthenticationFailed";
            nearPay.updateAuthentication(new AuthenticationData.Jwt(jwt));
          }
          else if ( getDataFailure instanceof GetDataFailure.FailureMessage ){
            type = "FailureMessage";
          }
        }
        JSObject ret = new JSObject();
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }


  @PluginMethod
  public void logoutNearpay(PluginCall call) {
    nearPay.logout(new LogoutListener() {
      @Override
      public void onLogoutCompleted() {
        JSObject ret = new JSObject();
        ret.put("status", true);
        call.resolve(ret);
      }
      @Override
      public void onLogoutFailed(@NonNull LogoutFailure logoutFailure) {
        JSObject ret = new JSObject();
        if (logoutFailure instanceof LogoutFailure.AlreadyLoggedOut) {
          ret.put("status", true);
        }
        else  if (logoutFailure instanceof LogoutFailure.GeneralFailure) {
          ret.put("status", false);
        }
        call.resolve(ret);
      }
    });

  }
}
