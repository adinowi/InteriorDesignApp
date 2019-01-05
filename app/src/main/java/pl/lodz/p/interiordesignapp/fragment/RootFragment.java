package pl.lodz.p.interiordesignapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.barcode.BarCodeCaptureFragment;
import pl.lodz.p.interiordesignapp.model.ArFragmentManager;

public class RootFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.root_fragment, container, false);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         */
        transaction.replace(R.id.root_frame, new CategoryFragment());
        transaction.addToBackStack(null);

        transaction.commit();

        view.bringToFront();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArFragmentManager.getInstance().setFragment(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        //if(!visible && getActivity() != null && getActivity().getSupportFragmentManager() != null) {
           // getActivity().getSupportFragmentManager().beginTransaction()
             //       .replace(R.id.root_frame, new CategoryFragment()).commit();
        //}
        if(visible) {
            this.getView().bringToFront();
        }
    }

}
