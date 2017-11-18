package com.proyect.notas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoNotaTarea;
import com.proyect.notas.Daos.NotaTarea;

import java.sql.Date;
import java.sql.Time;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addNota.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addNota#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addNota extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public addNota() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addNota.
     */
    // TODO: Rename and change types and number of parameters
    public static addNota newInstance(String param1, String param2) {
        addNota fragment = new addNota();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button btnOk;
    EditText etName;
    EditText etNote;
    EditText etTime;
    EditText etDate;
    Switch swActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_nota, container, false);
        btnOk = (Button) v.findViewById(R.id.btnOk);
        etName = (EditText) v.findViewById(R.id.etName);
        etNote = (EditText) v.findViewById(R.id.etNote);
        etTime = (EditText) v.findViewById(R.id.etTime);
        etDate = (EditText) v.findViewById(R.id.etDate);
        swActivity = (Switch) v.findViewById(R.id.swActivity);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (swActivity.isActivated()){
                   Toast.makeText(getActivity(),"Actividad", Toast.LENGTH_LONG).show();
                   DaoNotaTarea daoNotaTarea = new DaoNotaTarea(getContext());

                   daoNotaTarea.Insert(new NotaTarea(0,etName.getText().toString(),etNote.getText().toString()
                   ,2, Date.valueOf(etDate.getText().toString()), Time.valueOf(etTime.getText().toString())));

               }else{
                   DaoNotaTarea daoNotaTarea = new DaoNotaTarea(getContext());
                   Toast.makeText(getActivity(),"Nota", Toast.LENGTH_LONG).show();
                   daoNotaTarea.Insert(new NotaTarea(0,etName.getText().toString(),etNote.getText().toString()
                           ,1, null,null));
               }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
