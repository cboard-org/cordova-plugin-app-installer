package plugin.appInstaller;

import android.support.v4.content.FileProvider;

public class AppInstallerProvider extends FileProvider {
   // avoid android:authrities conflict with other lib which also use android.support.v4.content.FileProvider
}
