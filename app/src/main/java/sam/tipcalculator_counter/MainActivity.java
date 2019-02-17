package sam.tipcalculator_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ten;
    Button twelve;
    Button fifteen;
    Button twenty;
    Button custom;
    Button reset;
    boolean hasTyped = false;
    double percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ten = (Button) findViewById(R.id.button3);
        twelve = (Button) findViewById(R.id.button4);
        fifteen = (Button) findViewById(R.id.button5);
        twenty = (Button) findViewById(R.id.button6);
        custom = (Button) findViewById(R.id.button7);
        reset = (Button) findViewById(R.id.button8);



        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percent = 10.0;
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percent = 12.5;
            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percent = 15.0;
            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percent = 20.0;
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });




    }
    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.editText2: //this asks the amount of friends

                case R.id.editText3: //this is the cost of food

                case R.id.editText: //this is the custom amount keying
            }
            return false;
        }

    };


}
