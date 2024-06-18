/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.modelo;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 *
 * @author Administrador
 */
public class EfectosMusica implements Runnable {
	private BufferedInputStream buffer = null;
	private FileInputStream archivo;
	private Player player = null;

	public EfectosMusica(String archivo) {
		try {
			this.archivo = new FileInputStream(
					this.getClass().getResource("/fes/aragon/musica/" + archivo + ".mp3").toURI().getPath());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			buffer = new BufferedInputStream(this.archivo);
			player = new Player(buffer);
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
