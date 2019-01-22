package pl.lodz.p.interiordesignapp.model;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public class ArFragmentManager {
    private static ArFragmentManager manager;
    private DesignModel designModel;
    private ViewPager viewPager;
    private Fragment fragment;


    private ArFragmentManager() {
    }

    public static ArFragmentManager getInstance() {
        if (manager == null) {
            manager = new ArFragmentManager();
        }
        return manager;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public DesignModel getDesignModel() {
        return designModel;
    }

    public void setDesignModel(DesignModel designModel) {
        this.designModel = designModel;
    }
}
