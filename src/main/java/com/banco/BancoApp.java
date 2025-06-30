package com.banco;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BancoApp  extends Application {
    @Override
    public void start(Stage stage){
        ControladorUI ui = new ControladorUI();
        Scene scene = new Scene(ui.criarLayout(), 700, 400);
        stage.setScene(scene);
        stage.setTitle("Simulador Bancário Multithread ");
        stage.show();
    }
}
