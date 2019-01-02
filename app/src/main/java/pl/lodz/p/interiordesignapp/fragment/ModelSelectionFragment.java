package pl.lodz.p.interiordesignapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.adapter.ModelAdapter;
import pl.lodz.p.interiordesignapp.utils.HelperUtil;

public class ModelSelectionFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String category;
    private static final String CATEGORY_KEY = "category";

    public static ModelSelectionFragment newInstance(String category) {
        ModelSelectionFragment modelSelectionFragment = new ModelSelectionFragment();

        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_KEY, category);
        modelSelectionFragment.setArguments(bundle);

        return modelSelectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            this.category = savedInstanceState.getString(CATEGORY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.model_selection_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.modelRecycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> modelNames = HelperUtil.getModelsNames(getResources());
        // specify an adapter (see also next example)
        mAdapter = new ModelAdapter(modelNames, this.getActivity().getApplication());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
