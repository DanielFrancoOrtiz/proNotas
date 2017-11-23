package com.proyect.notas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoImagenVideo;
import com.proyect.notas.Daos.FotoVideo;
import com.proyect.notas.Daos.NotaTarea;
import com.proyect.notas.dummy.DummyContent;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, addNota.OnFragmentInteractionListener,addVideo.OnFragmentInteractionListener,addPhoto.OnFragmentInteractionListener,
        NotaTareaFragment.OnListFragmentInteractionListener, Video.OnFragmentInteractionListener, FotoFragment.OnListFragmentInteractionListener  {

    /*
    Esta variable se utilizara para saber cual de las opciones del menu
    esta seleccionada y dependiendo de su valor ejecutar la acción correspondiente
   */
    int opcion =0;
    private final int  PERMISSIONS =1 ;
    private final int  FOTO = 1;
    private final int  VIDEO = 2;
    private final int AUDIO =3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkPermissions();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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

        if (id == R.id.nav_camera) {
            opcion = 1;
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
        switch (position) {

            case 1:

                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FotoFragment camera = new FotoFragment();
                fragmentTransaction.replace(R.id.fragment, camera);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Video video = new Video();
                fragmentTransaction.replace(R.id.fragment, video);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                NotaTareaFragment notaTareaFragment = new NotaTareaFragment();
                fragmentTransaction.replace(R.id.fragment, notaTareaFragment);
                fragmentTransaction.commit();
                break;
            case 5:
                break;
            case 6:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                addPhoto addPhoto = new addPhoto();
                fragmentTransaction.replace(R.id.fragment, addPhoto);
                fragmentTransaction.commit();
                break;
            case 7:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                addVideo addVideo = new addVideo();
                fragmentTransaction.replace(R.id.fragment, addVideo);
                fragmentTransaction.commit();
                break;
            case 8:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                addNota addnota = new addNota();
                fragmentTransaction.replace(R.id.fragment, addnota);
                fragmentTransaction.commit();
                break;
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(NotaTarea item) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        addNota nota = addNota.newInstance(item);
        fragmentTransaction.replace(R.id.fragment, nota);
        fragmentTransaction.commit();
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
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        Long consecutivo = System.currentTimeMillis()/1000;
        nombre = consecutivo.toString()+".mp4";
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                File.separator+"imgNotas"+File.separator+nombre;
        archivoAG = new File(path);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(archivoAG));
        startActivityForResult(intent, AUDIO);
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
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                //ImageView im.setImageBitmap(bitmap);
                new DaoImagenVideo(this).Insert(new FotoVideo(0,nombre,path));
                break;
            case VIDEO:
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("path",path);
                            }
                        });
                break; 
            case AUDIO:
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


    @Override
    public void onListFragmentInteraction(FotoVideo item) {

    }
}
