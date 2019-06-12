package com.walls.vpman.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import id.zelory.compressor.Compressor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Photography extends AppCompatActivity {

    Toolbar toolbar;
    boolean isOpen=false;
    private final int PICK_IMAGE_REQUEST=71;
    private Uri filepath;
    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference mDatabaseRef;
    private List<Wall> walls=new ArrayList<>();
    Adapter adapter1;
    long like=0;

    private File actualImage;
    private File compressedImage;

    FloatingActionButton fab,floatingActionButton,floatingActionButton1;

    TextView upload;
    GridView gridView;
    List<String> keys=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photography);

        toolbar=findViewById(R.id.header33);

        gridView=findViewById(R.id.gdView);
        storage = FirebaseStorage.getInstance();



        storageReference = storage.getReference();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("upload");

        compressedImage=new File(filepath.getUserInfo());

        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                keys.add(dataSnapshot.getKey());
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    String s1=dataSnapshot1.getValue(String.class);
                    Wall wall=new Wall(s1,s1);
                    walls.add(wall);
                    break;

                }


                adapter1=new Adapter(Photography.this,walls);
                gridView.setAdapter(adapter1);

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

        fab=findViewById(R.id.fabu);
        floatingActionButton=findViewById(R.id.upload);
        floatingActionButton1=findViewById(R.id.wallpaper33);
        upload=findViewById(R.id.tvupload);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationfab();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    chooseImage();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                mDatabaseRef.child(keys.get(position)).child("likes");
                mDatabaseRef.child(keys.get(position)).child("likes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      //  Toast.makeText(Photography.this,String.valueOf(dataSnapshot),Toast.LENGTH_LONG).show();

                        like= (long) dataSnapshot.getValue();
                        mDatabaseRef.child(keys.get(position)).child("likes").setValue(like+1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                Intent intent=new Intent(Photography.this,Main3Activity.class);
                intent.putExtra("img",walls.get(position).getOriginal());
             //   intent.putExtra("key",keys.get(position));

                startActivity(intent);

            }
        });

    }

    private void animationfab()
    {

        if (isOpen)
        {
            OvershootInterpolator interpolator=new OvershootInterpolator();
            ViewCompat.animate(fab).rotation(360f).withLayer().setDuration(2000).setInterpolator(interpolator).start();

            floatingActionButton.hide();
            floatingActionButton1.hide();
            floatingActionButton.setClickable(false);
            floatingActionButton1.setClickable(false);
            upload.setVisibility(View.GONE);
            isOpen = false;
        }
        else
        {

            OvershootInterpolator interpolator=new OvershootInterpolator();
            ViewCompat.animate(fab).rotation(180f).withLayer().setDuration(2000).setInterpolator(interpolator).start();
            floatingActionButton.show();
            floatingActionButton1.show();
            upload.setVisibility(View.VISIBLE);
            floatingActionButton.setClickable(true);
            floatingActionButton1.setClickable(true);
            isOpen = true;
        }

    }

    private void chooseImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            filepath=data.getData();
            uploadImage();
        }

    }

    private void uploadImage() {
        if (filepath!=null)
        {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            customCompressImage();
            final StorageReference ref=storageReference.child("images/"+ UUID.randomUUID().toString());
            UploadTask uploadTask=ref.putFile(filepath);
            Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri url=task.getResult();
                        progressDialog.dismiss();
                        String key=mDatabaseRef.push().getKey();
                        mDatabaseRef.child(key).child("image").setValue(url.toString());
                        mDatabaseRef.child(key).child("likes").setValue(0);
                        Toast.makeText(Photography.this,"Uploaded",Toast.LENGTH_LONG).show();

                    }
                    else
                    {

                    }
                }
            });




        }
        else {
            Toast.makeText(this,"No File is Selected",Toast.LENGTH_LONG).show();
        }
    }


    public void customCompressImage() {
        if (actualImage == null) {
            showError("Please choose an image!");
        } else {
            // Compress image in main thread using custom Compressor
            try {
                compressedImage = new Compressor(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(actualImage);


            } catch (IOException e) {
                e.printStackTrace();
                showError(e.getMessage());
            }

            // Compress image using RxJava in background thread with custom Compressor
            /*new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            setCompressedImage();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            showError(throwable.getMessage());
                        }
                    });*/
        }
    }



    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


}
