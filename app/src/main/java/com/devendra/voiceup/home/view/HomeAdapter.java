package com.devendra.voiceup.home.view;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private ItemListener listener;

    @Inject
    public HomeAdapter(List<DisplayablePost> displayablePosts) {
        this.displayablePosts = displayablePosts;
    }

    public interface ItemListener {
        void onPostClicked(String postFile, int postType);
    }

    public void setListener(ItemListener listener) {
        this.listener = listener;

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
            binding.clPost.setOnClickListener(v -> listener.onPostClicked(displayablePosts.get(getAdapterPosition()).getFileName(),
                    displayablePosts.get(getAdapterPosition()).getPostType()));
        }

        private void bind(DisplayablePost current) {

            binding.tvPostTitle.setText(current.getPostTitle());
            binding.tvCreatorName.setText(current.getUserName());

            if (current.getPostType() == Constants.PHOTO) {
                binding.vvPost.setVisibility(View.GONE);
                binding.tvSomethingWentWrong.setVisibility(View.GONE);
                binding.ivCreatorImage.setVisibility(View.VISIBLE);

                Log.d("Log15", "" + Constants.FILE_LOCATION + current.getFileName());
                Picasso.get()
                        .load(Constants.FILE_LOCATION + current.getFileName())
                        .placeholder(R.drawable.add_profile)
                        .into(binding.ivCreatorImage);

                binding.ivPostImage.setImageURI(Uri.parse(Constants.FILE_LOCATION + current.getFileName()));


            } else if (current.getPostType() == Constants.VIDEO) {
                binding.vvPost.setVisibility(View.VISIBLE);
                binding.ivCreatorImage.setVisibility(View.GONE);
                binding.tvSomethingWentWrong.setVisibility(View.GONE);
               /* MediaController mediaController = new MediaController(binding.vvPost.getContext());
                mediaController.setAnchorView(binding.vvPost);
                binding.vvPost.setMediaController(mediaController);
                binding.vvPost.setVideoURI(Uri.parse(Constants.FILE_LOCATION + current.getFileName()));
                binding.vvPost.start();
                binding.vvPost.requestFocus();*/


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