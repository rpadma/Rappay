package rappay.com.rohit.rapidgrid.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rappay.com.rohit.rapidgrid.Listeners.RecyclerViewOnItemClickListener;
import rappay.com.rohit.rapidgrid.R;

/**
 * Created by Rohit on 3/25/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static List<String> dataSet = new ArrayList<>();
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View parent;
        public TextView textView;
        public ViewHolder(View parent) {
            super(parent);

            this.parent = parent;
           // this.textView = (TextView) parent.findViewById(R.id.data_text);
            this.textView=(TextView)parent.findViewById(R.id.data_text);

            //set the click listener that invokes the recycler callback listener
            this.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewOnItemClickListener.onItemClick(v, getPosition());
                }
            });
        }
    }

    public RecyclerViewAdapter(RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void addItem(String data) {
        //inserts an item at the start of the list
        dataSet.add(0, data);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
       dataSet.remove(position);
        notifyItemRemoved(position);
     }

    public boolean hasData() {
        return !dataSet.isEmpty();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
