/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

/**
 *
 * @author Kyle
 */
public class CustomScene extends Scene {

    public CustomScene(Parent root) {
        super(root);
        this.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
    }

    public CustomScene(Parent root, double width, double height) {
        super(root, width, height);
        this.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
    }

    public CustomScene(Parent root, Paint fill) {
        super(root, fill);
        this.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
    }

    public CustomScene(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
        this.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
    }

    public CustomScene(Parent root, double width, double height, boolean depthBuffer) {
        super(root, width, height, depthBuffer);
        this.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
    }

    public CustomScene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
        super(root, width, height, depthBuffer, antiAliasing);
        this.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
    }    
}
