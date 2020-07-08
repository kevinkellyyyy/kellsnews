package id.ac.umn.kevinkellyisyanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BumperLogonya extends AppCompatActivity {

    TextView namaapp;
    ImageView gambarlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bumper_logonya);

        namaapp = findViewById(R.id.namaApp);
        gambarlogo = findViewById(R.id.gambarLogo);

        Animation bumper = AnimationUtils.loadAnimation(this, R.anim.animasi) ;

        namaapp.startAnimation(bumper);
        gambarlogo.startAnimation(bumper);

        final Intent intent = new Intent(BumperLogonya.this, IniLogin.class);
        Thread timer = new Thread() {
            public void run () {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();

    }
}
