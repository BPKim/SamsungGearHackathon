package lts.google.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
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
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



/**
 * ���� ��ġ�� ������ �����ְ� �� ���� �������̸� �߰��ϴ� ����� ���� �� �� �ֽ��ϴ�.
 * �� ��ġ ǥ�ø� �� �ݴϴ�.
 * ���� ������ �̿��� ��ħ���� ȭ�鿡 ǥ���մϴ�.
 * 
 * Google APIs ���� �ϳ��� �÷������� �����ؾ� �մϴ�.
 * ���۸� v2�� ����ϱ� ���� ���� ���� ������ �־�� �մϴ�.
 * �Ŵ��佺Ʈ ���� �ȿ� �ִ� Ű ���� PC�� �´� ������ ���� �߱޹޾Ƽ� �־�� �մϴ�.
 * 
 * @author Mike
 * @param <GoogleMap>
 */
public class map<GoogleMap> extends Activity {
  
	private RelativeLayout mainLayout;
	private TableLayout serveLayout;
	private GoogleMap map;
	private TextView text,text1;
	private EditText edit,edit1;
	private Button button;
	Button button1,button2,button3,connect_btn;
	
	private CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;
    private GeoPoint geoPoint;
    private OverlayItem overlayItem;
	private MapView mapView;
	private MapController mapController;
	private List<Overlay> mapOverlays;
	
	Double longitude;
	Double latitude;
	Double latti;
	Double longi;
	
	Double sourcelat;
	Double sourcelog;
	Double destlat;
    Double destlog;
	
    List list;
    
    
    
