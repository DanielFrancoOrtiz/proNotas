package com.proyect.notas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoImagenVideoAudio;
import com.proyect.notas.Daos.DaoNotaTarea;
import com.proyect.notas.Daos.FotoVideoAudio;
import com.proyect.notas.Daos.NotaTarea;
import com.proyect.notas.MultimediaFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMultimediaRecyclerViewAdapter extends RecyclerView.Adapter<MyMultimediaRecyclerViewAdapter.ViewHolder> {

    private final List<FotoVideoAudio> mValues;
    private final OnListFragmentInteractionListener mListener;
    NotaTarea item;

    public MyMultimediaRecyclerViewAdapter(List<FotoVideoAudio> items, NotaTarea item, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.item=item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_multimedia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getNombre());
        holder.mContentView.setText(mValues.get(position).getDescripcion());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,true,true);
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext() ,item.getId()+"",Toast.LENGTH_LONG).show();


                if (holder.chkAddNota.isChecked()){

                    mValues.get(position).setIdNota(0);
                    new DaoImagenVideoAudio(view.getContext()).Update(mValues.get(position));
                    holder.chkAddNota.setChecked(true);
                }else{
                    mValues.get(position).setIdNota(item.getId());
                    new DaoImagenVideoAudio(view.getContext()).Update(mValues.get(position));
                    holder.chkAddNota.setChecked(true);
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public FotoVideoAudio mItem;
        public ImageView imageView;
        public CheckBox chkAddNota;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            chkAddNota=(CheckBox)  view.findViewById(R.id.chkAddNota);
            chkAddNota.setEnabled(false);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
