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
public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private NotificationsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        binding.setNotificationsFragment(this);

        viewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            NotificationAdapter notificationAdapter = new NotificationAdapter(requireContext(), notifications);
            binding.setNotificationAdapter(notificationAdapter);

            viewModel.markAllNotificationsAsSeen();
            viewModel.getUnseenNotificationCount().setValue(0); // update notification icon in bottom menu

            if (notifications.size() == 0) binding.frgNotificationsLblMsgNothingInHere.setVisibility(View.VISIBLE);
        });

        viewModel.getAllNotifications();
        return binding.getRoot();
    }

    public void fabDeleteOnClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.fragment_notifications_msg_delete));
        builder.setNegativeButton(getString(R.string.btn_no), null);
        builder.setPositiveButton(getString(R.string.btn_yes), (dialog, which) -> viewModel.deleteAllNotifications());
        builder.show();
    }
}