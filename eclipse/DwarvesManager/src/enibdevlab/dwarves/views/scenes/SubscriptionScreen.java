package enibdevlab.dwarves.views.scenes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.controllers.GameClickListener;
import enibdevlab.dwarves.views.Loader;
import enibdevlab.dwarves.views.actors.WaitingEffect;

/**
 * 
 * Ecran de création d'un compte utilisateur DwarvesManager
 * 
 * @author Clément Perreau
 *
 */
public final class SubscriptionScreen extends Stage {

	private Table table;
	
	private Button subscription;
	private Button cancel;
	
	private TextField username;
	private TextField password;
	private TextField passwordConfirm;
	
	private int state;
	private static int WAITINGSERVER = 1;
	private static int NORMAL = 0;
	
	/**
	 * Crée le formulaire d'inscription 
	 */
	public SubscriptionScreen(String name){
		super();
		init();
		build();
		state = NORMAL;
		this.username.setText(name);
		this.addActor(this.table);
	}

	private void init() {
		
		Skin skin = DwarvesManager.getSkin();
		
		this.table = new Table(skin);
		
		this.subscription   = new ImageButton(new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(21)),
				     					      new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(23)));
		this.cancel         = new ImageButton(new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(12)),
					     				      new TextureRegionDrawable(Loader.mainMenuGuiSmall.getTile(14)));
	
		this.username = new TextField("", skin);
		
		this.password = new TextField("", skin);
		this.password.setPasswordMode(true);
		this.password.setPasswordCharacter('*');
		
		this.passwordConfirm = new TextField("", skin);
		this.passwordConfirm.setPasswordMode(true);
		this.passwordConfirm.setPasswordCharacter('*');
		
		this.cancel.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				DwarvesManager.getInstance().setStage(new LogonScreen());
			}
		});
		
		this.subscription.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				if(state == NORMAL){
					addActor(new WaitingEffect("Ok"));
					state = WAITINGSERVER;
				}
			}
		});
		
	}
	
	
	private void build() {
		
		table.clear();
		table.defaults().space(10);
		
		Skin skin = DwarvesManager.getSkin();
		
		Table form = new Table(skin);
		form.defaults().space(10);
		
		form.add("Nom d'utilisateur :");
		form.add(username);
		form.row();
		form.add("Mot de passe :");
		form.add(password);
		form.row();
		form.add("Confirmation Mot de passe : ");
		form.add(passwordConfirm);
		form.row();
		
		Table buttons = new Table(skin);
		buttons.defaults().space(10);
		buttons.add(subscription);
		buttons.add(cancel);
		
		table.add(form);
		table.row();
		table.add(buttons);
		table.pack();
		
		this.table.setPosition(DwarvesManager.getWidth()/2-table.getWidth()/2, DwarvesManager.getHeight()/2-1f/3f*table.getHeight()/2);
	}
	
	
	
	
	
	
}
