package lts.google.map;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
	
public class MainActivity4 extends Activity {
	
	
	Button button1,button2;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity4);
	        
	        button1 = (Button)findViewById(R.id.button1);
	        button2 = (Button)findViewById(R.id.button2);
	        
	        
	        Toast.makeText(getApplicationContext(), global.ID+"님 환영합니다.", Toast.LENGTH_SHORT).show();
	        
	        button1.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			 Intent i = new Intent(getApplicationContext(), map.class);
	                 startActivity(i);
	                 finish();
	    			
	    		}
	    	
	    	});
	        button2.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			 Intent i = new Intent(getApplicationContext(), MainActivity6.class);
	                 startActivity(i);
	                 finish();
	    			
	    		}
	    	
	    	});
	        
	    }  
	 

	 
	 	
	 }

		

