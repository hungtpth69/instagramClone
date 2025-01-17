package com.usth.instagramclone.UI.View.ViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private UserRepository uRepo;
    private NotificationRepository nRepo;
    private MutableLiveData<Integer> unseenNotificationCount;
    private MutableLiveData<User> user;

    @Inject
    public MainViewModel(UserRepository uRepo, NotificationRepository nRepo) {
        this.uRepo = uRepo;
        this.nRepo = nRepo;

        this.unseenNotificationCount = nRepo.getUnseenNotificationCount();
        this.user = uRepo.getUser();
    }

    public MutableLiveData<Integer> getUnseenNotificationCount() {
        return unseenNotificationCount;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void getAllNotifications() {
        nRepo.getAllNotifications();
    }

    public void removeLastSessionUser() {
        uRepo.removeLastSessionUser();
    }
}

