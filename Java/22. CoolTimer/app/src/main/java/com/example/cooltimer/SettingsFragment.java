package com.example.cooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.timer_preferences);

        // set on start activity
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);

            if (preference instanceof ListPreference || preference instanceof EditTextPreference) {
                String value = sharedPreferences.getString(preference.getKey(),"");
                setPreferenceLabel(preference, value);
            }
        }

        Preference preference = findPreference("timerStartFrom");
        preference.setOnPreferenceChangeListener(this);
    }

    private  void setPreferenceLabel(Preference preference, String value) {
         if (preference instanceof ListPreference) {
             ListPreference listPreference = (ListPreference) preference;
             int index = listPreference.findIndexOfValue(value);
             if (index >=0) {
                  listPreference.setSummary(listPreference.getEntries()[index]);
             }
         } else if (preference instanceof EditTextPreference) {
             preference.setSummary(value);
         }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
         Preference preference = findPreference(key);
        if (preference instanceof ListPreference || preference instanceof EditTextPreference) {
            String value = sharedPreferences.getString(preference.getKey(),"");
            setPreferenceLabel(preference, value);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("timerStartFrom")) {
            String defaultTimerStartFromString = (String) newValue;

            try {
                int defaultTimerStartFrom = Integer.valueOf(defaultTimerStartFromString);
            } catch (NumberFormatException err) {
                Toast.makeText(getContext(), "Value should be a number only.", Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        // если true то мы сохраняем в sharedPreferences, если false то нет
        return true;
    }
}

