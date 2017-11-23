package com.proyect.notas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyect.notas.Daos.FotoVideo;
import com.proyect.notas.FotoFragment.OnListFragmentInteractionListener;
import com.proyect.notas.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFotoRecyclerViewAdapter extends RecyclerView.Adapter<MyFotoRecyclerViewAdapter.ViewHolder> {

    private List<FotoVideo> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.imageView);
            textView = (TextView) v.findViewById(R.id.textView);
        }
    }

    // Constructor, puedes crear varios según el tipo de contenido.
    public MyFotoRecyclerViewAdapter(List<FotoVideo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyFotoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - Se recupera el elemento del vector con position.
        holder.imageView.setContentDescription(mDataset.get(position).getdireccion());
        holder.textView.setText(mDataset.get(position).getNombre()+"\n"+mDataset.get(position).getdireccion());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
