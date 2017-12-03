package com.proyect.notas;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoImagenVideoAudio;
import com.proyect.notas.Daos.DaoNotaTarea;
import com.proyect.notas.Daos.FotoVideoAudio;
import com.proyect.notas.Daos.NotaTarea;


import java.io.File;
import java.nio.channels.MulticastChannel;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, addNota.OnFragmentInteractionListener,
        NotaTareaFragment.OnListFragmentInteractionListener, FotoFragment.OnListFragmentInteractionListener,
        VideoFragment.OnListFragmentInteractionListener,viewVideo.OnFragmentInteractionListener,
        audioRecorderFragment.OnFragmentInteractionListener,AudioFragment.OnListFragmentInteractionListener,
        MultimediaFragment.OnListFragmentInteractionListener, MultimediaNotaTareFragment.OnListFragmentInteractionListener{

    /*
    Esta variable se utilizara para saber cual de las opciones del menu
    esta seleccionada y dependiendo de su valor ejecutar la acción correspondiente
   */
    int opcion =0;
    private final int  PERMISSIONS =1 ;
    private final int  FOTO = 1;
    private final int  VIDEO = 2;
    private final int AUDIO =3;
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        checkPermissions();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        NotaTareaFragment notaTareaFragment = new NotaTareaFragment();
        fragmentTransaction.replace(R.id.fragment, notaTareaFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * En este switch se asignaran las funciones al boton flotante, se permitiran
                * las funciones de agregar nota, actividad, fotos...
                * los valores se definen mediante la opcion del menu que se selecciono
                * */
                switch (opcion){
                    case 1:
                        Audio();
                        break;
                    case 2:
                       //startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1001);
                        //setFragment(6);
                        //dispatchTakePictureIntent();
                        Camara();

                       break;
                    case 3:
                        //setFragment(7);
                        Video();
                        break;
                    case 4:
                        Snackbar.make(view, "Opcion de agregar nota ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        setFragment(8);
                        break;
                    case 5:
                        break;
                        default:
                            setFragment(8);
                            break;

                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    Locale locale;
    Configuration config = new Configuration();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Español",Toast.LENGTH_LONG).show();
            locale = new Locale("es");
            config.locale =locale;
            getResources().updateConfiguration(config, null);
            Intent refresh = new Intent(MainActivity.this, MainActivity.class);
            startActivity(refresh);
            finish();
            return true;
        }else if(id == R.id.action_settings2){
            Toast.makeText(getApplicationContext(),"English",Toast.LENGTH_LONG).show();
            locale = new Locale("en");
            config.locale =locale;
            getResources().updateConfiguration(config, null);
            Intent refresh = new Intent(MainActivity.this, MainActivity.class);
            startActivity(refresh);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


/*
* Se asignan los valores a la variable opcion
* */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_audio) {
            opcion = 1;
            setFragment(1);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            opcion = 2;
            setFragment(2);
        } else if (id == R.id.nav_slideshow) {
            opcion = 3;
            setFragment(3);
        } else if (id == R.id.nav_note) {
            opcion = 4;
            setFragment(4);
        } else if (id == R.id.nav_share) {
            opcion = 5;
        } else if (id == R.id.nav_send) {
            opcion = 6;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * dependiendo de la opcion, se agrega el fragmento correspondiente
    * */
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        int rotacion = getWindowManager().getDefaultDisplay().getRotation();
        if (rotacion == Surface.ROTATION_0 || rotacion == Surface.ROTATION_180) {
            //...hacer lo que quiera con la pantalla vertical
            switch (position) {

                case 1:
                    txtTitle.setText(getString(R.string.Audios));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    AudioFragment audio = new AudioFragment();
                    fragmentTransaction.replace(R.id.fragment, audio);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 2:
                    txtTitle.setText(getString(R.string.Photos));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    FotoFragment camera = new FotoFragment();
                    fragmentTransaction.replace(R.id.fragment, camera);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 3:
                    txtTitle.setText(getString(R.string.Videos));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    VideoFragment video = new VideoFragment();
                    fragmentTransaction.replace(R.id.fragment, video);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 4:
                    txtTitle.setText(getString(R.string.List_notes));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    NotaTareaFragment notaTareaFragment = new NotaTareaFragment();
                    fragmentTransaction.replace(R.id.fragment, notaTareaFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 8:
                    txtTitle.setText(getString(R.string.title_of_add_nota));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    addNota addnota = new addNota();
                    fragmentTransaction.replace(R.id.fragment, addnota);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
            }
        } else {
            //...hacer lo que quiera con la pantalla horizontal
            switch (position) {

                case 1:
                    txtTitle.setText(getString(R.string.Audios));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    AudioFragment audio = new AudioFragment();
                    fragmentTransaction.replace(R.id.fragment, audio);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 2:
                    txtTitle.setText(getString(R.string.Photos));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    FotoFragment camera = new FotoFragment();
                    fragmentTransaction.replace(R.id.fragment, camera);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 3:
                    txtTitle.setText(getString(R.string.Videos));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    VideoFragment video = new VideoFragment();
                    fragmentTransaction.replace(R.id.fragment, video);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 4:
                    txtTitle.setText(getString(R.string.Notes));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    NotaTareaFragment notaTareaFragment = new NotaTareaFragment();
                    fragmentTransaction.replace(R.id.fragment, notaTareaFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 8:
                    txtTitle.setText(getString(R.string.title_of_add_nota));
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    addNota addnota1 = new addNota();
                    fragmentTransaction.replace(R.id.fragment, addnota1);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
            }
        }


    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(final NotaTarea item, boolean flag) {
        if(flag){

            final DaoNotaTarea daoNotaTarea = new DaoNotaTarea(this);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this).setTitle(getString(R.string.title_dialog)).setItems(R.array.options,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentManager fragmentManager;
                    FragmentTransaction fragmentTransaction;
                    switch(i){
                        case 0:
                            daoNotaTarea.Delete(item.getId());
                            setFragment(4);
                            dialogInterface.dismiss();
                            break;
                        case 1:
                            txtTitle.setText(getString(R.string.title_of_add_nota));

                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            addNota nota = addNota.newInstance(item);
                            fragmentTransaction.replace(R.id.fragment, nota);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            dialogInterface.dismiss();
                            break;
                        case 2:
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MultimediaFragment mf = MultimediaFragment.newInstance(item);
                            fragmentTransaction.replace(R.id.fragment,mf);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            dialogInterface.dismiss();
                            break;
                        case 3:
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MultimediaNotaTareFragment mfnt =new MultimediaNotaTareFragment();
                            Bundle args = new Bundle();
                            args.putSerializable("NotaTarea", item);
                            mfnt.setArguments(args);
                            fragmentTransaction.replace(R.id.fragment,mfnt);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            dialogInterface.dismiss();
                            break;
                    }
                }
            });
            alertDialog.show();
        }else{

        }
    }

    String path;
    String nombre;
    File archivoAG;
    public void Camara()
    {
        //se crea una carpeta en el directorio externo
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"imgNotas");
        /*
        se verifica si la carpeta ya existe.
            si no existe se crea
        * */
        boolean existe = file.exists();
        if (!existe){
            existe = file.mkdirs();
        }
        if (existe){
            /*
            * se crea el nombre que tendra la imagen
            * */
            Long consecutivo = System.currentTimeMillis()/1000;
            nombre = consecutivo.toString()+".jpg";
            //se asigna la ruta en que sera guardada
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                    File.separator+"imgNotas"+File.separator+nombre;

            archivoAG = new File(path);
            //Se crea el intent que permitira utilizar la camara del celular
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //se le asigna el archivo que representara la imagen
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(archivoAG));
            startActivityForResult(intent,FOTO);

        }
    }
    private void Video() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            Long consecutivo = System.currentTimeMillis()/1000;
            nombre = consecutivo.toString()+".mp4";
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                File.separator+"imgNotas"+File.separator+nombre;
            archivoAG = new File(path);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(archivoAG));

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);

        startActivityForResult(intent, VIDEO);
    }
    public void Audio(){
        txtTitle.setText(getString(R.string.addAudio));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        audioRecorderFragment audioRecorder = new audioRecorderFragment();
        fragmentTransaction.replace(R.id.fragment, audioRecorder);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch ((requestCode)){
            case  FOTO:
                //se escanea la imagen
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("path",path);
                            }
                        });
                new DaoImagenVideoAudio(this).Insert(new FotoVideoAudio(0,nombre,path,1,null,1));
                setFragment(2);
                break;
            case VIDEO:
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("path",path);
                            }
                        });
                new DaoImagenVideoAudio(this).Insert(new FotoVideoAudio(0,nombre,path,2,null,1));
                break; 
            case AUDIO:
                Uri uri = data.getData();
                Toast.makeText(this, uri != null ? uri.toString() : null,Toast.LENGTH_LONG+Toast.LENGTH_LONG);
                MediaPlayer mediaPlayer = MediaPlayer.create(this, uri);
                mediaPlayer.start();
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("path",path);
                            }
                        });
                break;
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)){

            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)!=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECORD_AUDIO)){

            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},
                        PERMISSIONS);
            }
        }
    }

    public void openInGallery(String imageId) {
        File file = new File(imageId);
        Uri uri =  Uri.fromFile(file);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        String mime = "*/*";
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(
                mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
            mime = mimeTypeMap.getMimeTypeFromExtension(
                    mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
        intent.setDataAndType(uri,mime);
        startActivity(intent);
    }


    @Override
    public void onListFragmentInteraction(FotoVideoAudio item) {
        openInGallery(item.getDireccion());
    }

    @Override
    public void onListFragmentInteraction(FotoVideoAudio item, boolean flag) {
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        txtTitle.setText(R.string.Videos);
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        viewVideo v = viewVideo.newInstance(item);
        fragmentTransaction.replace(R.id.fragment, v);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(FotoVideoAudio item, int i) {
        txtTitle.setText(R.string.Audios);
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        audioRecorderFragment v = audioRecorderFragment.newInstance(item,"");
        fragmentTransaction.replace(R.id.fragment, v);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onListFragmentInteraction(FotoVideoAudio item, boolean f, boolean f2) {

    }

    @Override
    public void onListFragmentInteraction(FotoVideoAudio item, String s) {

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (item.getTipo()){
            case 1:
                openInGallery(item.getDireccion());
                break;
            case 2:
                txtTitle.setText(R.string.Videos);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                viewVideo v = viewVideo.newInstance(item);
                fragmentTransaction.replace(R.id.fragment, v);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 3:
                txtTitle.setText(R.string.Audios);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                audioRecorderFragment a = audioRecorderFragment.newInstance(item,"");
                fragmentTransaction.replace(R.id.fragment, a);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
}
