package com.example.musicstreamingapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    /** Portrait loaded over the network (placeholder service). */
    private static final String PROFILE_IMAGE_URL = "https://i.pravatar.cc/400";

    private static final String[] ALBUM_IMAGE_URLS = new String[]{
            "https://picsum.photos/id/119/600/600",
            "https://picsum.photos/id/364/600/600",
            "https://picsum.photos/id/452/600/600",
            "https://picsum.photos/id/582/600/600"
    };

    private String keypass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiTheme.applyLightStatusBar(this);
        setContentView(R.layout.activity_settings);

        if (getIntent() != null) {
            String extra = getIntent().getStringExtra(HomeActivity.EXTRA_KEYPASS);
            if (extra != null) {
                keypass = extra;
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                return true;
            }
            startActivity(AppNavigation.homeIntent(this, keypass, tabExtraFor(id)));
            finish();
            return true;
        });

        loadRemoteImages();
    }

    private void loadRemoteImages() {
        ImageView avatar = findViewById(R.id.profileAvatar);
        Glide.with(this)
                .load(PROFILE_IMAGE_URL)
                .centerCrop()
                .into(avatar);

        ImageView[] covers = new ImageView[]{
                findViewById(R.id.albumCover1),
                findViewById(R.id.albumCover2),
                findViewById(R.id.albumCover3),
                findViewById(R.id.albumCover4)
        };
        for (int i = 0; i < covers.length; i++) {
            Glide.with(this)
                    .load(ALBUM_IMAGE_URLS[i])
                    .centerCrop()
                    .into(covers[i]);
        }
    }

    private static String tabExtraFor(int menuItemId) {
        if (menuItemId == R.id.nav_discover) {
            return HomeActivity.TAB_DISCOVER;
        }
        if (menuItemId == R.id.nav_favourite) {
            return HomeActivity.TAB_FAVOURITE;
        }
        return null;
    }
}
