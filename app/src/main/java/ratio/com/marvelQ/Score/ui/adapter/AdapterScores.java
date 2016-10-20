package ratio.com.marvelQ.Score.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ratio.com.marvelQ.R;

/**
 * Created by FernandoV on 31/10/15.
 */
public class AdapterScores extends RecyclerView.Adapter<AdapterScores.ViewHolder> {


    private static final String TAG = "ScoresAdapter";
    private JSONArray mDataset;
    //private CuartoDetail il;
    boolean click = false;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        @Bind(R.id.scoreposition)
        TextView scoreposition;
        @Bind(R.id.scorename)
        TextView scorename;
        @Bind(R.id.scoreamount)
        TextView scoreamount;
        @Bind(R.id.score_row_container)
        RelativeLayout scoreRowContainer;


        public ViewHolder(View v) {
            super(v);
            view = v;
            ButterKnife.bind(this, view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterScores(JSONArray options) {
        mDataset = options;
        //il=index;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scorerow, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // renderea cada scorrerow layoutpor cada eemento del arreglo Json de scores, que viene del servidor
        Log.d(TAG,"bindviewholder");

        try {
            holder.scorename.setText(mDataset.getJSONObject(position).getJSONObject("user").getString("name"));
            holder.scoreamount.setText(mDataset.getJSONObject(position).getString("score"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void setScores(JSONArray scores){

        mDataset = scores;
        Log.d(TAG,"scores:"+scores.length());
        notifyDataSetChanged();
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}