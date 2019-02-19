package sam.tipcalculator_counter;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    Button calculate;
    boolean hasTyped = false;
    TextView hello;
    EditText cost;
    EditText tipAmt;
    EditText numFriends;
    TextView debug;
    int people;
    double tip;
    boolean usingCustom = false;
    boolean validInput = false;
    boolean hasPressedSomething = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make all of the view stuff that matters

        reset = (Button) findViewById(R.id.button8);
        hello = (TextView) findViewById(R.id.textView5);
        cost = (EditText) findViewById(R.id.editText2);
        tipAmt = (EditText) findViewById(R.id.editText);
        numFriends = (EditText) findViewById(R.id.editText3);
        calculate = (Button) findViewById(R.id.calculate);
        debug = (TextView) findViewById(R.id.textView7);
        ten = (Button) findViewById(R.id.radioButton5);
        twelve = (Button) findViewById(R.id.radioButton4);
        fifteen = (Button) findViewById(R.id.radioButton);
        twenty = (Button) findViewById(R.id.radioButton7);
        custom = (Button) findViewById(R.id.radioButton8);

        numFriends.setOnKeyListener(mKeyListener);
        tipAmt.setOnKeyListener(mKeyListener);
        cost.setOnKeyListener(mKeyListener);

        calculate.setEnabled(false);

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasPressedSomething = true;
                usingCustom = false;
                calculate.setEnabled(canCalcNonCustom());
                tip = 10.0;
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasPressedSomething = true;
                usingCustom = false;
                calculate.setEnabled(canCalcNonCustom());
                tip = 12.5;
            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasPressedSomething = true;
                usingCustom = false;
                calculate.setEnabled(canCalcNonCustom());
                tip = 15.0;
            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasPressedSomething = true;
                usingCustom = false;
                calculate.setEnabled(canCalcNonCustom());
                tip = 20.0;
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hasPressedSomething = true;
                usingCustom = true;
                calculate.setEnabled(canCalcCustom());
                try {
                    tip = Double.parseDouble(calculate.getText().toString());
                }
                catch(NumberFormatException n){
                    System.out.print("nothing");
                }

            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double finalAns =0.0;

                if(doShowError()[0]){
                    String errorOne = doShowError()[1]? "Field may not begin with \'.\'": "";
                    //sorry for the nested ternary operator, just to figure out if it should be empty, have a leading comma
                    //and space, or no leading comma or space, depending on what error is thrown
                    String errorTwo = doShowError()[2]? doShowError()[1]? ", No leading zeroes allowed.": "No leading zeroes allowed.": "";
                    showErrorAlert(errorOne + errorTwo,R.id.calculate);
                    return;
                }

                if(usingCustom)
                    finalAns = calculate((int)Double.parseDouble(numFriends.getText().toString()),
                        Double.parseDouble(tipAmt.getText().toString()),
                        Double.parseDouble(cost.getText().toString()));
                else
                    finalAns = calculate((int)Double.parseDouble(numFriends.getText().toString()),tip,
                            Double.parseDouble(cost.getText().toString()));
                System.out.println("FINAL TIP:   " + finalAns);
                CharSequence ans = Double.toString(finalAns);
                hello.setText(ans);
            }
        });
    }

    private boolean canCalcNonCustom(){
        return (numFriends.getText().toString().length() > 0) && (cost.getText().toString().length() > 0
        && hasPressedSomething);
    }
    private boolean canCalcCustom(){
        //return true if the friends, cost, and custom edit text aren't empty and if a button has been pressed        
        return (numFriends.getText().toString().length() > 0) && (cost.getText().toString().length()>0)
                && tipAmt.getText().toString().length() >0 && hasPressedSomething;

    }

    private boolean[] doShowError(){
        //do you have good values?
        boolean goodValues = (numFriends.getText().toString().charAt(0) != '.' && cost.getText().toString().charAt(0) != '.'
                && custom.getText().toString().charAt(0) != '.');
        //do you have a leading zero?
        boolean noLeadingZero = (numFriends.getText().toString().charAt(0) != '0' && cost.getText().toString().charAt(0) != '0'
                && custom.getText().toString().charAt(0) != '0');
        System.out.println(goodValues + " " + noLeadingZero );
        //if you don't have good values or you don't have if you have a leading zero then return true, sending an error message
        //also the other two parts return something for the error message
        return new boolean[]{(!goodValues || !noLeadingZero), !goodValues, !noLeadingZero};
    }

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) throws NumberFormatException {

            switch (v.getId()) {
                case R.id.editText2: //the asks the cost


                    if(numFriends.getText().toString().length() > 0 && !usingCustom) {
                        System.out.println("you're here!");
                        calculate.setEnabled(canCalcNonCustom());

                    }


                    /*    costnum = Double.parseDouble(cost.getText().toString());
                        System.out.println(costnum);
                    */

                case R.id.editText3://this is the friends

                    // if the cost field isn't empty and you're not using custom input
                    //see if you cna enable the button
                    if(cost.getText().toString().length() > 0 && !usingCustom) {
                        System.out.println("you're here!");
                        calculate.setEnabled(canCalcNonCustom());

                    }
                    //if the cost field isn't empty and you're using custom input
                    //see if you can enable the button
                    if(cost.getText().toString().length() >0 && usingCustom){
                        calculate.setEnabled(canCalcCustom());
                    }



                case R.id.editText: //this is the custom amount keying
                    //if the cust field isn't empty and you're using custom make sure the
                    if(cost.getText().toString().length() >0 && usingCustom && numFriends.getText().toString().length() >0){
                        calculate.setEnabled(canCalcCustom());
                    }
            }
            return false;

        }

    };
    private double calculate(int numPeeps, double tip, double cost){

        return ((cost * tip)/numPeeps)/100;
    }

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }

}
