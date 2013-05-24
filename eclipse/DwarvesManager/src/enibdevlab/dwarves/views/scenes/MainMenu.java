package enibdevlab.dwarves.views.scenes;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.controllers.GameClickListener;
import enibdevlab.dwarves.models.levels.Tuto1;
import enibdevlab.dwarves.views.Loader;
import enibdevlab.dwarves.views.Tileset;
import enibdevlab.dwarves.views.actors.AImage;
import enibdevlab.dwarves.views.scenes.game.GameScene;

/**
 * 
 * Scene destinée à afficher le menu principal du jeu
 * 
 * @author Clément Perreau
 *
 */
public class MainMenu extends Stage {

	protected DwarvesManager game;
	protected Table table;
	protected static Tileset buttonsImg = Loader.mainMenuGui;
	
	/**
	 * Constructeur
	 * @param game   Reference vers le jeu
	 * @param width  Taille X
	 * @param height Taille Y
	 */
	public MainMenu(DwarvesManager game){
		super(DwarvesManager.getWidth(),DwarvesManager.getHeight(), false);
		this.game = game;
		init();
		
		// Transition
		this.addAction(Actions.color(new Color(0,0,0,0)));
		this.addAction(Actions.color(new Color(1,1,1,1), 1f));
	}
	
	/**
	 * Fonction appelée à l'initialisation
	 * Dispose les widgets de manière à créer le menu
	 */
	private void init(){
		
		// Titre
		TextureRegion title = new TextureRegion(new Texture("data/sprites/credits.png"));
		AImage titleText = new AImage(title, (int)(Gdx.graphics.getWidth()*4f/5f), (int) (Gdx.graphics.getHeight()/2));
		this.addActor(titleText);
		
		// Creation du menu
		table = new Table(DwarvesManager.getSkin());
		this.addActor(table);
		
		// Créations des boutons
		table.defaults().space(15);
		ImageButton newGame =      new ImageButton(new TextureRegionDrawable(buttonsImg.getTile(0)),
				                                   new TextureRegionDrawable(buttonsImg.getTile(1)));
		ImageButton continueGame = new ImageButton(new TextureRegionDrawable(buttonsImg.getTile(2)),
				                                   new TextureRegionDrawable(buttonsImg.getTile(3)));
		ImageButton choiceLevel =  new ImageButton(new TextureRegionDrawable(buttonsImg.getTile(16)),
		           								   new TextureRegionDrawable(buttonsImg.getTile(18)));
		ImageButton options =      new ImageButton(new TextureRegionDrawable(buttonsImg.getTile(4)),
				                                   new TextureRegionDrawable(buttonsImg.getTile(5)));
		ImageButton quit =         new ImageButton(new TextureRegionDrawable(buttonsImg.getTile(6)),
				   						           new TextureRegionDrawable(buttonsImg.getTile(7)));
		
		table.add(newGame);
		table.row();
		table.add(continueGame);
		table.row();
		table.add(choiceLevel);
		table.row();
		table.add(options);
		table.row();
		table.add(quit);
		table.row();
		table.pack();
		
		table.setX(0);
		table.setY(Gdx.graphics.getHeight()/2-table.getHeight()/2);
		
		/* Ajout des actions des boutons */
		
		// Jouer
		newGame.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				this.dw.setStage(new GameScene(dw, Tuto1.class));
			}
		});
		
		// Continuer
		continueGame.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				this.dw.setStage(new LoadGameMenu(DwarvesManager.getInstance().getStage()));
			}
		});
		
		// Choix du niveau
		choiceLevel.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				this.dw.setStage(new LoadGameMenu(DwarvesManager.getInstance().getStage(), false));
			}
		});
		
		// Options
		options.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				this.dw.setStage(new OptionMenu(DwarvesManager.getInstance().getStage()));
			}
		});
		
		// Quitter
		quit.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				System.exit(0);
			}
		});
		
	}
	
}
