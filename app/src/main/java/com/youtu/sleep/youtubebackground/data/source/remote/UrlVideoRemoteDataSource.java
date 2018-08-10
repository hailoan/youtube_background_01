package com.youtu.sleep.youtubebackground.data.source.remote;

import com.youtu.sleep.youtubebackground.data.model.popularvideo.Video;
import com.youtu.sleep.youtubebackground.data.source.UrlVideoDataSource;
import com.youtu.sleep.youtubebackground.screens.video.OnGetListUrlVideoYoutube;

import java.util.List;

/**
 * Created by DaiPhongPC on 8/8/2018.
 */

public class UrlVideoRemoteDataSource implements UrlVideoDataSource.RemoteDataSource {
    private static UrlVideoRemoteDataSource sInstance;

    private UrlVideoRemoteDataSource() {
    }

    public static UrlVideoRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UrlVideoRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getListUrlVideo(List<Video> videos, OnGetListUrlVideoYoutube onListener) {
        new GetUrlVideoAsyncTask(onListener).execute(videos);
    }
}
