package sam.tipcalculator_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.Key;

public class MainActivity extends AppCompatActivity {

    Button ten;
    Button twelve;
    Button fifteen;
    Button twenty;
    Button custom;
    Button reset;
    boolean hasTyped = false;
    TextView hello;
    EditText cost;
    EditText tipAmt;
    EditText numFriends;
    TextView debug;
    int people;
    double tip;
    double costnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make all of the view stuff that matters
        ten = (Button) findViewById(R.id.button3);
        twelve = (Button) findViewById(R.id.button4);
        fifteen = (Button) findViewById(R.id.button5);
        twenty = (Button) findViewById(R.id.button6);
        custom = (Button) findViewById(R.id.button7);
        reset = (Button) findViewById(R.id.button8);
        hello = (TextView) findViewById(R.id.textView5);
        cost = (EditText) findViewById(R.id.editText2);
        tipAmt = (EditText) findViewById(R.id.editText);
        numFriends = (EditText) findViewById(R.id.editText3);

        debug = (TextView) findViewById(R.id.textView7);



        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(people != 0 || tip != 0.0 || costnum != 0) {
                    tip = 10.0;
                    CharSequence print = Double.toString(calculate(people,tip,costnum));
                    hello.setText(print);
                }
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(people != 0 || tip != 0.0 || costnum != 0) {
                    tip = 12.5;
                    CharSequence print = Double.toString(calculate(people,tip,costnum));
                    hello.setText(print);
                }

            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(people != 0  || costnum != 0) {
                    tip = 15.0;
                    CharSequence print = Double.toString(calculate(people,tip,costnum));
                    hello.setText(print);
                }

            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(people != 0  || costnum != 0) {
                    tip = 20.0;
                    CharSequence print = Double.toString(calculate(people,tip,costnum));
                    hello.setText(print);
                }
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(people != 0 || tip != 0.0 || costnum != 0) {
                    CharSequence print = Double.toString(calculate(people,tip,costnum));
                    hello.setText(print);
                }
            }
        });
        numFriends.setOnKeyListener(mKeyListener);
        tipAmt.setOnKeyListener(mKeyListener);
        numFriends.setOnKeyListener(mKeyListener);




    }
    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.editText2: //the asks the cost
                    if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ||keyCode == KeyEvent.KEYCODE_5) {
                        System.out.println("hey look you're doing something");

                }
                case R.id.editText3: //this is the friends

                case R.id.editText: //this is the custom amount keying
            }
            return true;
        }

    };
    private double calculate(int numPeeps, double tip, double cost){
        return ((cost * tip)/numPeeps);
    }


}
