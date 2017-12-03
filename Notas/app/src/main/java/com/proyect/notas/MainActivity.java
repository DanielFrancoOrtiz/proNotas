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
    private TextView txtTitle;
    private String path;
    private String nombre;
    private File archivoAG;

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
                       //caso de agregar foto
                       verificarCarper();

                       break;
                    case 3:
                        //caso de agregar video
                        verificarCarper();
                        break;
                    case 4:
                       //cso de agregar nota
                        setFragment(8);
                        break;
                    case 5:
                        break;
                        default:
                            //como default esta la opcion de agregar nota
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
        //operacion para el cambio de idiomas
        if (id == R.id.action_settings) {
            //se crea un AlertDialog para poner las opciones de idiomas disponibles
            AlertDialog.Builder b = new AlertDialog.Builder(this);

            //obtiene los idiomas del array de string.xml
            String[] types = getResources().getStringArray(R.array.languages);
            //dependiendo del item que seleccion se elige el nuevo idioma
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    switch(which){
                        case 0:
                            locale = new Locale("en");
                            config.locale =locale;
                            break;
                        case 1:
                            locale = new Locale("es");
                            config.locale =locale;
                            break;
                    }
                    //se establecen los nuevos valores de configuracion y se refresca el programa
                    getResources().updateConfiguration(config, null);
                    Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(refresh);
                    finish();
                }

            });

            b.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*
            * Se asignan los valores a la variable opcion y se colocan los fragmentos correspondientes
        * */
        int id = item.getItemId();

        if (id == R.id.nav_audio) {
            opcion = 1;
            setFragment(1);
        } else if (id == R.id.nav_gallery) {
            opcion = 2;
            setFragment(2);
        } else if (id == R.id.nav_slideshow) {
            opcion = 3;
            setFragment(3);
        } else if (id == R.id.nav_note) {
            opcion = 4;
            setFragment(4);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setFragment(int position) {
         /*
    * dependiendo de la opcion, se agrega el fragmento correspondiente
    * */
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        int rotacion = getWindowManager().getDefaultDisplay().getRotation();
        if (rotacion == Surface.ROTATION_0 || rotacion == Surface.ROTATION_180) {
            //pantalla vertical
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
            //En caso de pantalla horizontal
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
            //Se muestra el AlerDialog que permite manipular las Notas agregadas, dependiendo del item
            //puede eliminar, editar, agregar multimedia o mostrar la multimedia de la nota
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this).setTitle(getString(R.string.title_dialog)).setItems(R.array.options,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentManager fragmentManager;
                    FragmentTransaction fragmentTransaction;
                    switch(i){
                        case 0:
                            //caso de eliminar la nota
                            daoNotaTarea.Delete(item.getId());
                            setFragment(4);
                            dialogInterface.dismiss();
                            break;
                        case 1:
                            //caso de Editar la nota
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
                            //Caso de agregar multimedia a la nota
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MultimediaFragment mf = MultimediaFragment.newInstance(item);
                            fragmentTransaction.replace(R.id.fragment,mf);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            dialogInterface.dismiss();
                            break;
                        case 3:
                            //caso de mostrar la multimedia de la nota
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


    public void verificarCarper(){
       //metodo para verificar si la carpeta en donde se guardaran las fotos y los videos existe,
        // en caso de que no exista se creara la carpeta
        File file = new Info().creaCarpeta();
        boolean existe = file.exists();
        if (!existe){//se crea una carpeta en el directorio externo
            existe = file.mkdirs();
        }
        //dependiendo de la opcion seleccionada, se mandara al metodo de tomar una foto o al metodo
        //de gragar un video
        if (existe){
            switch (opcion){
                case 2:
                    Camara();
                    break;
                case 3:
                    Video();
                    break;
            }

        }
    }

    public void Camara()
    {
        //se asigna el nombre de la imagen
        nombre = new Info().NRImagen();
        //se asigna la ruta en que sera guardada
        path = new Info().direccion(nombre);
        archivoAG = new File(path);
        //Se crea el intent que permitira utilizar la camara del celular
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //se le asigna el archivo que representara la imagen
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(archivoAG));
        startActivityForResult(intent,FOTO);


    }
    private void Video() {
        //se asigna el nombre del video
        nombre = new Info().NRVideo();
        //se asigna la ruta en donde se guardara
        path =new Info().direccion(nombre);
        archivoAG = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(archivoAG));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(intent, VIDEO);
    }
    public void Audio(){
        //se llama al fragmento que permitira grabar el audio
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
            case  FOTO://Se guada la foto en la base de datos y se muestra el fragmento de fotos
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("path",path);
                            }
                        });
                new DaoImagenVideoAudio(this).Insert(new FotoVideoAudio(0,nombre,path,1,null,0));
                setFragment(2);
                break;
            case VIDEO://Se guada el video en la base de datos y se muestra el fragmento de videos
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("path",path);
                            }
                        });
                new DaoImagenVideoAudio(this).Insert(new FotoVideoAudio(0,nombre,path,2,null,0));
            break;
            case AUDIO://se obtienen los datos del audio, se crea un mediaplayer y se ejecuta
                Uri uri = data.getData();
                MediaPlayer mediaPlayer = MediaPlayer.create(this, uri);
                mediaPlayer.start();
                break;
        }
    }

    private void checkPermissions() {
        //se verifican los permisos
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
        //se permite abrir la imagen en la galeria
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
        // se muestra el fragmento en donde se reproducira el Video
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
        //se muestra el fragmento en donde se reproducira el audio
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
