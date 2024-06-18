package fes.aragon.controller;

import fes.aragon.modelo.OperacionRacional;
import fes.aragon.modelo.Racional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class RacionalController{

    @FXML
    private Button btnOperacion;

    @FXML
    private TextField txtDenominadorDos;

    @FXML
    private TextField txtDenominadorUno;

    @FXML
    private TextField txtNumeradorDos;

    @FXML
    private TextField txtNumeradorUno;

    @FXML
    private TextField txtResultadoDenominador;

    @FXML
    private TextField txtResultadoNumerador;

    @FXML
    void operacion(ActionEvent event) {
        Racional racionalUno = new Racional(
                Integer.parseInt(txtNumeradorUno.getText()),
                Integer.parseInt(txtDenominadorUno.getText()));
        Racional racionalDos = new Racional(
                Integer.parseInt(txtNumeradorDos.getText()),
                Integer.parseInt(txtDenominadorDos.getText()));
        OperacionRacional op = new OperacionRacional(racionalUno, racionalDos);
        Racional resultado = op.suma();
        txtResultadoNumerador.setText(String.valueOf(resultado.getNumerador()));
        txtResultadoDenominador.setText(String.valueOf(resultado.getDenominador()));
    }

}



