package com.usth.instagramclone.UI.View.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usth.instagramclone.R;

@AndroidEntryPoint
public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setSearchFragment(this);

        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            UserAdapterSearch userAdapter = new UserAdapterSearch(requireContext(), users);
            binding.setUserAdapter(userAdapter);
        });

        viewModel.filterUsersByName(binding.frgSearchTxtUserName.getText().toString().trim());

        return binding.getRoot();
    }

    public void onSearchTextChanged(String text) {
        viewModel.filterUsersByName(text);
    }
}