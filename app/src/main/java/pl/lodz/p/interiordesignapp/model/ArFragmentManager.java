package pl.lodz.p.interiordesignapp.model;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;

import pl.lodz.p.interiordesignapp.utils.AppConst;

public class ArFragmentManager {
    private static ArFragmentManager manager;
    private String name;
    private ViewPager viewPager;
    private Activity activity;
    private Fragment fragment;


    private ArFragmentManager() {
        name = "drawer.sfb";
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
