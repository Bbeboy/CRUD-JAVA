package fes.aragon.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SingletonUsuario {
    private static SingletonUsuario singletonUsuario;

    private ObservableList<Usuario> lista;

    private SingletonUsuario(){
        lista = FXCollections.observableArrayList();
    }

    public static SingletonUsuario getInstance(){
        if(singletonUsuario == null){
            singletonUsuario = new SingletonUsuario();
        }
        return singletonUsuario;
    }

    public ObservableList<Usuario> getLista(){
        return lista;
    }

    public ArrayList<Usuario> getConversion(){
        return new ArrayList<>(lista);
    }
}
