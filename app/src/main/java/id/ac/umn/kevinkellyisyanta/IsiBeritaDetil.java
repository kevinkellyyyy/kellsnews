package id.ac.umn.kevinkellyisyanta;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class IsiBeritaDetil extends AppCompatActivity {

    public ImageView imgBeritaD;
    public TextView judulBeritaD, penulisBeritaD, rilisD, contentD, urlD;
    public WebView webView;
    private Notif helper;
    final Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_berita_detil);

        helper = new Notif(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent detailIntent = getIntent();
        final String judul = detailIntent.getStringExtra("extrajudul");
        String rilis = detailIntent.getStringExtra("extrarilis");
        String img = detailIntent.getStringExtra("extraimg");
        String penulis = detailIntent.getStringExtra("extrapenulis");
        String content = detailIntent.getStringExtra("extracontent");
        final String link = detailIntent.getStringExtra("extralink");

        imgBeritaD = findViewById(R.id.imgdetail);
        judulBeritaD = findViewById(R.id.titledetail);
        penulisBeritaD = findViewById(R.id.authordetil);
        rilisD = findViewById(R.id.publishedAtDetail);
        contentD = findViewById(R.id.content);
//        urlD = findViewById(R.id.urldetail);
//        urlD.setMovementMethod(LinkMovementMethod.getInstance());

        Picasso.get().load(img).fit().centerCrop().into(imgBeritaD);
        judulBeritaD.setText(judul);
        penulisBeritaD.setText(penulis);
        rilisD.setText(rilis);
        contentD.setText(content);
//        urlD.setText(link);

        webView = findViewById(R.id.webviewtampil);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);

        FloatingActionButton fab = findViewById(R.id.favobut);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Notification.Builder builder = helper.getChannelNotification(judul,link);
                        helper.getManager().notify(1, builder.build());
                    }
                }, 5000);
                Toast.makeText(IsiBeritaDetil.this, "The data has been registered",Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton sharebut = findViewById(R.id.sharebut);
        sharebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareint = new Intent(Intent.ACTION_SEND);
                shareint.setType("text/plain");
                String shareurl = link;
                shareint.putExtra(Intent.EXTRA_TEXT, shareurl);
                startActivity(Intent.createChooser(shareint, "Share this news via ..."));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
