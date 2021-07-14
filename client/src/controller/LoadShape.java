package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

//SERVERUL
//citirea si scrierea din fisier
//model tot pe server
public class LoadShape {
    private Pane view;

    public Pane getShape(String fileName) {
        try {
            view = new FXMLLoader().load(getClass().getResource("/view/poliedre/" + fileName + ".fxml"));
        } catch (Exception e) {
            System.out.println("Nu s-a gasit folderul");
        }
        return view;
    }

    public ImageView getImage(String imgName){
        Image image = new Image(String.valueOf(getClass().getResource("/view/img/" + imgName + ".png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(65);
        return imageView;
    }

}
