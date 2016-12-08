package spotfood.spotfood;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.poili.spotfood.R;

import java.io.File;

public class MenuScreen extends Activity {
    private ImageButton mAddMenuButton;
    private ImageView mMenuImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);

        this.inicializeVariables();
    }

    private void inicializeVariables() {
        this.mAddMenuButton = (ImageButton)findViewById(R.id.addButtonMenu);
        this.mAddMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });
        this.mMenuImage = (ImageView)findViewById(R.id.imageMenu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && data != null && data.getData() != null){
            Uri _uri = data.getData();
            if (_uri != null) {



                Cursor cursor = getContentResolver().query(_uri,
                        new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
                        null, null, null);
                cursor.moveToFirst();
                final String imageFilePath = cursor.getString(0);
                cursor.close();

                this.mMenuImage.setImageDrawable(Drawable.createFromPath(imageFilePath));
            }
        }
    }
}
