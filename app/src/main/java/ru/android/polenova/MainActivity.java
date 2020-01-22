package ru.android.polenova;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn2 = (findViewById(R.id.buttonNote));
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}

        /*private void loadImage(ImageView image, @RawRes int typeID, String imagePath) {
            Context context = image.getContext();
            BitmapPool pool = Glide.get(context).getBitmapPool();

// OPTION 1 Bitmap
            Glide
                    .with(image.getContext())
                    .load(imagePath)
                    .asBitmap()
                    .animate(android.R.anim.fade_in)
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .thumbnail(Glide
                            .with(context)
                            .load(typeID)
                            .asBitmap()
                            .imageDecoder(new SvgBitmapDecoder(pool)) // implements ResourceDecoder<InputStream, Bitmap>
                    )
                    .into(image)
            ;

// OPTION 2 GlideDrawable
            Glide
                    .with(image.getContext())
                    .load(imagePath)
                    .crossFade()
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_error)
                    .thumbnail(Glide
                            .with(context)
                            .load(typeID)
                            .decoder(new GifBitmapWrapperResourceDecoder(
                                            new ImageVideoBitmapDecoder(
                                                    new SvgBitmapDecoder(pool),
                                                    null //fileDescriptorDecoder
                                            ),
                                            // just to satisfy GifBitmapWrapperResourceDecoder.getId() which throws NPE otherwise
                                            new GifResourceDecoder(context, pool),
                                            pool
                                    )
                            )
                    )
                    .into(image)
            ;

    }
}*/
