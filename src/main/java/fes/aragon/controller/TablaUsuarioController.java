package fes.aragon.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Archivos;
import fes.aragon.modelo.SingletonUsuario;
import fes.aragon.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TablaUsuarioController implements Initializable{

    @FXML
    private TableColumn<Usuario, String> clmApellido;

    @FXML
    private TableColumn<Usuario, String> clmCorreo;

    @FXML
    private TableColumn<Usuario, String> clmNombre;

    @FXML
    private TableColumn<Usuario, String> clmOperaciones;

    @FXML
    private FontAwesomeIconView iconAbrirUsuario;

    @FXML
    private FontAwesomeIconView iconGuardarUsuario;

    @FXML
    private FontAwesomeIconView iconNuevoUsuario;

    @FXML
    private TableView<Usuario> tblUsuario;

    @FXML
    void accionAbrirUsuario(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //       fileChooser.getExtensionFilters().addAll(
        //               new FileChooser.ExtensionFilter("FES", "*.fes"));
        File selectedFile = fileChooser.showOpenDialog(this.iconAbrirUsuario.getScene().
                getWindow());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                ObjectInputStream entrada = new ObjectInputStream(fo);
                ArrayList<Usuario> datos = (ArrayList<Usuario>) entrada.readObject();
                SingletonUsuario.getInstance().getLista().clear();
                for (Usuario us:datos) {
                    System.out.println(us.getImagen());
                    SingletonUsuario.getInstance().getLista().add(us);
                }
            }catch (IOException | ClassNotFoundException e) { //+FileNotFound
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void accionGuardarUsuario(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        //       fileChooser.getExtensionFilters().addAll(
        //               new FileChooser.ExtensionFilter("FES", "*.fes"));
        File selectedFile = fileChooser.showSaveDialog(this.iconAbrirUsuario.getScene().
                getWindow());
        if (selectedFile != null) {
            try {
                FileOutputStream fo = new FileOutputStream(selectedFile);
                ObjectOutputStream salida = new ObjectOutputStream(fo);
                ArrayList<Usuario> datos = SingletonUsuario.getInstance().getConversion();
                for (Usuario us : datos) {
                    System.out.println(us.getImagen());
                }
                salida.writeObject(datos);
                salida.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void accionNuevoUsuario(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/xml/registro_de_usuario.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clmApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        this.clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tblUsuario.setItems(SingletonUsuario.getInstance().getLista());
        Callback<TableColumn<Usuario, String>, TableCell<Usuario, String>>
                celda = (TableColumn<Usuario, String> parametros) -> {
            final TableCell<Usuario, String> cel = new TableCell<>() {
                @Override
                protected void updateItem(String s, boolean b) {
                    super.updateItem(s, b);
                    if (b) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        borrarIcono.setGlyphStyle("-fx-cursor:hand;" + "gylph-size:28px;" + "-fx-fill:#ff1744");

                        FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                        modificarIcono.setGlyphStyle("-fx-cursor:hand;" + "gylph-size:28px;" + "-fx-fill:#ff1744");
                        borrarIcono.setOnMouseClicked((MouseEvent evento) -> {
                            int indice = tblUsuario.getSelectionModel().
                                    getSelectedIndex();
                            SingletonUsuario.getInstance().getLista().
                                    remove(indice);
                        });
                        modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
                            modificarUsuario(tblUsuario.getSelectionModel().
                                    getSelectedIndex());
                        });
                        HBox hBox = new HBox(modificarIcono, borrarIcono);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(modificarIcono, new Insets(2, 2, 0, 3));
                        HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cel;
        };
        this.clmOperaciones.setCellFactory(celda);
    }

    public void modificarUsuario(int indice){
        try {
            FXMLLoader modificar = new FXMLLoader(getClass().
                        getResource("/fes/aragon/xml/registro_de_usuario.fxml"));
            Parent parent = (Parent) modificar.load();
            ((RegistroDeUsuarioController) modificar.getController()).indiceUsuario(indice);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
