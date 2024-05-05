package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class Fullimage extends AppCompatActivity {

    ImageView imageView;
    Button setwall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);

         imageView = findViewById(R.id.myZoomageView);
         setwall = findViewById(R.id.set);


        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imageView);

        setwall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setWallpaper();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





    }
    private void setWallpaper() throws IOException {
        // get image from the view
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // set wallpaper
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK); // for lock screen
        }

        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "Picture set as wallpaper", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
        }
    }


}