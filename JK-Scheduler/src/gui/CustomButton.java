/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 *
 * @author Kyle
 */
public class CustomButton extends Button {
    public CustomButton(String name, Scene scene) {
        super();
        setText(name);
        this.getStyleClass().add("button-raised");
        this.setOnMouseEntered(e->{
            scene.setCursor(Cursor.HAND);
        });
        this.setOnMouseExited(e->{
            scene.setCursor(Cursor.DEFAULT);
        });
    }
}
