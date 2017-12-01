package com.proyect.notas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoImagenVideoAudio;
import com.proyect.notas.Daos.DaoNotaTarea;
import com.proyect.notas.Daos.FotoVideoAudio;
import com.proyect.notas.Daos.NotaTarea;

import java.io.File;
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
            Toast.makeText(getActivity(),mParam1.getRealizada()==1?"Yes :V":"No :V",Toast.LENGTH_LONG);
        }
    }
    private static final int PHOTO_SELECTED = 1;
    Button btnOk;
    EditText etName;
    EditText etNote;
    EditText etTime;
    EditText etDate;
    Switch swActivity;
    Switch swRealizada;
    ImageView ivAddNota;

    String nombre;
    String path;
    File archivoAG;

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
        swRealizada =(Switch) v.findViewById(R.id.swRealizada);
        ivAddNota =(ImageView) v.findViewById(R.id.ivAddNota);

        etDate.setInputType(InputType.TYPE_NULL);
        etTime.setInputType(InputType.TYPE_NULL);
        if(getArguments()!= null){
          //  path = mParam1.getImagen();

            etName.setText(mParam1.getTitulo());
            etNote.setText(mParam1.getDescripcion());

            swRealizada.setChecked(mParam1.getRealizada()==1 ? true:false);
            if(mParam1.getTipo()==2){
                swActivity.setChecked(true);
                etDate.setText(mParam1.getFecha().toString());
                etTime.setText(mParam1.getHora().toString());
            }else{
                swActivity.setChecked(false);
                stateOfInterface(false);
            }
            /*
            if (mParam1.getImagen()!=null){
                Bitmap bitmap = BitmapFactory.decodeFile(mParam1.getImagen());
                ivAddNota.setImageBitmap(bitmap);
            }
*/
            if (mParam1.getRealizada()==1){
                swRealizada.setChecked(true);
            }else{
                swRealizada.setChecked(false);
            }
        }
        if(swActivity.isChecked()){
            stateOfInterface(true);
        }else{
            stateOfInterface(false);
        }


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments()!=null) {
                    if (swActivity.isChecked()) {
                        if(swRealizada.isChecked()) {

                            new DaoNotaTarea(getContext()).Update(new NotaTarea(mParam1.getId(), etName.getText().toString(),
                                    etNote.getText().toString()
                                    , 2, Date.valueOf(etDate.getText().toString()),
                                    Time.valueOf(etTime.getText().toString()),

                                    1));
                            Toast.makeText(getActivity(), "Actividad realizada", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getActivity(), "Actividad", Toast.LENGTH_LONG).show();
                            new DaoNotaTarea(getContext()).Update(new NotaTarea(mParam1.getId(), etName.getText().toString(), etNote.getText().toString()
                                    , 2, Date.valueOf(etDate.getText().toString()),
                                    Time.valueOf(etTime.getText().toString()),

                                    swRealizada.isChecked()?1:0));
                        }

                    } else {
                        new DaoNotaTarea(getContext()).Update(new NotaTarea(mParam1.getId(), etName.getText().toString(), etNote.getText().toString()
                                , 1, null, null,

                                swRealizada.isChecked()?1:0));
                    }
                }else{
                    if (swActivity.isChecked()) {
                        Toast.makeText(getActivity(), "Actividad", Toast.LENGTH_LONG).show();
                        new DaoNotaTarea(getContext()).Insert(new NotaTarea(0, etName.getText().toString(), etNote.getText().toString()
                                , 2, Date.valueOf(etDate.getText().toString()),
                                Time.valueOf(etTime.getText().toString()),

                                swRealizada.isChecked()?1:0));

                    } else {
                    new DaoNotaTarea(getContext()).Insert(new NotaTarea(0, etName.getText().toString(), etNote.getText().toString()
                                , 1, null, null,

                            swRealizada.isChecked()?1:0));
                        Toast.makeText(getActivity(), "Nota", Toast.LENGTH_LONG).show();
                    }
                    
                }
            }
        });
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    showPickDate();
                }
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickDate();
            }
        });
        etTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    showPickTime();
                }
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickTime();
            }
        });
        swActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                stateOfInterface(b);
            }
        });


        ivAddNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"imgNotas");
        /*
        se verifica si la carpeta ya existe.
            si no existe se crea
        * */
                boolean existe = file.exists();
                if (!existe){
                    existe = file.mkdirs();
                }
                if (existe) {
            /*
            * se crea el nombre que tendra la imagen
            * */
                    Long consecutivo = System.currentTimeMillis() / 1000;
                    nombre = consecutivo.toString() + ".jpg";
                    //se asigna la ruta en que sera guardada
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                            File.separator + "imgNotas" + File.separator + nombre;

                    archivoAG = new File(path);
                    //Se crea el intent que permitira utilizar la camara del celular
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //se le asigna el archivo que representara la imagen
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(archivoAG));
                    startActivityForResult(intent, 1);
                }
            }

        });
        return v;
    }

    public void showPickTime(){
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
    public void showPickDate(){
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

    public void stateOfInterface(Boolean flag){
        etDate.setEnabled(flag);
        etTime.setEnabled(flag);
        swRealizada.setEnabled(flag);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 &&
                resultCode == getActivity().RESULT_OK){

            MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("path",path);
                        }
                    });
            new DaoImagenVideoAudio(getContext()).Insert(new FotoVideoAudio(0,nombre,path,1,null,1));
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ivAddNota.setImageBitmap(bitmap);
            //ivAddNota.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }

    }
}