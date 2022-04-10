package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public ImageView camera,gallery,select;
    int SELECT_PICTURE = 200;
    private static final int pic_id = 123;
    public FloatingActionButton nextt;
    public Bitmap photo ;
    public Uri selectedImageUri;
    public String g;
    public Snackbar CSnackBar;
    public static String linkgallery,linkcamera,path1;
    private static final int STORAGE_PERMISSION_CODE = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gallery= findViewById(R.id.file);
        select=findViewById(R.id.roundedImageView);
        camera=findViewById(R.id.camera);
        nextt=findViewById(R.id.next);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        nextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                move();
            }
        });



        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                Intent camera_intent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            }
        });





    }

    void imageChooser() {


        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                 selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    select.setImageURI(selectedImageUri);
                }
            }
        }

        if (requestCode == pic_id) {
             photo = (Bitmap)data.getExtras()
                    .get("data");
            select.setImageBitmap(photo);
        }


    }

    void move() {
        if(photo != null || selectedImageUri != null ){

            if (photo != null){
                crrmain();


            }
            else {
                grrmain();
            }



        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Select the image", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void crrmain() {
        linkcamera=photo.toString();
        linkgallery="null";
        File mediaDir = new File(Environment.getExternalStorageDirectory(),"mediamyapplication");
        if (!mediaDir.exists()){
            if (!mediaDir.mkdirs()) {
                Toast.makeText(MainActivity.this, "no" , Toast.LENGTH_SHORT).show();
            }
        }

        Random rand = new Random();
        @SuppressLint("DefaultLocale") String id = String.format("%04d", rand.nextInt(10000));
        g= "temp" + id +".jpg";
        String gb= Environment.getExternalStorageDirectory()+"/mediamyapplication/"+g;
        File resolveMeSDCard = new File(Environment.getExternalStorageDirectory()+"/mediamyapplication/"+g);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(resolveMeSDCard);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(MainActivity.this, "/Internal Storage/download/mediamyapplication/"+g , Toast.LENGTH_SHORT).show();
        path1=gb;
        progressBar.setVisibility(View.VISIBLE); //to show
        progressBar.setVisibility(View.GONE);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent);

            }
        }, 5000);


    }

    public void grrmain(){
        linkgallery=selectedImageUri.toString();
        linkcamera="null";
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);
    }


    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(MainActivity.this,
                    "",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void createfile() throws IOException {



    }


}