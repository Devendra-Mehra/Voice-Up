package com.devendra.voiceup.home.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.PostViewItemBinding;

import java.util.List;

import javax.inject.Inject;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<DisplayablePost> displayablePosts;


    @Inject
    public HomeAdapter(List<DisplayablePost> displayablePosts) {
        this.displayablePosts = displayablePosts;
    }

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
            //binding.setData(current);
            binding.tvPostTitle.setText(current.getPostTitle());
        }

    }

    public void addPosts(List<DisplayablePost> displayablePosts) {
        int startSize = this.displayablePosts.size();
        this.displayablePosts.addAll(displayablePosts);
        int endSize = this.displayablePosts.size();
        notifyItemRangeInserted(startSize, endSize);
    }

}