package com.yourdomain.showphotos;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;



//import android.app.ProgressDialog;
//import android.widget.Toast;


public class MainActivity extends Activity
{

    private Context context;
    private ListView pictureList;
    //  private ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        FragmentManager fm = getFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer); //vc identifica o fragmento
        //pelo container dele. Está na página 144 do livro de Anrdroid BigNerd
        //um dia vou experimentar ter mais de um fragmento em uma mesma atividade

     /*   if (progress == null)
        {
            progress = ProgressDialog.show(context, "Atenção", "Carregando Imagens antes de chamar o Fragment");
        }
        //Toast.makeText(getApplicationContext(), "Começando...", Toast.LENGTH_LONG).show();
*/

        if (fragment == null)
        {
            fragment = new ImageListFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        Log.d("MainActivity", "Depois da Criação de fragmento");

        //loadComponents();
        // loadActions()
        //loadImages();
    }





}