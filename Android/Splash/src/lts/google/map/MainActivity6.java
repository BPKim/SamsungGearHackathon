package lts.google.map;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity6 extends Activity {
	
	//TextView textView1,textView2,textView3,textView4,textView5;
	Button button1;
	//CheckBox checkBox1,checkBox2;
	ListView listView1;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity6);
	        
	        listView1 = (ListView)findViewById(R.id.listView1);
	        button1 = (Button)findViewById(R.id.button1);
	        
	   }
}