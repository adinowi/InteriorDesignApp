package pl.lodz.p.interiordesignapp.model;

import android.content.Context;
import android.net.Uri;
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
}
