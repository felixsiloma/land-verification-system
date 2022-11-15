package com.oyopizmanga.landownerverification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

public class detailsActivity extends AppCompatActivity {
    TextView location, size, mail, price, urlme;
    ImageView imageview1;
    Button contact, map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mail = findViewById(R.id.emailgetter);
        price = findViewById(R.id.pricegetter);
        imageview1 = findViewById(R.id.imagevie1);
        location = findViewById(R.id.locationgetter);
        size = findViewById(R.id.sizegetter);
        urlme = findViewById(R.id.urlme);
        location.setText(getIntent().getStringExtra("locationsn") + "");
        size.setText(getIntent().getStringExtra("sizesn") + "");
        mail.setText(getIntent().getStringExtra("emailsn") + "");
        price.setText(getIntent().getStringExtra("pricesn") + "");
        urlme.setText(getIntent().getStringExtra("urlsn") + "");
        String url = urlme.getText().toString();
        Picasso.get().load(url).into(imageview1);
        contact = findViewById(R.id.contact);
        map = findViewById(R.id.btnmap);
        String mapslcn = location.getText().toString();
        String mailme = mail.getText().toString();
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), mapSearch.class);
                //intent.putExtra("model", model);
                intent.putExtra("mapslcn", mapslcn);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{mailme});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    v.getContext().startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(v.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullscreen();
            }
        });


    }

    private void fullscreen() {
        ImagePopup imagePopup = new ImagePopup(this);
        String url = urlme.getText().toString();
        imagePopup.initiatePopupWithPicasso(url);
        imagePopup.viewPopup();
    }
}