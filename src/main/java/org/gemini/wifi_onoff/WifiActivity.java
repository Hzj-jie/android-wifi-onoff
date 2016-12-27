package org.gemini.wifi_onoff;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Process;

public abstract class WifiActivity extends Activity
{
  public static final boolean isOn(Context context) {
    WifiManager manager =
        (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    return manager.isWifiEnabled();
  }

  @Override
  protected final void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    // Wifi Ap On == Wifi On
    // Wifi Off == Wifi Ap Off
    // So only change wifi state when AP is off.
    if (!WifiApActivity.isOn(this))
    {
      WifiManager manager =
        (WifiManager) getSystemService(Context.WIFI_SERVICE);
      manager.setWifiEnabled(value());
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
