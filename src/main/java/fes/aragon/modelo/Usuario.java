package fes.aragon.modelo;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Usuario implements Serializable{
    public String nombre;
    public String apellidoPaterno;
    public String correo;
    private SerializableImage imagen;

    public Usuario() {

    }

    public Usuario(String nombre, String apellidoPaterno, String correo) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public SerializableImage getImagen() {
        return imagen;
    }

    public void setImagen(SerializableImage imagen) {
        this.imagen = imagen;
    }
}
