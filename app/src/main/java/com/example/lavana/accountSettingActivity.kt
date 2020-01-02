package com.example.lavana

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat


class accountSettingActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        setTitle("Account Setting")


        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment()).commit()
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    class SettingsFragment: PreferenceFragmentCompat() {



        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences)

            val changePass = findPreference<ListPreference>("changePass")
            val changeProfilePic = findPreference<ListPreference>("changeProfilePic")



            changePass?.setOnPreferenceClickListener {

                val intent = Intent(activity, changePassActivity::class.java)
                startActivity(intent)

                true
            }

            changeProfilePic?.setOnPreferenceClickListener {

                val intent1 = Intent(activity, changeProfilePicActivity::class.java)
                startActivity(intent1)


                true
            }


        }
    }
}


