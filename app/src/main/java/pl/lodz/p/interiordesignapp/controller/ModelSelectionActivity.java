package pl.lodz.p.interiordesignapp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.adapter.ModelAdapter;
import pl.lodz.p.interiordesignapp.utils.HelperUtil;

public class ModelSelectionActivity extends AppCompatActivity {
    private static final String TAG = ModelSelectionActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_selection);

        mRecyclerView = (RecyclerView) findViewById(R.id.modelRecycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> modelNames = HelperUtil.getModelsNames(getResources());
        // specify an adapter (see also next example)
        mAdapter = new ModelAdapter(modelNames, getApplication());
        mRecyclerView.setAdapter(mAdapter);
    }
}
