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
        tf.setText("L????imea bazei ??i baza superioar??(la trunchiul de piramid??)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("??n??l??imea");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Lungimea bazei");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Calculeaz??");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("Generare simetrice");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Prism??");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Paralelipiped dreptunghic");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("Cub");
        b = (Button) mainTop.getChildren().get(3);
        b.setText("Piramid??");
        b = (Button) mainTop.getChildren().get(4);
        b.setText("Tetraedru");
        b = (Button) mainTop.getChildren().get(5);
        b.setText("Trunchi de piramid??");
        HBox h = (HBox) mainLeft.getChildren().get(10);
        b = (Button) h.getChildren().get(1);
        b.setText("Coloreaz??");
        if (this.poliedru.equals("Open")) {
            tf = (Label) this.textOpen.getChildren().get(0);
            tf.setText("Poliedre regulate");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("Defini??ie: Un poliedru convex P se nume??te poliedru regulat dac?? fiecare v??rf");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("apar??ine aceluia??i num??r de muchii, toate fe??ele sunt suprafe??e poligonale regulate");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("congruente ??i toate unghiurile diedre determinate de fe??e cu muchie comun??");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("sunt congruente.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Rela??ia lui Euler: Dac?? v,m,f reprezint?? respectiv num??rul v??rfurilor, muchiilor");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("??i fe??elor unui  poliedru convex, atunci:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- Prisma dreapt?? care are baza un poligon regulat se numeste PRISM?? REGULAT??.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- ??n??l??imea(h) a unei prisme drepte este egal?? cu lungimea muchiei laterale.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) este suma ariilor fe??elor laterale iar At (At) este suma dintre Al  ??i ariile celor dou?? baze.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- V ul(V) unei prisme este egal cu produsul dintre Ab  ??i ??n??l??ime.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- O prism?? care are muchiile laterale perpendiculare pe planele bazelor se nume??te PRISM?? DREAPT??.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Prism?? regulata triunghiular??");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Prism?? regulata patrulater??");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Prism?? regulata hexagonal??");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- Diagonala paralelipipedului dreptunghic este segmentul care une??te dou?? v??rfuri ce nu apar??in aceleia??i fe??e. Sunt patru ");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("astfel de drepte, concurente ??ntr-un punct denumit centrul paralelipipedului dreptunghic.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- Lungimea diagonalei este: ");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- Volumul(V) unei prisme este egal cu produsul dintre aria bazei ??i ??n??l??ime.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- ??n geometrie, un paralelipiped dreptunghic este un poliedru convex, cu ??ase fe??e dreptunghiulare.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Paralelipiped dreptunghic");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- Cubul este paralelipipedul dreptunghic cu toate muchiile egale.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- Fe??ele unui cub au form?? de p??trat ??i sunt congruente, iar aria oric??rei fe??e este egal?? cu p??tratul laturii; figura are");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("??ase fe??e congruente, deci aria total?? este 6 ori p??tratul laturii. ");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- Un cub este un corp geometric (tridimensional) care are din punct de vedere constructiv: 6 fe??e, 12 muchii, 8 v??rfuri.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- Cubul sau hexaedrul este un poliedru limitat de ??ase fe??e de form?? p??trat??.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- Piramid?? patrulater?? este o piramid?? care are ca baz?? un patrulater.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- Piramida patrulater?? are: 8 muchii (4 laterale ??i 4 ale bazei) ??i 5 fe??e (4 laterale ??i baza) ??i 5 v??rfuri.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- Volumul piramidei este o treime din volumul cubului cu latur?? de lungime egal?? cu muchia piramidei.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- Piramid?? este un poliedru format prin conectarea unei suprafe??e poligonale (baz??) cu un punct (v??rf).");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Piramida");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- Tetraedrul este cel mai simplu tip de piramid??, la care baza este triunghi, de aceea mai este denumit ??i piramid?? ");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- Piramida patrulater?? are: 4 v??rfuri, 6 muchii ??i 4 fe??e.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Tetraedrul este un poliedru alc??tuit din patru fe??e triunghiulare, oricare trei dintre ele intersect??ndu-se ??ntr-unul ");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("Tetraedru regulat");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- Muchiile bazei nu sunt congruente cu muchiile laterale.");
            tf = (Label) this.textTetraedru.getChildren().get(5);
            tf.setText("din cele patru v??rfuri.");
            tf = (Label) this.textTetraedru.getChildren().get(6);
            tf.setText("triunghiular??.");
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            tf = (Label) this.textTrunchi.getChildren().get(0);
            tf.setText("situat sub planul de sectiune.");
            tf = (Label) this.textTrunchi.getChildren().get(1);
            tf.setText("- Trunchiul de piramid?? are fa??a lateral?? un trapez isoscel ortodiagonal");
            tf = (Label) this.textTrunchi.getChildren().get(2);
            tf.setText("- Baza superioar?? ??i baza inferioar?? sunt p??trate.");
            tf = (Label) this.textTrunchi.getChildren().get(3);
            tf.setText("- Trunchiul de piramid?? este corpul geometric ob??inut prin sec??ionarea unei piramide printr-un plan paralel cu baza,");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Trunchi de piramid??");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- Trunchiul de piramid?? are: 8 v??rfuri, 6 fe??e ??i 12 muchii. ");
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
            tf.setText("??i fe??elor unui  poliedru convex, atunci:");

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
        tf.setText("Largeur de la base et base sup??rieure (au tronc de la pyramide)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("Hauteur");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Longueur de la base");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Calcul??");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("G??n??ration sym??trique");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Prism");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Parall??l??pip??de rectangulaire");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("Cube");
        b = (Button) mainTop.getChildren().get(3);
        b.setText("Pyramide");
        b = (Button) mainTop.getChildren().get(4);
        b.setText("T??tra??dre");
        b = (Button) mainTop.getChildren().get(5);
        b.setText("Tronc de pyramide");
        HBox h = (HBox) mainLeft.getChildren().get(10);
        b = (Button) h.getChildren().get(1);
        b.setText("Couleur");
        if (this.poliedru.equals("Open")) {
            tf = (Label) this.textOpen.getChildren().get(0);
            tf.setText("Poly??dres r??guliers");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("D??finition: Un poly??dre convexe P est appel?? un poly??dre r??gulier si chaque sommet");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("appartient au m??me nombre d'ar??tes, toutes les faces sont des surfaces polygonales r??guli??res");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("congruents et tous les angles di??dres d??termin??s par des faces avec une ar??te commune");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("sont congruents.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Relation d'Euler: Si v, m, f repr??sentent respectivement le nombre de sommets, d'ar??tes");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("et les faces d'un poly??dre convexe, alors:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- Le prisme droit qui a la base d'un polygone r??gulier est appel?? PRISME R??GULIER.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- La hauteur (h) d'un prisme droit est ??gale ?? la longueur du bord lat??ral.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) est la somme des aires des faces lat??rales et At (At) est la somme de Al et des aires des deux bases.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- Le V (V) d'un prisme est ??gal au produit de Ab et de la hauteur.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- Un prisme qui a des bords lat??raux perpendiculaires aux plans des bases est appel?? PRISME DROIT.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Prisme triangulaire r??gulier");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Prisme quadrilat??ral r??gulier");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Prisme hexagonal r??gulier");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- La diagonale du parall??l??pip??de rectangulaire est le segment qui joint deux sommets n'appartenant pas ?? la m??me face. Ils sont quatre");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("de telles lignes, en concurrence en un point appel?? le centre du parall??l??pip??de rectangulaire.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- La longueur de la diagonale est:");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- Le volume (V) d'un prisme est ??gal au produit entre la surface de base et la hauteur.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- En g??om??trie, un parall??l??pip??de rectangulaire est un poly??dre convexe ?? six faces rectangulaires.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Parall??l??pip??de rectangulaire");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- Le cube est le parall??l??pip??de rectangulaire avec toutes les ar??tes ??gales.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- Les faces d'un cube sont carr??es et congruentes, et l'aire de n'importe quelle face est ??gale au carr?? du c??t??; la figure a");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("six faces congruentes, donc la surface totale est de 6 fois le carr?? du c??t??.");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- Un cube est un corps g??om??trique (tridimensionnel) qui a un point de vue constructif: 6 faces, 12 ar??tes, 8 pointes.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- Le cube ou hexa??dre est un poly??dre d??limit?? par six faces carr??es.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- Une pyramide quadrilat??rale est une pyramide bas??e sur un quadrilat??re.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- La pyramide quadrilat??rale comporte: 8 ar??tes (4 lat??rales et 4 de la base) et 5 faces (4 lat??rales et la base) et 5 sommets.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- Le volume de la pyramide est un tiers du volume du cube avec une longueur de c??t?? ??gale au bord de la pyramide.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- La pyramide est un poly??dre form?? en reliant une surface polygonale (base) avec un point (pic).");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Pyramide");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- Le t??tra??dre est le type de pyramide le plus simple, dont la base est un triangle, on l'appelle donc aussi une pyramide");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- La pyramide quadrilat??rale a: 4 sommets, 6 ar??tes et 4 faces.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Le t??tra??dre est un poly??dre compos?? de quatre faces triangulaires, dont trois se coupent en une");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("T??tra??dre r??gulier");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- Les bords de la base ne sont pas congruents avec les bords lat??raux.");
            tf = (Label) this.textTetraedru.getChildren().get(5);
            tf.setText("des quatre sommets.");
            tf = (Label) this.textTetraedru.getChildren().get(6);
            tf.setText("triangulaire.");
        } else if (this.poliedru.equals("TrunchiDePiramida")) {
            tf = (Label) this.textTrunchi.getChildren().get(0);
            tf.setText("situ?? sous le plan de coupe.");
            tf = (Label) this.textTrunchi.getChildren().get(1);
            tf.setText("- Le tronc de la pyramide a un trap??ze isoc??le orthodiagonal sur le c??t??");
            tf = (Label) this.textTrunchi.getChildren().get(2);
            tf.setText("- La base sup??rieure et la base inf??rieure sont carr??es.");
            tf = (Label) this.textTrunchi.getChildren().get(3);
            tf.setText("- Le tronc d'une pyramide est le corps g??om??trique obtenu en sectionnant une pyramide dans un plan parall??le ?? la base,");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Tronc de pyramide");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- Le tronc de la pyramide a: 8 pointes, 6 faces et 12 ar??tes.");
        }
    }

    public void germana(ActionEvent actionEvent) {

        Label tf;
        Button b;
        tf = (Label) mainLeft.getChildren().get(0);
        tf.setText("Grundl??nge");
        tf = (Label) mainLeft.getChildren().get(2);
        tf.setText("Basisbreite und obere Basis (am Stamm der Pyramide)");
        tf = (Label) mainLeft.getChildren().get(4);
        tf.setText("H??he");
        tf = (Label) mainLeft.getChildren().get(6);
        tf.setText("Basisl??nge");
        b = (Button) mainLeft.getChildren().get(8);
        b.setText("Berechnet");
        b = (Button) mainLeft.getChildren().get(9);
        b.setText("Symmetrische Erzeugung");
        b = (Button) mainTop.getChildren().get(0);
        b.setText("Winkel");
        b = (Button) mainTop.getChildren().get(1);
        b.setText("Rechteckiges Parallelepiped");
        b = (Button) mainTop.getChildren().get(2);
        b.setText("W??rfel");
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
            tf.setText("Regelm????ige Polyeder");
            tf = (Label) this.textOpen.getChildren().get(1);
            tf.setText("Definition: Ein konvexes Polyeder P wird bei jedem Scheitelpunkt als regul??res Polyeder bezeichnet");
            tf = (Label) this.textOpen.getChildren().get(2);
            tf.setText("geh??rt zur gleichen Anzahl von Kanten, alle Fl??chen sind regelm????ige polygonale Fl??chen");
            tf = (Label) this.textOpen.getChildren().get(3);
            tf.setText("kongruente und alle Diederwinkel, die durch Fl??chen mit gemeinsamer Kante bestimmt werden");
            tf = (Label) this.textOpen.getChildren().get(4);
            tf.setText("sind kongruent.");
            tf = (Label) this.textOpen.getChildren().get(5);
            tf.setText("Eulers Beziehung: Wenn v, m, f jeweils die Anzahl der Eckpunkte, Kanten darstellen");
            tf = (Label) this.textOpen.getChildren().get(6);
            tf.setText("und die Gesichter eines konvexen Polyeders, dann:");

        } else if (this.poliedru.equals("Prisma")) {
            tf = (Label) this.textPrisma.getChildren().get(0);
            tf.setText("- Das gerade Prisma, das die Basis eines regul??ren Polygons hat, hei??t REGELM??SSIGES PRISMUS.");
            tf = (Label) this.textPrisma.getChildren().get(1);
            tf.setText("- Die H??he (h) eines geraden Prismas entspricht der L??nge der Seitenkante.");
            tf = (Label) this.textPrisma.getChildren().get(2);
            tf.setText("- Al (AL) ist die Summe der Fl??chen der Seitenfl??chen und At (At) ist die Summe von Al und den Fl??chen der beiden Basen.");
            tf = (Label) this.textPrisma.getChildren().get(3);
            tf.setText("- Das V (V) eines Prismas ist gleich dem Produkt aus Ab und H??he.");
            tf = (Label) this.textPrisma.getChildren().get(4);
            tf.setText("- Ein Prisma mit seitlichen Kanten senkrecht zu den Ebenen der Basen wird als GERADES PRISMUS bezeichnet.");
            tf = (Label) this.textPrisma.getChildren().get(5);
            tf.setText("Regelm????iges Dreiecksprisma");
            tf = (Label) this.textPrisma.getChildren().get(6);
            tf.setText("Regelm????iges viereckiges Prisma");
            tf = (Label) this.textPrisma.getChildren().get(7);
            tf.setText("Regelm????iges sechseckiges Prisma");
        } else if (this.poliedru.equals("ParalelipipedDreptunghic")) {
            tf = (Label) this.textParalelipiped.getChildren().get(0);
            tf.setText("- Die Diagonale des rechteckigen Parallelepipeds ist das Segment, das zwei Eckpunkte verbindet, die nicht zur selben Fl??che geh??ren. Es gibt vier");
            tf = (Label) this.textParalelipiped.getChildren().get(1);
            tf.setText("solche Linien, die an einem Punkt konkurrieren, der als Zentrum des rechteckigen Parallelepipeds bezeichnet wird.");
            tf = (Label) this.textParalelipiped.getChildren().get(2);
            tf.setText("- Die L??nge der Diagonale betr??gt:");
            tf = (Label) this.textParalelipiped.getChildren().get(3);
            tf.setText("- Das Volumen (V) eines Prismas entspricht dem Produkt zwischen Grundfl??che und H??he.");
            tf = (Label) this.textParalelipiped.getChildren().get(4);
            tf.setText("- In der Geometrie ist ein rechteckiges Parallelepiped ein konvexes Polyeder mit sechs rechteckigen Fl??chen.");
            tf = (Label) this.textParalelipiped.getChildren().get(5);
            tf.setText("Rechteckiges Parallelepiped");
        } else if (this.poliedru.equals("Cub")) {
            tf = (Label) this.textCub.getChildren().get(0);
            tf.setText("- Der W??rfel ist das rechteckige Parallelepiped mit allen Kanten gleich.");
            tf = (Label) this.textCub.getChildren().get(1);
            tf.setText("- Die Fl??chen eines W??rfels sind quadratisch und kongruent, und die Fl??che einer Fl??che entspricht dem Quadrat der Seite. Die Figur hat");
            tf = (Label) this.textCub.getChildren().get(2);
            tf.setText("sechs kongruente Fl??chen, so dass die Gesamtfl??che das 6-fache des Quadrats der Seite betr??gt.");
            tf = (Label) this.textCub.getChildren().get(3);
            tf.setText("- Ein W??rfel ist ein geometrischer (dreidimensionaler) K??rper mit einer konstruktiven Sichtweise: 6 Fl??chen, 12 Kanten, 8 Spitzen.");
            tf = (Label) this.textCub.getChildren().get(4);
            tf.setText("- Der W??rfel oder das Hexaeder ist ein Polyeder, das von sechs quadratischen Fl??chen begrenzt wird.");
        } else if (this.poliedru.equals("PiramidaPatrulatera")) {
            tf = (Label) this.textPiramida.getChildren().get(0);
            tf.setText("- Eine viereckige Pyramide ist eine Pyramide, die auf einem Viereck basiert.");
            tf = (Label) this.textPiramida.getChildren().get(1);
            tf.setText("- Die viereckige Pyramide hat: 8 Kanten (4 seitliche und 4 der Basis) und 5 Fl??chen (4 seitliche und die Basis) und 5 Eckpunkte.");
            tf = (Label) this.textPiramida.getChildren().get(2);
            tf.setText("- Das Volumen der Pyramide betr??gt ein Drittel des Volumens des W??rfels mit einer Seitenl??nge, die der Kante der Pyramide entspricht.");
            tf = (Label) this.textPiramida.getChildren().get(3);
            tf.setText("- Die Pyramide ist ein Polyeder, das durch Verbinden einer polygonalen Oberfl??che (Basis) mit einem Punkt (Peak) gebildet wird.");
            tf = (Label) this.textPiramida.getChildren().get(4);
            tf.setText("Pyramide");
        } else if (this.poliedru.equals("Tetraedru")) {
            tf = (Label) this.textTetraedru.getChildren().get(0);
            tf.setText("- Der Tetraeder ist der einfachste Pyramidentyp, bei dem die Basis ein Dreieck ist, daher wird er auch als Pyramide bezeichnet");
            tf = (Label) this.textTetraedru.getChildren().get(1);
            tf.setText("- Die viereckige Pyramide hat: 4 Eckpunkte, 6 Kanten und 4 Fl??chen.");
            tf = (Label) this.textTetraedru.getChildren().get(2);
            tf.setText("- Der Tetraeder ist ein Polyeder, das aus vier dreieckigen Fl??chen besteht, von denen sich drei in einer schneiden");
            tf = (Label) this.textTetraedru.getChildren().get(3);
            tf.setText("Normaler Tetraeder");
            tf = (Label) this.textTetraedru.getChildren().get(4);
            tf.setText("- Die Kanten der Basis stimmen nicht mit den Seitenkanten ??berein.");
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
            tf.setText("- Der Stamm einer Pyramide ist der geometrische K??rper, der durch Schneiden einer Pyramide durch eine Ebene parallel zur Basis erhalten wird.");
            tf = (Label) this.textTrunchi.getChildren().get(4);
            tf.setText("Pyramidenstamm");
            tf = (Label) this.textTrunchi.getChildren().get(5);
            tf.setText("- Der Stamm der Pyramide hat: 8 Spitzen, 6 Fl??chen und 12 Kanten.");
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
