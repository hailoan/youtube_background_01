package com.youtu.sleep.youtubebackground.screens.main.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youtu.sleep.youtubebackground.R;
import com.youtu.sleep.youtubebackground.data.model.popularvideo.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuy on 01/08/2018.
 */
public class PopularVideosAdapter extends RecyclerView.Adapter<PopularVideosAdapter.MyViewHolder> {

    private static List<Video> sVideos;
    private static OnClickItemVideoListener sOnClickVideo;

    public List<Video> getVideos() {
        return sVideos;
    }

    PopularVideosAdapter() {
        this.sVideos = new ArrayList<>();
    }

    public void setData(List<Video> videos) {
        this.sVideos.clear();
        this.sVideos.addAll(videos);
        notifyDataSetChanged();
    }

    public void setOnClickVideoListener(OnClickItemVideoListener onClickVideo) {
        this.sOnClickVideo = onClickVideo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return sVideos == null ? 0 : sVideos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageVideo;
        private TextView mTextDuration, mTextVideoName, mTextChannel, mTextDescription;
        private RelativeLayout mRelativeVideo;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImageVideo = itemView.findViewById(R.id.image_video);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            mTextVideoName = itemView.findViewById(R.id.text_name);
            mTextChannel = itemView.findViewById(R.id.text_channel);
            mTextDescription = itemView.findViewById(R.id.text_description);
            mRelativeVideo = itemView.findViewById(R.id.relative_video);
        }

        void bindData(int position) {
            Video video = sVideos.get(position);
            Glide.with(itemView.getContext()).load(video.getUrlThumbnail()).into(mImageVideo);
            mTextVideoName.setText(video.getTitle());
            mTextChannel.setText(video.getChannelTitle());
            mTextDescription.setText(video.getDescription());
            mRelativeVideo.setTag(position);
            mRelativeVideo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            sOnClickVideo.onClickItemVideo(position);
        }
    }

    interface OnClickItemVideoListener {
        void onClickItemVideo(int position);
    }
}
