package lts.google.map;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity {
	
	Button button1,button2;
	CheckBox checkBox1,checkBox2;
	EditText loginID1,loginID2;
	SQLiteDatabase database;
	String tableName = "CUSTOMER";
	TextView textView1;
	Thread t;
	int order=0;
	int order2=0;
	int count=0;
	int flagcount=0; 
	int checkID=0; 

    @Override
    protected void onCreate(Bundle savedInstanceState) 
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase();
        createTable() ; 
        if(global.flag==0) 
        {
        	global.flag++;
        	startActivity(new Intent(this, MainSplashClass.class));
        }

        button1 = (Button)findViewById(R.id.button1);//login
        button2 = (Button)findViewById(R.id.button2);//create_acc
        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        loginID1 = (EditText)findViewById(R.id.editText1);//id
        loginID2 = (EditText)findViewById(R.id.editText2);//pw
        textView1 = (TextView)findViewById(R.id.textView1);
       
        button1.setOnClickListener(new View.OnClickListener() {
    		
    		public void onClick(View v)
    		{
    		
    			 IDcheck();
    			 if(checkID==1)
    			 {
    				 checkID=0;
        			 Intent k = new Intent(getApplicationContext(), MainActivity4.class);
    	             startActivity(k);
    			 }
    			 else
    			 {
    				 Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
    			 }
    			 //PWcheck();

          	}
    	
    	});
        
        button2.setOnClickListener(new View.OnClickListener() {
    		
    		public void onClick(View v)
    		{
    			 Intent i = new Intent(getApplicationContext(), MainActivity5.class);
                 startActivity(i);
                 finish();
    			
    		}
    	
    	});
        
    }  
    
    	private void createDatabase(){
            String name = "customer1.db"; 
            database = openOrCreateDatabase(name, MODE_WORLD_WRITEABLE, null); //DB가 존재하면 오픈. 존재하지않은면 생성
            //text01.append("데이터베이스가 만들어졌어요"+name+"\n");
        }
        
        //테이블 생성
        private void createTable() {
            
            String sql = "create table " + tableName + "(id text, pw text)";
            
            try {
                database.execSQL(sql);//slq문 실행
               // text01.append("테이블이 만들어졌어요"+tableName+"\n");
            } catch (Exception e) {
                //text01.append("테이블 만들 때 예외 : "+e.getMessage()+"\n");
                e.printStackTrace();
            }
     
        }
        
        private void queryData(){
	        String sql = "select id,pw from "+ tableName;
	        Cursor cursor = database.rawQuery(sql, null);
	        
	        
	        
	        if(cursor != null){
	            int count = cursor.getCount(); //조회된 개수얻기
	            
	            for(int i = 0; i< count ; i++){
	                cursor.moveToNext();
	                
	                //String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getInt(2);
	                String name=cursor.getString(0) + "/" +cursor.getString(1);
	                //String name= cursor.getString(1);
	            }
	        }
	        
	    }  
        
        private void IDcheck() 
        {
 	        String sql = "select id,pw from "+ tableName;
 	        Cursor cursor = database.rawQuery(sql, null);
 		 	

 		 	   String logIDtext = loginID1.getText().toString();
 		 	   String logPWtext = loginID2.getText().toString();
 	        
 	        
 	        if(cursor != null){
 	            int count = cursor.getCount(); //조회된 개수얻기	            
 	            for(int i = 0; i< count ; i++){
 	                cursor.moveToNext();
 	                
 	                //String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getInt(2);
 	                String id=cursor.getString(0);
 	                String pw = cursor.getString(1);
 	                if(logIDtext.equalsIgnoreCase(id))
 	                {
 	                	//checkID++;
 	                	//order=order2;
 	                	if(logPWtext.equalsIgnoreCase(pw))
 	                	{
 	                		checkID=1;
 	                		//비밀번호까지 확인됫을떄
 	                		global.ID=logIDtext;
 	                		break;
 	                	}
 	                	//break;
 	                }
 	                order2++;
 	                //String name= cursor.getString(1);
 	            }
 	        }
 	        if(checkID==0)
 	        {
 	        	Toast.makeText(getApplicationContext(), "없는 계정입니다.", Toast.LENGTH_SHORT).show();
 	        }
 	        
 	    }  
 	    private void PWcheck(){
 	        String sql = "select pw from "+ tableName;
 	        Cursor cursor = database.rawQuery(sql, null);
 		 	  
 	        String logIDtext = loginID1.getText().toString();
 		    String logPWtext = loginID2.getText().toString();
 		    String pw=null;
 	        
 	        if(cursor != null){           
 	            for(int i = 0; i< order+1 ; i++)
 	            {
 	                cursor.moveToNext();
 	            }
 	                
 	                //String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getInt(2);
 	                pw=cursor.getString(0);

 	                //String name= cursor.getString(1);
 	            }
             if(logPWtext.equalsIgnoreCase(pw))
             {
             	global.ID=loginID1.getText().toString();
             	final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "LOGIN", "로그인 로딩중입니다.");						// TODO Auto-generated method stub
             t =	new Thread(new Runnable()
         		{
         			public void run()//new thread
         			{						
         				while(global.count!=3) //((Button)v == buttonStart) || 
         				{
         					try
         					{
         						Thread.sleep(1000); // rest 1second								
         					}
         					catch(InterruptedException e)
         					{}
         						global.count=global.count+1;
         					if(global.count==3)
         					{
         						t.interrupt();
         						dialog.dismiss();
         						Intent intent2 = new Intent(getApplicationContext(), MainActivity4.class);
         						startActivity(intent2);
         						finish();
         					}
         				}
         			}
         		});
             t.start();

             }
             else
             {
             	Toast.makeText(getApplicationContext(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
             	order=0;
             	order2=0;
             }
 	  }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}
