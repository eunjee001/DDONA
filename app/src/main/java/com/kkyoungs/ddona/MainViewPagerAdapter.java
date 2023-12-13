package com.kkyoungs.ddona;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragmentArrayList;

    public MainViewPagerAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
        fragmentArrayList = new ArrayList<>();
    }

    public void setFragmentArrayList(ArrayList<Fragment> fragments){
        if(fragments == null) return;
        fragmentArrayList = fragments;
    }

    public void addFragment(Fragment fragment){
        if(fragment == null) return;
        fragmentArrayList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment, Bundle bundle){
        if(fragment == null) return;
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentArrayList.add(fragment);
        notifyDataSetChanged();
    }

    public ArrayList<Fragment> getFragmentArrayList() {
        return fragmentArrayList == null ? new ArrayList<>() : fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
