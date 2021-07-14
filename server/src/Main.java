
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String args[]) throws IOException {
        ServerSocket ss = new ServerSocket(6000);
        System.out.println("S-a deschis serverul");
        //while infinit pentru neinchiderea serverului sa se poata urmarii mereu ce client si server se conecteaza
        while (true) {
            try {
                //activez conexiunea
                Socket s = ss.accept();
                System.out.println("Client conectat");
                DataInputStream dIn = new DataInputStream(s.getInputStream());
                DataOutputStream doOut = new DataOutputStream(s.getOutputStream());
                //in acest while primesc si trimit datele intre client si server
                //daca dout si din este intrerupt(clientul se inchide) se iese din while
                while (true) {
                    String datePrimite = (String) dIn.readUTF();
                    System.out.println("Datele primite sunt: " + datePrimite);
                    Operatii operatii = new Operatii(datePrimite);
                    String dateTrimise = operatii.calcArieLaterala() + " " + operatii.calcArieBaza() + " "
                            + operatii.calcArieTotala() + " "+ operatii.calcVolum();
                    doOut.writeUTF(dateTrimise);
                }
            } catch (IOException e) {
                System.out.println("Client deconectat");
            }
        }
    }

}
