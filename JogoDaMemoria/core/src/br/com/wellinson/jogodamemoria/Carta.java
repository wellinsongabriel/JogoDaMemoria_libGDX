package br.com.wellinson.jogodamemoria;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Carta extends Actor{
	
	Texture texturaVerso;
	Texture texturaFrente;
	boolean virada = false;
	float x;
	float y;
	float largura = 190;
	float altura = 255;
	Carta cartaAtual;
	
	public Carta(int numeroCarta, float x, float y, JogoDaMemoria jogo) {
		texturaVerso = new Texture("verso.png");
		texturaFrente = new Texture("carta"+numeroCarta+".png");
		
		this.x = x;
		this.y = y;
		setBounds(x, y, largura, altura);
		cartaAtual = this;
		
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				ArrayList<Carta> cartasViradas = jogo.cartasViradas; 
				
				if(cartasViradas.size() < 2) {
					virada = true;
					jogo.cartasViradas.add(cartaAtual);
				}else if(cartasViradas.size()>=2) {
					jogo.virarCartas();
					virada = true;
					jogo.cartasViradas.add(cartaAtual);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}
	
	public void draw(Batch batch, float delta) {
		batch.draw(!virada ? texturaVerso : texturaFrente, x, y, largura, altura);
	}
	

}
