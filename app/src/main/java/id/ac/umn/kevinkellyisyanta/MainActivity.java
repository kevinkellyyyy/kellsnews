package id.ac.umn.kevinkellyisyanta;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View view;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);
        if(!session.loggedIn()){
            logout();
        }

        view = findViewById(android.R.id.content);

        BottomNavigationView navBawah = findViewById(R.id.nav_bawah);
        navBawah.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IndonewsFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.menu_indo:
                            selectedFragment = new IndonewsFragment();
                            break;
                        case R.id.menu_inter:
                            selectedFragment = new InternewsFragment();
                            break;
                        case R.id.menu_search:
                            selectedFragment = new SearchnewsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_titiktiga, menu);
        return true;
    }

    final String TAG = MainActivity.class.getSimpleName();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_aboutme:
                Log.e(TAG, "This is from About Me");
                Intent intent = new Intent(MainActivity.this, AboutSaya.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        session.setLoggedIn(false);
        finish();
        Intent intent = new Intent(MainActivity.this, IniLogin.class);
        startActivity(intent);
    }
}
