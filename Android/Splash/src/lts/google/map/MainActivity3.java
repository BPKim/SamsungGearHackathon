package lts.google.map;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity3 extends Activity  {
	
	TextView textView1,textView2,textView3,textView4,textView5;
	EditText editText1,editText2,editText3,editText4;
	Button button1,button2,button3;
	SQLiteDatabase database;
	String tableName = "CUSTOMER";
	String checkid1 = null;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity3);
	        createDatabase();
	        createTable();
	        
	        textView1 = (TextView)findViewById(R.id.textView1);
	        textView2 = (TextView)findViewById(R.id.textView2);
	        textView3 = (TextView)findViewById(R.id.textView3);
	        textView4 = (TextView)findViewById(R.id.textView4);
	        textView5 = (TextView)findViewById(R.id.textView5);
	        editText1 = (EditText)findViewById(R.id.editText1);
	        editText2 = (EditText)findViewById(R.id.editText2);
	        editText3 = (EditText)findViewById(R.id.editText3);
	        editText4 = (EditText)findViewById(R.id.editText4);
	        button1 = (Button)findViewById(R.id.button1);
	        button2 = (Button)findViewById(R.id.button2);
	        button3 = (Button)findViewById(R.id.button3);
	       
	        button3.setEnabled(false);
	        
	        button3.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			
	    			SQLiteDatabase db;
	    			ContentValues row;
	    			
	    			if(editText4.getText().toString().equalsIgnoreCase(editText3.getText().toString()))
	    			{
	    				if(editText3.getText().toString().length()<4)
	    				{
	    					Toast.makeText(getApplicationContext(), "형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
	    				}
	    				else
	    				{
	    					if(editText2.getText().toString().length()<4)
	    					{
	    						Toast.makeText(getApplicationContext(), "형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
	    					}
	    					else
	    					{
	    						if(checkid1.equalsIgnoreCase(editText2.getText().toString()))
	    						{
	    							Toast.makeText(getApplicationContext(), "가입되었습니다.", Toast.LENGTH_SHORT).show();

	    							insertData();
	    							Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
	    							startActivity(intent2);
	    							finish();
	    						}
	    						else
	    						{
	    				        	button3.setEnabled(false);
	    							Toast.makeText(getApplicationContext(), "아이디를 다시입력하세요.", Toast.LENGTH_SHORT).show();
	    						}
	    					}
	    				}
	    			}
	    			else
	    			{
	    				Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
	    			}
	    			/*insertData();
	    			Toast.makeText(getApplicationContext(), "가입완료.", Toast.LENGTH_SHORT).show();
	    			
	    			Intent i = new Intent(getApplicationContext(), MainActivity.class);
	                startActivity(i); */
	    		  } 
	    			
	    			//queryData();
	    			 
	           
	    			
	    		
	   
	    	}); 
	        
	        button1.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			//queryData();
	    			 queryData2();
	    		}
	    		/*	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);     

	    			builder.setTitle("아이디확인")        
	    			.setMessage("사용 가능한 아이디 입니다. 계속하시겠습니까?")        
	    			.setCancelable(false)        
	    			.setPositiveButton("아니요", new DialogInterface.OnClickListener(){       

	    			public void onClick(DialogInterface dialog, int whichButton){

	    			finish();   
	    				} 

	    		})
	    			.setNegativeButton("예", new DialogInterface.OnClickListener(){      

	    			public void onClick(DialogInterface dialog, int whichButton){
	    				dialog.cancel();

	    				}

	    			});

	    			AlertDialog dialog = builder.create();    
	    			dialog.show();    
	    			}  */
	    	
	    		}
	        ); 
	    			
	    		button2.setOnClickListener(new View.OnClickListener() {
	    		
	    		public void onClick(View v)
	    		{
	    			 Intent i = new Intent(getApplicationContext(), MainActivity.class);
	                 startActivity(i);
	    			
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
          //String sql = "create table " + tableName + "(id text, pw text)";
          
          try {
              database.execSQL(sql);//slq문 실행
             // text01.append("테이블이 만들어졌어요"+tableName+"\n");
          } catch (Exception e) {
              //text01.append("테이블 만들 때 예외 : "+e.getMessage()+"\n");
              e.printStackTrace();
          }
   
      }
      
      private void insertData(){
       database.beginTransaction(); //sql문을 실행하는 일정구간을 트랜잭션으로 묶어주겠다라는 의미
    
      
       String et01text = editText2.getText().toString();
       String et02text = editText3.getText().toString();
      // String et03text = editText1.getText().toString();
                                //트랜잭션 시작을 나타내는 메소드
       try{

          //String sql = "insert into "+ tableName + "(name, id, pw) values('" +et03text+"','" +et01text+"','"+et02text+"')";

           String sql = "insert into "+ tableName + "(id, pw) values('" +et01text+"','"+et02text+"')";
           database.execSQL(sql); 
           database.setTransactionSuccessful(); //트랜잭션으로 묶어준 일정영역의 SQL문들이 모두 성공적으로 끝났을 지정
           
       }catch(Exception e){
           //SQL문 실행중 오류가 발생하면 예외처리가 되고.. 
           //트랜잭션에 정의된 SQL쿼리가 성공되지 않았기때문에 finally블록에서 
           //트랜잭션 종료메서드 실행시(endTransaction()) 롤백이 된다.
           
           //text01.append("데이터 추가할때 예외 : "+e.getMessage()+"\n");
           e.printStackTrace();
       }finally{
           database.endTransaction(); //트랜잭션을 끝내는 메소드.
       }
           
   }
      
      private void queryData(){
           String sql = "select id ,pw from "+ tableName;
           //String sql = "id ,pw from "+ tableName;
           Cursor cursor = database.rawQuery(sql, null);
           
           
           
           if(cursor != null){
               int count = cursor.getCount(); //조회된 개수얻기
               
               for(int i = 0; i< count ; i++){
                   cursor.moveToNext();
                   
                  // String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getInt(2);
                   String name=cursor.getString(0) + "/" +cursor.getString(1);
                   textView5.append(name);
                   //String name= cursor.getString(1);
               }
           }
           
           
       }  
   
      private void queryData2(){
	        String sql = "select id from "+ tableName;
	        Cursor cursor = database.rawQuery(sql, null);
		 	String et01text = editText2.getText().toString();
		 	int checkID=0;
	        
	        
	        if(cursor != null){
	            int count = cursor.getCount(); //조회된 개수얻기	            
	            for(int i = 0; i< count ; i++){
	                cursor.moveToNext();
	                
	                //String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getInt(2);
	                String name=cursor.getString(0);
	                if(et01text.equalsIgnoreCase(name))
	                {
	                	Toast.makeText(getApplicationContext(), "아이디가 중복됩니다.", Toast.LENGTH_SHORT).show();
	                	button3.setEnabled(false);
	                	checkID++;
	                }
	                //String name= cursor.getString(1);
	            }
	        }
	        if(checkID==0)
	        {
	        	Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
	        	button3.setEnabled(true);
	        	checkid1 = editText2.getText().toString();
	        }
	        checkID=0;
	        
	    }  
      }
	 
	 

