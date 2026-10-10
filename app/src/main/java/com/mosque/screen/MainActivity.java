package com.mosque.screen;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        // Orientation horizontale automatique (les deux sens), même si le manifeste est ignoré
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        web = new WebView(this);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);               // localStorage (réglages sauvegardés)
        s.setMediaPlaybackRequiresUserGesture(false); // bip sonore sans clic
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.setBackgroundColor(0xFF000000);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.addJavascriptInterface(new Object() {
            @JavascriptInterface public void exit() { runOnUiThread(() -> finishAndRemoveTask()); }
        }, "Android");
        web.loadUrl("file:///android_asset/index.html");
        hideSystemUi();
        askOverlayPermissionOnce();
    }

    // Android 10+ : l'autorisation "afficher par-dessus d'autres applis" est nécessaire
    // pour que le démarrage automatique au boot fonctionne. Demandée une seule fois.
    private void askOverlayPermissionOnce() {
        try {
            if (Build.VERSION.SDK_INT >= 29 && !Settings.canDrawOverlays(this)) {
                SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
                if (!sp.getBoolean("overlayAsked", false)) {
                    sp.edit().putBoolean("overlayAsked", true).apply();
                    startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName())));
                }
            }
        } catch (Exception ignored) { }
    }

    private void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override public void onWindowFocusChanged(boolean f) { super.onWindowFocusChanged(f); if (f) hideSystemUi(); }
    @Override protected void onResume() { super.onResume(); web.onResume(); }

    // Touche Menu de la télécommande = ouvrir les réglages
    @Override public boolean onKeyDown(int code, KeyEvent e) {
        if (code == KeyEvent.KEYCODE_MENU) { web.evaluateJavascript("toggleSettings()", null); return true; }
        return super.onKeyDown(code, e);
    }
}
