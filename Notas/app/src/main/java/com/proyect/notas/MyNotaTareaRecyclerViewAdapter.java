package com.proyect.notas;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.notas.Daos.NotaTarea;
import com.proyect.notas.NotaTareaFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNotaTareaRecyclerViewAdapter extends RecyclerView.Adapter<MyNotaTareaRecyclerViewAdapter.ViewHolder> {

    private final List<NotaTarea> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyNotaTareaRecyclerViewAdapter(List<NotaTarea> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notatarea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf( mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getTitulo());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem,false);
                    if(holder.mDescriptionView.getText()!=""){
                        holder.mDescriptionView.setEnabled(false);
                        holder.mDescriptionView.setText("");
                    }else {
                        holder.mDescriptionView.setText(mValues.get(position).getDescripcion());
                        holder.mDescriptionView.setEnabled(true);
                    }
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onListFragmentInteraction(holder.mItem,true);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues!=null ? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDescriptionView;
        public NotaTarea mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDescriptionView = (TextView) view.findViewById(R.id.description);
            mDescriptionView.setEnabled(false);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
