import java.io.*;
import java.net.*;
import java.util.Scanner;
public class LgameClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc= new Scanner(System.in);
		String clientplayer, serverplayer, serveraddress;
		System.out.print("Input server address(192.168.56.1/5000): ");   //���� �ּҿ� ��Ʈ �Է�
		serveraddress = sc.next();
		String serverinfo[] = serveraddress.split("/");                // /�������� ������ ����
		int serverport = Integer.parseInt(serverinfo[1]);
		try {
			Socket soc = new Socket(serverinfo[0], serverport);     //ip�ּҿ� ��Ʈ��ȣ�� ����
			DataOutputStream dous = new DataOutputStream (soc.getOutputStream());  //������ ������ ��Ʈ��
			DataInputStream dins = new DataInputStream(soc.getInputStream());      //������ �о���� ��Ʈ��
		
			System.out.println("Connected to server!: "+serveraddress);   //����Ǹ� �����ּ� ���
			
			System.out.print("Input yout nickname: ");             //Ŭ���̾�Ʈ �г��� �Է�
			clientplayer = sc.next();
			dous.writeUTF(clientplayer);
			System.out.println("[client] Your ip: "+ soc.getLocalAddress());    //Ŭ���̾�Ʈ ip�����ֱ�(�׳�)
			System.out.println("[client] Your nickname: "+clientplayer);        //�̸��� ���� �����ֱ�
			
			
			serverplayer = dins.readUTF();                                      //������ �г��� ���� �� ����
			System.out.println("[server] Server nickname: "+serverplayer);
			
			int order= dins.readInt();            //���� ����� �о�帲(0�Ǵ� 1)
			if(order==1) {                              //1�̸� Ŭ���̾�Ʈ���� ����
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
			soc.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
