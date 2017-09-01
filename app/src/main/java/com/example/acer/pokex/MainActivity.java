package com.example.acer.pokex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextDirectionHeuristics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  Button left;
    Button right;
    ImageView Poke;
    OkHttpClient okHttpClient;
    TextView pokeName;
    int pokeNo=1;
    TextView no;
    Context c;
    String pokemonName;
    String pokemonImage;
    int pokeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        left=(Button)findViewById(R.id.left);
        left.setOnClickListener(this);
        right=(Button) findViewById(R.id.right);
        right.setOnClickListener(this);
        Poke=(ImageView)findViewById(R.id.image1);
        pokeName=(TextView)findViewById(R.id.pokename);
        no=(TextView)findViewById(R.id.pokeno);

        c=this;
Button b=(Button)findViewById(R.id.button2);
        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
      if(preferences.contains("NAME"))
      {
          pokeName.setText(preferences.getString("NAME","balbasaur"));





      }
      if(preferences.contains("NO"))
      {
          no.setText(preferences.getInt("NO",1)+"");
          pokeNo=preferences.getInt("NO",1);
          okHttpClient=new OkHttpClient();
          connection(pokeNo);

      }
      else{
          okHttpClient=new OkHttpClient();
          connection(pokeNo);

      }
        if(preferences.contains("URL"))
        {
            Picasso.with(getBaseContext()).load(preferences.getString("URL","abc.com")).into(Poke);

        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder=     new android.support.v7.app.AlertDialog.Builder(c);


                builder.setTitle("Enter Pokemon No");

// Set up the input
                final EditText input = new EditText(c);

                input.setInputType(InputType.TYPE_CLASS_NUMBER );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(input);
// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if(Integer.parseInt(m_Text)<=721&&Integer.parseInt(m_Text)>=1)
                        {pokeNo=Integer.parseInt(m_Text);
                        connection(Integer.parseInt(m_Text));}
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });





    }
int k;
    public void connection(int s)
    {

        k=s;
        Request request=new Request.Builder().url("http://pokeapi.co/api/v2/pokemon/"+s+"/").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                  String s=response.body().string();
                Gson gson=new Gson();
                final BasicInfo info =  gson.fromJson(s,BasicInfo.class);
                pokemonName=info.getName();
                pokeNumber=k;
                pokemonImage=info.getSprites().getFront_default();


                MainActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        pokeName.setText(info.getName());
                        no.setText(k+"");
                        Picasso.with(getBaseContext()).load(info.getSprites().getFront_default()).into(Poke);
                    }
                });




            }
        });



    }

    @Override
    protected void onPause() {

        SharedPreferences preferences= getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("NAME",pokemonName);
        editor.putInt("NO",pokeNumber);
        editor.putString("URL",pokemonImage);
       editor.commit();

        super.onPause();


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.left)
        {
              if(pokeNo>=2)
              { connection(--pokeNo);}
        }

        if(v.getId()==R.id.right)
        {
            if(pokeNo<=720)
            connection(++pokeNo);
        }
    }
}
