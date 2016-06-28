package madhushala.npbazzar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        //webView.setWebViewClient(new CustomWebViewClient());

        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl("file:///android_asset/home.html");
        //end of onCreateBundle

        webView.getSettings().setAppCacheMaxSize(50 * 1024 * 1024); // 5MB
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        String useragente= "Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30";
        webView.getSettings().setUserAgentString(useragente);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        final Activity activity = this;

//        webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                // Activities and WebViews measure progress with different scales.
//                // The progress meter will automatically disappear when we reach 100%
//                activity.setProgress(progress * 1000);
//            }
//        });
//        webView.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                webView.loadUrl("file:///android_asset/index.html");
//                Toast.makeText(activity, "Bad! No internet. . " + description, Toast.LENGTH_SHORT).show();
//
//            }
//        });

        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            Toast.makeText(activity, "Turn on your internet connection for Latest Data", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(activity, "Updating Data. . . :)", Toast.LENGTH_SHORT).show();
            webView.loadUrl("file:///android_asset/index.html");
        }

        webView.loadUrl("file:///android_asset/index.html");

                                  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    //implemeted from http://stackoverflow.com/questions/14670638/webview-load-website-when-online-load-local-file-when-offline

    //web view client implementation
    //private class CustomWebViewClient extends WebViewClient {
        //public boolean shouldOverrideUrlLoading(WebView view, String url)
        //{
            //do whatever you want with the url that is clicked inside the webview.
            //for example tell the webview to load that url.
           // view.loadUrl(url);
            //return true if this method handled the link event
            //or false otherwise
            //return true;
      //  }


//    @Override
//public void onConfigurationChanged(Configuration newConfig) {
//    super.onConfigurationChanged(newConfig);
//    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//}


    private static long back_pressed;

    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
        }
        else {
            webView.loadUrl("file:///android_asset/index.html");
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }}



    protected void onResume()
    {
        super.onResume();
        webView.loadUrl("file:///android_asset/index.html");

    }

}

