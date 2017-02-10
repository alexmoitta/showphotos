package com.yourdomain.showphotos;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import com.yourdomain.showphotos.model.Image;

/**
 * Created by asantos on 04/06/15.
 */
public class ImageListFragment extends Fragment
{
    private static final String TAG = "ImageListFragment";
    // private ProgressDialog progress;
    private Context context;
    private List<Image> album = new ArrayList<Image>();

    private RecyclerView mImageRecyclerListView;
    private ImageItemAdapter mImageItemAdapter;
    private ProgressBar mProgressBar;


    private int indiceImagem;
    private ImageView currentImage;
    FetchItemsTask fetchItemsTask = new FetchItemsTask();




    //public ImageDownloader<ImageView> imageDownloader;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //isso faz com que o fragmento que tem
        //asynctask, socket, thread não perca esses dados transitórios
        //durante uma mudança de configuração que mata a atividade mãe
        //mais detalhes em: http://www.androiddesignpatterns.com/2013/04/retaining-objects-across-config-changes.html


        context = getActivity();


        indiceImagem = 0; //iniciando o índice de imagens


        //primeira carga de endereços das imagens
        fetchItemsTask.execute();


        //iniciando a thread em background
        // por enquanto não vou baixar as imagens
        // não vou ter de lidar com thread para baixar as imagens
        // só fazer um RecyclerView para ler os textos

        /*imageDownloader = new ImageDownloader<ImageView>();
        imageDownloader.start();
        imageDownloader.getLooper();
        Log.i(TAG,"Background thread started");
        */
    }

 /*   @Override
    public void onDestroy()
    {
        super.onDestroy();
        imageDownloader.quit(); //tem de lembrar de matar o HandlerThread
        Log.i(TAG,"Background thread destroyed");
    }
*/


