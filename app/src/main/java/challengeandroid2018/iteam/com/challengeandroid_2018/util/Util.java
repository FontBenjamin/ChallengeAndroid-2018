package challengeandroid2018.iteam.com.challengeandroid_2018.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import challengeandroid2018.iteam.com.challengeandroid_2018.R;

/**
 * Created by Marianna on 13/03/2018.
 */

public class Util {

    public static void displayErrorAlert(String title, String content, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content).setCancelable(false)
                .setIcon(R.drawable.ic_error_black_24dp)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public static boolean requestPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static Sensor requestLightSensor(Context context){
        SensorManager sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        return sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }
}




