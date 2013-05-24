package enibdevlab.dwarves.views.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.controllers.actions.animations.VAction;
import enibdevlab.dwarves.views.actors.AImage;
import enibdevlab.dwarves.views.audio.MusicManager;
import enibdevlab.dwarves.views.audio.SoundManager;


/**
 * 
 * Scène qui affiche un écran de présentation avec le logo Enib
 * 
 * @author Clément
 *
 */
public class EnibScreen extends Stage {

	protected DwarvesManager game;
	protected boolean soundPlayed = false;
	
	public EnibScreen(DwarvesManager game){
		super(DwarvesManager.getWidth(),DwarvesManager.getHeight(), false);
		this.game = game;
		MusicManager.playMusic("data/music/music2.mp3");
		init();
	}
	
	private void init(){
		
		// Titre
		TextureRegion title = new TextureRegion(new Texture("data/sprites/eniblogo.png"));
		AImage logo = new AImage(title, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		this.addActor(logo);
		
		/* Ajout d'un mouvement pour le logo */
		logo.addAction(Actions.scaleBy(.1f,.1f));
		logo.addAction(Actions.parallel(
								Actions.rotateBy(360f,2f),
					            Actions.scaleTo(1f,1f,2f)
					            ));
		
		
		/* Ajout d'une action de controle pour la transition vers le menu */
		this.addAction(new VAction(game) {
	
			protected float elapsedTime;
			
			@Override
			public boolean act(float delta) {
				elapsedTime += delta;
				
				if(elapsedTime > 2f && !soundPlayed){
					SoundManager.play("lenib");
					soundPlayed = true;
				}
				
				if (elapsedTime > 4f){
					game.setStage(new MainMenu(game));
				}
				return false;
			}
		});
				            
	}
	
}