    HttpClient httpclient;
    Polyline line;
    Double center1;
    Double center2;
    Float distance;
    LatLng src;
    LatLng dest;
    String str;
    Context context;
    private ControlHelper controlHelper; 
	String  IP_ADDRESS;
    

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);
        
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        // ���� ���̾ƿ� ��ü ����
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        serveLayout = (TableLayout) findViewById(R.id.tablelayout);
        text = (TextView)findViewById(R.id.textViewAddress);
        text1 = (TextView)findViewById(R.id.textViewAddress1);
        edit = (EditText)findViewById(R.id.editTextAddress);
        edit1 = (EditText)findViewById(R.id.editTextAddress1);
        button = (Button)findViewById(R.id.buttonGetAddress);
        button1 = (Button)findViewById(R.id.buttonGetAddress1);
        button2 = (Button)findViewById(R.id.buttonGetAddress2);
        button3 = (Button)findViewById(R.id.buttonGetAddress3);
        connect_btn = (Button)findViewById(R.id.buttonGetAddress4);
        context  = getApplicationContext();
       
      
        connect_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("TAG", "���� ��ŸƮ");
				
				
				controlHelper = new ControlHelper("192.168.25.222");
				controlHelper.startControlThread();
				

				connect_btn.setVisibility(View.GONE);
			}
		});
        
        
        
        
        
        button1.setOnClickListener(new View.OnClickListener() {
    		
    		public void onClick(View v)
    		{
    			 Intent i = new Intent(getApplicationContext(), MainActivity4.class);
                 startActivity(i);
                 finish();
    			
    		}
    	
    	});
        
        button2.setOnClickListener(new View.OnClickListener() {
    		
    		public void onClick(View v)
    		{
    			 Intent i = new Intent(getApplicationContext(), MainActivity7.class);
                 startActivity(i);
                 finish();
    			
    		}
    	
    	});
        
        button3.setOnClickListener(new View.OnClickListener() {
        	
        	 @Override
            public void onClick(View v) {
         
        			global.str = edit1.getText().toString();
        			
        			Geocoder geoCoder = new Geocoder(getApplicationContext());

    				try {
    					List<Address> locations = geoCoder.getFromLocationName(global.str, 1);

    					for (Address a : locations) {
    						 latti = a.getLatitude();
    						 longi = a.getLongitude();
    						
    						Toast.makeText(getApplicationContext(), latti + "," + longi, Toast.LENGTH_SHORT).show();

    				
    				MarkerOptions markerOptions = new MarkerOptions() 

    						// ��Ŀ ��ġ 
    						.position(new LatLng(latti, longi)) 
    						.title(latti + "," + longi) // ��ǳ�� �� ���� 
    						.snippet(global.str+"�Դϴ�."); // ��ǳ�� �������� 

    						// ��Ŀ�� �߰��ϰ� ��ǳ�� ǥ���� ���� �����ֵ��� ȣ�� 
    						((com.google.android.gms.maps.GoogleMap) map).addMarker(markerOptions).showInfoWindow(); 
    						
    				
    						
    						
    						
    						
    						
    					/*	final String urlTopass = getUrl(latitude, longitude, latti, longi);
    				        Log.e("CHECK","urlTopass = "+urlTopass);   
    				       
    			            
    				        connectAsyncTask ca = new connectAsyncTask(urlTopass);
    						ca.execute();
    				        ca.onPreExecute();
    						ca.doInBackground();
    						ca.onPostExecute(urlTopass);
    						
    						// JSONParser();
    				        
    				       new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									//String ret=null;
									try{
										JSONParser js = new JSONParser();
										js.getJSONFromUrl(urlTopass);
										//ret=js.getJSONFromUrl(urlTopass);									
										Log.d("mylog","���̽� �Ľ� ����");
									}catch(Exception e) {
										e.printStackTrace();
										Log.d("mylog","���̽� �Ľ� �ͼ���");
									}finally {
										Log.d("mylog","���̽� �����͸� �̿��� �׸��� ����");
										drawPath(urlTopass);
										//drawPath(ret);
										Log.d("mylog","���̽� �����͸� �̿��� �׸��� ��");
									}
		    						
								}
							}).start();	*/
    						
    						
    						
    						
    						
    						
    						String url = "http://maps.googleapis.com/maps/api/directions/json?origin="+(latitude+","+longitude)+"&destination="+(latti+","+longi)+"&sensor=false&departure_time=1343605500&mode=transit";


    					    
    				        connectAsyncTask ca = new connectAsyncTask(url);
    						ca.execute();
    				        ca.onPreExecute();
    						ca.doInBackground();
    						ca.onPostExecute(url);
    						
    	    				
    	    				
       	    			/*	HttpPost httppost = new HttpPost(url);
    	    				Log.e("CHECK","json ����? : "+latitude); 
    	    				HttpResponse response = httpclient.execute(httppost);
    	    				HttpEntity entity = response.getEntity();
    	    				InputStream is = null;
    	    				is = entity.getContent();
    	    				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
    	    				StringBuilder sb = new StringBuilder();
    	    				sb.append(reader.readLine() + "\n");
    	    				String line = "0";
    	    				while ((line = reader.readLine()) != null) {
    	    				    sb.append(line + "\n");
    	    				}
    	    				is.close();
    	    				reader.close();
    	    				String result = sb.toString();
    	    				JSONObject jsonObject = new JSONObject(result);
    	    				JSONArray routeArray = jsonObject.getJSONArray("routes");
    	    				JSONObject routes = routeArray.getJSONObject(0);
    	    				JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
    	    				String encodedString = overviewPolylines.getString("points");
    	    				List<GeoPoint> pointToDraw = decodePoly(encodedString);

    	    				//Added line:
    	    				mapView.getOverlays().add(new RoutePathOverlay(pointToDraw));
    	    				
    						*/
    						
    						
    						
    						
    						
    						
    						
    					
    						
    				        
    				        
    				         						
    						
    				      //  drawPath(urlTopass);
    				    //	Polyline line = map.addPolyline(new PolylineOptions()
        				//	.add(new LatLng(latti,longi), new LatLng(latitude, longitude))
        				//	.width(5)
        				//	.color(Color.RED));
    						
    						
    						
    						
    				            
    			}
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				//} catch (JSONException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					} 
    				
    				
    				
    				


				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				center1=((latitude + latti)/2);
    				center2=((longitude + longi)/2);
    				//Toast.makeText(getApplicationContext(), center1+","+center2, Toast.LENGTH_LONG).show();
    				LatLng curPoint = new LatLng(center1, center2);
    				((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,10));
    				
    				Location locationA = new Location("Point A");
    				
    				locationA.setLatitude(latitude);
    				locationA.setLongitude(longitude);
    				
    				Location locationB = new Location("Point B");
    				
    				locationB.setLatitude(latti);
    				locationB.setLongitude(longi);
    				
    				global.distance = locationA.distanceTo(locationB);
    				Toast.makeText(getApplicationContext(), "���� ������ �Ÿ��� ��"+global.distance+"m�Դϴ�.", Toast.LENGTH_SHORT).show();
    				
    				if(global.distance<500)
    				{
    					((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,17));
    				}
    				else if(global.distance>500 && global.distance<2000)
    				{
    					((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
    				}	
    				else if(global.distance>2000 && global.distance<3000)
    				{
    					((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,14));
    				}
    				else if(global.distance>3000 && global.distance<6000)
    				{
    					((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,13));
    				}
    				else if(global.distance>6000 && global.distance<10000)
    				{
    					((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,12));
    				}
    				else if(global.distance>10000 && global.distance<20000)
    				{
    					((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,11));
    				}
    				
    				
    				
    		}
        	 
        });
        
        
        // ���� ��ü ����
        map = (GoogleMap) ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
             

   	 	// ���� ������ ��ü ����
   	 	mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
   	    button.setOnClickListener(buttonGetAddressClickListener);
   	   

		// ��ħ���� ǥ���� �� ����
		boolean sideBottom = true;
	   	mCompassView = new CompassView(this);
	    mCompassView.setVisibility(View.VISIBLE);

	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	    		RelativeLayout.LayoutParams.MATCH_PARENT, 
	    		RelativeLayout.LayoutParams.MATCH_PARENT);
	    

	    mCompassEnabled = true; 
	    
       // ��ġ Ȯ���Ͽ� ��ġ ǥ�� ����
        startLocationService();
    }
    
    
  /*  public String getUrl(double latitude, double longitude, double latti,  double longi){
     
    StringBuilder urlString = new StringBuilder();
     
    urlString.append(("http://maps.google.com/maps?f=d&hl=en")); 
    urlString.append("&saddr=");
    urlString.append(Double.toString(latitude));
    urlString.append(",");
    urlString.append(Double.toString(longitude));
    urlString.append("&daddr=");// to
    urlString.append(Double.toString(latti));
    urlString.append(",");
    urlString.append(Double.toString(longi));
    urlString.append("&ie=UTF8&0&om=0&output=kml");
     
    return urlString.toString();
    }
     
    */
    
  



    public class connectAsyncTask extends AsyncTask<Void, Void, String> {
        //private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlpass) {
            url = urlpass;
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
          //  Log.e("CHECK","onPreExecute"); 
           // progressDialog = new ProgressDialog(context);
           // progressDialog.setMessage("Fetching route, Please wait...");
           // progressDialog.setIndeterminate(true);
           // progressDialog.show();

        }

        @Override
        public String doInBackground(Void... params) {
          //  Log.e("CHECK","doInBackground");
            JSONParser jParser = new JSONParser();
           // Log.e("CHECK","jParser: "+jParser);
            String json = jParser.getJSONFromUrl(url);

            return json;
        } 

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("CHECK","onPostExecute result : "+result);
         //   progressDialog.hide();
            if (result != null) {
            	Log.e("CHECK","path �׸��� ����");
                drawPath(result);
            }
        }
    }

    

    

    public class JSONParser {

        InputStream is = null;
        JSONObject jObj = null;
        String json = "";

        // constructor
       public JSONParser() {
        }

        public String getJSONFromUrl(String url) {
        	
        	
            // Making HTTP request
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                json = sb.toString();

            	Log.e("CHECK","json ����? : "+json);
                is.close();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            return json;

        }
    }

    
    	public void drawPath(String result) {
    	Log.d("CHECK","result data = [ "+result+" ]");
        if (line != null) {
        	Log.e("CHECK","line �������");
        	((Bundle) map).clear();
     //   map.addMarker(new MarkerOptions().position(new LatLng(latti,longi)).icon(
              //  BitmapDescriptorFactory.fromResource(R.drawable.arrow_n)));
       // map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(
              //  BitmapDescriptorFactory.fromResource(R.drawable.arrow_n)));
        }else {
        	Log.e("CHECK","line �Ⱥ�");
        }
        
        try {
            // Tranform the string into a json object
        	
            final JSONObject json = new JSONObject(result);
            Log.e("CHECK","line �Ѿ�͹�");
            
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            
    
            
            
            
            
            
            
            List<LatLng> list = decodePoly(encodedString);
            //draw point
			
            for (int z = 0; z < list.size() - 1; z++) {
                src = list.get(z);
                dest = list.get(z + 1);
                line = ((com.google.android.gms.maps.GoogleMap) map).addPolyline(new PolylineOptions()
              //  .add(new LatLng(latti,longi), new LatLng(latitude, longitude))
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(Color.RED).geodesic(true));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CHECK","�׸��� ����");
        }
       
    }
    
    
     public List<LatLng> decodePoly(String encoded) {
    	
    	
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }	
    
    
  /*  public List<GeoPoint> decodePoly(String encoded) {

        List<GeoPoint> poly = new ArrayList<GeoPoint>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6), (int) (((double) lng / 1E5) * 1E6));
            poly.add(p);
        }

        return poly;
    }

    
    
    public class RoutePathOverlay extends Overlay {

        private int _pathColor;
        private final List<GeoPoint> _points;
        private boolean _drawStartEnd;

        public RoutePathOverlay(List<GeoPoint> points) {
                this(points, Color.RED, true);
        }

        public RoutePathOverlay(List<GeoPoint> points, int pathColor, boolean drawStartEnd) {
                _points = points;
                _pathColor = pathColor;
                _drawStartEnd = drawStartEnd;
        }


        public void drawOval(Canvas canvas, Paint paint, Point point) {
                Paint ovalPaint = new Paint(paint);
                ovalPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                ovalPaint.setStrokeWidth(2);
                int _radius = 6;
                RectF oval = new RectF(point.x - _radius, point.y - _radius, point.x + _radius, point.y + _radius);
                canvas.drawOval(oval, ovalPaint);               
        }

        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
                com.google.android.maps.Projection projection = mapView.getProjection();
                if (shadow == false && _points != null) {
                        Point startPoint = null, endPoint = null;
                        Path path = new Path();
                        //We are creating the path
                        for (int i = 0; i < _points.size(); i++) {
                                GeoPoint gPointA = _points.get(i);
                                Point pointA = new Point();
                                projection.toPixels(gPointA, pointA);
                                if (i == 0) { //This is the start point
                                        startPoint = pointA;
                                        path.moveTo(pointA.x, pointA.y);
                                } else {
                                        if (i == _points.size() - 1)//This is the end point
                                                endPoint = pointA;
                                        path.lineTo(pointA.x, pointA.y);
                                }
                        }

                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setColor(_pathColor);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(5);
                        paint.setAlpha(90);
                        if (getDrawStartEnd()) {
                                if (startPoint != null) {
                                        drawOval(canvas, paint, startPoint);
                                }
                                if (endPoint != null) {
                                        drawOval(canvas, paint, endPoint);
                                }
                        }
                        if (!path.isEmpty())
                                canvas.drawPath(path, paint);
                }
                return super.draw(canvas, mapView, shadow, when);
        }

        public boolean getDrawStartEnd() {
                return _drawStartEnd;
        }

        public void setDrawStartEnd(boolean markStartEnd) {
                _drawStartEnd = markStartEnd;
        }
    }	
    
    
    
    
    
    
    
    
    /**
    private List<LatLng> decodePoly(String encodedString) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	OnClickListener buttonGetAddressClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
    
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            
             if (null != lastKnownLocation) {
                
                 edit.setText(getAddressFromLocation(lastKnownLocation, Locale.KOREA));
             }
             else {
               
                 edit.setText("�ּ� �ĺ� �Ұ�");
             }
            
        }
    };
    
    String getAddressFromLocation(Location location, Locale locale) {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this, locale);
        
        //------------------------------------------------------------------
        // �����ڴ��� �̿��Ͽ� �ּ� ����Ʈ�� ���մϴ�.
        
        try {
            addressList = geocoder.getFromLocation(
                location.getLatitude(),
                location.getLongitude(),
                1
            );
        } catch (IOException e) {
            Toast.makeText(this, "��ġ�κ��� �ּҸ� �ν��� �� �����ϴ�. ��Ʈ��ũ�� ����Ǿ� �ִ��� Ȯ���� �ּ���.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return "�ּ� �ν� �Ұ�";
        }
        
        //------------------------------------------------------------------
        // �ּ� ����Ʈ�� ����ִ��� Ȯ���մϴ�. ��� ������, �ּ� ��� �װ��� ������ �˸��� ���ڿ��� �����մϴ�.
        
        if (1 > addressList.size()) {
            return "�ش� ��ġ�� �ּ� ����";
        }
        
        //------------------------------------------------------------------
        // �ּҸ� ��� ���ڿ��� �����ϰ� �����մϴ�.
        
        Address address = addressList.get(0);
        StringBuilder addressStringBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressStringBuilder.append(address.getAddressLine(i));
            if (i < address.getMaxAddressLineIndex())
                addressStringBuilder.append("\n");
        }
        
        return addressStringBuilder.toString();
    }
        
    @Override
	public void onResume() {
		super.onResume();

		// �� ��ġ �ڵ� ǥ�� enable
		((com.google.android.gms.maps.GoogleMap) map).setMyLocationEnabled(true);
		if(mCompassEnabled) {
            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// �� ��ġ �ڵ� ǥ�� disable
		((com.google.android.gms.maps.GoogleMap) map).setMyLocationEnabled(false);
		if(mCompassEnabled) {
			mSensorManager.unregisterListener(mListener);
		}
	}
 
     /**
     * ���� ��ġ Ȯ���� ���� ������ �޼ҵ�
     */
    private void startLocationService() {
    	// ��ġ ������ ��ü ����
    	LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// ������ ��ü ����
    	GPSListener gpsListener = new GPSListener();
		long minTime = 10000;
		float minDistance = 0;

		// GPS ��� ��ġ ��û
		manager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					minTime,
					minDistance,
					gpsListener);
		
		// ��Ʈ��ũ ��� ��ġ ��û
		manager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER,
				minTime,
				minDistance,
				gpsListener);

		Toast.makeText(getApplicationContext(), "��ġ Ȯ�� ������. �α׸� Ȯ���ϼ���.", Toast.LENGTH_SHORT).show();
    }

    /**
     * ������ ����
     */
	private class GPSListener implements LocationListener {
		/**
		 * ��ġ ������ Ȯ�εǾ��� �� ȣ��Ǵ� �޼ҵ�
		 */
	    public void onLocationChanged(Location location) {
	    		
	    	latitude = location.getLatitude();
			longitude = location.getLongitude();

			try {
				if(ControlHelper.dos !=null){
				ControlHelper.dos.writeUTF(latitude+":"+longitude);	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
			Log.i("GPSLocationService", msg);
			
			// ���� ��ġ�� ������ �����ֱ� ���� ������ �޼ҵ� ȣ��
			showCurrentLocation(latitude, longitude);
		}

	    public void onProviderDisabled(String provider) {
	    }

	    public void onProviderEnabled(String provider) {
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }

	}

	/**
	 * ���� ��ġ�� ������ �����ֱ� ���� ������ �޼ҵ�
	 * 
	 * @param latitude
	 * @param longitude
	 */
	private void showCurrentLocation(Double latitude, Double longitude) {
		// ���� ��ġ�� �̿��� LatLon ��ü ����
		LatLng curPoint = new LatLng(latitude, longitude);

		((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17));

		// ���� ���� ����. �������� ��쿡�� GoogleMap.MAP_TYPE_TERRAIN, ���� ������ ��쿡�� GoogleMap.MAP_TYPE_SATELLITE
		((com.google.android.gms.maps.GoogleMap) map).setMapType(((com.google.android.gms.maps.GoogleMap) map).MAP_TYPE_NORMAL); 
		
		// ���� ��ġ ������ �������� ǥ���ϱ� ���� ������ �޼ҵ�
		//showAllBankItems(latitude, longitude);
	}
	
	/**
	 * �������� ǥ���ϱ� ���� ������ �޼ҵ�
	 */
	
	
	/**
	 * ������ ������ �ޱ� ���� ������ ��ü ����
	 */
	private final SensorEventListener mListener = new SensorEventListener() {
        private int iOrientation = -1;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        // ������ ���� ���� �� �ֵ��� ȣ��Ǵ� �޼ҵ�
        public void onSensorChanged(SensorEvent event) {
            if (iOrientation < 0) {
                    iOrientation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            }
            
            mCompassView.setAzimuth(event.values[0] + 90 * iOrientation);
            mCompassView.invalidate();

        }
 
};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
