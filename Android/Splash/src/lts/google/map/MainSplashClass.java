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
        // TODO �ڵ� ������ �޼ҵ� ����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        
       ProgressDialog.show(MainSplashClass.this, "", "��ø� ��ٷ� �ּ���..."); 
       
        	Handler hdl = new Handler() {
    	   
     @Override
     public void handleMessage(Message msg) {
            	  
                // TODO �ڵ� ������ �޼ҵ� ����
                finish(); // activity ����
                
            }
 
        };
        dialog = new ProgressDialog(this);
        dialog.setMessage("��ø� ��ٷ� �ּ���.");
        dialog.setCancelable(false);
        hdl.sendEmptyMessageDelayed(0, 3000);
        dialog.dismiss();
    }
 
}
