package br.com.androidlocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import static android.content.Context.LOCATION_SERVICE;

public class FusedLocationUtil {

    private Context ctx;
    protected FusedLocationProviderClient fusedLocationClient;

    FusedLocationUtil(Context ctx) {
        this.ctx = ctx;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx);
    }


    public boolean isGPSActive() {
        LocationManager service = (LocationManager) this.ctx.getSystemService(LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void openLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.ctx.startActivity(intent);
    }

    public Task<Location> getLastLocation() {
        if (
                ActivityCompat.checkSelfPermission(this.ctx,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this.ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return fusedLocationClient.getLastLocation();
        }
}
