package com.oyopizmanga.landownerverification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class uploadadActivity extends AppCompatActivity {
    EditText location, size, email, url, price;
    Button upload, btnChoose;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    DatabaseReference reference1;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseDatabase rootNode;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadad);
        location = findViewById(R.id.ad_location);
        size = findViewById(R.id.ad_size);
        imageView = (ImageView) findViewById(R.id.imgView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        btnChoose = (Button) findViewById(R.id.btnChoose);
        email = findViewById(R.id.ad_email);
        url = findViewById(R.id.imgurl);
        price = findViewById(R.id.ad_price);
        String uid = FirebaseAuth.getInstance().getUid();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getInstance().getReference("advertiseLand");
        reference1 = rootNode.getInstance().getReference("myLandAds").child(uid);
        upload = findViewById(R.id.upload_ad);
        email = findViewById(R.id.ad_email);
        price = findViewById(R.id.ad_price);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
    }

    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            Toast.makeText(uploadadActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                            url.setText(uri.toString());
                            String locationme = location.getText().toString();
                            String sizeme = size.getText().toString();
                            String emailme = email.getText().toString();
                            String priceme = price.getText().toString();
                            String urlme = url.getText().toString();
                            if (locationme.isEmpty() || sizeme.isEmpty() || emailme.isEmpty() || priceme.isEmpty()) {
                                Toast.makeText(uploadadActivity.this, "All fields must filled", Toast.LENGTH_SHORT).show();

                            } else if (!emailme.matches(emailPattern)) {
                                Toast.makeText(uploadadActivity.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                            } else if (urlme.isEmpty()) {
                                Toast.makeText(uploadadActivity.this, "Image Required", Toast.LENGTH_SHORT).show();
                            } else if (!locationme.contains(",")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(uploadadActivity.this);
                                builder.setMessage("Specify location i.e Nairobi, Embakasi East, Simba Villa Estate");
                                builder.setTitle("Specify Location");
                                builder.setCancelable(false);
                                builder.setCancelable(false);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                UploadLand uploadLand = new UploadLand(locationme, sizeme, emailme, priceme, urlme);
                                reference.push().setValue(uploadLand);
                                reference1.push().setValue(uploadLand);
                                Toast.makeText(uploadadActivity.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        //mDownloadUrl = uri.toString();

                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(uploadadActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }

    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK &&
                data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}