package pl.lodz.p.interiordesignapp.fragment;

import android.content.ContentValues;
import android.media.CamcorderProfile;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.model.ArFragmentManager;
import pl.lodz.p.interiordesignapp.model.WritingArFragment;
import pl.lodz.p.interiordesignapp.multimedia.PictureTaker;
import pl.lodz.p.interiordesignapp.multimedia.VideoRecorder;

public class BlankFragment extends Fragment {
    private WritingArFragment arFragment;
    private ArFragmentManager arFragmentManager;
    private ModelRenderable modelRenderable;
    private FloatingActionButton recordButton;
    private FloatingActionButton takePicutreButton;
    private VideoRecorder videoRecorder;
    private PictureTaker pictureTaker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blank_fragment, container, false);
        arFragmentManager = ArFragmentManager.getInstance();
        arFragment = (WritingArFragment) getChildFragmentManager().findFragmentById(R.id.ux_fragment);
        setRenderable("table.sfb");

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (modelRenderable == null) {
                        return;
                    }
                    Anchor anchor = hitResult.createAnchor();
                    placeObject(arFragment, anchor, Uri.parse(arFragmentManager.getName()));
                });
        videoRecorder = new VideoRecorder();
        videoRecorder.setSceneView(arFragment.getArSceneView());
        videoRecorder.setVideoQuality(CamcorderProfile.QUALITY_720P, getResources().getConfiguration().orientation);
        recordButton = (FloatingActionButton)view.findViewById(R.id.recordButton);
        recordButton.setOnClickListener(this::toggleRecording);
        pictureTaker = new PictureTaker();
        pictureTaker.setSceneView(arFragment.getArSceneView());
        takePicutreButton = (FloatingActionButton)view.findViewById(R.id.takePictureButton);
        takePicutreButton.setOnClickListener(view1 -> {
            pictureTaker.setSceneView(arFragment.getArSceneView());
            pictureTaker.takePhoto(getContext());
        });

        return view;
    }

    private void toggleRecording(View unusedView) {
        if (!arFragment.hasWritePermission()) {
            //Log.e(TAG, "Video recording requires the WRITE_EXTERNAL_STORAGE permission");
            Toast.makeText(
                    getContext(),
                    "Video recording requires the WRITE_EXTERNAL_STORAGE permission",
                    Toast.LENGTH_LONG)
                    .show();
            arFragment.launchPermissionSettings();
            return;
        }
        boolean recording = videoRecorder.onToggleRecord();
        if (recording) {
            recordButton.setImageResource(R.drawable.ic_stop_white_24dp);
        } else {
            recordButton.setImageResource(R.drawable.ic_videocam_white_24dp);
            String videoPath = videoRecorder.getVideoPath().getAbsolutePath();
            Toast.makeText(getContext(), "Video saved: " + videoPath, Toast.LENGTH_SHORT).show();
            //Log.d(TAG, "Video saved: " + videoPath);

            // Send  notification of updated content.
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.TITLE, "Sceneform Video");
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            values.put(MediaStore.Video.Media.DATA, videoPath);
            getContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    private void setRenderable(String name) {
        ModelRenderable.builder()
                .setSource(this.getContext(), Uri.parse(name))
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this.getContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void placeObject(ArFragment fragment, Anchor anchor, Uri model) {
        CompletableFuture<Void> renderableFuture =
                ModelRenderable.builder()
                        .setSource(fragment.getContext(), model)
                        .build()
                        .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                        .exceptionally((throwable -> {
                            Toast toast =
                                    Toast.makeText(this.getContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        }));
    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }
}
