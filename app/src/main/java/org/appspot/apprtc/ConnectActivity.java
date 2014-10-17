package org.appspot.apprtc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Handles the initial setup where the user selects which room to join.
 */
public class ConnectActivity extends Activity {

  private static final String TAG = "ConnectActivity";
  public static final String CONNECT_URL_EXTRA = "connect_url";
  private Toast logToast;
  private Button connectButton;
  private EditText urlEditText;
  private EditText roomEditText;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // If an implicit VIEW intent is launching the app, go directly to that URL.
    final Intent intent = getIntent();
    if ("android.intent.action.VIEW".equals(intent.getAction())) {
      connectToRoom(intent.getData().toString());
      return;
    }

    setContentView(R.layout.activity_connect);

    urlEditText = (EditText) findViewById(R.id.url_edittext);
    roomEditText = (EditText) findViewById(R.id.room_edittext);
    roomEditText.requestFocus();

    // TODO(kjellander): Make pressing the Enter key the same as clicking Connect.

    connectButton = (Button) findViewById(R.id.connect_button);
    connectButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = urlEditText.getText() + "/?r=" + roomEditText.getText();
        // TODO(kjellander): Add support for custom parameters to the URL.
        connectToRoom(url);
      }
    });
  }

  private void connectToRoom(String roomUrl) {
    if (validateUrl(roomUrl)) {
      Uri url = Uri.parse(roomUrl);
      Intent intent = new Intent(this, AppRTCDemoActivity.class);
      intent.setData(url);
      startActivity(intent);
    }
  }

  public static boolean isValidUrl(String url) {
    if (!Patterns.WEB_URL.matcher(url).matches()) {
      return false;
    }
    Uri uri = Uri.parse(url);
    String room = uri.getQueryParameter("r");
    return room != null && !room.equals("");
  }

  private boolean validateUrl(String url) {
    if (isValidUrl(url)) {
      return true;
    }
    new AlertDialog.Builder(this)
        .setTitle(getText(R.string.invalid_url_title))
        .setMessage(getString(R.string.invalid_url_text, url))
        .setCancelable(false)
        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              dialog.cancel();
            }
          }).create().show();
    return false;
  }

  // Log |msg| and Toast about it.
  private void logAndToast(String msg) {
    Log.d(TAG, msg);
    if (logToast != null) {
      logToast.cancel();
    }
    logToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
    logToast.show();
  }
}
