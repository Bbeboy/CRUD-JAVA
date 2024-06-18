package fes.aragon.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Archivos;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ArchivoController implements Initializable{
    @FXML
    private TableColumn<Archivos, String> clmNombre;

    @FXML
    private TableColumn<Archivos, String> clmOperacion;

    @FXML
    private TableColumn<Archivos, String> clmRuta;

    @FXML
    private Button idAbrir;

    @FXML
    private TableView<Archivos> tblTabla;

    private ObservableList<Archivos> listaGeneral;

    @FXML
    void accionAbrirDirectorio(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        File f = fileChooser.showDialog(idAbrir.getScene().getWindow());
        if (f != null) {
            listaGeneral = listaArchivos(f.getAbsoluteFile());
            tblTabla.setItems(listaGeneral);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clmRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));
        this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        Callback<TableColumn<Archivos, String>, TableCell<Archivos, String>>
                celda = (TableColumn<Archivos, String> parametros)->{
            final TableCell<Archivos, String> cel = new TableCell<>(){
                @Override
                protected void updateItem(String s, boolean b) {
                    super.updateItem(s, b);
                    if(b){
                        setGraphic(null);
                        setText(null);
                    }else{
                        FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        borrarIcono.setGlyphStyle("-fx-cursor:hand;" + "gylph-size:28px;" + "-fx-fill:#ff1744");
                        borrarIcono.setOnMouseClicked((MouseEvent evento) ->{

                            //System.out.println("evento para borrar");

                            Archivos elemento = tblTabla.getSelectionModel().getSelectedItem();
                            listaGeneral.remove(elemento);
                            File borrar = new File(elemento.getRuta());
                            borrar.delete();
                        });
                        HBox hBox = new HBox(borrarIcono);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(borrarIcono, new Insets(2,2,0,3));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cel;
        };
        this.clmOperacion.setCellFactory(celda);

    }
    private ObservableList<Archivos> listaArchivos(File f) {
        ObservableList<Archivos> lista = FXCollections.observableArrayList();
        ArrayList<File> directorios = new ArrayList<>();
        directorios.add(f);
        while(!directorios.isEmpty()){
            File actual = directorios.remove(0);
            if (actual.listFiles() != null) {
                for (File dato:actual.listFiles()){
                    if (dato.isDirectory()) {
                        directorios.add(dato.getAbsoluteFile());
                    }else{
                        Archivos archivos = new Archivos(dato.getAbsolutePath(),dato.getName());
                        lista.add(archivos);
                    }
                }

            }
        }
        return lista;
    }
}
