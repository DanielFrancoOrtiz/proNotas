package com.proyect.notas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.proyect.notas.Daos.DaoNotaTarea;
import com.proyect.notas.Daos.NotaTarea;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Servicio extends Service {

    @Override
    public void onCreate(){

    }

    hilo h;
    @Override
    public int onStartCommand(Intent intent,int flag,int idProcess){

        try{
        if(h==null) {
            h = new hilo();
            h.start();
        }
        }catch (Exception err){

        }

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        try{
        if(h.isAlive()) {
            h.stop();
        }
        }catch (Exception err){

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    int x=0;
    public void btnNoti_click(String Titulo,String Descripcion,int indice) {

        x=x+1;
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

       Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        int icono = R.mipmap.calendar;

        long hora = System.currentTimeMillis();

        Intent i = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle(Titulo)
                .setContentText(Descripcion)
                .setWhen(hora)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true)
                .setSound(defaultSound)
        ;

        mNotifyMgr.notify(x, mBuilder.build());

    }


    public class hilo extends Thread{
        @Override
        public  void  run(){
            while (true){

                try {

                    DaoNotaTarea dao = new DaoNotaTarea(getApplicationContext());


                    final Calendar calendario = Calendar.getInstance();
                    final int hh = calendario.get(Calendar.HOUR_OF_DAY);
                    final int mm = calendario.get(Calendar.MINUTE);
                    List<NotaTarea> lista ;
                    lista = dao.getActivitys();
                    if (lista==null){
                        lista = new ArrayList<>();
                    }
                    for (int i = 0; i < lista.size(); i++) {
                        if (String.valueOf(lista.get(i).getHora()).equals(
                                (String.valueOf(hh).matches("[0-9][0-9]")?hh:"0"+hh)+":"+
                                        (String.valueOf(mm).matches("[0-9][0-9]")?mm:"0"+mm)+":00") ) {
                            lista.get(i).setRealizada(1);
                            dao.Update(lista.get(i));

                            btnNoti_click(lista.get(i).getTitulo(),lista.get(i).getDescripcion(),i);
                        }

                    }

                } catch (Exception e) {
                    Log.e("Servicio>>>>>>>>>>>>>>>","Error!!! :( "+e);
                }finally {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
