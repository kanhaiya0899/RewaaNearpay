package com.rewaa.nearpay.capacitor;

import android.content.Context;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.List;

import io.nearpay.sdk.Configuration;
import io.nearpay.sdk.Environments;
import io.nearpay.sdk.NearPay;
import io.nearpay.sdk.data.models.TransactionReceipt;
import io.nearpay.sdk.utils.enums.InstallationError;
import io.nearpay.sdk.utils.enums.LoginError;
import io.nearpay.sdk.utils.enums.PaymentError;
import io.nearpay.sdk.utils.enums.StatusCheck;
import io.nearpay.sdk.utils.listeners.CheckStatusListener;
import io.nearpay.sdk.utils.listeners.InstallationListener;
import io.nearpay.sdk.utils.listeners.LoginListener;
import io.nearpay.sdk.utils.listeners.PaymentListener;

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
        Log.e("list", "" + list);
        for (StatusCheck status :
          list) {
          Log.e("status name", "" + status.name());
        }
        String displayList = list.toString();
        displayList = displayList.substring(1, displayList.length() - 1);
        JSObject ret = new JSObject();
        ret.put("status", displayList);
        call.resolve(ret);
      }
    });
  }

  public void sendEvent(JSObject data) {
    Log.e("percent", String.valueOf(data));
    notifyListeners("downloadProgressChange", data);
  }

  @PluginMethod
  public void installApp(PluginCall call) {

    nearPay.install(new InstallationListener() {
      @Override
      public void onDownloadStart() {
        // when the download starts
        Log.e("installApp", "DownloadStart");
        JSObject ret = new JSObject();
        ret.put("value", "DownloadStart");
        sendEvent(ret);
      }

      @Override
      public void onDownloadFailed() {
        // when download fails
        Log.e("installApp", "DownloadFailed");
        JSObject ret = new JSObject();
        ret.put("value", "DownloadFailed");
        sendEvent(ret);
        ;
      }

      @Override
      public void onDownloadProgressChange(int i) {
        // whe the download is on progress
        Log.e("installApp", String.valueOf(i));
        JSObject ret = new JSObject();
        ret.put("percent", i);
        sendEvent(ret);
      }

      @Override
      public void onInstallationStart() {
        // when the instalation starts
        Log.e("installApp", "InstallationStart");
        JSObject ret = new JSObject();
        ret.put("value", "InstallationStart");
        sendEvent(ret);
      }

      @Override
      public void onInstallationCompleted() {
        // instation is completed
        Log.e("installApp", "InstallationCompleted");
        JSObject ret = new JSObject();
        ret.put("value", "InstallationCompleted");
        sendEvent(ret);
      }

      @Override
      public void onInstallationFailed(InstallationError installationError) {
        if (installationError instanceof InstallationError.AlreadyInstalled) {
          // when the app is already installed
          Log.e("installApp", "AlreadyInstalled");
          JSObject ret = new JSObject();
          ret.put("value", "AlreadyInstalled");
          sendEvent(ret);
        } else if (installationError instanceof InstallationError.DownloadedFileNotFound) {
          // when the download file is not found
          Log.e("installApp", "DownloadedFileNotFound");
          JSObject ret = new JSObject();
          ret.put("value", "DownloadedFileNotFound");
          sendEvent(ret);
        } else if (installationError instanceof InstallationError.InstallFailed) {
          // when the installation failed
          Log.e("installApp", "InstallFailed");
          JSObject ret = new JSObject();
          ret.put("value", "InstallFailed");
          sendEvent(ret);
        } else if (installationError instanceof InstallationError.NetworkError) {
          // you can get the error message using getMessage
          String message = ((InstallationError.NetworkError) installationError).getMessage();
          Log.e("installationError", message);
          JSObject ret = new JSObject();
          ret.put("value", message);
          call.resolve(ret);
        } else if (installationError instanceof InstallationError.UnsupportedSdkVersion) {
          //NearPay services compatible with devices running Android API 26 or higher, otherwise UNSUPPORTED_SDK_VERSION will returned.
          Log.e("installationError", "UNSUPPORTED_SDK_VERSION");
          JSObject ret = new JSObject();
          ret.put("value", "UNSUPPORTED_SDK_VERSION");
          call.resolve(ret);
        }


      }
    });
  }

  @PluginMethod
  public void auth(PluginCall call) {
//    String token = call.getString("token");
    String token = "0000000000000036";
    Log.e("token", token);
    nearPay.login(token, new LoginListener() {
      @Override
      public void onLoginSucceed() {
        // write your code here
        Log.e("login", "Login Success");
        JSObject ret = new JSObject();
        ret.put("value", "LoginSucceed");
        call.resolve(ret);
      }

      @Override
      public void onLoginFailed(LoginError loginError) {
        if (loginError instanceof LoginError.AlreadyLogged) {
          // when you are already logged in
          Log.e("login", "AlreadyLogged");
          JSObject ret = new JSObject();
          ret.put("value", "AlreadyLogged");
          call.resolve(ret);
        } else if (loginError instanceof LoginError.InvalidArgs) {
          // when the login credentials are not correct
          Log.e("login", "InvalidArgs");
          JSObject ret = new JSObject();
          ret.put("value", "InvalidArgs");
          call.resolve(ret);
        } else if (loginError instanceof LoginError.Unexpected) {
          // when the error is Unexpected
          Log.e("login", "Unexpected");
          JSObject ret = new JSObject();
          ret.put("value", "Unexpected");
          call.resolve(ret);
        } else if (loginError instanceof LoginError.ErrorMessage) {
          // you can get the error message using the following code
          String error = ((LoginError.ErrorMessage) loginError).getMessage();
          Log.e("login", error);
          JSObject ret = new JSObject();
          ret.put("value", error);
          call.resolve(ret);
        } else if (loginError instanceof LoginError.InvalidStatus) {
          // you can get the status using the following code
          List<StatusCheck> status = ((LoginError.InvalidStatus) loginError).getStatus();
          Log.e("login", "InvalidStatus");
          JSObject ret = new JSObject();
          ret.put("value", "InvalidStatus");
          call.resolve(ret);
        }
        // return here
      }
    });
  }

  @PluginMethod
  public void purchase(PluginCall call) {
    nearPay.purchase(getActivity(), 2500l, "dfgdfgdfgd5fg4d5fg", new PaymentListener() {
      @Override
      public void onPaymentApproved(TransactionReceipt transactionReceipt) {
        //write your code here.
        Log.e("purchase", "PaymentApproved");
        JSObject ret = new JSObject();
        ret.put("value", "PaymentApproved");
        call.resolve(ret);
      }

      @Override
      public void onPaymentDeclined(TransactionReceipt transactionReceipt) {
        // when the payment declined.
        Log.e("purchase", "PaymentDeclined");
        JSObject ret = new JSObject();
        ret.put("value", "PaymentDeclined");
        call.resolve(ret);
      }

      @Override
      public void onPaymentUiClosed() {
        // when the payment ui is closed.
        Log.e("purchase", "PaymentUiClosed");
        JSObject ret = new JSObject();
        ret.put("value", "PaymentUiClosed");
        call.resolve(ret);
      }

      @Override
      public void onPaymentError(PaymentError paymentError) {
        if (paymentError instanceof PaymentError.PaymentCancelled) {
          // when the payment is canceled.
          Log.e("purchase", "PaymentCancelled");
          JSObject ret = new JSObject();
          ret.put("value", "PaymentCancelled");
          call.resolve(ret);
        } else if (paymentError instanceof PaymentError.PaymentProcessingError) {
          // when there is an issue with the payment.
          Log.e("purchase", "PaymentProcessingError");
          JSObject ret = new JSObject();
          ret.put("value", "PaymentProcessingError");
          call.resolve(ret);
        } else if (paymentError instanceof PaymentError.PaymentNetworkError) {
          // when there is an issue with the network.
          Log.e("purchase", "PaymentNetworkError");
          JSObject ret = new JSObject();
          ret.put("value", "PaymentNetworkError");
          call.resolve(ret);
        } else if (paymentError instanceof PaymentError.PaymentTryAnotherCard) {
          // here you will have to use another card.
          Log.e("purchase", "PaymentTryAnotherCard");
          JSObject ret = new JSObject();
          ret.put("value", "PaymentTryAnotherCard");
          call.resolve(ret);
        } else if (paymentError instanceof PaymentError.Unexpected) {
          // when the error is Unexpected
          Log.e("purchase", "Unexpected");
          JSObject ret = new JSObject();
          ret.put("value", "Unexpected");
          call.resolve(ret);
        } else if (paymentError instanceof PaymentError.InvalidStatus) {
          // you can get the status using the following code
          List<StatusCheck> status = ((PaymentError.InvalidStatus) paymentError).getStatus();
        }
      }
    });
  }
}
