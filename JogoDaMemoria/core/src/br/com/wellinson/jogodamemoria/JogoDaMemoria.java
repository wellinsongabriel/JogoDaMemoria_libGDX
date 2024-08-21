package br.com.wellinson.jogodamemoria;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class JogoDaMemoria extends ApplicationAdapter {
	SpriteBatch batch;
	Stage stage;
	BitmapFont textoFimJogo;
	ArrayList<Carta> cartasViradas;
	ArrayList<Carta> cartasIguais;
	float tempo = 0.5f;
	float contador = 0f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);//para reconhecer os cliques
		
		textoFimJogo = criarFonte(48, Color.BLUE);
		cartasViradas = new ArrayList<Carta>();
		cartasIguais = new ArrayList<Carta>();
		
		criarCartas();
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.8f, 0.8f, 0.8f, 0.8f);
		
		stage.draw();
		atualizarTempo();
		removerCartasIguais();
		compararCartas(cartasViradas);
		fimJogo();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}

	private BitmapFont criarFonte(int tamanho, Color cor) {
		BitmapFont fonte;
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ARIALBD.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		parametro.size = tamanho;
		parametro.color = cor;
		
		fonte = generator.generateFont(parametro);
		
		generator.dispose();
		
		return fonte;
	}
	
	private void criarCartas() {
		ArrayList<Integer> numeroCartas = new ArrayList<Integer>();
		
		for (int i = 1; i <=4; i++) {
			numeroCartas.add(i);		
			numeroCartas.add(i);		
		}
		
		Collections.shuffle(numeroCartas);
		
		for (int i = 0, x = 50; i < 4; i++, x+= 210) {
			Carta carta = new Carta(numeroCartas.get(i), x, Gdx.graphics.getHeight()-300, this);
			stage.addActor(carta);
		}
		
		for (int i = 4, x = 50; i < 8; i++, x+= 210) {
			Carta carta = new Carta(numeroCartas.get(i), x, Gdx.graphics.getHeight()-600, this);
			stage.addActor(carta);
		}
		
	}
	
	public void virarCartas() {
		for (Actor actor : stage.getActors()) {
			if(actor instanceof Carta)
				((Carta)actor).virada = false;
		}
		cartasViradas.clear();
	}
	
	private void removerCartasIguais() {
		
		if(contador <=0) {
			for (Carta carta : cartasIguais) {
				stage.getActors().removeValue(carta, true);
			}
			cartasIguais.clear();
		}
		
	}
	
	
	private void compararCartas(ArrayList<Carta> cartasViradas) {
		if(cartasViradas.size() == 2) {
			Carta carta1 = cartasViradas.get(0);
			Carta carta2 = cartasViradas.get(1);
			
			if(carta1.texturaFrente.toString().contentEquals(carta2.texturaFrente.toString())
					&& carta1 != carta2) {
				cartasIguais.add(carta1);
				cartasIguais.add(carta2);
				contador = tempo;
				cartasViradas.clear();				
			}
		}
	}
	
	private void atualizarTempo() {
		if(contador > 0) {
			contador -= Gdx.graphics.getDeltaTime();
		}
		
	}
	
	private void fimJogo() {
		boolean jogoConcluido = true;
		for (Actor actor : stage.getActors()) {
			if(actor instanceof Carta) {
				jogoConcluido = false;
				break;
			}
		}
		if(jogoConcluido) {
			String mensagem = "Jogo concluído";
			
			batch.begin();
			
			GlyphLayout layout = new GlyphLayout(textoFimJogo, mensagem);
			float larguraTexto = layout.width;
			float alturaTexto = layout.height;

			float x = (Gdx.graphics.getWidth() - larguraTexto)/2;
			float y = (Gdx.graphics.getHeight() + alturaTexto)/2;
			
			textoFimJogo.draw(batch, mensagem, x, y);
			
			batch.end();
			
			
		}
		
	}
}
