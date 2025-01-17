package com.usth.instagramclone.UI.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

@AndroidEntryPoint
public class FeedFragment extends Fragment {
    private FragmentFeedBinding binding;
    private FeedViewModel viewModel;
    private List<Post> postsSaved;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        binding.frgFeedRecyclerViewSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.getFeed();
            binding.frgFeedRecyclerViewSwipeRefresh.setRefreshing(false);
        });

        // post crud messages
        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getPostsSaved().observe(getViewLifecycleOwner(), savedPosts -> {
            postsSaved = savedPosts;

            // load feed after saved posts
            viewModel.getFeed();
        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            PostAdapterFeed postAdapter = new PostAdapterFeed(requireContext(), (MainActivity) requireActivity(), posts, postsSaved, viewModel);
            binding.setPostAdapter(postAdapter);

            if (posts.size() == 0) binding.frgFeedLblMsgNoPost.setVisibility(View.VISIBLE);
            else binding.frgFeedLblMsgNoPost.setVisibility(View.GONE);
        });

        viewModel.getSavedPosts();

        return binding.getRoot();
    }
}
