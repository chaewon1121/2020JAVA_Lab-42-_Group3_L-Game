import java.beans.DesignMode;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;
public class LgameServer {
	static int DesideOrder() {
		return (int)(new Random().nextInt())%2;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serversocket = null;
		Scanner sc= new Scanner(System.in);
		System.out.print("Input your nickname: ");
		String serverplayer=sc.next();         //������ ����ڿ� Ŭ���̾�Ʈ �� ����� �г���                        
		String clientplayer;
		 try {
			 //���� ���� ����
			 serversocket = new ServerSocket();

	         InetAddress inetAddress = InetAddress.getLocalHost();
	         String localhost = inetAddress.getHostAddress();
	 
	         serversocket.bind(new InetSocketAddress(localhost, 5000));
	 
	         System.out.println("[server] Binding " + localhost);
	         System.out.println("[server] Port: 5000");
	         System.out.println("[server] Your nickname: "+serverplayer);
	         System.out.println("Server is working...");
	         System.out.println("=====================================");
	         
	         
	         //Ŭ���̾�Ʈ ���� ���
	         Socket socket = serversocket.accept();
	 
	         OutputStream os = socket.getOutputStream();
	         DataOutputStream dous = new DataOutputStream(os);   //������ ������ ��Ʈ��
	         
	         InputStream ins = socket.getInputStream();
	         DataInputStream dins = new DataInputStream(ins);    //������ �о���� ��Ʈ��
	         clientplayer= dins.readUTF();                    // Ŭ���̾�Ʈ �г��� �о����
	         System.out.println("Client connected!");
	         System.out.println("Client ip: "+socket.getInetAddress());    //Ŭ���̾�Ʈ�� ip�ּ�   
	         System.out.println("Client nickname: "+clientplayer);
	         dous.writeUTF(serverplayer);   //���� �г��� Ŭ���̾�Ʈ�� ������
	         int order = DesideOrder();      //������ ���ϱ� 0,1 �� �������� ������
	         dous.writeInt(order);           //���� ����� Ŭ���̾�Ʈ�� ����(0�Ǵ� 1)
	         
	         if(order==0) {                                  //��������� 0�ָ� ���� ���� ����
	        	 System.out.println("You first!");
	         }
	         else {
	        	 System.out.println("You second");
	         }
	         
	         
	         /*
				 * ���⿡ GUI�� ���� ����
				 */
	         
	         
	         System.out.println("The end(�ϴ� �������)");  //�ϴ� �������
	         
	         dins.close();
	         dous.close();
	         socket.close();
	         serversocket.close();
	         
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 
		 
		 
		
		 
		 
	}

}

/*Room Ŭ���� 
 * �� ����ڰ� ���� �����ϰ� �ٸ� �� ����ڰ� ������ �÷��� �� �� ����
 * ���� ������ �����ϴ� Ŭ����
 * ��� ������ �� ���� ���� �����ؾ� �� �� �� ����
 * 1. �� ����� ��� : �� �̸��� ����� ����
 * 2. ������ ��� : �� �̸��� Ŭ���ϰ� ����� �Է� 
 *  
 */
class Room{
	String title; //�� ����
}