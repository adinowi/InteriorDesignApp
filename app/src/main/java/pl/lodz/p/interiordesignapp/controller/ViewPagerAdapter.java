package pl.lodz.p.interiordesignapp.controller;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import pl.lodz.p.interiordesignapp.fragment.BlankFragment;
import pl.lodz.p.interiordesignapp.fragment.RootFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final int ITEMS_NUMBER = 2;
    private Fragment arcoreFragmentTag;
    private Fragment menuFragmentTag;

    ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BlankFragment();
            case 1:
                return new RootFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return ITEMS_NUMBER;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment result = (Fragment)super.instantiateItem(container, position);
        if(position == 0) {
            arcoreFragmentTag = result;
        } else {
            menuFragmentTag = result;
        }
        return result;
    }

    public Fragment getArcoreFragment() {
        return arcoreFragmentTag;
    }

    public Fragment getMenuFragment() {
        return menuFragmentTag;
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
    }
}
