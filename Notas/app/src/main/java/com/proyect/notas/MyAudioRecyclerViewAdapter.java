package com.proyect.notas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.notas.AudioFragment.OnListFragmentInteractionListener;
import com.proyect.notas.Daos.DaoImagenVideoAudio;
import com.proyect.notas.Daos.FotoVideoAudio;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAudioRecyclerViewAdapter extends RecyclerView.Adapter<MyAudioRecyclerViewAdapter.ViewHolder> {

    private final List<FotoVideoAudio> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAudioRecyclerViewAdapter(List<FotoVideoAudio> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getNombre()+"\n"+mValues.get(position).getDescripcion());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,0);
                }
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                //Investigar AlertDialog para tex
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle("Descripcion");
                alertDialog.setMessage("Introduce una descripcion");

                final EditText input = new EditText(v.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Aqui se asigna el texto del input

                                mValues.get(position).setDescripcion(input.getText().toString());
                                new DaoImagenVideoAudio(v.getContext()).Update(mValues.get(position));

                                Toast.makeText(v.getContext(),"Descripcion agregada: " ,Toast.LENGTH_LONG).show();

                                holder.mContentView.setText(mValues.get(position).getNombre()+"\n"+mValues.get(position).getDescripcion());
                                new DaoImagenVideoAudio(v.getContext()).Update(mValues.get(position));
                            }

                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });



                alertDialog.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        ImageView imageView;
        TextView mContentView;
        public FotoVideoAudio mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = (ImageView) view.findViewById(R.id.imageViewAudio);
            mContentView = (TextView) view.findViewById(R.id.textView2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
