package pl.lodz.p.interiordesignapp.model;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public class ArFragmentManager {
    private static ArFragmentManager manager;
    private String name;
    private DesignModel designModel;
    private ViewPager viewPager;
    private Fragment fragment;


    private ArFragmentManager() {
        name = "models/wardrobe/drawer.sfb";
    }

    public static ArFragmentManager getInstance() {
        if (manager == null) {
            manager = new ArFragmentManager();
        }
        return manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
