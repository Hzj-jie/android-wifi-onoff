package org.gemini.wifi_onoff;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Process;

public abstract class WifiActivity extends Activity
{
  @Override
  protected final void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    WifiManager manager =
      (WifiManager) getSystemService(Context.WIFI_SERVICE);
    manager.setWifiEnabled(value());
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
