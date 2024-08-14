package com.ntarevpn.rbpessacash.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ntarevpn.rbpessacash.fragment.FreeServersFragment;
import com.ntarevpn.rbpessacash.fragment.PaidServersFragment;

public class ServerPageAdapter extends FragmentStateAdapter {
    public ServerPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("TAB POSITION", String.valueOf(position));
        if (position == 0) {
            return new FreeServersFragment();
        } else {
            return new PaidServersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
