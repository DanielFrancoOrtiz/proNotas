package com.proyect.notas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyect.notas.Daos.FotoVideoAudio;
import com.proyect.notas.VideoFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyVideoRecyclerViewAdapter extends RecyclerView.Adapter<MyVideoRecyclerViewAdapter.ViewHolder> {

    private final List<FotoVideoAudio> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyVideoRecyclerViewAdapter(List<FotoVideoAudio> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    Context c ;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c= parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card_view_videos, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textView.setText(holder.mItem.getNombre());
        /*holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getdireccion());

        holder..setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListFragmentInteraction(holder.mItem,true);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues!=null ? mValues.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView textView;
        public FotoVideoAudio mItem;
        public ViewHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.imageView2);
            textView = (TextView) v.findViewById(R.id.textView2);
        }

    }
}
