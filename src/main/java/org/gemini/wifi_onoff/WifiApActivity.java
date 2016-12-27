package org.gemini.wifi_onoff;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import java.lang.reflect.Method;

public abstract class WifiApActivity extends Activity
{
  private static final Method isWifiApEnabled;
  private static final Method setWifiApEnabled;

  static
  {
    Method m = null;
    try
    {
      m = WifiManager.class.getMethod("isWifiApEnabled");
    }
    catch (Exception ex)
    {
      m = null;
    }
    isWifiApEnabled = m;

    try
    {
      m = WifiManager.class.getMethod("setWifiApEnabled",
                                      WifiConfiguration.class,
                                      boolean.class);
    }
    catch (Exception ex)
    {
      m = null;
    }
    setWifiApEnabled = m;
  }

  public static final boolean isOn(Context context) {
    if (isWifiApEnabled == null) return false;
    WifiManager manager =
        (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    try
    {
      return (boolean) isWifiApEnabled.invoke(manager);
    }
    catch (Exception ex)
    {
      Log.e("WifiApActivity", "", ex);
      return false;
    }
  }

  @Override
  protected final void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    // Wifi Ap On == Wifi On
    // Wifi Off == Wifi Ap Off
    // So only change Wifi Ap state if current state does not equal to value().
    if (setWifiApEnabled != null && isOn(this) != value())
    {
      WifiManager manager =
        (WifiManager) getSystemService(Context.WIFI_SERVICE);
      try
      {
        setWifiApEnabled.invoke(manager, null, value());
      }
      catch (Exception ex) {
        Log.e("WifiApActivity", "", ex);
      }
    }
    finish();
  }

  @Override
  protected final void onDestroy()
  {
    super.onDestroy();
    Process.killProcess(Process.myPid());
    System.exit(0);
  }

  protected abstract boolean value();
}
