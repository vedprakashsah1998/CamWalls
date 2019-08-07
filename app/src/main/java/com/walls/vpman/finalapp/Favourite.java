package com.walls.vpman.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Favourite extends AppCompatActivity {


    Adapter adapter1;
    static List<Wall> walls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        loadFirebase();
    }

    public void loadFirebase()
    {
        walls=new ArrayList<>();
        adapter1=new Adapter(Favourite.this,walls);
        final GridView view=findViewById(R.id.gridView);
        view.setAdapter(adapter1);
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).child("favourites");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                //Toast.makeText(Favourite.this,url,Toast.LENGTH_LONG).show();



               // Boolean b= Boolean.valueOf(dataSnapshot.getKey());

         //       Log.d("hello",dataSnapshot.getKey());


                    String url=dataSnapshot.getKey();

                   url= url.replace("*",".");
                    url=url.replace("@","/");

                    Log.d("Hello",url);


                   // Toast.makeText(Favourite.this,url,Toast.LENGTH_LONG).show();

                    walls.add(new Wall(url,url));

                    adapter1.notifyDataSetChanged();




               /* if (b)
                {
                    String s1=String.valueOf(b);
//                 /*   databaseReference.child(s1).setValue(false);
                  //  Toast.makeText(Favourite.this,s1,Toast.LENGTH_LONG).show();

                    s1.replace("*",".");
                    s1.replace("@","/");

                    walls.add(new Wall(s1,s1));

                    adapter1.notifyDataSetChanged();



                }
                else
                {
                    String s1=String.valueOf(b);
                  //  databaseReference.child(s1).setValue(true);

                   // Toast.makeText(Favourite.this,s1,Toast.LENGTH_LONG).show();


                }*/

              /*  walls.add(new Wall(url,url));
                Collections.shuffle(walls);*/

                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
