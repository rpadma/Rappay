package rappay.com.rohit.rapidgrid.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rappay.com.rohit.rapidgrid.Models.TransactionList;
import rappay.com.rohit.rapidgrid.R;

/**
 * Created by Rohit on 3/24/2016.
 */
public class TransAdapter extends RecyclerView.Adapter<TransAdapter.ViewHolder> {

    public JSONArray tjsonArray;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View parent;
        // each data item is just a string in this case
        public TextView TransactionDate_tx ;
        public TextView TransactionAmount_tx;
        public TextView closingBalance_tx;
        public TextView Type_tx;
        public TextView Comments_tx;


        public ViewHolder(View parent) {
            super(parent);


            this.parent = parent;
           // this.TransactionDate_tx = (TextView) parent.findViewById(R.id.trans_TransactionDate);
            //this.TransactionAmount_tx=(TextView) parent.findViewById(R.id.trans_TransactionAmount);
            //this.closingBalance_tx=(TextView) parent.findViewById(R.id.trans_ClosingBalance);
            //this.Type_tx=(TextView) parent.findViewById(R.id.trans_Type);
            //this.Comments_tx=(TextView) parent.findViewById(R.id.trans_Comments);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TransAdapter( JSONArray objtlist) {

        Log.i("inadapter","ffdd");
        tjsonArray = objtlist;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;



    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {


            JSONObject jsonobj = tjsonArray.getJSONObject(position);

          //  Log.i("inside the adapter", objTransactionList.toString());

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.TransactionAmount_tx.setText(holder.TransactionAmount_tx.getText() + jsonobj.getString("transaction_amount"));
            //holder.TransactionDate_tx.setText(holder.TransactionDate_tx.getText() + jsonobj.getString("transactiondate"));
            //holder.closingBalance_tx.setText(holder.closingBalance_tx.getText() + jsonobj.getString("closing_balance"));
            //holder.Type_tx.setText(holder.TransactionAmount_tx.getText() + (jsonobj.getString("credit_debit_flag").equals("Dr.") ? "Debit" : "Credit"));
            //holder.Comments_tx.setText(holder.Comments_tx.getText() + jsonobj.getString("remark"));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return (null != tjsonArray ? tjsonArray.length() : 0);
    }
}
