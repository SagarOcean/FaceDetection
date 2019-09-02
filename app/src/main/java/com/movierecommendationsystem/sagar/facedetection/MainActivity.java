package com.movierecommendationsystem.sagar.facedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);
        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ocean);
        imageView.setImageBitmap(myBitmap);
        final Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        final Bitmap bitmap = Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(),myBitmap.getConfig().RGB_565);
        final Canvas canvas=new Canvas(bitmap);
        canvas.drawBitmap(myBitmap,0,0,null);
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();
                if(!faceDetector.isOperational()){
                    Toast.makeText(getApplicationContext(),"Face could not be detected on your device",Toast.LENGTH_LONG).show();
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> sparseArray=faceDetector.detect(frame);
                for (int i = 0; i<sparseArray.size();i++){
                    Face face = sparseArray.valueAt(i);
                    float x1 = face.getPosition().x;
                    float y1 = face.getPosition().y;
                    float x2 = x1+face.getWidth();
                    float y2 = y1+face.getHeight();
                    RectF rectF = new RectF(x1,y1,x2,y2);
                    canvas.drawRoundRect(rectF,2,2,paint);
                    imageView.setImageDrawable(new BitmapDrawable(getResources(),bitmap));
                }
            }
        });

    }
}
