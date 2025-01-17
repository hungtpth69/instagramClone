package com.usth.instagramclone.UI.View.ViewModel;

import java.util.List;

@HiltViewModel
public class SavedPostsViewModel extends ViewModel {
    private PostRepository pRepo;

    private MutableLiveData<List<Post>> postsSaved;
    private MutableLiveData<String> message;

    @Inject
    public SavedPostsViewModel(PostRepository pRepo) {
        this.pRepo = pRepo;

        postsSaved = pRepo.getPostsSaved();
        message = pRepo.getMessage();
    }

    public MutableLiveData<List<Post>> getPostsSaved() {
        return postsSaved;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void updatePost(Post post) {
        pRepo.updatePost(post);
    }

    public void deletePost(int post_id) {
        pRepo.deletePost(post_id);
    }

    public void like(int post_id) {
        pRepo.like(post_id);
    }

    public void unlike(int post_id) {
        pRepo.unlike(post_id);
    }

    public void getSavedPosts() {
        pRepo.getSavedPosts();
    }

    public void save(int post_id) {
        pRepo.save(post_id);
    }

    public void unsave(int post_id) {
        pRepo.unsave(post_id);
    }
}
