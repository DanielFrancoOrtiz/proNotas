package com.proyect.notas;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.proyect.notas.Daos.FotoVideoAudio;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link viewVideo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link viewVideo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewVideo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    VideoView videoView;
    // TODO: Rename and change types of parameters
    private FotoVideoAudio mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public viewVideo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment viewVideo.
     */
    // TODO: Rename and change types and number of parameters
    public static viewVideo newInstance(FotoVideoAudio param1) {
        viewVideo fragment = new viewVideo();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (FotoVideoAudio) getArguments().getSerializable(ARG_PARAM1);
        }
    }
    MediaPlayer.OnPreparedListener videoViewListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_video, container, false);

        videoView=(VideoView) view.findViewById(R.id.videoView);
        // Prepara la URI del vídeo que será reproducido.

        MediaController mediaController = new MediaController(getActivity());

        // Asigna los controles multimedia a la VideoView.
        videoView.setMediaController(mediaController);

        try {
            // Asigna la URI del vídeo que será reproducido a la vista.
            videoView.setVideoPath(mParam1.getDireccion());
            // Se asigna el foco a la VideoView.
            videoView.requestFocus();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        /*
         * Se asigna un listener que nos informa cuando el vídeo
         * está listo para ser reproducido.
         */

        videoView.setOnPreparedListener(videoViewListener);
        return view;
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
