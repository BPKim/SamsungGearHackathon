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
	    					Toast.makeText(getApplicationContext(), "������ �߸��Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
	    				}
	    				else
	    				{
	    					if(editText2.getText().toString().length()<4)
	    					{
	    						Toast.makeText(getApplicationContext(), "������ �߸��Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
	    					}
	    					else
	    					{
	    						if(checkid1.equalsIgnoreCase(editText2.getText().toString()))
	    						{
	    							Toast.makeText(getApplicationContext(), "���ԵǾ����ϴ�.", Toast.LENGTH_SHORT).show();

	    							insertData();
	    							Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
	    							startActivity(intent2);
	    							finish();
	    						}
	    						else
	    						{
	    				        	button3.setEnabled(false);
	    							Toast.makeText(getApplicationContext(), "���̵� �ٽ��Է��ϼ���.", Toast.LENGTH_SHORT).show();
	    						}
	    					}
	    				}
	    			}
	    			else
	    			{
	    				Toast.makeText(getApplicationContext(), "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", Toast.LENGTH_SHORT).show();
	    			}
	    			/*insertData();
	    			Toast.makeText(getApplicationContext(), "���ԿϷ�.", Toast.LENGTH_SHORT).show();
	    			
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

	    			builder.setTitle("���̵�Ȯ��")        
	    			.setMessage("��� ������ ���̵� �Դϴ�. ����Ͻðڽ��ϱ�?")        
	    			.setCancelable(false)        
	    			.setPositiveButton("�ƴϿ�", new DialogInterface.OnClickListener(){       

	    			public void onClick(DialogInterface dialog, int whichButton){

	    			finish();   
	    				} 

	    		})
	    			.setNegativeButton("��", new DialogInterface.OnClickListener(){      

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
          database = openOrCreateDatabase(name, MODE_WORLD_WRITEABLE, null); //DB�� �����ϸ� ����. �������������� ����
          //text01.append("�����ͺ��̽��� ����������"+name+"\n");
      }
      
      //���̺� ����
      private void createTable() {
          
          String sql = "create table " + tableName + "(id text, pw text)";
          //String sql = "create table " + tableName + "(id text, pw text)";
          
          try {
              database.execSQL(sql);//slq�� ����
             // text01.append("���̺��� ����������"+tableName+"\n");
          } catch (Exception e) {
              //text01.append("���̺� ���� �� ���� : "+e.getMessage()+"\n");
              e.printStackTrace();
          }
   
      }
      
      private void insertData(){
       database.beginTransaction(); //sql���� �����ϴ� ���������� Ʈ��������� �����ְڴٶ�� �ǹ�
    
      
       String et01text = editText2.getText().toString();
       String et02text = editText3.getText().toString();
      // String et03text = editText1.getText().toString();
                                //Ʈ����� ������ ��Ÿ���� �޼ҵ�
       try{

          //String sql = "insert into "+ tableName + "(name, id, pw) values('" +et03text+"','" +et01text+"','"+et02text+"')";

           String sql = "insert into "+ tableName + "(id, pw) values('" +et01text+"','"+et02text+"')";
           database.execSQL(sql); 
           database.setTransactionSuccessful(); //Ʈ��������� ������ ���������� SQL������ ��� ���������� ������ ����
           
       }catch(Exception e){
           //SQL�� ������ ������ �߻��ϸ� ����ó���� �ǰ�.. 
           //Ʈ����ǿ� ���ǵ� SQL������ �������� �ʾұ⶧���� finally��Ͽ��� 
           //Ʈ����� ����޼��� �����(endTransaction()) �ѹ��� �ȴ�.
           
           //text01.append("������ �߰��Ҷ� ���� : "+e.getMessage()+"\n");
           e.printStackTrace();
       }finally{
           database.endTransaction(); //Ʈ������� ������ �޼ҵ�.
       }
           
   }
      
      private void queryData(){
           String sql = "select id ,pw from "+ tableName;
           //String sql = "id ,pw from "+ tableName;
           Cursor cursor = database.rawQuery(sql, null);
           
           
           
           if(cursor != null){
               int count = cursor.getCount(); //��ȸ�� �������
               
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
	            int count = cursor.getCount(); //��ȸ�� �������	            
	            for(int i = 0; i< count ; i++){
	                cursor.moveToNext();
	                
	                //String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getInt(2);
	                String name=cursor.getString(0);
	                if(et01text.equalsIgnoreCase(name))
	                {
	                	Toast.makeText(getApplicationContext(), "���̵� �ߺ��˴ϴ�.", Toast.LENGTH_SHORT).show();
	                	button3.setEnabled(false);
	                	checkID++;
	                }
	                //String name= cursor.getString(1);
	            }
	        }
	        if(checkID==0)
	        {
	        	Toast.makeText(getApplicationContext(), "��밡���� ���̵��Դϴ�.", Toast.LENGTH_SHORT).show();
	        	button3.setEnabled(true);
	        	checkid1 = editText2.getText().toString();
	        }
	        checkID=0;
	        
	    }  
      }
	 
	 

