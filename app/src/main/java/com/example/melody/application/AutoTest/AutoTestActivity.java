package com.example.melody.application.AutoTest;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.melody.application.R;

public class AutoTestActivity extends AppCompatActivity {

    private static final String TAG = AutoTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_test);

        Button startBtn = findViewById(R.id.button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animal animal = new Animal();
                Log.d(TAG, "Animal");
                TestUtils.executePublicMethods(animal);

                Cat cat = new Cat();
                Log.d(TAG, "Cat");
                TestUtils.executePublicMethods(cat);

                Dog dog = new Dog();
                Log.d(TAG, "dog");
                TestUtils.executePublicMethods(dog);
            }
        });
    }
}