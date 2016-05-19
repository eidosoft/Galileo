package com.eido.galileo.activity.contentactivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.eido.galileo.R;

public class ImageViewer extends ViewerActivity {


    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Bundle b = getIntent().getExtras();
        String resource = b.getString("resource");
        int idResource = this.getResources().getIdentifier(resource,"drawable",this.getPackageName());
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(idResource);

    }
}
