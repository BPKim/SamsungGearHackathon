package lts.google.map;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity5 extends Activity {
	
	TextView textView1,textView2,textView3,textView4,textView5;
	Button button1;
	CheckBox checkBox1,checkBox2;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity5);
	        
	        button1 = (Button)findViewById(R.id.button1);
	        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
	        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
	        textView1 = (TextView)findViewById(R.id.textView1);
	        textView2 = (TextView)findViewById(R.id.textView2);
	        textView3 = (TextView)findViewById(R.id.textView3);
	        textView4 = (TextView)findViewById(R.id.textView4);
	        textView5 = (TextView)findViewById(R.id.textView5);
	        
	        button1.setEnabled(false);
	        checkBox1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(checkBox1.isChecked())
					{
						checkBox1.setChecked(true);
						if(checkBox2.isChecked())
						{
							button1.setEnabled(true);
						}
					}
					else
					{
						checkBox1.setChecked(false);
						button1.setEnabled(false);
					}
					
				}
			});
	        
	        checkBox2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(checkBox2.isChecked())
					{
						checkBox2.setChecked(true);
						
						if(checkBox1.isChecked())
						{
							button1.setEnabled(true);
						}
					}
					else
					{
						checkBox2.setChecked(false);
						button1.setEnabled(false);
					}
					
				
					
				}
			});
	        
	        
	        
	        button1.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			 
	    				 Intent i = new Intent(getApplicationContext(), MainActivity3.class);
	    				 startActivity(i);
	    		}   		
	    	
	    	});
	       //button1.setEnabled(false);
/*	        button1.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			 //Intent i = new Intent(getApplicationContext(), MainActivity3.class);
    				 //startActivity(i);
	    			
	    			 if(checkBox1.isChecked() || checkBox2.isChecked())
	    			 {
	    				 button1.setEnabled(false);
	    			 }
	    				 
	    				 else{
	    					 button1.setEnabled(true);
	    				 }
	    				 Intent i = new Intent(getApplicationContext(), MainActivity3.class);
	    				 startActivity(i);
	    			 }
	    			
	    		
	    	
	    	});*/
	        
	 }
	 
}
	        