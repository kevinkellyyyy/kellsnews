package id.ac.umn.kevinkellyisyanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IniLogin extends AppCompatActivity {

    EditText edituser, editpassword;
    Button btnlogin;
    SqliteDbHelp db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ini_login);

        db = new SqliteDbHelp(this);
        session = new Session(this);
        edituser = findViewById(R.id.edituser);
        editpassword = findViewById(R.id.editpassword);
        btnlogin = findViewById(R.id.buttonlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        if(session.loggedIn()){
            Intent intent2 = new Intent(IniLogin.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_titiktigalogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_aboutme:
                Intent intent = new Intent(IniLogin.this, AboutSaya.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void login(){
        String user = edituser.getText().toString().trim();
        String pass = editpassword.getText().toString().trim();
        Boolean cek = db.cekPengguna(user, pass);
        if(cek == true){
            session.setLoggedIn(true);
            Toast.makeText(IniLogin.this, "Login berhasil...",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(IniLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(IniLogin.this, "Login GAGAL",Toast.LENGTH_SHORT).show();
        }
    }
}
