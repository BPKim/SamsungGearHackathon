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
 * 현재 위치의 지도를 보여주고 그 위에 오버레이를 추가하는 방법에 대해 알 수 있습니다.
 * 내 위치 표시를 해 줍니다.
 * 방향 센서를 이용해 나침반을 화면에 표시합니다.
 * 
 * Google APIs 중의 하나를 플랫폼으로 선택해야 합니다.
 * 구글맵 v2를 사용하기 위한 여러 가지 권한이 있어야 합니다.
 * 매니페스트 파일 안에 있는 키 값을 PC에 맞는 것으로 새로 발급받아서 넣어야 합니다.
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

        // 메인 레이아웃 객체 참조
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
				Log.d("TAG", "비디오 스타트");
				
				
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

    						// 마커 위치 
    						.position(new LatLng(latti, longi)) 
    						.title(latti + "," + longi) // 말풍선 주 내용 
    						.snippet(global.str+"입니다."); // 말풍선 보조내용 

    						// 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출 
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
										Log.d("mylog","제이슨 파싱 성공");
									}catch(Exception e) {
										e.printStackTrace();
										Log.d("mylog","제이슨 파싱 익셉션");
									}finally {
										Log.d("mylog","제이슨 데이터를 이용해 그리기 시작");
										drawPath(urlTopass);
										//drawPath(ret);
										Log.d("mylog","제이슨 데이터를 이용해 그리기 끝");
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
    	    				Log.e("CHECK","json 성공? : "+latitude); 
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
    				Toast.makeText(getApplicationContext(), "두점 사이의 거리는 약"+global.distance+"m입니다.", Toast.LENGTH_SHORT).show();
    				
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
        
        
        // 지도 객체 참조
        map = (GoogleMap) ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
             

   	 	// 센서 관리자 객체 참조
   	 	mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
   	    button.setOnClickListener(buttonGetAddressClickListener);
   	   

		// 나침반을 표시할 뷰 생성
		boolean sideBottom = true;
	   	mCompassView = new CompassView(this);
	    mCompassView.setVisibility(View.VISIBLE);

	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	    		RelativeLayout.LayoutParams.MATCH_PARENT, 
	    		RelativeLayout.LayoutParams.MATCH_PARENT);
	    

	    mCompassEnabled = true; 
	    
       // 위치 확인하여 위치 표시 시작
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
            	Log.e("CHECK","path 그리기 시작");
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

            	Log.e("CHECK","json 성공? : "+json);
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
        	Log.e("CHECK","line 비어잇음");
        	((Bundle) map).clear();
     //   map.addMarker(new MarkerOptions().position(new LatLng(latti,longi)).icon(
              //  BitmapDescriptorFactory.fromResource(R.drawable.arrow_n)));
       // map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(
              //  BitmapDescriptorFactory.fromResource(R.drawable.arrow_n)));
        }else {
        	Log.e("CHECK","line 안빔");
        }
        
        try {
            // Tranform the string into a json object
        	
            final JSONObject json = new JSONObject(result);
            Log.e("CHECK","line 넘어와바");
            
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
            Log.e("CHECK","그리기 실패");
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
               
                 edit.setText("주소 식별 불가");
             }
            
        }
    };
    
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
        
    @Override
	public void onResume() {
		super.onResume();

		// 내 위치 자동 표시 enable
		((com.google.android.gms.maps.GoogleMap) map).setMyLocationEnabled(true);
		if(mCompassEnabled) {
            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// 내 위치 자동 표시 disable
		((com.google.android.gms.maps.GoogleMap) map).setMyLocationEnabled(false);
		if(mCompassEnabled) {
			mSensorManager.unregisterListener(mListener);
		}
	}
 
     /**
     * 현재 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
    	// 위치 관리자 객체 참조
    	LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// 리스너 객체 생성
    	GPSListener gpsListener = new GPSListener();
		long minTime = 10000;
		float minDistance = 0;

		// GPS 기반 위치 요청
		manager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					minTime,
					minDistance,
					gpsListener);
		
		// 네트워크 기반 위치 요청
		manager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER,
				minTime,
				minDistance,
				gpsListener);

		Toast.makeText(getApplicationContext(), "위치 확인 시작함. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스너 정의
     */
	private class GPSListener implements LocationListener {
		/**
		 * 위치 정보가 확인되었을 때 호출되는 메소드
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
			
			// 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
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
	 * 현재 위치의 지도를 보여주기 위해 정의한 메소드
	 * 
	 * @param latitude
	 * @param longitude
	 */
	private void showCurrentLocation(Double latitude, Double longitude) {
		// 현재 위치를 이용해 LatLon 객체 생성
		LatLng curPoint = new LatLng(latitude, longitude);

		((com.google.android.gms.maps.GoogleMap) map).animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17));

		// 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN, 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
		((com.google.android.gms.maps.GoogleMap) map).setMapType(((com.google.android.gms.maps.GoogleMap) map).MAP_TYPE_NORMAL); 
		
		// 현재 위치 주위에 아이콘을 표시하기 위해 정의한 메소드
		//showAllBankItems(latitude, longitude);
	}
	
	/**
	 * 아이콘을 표시하기 위해 정의한 메소드
	 */
	
	
	/**
	 * 센서의 정보를 받기 위한 리스너 객체 생성
	 */
	private final SensorEventListener mListener = new SensorEventListener() {
        private int iOrientation = -1;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        // 센서의 값을 받을 수 있도록 호출되는 메소드
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
