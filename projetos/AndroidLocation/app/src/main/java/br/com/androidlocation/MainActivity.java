


package br.com.androidlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnGetLocatization = (Button) findViewById(R.id.btnGetLocatization);
        btnGetLocatization.setOnClickListener(v -> {

            FusedLocationUtil fusedLocationUtil = new FusedLocationUtil(this);

            if (!fusedLocationUtil.isGPSActive()) {
                Log.i("=====>", "LOCALIZACAO NAO ATIVADA!!!");
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Localização GPS Desativado no momento! Deseja abrir tela de habilitacao do GPS?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fusedLocationUtil.openLocationSettings();
                    }
                });
                dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            } else {
                Log.i("=====>", "LOCALIZACAO S I M  ATIVADA!!!");

                if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Log.i("===>","APP NAO TEM PERMISSAO");

                    ActivityCompat.requestPermissions(
                            this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},123);

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }else{//if (ActivityCompat.checkSelfPermission(
                    Log.i("===>","APP TEM TEM TEM PERMISSAO");
                    LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            Log.i("==$$=$$==>", "public void onSuccess(Location location) {...");
                            if(location != null) {
                                String strLat = Double.toString(location.getLatitude());
                                String strLgn = Double.toString(location.getLongitude());

                                TextView txtLgt = (TextView) findViewById(R.id.txtLgt);
                                TextView txtLat = (TextView) findViewById(R.id.txtLat);

                                txtLgt.setText(strLgn);
                                txtLat.setText(strLat);
                            }else {//if (location !=null)
                                Log.i("===>","Location foi null :((");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("==$$==$$=>", "public void onFailure(@NonNull Exception e) {...");
                            e.printStackTrace();
                        }
                    });
                }////if (ActivityCompat.checkSelfPermission(
            }


        });
    }
}