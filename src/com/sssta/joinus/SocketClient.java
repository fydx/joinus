package com.sssta.joinus;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Environment;
import android.util.Log;

import com.sssta.model.ConstValue;

public class SocketClient extends Thread implements Serializable {
	enum OperateType {
		SendImage, SendCmd, DownloadImage
	}

	static private String host = "192.168.1.135";

	static private int port = 3001;
	static private Socket socket;

	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	private byte[] outbuffer = new byte[ConstValue.BufferSIze];
	private byte[] inbuffer = new byte[ConstValue.BufferSIze];
	private String inbufferString = "";
	private String cmdString;
	private String returnString;

	private int ImageId;
	private String imagePathString;

	private OperateType type = OperateType.SendCmd;

	public static void Close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// id为活动id
	static public String DownloadImage(int id) {

		try {
			SocketClient client = new SocketClient();
			client.type = OperateType.DownloadImage;
			client.ImageId = id;
			client.start();
			client.join();
			return client.imagePathString;
		} catch (Exception e) {
			return null;
		}

	}

	// id为活动id
	static public boolean UploadImage(int id, String ImagePath) {
		try {
			SocketClient client = new SocketClient();
			client.type = OperateType.SendImage;
			client.ImageId = id;
			client.imagePathString = ImagePath;
			client.start();
			//while(client.isAlive());
			client.join();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	static public String Execute(String cmd) {
		SocketClient client = new SocketClient();
		client.type = OperateType.SendCmd;
		client.cmdString = cmd;
		client.start();
		try {
			client.join();
			//sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client.returnString;
	}

	public void run() {
		if (socket == null || socket.isClosed()) {
			try {
				/*
				 * File file=new File("setting.ini"); FileInputStream fis=new
				 * FileInputStream(file); byte[] inputbytes=new byte [1024];
				 * fis.read(inputbytes); String inputstr=new String(inputbytes);
				 * String[] inputstrings=inputstr.split(" ");
				 * host=inputstrings[0]; port=Integer.parseInt(inputstrings[1]);
				 */
				socket = new Socket(host, port);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		int y;
		switch (type) {
		case SendCmd:
			SendCmd();
			break;
		case SendImage:
			SendImage();
			break;
		case DownloadImage:
			ReceiveImage();
			break;
		}
		/*
		 * try { socket.close(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	private void SendCmd() {
		try {
			cmdString += ";";
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			outbuffer = cmdString.getBytes("utf-8");

			outputStream.write(outbuffer);
			// outputStream.close();


			if (ConstValue.IsQuery(cmdString)) {
				while (true) {
					inbuffer=new byte[1024];
					int read = inputStream.read(inbuffer);
					String tempString = new String(inbuffer, "utf-8");
					inbufferString += tempString;
					
					inbufferString=inbufferString.trim();
					int end=inbufferString.length();
					String judge=inbufferString.substring(end-ConstValue.EndString.length() ,end );

					if (judge.compareTo(ConstValue.EndString)==0) 
					/*if(inbufferString.contains(ConstValue.EndString))*/{
						int index = end-ConstValue.EndString.length();
						inbufferString = inbufferString.substring(0, index);
						// inputStream.close();
						break;
					}
				}
			} else {
				boolean test=ReceriveSure();
				inbufferString = null;
				Log.i("test", String.valueOf(test));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			this.returnString = inbufferString;

		}
	}

	private void SendImage() {
		try {
			File file = new File(imagePathString);
			FileInputStream fis=new FileInputStream(file);
			
			outputStream = socket.getOutputStream();
			String filelength=""+file.length();
			String outString=ImageId+" "+filelength+" "+"upload;";
			outbuffer = outString.getBytes("utf-8");
			outputStream.write(outbuffer);
			
			boolean test=ReceriveSure();
			Log.i("test", String.valueOf(test));

			byte[] buf = new byte[ConstValue.BufferSIze];
			int read;
			while ((read = fis.read(buf, 0, ConstValue.BufferSIze))!=-1) {
				outputStream.write(buf, 0, read);
			}
			fis.close();
			
			ReceriveSure();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
		}
	}

	private void ReceiveImage() {
		try {
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();

			String outString = ImageId + " download;";
			outbuffer = outString.getBytes("utf-8");
			outputStream.write(outbuffer);
			
			boolean test=ReceriveSure();
			Log.i("test", String.valueOf(test));
			
			imagePathString = "/"+ImageId + ".jpg";

			File file = new File(Environment.getExternalStorageDirectory()+imagePathString);
			
			boolean canwrite=file.canWrite();
			String pathString=file.getAbsolutePath();
			imagePathString=pathString;
			
			/*DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));*/
			DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			
			int read;
			
			byte[] lengthbytes=new byte[ConstValue.BufferSIze];
			inputStream.read(lengthbytes);
			String filelenthString=new String(lengthbytes);
			filelenthString=filelenthString.trim();
			int length=Integer.parseInt(filelenthString);
			
			SendSure();
			
			int sum=0;
			while (true) {
				read = inputStream.read(inbuffer,0,ConstValue.BufferSIze);
				sum+=read;
				dos.write(inbuffer,0,read);
				if (sum== length )
					{
					//socket.close();
					break;
					}
			}
			dos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
		}

	}
	
	private Boolean ReceriveSure()
	{
		
		try {
			
			inputStream=socket.getInputStream();
			byte buf[]=new byte[ConstValue.BufferSIze];
			inputStream.read(buf, 0 ,ConstValue.Suresize );
			for(int i=0;i<ConstValue.Suresize;i++)
			{
				if(buf[i]!=ConstValue.Surebytes[i])return false;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private void SendSure()
	{
		try {
			outputStream=socket.getOutputStream();
			outputStream.write(ConstValue.Surebytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isconnected()
	{
		try {
			socket.sendUrgentData(0xFF);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}
