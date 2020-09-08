package com.example.jenny.datingfuwa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Jenny on 06.01.2018.
 */

public class RegistActivity extends AppCompatActivity {

    //Create Instance of SQL Database
    DbHelper myDB;

    ImageView iv;
    Button btn;
    Intent intent1;
    final int requcode = 1;
    Uri bilduri;
    Bitmap bm;
    InputStream ism;

    //Edit Text in Database
    EditText editUsername, editPasswort, editAltere, editEmail, editName, editBeschreibung;
    String editGeschlecht, editSuche;
    ImageView editPicture;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);


        //FOTOOOOOOOOOOOOOOOOOOO

        iv = (ImageView) findViewById(R.id.editImageView);
        btn = (Button) findViewById(R.id.button11);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1, requcode);
            }
        });

        final Button button = findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        final Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RegistActivity.this, ProfilActivity.class);
                startActivity(i);
            }
        });


        //Create new Instance of SQL Database
        myDB = new DbHelper(this);

        //Edit Database
        editUsername = (EditText) findViewById(R.id.edit_Username);
        editPasswort = (EditText) findViewById(R.id.edit_Password);
        editAltere = (EditText) findViewById(R.id.edit_Altere);
        editName = (EditText) findViewById(R.id.edit_Name);
        editEmail = (EditText) findViewById(R.id.edit_Email);
        editBeschreibung = (EditText) findViewById(R.id.edit_Beschreibung);
        editPicture = (ImageView) findViewById(R.id.editImageView);
        btnAddData = (Button) findViewById(R.id.button4);

        AddData();

        if (findViewById(R.id.edit_weiblich).isActivated()) {
            editGeschlecht = "weiblich";
        }
        if (findViewById(R.id.edit_maennlich).isActivated()) {
            editGeschlecht = "maennlich";
        }

        if (findViewById(R.id.edit_m).isActivated()) {
            editSuche = "m";
        }
        if (findViewById(R.id.edit_w).isActivated()) {
            editSuche = "w";
        }
        if (findViewById(R.id.edit_beides).isActivated()) {
            editSuche = "beides";
        }
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    //String email, String beschreibung
                    public void onClick(View view) {
                        boolean isInserted = myDB.insertData(
                                editUsername.getText().toString(),
                                editPasswort.getText().toString(),
                                editAltere.getText().toString(),
                                editName.getText().toString(),
                                editGeschlecht,
                                editEmail.getText().toString(),
                                editSuche,
                                toByteArray(((BitmapDrawable)editPicture.getDrawable()).getBitmap()),
                                editBeschreibung.getText().toString()
                        );

                        if(isInserted == true){
                            Toast.makeText(RegistActivity.this,"Data inserted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(RegistActivity.this,"Data not inserted", Toast.LENGTH_LONG).show();
                        }
                        Intent i = new Intent(RegistActivity.this, ProfilActivity.class);
                        startActivity(i);
                    }
                }

        );

    }

    public static byte[] toByteArray(Bitmap bitmap){
        //convert from bitmap to byteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){

            if (requestCode == requcode) {
                bilduri = data.getData();
                try{
                    ism = getContentResolver().openInputStream(bilduri);
                    bm = BitmapFactory.decodeStream(ism);
                    iv.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}



