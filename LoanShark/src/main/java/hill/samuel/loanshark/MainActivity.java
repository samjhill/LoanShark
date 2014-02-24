package hill.samuel.loanshark;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class MainActivity extends Activity {
    private static final String LOAN_TOTAL = "LOAN_TOTAL";
    private static final String INTEREST_RATE = "INTEREST_RATE";

    private double currentLoan; //the loan amount
    private double interestRate; //the rate of interest

    private EditText loanEditText; // user enters the loan amount
    private EditText interestRateEditText; //user enters interest rate
    private TextView fiveYearsMonthlyPaymentTextView; //five years
    private TextView tenYearsMonthlyPaymentTextView; //ten years
    private TextView fifteenYearsMonthlyPaymentTextView; //fifteen years
    private TextView twentyYearsMonthlyPaymentTextView; //twenty years
    private TextView twentyFiveYearsMonthlyPaymentTextView; //twenty-five years
    private TextView thirtyYearsMonthlyPaymentTextView; //thirty years

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //inflates the GUI

        //is the app being initialised, or is it resuming?
        if (savedInstanceState == null) {
            //initialize at zero
            currentLoan = 0.0;
            //initialize interestRate at 10 so we don't get divide-by-zero errors
            interestRate = 10.0;
        } else {
            //we're resuming the ap
            currentLoan = savedInstanceState.getDouble(LOAN_TOTAL);
            interestRate = savedInstanceState.getInt(INTEREST_RATE);
        }

        //get references for each textView
        fiveYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.fiveYearsMonthlyPaymentTextView);
        tenYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.tenYearsMonthlyPaymentTextView);
        fifteenYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.fifteenYearsMonthlyPaymentTextView);
        fifteenYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.fifteenYearsMonthlyPaymentTextView);
        twentyYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.twentyYearsMonthlyPaymentTextView);
        twentyFiveYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.twentyFiveYearsMonthlyPaymentTextView);
        thirtyYearsMonthlyPaymentTextView = (TextView) findViewById(R.id.thirtyYearsMonthlyPaymentTextView);

        //get EditText for interest rate
        interestRateEditText = (EditText) findViewById(R.id.interestRateEditText);

        //get EditText for loan amount
        loanEditText = (EditText) findViewById(R.id.loanEditText);

        //watch loanEditText and interestRateEditText
        loanEditText.addTextChangedListener(loanEditTextWatcher);
        interestRateEditText.addTextChangedListener(interestRateEditTextWatcher);
    }

    //emi calculation handled here
    //p = principal
    //r = rate
    //n = number of years
    public Double calculateEmi(double p, double r, double n){
        double rate = (r/12)/100;
        double emi = (p * rate * (Math.pow((1+rate), n)) / ((Math.pow((1+rate),n)) -1));
        return emi;
    }

    //update the monthly payment amounts for everything
    private void updateScreen() {
        //
        //five years
        //
        double fiveYearsMonthlyPayment = calculateEmi(currentLoan, interestRate, 5);
        fiveYearsMonthlyPaymentTextView.setText(String.format("%.02f", fiveYearsMonthlyPayment));
        //

        //
        //ten years
        //
        double tenYearsMonthlyPayment = calculateEmi(currentLoan, interestRate, 10);
        tenYearsMonthlyPaymentTextView.setText(String.format("%.02f", tenYearsMonthlyPayment));
        //

        //
        //fifteen years
        //
        double fifteenYearsMonthlyPayment = calculateEmi(currentLoan, interestRate, 15);
        fifteenYearsMonthlyPaymentTextView.setText(String.format("%.02f", fifteenYearsMonthlyPayment));
        //

        //
        //twenty years
        //
        double twentyYearsMonthlyPayment = calculateEmi(currentLoan, interestRate, 20);
        twentyYearsMonthlyPaymentTextView.setText(String.format("%.02f", twentyYearsMonthlyPayment));
        //

        //
        //twentyFive years
        //
        double twentyFiveYearsMonthlyPayment = calculateEmi(currentLoan, interestRate, 25);
        twentyFiveYearsMonthlyPaymentTextView.setText(String.format("%.02f", twentyFiveYearsMonthlyPayment));
        //

        //
        //thirty years
        //
        double thirtyYearsMonthlyPayment = calculateEmi(currentLoan, interestRate, 30);
        thirtyYearsMonthlyPaymentTextView.setText(String.format("%.02f", thirtyYearsMonthlyPayment));
        //
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save values inside loan amount and interest rate
        outState.putDouble(LOAN_TOTAL,currentLoan);
        outState.putDouble(INTEREST_RATE, interestRate);
    }

    //event handling for billEditText's events
    private TextWatcher loanEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //convert loanEditText's text to a double 
            try {
                currentLoan = Double.parseDouble(s.toString());
                System.out.println("Current loan: " + currentLoan);
            }
            catch (NumberFormatException e) {
                currentLoan = 0.0; //default if an exception occurs
            }
            //update the screen
            updateScreen();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //event handling for billEditText's events
    private TextWatcher interestRateEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //convert interestRateEditText's text to a double
            try {
                interestRate = Double.parseDouble(s.toString());
                System.out.println("Current interestRate: " + interestRate);
            }
            catch (NumberFormatException e) {
                interestRate = 10.0; //default to 10 to avoid NaN
            }
            //update everything
            updateScreen();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }


}
