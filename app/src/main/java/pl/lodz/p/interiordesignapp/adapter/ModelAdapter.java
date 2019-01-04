package pl.lodz.p.interiordesignapp.adapter;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.fragment.CategoryFragment;
import pl.lodz.p.interiordesignapp.model.ArFragmentManager;
import pl.lodz.p.interiordesignapp.model.ModelInfo;
import pl.lodz.p.interiordesignapp.utils.HelperUtil;

public class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ModelInfo> models;
    private final LayoutInflater inflater;
    private Application application;
    private String category;
    private Fragment fragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ModelAdapter(List<ModelInfo> models, Application application, String category, Fragment fragment) {
        this.models = models;
        this.application = application;
        inflater = LayoutInflater.from(application.getApplicationContext());
        this.category = category;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        switch(viewType) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.category_model, parent, false));
            default:
                return new ModelViewHolder(inflater.inflate(R.layout.model, parent, false));
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    /*@Override
    public void onBindViewHolder(ViewGroup holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(models != null) {
            final String modelName = models.get(position);
            if (modelName != null) {

                Drawable drawable = application.getApplicationContext().getResources().getDrawable(application.getApplicationContext().getResources()
                        .getIdentifier((modelName.split("[.]"))[0], "drawable", application.getPackageName()));
                holder.imageButton.setImageDrawable(drawable);
                holder.imageButton.setOnClickListener(view -> {
                    ArFragmentManager.getInstance().setName(modelName);
                });
            }
        }

    }*/
    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return 0;
        }
        return 1;
    }


    private void initializeModelViewHolder(ModelViewHolder holder, int position) {
        if(models != null) {
            final ModelInfo model = models.get(position);
            if (model != null) {
                Drawable drawable;
                if(model.isDownloaded()){
                    try {
                        Uri uriImage = Uri.parse(model.getDir() + model.getImagePath());
                        InputStream inputStream = application.getApplicationContext().getContentResolver().openInputStream(uriImage);
                        drawable = Drawable.createFromStream(inputStream, uriImage.toString());
                        holder.deleteButton.setVisibility(View.VISIBLE);
                        holder.deleteButton.setOnClickListener(view -> {
                            Uri uriSfb = Uri.parse(model.getDir() + model.getName());
                            HelperUtil.deleteFile(uriImage);
                            HelperUtil.deleteFile(uriSfb);
                            models.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, models.size());
                        });
                        //holder.constraintLayout.addView(deleteButton);
                    } catch (FileNotFoundException e) {
                        drawable = application.getApplicationContext().getResources().getDrawable(R.drawable.question);
                        e.printStackTrace();
                    }
                } else {
                    drawable = application.getApplicationContext().getResources().getDrawable(application.getApplicationContext().getResources()
                            .getIdentifier((model.getName().split("[.]"))[0], "drawable", application.getPackageName()));
                }

                holder.imageButton.setImageDrawable(drawable);
                holder.imageButton.setOnClickListener(view -> {
                    ArFragmentManager.getInstance().setName(model.toString());
                    FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.root_frame, new CategoryFragment());
                    ArFragmentManager.getInstance().getViewPager().setCurrentItem(0);
                    transaction.commit();
                });
            }
        }
    }

    private void initializeCategoryViewHolder(CategoryViewHolder holder) {
        if(category != null && !category.equals("")) {
            Drawable drawable = application.getApplicationContext().getResources().getDrawable(application.getApplicationContext().getResources()
                    .getIdentifier(category + "_category", "drawable", application.getPackageName()));
            holder.imageView.setImageDrawable(drawable);
            String categoryName = application.getApplicationContext().getResources().getString(application.getApplicationContext().getResources()
                    .getIdentifier(category, "string", application.getPackageName()));
            holder.categoryText.setText(categoryName);
            holder.backButton.setOnClickListener(view -> {
                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame, new CategoryFragment());

                transaction.commit();
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (position) {
            case 0:
                initializeCategoryViewHolder((CategoryViewHolder) viewHolder);
                break;
            default:
                initializeModelViewHolder((ModelViewHolder) viewHolder, position - 1);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return models.size() + 1;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageButton imageButton;
        public ImageButton deleteButton;
        public ModelViewHolder(View view) {
            super(view);
            imageButton = view.findViewById(R.id.model);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView categoryText;
        public ImageButton backButton;

        public CategoryViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.categoryImage);
            categoryText = view.findViewById(R.id.categoryText);
            backButton = view.findViewById(R.id.backButton);

        }
    }
}
