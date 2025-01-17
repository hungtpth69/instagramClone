package com.usth.instagramclone.UI.View.Activities;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.usth.instagramclone.API.ApiClient;
import com.usth.instagramclone.UI.View.Fragments.NotificationsFragment;
import com.usth.instagramclone.UI.View.Fragments.ProfileFragment;
import com.usth.instagramclone.UI.View.Fragments.ReelsFragment;
import com.usth.instagramclone.UI.View.Fragments.SearchFragment;
import com.usth.instagramclone.R;

import android.widget.EditText;

import com.usth.instagramclone.API.MyAPI;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    //Random image urls below
    public static final String profilePicUrl = "https://instagram.fnag1-1.fna.fbcdn.net/vp/92a15d2c91d06d45f9a5b72ffd4cf3bd/5D84C9FD/t51.2885-19/s150x150/54731743_2011997322443409_3029283709959274496_n.jpg?_nc_ht=instagram.fnag1-1.fna.fbcdn.net";
    public static final String images[] = {profilePicUrl,
            "https://blog.rackspace.com/wp-content/uploads/2018/09/pumping-iron-arnold-schwarzenegger-1-1108x0-c-default.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSijnCjlpixxnEcvYeKm-1pg6s2ohuD2VMcMoIzTZInCSZ57SJN",
            "https://pbs.twimg.com/profile_images/798351849984294912/okhePpJW.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhuaOnKGXWUAV7UMA9UhUQB66kaIne0HYKUDOgfzr8dCO2tchv"
    };

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }


    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        loadFragment(new HomeFragment());               //Default is home fragment

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return loadFragment(new HomeFragment());
                    case R.id.search:
                        return loadFragment(new SearchFragment());
                    case R.id.reels:
                        return loadFragment(new ReelsFragment());
                    case R.id.notifications:
                        return loadFragment(new NotificationsFragment());
                    case R.id.profile:
                        return loadFragment(new ProfileFragment());
                }

                return false;
            }
        });
    }


    private boolean loadFragment(Fragment fragment){

        if (fragment != null) {
            FragmentManager fm  = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_popup_post, menu);
        return true;
    }
    private void makeApiCall() {
        MyAPI myApi = ApiClient.getMyApi();
        Call<User> call = myApi.getUser("841043837003246?fields=id,username,profile_picture_url&access_token=EAAHLJkkZCZBsYBAAEQn40ee1iAyRu37wMEevAdFVHDgxdafJw2rP5uUVCh5PBgHEkkaUn72VjI4fMZCZA0Cyb54ZAGm6rpSpPplX5nP8LiQJDnqwhKR3HLycPhf7OCc5INsabi49XtuadGTwNtb9jwP2xOkefKTSendKxpuV7LoeaFDLlNJQWTw0Fj9xan7rqpPYpyF6ZCCxLuY8rZCZCViO");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    // Do something with the user object
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle the error
            }
        });
    }
}
