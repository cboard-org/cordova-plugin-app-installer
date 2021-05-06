package plugin.appInstaller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import java.io.File;
import java.text.SimpleDateFormat;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInstaller extends CordovaPlugin {

  private static final String ACTION_INSTALL = "install";

  private static final String MYME_TYPE_APK =
    "application/vnd.android.package-archive";

  @Override
  public boolean execute(
    String action,
    JSONArray data,
    CallbackContext callbackContext
  )
    throws JSONException {
    if (action.equals(ACTION_INSTALL)) {
      try {
        String fileName = data.getString(0);
        Context context = this.cordova.getActivity().getApplicationContext();
        String filepath = context.getFilesDir() + "/" + fileName;
        Log.d("appInstaller", filepath);
        File file = new File(context.getFilesDir() + "/" + fileName);

        // Get file uri
        Uri uri = FileProvider.getUriForFile(
          context,
          context.getPackageName() + ".fileprovider",
          file
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // MIME-TYPE settings
        intent.setDataAndType(uri, MYME_TYPE_APK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        //intent.data = contentUri;
        context.startActivity(intent);
        //context.unregisterReceiver(this);
        // finish()

        callbackContext.success();
      } catch (Exception ex) {
        callbackContext.error(ex.toString());
      }
      return true;
    } else {
      return false;
    }
  }
}
