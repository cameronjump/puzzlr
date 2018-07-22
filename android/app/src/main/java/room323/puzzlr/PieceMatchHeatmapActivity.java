package room323.puzzlr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.security.AccessController.getContext;

public class PieceMatchHeatmapActivity extends AppCompatActivity {

    private static final String TAG = "HeatmapActivity";
    ImageView imageview;
    String imagePath;
    GridLayout table;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piece_match_heatmap);
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    0);
        }

        table = findViewById(R.id.heatmap);
        createHeatmap(MyUtils.createDummyArray());

        imageview = findViewById(R.id.pieceimage);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraDialog();
            }
        });


    }

    public void createHeatmap(double[][] f) {

        //double[][] f = MyUtils.f;
        table.setColumnCount(f[0].length);
        table.setRowCount(f.length);

        int c = 0;
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[0].length; j++) {
                EditText box = new EditText(this);
                android.widget.TableRow.LayoutParams p = new android.widget.TableRow.LayoutParams();
                box.setLayoutParams(p);
                box.setBackgroundColor(getColor(MyUtils.getColor(f[i][j])));
                box.setWidth(40);
                box.setHeight(40);
                box.setPadding(20, 20, 20, 20);
                table.addView(box, c);
                c = c+1;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK){
            if (requestCode == 0) {
                Uri selectedImage = imageReturnedIntent.getData();
                imageview.setImageURI(selectedImage);
                imagePath = MyUtils.getRealPathFromURI(this, selectedImage);
            }
            else if (requestCode == 1) {
                imageview.setImageURI(Uri.parse(imagePath));
                imageview.setRotation(90);
            }
            PieceMatchActivity.pieceid = Frisbee.uploadFile(imagePath);
            Frisbee.notifyFlask(PieceMatchActivity.refid, PieceMatchActivity.pieceid, PieceMatchActivity.pieces);
        }
        Log.d(TAG, imagePath);
    }

    public void cameraDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(PieceMatchHeatmapActivity.this).create();
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