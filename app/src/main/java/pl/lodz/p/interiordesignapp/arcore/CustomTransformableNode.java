package pl.lodz.p.interiordesignapp.arcore;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.collision.CollisionShape;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.model.DesignModel;
import pl.lodz.p.interiordesignapp.model.SkinModel;

public class CustomTransformableNode extends TransformableNode {
    private Context context;
    private GestureDetector gestureDetector;
    private Node viewNode;
    private Renderable renderableView;
    private Node rotateNode;
    private DesignModel designModel;

    public CustomTransformableNode(Context context, TransformationSystem transformationSystem, DesignModel designModel) {
        super(transformationSystem);
        getScaleController().setMinScale(0.1f);
        getScaleController().setMaxScale(4f);
        this.context = context;
        this.designModel = designModel;
        viewNode = new Node();
        ViewRenderable.builder().setView(context, R.layout.options_view).build().thenAccept(renderable -> {
            renderableView = renderable;
            View view = ((ViewRenderable) renderableView).getView();
            Button button = view.findViewById(R.id.deleteButton);
            button.setOnClickListener(unusedView -> {
                getTransformableNode().getParent().removeChild(getTransformableNode());
            });
            Button closeButton = view.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(v2 -> {
                viewNode.setRenderable(null);
            });
            SeekBar xRotation = view.findViewById(R.id.xRotation);
            xRotation.setMax(360);
            SeekBar zRotation = view.findViewById(R.id.zRotation);
            zRotation.setMax(360);
            xRotation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    getRotateNode().setLocalRotation(Quaternion.axisAngle(new Vector3(1, 0, 0), i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            zRotation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    getRotateNode().setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1), i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            if(designModel instanceof SkinModel) {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.colorLayout);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                SkinModel skinModel = (SkinModel) designModel;
                Button btMain = new Button(context);
                btMain.setLayoutParams(p);
                linearLayout.addView(btMain);
                btMain.setBackgroundColor(Color.parseColor(skinModel.getMainModel().getColor()));
                btMain.setOnClickListener( v -> {
                    createRenderable(skinModel.getMainModel());
                });
                for(DesignModel model : skinModel.getSubModels()) {
                    Button bt = new Button(context);
                    bt.setLayoutParams(p);
                    linearLayout.addView(bt);
                    bt.setBackgroundColor(Color.parseColor(model.getColor()));
                    bt.setOnClickListener( v -> {
                        createRenderable(model);
                    });
                }
            }
        });
        viewNode.setParent(this);

        rotateNode = new Node();

        rotateNode.setParent(this);
        createRenderable(designModel);


        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Box box = (Box)getRotateNode().getRenderable().getCollisionShape();
                viewNode.setLocalPosition(new Vector3(0f, box.getSize().y + 0.3f, 0f));
                viewNode.setRenderable(renderableView);

                //getTransformableNode().getParent().removeChild(getTransformableNode());

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

    private Node getRotateNode() {
        return rotateNode;
    }

    private void createRenderable(DesignModel designModel) {
        File file = new File(designModel.getSfbPath());
        CompletableFuture<Void> renderableFuture =
                ModelRenderable.builder()
                        .setSource(context, Uri.fromFile(file))
                        .build()
                        .thenAccept(renderable -> rotateNode.setRenderable(renderable))
                        .exceptionally((throwable -> {
                            Toast toast =
                                    Toast.makeText(context, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        }));
    }

}
