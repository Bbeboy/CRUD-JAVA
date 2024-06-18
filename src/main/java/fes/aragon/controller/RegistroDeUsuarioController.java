package fes.aragon.controller;

import fes.aragon.modelo.Archivos;
import fes.aragon.modelo.SerializableImage;
import fes.aragon.modelo.SingletonUsuario;
import fes.aragon.modelo.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RegistroDeUsuarioController {
    private Integer indice;

    @FXML
    private Button btnAbrirImagen;

    @FXML
    private Button btnGuardar;

    @FXML
    private ImageView imgImagen;

    @FXML
    private TextField txtApellidoPaterno;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;
    private File selectedFile;

    @FXML
    void abrirImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("imagen", "*.png"),
                new FileChooser.ExtensionFilter("imagen JPG", "*.jpg")
        );
        this.selectedFile = fileChooser.showOpenDialog(
                this.btnGuardar.getScene().getWindow());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                Image imagen = new Image(fo);
                this.imgImagen.setImage(imagen);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void guardarArchivo(ActionEvent event) {
       Usuario usuario = new Usuario();
       usuario.setNombre(txtNombre.getText());
       usuario.setApellidoPaterno(txtApellidoPaterno.getText());
       usuario.setCorreo(txtCorreo.getText());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                Image imagen = new Image(fo);
                SerializableImage img = new SerializableImage();
                img.setImage(imagen);
                usuario.setImagen(img);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if (indice == null) {
            SingletonUsuario.getInstance().getLista().add(usuario);
        }else{
            SingletonUsuario.getInstance().getLista().
                    set(indice, usuario);
            Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
            stage.close();
        }

        txtNombre.clear();
        txtApellidoPaterno.clear();
        txtCorreo.clear();
        imgImagen.setImage(null);
    }

    public void indiceUsuario(int indice){
        this.indice = indice;
        Usuario usuario = SingletonUsuario.   // ¡¡¡  !!!
                getInstance().getLista().get(indice);
        txtNombre.setText(usuario.getNombre());
        txtApellidoPaterno.setText(usuario.getApellidoPaterno());
        txtCorreo.setText(usuario.getCorreo());
        System.out.println(usuario.getImagen());
        imgImagen.setImage(usuario.getImagen().getImage()); //Usuarios -> Imagen / SerializableImage ->Image
    }
}
