package enibdevlab.dwarves.views.scenes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.controllers.GameClickListener;
import enibdevlab.dwarves.views.Loader;

/**
 * 
 * Ecran de connection au jeu
 * Compte et Mot de passe
 * 
 * @author Clément Perreau
 *
 */
public final class LogonScreen extends Stage{

	private Table table;
	
	private Button connect;
	private Button offline;
	private Button subscribe;
	private Button quit;
	
	private CheckBox rememberMe;
	
	private TextField account;
	private TextField password;
		
	public LogonScreen(){
		super();
		init();
		build();
		this.addActor(this.table);
	}

	private void init() {
		
		Skin skin = DwarvesManager.getSkin();
		
		this.table = new Table(skin);
		
		this.connect   = new ImageButton(new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(17)),
                		                 new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(19)));
		this.offline   = new ImageButton(new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(20)),
				   					     new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(22)));
		this.quit      = new ImageButton(new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(6)),
				   				         new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(7)));
		this.subscribe = new TextButton("Inscription", skin);
		
		this.account = new TextField("", skin);
		
		this.password = new TextField("", skin);
		this.password.setPasswordMode(true);
		this.password.setPasswordCharacter('*');
		
		this.rememberMe = new CheckBox("Se souvenir de moi", skin);
	
		
		this.offline.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				DwarvesManager.getInstance().setStage(new EnibScreen(DwarvesManager.getInstance()));
			}
		});
		
		this.connect.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				DwarvesManager.getInstance().setStage(new EnibScreen(DwarvesManager.getInstance()));
			}
		});
		
		this.subscribe.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				DwarvesManager.getInstance().setStage(new SubscriptionScreen(account.getText()));
			}
		});
		
	}
	
	
	@Override
	public void act() {
		if(DwarvesManager.debug){
			init();
			build();
		}
	}
	
	private void build() {
		
		Skin skin = DwarvesManager.getSkin();
		
		this.table.clear();
		this.table.defaults().space(10);
		
		Table textfieldTable = new Table(skin);
		
		textfieldTable.defaults().space(10);
		
		textfieldTable.add("Compte Utilisateur :");
		textfieldTable.add(account);
		textfieldTable.add(rememberMe);
		textfieldTable.row();
		textfieldTable.add("Mot de passe :");
		textfieldTable.add(password);
		textfieldTable.add(subscribe);
		
		Table buttonTable = new Table(skin);
		buttonTable.defaults().space(10);
		buttonTable.add(connect);
		buttonTable.add(offline);
		//buttonTable.row();
		buttonTable.add(quit).fillX().center();
		
		table.add(textfieldTable).center().height(DwarvesManager.getHeight()/3);
		table.row();
		table.add(buttonTable).center();
		table.pack();
		
		this.table.setPosition(DwarvesManager.getWidth()/2-table.getWidth()/2, DwarvesManager.getHeight()/2-1f/3f*table.getHeight()/2);
		
	}

	
	
}
