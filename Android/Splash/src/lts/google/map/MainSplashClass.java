package lts.google.map;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
  
 
public class MainSplashClass extends Activity {
	
	private Handler Handler;
    private ProgressDialog dialog; 

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 자동 생성된 메소드 스텁
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        
       ProgressDialog.show(MainSplashClass.this, "", "잠시만 기다려 주세요..."); 
       
        	Handler hdl = new Handler() {
    	   
     @Override
     public void handleMessage(Message msg) {
            	  
                // TODO 자동 생성된 메소드 스텁
                finish(); // activity 종료
                
            }
 
        };
        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려 주세요.");
        dialog.setCancelable(false);
        hdl.sendEmptyMessageDelayed(0, 3000);
        dialog.dismiss();
    }
 
}
