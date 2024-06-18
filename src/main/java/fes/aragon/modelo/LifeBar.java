package fes.aragon.modelo;

import fes.aragon.controller.JuegoController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;

public class LifeBar extends ComponentesJuego{

    private Image life;

    public LifeBar(int x, int y, String imagen, double velocidad) {
        super(x, y, imagen, velocidad);
        try {
            File f = new File(imagen);
            this.life = new Image(f.toURI().toString());
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void pintar(GraphicsContext graficos) {
        graficos.drawImage(life,x,y);
    }

    @Override
    public void teclado(KeyEvent evento, boolean presiona) {

    }

    @Override
    public void raton(MouseEvent evento) {

    }

    @Override
    public void logicaCalculos() {
        int c=SingletonItems.getInstance().getNave().getVida();
        switch (c){
            case 5:
                File f=new File(imagen);
                this.life=new Image(f.toURI().toString());
                break;
            case 4:
                f=new File(getClass().getResource("/fes/aragon/fondo/lifebar_80.png").getFile());
                this.life=new Image(f.toURI().toString());
                break;
            case 3:
                f=new File(getClass().getResource("/fes/aragon/fondo/lifebar_60.png").getFile());
                this.life=new Image(f.toURI().toString());
                break;
            case 2:
                f=new File(getClass().getResource("/fes/aragon/fondo/lifebar_40.png").getFile());
                this.life=new Image(f.toURI().toString());
                break;
            case 1:
                f=new File(getClass().getResource("/fes/aragon/fondo/lifebar_20.png").getFile());
                this.life=new Image(f.toURI().toString());
                break;
            case 0:
                f=new File(getClass().getResource("/fes/aragon/fondo/fondo_gameOverver1.png").getFile());
                SingletonItems.getInstance().getFondo().setImg(new Image(f.toURI().toString()));
                for (int i=1;i<SingletonItems.getInstance().getElementos().size();i++){
                    SingletonItems.getInstance().getElementos().remove(i);}
                Timeline timeline =new Timeline(new KeyFrame(Duration.seconds(0.1),event -> {
                    for (int i=1;i<SingletonItems.getInstance().getElementos().size();i++){
                        SingletonItems.getInstance().getElementos().remove(i);}
                    System.out.println("Cerrar");
                    //cerrarJuego();
                }));
                timeline.play();
                break;
        }
    }
    public void cerrarJuego(){
        Platform.exit();
    }
}
