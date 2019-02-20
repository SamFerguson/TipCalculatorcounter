package sam.tipcalculator_counter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Dimension;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.Key;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    RadioButton ten;
    RadioButton twelve;
    RadioButton fifteen;
    RadioButton twenty;
    RadioButton custom;
    Button reset;
    Button calculate;
    TextView output;
    EditText cost;
    EditText tipAmt;
    EditText numFriends;
    double tip;
    boolean usingCustom = false;
    boolean hasPressedSomething = false;
    CharSequence oldText = "";
    int focus;
    int imageId;
    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            oldText = savedInstanceState.getCharSequence("key");
            usingCustom = savedInstanceState.getBoolean("custom");
            hasPressedSomething = savedInstanceState.getBoolean("pressed");
        }

        setContentView(R.layout.activity_main);
        //make all of the view stuff that matters
        reset = (Button) findViewById(R.id.button8);
        output = (TextView) findViewById(R.id.textView5);
        cost = (EditText) findViewById(R.id.editText2);
        tipAmt = (EditText) findViewById(R.id.editText);
        numFriends = (EditText) findViewById(R.id.editText3);
        calculate = (Button) findViewById(R.id.calculate);
        ten = (RadioButton) findViewById(R.id.radioButton5);
        twelve = (RadioButton) findViewById(R.id.radioButton4);
        fifteen = (RadioButton) findViewById(R.id.radioButton);
        twenty = (RadioButton) findViewById(R.id.radioButton7);
        custom = (RadioButton) findViewById(R.id.radioButton8);
        main = findViewById(R.id.activitymain);
        if(savedInstanceState != null){
            output.setText(oldText);
            if(usingCustom){
                canCalcCustom();
            }
            else{
                canCalcNonCustom();
            }
        }
        numFriends.setOnKeyListener(mKeyListener);
        tipAmt.setOnKeyListener(mKeyListener);
        cost.setOnKeyListener(mKeyListener);

        /*
        All of these click listeners tell app a button has been pressed,
        whether they're using custom, what the tip is, whether to calculate
         */

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
                System.out.println(calculate.isEnabled());
                hasPressedSomething = true;
                usingCustom = false;
                calculate.setEnabled(canCalcNonCustom());
                tip = 20.0;
            }
        });
        /*
        Same as other radio buttons but handles differently becuse it's custom
         */
        custom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hasPressedSomething = true;
                usingCustom = true;
                calculate.setEnabled(canCalcCustom());
                try {
                    tip = Double.parseDouble(calculate.getText().toString());
                }
                catch(NumberFormatException ok){
                }

            }
        });
        /*
        when they calculate check for errors and then calculate based on custom and non-custom
         */
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double finalAns =0.0;
                try {
                    if (doShowError()[0]) {
                        String errorOne = doShowError()[1] ? getString(R.string.errorCode1) : "";
                        String errorTwo = doShowError()[2] ? doShowError()[1] ? getString(R.string.errorCode2a) : getString(R.string.errorCode2b) : "";
                        showErrorAlert(errorOne + errorTwo, 1);
                        return;
                    }
                }
                catch(StringIndexOutOfBoundsException e){
                    showErrorAlert(getString(R.string.error), R.id.calculate);
                }
                if(usingCustom) {
                    finalAns = calculate((int) Double.parseDouble(numFriends.getText().toString()),
                            Double.parseDouble(tipAmt.getText().toString()),
                            Double.parseDouble(cost.getText().toString()));
                    tip = Double.parseDouble(tipAmt.getText().toString());
                }
                else
                    finalAns = calculate((int)Double.parseDouble(numFriends.getText().toString()),tip,
                            Double.parseDouble(cost.getText().toString()));
                NumberFormat format = NumberFormat.getCurrencyInstance();
                CharSequence ans = format.format(Math.round(finalAns *100.0)/100.0);
                output.setText(String.format("Total TIP: %s", ans));
                if(tip <100.00 && tip >=20.00){
                    imageId = R.drawable.giveitupwaiter;
                }

                else if(tip <20.00 && tip > 10.00){
                    imageId = R.drawable.contentserver;
                }
                else if(tip <=10.0){
                    imageId = R.drawable.madserver;
                }
                else {
                    imageId = R.drawable.clappingwaiter;
                }
                output.setTextColor(getColor(R.color.colorAccent));
                output.setTextSize(45);
                main.setBackground(getDrawable(imageId));
            }
        });
        /*
        reset button resets everything to basically oncreate
         */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost.getText().clear();
                numFriends.getText().clear();
                tipAmt.getText().clear();
                ten.setChecked(false);
                twelve.setChecked(false);
                fifteen.setChecked(false);
                twenty.setChecked(false);
                hasPressedSomething = false;
                output.setText("");
                calculate.setEnabled(false);

            }
        });
    }
    /*
    method to see if user can calculate tip with distinct button pressed
     */
    private boolean canCalcNonCustom(){
        return (numFriends.getText().toString().length() > 0) && (cost.getText().toString().length() > 0
        && hasPressedSomething);
    }
    /*
    method to see if user can calculate tip with custom button pressed
     */
    private boolean canCalcCustom(){
        //return true if the friends, cost, and custom edit text aren't empty and if a button has been pressed        
        return (numFriends.getText().toString().length() > 0) && (cost.getText().toString().length()>0)
                && tipAmt.getText().toString().length() >0 && hasPressedSomething;

    }
    /*
    method to see whether to show an error, and what error messages to throw if there is
    and it finds what EditText throws the error and stores the int and passes that ShowError
     */
    private boolean[] doShowError(){
        boolean goodValues = (numFriends.getText().toString().charAt(0) != '.' && cost.getText().toString().charAt(0) != '.'
                && custom.getText().toString().charAt(0) != '.');
        boolean noLeadingZero = (numFriends.getText().toString().charAt(0) != '0' && cost.getText().toString().charAt(0) != '0'
                && custom.getText().toString().charAt(0) != '0');
        if(!goodValues||!noLeadingZero){
            if(numFriends.getText().toString().charAt(0) != '.' || numFriends.getText().toString().charAt(0) != '0')
                focus = numFriends.getId();
            else if(cost.getText().toString().charAt(0) != '.' || cost.getText().toString().charAt(0) != '0')
                focus = cost.getId();
            else
                focus = custom.getId();
        }
        return new boolean[]{(!goodValues || !noLeadingZero), !goodValues, !noLeadingZero};
    }
    /*
    Handle whether the user can calculate everytime they do a keystroke
     */
    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.editText2: //the asks the cost
                    if(numFriends.getText().toString().length() > 0 && !usingCustom) {
                        calculate.setEnabled(canCalcNonCustom());
                        System.out.println("Is it enabled?(not custom): " + calculate.isEnabled()+ " "+hasPressedSomething);
                    }
                    if(usingCustom && numFriends.getText().toString().length()>0
                            && tipAmt.getText().toString().length()>0){
                        calculate.setEnabled(canCalcCustom());
                        System.out.println("Is it enabled?(custom): " + calculate.isEnabled()+ " " + hasPressedSomething);
                    }
                    break;
                case R.id.editText3://this is the friends
                    if(cost.getText().toString().length() > 0 && !usingCustom) {
                        System.out.println(canCalcNonCustom());
                        calculate.setEnabled(canCalcNonCustom());
                    }
                    if(cost.getText().toString().length() >0 && usingCustom){
                        calculate.setEnabled(canCalcCustom());
                    }
                    break;
                case R.id.editText: //this is the custom amount keying
                    calculate.setEnabled(canCalcCustom());
                    if(cost.getText().toString().length() >0 && usingCustom && numFriends.getText().toString().length() >0){
                        calculate.setEnabled(canCalcCustom());
                    }
                    break;
            }
            System.out.println("outside switch?: " + calculate.isEnabled());
            return false;
        }
    };
    /*
     calculate the tip
     */
    private double calculate(int numPeeps, double tip, double cost){
        return ((cost * tip)/numPeeps)/100;
    }
    /*
    show the error
     */
    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setMessage(errorMessage)
                .setNeutralButton(getString(R.string.close),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(focus).requestFocus();
                            }
                        }).show();
    }
    /*
    bundle the things you need
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //put the old text in bundle
        outState.putCharSequence("key", output.getText());
        //put whether they're using the custom in the bundle
        outState.putBoolean("custom", usingCustom);
        outState.putBoolean("pressed", hasPressedSomething);
    }

}
