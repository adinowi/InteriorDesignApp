package pl.lodz.p.interiordesignapp.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.barcode.BarCodeCaptureActivity;
import pl.lodz.p.interiordesignapp.model.DesignObject;
import pl.lodz.p.interiordesignapp.service.DownloadTask;
import pl.lodz.p.interiordesignapp.service.ServiceManager;
import pl.lodz.p.interiordesignapp.utils.AppConst;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();
    public static final int RC_BARCODE_CAPTURE = 9001;

    private ImageButton qrCodeButton;
    private ImageButton armChairCategoryButton;
    private ImageButton bedCategoryButton;
    private ImageButton couchCategoryButton;
    private ImageButton lampCategoryButton;
    private ImageButton decorationCategoryButton;
    private ImageButton wardrobeCategoryButton;
    private ImageButton tableCategoryButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        qrCodeButton = view.findViewById(R.id.qrCodeButton);
        qrCodeButton.setOnClickListener(viewOnClick -> {
            Intent intent = new Intent(getActivity(), BarCodeCaptureActivity.class);
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        });
        armChairCategoryButton = view.findViewById(R.id.armChairCategoryButton);
        bedCategoryButton = view.findViewById(R.id.bedCategoryButton);
        couchCategoryButton = view.findViewById(R.id.couchCategoryButton);
        lampCategoryButton = view.findViewById(R.id.lampCategoryButton);
        decorationCategoryButton = view.findViewById(R.id.decorationCategoryButton);
        wardrobeCategoryButton = view.findViewById(R.id.wardrobeCategoryButton);
        tableCategoryButton = view.findViewById(R.id.tableCategoryButton);

        setCategoryButton(armChairCategoryButton, AppConst.ARMCHAIR_CATEGORY);
        setCategoryButton(bedCategoryButton, AppConst.BED_CATEGORY);
        setCategoryButton(couchCategoryButton, AppConst.COUCH_CATEGORY);
        setCategoryButton(lampCategoryButton, AppConst.LAMP_CATEGORY);
        setCategoryButton(decorationCategoryButton, AppConst.DECORATION_CATEGORY);
        setCategoryButton(wardrobeCategoryButton, AppConst.WARDROBE_CATEGORY);
        setCategoryButton(tableCategoryButton, AppConst.TABLE_CATEGORY);

        return view;
    }

    private void setCategoryButton(ImageButton button, String category) {
        button.setOnClickListener(view -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.root_frame, ModelSelectionFragment.newInstance(category));

            transaction.commit();
        });
    }

    /*@Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if(visible) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.root_frame, new CategoryFragment()).commit();
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarCodeCaptureActivity.BarcodeObject);
                    //statusMessage.setText(R.string.barcode_success);
                    //barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    ServiceManager.getInstance().getDesignObject(barcode.displayValue, new Callback<DesignObject>() {
                        @Override
                        public void onResponse(Call<DesignObject> call, Response<DesignObject> response) {
                            DesignObject designObject = response.body();
                            if(response.isSuccessful() && designObject != null) {
                                startDownload(designObject);
                            }
                        }

                        @Override
                        public void onFailure(Call<DesignObject> call, Throwable t) {
                            Log.d(TAG, "respnse error");
                        }
                    });
                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                //      CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startDownload(DesignObject designObject) {
        // declare the dialog as a member field of your activity
        ProgressDialog mProgressDialog;

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Downloading files");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(getContext(), mProgressDialog, designObject);
        downloadTask.execute(ServiceManager.HOST_NAME + designObject.getImageURL(), ServiceManager.HOST_NAME + designObject.getSfbURL());

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
    }
}
