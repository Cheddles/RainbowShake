// This is the package name that the app is developed under. The user usually never sees this.
package au.edu.asms.flicklength;

// Import all the required elements from external libraries
// In Eclipse ADT, press CTL-shift-O to automatically update this list (CMD-shift-O for OS X users)
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import au.edu.asms.RainbowShake.R;

// Declare the main class (this is where the App starts running)
//
// The "implements SensorEventListener" part of this declaration is required
// for us to see the accelerometer.
//
public class MainActivity extends Activity implements SensorEventListener{
	
//	Declare variables for both the accelerometer sensor and a manager for the accelerometer
//	The variable declaration syntax here is:
//		- variable scope. Private indicates that the variable is only visible within the containing class (MainActivity)
//		- variable name. By convention multiple words are connected with underscores or by capitalising each word
//		- variable class (usually imported but can be a private class you write yourself)
//	
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	
	// The first method (or subroutine) within MainActivity is called Oncreate.
	// This is run when the MainActivity is first created.
	// 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_UI);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		  if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			  RelativeLayout view = (RelativeLayout)findViewById(R.id.view);

//			 Set an array of numbers to the accelerometer values ([0] for x, [1] for y and [2] for z) 
			  float[] values = event.values;
			  
//				set the red colour intensity (0-255) mapped over a scale
//				from -10m/s/s to 10m/s/s for x-axis acceleration
				int red = Math.round((values[0]*(255/11)+127));
				
//				ensure than red is within the legal range of 0-255				
				if (red>255){
					red=255;
				}
				if (red<0){
					red=0;
				}

//				set the green colour intensity (0-255) mapped over a scale
//				from -10m/s/s to 10m/s/s for y-axis acceleration
				int green = Math.round((values[1]*(255/11)+127));
				
//				ensure than green is within the legal range of 0-255				
				if (green>255){
					green=255;
				}
				if (green<0){
					green=0;
				}
//				set the blue colour intensity (0-255) mapped over a scale
//				from -10m/s/s to 10m/s/s for z-xis acceleration
				int blue = Math.round((values[2]*(255/11)+127));
				
//				ensure than blue is within the legal range of 0-255				
				if (blue>255){
					blue=255;
				}
				if (blue<0){
					blue=0;
				}
				
//				set background colour according to z-axis acceleration (leave red and green channels at 0
				view.setBackgroundColor(Color.argb(255, red, green, blue));
				
//				calculate total acceleration
			    double totalA = Math.pow((values[0] * values[0] + values[1] * values[1] + values[2] * values[2]),0.5);

//				set the on-screen text label to a new total acceleration value
			    TextView accel_label =(TextView)findViewById(R.id.textView1);
				String newmessage = String.valueOf(values[2]);
				accel_label.setText(newmessage);

		  }
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}
		
	protected void onPause() {
	    super.onPause();
	    senSensorManager.unregisterListener(this);
	}
		
	protected void onResume() {
	    super.onResume();
	    senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_UI);
	}
}
