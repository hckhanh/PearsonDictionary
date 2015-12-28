package com.dictionary.hckhanh.pearsondictionary.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PagerManager extends FragmentPagerAdapter {

    public static List<Pager> pagers;

    public PagerManager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new PagerFragment();
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagers.get(position).tabTitle;
    }

}
