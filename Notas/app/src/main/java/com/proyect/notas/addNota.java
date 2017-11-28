package com.proyect.notas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoNotaTarea;
import com.proyect.notas.Daos.NotaTarea;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;


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
    private NotaTarea mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    public addNota() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nota Parameter 1.
     * @return A new instance of fragment addNota.
     */
    // TODO: Rename and change types and number of parameters
    public static addNota newInstance(NotaTarea nota) {
        addNota fragment = new addNota();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,nota);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = (NotaTarea) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    Button btnOk;
    EditText etName;
    EditText etNote;
    EditText etTime;
    EditText etDate;
    Switch swActivity;
    int year,mes,dia;

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

        if(getArguments()!= null){
            etName.setText(mParam1.getTitulo());
            etNote.setText(mParam1.getDescripcion()+""+mParam1.getId());
            if(mParam1.getTipo()==2){
                swActivity.setChecked(true);
                etDate.setText(mParam1.getFecha().toString());
                etTime.setText(mParam1.getHora().toString());
            }else{
                swActivity.setChecked(false);
            }

        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments()!=null) {

                }else{
                    if (swActivity.isChecked()) {
                        Toast.makeText(getActivity(), "Actividad", Toast.LENGTH_LONG).show();
                        DaoNotaTarea daoNotaTarea = new DaoNotaTarea(getContext());

                        daoNotaTarea.Insert(new NotaTarea(0, etName.getText().toString(), etNote.getText().toString()
                                , 2, Date.valueOf(etDate.getText().toString()), Time.valueOf(etTime.getText().toString())));

                    } else {
                        DaoNotaTarea daoNotaTarea = new DaoNotaTarea(getContext());
                        Toast.makeText(getActivity(), "Nota", Toast.LENGTH_LONG).show();
                        daoNotaTarea.Insert(new NotaTarea(0, etName.getText().toString(), etNote.getText().toString()
                                , 1, null, null));
                    }
                    
                }
            }
        });
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    final Calendar calendario = Calendar.getInstance();
                    int yy = calendario.get(Calendar.YEAR);
                    int mm = calendario.get(Calendar.MONTH);
                    int dd = calendario.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String fecha = String.valueOf(year) +"-"+String.valueOf(monthOfYear)
                                    +"-"+String.valueOf(dayOfMonth);
                            etDate.setText(fecha);

                        }
                    }, yy, mm, dd);

                    datePicker.show();
                }
            }
        });
        etTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    final Calendar calendario = Calendar.getInstance();
                    final int hh = calendario.get(Calendar.HOUR_OF_DAY);
                    final int mm = calendario.get(Calendar.MINUTE);
                    TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            String time = String.valueOf(i) + ":" + String.valueOf(i1) + ":00" ;
                            etTime.setText(time);

                        }
                    }, hh, mm, true);
                    timePicker.show();
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