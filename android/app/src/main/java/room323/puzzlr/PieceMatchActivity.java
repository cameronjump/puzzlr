package room323.puzzlr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PieceMatchActivity extends AppCompatActivity {

    private static final String TAG = "PieceMatchActivity";
    private ImageView imageview;

    public static String refid;
    public static String pieceid;
    public static int pieces;

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
                Uri selectedImage = imageReturnedIntent.getData();
                imageview.setImageURI(selectedImage);
                imagePath = MyUtils.getRealPathFromURI(this, selectedImage);
                /*Bundle extras = imageReturnedIntent.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Uri uri = MyUtils.getImageUri(this, imageBitmap);
                imageview.setImageURI(uri);
                imagePath = MyUtils.getRealPathFromURI(this, uri);*/
            }
            else if (requestCode == 1) {
                imageview.setImageURI(Uri.parse(imagePath));
                imageview.setRotation(90);
            }
        }
        Log.d(TAG, imagePath);
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

            refid = Frisbee.uploadFile(imagePath);
            pieces = Integer.parseInt(number);
        }
        Intent intent = new Intent(PieceMatchActivity.this, PieceMatchHeatmapActivity.class);
        startActivity(intent);
    }

    public void cameraDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(PieceMatchActivity.this).create();
        alertDialog.setTitle("Image Upload");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 0);
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir("Pictures");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}

