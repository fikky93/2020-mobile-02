package id.putraprima.marketplacelayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Personal_info extends AppCompatActivity {

    private static final String TAG = Personal_info.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    private ImageView avatarImage;

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String NOMOR_KEY = "nomor";

    private TextView fullnameOutput;
    private TextView emailOutput;
    private TextView nomorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        fullnameOutput = findViewById(R.id.textView10);
        emailOutput = findViewById(R.id.textView12);
        nomorOutput = findViewById(R.id.textView11);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            fullnameOutput.setText(extras.getString(FULLNAME_KEY));
            emailOutput.setText(extras.getString(EMAIL_KEY));
            nomorOutput.setText(extras.getString(NOMOR_KEY));
        }

        //foto
        avatarImage = (ImageView) findViewById(R.id.imageView8);
        Button btn = findViewById(R.id.uploadFoto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(Personal_info.this);
            }
        });
    }

    public void backPersonal(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        avatarImage.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                avatarImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                            if (data != null) {
                                try {
                                    Uri imageUri = data.getData();
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                                    avatarImage.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }

                    }
                    break;
            }
        }
    }

    public void uploadFoto(View view) {
    }
}
