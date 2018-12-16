package pl.lodz.p.interiordesignapp.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.controller.MainActivity;
import pl.lodz.p.interiordesignapp.controller.ModelSelectionActivity;
import pl.lodz.p.interiordesignapp.model.ArFragmentManager;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {
    private List<String> models;
    private final LayoutInflater inflater;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ModelAdapter(List<String> models, Application application) {
        this.models = models;
        inflater = LayoutInflater.from(application.getApplicationContext());

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ModelAdapter.ModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) inflater.inflate(R.layout.model, parent, false);
        ModelViewHolder modelViewHolder = new ModelViewHolder(view);
        return modelViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ModelViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(models != null) {
            final String modelName = models.get(position);
            if (modelName != null) {
                holder.modelNameTextView.setText(modelName);
                holder.setButton.setOnClickListener(view -> {
                    ArFragmentManager.getInstance().setName(modelName);
                });
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return models.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView modelNameTextView;
        public Button setButton;
        public ModelViewHolder(View view) {
            super(view);
            modelNameTextView = view.findViewById(R.id.modelNameTextView);
            setButton = view.findViewById(R.id.setButton);
        }
    }
}
