package com.ranger.xyg.demos.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/18.
 */

public class LocalVideoActivity extends BaseActivity {
    public static final String videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    @BindView(R.id.video_view)
    VideoView videoView;

    @Override
    protected int getContentResId() {
        return R.layout.activity_local_video;
    }

    @Override
    protected void initViews() {
        super.initViews();
        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4";
        Uri uri = Uri.parse(videoUrl);
        // 设置视频控制器
        videoView.setMediaController(new MediaController(this));
        // 播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        // 设置视频路径
        videoView.setVideoURI(uri);
        // 开始播放视频
        videoView.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( LocalVideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
