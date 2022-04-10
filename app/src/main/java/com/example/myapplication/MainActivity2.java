package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;

public class MainActivity2 extends AppCompatActivity {


    public String gallerylink=MainActivity.linkgallery;
    public String cameralink=MainActivity.linkcamera;
    public String photopath=MainActivity.path1;
    public ImageView image;
    public Button undo,crop,rotate,save;


    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        image=findViewById(R.id.imagem2);
        undo=findViewById(R.id.button);
        crop=findViewById(R.id.button2);
        rotate=findViewById(R.id.button3);
        save=findViewById(R.id.button4);



        if (gallerylink.equals("null")){

            File imgFile = new  File(photopath);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                image.setImageBitmap(myBitmap);

            }

        }
        else {
            image.setImageURI(Uri.parse(gallerylink));
        }

        crop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {


            }
        });








    }








}




