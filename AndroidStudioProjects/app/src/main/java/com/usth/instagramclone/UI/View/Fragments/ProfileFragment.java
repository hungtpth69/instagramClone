package com.usth.instagramclone.UI.View.Fragments;

import static com.usth.instagramclone.UI.View.Activities.MainActivity.images;
import static com.usth.instagramclone.UI.View.Activities.MainActivity.profilePicUrl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.usth.instagramclone.UI.View.Adapters.StoryAdapter;
import com.usth.instagramclone.Data.Model.Post;
import com.usth.instagramclone.Data.Model.Story;
import com.usth.instagramclone.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setProfileFragment(this);

        ProfileFragmentArgs bundle = ProfileFragmentArgs.fromBundle(getArguments());
        int user_id = (bundle.getUserId() == 0) ? Session.ACTIVE_USER.getUser_id() : bundle.getUserId();

        binding.frgProfileSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.getUserDetailsById(user_id);
            viewModel.getPostsByUserId(user_id);

            binding.frgProfileSwipeRefresh.setRefreshing(false);
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.setUser(user);
            Picasso.get().load(ApiUtils.BASE_URL + getResources().getString(R.string.dir_profile_photos) + user.getUser_photo()).into(binding.frgProfileImgUserPhoto);

            if (user.getUser_profile_private() == 1 && !user.getUser_id().equals(Session.ACTIVE_USER.getUser_id())) {
                binding.frgProfilerecyclerView.setVisibility(View.GONE);
                binding.frgProfileLblMsgPrivate.setVisibility(View.VISIBLE);
            } else {
                binding.frgProfilerecyclerView.setVisibility(View.VISIBLE);
                binding.frgProfileLblMsgPrivate.setVisibility(View.GONE);
            }
        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            binding.setPostCount(posts.size());

            PostAdapterProfile postAdapter = new PostAdapterProfile(requireContext(), posts);
            binding.setPostAdapter(postAdapter);
            binding.frgProfilerecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        });

        viewModel.getUserDetailsById(user_id);
        viewModel.getPostsByUserId(user_id);

        return binding.getRoot();
    }

    public void navToFollow(View view, List<User> users) {
        ProfileFragmentDirections.ProfileToFollow profileToFollow = ProfileFragmentDirections.profileToFollow(users.toArray(new User[0]));
        Navigation.findNavController(view).navigate(profileToFollow);
    }

    public void follow(User user) {
        viewModel.follow(user.getUser_id());

        // update ui
        Session.ACTIVE_USER.getFollowing().add(user);
        viewModel.getUserDetailsById(user.getUser_id());
    }

    public void unfollow(int user_id) {
        viewModel.unfollow(user_id);

        // update ui
        Iterator<User> iter = Session.ACTIVE_USER.getFollowing().iterator();
        while (iter.hasNext()) {
            User u = iter.next();

            if (u.getUser_id() == user_id) {
                iter.remove();
            }
        }
        viewModel.getUserDetailsById(user_id);
    }

    public void showProfilePrivateError() {
        Toast.makeText(requireContext(), getString(R.string.fragment_profile_msg_private), Toast.LENGTH_SHORT).show();
    }
}