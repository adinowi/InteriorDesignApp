package pl.lodz.p.interiordesignapp.arcore;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class CustomTransformableNode extends TransformableNode {
    private Context context;
    private GestureDetector gestureDetector;

    public CustomTransformableNode(Context context, TransformationSystem transformationSystem) {
        super(transformationSystem);
        this.context = context;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                getTransformableNode().getParent().removeChild(getTransformableNode());

                return true;
            }


            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

        });

    }

    @Override
    public boolean onTouchEvent(HitTestResult hitTestResult, MotionEvent motionEvent) {

         return gestureDetector.onTouchEvent(motionEvent);
    }

    private TransformableNode getTransformableNode() {
        return this;
    }
}
