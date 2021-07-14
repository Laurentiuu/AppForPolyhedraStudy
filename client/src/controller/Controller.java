package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable, Observer {

    public String poliedru;
    public VBox mainLeft;
    public HBox mainTop;
    @FXML
    private Button romBtn;
    public Button engBtn;
    public Button frBtn;
    public Button gerBtn;

    Poliedru p = new Poliedru();

    //campuri din GUI
    @FXML
    public TextField tf1;
    public TextField tf2;
    public TextField tf3;
    public TextField tf4;
    public ColorPicker color;

    @FXML
    private BorderPane mainPane;

    //variabile convertite si luate din GUI
    double lungimeBaza = 0;
    double latimeBaza = 0;
    double apotema = 0;
    double inaltime = 0;

    //formele geometrice
    Group shape = new Group();
    Group shape1 = new Group();
    Group shape2 = new Group();

    Group arii = new Group();
    Group arii1 = new Group();
    Group arii2 = new Group();

    LoadShape layout = new LoadShape();
    Pane view = new Pane();

    Group text = new Group();

    public Group textOpen;
    public Group textTrunchi;
    public Group textTetraedru;
    public Group textPrisma;
    public Group textPiramida;
    public Group textParalelipiped;
    public Group textCub;

    Buton b = Buton.getInstance();
    DataOutputStream dout;
    DataInputStream din;
    BufferedReader br;
    ModelButon mb = new ModelButon();

    public Controller() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServerSocket ss = null;
        try {
            Socket s = new Socket("localhost", 6000);
            System.out.println("Clientul s-a conectat la server");
            dout = new DataOutputStream(s.getOutputStream());
            din=new DataInputStream(s.getInputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
            //dout.close();
            //s.close();

        } catch (IOException e) {
            System.out.println("Serverul nu este deschis");
            System.exit(0);
        }


        this.color.setValue(Color.BLACK);
        romBtn.setGraphic(layout.getImage("rom"));
        engBtn.setGraphic(layout.getImage("eng"));
        frBtn.setGraphic(layout.getImage("fr"));
        gerBtn.setGraphic(layout.getImage("ger"));
        this.poliedru = "Open";
    }

    public void btn1(ActionEvent actionEvent) throws IOException {
        this.view = this.layout.getShape("prisma");
        this.shape = (Group) view.getChildren().get(0);
        this.shape1 = (Group) view.getChildren().get(1);
        this.shape2 = (Group) view.getChildren().get(2);
        this.poliedru = "Prisma";

        this.arii = (Group) view.getChildren().get(3);
        this.arii1 = (Group) view.getChildren().get(4);
        this.arii2 = (Group) view.getChildren().get(5);
        mainPane.setCenter(view);

        Pane p = view;
        this.textPrisma = (Group) p.getChildren().get(6);
    }

    public void btn2(ActionEvent actionEvent) throws IOException {
        this.view = this.layout.getShape("paralelipipedDreptunghic");
        this.shape = (Group) view.getChildren().get(0);
        shape1 = null;
        shape2 = null;
        this.poliedru = "ParalelipipedDreptunghic";
        this.arii = (Group) view.getChildren().get(1);
        mainPane.setCenter(view);
        Pane p = view;
        this.textParalelipiped = (Group) p.getChildren().get(2);
    }

    public void btn3(ActionEvent actionEvent) {
        this.view = this.layout.getShape("cub");
        this.shape = (Group) view.getChildren().get(0);
        shape1 = null;
        shape2 = null;
        this.poliedru = "Cub";
        this.arii = (Group) view.getChildren().get(1);
        mainPane.setCenter(view);
        Pane p = view;
        this.textCub = (Group) p.getChildren().get(2);
    }

    public void btn4(ActionEvent actionEvent) {
        this.view = this.layout.getShape("piramidaPatrulatera");
        this.shape = (Group) view.getChildren().get(0);
        shape1 = null;
        shape2 = null;
        this.poliedru = "PiramidaPatrulatera";
        this.arii = (Group) view.getChildren().get(1);
        mainPane.setCenter(view);
        Pane p = view;
        this.textPiramida = (Group) p.getChildren().get(2);
    }

    public void btn5(ActionEvent actionEvent) {
        this.view = this.layout.getShape("tetraedru");
        this.shape = (Group) view.getChildren().get(0);
        shape1 = null;
        shape2 = null;
        this.poliedru = "Tetraedru";
        this.arii = (Group) view.getChildren().get(1);
        mainPane.setCenter(view);
        Pane p = view;
        this.textTetraedru = (Group) p.getChildren().get(2);
    }

    public void btn6(ActionEvent actionEvent) {
        this.view = this.layout.getShape("trunchiDePiramida");
        this.shape = (Group) view.getChildren().get(0);
        shape1 = null;
        shape2 = null;
        this.poliedru = "TrunchiDePiramida";
        this.arii = (Group) view.getChildren().get(1);
        mainPane.setCenter(view);
        Pane p = view;
        this.textTrunchi = (Group) p.getChildren().get(2);
    }

    public void calcBtn(ActionEvent actionEvent) throws IOException {
        if (this.poliedru == null) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Nu s-a selectat poliedrul.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (this.poliedru == "Open") {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Nu s-a selectat poliedrul.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (!tf1.getText().isEmpty() && !tf2.getText().isEmpty() && !tf3.getText().isEmpty() && !tf4.getText().isEmpty()) {
            String dateDeTrimis = tf1.getText() + " "+ tf2.getText() + " "+ tf3.getText() + " "+ tf4.getText();
            dout.writeUTF(this.poliedru + " "+dateDeTrimis);
        } else {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Nu s-au introdus date.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        String s = din.readUTF();
        System.out.println("Datele primite sunt: " + s);
        String datePrimite[] = s.split(" ");
        if (this.poliedru.equals("Prisma")) {
            Label tf = (Label) arii.getChildren().get(0);
            tf.setText("Al = " + datePrimite[0] + "cm");
            tf = (Label) arii.getChildren().get(1);
            tf.setText("Ab = " + datePrimite[1] + "cm");
            tf = (Label) arii.getChildren().get(2);
            tf.setText("At = " + datePrimite[2] + "cm");
            tf = (Label) arii.getChildren().get(3);
            tf.setText("V= " + datePrimite[3] + "cm");

            tf = (Label) arii1.getChildren().get(0);
            tf.setText("Al = " + datePrimite[4] + "cm");
            tf = (Label) arii1.getChildren().get(1);
            tf.setText("Ab = " + datePrimite[5] + "cm");
            tf = (Label) arii1.getChildren().get(2);
            tf.setText("At = " + datePrimite[6] + "cm");
            tf = (Label) arii1.getChildren().get(3);
            tf.setText("V = " + datePrimite[7] + "cm");

            tf = (Label) arii2.getChildren().get(0);
            tf.setText("Al = " + datePrimite[8] + "cm");
            tf = (Label) arii2.getChildren().get(1);
            tf.setText("Ab = " + datePrimite[9] + "cm");
            tf = (Label) arii2.getChildren().get(2);
            tf.setText("At = " + datePrimite[10] + "cm");
            tf = (Label) arii2.getChildren().get(3);
            tf.setText("V = " + datePrimite[11] + "cm");
        } else {
            Label tf = (Label) arii.getChildren().get(0);
            tf.setText("Al = " + datePrimite[0] + "cm");
            tf = (Label) arii.getChildren().get(1);
            tf.setText("Ab = " + datePrimite[1] + "cm");
            tf = (Label) arii.getChildren().get(2);
            tf.setText("At = " + datePrimite[2] + "cm");
            tf = (Label) arii.getChildren().get(3);
            tf.setText("V = " + datePrimite[3] + "cm");
        }

    }

    public void colorBtn(ActionEvent actionEvent) {
        if (this.shape.getChildren().size() != 0) {
            this.shape = (Group) view.getChildren().get(0);
            for (int i = 0; i < shape.getChildren().size(); i++) {
                if (shape.getChildren().get(i) instanceof Line) {
                    Line l = (Line) shape.getChildren().get(i);
                    l.setStroke(Paint.valueOf(color.getValue().toString()));
                }
            }
            if (shape1 != null && shape2 != null) {
                this.shape1 = (Group) view.getChildren().get(1);
                for (int i = 0; i < shape1.getChildren().size(); i++) {
                    if (shape1.getChildren().get(i) instanceof Line) {
                        Line l = (Line) shape1.getChildren().get(i);
                        l.setStroke(Paint.valueOf(color.getValue().toString()));
                    }
                }
                this.shape2 = (Group) view.getChildren().get(2);
                for (int i = 0; i < shape2.getChildren().size(); i++) {
                    if (shape2.getChildren().get(i) instanceof Line) {
                        Line l = (Line) shape2.getChildren().get(i);
                        l.setStroke(Paint.valueOf(color.getValue().toString()));
                    }
                }
            }
        }
    }

    public void simetrice(ActionEvent actionEvent) throws IOException {
        if (this.poliedru.equals("Prisma")) {
            JPanel panel = new JPanel();

            BufferedImage image = ImageIO.read(new File("D:/1facultatie/AN3/sem2/PS/tema2/out/production/tema2/view/simetrice/prisma.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

            JFrame frame = new JFrame("Simetrica prisma");

            frame.add(panel);

            frame.pack();
            frame.setVisible(true);
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            JPanel panel = new JPanel();

            BufferedImage image = ImageIO.read(new File("D:/1facultatie/AN3/sem2/PS/tema2/out/production/tema2/view/simetrice/paralelipiped.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

            JFrame frame = new JFrame("Simetrica paralelipiped");

            frame.add(panel);

            frame.pack();
            frame.setVisible(true);
        } else if (this.poliedru.equals("Cub")) {
            JPanel panel = new JPanel();

            BufferedImage image = ImageIO.read(new File("D:/1facultatie/AN3/sem2/PS/tema2/out/production/tema2/view/simetrice/cub.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

            JFrame frame = new JFrame("Simetrica cub");

            frame.add(panel);

            frame.pack();
            frame.setVisible(true);
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            JPanel panel = new JPanel();

            BufferedImage image = ImageIO.read(new File("D:/1facultatie/AN3/sem2/PS/tema2/out/production/tema2/view/simetrice/piramida.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

            JFrame frame = new JFrame("Simetrica piramida");

            frame.add(panel);

            frame.pack();
            frame.setVisible(true);
        } else if (this.poliedru.equals("Tetraedru")) {
            JPanel panel = new JPanel();

            BufferedImage image = ImageIO.read(new File("D:/1facultatie/AN3/sem2/PS/tema2/out/production/tema2/view/simetrice/tetraedru.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

            JFrame frame = new JFrame("Simetrica tetraedru");

            frame.add(panel);

            frame.pack();
            frame.setVisible(true);
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            JPanel panel = new JPanel();

            BufferedImage image = ImageIO.read(new File("D:/1facultatie/AN3/sem2/PS/tema2/out/production/tema2/view/simetrice/trunchipiramida.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

            JFrame frame = new JFrame("Simetrica trunchipiramida");

            frame.add(panel);

            frame.pack();
            frame.setVisible(true);
        }
    }

    //butoanele care schimba textul in limbi diferite
    public void romana(ActionEvent actionEvent) {

        Label tf;
        Button b;
        tf = (Label) mainLeft.getChildren().get(0);
        tf.setText("Lungimea bazei");
        tf = (Label) mainLeft.getChildren().get(2);
        tf.setText("Lățimea bazei și baza superioară(la trunchiul de piramidă)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("Înălțimea");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Lungimea bazei");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Calculează");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("Generare simetrice");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Prismă");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Paralelipiped dreptunghic");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("Cub");
        b = (Button) mainTop.getChildren().get(3);
        b.setText("Piramidă");
        b = (Button) mainTop.getChildren().get(4);
        b.setText("Tetraedru");
        b = (Button) mainTop.getChildren().get(5);
        b.setText("Trunchi de piramidă");
        HBox h = (HBox) mainLeft.getChildren().get(10);
        b = (Button) h.getChildren().get(1);
        b.setText("Colorează");
        if (this.poliedru.equals("Open")) {
            tf = (Label) this.textOpen.getChildren().get(0);
            tf.setText("Poliedre regulate");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("Definiție: Un poliedru convex P se numește poliedru regulat dacă fiecare vârf");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("aparține aceluiași număr de muchii, toate fețele sunt suprafețe poligonale regulate");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("congruente și toate unghiurile diedre determinate de fețe cu muchie comună");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("sunt congruente.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Relația lui Euler: Dacă v,m,f reprezintă respectiv numărul vârfurilor, muchiilor");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("și fețelor unui  poliedru convex, atunci:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- Prisma dreaptă care are baza un poligon regulat se numeste PRISMĂ REGULATĂ.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- Înălțimea(h) a unei prisme drepte este egală cu lungimea muchiei laterale.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) este suma ariilor fețelor laterale iar At (At) este suma dintre Al  și ariile celor două baze.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- V ul(V) unei prisme este egal cu produsul dintre Ab  și înălțime.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- O prismă care are muchiile laterale perpendiculare pe planele bazelor se numește PRISMĂ DREAPTĂ.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Prismă regulata triunghiulară");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Prismă regulata patrulateră");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Prismă regulata hexagonală");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- Diagonala paralelipipedului dreptunghic este segmentul care unește două vârfuri ce nu aparțin aceleiași fețe. Sunt patru ");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("astfel de drepte, concurente într-un punct denumit centrul paralelipipedului dreptunghic.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- Lungimea diagonalei este: ");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- Volumul(V) unei prisme este egal cu produsul dintre aria bazei și înălțime.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- În geometrie, un paralelipiped dreptunghic este un poliedru convex, cu șase fețe dreptunghiulare.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Paralelipiped dreptunghic");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- Cubul este paralelipipedul dreptunghic cu toate muchiile egale.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- Fețele unui cub au formă de pătrat și sunt congruente, iar aria oricărei fețe este egală cu pătratul laturii; figura are");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("șase fețe congruente, deci aria totală este 6 ori pătratul laturii. ");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- Un cub este un corp geometric (tridimensional) care are din punct de vedere constructiv: 6 fețe, 12 muchii, 8 vărfuri.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- Cubul sau hexaedrul este un poliedru limitat de șase fețe de formă pătrată.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- Piramidă patrulateră este o piramidă care are ca bază un patrulater.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- Piramida patrulateră are: 8 muchii (4 laterale și 4 ale bazei) și 5 fețe (4 laterale și baza) și 5 vârfuri.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- Volumul piramidei este o treime din volumul cubului cu latură de lungime egală cu muchia piramidei.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- Piramidă este un poliedru format prin conectarea unei suprafețe poligonale (bază) cu un punct (vârf).");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Piramida");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- Tetraedrul este cel mai simplu tip de piramidă, la care baza este triunghi, de aceea mai este denumit și piramidă ");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- Piramida patrulateră are: 4 vârfuri, 6 muchii și 4 fețe.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Tetraedrul este un poliedru alcătuit din patru fețe triunghiulare, oricare trei dintre ele intersectându-se într-unul ");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("Tetraedru regulat");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- Muchiile bazei nu sunt congruente cu muchiile laterale.");
            tf = (Label) this.textTetraedru.getChildren().get(5);
            tf.setText("din cele patru vârfuri.");
            tf = (Label) this.textTetraedru.getChildren().get(6);
            tf.setText("triunghiulară.");
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            tf = (Label) this.textTrunchi.getChildren().get(0);
            tf.setText("situat sub planul de sectiune.");
            tf = (Label) this.textTrunchi.getChildren().get(1);
            tf.setText("- Trunchiul de piramidă are fața laterală un trapez isoscel ortodiagonal");
            tf = (Label) this.textTrunchi.getChildren().get(2);
            tf.setText("- Baza superioară și baza inferioară sunt pătrate.");
            tf = (Label) this.textTrunchi.getChildren().get(3);
            tf.setText("- Trunchiul de piramidă este corpul geometric obținut prin secționarea unei piramide printr-un plan paralel cu baza,");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Trunchi de piramidă");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- Trunchiul de piramidă are: 8 vârfuri, 6 fețe și 12 muchii. ");
        }
    }

    public void engleza(ActionEvent actionEvent) {

        Label tf;
        Button b;
        tf = (Label) mainLeft.getChildren().get(0);
        tf.setText("Base length");
        tf = (Label) mainLeft.getChildren().get(2);
        tf.setText("Base width and top base (at the trunk of the pyramid)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("Height");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Lungimea bazei");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Calculate");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("Symmetrical generation");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Prism");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Rectangular parallelepiped");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("Cube");
        b = (Button) mainTop.getChildren().get(3);
        b.setText("Pyramid");
        b = (Button) mainTop.getChildren().get(4);
        b.setText("Tetrahedron");
        b = (Button) mainTop.getChildren().get(5);
        b.setText("Pyramid trunk");
        HBox h = (HBox) mainLeft.getChildren().get(10);
        b = (Button) h.getChildren().get(1);
        b.setText("Color");
        if (this.poliedru.equals("Open")) {
            tf = (Label) this.textOpen.getChildren().get(0);
            tf.setText("Regular polyhedra");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("Definition: A convex polyhedron P is called a regular polyhedron if each vertex");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("belongs to the same number of edges, all faces are regular polygonal surfaces");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("congruent and all dihedral angles determined by faces with common edge");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("are congruent.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Euler's relation: If v, m, f represent respectively the number of vertices, edges");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("și fețelor unui  poliedru convex, atunci:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- The straight prism that has the base of a regular polygon is called REGULAR PRISM.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- The height (h) of a straight prism is equal to the length of the side edge.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) is the sum of the areas of the side faces and At (At) is the sum of Al and the areas of the two bases.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- The V (V) of a prism is equal to the product of Ab and height.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- A prism that has side edges perpendicular to the planes of the bases is called a RIGHT PRISM.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Regular triangular prism");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Regular quadrilateral prism");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Regular hexagonal prism");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- The diagonal of the rectangular parallelepiped is the segment that joins two vertices that do not belong to the same face. There are four");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("such lines, competing at a point called the center of the rectangular parallelepiped.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- The length of the diagonal is: ");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- The volume (V) of a prism is equal to the product between the base area and the height.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- In geometry, a rectangular parallelepiped is a convex polyhedron with six rectangular faces.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Rectangular parallelepiped");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- The cube is the rectangular parallelepiped with all edges equal.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- The faces of a cube are square and congruent, and the area of any face is equal to the square of the side; the figure has");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("six congruent faces, so the total area is 6 times the square of the side. ");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- A cube is a geometric (three-dimensional) body that has a constructive point of view: 6 faces, 12 edges, 8 tips.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- The cube or hexahedron is a polyhedron bounded by six square faces.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- A quadrilateral pyramid is a pyramid based on a quadrilateral.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- The quadrilateral pyramid has: 8 edges (4 lateral and 4 of the base) and 5 faces (4 lateral and the base) and 5 vertices.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- The volume of the pyramid is one third of the volume of the cube with a side length equal to the edge of the pyramid.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- Pyramid is a polyhedron formed by connecting a polygonal surface (base) with a point (vertex).");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Pyramid");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- The tetrahedron is the simplest type of pyramid, at which the base is a triangle, so it is also called a triangular ");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- The quadrilateral pyramid has: 4 vertices, 6 edges and 4 faces.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Tetrahedron is a polyhedron composed of four triangular faces, any three of them are intersecting in one ");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("Regular tetrahedron");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- The edges of the base are not congruent with the side edges.");
            tf = (Label) this.textTetraedru.getChildren().get(5);
            tf.setText("from the four peaks.");
            tf = (Label) this.textTetraedru.getChildren().get(6);
            tf.setText("pyramid");
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            tf = (Label) this.textTrunchi.getChildren().get(0);
            tf.setText("located below the section plan.");
            tf = (Label) this.textTrunchi.getChildren().get(1);
            tf.setText("- The pyramid trunk has an orthodiagonal isosceles trapezoid on the side");
            tf = (Label) this.textTrunchi.getChildren().get(2);
            tf.setText("- The upper base and the lower base are square.");
            tf = (Label) this.textTrunchi.getChildren().get(3);
            tf.setText("- The trunk of a pyramid is the geometric body obtained by sectioning a pyramid through a plane parallel to the base,");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Pyramid trunk");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- The trunk of the pyramid has: 8 tips, 6 faces and 12 edges.");
        }
    }

    public void franceza(ActionEvent actionEvent) {
        Label tf;
        Button b;
        tf = (Label) mainLeft.getChildren().get(0);
        tf.setText("Longueur de la base");
        tf = (Label) mainLeft.getChildren().get(2);
        tf.setText("Largeur de la base et base supérieure (au tronc de la pyramide)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("Hauteur");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Longueur de la base");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Calculé");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("Génération symétrique");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Prism");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Parallélépipède rectangulaire");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("Cube");
        b = (Button) mainTop.getChildren().get(3);
        b.setText("Pyramide");
        b = (Button) mainTop.getChildren().get(4);
        b.setText("Tétraèdre");
        b = (Button) mainTop.getChildren().get(5);
        b.setText("Tronc de pyramide");
        HBox h = (HBox) mainLeft.getChildren().get(10);
        b = (Button) h.getChildren().get(1);
        b.setText("Couleur");
        if (this.poliedru.equals("Open")) {
            tf = (Label) this.textOpen.getChildren().get(0);
            tf.setText("Polyèdres réguliers");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("Définition: Un polyèdre convexe P est appelé un polyèdre régulier si chaque sommet");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("appartient au même nombre d'arêtes, toutes les faces sont des surfaces polygonales régulières");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("congruents et tous les angles dièdres déterminés par des faces avec une arête commune");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("sont congruents.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Relation d'Euler: Si v, m, f représentent respectivement le nombre de sommets, d'arêtes");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("et les faces d'un polyèdre convexe, alors:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- Le prisme droit qui a la base d'un polygone régulier est appelé PRISME RÉGULIER.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- La hauteur (h) d'un prisme droit est égale à la longueur du bord latéral.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) est la somme des aires des faces latérales et At (At) est la somme de Al et des aires des deux bases.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- Le V (V) d'un prisme est égal au produit de Ab et de la hauteur.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- Un prisme qui a des bords latéraux perpendiculaires aux plans des bases est appelé PRISME DROIT.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Prisme triangulaire régulier");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Prisme quadrilatéral régulier");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Prisme hexagonal régulier");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- La diagonale du parallélépipède rectangulaire est le segment qui joint deux sommets n'appartenant pas à la même face. Ils sont quatre");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("de telles lignes, en concurrence en un point appelé le centre du parallélépipède rectangulaire.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- La longueur de la diagonale est:");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- Le volume (V) d'un prisme est égal au produit entre la surface de base et la hauteur.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- En géométrie, un parallélépipède rectangulaire est un polyèdre convexe à six faces rectangulaires.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Parallélépipède rectangulaire");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- Le cube est le parallélépipède rectangulaire avec toutes les arêtes égales.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- Les faces d'un cube sont carrées et congruentes, et l'aire de n'importe quelle face est égale au carré du côté; la figure a");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("six faces congruentes, donc la surface totale est de 6 fois le carré du côté.");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- Un cube est un corps géométrique (tridimensionnel) qui a un point de vue constructif: 6 faces, 12 arêtes, 8 pointes.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- Le cube ou hexaèdre est un polyèdre délimité par six faces carrées.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- Une pyramide quadrilatérale est une pyramide basée sur un quadrilatère.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- La pyramide quadrilatérale comporte: 8 arêtes (4 latérales et 4 de la base) et 5 faces (4 latérales et la base) et 5 sommets.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- Le volume de la pyramide est un tiers du volume du cube avec une longueur de côté égale au bord de la pyramide.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- La pyramide est un polyèdre formé en reliant une surface polygonale (base) avec un point (pic).");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Pyramide");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- Le tétraèdre est le type de pyramide le plus simple, dont la base est un triangle, on l'appelle donc aussi une pyramide");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- La pyramide quadrilatérale a: 4 sommets, 6 arêtes et 4 faces.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Le tétraèdre est un polyèdre composé de quatre faces triangulaires, dont trois se coupent en une");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("Tétraèdre régulier");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- Les bords de la base ne sont pas congruents avec les bords latéraux.");
            tf = (Label) this.textTetraedru.getChildren().get(5);
            tf.setText("des quatre sommets.");
            tf = (Label) this.textTetraedru.getChildren().get(6);
            tf.setText("triangulaire.");
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            tf = (Label) this.textTrunchi.getChildren().get(0);
            tf.setText("situé sous le plan de coupe.");
            tf = (Label) this.textTrunchi.getChildren().get(1);
            tf.setText("- Le tronc de la pyramide a un trapèze isocèle orthodiagonal sur le côté");
            tf = (Label) this.textTrunchi.getChildren().get(2);
            tf.setText("- La base supérieure et la base inférieure sont carrées.");
            tf = (Label) this.textTrunchi.getChildren().get(3);
            tf.setText("- Le tronc d'une pyramide est le corps géométrique obtenu en sectionnant une pyramide dans un plan parallèle à la base,");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Tronc de pyramide");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- Le tronc de la pyramide a: 8 pointes, 6 faces et 12 arêtes.");
        }
    }

    public void germana(ActionEvent actionEvent) {

        Label tf;
        Button b;
        tf = (Label) mainLeft.getChildren().get(0);
        tf.setText("Grundlänge");
        tf = (Label) mainLeft.getChildren().get(2);
        tf.setText("Basisbreite und obere Basis (am Stamm der Pyramide)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("Höhe");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Basislänge");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Berechnet");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("Symmetrische Erzeugung");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Winkel");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Rechteckiges Parallelepiped");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("Würfel");
        b = (Button) mainTop.getChildren().get(3);
        b.setText("Pyramide");
        b = (Button) mainTop.getChildren().get(4);
        b.setText("Tetraeder");
        b = (Button) mainTop.getChildren().get(5);
        b.setText("Pyramidenstamm");
        HBox h = (HBox) mainLeft.getChildren().get(10);
        b = (Button) h.getChildren().get(1);
        b.setText("Farbe");
        if (this.poliedru.equals("Open")) {
            tf = (Label) this.textOpen.getChildren().get(0);
            tf.setText("Regelmäßige Polyeder");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("Definition: Ein konvexes Polyeder P wird bei jedem Scheitelpunkt als reguläres Polyeder bezeichnet");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("gehört zur gleichen Anzahl von Kanten, alle Flächen sind regelmäßige polygonale Flächen");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("kongruente und alle Diederwinkel, die durch Flächen mit gemeinsamer Kante bestimmt werden");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("sind kongruent.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Eulers Beziehung: Wenn v, m, f jeweils die Anzahl der Eckpunkte, Kanten darstellen");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("und die Gesichter eines konvexen Polyeders, dann:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- Das gerade Prisma, das die Basis eines regulären Polygons hat, heißt REGELMÄSSIGES PRISMUS.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- Die Höhe (h) eines geraden Prismas entspricht der Länge der Seitenkante.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) ist die Summe der Flächen der Seitenflächen und At (At) ist die Summe von Al und den Flächen der beiden Basen.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- Das V (V) eines Prismas ist gleich dem Produkt aus Ab und Höhe.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- Ein Prisma mit seitlichen Kanten senkrecht zu den Ebenen der Basen wird als GERADES PRISMUS bezeichnet.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Regelmäßiges Dreiecksprisma");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Regelmäßiges viereckiges Prisma");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Regelmäßiges sechseckiges Prisma");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- Die Diagonale des rechteckigen Parallelepipeds ist das Segment, das zwei Eckpunkte verbindet, die nicht zur selben Fläche gehören. Es gibt vier");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("solche Linien, die an einem Punkt konkurrieren, der als Zentrum des rechteckigen Parallelepipeds bezeichnet wird.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- Die Länge der Diagonale beträgt:");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- Das Volumen (V) eines Prismas entspricht dem Produkt zwischen Grundfläche und Höhe.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- In der Geometrie ist ein rechteckiges Parallelepiped ein konvexes Polyeder mit sechs rechteckigen Flächen.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Rechteckiges Parallelepiped");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- Der Würfel ist das rechteckige Parallelepiped mit allen Kanten gleich.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- Die Flächen eines Würfels sind quadratisch und kongruent, und die Fläche einer Fläche entspricht dem Quadrat der Seite. Die Figur hat");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("sechs kongruente Flächen, so dass die Gesamtfläche das 6-fache des Quadrats der Seite beträgt.");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- Ein Würfel ist ein geometrischer (dreidimensionaler) Körper mit einer konstruktiven Sichtweise: 6 Flächen, 12 Kanten, 8 Spitzen.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- Der Würfel oder das Hexaeder ist ein Polyeder, das von sechs quadratischen Flächen begrenzt wird.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- Eine viereckige Pyramide ist eine Pyramide, die auf einem Viereck basiert.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- Die viereckige Pyramide hat: 8 Kanten (4 seitliche und 4 der Basis) und 5 Flächen (4 seitliche und die Basis) und 5 Eckpunkte.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- Das Volumen der Pyramide beträgt ein Drittel des Volumens des Würfels mit einer Seitenlänge, die der Kante der Pyramide entspricht.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- Die Pyramide ist ein Polyeder, das durch Verbinden einer polygonalen Oberfläche (Basis) mit einem Punkt (Peak) gebildet wird.");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Pyramide");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- Der Tetraeder ist der einfachste Pyramidentyp, bei dem die Basis ein Dreieck ist, daher wird er auch als Pyramide bezeichnet");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- Die viereckige Pyramide hat: 4 Eckpunkte, 6 Kanten und 4 Flächen.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Der Tetraeder ist ein Polyeder, das aus vier dreieckigen Flächen besteht, von denen sich drei in einer schneiden");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("Normaler Tetraeder");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- Die Kanten der Basis stimmen nicht mit den Seitenkanten überein.");
            tf = (Label) this.textTetraedru.getChildren().get(5);
            tf.setText("von den vier Gipfeln.");
            tf = (Label) this.textTetraedru.getChildren().get(6);
            tf.setText("dreieckig.");
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            tf = (Label) this.textTrunchi.getChildren().get(0);
            tf.setText("befindet sich unter dem Schnittplan.");
            tf = (Label) this.textTrunchi.getChildren().get(1);
            tf.setText("- Der Pyramidenstamm hat an der Seite ein orthodiagonales gleichschenkliges Trapez");
            tf = (Label) this.textTrunchi.getChildren().get(2);
            tf.setText("- Die obere Basis und die untere Basis sind quadratisch.");
            tf = (Label) this.textTrunchi.getChildren().get(3);
            tf.setText("- Der Stamm einer Pyramide ist der geometrische Körper, der durch Schneiden einer Pyramide durch eine Ebene parallel zur Basis erhalten wird.");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Pyramidenstamm");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- Der Stamm der Pyramide hat: 8 Spitzen, 6 Flächen und 12 Kanten.");
        }
    }

    @Override
    public void actualizare() {
        if(b.getB().equals("romana"))
            this.mb.setButon("romana");
        else if(b.getB().equals("engleza"))
            this.mb.setButon("engleza");
        else if(b.getB().equals("franceza"))
            this.mb.setButon("franceza");
        else if(b.getB().equals("germana"))
            this.mb.setButon("germana");
    }
}
