package lts.google.map;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



import android.os.Handler;
import android.util.Log;

public class ControlHelper {


	private String IP_ADDRESS;
	private int VP_PORT = 8080;
	private Socket client; 
	public DataInputStream dis = null; 
	public static DataOutputStream dos = null;
	private ControlThread controlThread ;
	Handler mHandler;

	byte[] buf  	= new byte[1];
	public ControlHelper(String IP_ADDRESS)
	{
		this.IP_ADDRESS = IP_ADDRESS; 


	}


	public void startControlThread()
	{
		Log.d("TAG","������ ��ŸƮ �޼ҵ� ����");

		controlThread = new ControlThread();
		controlThread.start();
	}
	public void stopControlThread()
	{
		if(controlThread !=null)
		{
			controlThread.stop = true;
			controlThread = null;
		}
	}




	private class ControlThread extends Thread
	{
		private boolean stop = false; 
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				String name;
				client = new Socket(IP_ADDRESS,VP_PORT);
				Log.d("TAG", "���ϻ���");
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
			
				while(true)
			   {
				name =dis.readUTF();
				Log.d("TAG", "���浵 �޾Ҵ�: "+name);
		     	}


			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
