package ratio.com.marvelQ.Game.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ratio.com.marvelQ.entities.Option;
import ratio.com.marvelQ.R;

/**
 * Created by FranciscoV on 31/10/15.
 */
public class AdapterOptions extends RecyclerView.Adapter<AdapterOptions.ViewHolder> {

    private List<Option> mDataset;
    //private CuartoDetail il;
    boolean click = false;
    OptionClick optionClick;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.optionname)
        TextView optionname;
        @Bind(R.id.option_container)
        LinearLayout optionContainer;
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            ButterKnife.bind(this,view);

        }

        public void setRowClick(final Option option, final OptionClick optionClick, final int postion){

            optionContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    optionClick.onOptionClick(option,postion);

                }
            });
        }

        private void performClick( Option option,  OptionClick optionClick) {


        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterOptions(List<Option> options,OptionClick optionClick) {
        mDataset = options;
        this.optionClick = optionClick;
        //il=index;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.optionrow, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d("lights", "Mdataset es: " + mDataset.get(position).getTitle());
        holder.optionname.setText(mDataset.get(position).getTitle());

        holder.optionContainer.setBackgroundColor(Color.WHITE);

        holder.setRowClick(mDataset.get(position),optionClick,position);



        /*holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click == false) {
                    click = true;
                    Log.d("click", "click:" + position);
                    if (mDataset[position].answer) {
                        v.setBackgroundColor(Color.GREEN);
                        ((GameActivity) v.getContext()).Correct();
                    } else {
                        v.setBackgroundColor(Color.RED);
                        ((GameActivity) v.getContext()).Incorrect();
                    }
                }


            }
        });*/

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public  void setOptions(List<Option> options){
        mDataset = options;
        notifyDataSetChanged();
        click = false;
    }

    public void setClick(boolean click){
        this.click = click;
    }

    public void ChangeOptionBackgroundAt(int position){
        notifyItemChanged(position);
    }

}