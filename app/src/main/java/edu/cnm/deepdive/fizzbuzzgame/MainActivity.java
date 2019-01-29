package edu.cnm.deepdive.fizzbuzzgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private static final int UPPER_BOUND = 99; // a constant is a static final.
  // Screaming snake case
  private static final int TIMEOUT_INTERVAL = 5000;

  private TextView numberView;  // refers to TextView object from xml
  private ToggleButton fizzToggle;
  private ToggleButton buzzToggle;
  private TextView activeCorrectView;
  private TextView passiveCorrectView;
  private TextView incorrectView;
  private Random rng;
  private int value;
  private int activeCorrect;
  private int passiveCorrect;
  private int incorrect;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    numberView = findViewById(R.id.number_view);
    fizzToggle = findViewById(R.id.fizz_toggle);
    buzzToggle = findViewById(R.id.buzz_toggle);
    activeCorrectView = findViewById(R.id.active_correct_view);
    passiveCorrectView = findViewById(R.id.passive_correct_view);
    incorrectView = findViewById(R.id.incorrect_view);
    rng = new Random();
    startTimer();
  }

  private void startTimer() {
    Timer timer = new Timer();
    timer.start();
  }

  private void updateTally() {
    boolean isFizz = (value % 3 == 0);
    boolean isBuzz = (value % 5 == 0);
    boolean fizzCorrect = (isFizz == fizzToggle.isChecked());
    boolean buzzCorrect = (isBuzz == buzzToggle.isChecked());
    if (!(fizzCorrect && buzzCorrect)) {
      incorrect++;
    } else if (isFizz || isBuzz) {
      activeCorrect++;
    } else {
      passiveCorrect++;
    }
  }// updateTally()

  private void updateTallyDisplay() {
    activeCorrectView.setText(getString(R.string.active_correct, activeCorrect));
    passiveCorrectView.setText(getString(R.string.passive_correct, passiveCorrect));
    incorrectView.setText(getString(R.string.incorrect, incorrect));
  }

  private void updateView() {
    value = 1 + rng.nextInt(UPPER_BOUND);  // 0 to 100 exclusive
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        updateTallyDisplay();
        numberView.setText(Integer.toString(value));
        fizzToggle.setChecked(false);  // button not pushed in when false
        buzzToggle.setChecked(false);
      }
    });
  }


  private class Timer extends
      Thread { // if it were static it would only be able

    // to see other static members of the class
    // in this case UPPER_BOUND
    @Override
    public void run() { // documentation in thread says I don;t need "super." here
      try {
        updateView();
        Thread.sleep(TIMEOUT_INTERVAL);
      } catch (InterruptedException e) {  // InterruptedException is the only one
        // that we will consitently ignore.  Must put in
        // DO NOTHING!
      } finally {  // thread goes here when it wakes up
        updateTally();
        startTimer();// when one timer ends then start a new one.
      }

    } // end run()

  } // end class Timer

} // end main class MainActivity
