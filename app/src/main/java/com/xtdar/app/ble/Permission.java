package com.xtdar.app.ble;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

@TargetApi(23)
public class Permission {

    public static boolean checkPermission(@NonNull Context context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean containPermission(@NonNull Context context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
}
