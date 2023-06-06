package com.example.staysafe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Acceuil extends AppCompatActivity {

    Button btnrefresh ,btnmap;
    Double longe , lat ;
    TextView edlat , edlong,edtxt ,name;


    Double a ;
    Double b ;
    String longitude,latitude ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        edlat=findViewById(R.id.edlat);
        edtxt=findViewById(R.id.txt_add5);
        edlong=findViewById(R.id.edlong_acc);
        btnrefresh = findViewById(R.id.btnrefresh);
        btnmap =findViewById(R.id.btnmap_acc);
        name=findViewById(R.id.add_name);



        //name.setText();

        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Toast.makeText(Acceuil.this, " this is your location!", Toast.LENGTH_LONG).show();



                FusedLocationProviderClient mClient =
                        LocationServices.getFusedLocationProviderClient(Acceuil.this);

                mClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        edtxt.setText("your location is:");
                        edlat.setText(location.getLatitude()+"");
                        edlong.setText(location.getLongitude()+"");

                        latitude = edlat.getText().toString();
                        longitude = edlong.getText().toString();
                        a =location.getLatitude();
                        b = location.getLongitude();
                    }

                });

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("map-coordinates");
                databaseReference.child("latitudes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value1 = String.valueOf(dataSnapshot.child("AK").getValue());

                        double f1 = Double.parseDouble(value1);
                        Log.i("our value1", value1);
                        String value2 = String.valueOf(dataSnapshot.child("HR").getValue());
                        double f2 = Double.parseDouble(value2);
                        Log.i("our value2", value2);
                        String value3 = String.valueOf(dataSnapshot.child("HS").getValue());
                        double f3 = Double.parseDouble(value3);
                        Log.i("our value3", value3);
                        String value4 = String.valueOf(dataSnapshot.child("MS").getValue());
                        double f4 = Double.parseDouble(value4);
                        Log.i("our value4", value4);
                        if ((f1 == a) || (f2 == a) || (f3 == a) || (f4 == a)) {

                            Log.i("message","true");
                            String message=" dear Sir / Madam you are in a dangerous region .. !! please take your necessary precautions !!! do not forget to wash your hands, to move away at least 1 m from the others and especially to wear your masks !!"+"  God protects you ";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager manager = getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);
                            }
                            NotificationCompat.Builder builder= new NotificationCompat.Builder(
                                    Acceuil.this,"My Notification");
                            builder.setSmallIcon(R.drawable.ic_message);
                            builder.setContentTitle("Alerte !!");
                            builder.setContentText(message);
                            builder.setAutoCancel(true);
                            Intent intent = new Intent(Acceuil.this,NotificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("message",message);
                            PendingIntent pendingIntent = PendingIntent.getActivity(Acceuil.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0,builder.build());

                        }
                        else {
                            String message="dear Sir / Madam you are in a safe region .. !! don't forget to take your necessary precautions !!!  wash your hands,  move away at least 1 m from the others and especially to wear your masks !! "+"God protects you";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager manager = getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);
                            }
                            NotificationCompat.Builder builder= new NotificationCompat.Builder(
                                    Acceuil.this,"My Notification");
                            builder.setSmallIcon(R.drawable.ic_message);
                            builder.setContentTitle("Alerte !!");
                            builder.setContentText(message);
                            builder.setAutoCancel(true);
                            Intent intent = new Intent(Acceuil.this,NotificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("message",message);
                            PendingIntent pendingIntent = PendingIntent.getActivity(Acceuil.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0,builder.build());


                            Log.d("message", "false");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                databaseReference.child("longitudes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String valuea = String.valueOf(dataSnapshot.child("Ak").getValue());
                        double fa = Double.parseDouble(valuea);
                        Log.i("our valuea", valuea);
                        String valueb = String.valueOf(dataSnapshot.child("HR").getValue());
                        double fb = Double.parseDouble(valueb);
                        Log.i("our valueb", valueb);
                        String valuec = String.valueOf(dataSnapshot.child("HS").getValue());
                        double fc = Double.parseDouble(valuec);
                        Log.i("our valuec", valuec);
                        String valued = String.valueOf(dataSnapshot.child("Ms").getValue());
                        double fd = Double.parseDouble(valued);
                        Log.i("our valued", valued);
                        if ((fa == b) || (fb == b) || (fc == b) || (fd == b)) {
                            String message ="dear Sir / Madam you are in a dangerous region .. !! please take your necessary precautions !!! do not forget to wash your hands, to move away at least 1 m from the others and especially to wear your masks !!" +" God protects you " ;
                            Log.i("message","true");

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager manager = getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);
                            }
                            NotificationCompat.Builder builder= new NotificationCompat.Builder(
                                    Acceuil.this,"My Notification");
                            builder.setSmallIcon(R.drawable.ic_message);
                            builder.setContentTitle("Alerte !!");
                            builder.setContentText(message);
                            builder.setAutoCancel(true);
                            Intent intent = new Intent(Acceuil.this,NotificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("message",message);
                            PendingIntent pendingIntent = PendingIntent.getActivity(Acceuil.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0,builder.build());




                        }
                        else {
                            String message="dear Sir / Madam you are in a safe region .. !! don't forget to take your necessary precautions !!!  wash your hands,  move away at least 1 m from the others and especially to wear your masks !! "+"God protects you";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager manager = getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);
                            }
                            NotificationCompat.Builder builder= new NotificationCompat.Builder(
                                    Acceuil.this,"My Notification");
                            builder.setSmallIcon(R.drawable.ic_message);
                            builder.setContentTitle("Alerte !!");
                            builder.setContentText(message);
                            builder.setAutoCancel(true);
                            Intent intent = new Intent(Acceuil.this,NotificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("message",message);
                            PendingIntent pendingIntent = PendingIntent.getActivity(Acceuil.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0,builder.build());

                            Log.d("message", "false");
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Acceuil.this, "this is your location !", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Acceuil.this,MapsActivity.class);
                i.putExtra("Longitude", longitude);
                i.putExtra("Latitude", latitude);
                startActivity(i);
            }
        });


    }}