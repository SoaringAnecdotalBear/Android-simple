package com.wangxingxing.demo.videocache;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity  {

    public static final String URL = "http://music.163.com/song/media/outer/url?id=562598065.mp3";
    private String localUrl;
    private MediaPlayer mediaPlayer;
    private HttpProxyCacheServer mCacheServerProxy;
    //缓存相关
    private CacheListener mCacheListener = new CacheListener() {
        @Override
        public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCacheServerProxy = BaseApplication.getProxy(this);

        //初始化播放器
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {mediaPlayer.start();}
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {}
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {return false;}
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {}
        });
        //播放音乐
        try {
            mCacheServerProxy.registerCacheListener(mCacheListener, URL);
            localUrl = mCacheServerProxy.getProxyUrl(URL);
            mediaPlayer.setDataSource(localUrl);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
