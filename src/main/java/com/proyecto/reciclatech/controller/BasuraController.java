package com.proyecto.reciclatech.controller;

import com.proyecto.reciclatech.service.BasuraService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.proyecto.reciclatech.model.Basura;
import javafx.stage.Stage;

import java.io.IOException;

public class BasuraController {

    @FXML private TextField txtNombreResiduo;
    @FXML private Button btnClasificar;
    @FXML private Label lblResultado;
    @FXML private Button btnCambio;
    @FXML private Label lblMensaje;

    private final BasuraService service = new BasuraService();

    @FXML
    private void clasificarResiduo(ActionEvent event) {
        String nombre = txtNombreResiduo.getText().trim();

        if (nombre.isEmpty()) {
            lblResultado.setText("Por favor, ingresa el nombre de un residuo.");
            return;
        }

        Basura basura = service.obtenerBasura(nombre);

        if (basura != null) {
            lblResultado.setText("Categoría: " + basura.getCategoria());
        } else {
            lblResultado.setText("No se pudo clasificar el residuo.");
            txtNombreResiduo.clear();
        }
    }

    @FXML
    private void irAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyecto/reciclatech/view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnCambio.getScene().getWindow(); // obtener ventana actual
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("Error al cargar la ventana de login.");
        }
    }

}
