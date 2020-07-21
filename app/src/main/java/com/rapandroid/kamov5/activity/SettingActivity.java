package com.rapandroid.kamov5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.notifications.DailyReminderReceiver;
import com.rapandroid.kamov5.notifications.ReleaseReminderReceiver;
import com.rapandroid.kamov5.setting.SettingPreference;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private Switch swDaily;
    private Switch swRelease;
    private DailyReminderReceiver mDailyReminderReceiver;
    private ReleaseReminderReceiver mReleaseReminderReceiver;
    private SettingPreference mSettingPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        swDaily = findViewById(R.id.sw_daily);
        swRelease = findViewById(R.id.sw_release);
        Button btnChange = findViewById(R.id.btn_change);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Setting");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDailyReminderReceiver = new DailyReminderReceiver();
        mReleaseReminderReceiver = new ReleaseReminderReceiver();

        mSettingPreference = new SettingPreference(this);
        setSwitchRelease();
        setSwitchReminder();
        swDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swDaily.isChecked()) {
                    mDailyReminderReceiver.setDailyAlarm(getApplicationContext());
                    mSettingPreference.setDailyReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    mDailyReminderReceiver.cancelAlarm(getApplicationContext());
                    mSettingPreference.setDailyReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });
        swRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swRelease.isChecked()) {
                    mReleaseReminderReceiver.setReleaseAlarm(getApplicationContext());
                    mSettingPreference.setReleaseReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    mReleaseReminderReceiver.cancelAlarm(getApplicationContext());
                    mSettingPreference.setReleaseReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSwitchReminder() {
        if (mSettingPreference.getDailyReminder()) {swDaily.setChecked(true);
        swDaily.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));}
        else {swDaily.setChecked(false);}
    }

    private void setSwitchRelease() {
        if (mSettingPreference.getReleaseReminder()) swRelease.setChecked(true);
        else swRelease.setChecked(false);
    }
}
