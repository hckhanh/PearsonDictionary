package com.dictionary.hckhanh.pearsondictionary.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerManager extends FragmentPagerAdapter {

    public List<Pager> pagers;

    public PagerManager(FragmentManager fm) {
        super(fm);
        pagers = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return pagers.get(position).getPagerFragment();
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagers.get(position).getTabTitle();
    }

    public void addPager(Pager pager) {
        pagers.add(pager);
    }

    public Pager getPager(int position) {
        return pagers.get(position);
    }
}
