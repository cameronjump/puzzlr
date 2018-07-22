package room323.puzzlr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.net.URI;

public class PieceMatchActivity extends AppCompatActivity {

    private static final String TAG = "PieceMatchActivity";
    private ImageView imageview;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    0);
        }

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piece_match_main);

        imageview = findViewById(R.id.refimage);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraDialog();
            }
        });

        Button uploadButton = findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK){
            if (requestCode == 0) {
                Bundle extras = imageReturnedIntent.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Uri uri = MyUtils.getImageUri(this, imageBitmap);
                imageview.setImageURI(uri);
                imagePath = MyUtils.getRealPathFromURI(this, uri);
            }
            else if (requestCode == 1) {
                Uri selectedImage = imageReturnedIntent.getData();
                imageview.setImageURI(selectedImage);
                imagePath = MyUtils.getRealPathFromURI(this, selectedImage);
            }
        }
    }

    private void upload() {
        EditText numberInput = findViewById(R.id.number);
        String number = numberInput.getText().toString();
        Log.d(TAG, number);
        if (number.equals("") && imagePath == null) {
            Toast.makeText(this, "Please upload image and enter number of pieces.", Toast.LENGTH_SHORT).show();
        }
        else if(number.equals("")) {
            Toast.makeText(this, "Please upload image.", Toast.LENGTH_SHORT).show();
        }
        else if(imagePath == null) {
            Toast.makeText(this, "Please enter number of pieces.", Toast.LENGTH_SHORT).show();
        }
        else {
            Frisbee.uploadAndNotifyFlask(imagePath, Integer.parseInt(number), "ref");
        }
    }

    public void cameraDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(PieceMatchActivity.this).create();
        alertDialog.setTitle("Image Upload");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}

