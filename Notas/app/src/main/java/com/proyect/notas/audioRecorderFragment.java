package com.proyect.notas;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyect.notas.Daos.DaoImagenVideoAudio;
import com.proyect.notas.Daos.FotoVideoAudio;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link audioRecorderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link audioRecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class audioRecorderFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FotoVideoAudio mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    MediaRecorder mr=null;
    MediaPlayer mp= null;
    public audioRecorderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment audioRecorderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static audioRecorderFragment newInstance(FotoVideoAudio param1, String param2) {
        audioRecorderFragment fragment = new audioRecorderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    boolean grabando;
    boolean reproduciendo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (FotoVideoAudio) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mitem=mParam1;
        }
    }
    FloatingActionButton fab;
    FloatingActionButton fab1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        grabando=false;
        View v = inflater.inflate(R.layout.fragment_audio_recorder, container, false);
        fab=v.findViewById(R.id.fabRecordAudio);
        if(getArguments()!=null){
            fab.setVisibility(View.GONE);
        }
        fab1=v.findViewById(R.id.fabPlayAudio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grabando){
                    stopGrabar();
                }else{
                    startGrabar();
                }
            }

        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(reproduciendo){
                        stopReproducir();
                    }else{
                    startReproducir();}


            }
        });


        return v;
    }
    int peticion=1;
    Uri uri;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == peticion) {
            uri = data.getData();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    FotoVideoAudio mitem;
    private void startGrabar() {
        fab.setImageDrawable(getResources().getDrawable(R.drawable.microphone));
        Calendar c = Calendar.getInstance();
        String name = c.get(Calendar.MONTH)+""+c.get(Calendar.DAY_OF_MONTH)
                +""+c.get(Calendar.YEAR)+""+c.get(Calendar.HOUR)+""+c.get(Calendar.MINUTE)+""+c.get(Calendar.SECOND)+".mp3";
        mr = new MediaRecorder();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);
        mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File audio = new File(dir, name);
        FotoVideoAudio audio1=new FotoVideoAudio(0,name,audio.getAbsolutePath()+name,3,null,1);
        new DaoImagenVideoAudio(getActivity()).Insert(audio1);
        mitem=audio1;
        mr.setOutputFile(audio.getAbsolutePath());
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            mr.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mr.start();
        grabando=true;


    }

    private void stopGrabar() {
        fab.setImageDrawable(getResources().getDrawable(R.drawable.microphone_off));
        mr.stop();
        mr.release();
        mr=null;
        grabando=false;
    }
    private void startReproducir(){
        //Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        //Uri data = Uri.parse(mitem.getdireccion());;
        //String mime = "*/*";
        /*MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(
                mimeTypeMap.getFileExtensionFromUrl(data.toString())))
            mime = mimeTypeMap.getMimeTypeFromExtension(
                    mimeTypeMap.getFileExtensionFromUrl(data.toString()));
        intent.setDataAndType(data,mime);
        startActivity(intent);
        */
        //mp = new MediaPlayer();
        fab1.setImageDrawable(getResources().getDrawable(R.drawable.stop));
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File audio = new File(dir.getAbsolutePath()+mitem.getNombre());
        try {
            /*mp.setDataSource(dir.getAbsolutePath()+mitem.getNombre());
            mp.set
            mp.prepareAsync();
            mp.start();*/
            Uri datos = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                    .getPath() + "/"+mitem.getNombre());
            mp = MediaPlayer.create(getActivity(), datos);
            mp.setOnPreparedListener(this);
            reproduciendo=true;
            mp.prepareAsync();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void stopReproducir(){
        fab1.setImageDrawable(getResources().getDrawable(R.drawable.play));
        mp.release();
        mp = null;
        reproduciendo=false;
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

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
        mediaPlayer = null;
        try {

            Uri datos = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                    .getPath() + "/"+mitem.getNombre());
            MediaPlayer mp = MediaPlayer.create(getActivity(), datos);
            mp.setOnPreparedListener(this);
            mp.prepareAsync();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        mediaPlayer.start();
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
