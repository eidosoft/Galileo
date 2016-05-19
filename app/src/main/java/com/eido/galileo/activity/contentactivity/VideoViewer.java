package com.eido.galileo.activity.contentactivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

import com.eido.galileo.R;


public class VideoViewer extends ViewerActivity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_video_viewer);

        Bundle bundle = getIntent().getExtras();
        String resource = bundle.getString("resource");
        int idResource = this.getResources().getIdentifier(resource,"raw",this.getPackageName());
        VideoView vd = (VideoView)findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + idResource);
        MediaController mc = new MediaController(this);
        vd.setMediaController(mc);
        vd.setVideoURI(uri);
        vd.start();
        vd.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
