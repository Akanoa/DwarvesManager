package enibdevlab.dwarves;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import enibdevlab.dwarves.controllers.cloud.Cloud;
import enibdevlab.dwarves.models.levels.DWLevel;
import enibdevlab.dwarves.views.Loader;
import enibdevlab.dwarves.views.audio.SoundManager;
import enibdevlab.dwarves.views.scenes.LogonScreen;
import enibdevlab.dwarves.views.scenes.game.GameScene;

/**
 * 
 * Classe principale requise par libGdx
 * (a ici le rôle d'un directeur de scène, et contient aussi quelques objets et infos statiques utiles partout dans le jeu)
 * 
 * @author Clément Perreau
 *
 */
public class DwarvesManager extends Game {
	
	/**
	 * Objet qui permet le rendu des formes
	 */
	public static ShapeRenderer renderer;
	
	/**
	 * Objet qui permet le rendu de police
	 */
	public static BitmapFont font;
	
	/**
	 * Scene en cours
	 */
	protected Stage stage;
	
	/**
	 * Skin des GUI
	 */
	protected static Skin skin;
	
	/**
	 * Resolution
	 */
	protected static int width, height;
	
	/**
	 * Debug
	 */
	public static boolean debug = true;
	
	/**
	 * Random
	 */
	public static Random random = new Random();
	
	/**
	 * Timer
	 */
	public static float elapsedTime = 0f;
	public static Timer timer = new Timer();
	
	protected static DwarvesManager instance;
	
	@Override
	public void create() {

		instance = this;
		
		renderer = new ShapeRenderer();
		font = new BitmapFont();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		Loader.init();
		SoundManager.init();
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		this.setStage(new LogonScreen());
		//this.newGame(Level1.class);
		//this.loadGame("game.xml");

		this.resize(width, height);
		
		try {
			Cloud.init();
			//Cloud.instance.suscribe("Noa", "moi");
			//Cloud.instance.save(Gdx.files.external("DwarvesManager/Saves/test.xml"), "Noa");
			//Cloud.instance.load("PartieEnCours.xml", "Noa");
			System.out.println("Fin de loading");
			FileHandle file = Gdx.files.external("DwarvesManager/log.log");
			file.writeString(Boolean.toString(Cloud.instance.load("PartieEnCours.xml", "Noa")), false);
			Cloud.instance.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FileHandle file = Gdx.files.external("DwarvesManager/log.log");
			file.writeString("Exception MOTHERFUCKER", true);
			e.printStackTrace();
		}
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		elapsedTime+=(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize (int w, int h) {
		stage.setViewport(width, height, false);
		stage.getCamera().position.set(width/2, height/2, 0);
	}

	@Override
	public void dispose () {
		stage.dispose();
		skin.dispose();
	}
	
	public static Skin getSkin(){
		return skin;
	}
	
	public static ShapeRenderer getRenderer() {
		return renderer;
	}

	public static void setRenderer(ShapeRenderer renderer) {
		DwarvesManager.renderer = renderer;
	}

	public static DwarvesManager getInstance() {
		return instance;
	}

	public void setStage(Stage stage){
		this.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	public static void toggleDebug(){
		debug = !debug;
	}
	
	public void newGame(Class<? extends DWLevel> level){
		this.setStage(new GameScene(this, level));
	}
	
	public void loadGame(String path) {
		this.setStage(new GameScene(this, path));
	}

	public Stage getStage() {
		return this.stage;
	}

}
