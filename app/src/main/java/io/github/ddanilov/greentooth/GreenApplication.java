package io.github.ddanilov.greentooth;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;


public class GreenApplication extends Application {
    public static final String APP_KEY = "Greentooth";
    public static final String ENABLED_KEY = "greentoothEnabled";
    public static final String DELAY_KEY = "waitTime";
    public static final String PRE_DISABLE_NOTIFICATIONS_KEY = "preDisableNotificationsEnabled";
    public static final String POST_DISABLE_NOTIFICATIONS_KEY = "postDisableNotificationsEnabled";
    public static final String THEME_KEY = "theme";
    public static final String PRE_DISABLE_CHANNEL_ID = "preDisableGreentoothChannel";
    public static final String POST_DISABLE_CHANNEL_ID = "postDisableGreentoothChannel";
    public static final String LAST_NOTIFICATION_ID_KEY = "lastNotificationId";
    public static final String ACTION_TEMP_DISABLE = "io.github.ddanilov.greentooth.ACTION_TEMP_DISABLE";
    public static final String ACTION_DISABLE = "io.github.ddanilov.greentooth.ACTION_DISABLE";
    public static final String ACTION_SWITCH_OFF = "io.github.ddanilov.greentooth.ACTION_SWITCH_OFF";
    public static final String NOTIFICATION_TAG = "TAG";
    public static final int NOTIFICATION_TYPE_PRE_DISABLE = 0;
    public static final int NOTIFICATION_TYPE_POST_DISABLE = 1;
    public static final int PRE_DISABLE_NOTIFICATION_ID = 999;
    public static final int DEFAULT_DELAY = 20;
    public static final int MAX_MINUTE_DELAY = 59;
    public static final int MIN_MINUTE_DELAY = 0;
    public static final int MAX_SECOND_DELAY = 59;
    public static final int MIN_SECOND_DELAY = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
        SharedPreferences sharedPreferences = getSharedPreferences(APP_KEY, 0);
        int themeItemId = sharedPreferences.getInt(THEME_KEY, R.id.action_default_theme);
        switch (themeItemId) {
            case R.id.action_default_theme:
                //Samsung phones have night mode in Android Pie
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.action_dark_theme:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.action_light_theme:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void createNotificationChannels() {
        createNotificationChannel(
                PRE_DISABLE_CHANNEL_ID,
                getString(R.string.pre_disable_channel_name),
                getString(R.string.pre_disable_channel_description),
                NotificationManager.IMPORTANCE_LOW
        );
        createNotificationChannel(
                POST_DISABLE_CHANNEL_ID,
                getString(R.string.post_disable_channel_name),
                getString(R.string.post_disable_channel_description),
                NotificationManager.IMPORTANCE_DEFAULT
        );
    }

    private void createNotificationChannel(String id, String name, String description, int importance) {
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
    }
}