/* não vou usar dessa maneira, por enquanto.
    public void setupAdapter()
    {
        if(isAdded())
        {
            imageRecyclerListView.setAdapter(new ImageItemAdapter(album));
        }
    }
*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        // só não entendi direito o que é esse terceiro parâmetro na chamada acima
        // ele está com valor false para não ficar associado com o container,
        // mas com a Activity?


        mImageRecyclerListView = (RecyclerView) v.findViewById(R.id.image_recycler_view);
        mImageRecyclerListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //RecyclerView precisa de um Layout Manager para funcionar
        //Se não tiver um, vai dar crash
        //O Layout Manager lida com o posicionamento dos elementos e define o comportamento
        // de scroll

        //mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);



        //updateUI();

        //setupAdapter(); //olhei como criar isso inicialmente na página 429 do livro Nerd's Ranch

        assert v != null;


        return v;
    }

    private void updateUI()
    {


        List<Image> imageList = album;

        if(isAdded() ) //esse isAdded é bom para verificar se o Fragment já foi adicionado pela Activity
        {
            Log.d("updateUI", "tudo certo com o fragmento");
            mImageItemAdapter = new ImageItemAdapter(imageList);
            if (mImageItemAdapter == null)
            {
                Log.e("updateUI","mImageItemAdapter é nulo");
            }
            mImageRecyclerListView.setAdapter(mImageItemAdapter);
        }
        else
        {
            Log.e("updateUI", "Erro com o fragmento");
        }
    }

    //essa classe vai mudar para outro lugar
   /* private void renderizaImagem()
    {


        Log.d("renderizaImagem", "renderizaImagem");
        Image image = album.get(indiceImagem);

        AsyncTask<String, Void, Bitmap> tarefa = new AsyncTask<String, Void, Bitmap>()
        {
            @Override
            protected Bitmap doInBackground(String... params)
            {

                Bitmap bitmap = null;

                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(false);
                    connection.setInstanceFollowRedirects(false);
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (Exception ex) {
                    // if(progress.isShowing())
                    //   progress.dismiss();
                    Log.e("MyActivity", ex.getMessage());
                }

                return bitmap;
            }

        };

        // tarefa.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, image.getPhoto());
        tarefa.execute(image.getPhoto());
    }
    */

    private class FetchItemsTask extends AsyncTask<Void, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(Void... params)
        {
            //Integer downloadPercentage = 0;
            //Log.d("doInBackGround", "downloadPercentage" + downloadPercentage);
            //publishProgress(downloadPercentage);
            Log.d("doInBackGround", "Passo1");

            JSONObject jsonObject = null;
            Log.d("doInBackGround", "Passo2");
            try {
                jsonObject = new JSONObject();

                Log.d("doInBackGround", "Passo3");
                DefaultHttpClient client = new DefaultHttpClient();
                // HttpGet get = new HttpGet(String.valueOf(R.string.images_url));
                // era de esporte HttpGet get = new HttpGet("http://esporte.uol.com.br/futebol/album/2015/03/20/anderson-silva-visita-o-ct-do-corinthians.htm?app=placar-futebol&formato=json&plataforma=iphone&version=2");
                HttpGet get = new HttpGet("http://noticias.uol.com.br/album/olho-magico/2015/03/19/olho-magico.htm?app=placar-futebol&formato=json&plataforma=iphone&version=2");
                Log.d("doInBackGround", "Passo4");
                Log.d("doInBackGround", "Passo40");

                HttpResponse response = client.execute(get);
                Log.d("doInBackGround", "Passo41");
                HttpEntity entity = response.getEntity();
                Log.d("doInBackGround", "Passo42");
                InputStream inputStream = entity.getContent();
                Log.d("doInBackGround", "Passo43");
                InputStreamReader readerBinario = new InputStreamReader(inputStream);
                Log.d("doInBackGround", "Passo44");
                BufferedReader readerTexto = new BufferedReader(readerBinario);
                Log.d("doInBackGround", "Passo45");
                String linha = "";
                Log.d("doInBackGround", "Passo46");
                String conteudoJson = "";
                Log.d("doInBackGround", "Passo47");

                while ((linha = readerTexto.readLine()) != null) {
                    conteudoJson += linha;
                }

                Log.d("doInBackGround", "Passo5");
                Log.d("doInBackGroud", "conteudoJson" + conteudoJson);
                jsonObject = new JSONObject(conteudoJson);
                Log.d("doInBackGround", "jsonObject" + jsonObject.toString());
                Log.d("doInBackGround", "Passo6");
                //downloadPercentage = 100;
            } catch (Exception ex) {
                //downloadPercentage = -1;
                //Log.d("doInBackGround", "downloadPercentage" + downloadPercentage);
                //publishProgress(downloadPercentage);
                Log.e("Erro1", ex.getMessage());
            }

            Log.d("doInBackGround", "Saindo");
            //Log.d("doInBackGround", "downloadPercentage" + downloadPercentage);
            //publishProgress(downloadPercentage);
            return jsonObject;


        }


        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            Log.d("PostExecute", "Entrou");
            //                super.onPostExecute(jsonObject);

            try {

                JSONObject albumJson = jsonObject.getJSONObject("album");
                //JSONArray matrizDeFotos = albumJson.getJSONArray("photos");

                JSONArray matrizDeFotos = albumJson.getJSONArray("photos");

                JSONObject objetoDaMatriz;

                for (int i = 0; i < matrizDeFotos.length(); i++) {

                    objetoDaMatriz = matrizDeFotos.getJSONObject(i);
                    Image image = new Image();
                    image.setPhoto(objetoDaMatriz.getString("photo"));
                    image.setDetails(objetoDaMatriz.getString("details"));
                    image.setTitle(objetoDaMatriz.getString("title"));
                    image.setThumb(objetoDaMatriz.getString("thumb"));
                    image.setCredit(objetoDaMatriz.getString("credit"));
                    album.add(image);
                    Log.d("PostExecute", "Foto" + i);

                }

                //setupAdapter(); //olhei como criar isso inicialmente na página 429 do livro Nerd's Ranch


                //  if(progress.isShowing())
                //    progress.dismiss();

                updateUI();

            }
            catch (Exception ex)
            {
                //if(progress.isShowing())
                //  progress.dismiss();
                //Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Erro2", ex.getMessage());
            }
        }

       /* protected void onProgressUpdate(Integer... progress)
        {
           // setProgressPercent(progress[0]);
        } */


    }






    private class PhotoHolder extends RecyclerView.ViewHolder
    {

        private TextView photoTitle;
        private TextView photoCredit;
        private ImageView imagePicture;
        private Image mImage;
        //private FetchPictureTask fetchPictureTask = new FetchPictureTask();

        public PhotoHolder(View itemView)
        {
            super(itemView);
            this.photoTitle = (TextView) itemView.findViewById(R.id.photo);
            this.photoCredit = (TextView) itemView.findViewById(R.id.credit);
            //por enquanto só será exibido o título e crédito da foto
            //depois vejo como exibir a foto.
            this.imagePicture = (ImageView) itemView.findViewById(R.id.imagePicture);

        }

        public void bindPhoto(Image image)
        {
            FetchPictureTask fetchPictureTask = new FetchPictureTask();
            mImage = image;
            photoTitle.setText(image.getTitle());
            photoCredit.setText(image.getCredit());
            fetchPictureTask.execute(image);
            //imagePicture.setImageBitmap();
        }


        private class FetchPictureTask extends AsyncTask <Image, Void, Bitmap>
        {


            @Override
            protected Bitmap doInBackground(Image... params)
            {

                Bitmap bitmap = null;
                Image imageToBeLoaded;
                try
                {
                    imageToBeLoaded = params[0];
                    URL url = new URL(imageToBeLoaded.getPhoto());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(false);
                    connection.setInstanceFollowRedirects(false);
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    Log.d("FetchPictureTask", "Carregando Imagem");

                } catch (Exception ex) {
                    // if(progress.isShowing())
                    //   progress.dismiss();
                    Log.e("FetchPictureTask", ex.getMessage());
                }

                return bitmap;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap)
            {
                //super.onPostExecute(bitmap);
                imagePicture.setImageBitmap(bitmap);
            }
        }


    }



    private class ImageItemAdapter extends RecyclerView.Adapter<PhotoHolder>
    {
        private List<Image> mImageList;

        public ImageItemAdapter(List<Image> imageList)
        {
            Log.d("Criação do ImageItemAdapter", "construtor chamado");
            mImageList = imageList;

        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            View view = layoutInflater.inflate(R.layout.picture_list_item,parent,false);

            Log.d("onCreateViewHolder", "onCreateViewHolder");
            return new PhotoHolder(view);

        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position)
        {
            Image image = mImageList.get(position);
            Log.d("onBindViewHolder","position " + position);

            //holder.photoTitle.setText(image.getTitle());
            holder.bindPhoto(image);
        }

        @Override
        public int getItemCount()
        {
            Log.d("getItemCountDentroDoAdapter","Passando " + mImageList.size());
            return mImageList.size();
        }
    }




}





