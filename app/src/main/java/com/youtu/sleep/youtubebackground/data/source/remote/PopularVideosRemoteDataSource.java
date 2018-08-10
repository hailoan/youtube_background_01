package com.youtu.sleep.youtubebackground.data.source.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.youtu.sleep.youtubebackground.data.model.popularvideo.Video;
import com.youtu.sleep.youtubebackground.data.source.PopularVideosDataSource;

import com.youtu.sleep.youtubebackground.utils.exception.ParseJSONException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.youtu.sleep.youtubebackground.BuildConfig.API_KEY;
import static com.youtu.sleep.youtubebackground.utils.Contants.BASE_URL;

/**
 * Created by thuy on 06/08/2018.
 */
public class PopularVideosRemoteDataSource implements PopularVideosDataSource.RemoteDataSource {

    private static final String MY_TAG = PopularVideosRemoteDataSource.class.getSimpleName();
    private OnGetPopularVideosListener mListener;

    private Exception mException = null;

    @Override
    public void getPopularVideos(OnGetPopularVideosListener listener) {
        this.mListener = listener;
        loadData();
    }

    private void loadData() {
        GetVideoDataAsyncTask myAsyncTask = new GetVideoDataAsyncTask();
        myAsyncTask.execute();
    }

    private class GetVideoDataAsyncTask extends AsyncTask<Void, Void, List> {

        /**
         * Get result (json string)
         */

        @Override
        protected List<Video> doInBackground(Void... voids) {
            try {
                String s = getFullUrl();
                URL url = new URL(s);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(ParameterPopularVideo.REQUEST_METHOD);
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertInputStreamToString(inputStream);
                return parseData(result);
            } catch (IOException | ParseJSONException e) {
                mException = e;
                return null;
            }
        }

        /**
         * Parse from json string to Video object list
         */

        @Override
        protected void onPostExecute(List videos) {
            super.onPostExecute(videos);
            if (mException == null) {
                if (videos != null) {
                    mListener.onSuccess(videos);
                } else mListener.onFail(ParameterPopularVideo.NULL_LIST);
            } else mListener.onFail(mException.getMessage());
        }
    }

    private List<Video> parseData(String s) throws ParseJSONException {
        List<Video> videos = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray(ParameterPopularVideo.ITEMS_KEY);
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getJSONObject(i).getString(ParameterPopularVideo.ID);
                JSONObject snippet = jsonArray.getJSONObject(i).getJSONObject(ParameterPopularVideo.SNIPPET_KEY);
                String title = snippet.getString(ParameterPopularVideo.TITLE_KEY);
                String description = snippet.getString(ParameterPopularVideo.DES_KEY);
                String channelTitle = snippet.getString(ParameterPopularVideo.CHANNEL_TITLE_KEY);
                JSONObject high = snippet.getJSONObject(ParameterPopularVideo.THUMBNAILS_KEY)
                        .getJSONObject(ParameterPopularVideo.HIGH_KEY);
                String url = high.getString(ParameterPopularVideo.URL_KEY);
                videos.add(new Video(id, title, channelTitle, description, url));
            }
        } catch (JSONException e) {
            throw new ParseJSONException(e);
        }
        return videos;
    }

    /**
     * Get url to get videos
     */

    private String getFullUrl() {
        StringBuilder str = new StringBuilder(BASE_URL);
        Map<String, String> params = new HashMap<>();

        params.put(ParameterPopularVideo.PART_KEY, ParameterPopularVideo.PART_VALUE);
        params.put(ParameterPopularVideo.CHART_KEY, ParameterPopularVideo.CHART_VALUE);
        params.put(ParameterPopularVideo.REGION_CODE_KEY, ParameterPopularVideo.REGION_CODE_VALUE);
        params.put(ParameterPopularVideo.KEY, API_KEY);
        for (String s : params.keySet()) {
            str.append(s).append(params.get(s));
        }
        return str.toString();
    }

    /**
     * convert from json to string
     */

    private String convertInputStreamToString(InputStream inputStream) {
        String str;
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        try {
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            Log.e(MY_TAG, e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    @interface ParameterPopularVideo {
        String PART_KEY = "part=";
        String PART_VALUE = "snippet";
        String CHART_KEY = "&chart=";
        String CHART_VALUE = "mostPopular";
        String REGION_CODE_KEY = "&regionCode=";
        String REGION_CODE_VALUE = "vn";
        String KEY = "&key=";

        String ITEMS_KEY = "items";
        String ID = "id";
        String SNIPPET_KEY = "snippet";
        String TITLE_KEY = "title";
        String DES_KEY = "description";
        String CHANNEL_TITLE_KEY = "channelTitle";
        String THUMBNAILS_KEY = "thumbnails";
        String HIGH_KEY = "high";
        String URL_KEY = "url";

        String REQUEST_METHOD = "GET";

        String NULL_LIST = "No video to display";
    }
}
