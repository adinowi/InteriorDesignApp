package pl.lodz.p.interiordesignapp.adapter;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.model.ArFragmentManager;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private String category;
    private final LayoutInflater inflater;
    private Application application;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(String category, Application application) {
        this.category = category;
        this.application = application;
        inflater = LayoutInflater.from(application.getApplicationContext());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) inflater.inflate(R.layout.category_model, parent, false);
        CategoryAdapter.CategoryViewHolder categoryViewHolder = new CategoryAdapter.CategoryViewHolder(view);
        return categoryViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(category != null && !category.equals("")) {

            Drawable drawable = application.getApplicationContext().getResources().getDrawable(application.getApplicationContext().getResources()
                    .getIdentifier(category + "_category", "drawable", application.getPackageName()));
            holder.imageView.setImageDrawable(drawable);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public CategoryViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.model);
        }
    }
}
