package lts.google.map;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

	
public class MainActivity7 extends Activity {
	
	
	Button button1,button2,button3;
	private CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;
    private GoogleMap map;
    EditText editText1,editText2,editText3,editText4,editText5;
    TextView textView1,textView2,textView3,textView4,textView5;
    Double latitude;
    Double longitude;
    String str;
	Double latti;
	Double longi;
	Double center1;
	Double center2;
	Float distance;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity7);
	        
	        double distance;
	        String meter;
	        
	        button1 = (Button)findViewById(R.id.button1);
	        button2 = (Button)findViewById(R.id.button2);
	        button3 = (Button)findViewById(R.id.button3);
	        editText1 = (EditText)findViewById(R.id.editText1);
	        editText2 = (EditText)findViewById(R.id.editText2);
	        editText3 = (EditText)findViewById(R.id.editText3);
	        editText4 = (EditText)findViewById(R.id.editText4);
	        editText5 = (EditText)findViewById(R.id.editText5);
	        textView1 = (TextView)findViewById(R.id.textView1);
	        textView2 = (TextView)findViewById(R.id.textView2);
	        textView3 = (TextView)findViewById(R.id.textView3);
	        textView4 = (TextView)findViewById(R.id.textView4);
	        textView5 = (TextView)findViewById(R.id.textView5);
	        
	        button2.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			Toast.makeText(getApplicationContext(), global.ID+"님, 택시가 호출되었습니다.", Toast.LENGTH_SHORT).show();
	    			
	    		}
	    	
	    	});
	 
	        
	        button3.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			 Intent i = new Intent(getApplicationContext(), map.class);
	                 startActivity(i);
	                 finish();
	    			
	    		}
	    	
	    	});
	 
	        
	       //global.str=editText2.getText().toString();
	       //Toast.makeText(getApplicationContext(), ""+str, Toast.LENGTH_LONG).show();
	       //String str = editText2.getText().toString();
	        
	       
	      
	      LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	      Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	      
	      
	      	if (null != lastKnownLocation) {
	                        
	         editText1.setText(getAddressFromLocation(lastKnownLocation, Locale.KOREA));
	         }
	                    
	         else {
	                       
	          editText1.setText("주소 식별 불가");
	               }
	      	
	      
	      	editText2.setText(global.str);
	      	editText4.setText("약" + global.distance + "m입니다.");
			
		/*	Geocoder geoCoder = new Geocoder(getApplicationContext());

			try {
				List<Address> locations = geoCoder.getFromLocationName(global.str, 1);

				for (Address a : locations) {
					 latti = a.getLatitude();
					 longi = a.getLongitude();
					
					Toast.makeText(getApplicationContext(), latti + "," + longi, Toast.LENGTH_LONG).show();

				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	*/
		
			
			 }
	 
	 		
	 				String getAddressFromLocation(Location location, Locale locale) {
	                List<Address> addressList = null;
	                Geocoder geocoder = new Geocoder(this, locale);
	                
	                //------------------------------------------------------------------
	                // 지오코더를 이용하여 주소 리스트를 구합니다.
	            	
	            	
	                
	                try {
	                    addressList = geocoder.getFromLocation(
	                        location.getLatitude(),
	                        location.getLongitude(),
	                        1
	                    );
	                } catch (IOException e) {
	                    Toast.makeText(this, "위치로부터 주소를 인식할 수 없습니다. 네트워크가 연결되어 있는지 확인해 주세요.", Toast.LENGTH_SHORT).show();
	                    e.printStackTrace();
	                    return "주소 인식 불가";
	                }
	                
	                //------------------------------------------------------------------
	                // 주소 리스트가 비어있는지 확인합니다. 비어 있으면, 주소 대신 그것이 없음을 알리는 문자열을 리턴합니다.
	                
	                if (1 > addressList.size()) {
	                    return "해당 위치에 주소 없음";
	                }
	                
	                //------------------------------------------------------------------
	                // 주소를 담는 문자열을 생성하고 리턴합니다.
	                
	                Address address = addressList.get(0);
	                StringBuilder addressStringBuilder = new StringBuilder();
	                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
	                    addressStringBuilder.append(address.getAddressLine(i));
	                    if (i < address.getMaxAddressLineIndex())
	                        addressStringBuilder.append("\n");
	                }

	                return addressStringBuilder.toString();
	            }
	 				
	 				
	            

	            
	            private class GPSListener implements LocationListener {
	        	
	            /**
	        		 * 위치 정보가 확인되었을 때 호출되는 메소드
	        		 */
	        	    public void onLocationChanged(Location location) {
	        			latitude = location.getLatitude();
	        			longitude = location.getLongitude();

	        			String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
	        			Log.i("GPSLocationService", msg);
	        			
	        			// 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
	        		
	                editText4.setText("+"+"msg");
	                }

	        	    public void onProviderDisabled(String provider) {
	        	    }

	        	    public void onProviderEnabled(String provider) {
	        	    }

	        	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        	    }

	        	}
	            
	            
	            
	      
	            
	            
	            
	            
	   }
