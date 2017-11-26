package com.proyect.notas;

import android.content.Context;

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
    private final OnListFragmentInteractionListener mlistener;


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

    Context c ;
    // Constructor, puedes crear varios según el tipo de contenido.
    public MyFotoRecyclerViewAdapter(List<FotoVideo> myDataset,OnListFragmentInteractionListener mlistener) {
        mDataset = myDataset;
        this.mlistener=mlistener;
    }

    @Override
    public MyFotoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        c= parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - Se recupera el elemento del vector con position.
        holder.imageView.setContentDescription(mDataset.get(position).getdireccion());
        //Aqui se deberia poder cargar la imagen en el imageView de arriva.
        //DaoImagenVideo da = new DaoImagenVideo(c);
        //List<FotoVideo> lista = da.getAllFotos();
        //Bitmap bitmap = BitmapFactory.decodeFile(lista.get(position).getdireccion());
        //holder.imageView.setImageBitmap(bitmap);
        //File img= new File(mDataset.get(position).getdireccion());
        //holder.imageView.setImageDrawable();

        holder.textView.setText(mDataset.get(position).getNombre()+"\n"+mDataset.get(position).getdireccion());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onListFragmentInteraction(mDataset.get(position));

            }
        });


    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
