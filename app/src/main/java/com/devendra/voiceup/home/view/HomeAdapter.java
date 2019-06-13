package com.devendra.voiceup.home.view;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.PostViewItemBinding;
import com.devendra.voiceup.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<DisplayablePost> displayablePosts;
    private MediaController mediaController;


    @Inject
    public HomeAdapter(List<DisplayablePost> displayablePosts, MediaController mediaController) {
        this.displayablePosts = displayablePosts;
        this.mediaController = mediaController;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeAdapter.HomeViewHolder(DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.post_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bind(displayablePosts.get(position));
    }

    @Override
    public int getItemCount() {
        return displayablePosts.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private PostViewItemBinding binding;


        private HomeViewHolder(PostViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        private void bind(DisplayablePost current) {

            binding.tvPostTitle.setText(current.getPostTitle());
            binding.tvCreatorName.setText(current.getUserName());

            if (current.getPostType() == Constants.PHOTO) {
                binding.vvPost.setVisibility(View.GONE);
                binding.tvSomethingWentWrong.setVisibility(View.GONE);
                binding.ivCreatorImage.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(current.getPostFilePath())
                        .placeholder(R.drawable.add_profile)
                        .into(binding.ivCreatorImage);
            } else if (current.getPostType() == Constants.VIDEO) {
                binding.vvPost.setVisibility(View.VISIBLE);
                binding.ivCreatorImage.setVisibility(View.GONE);
                binding.tvSomethingWentWrong.setVisibility(View.GONE);
                mediaController.setAnchorView(binding.vvPost);
                binding.vvPost.setMediaController(mediaController);
                binding.vvPost.setVideoURI(Uri.parse(current.getPostFilePath()));
                binding.vvPost.requestFocus();
            } else {
                binding.vvPost.setVisibility(View.GONE);
                binding.ivCreatorImage.setVisibility(View.GONE);
                binding.tvSomethingWentWrong.setVisibility(View.VISIBLE);
            }


        }

    }

    public void addPosts(List<DisplayablePost> displayablePosts) {
        int startSize = this.displayablePosts.size();
        this.displayablePosts.addAll(displayablePosts);
        int endSize = this.displayablePosts.size();
        notifyItemRangeInserted(startSize, endSize);
    }

}