package enibdevlab.dwarves.views.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.controllers.GameClickListener;
import enibdevlab.dwarves.models.levels.DWLevel;
import enibdevlab.dwarves.views.Loader;

/**
 * 
 * Menu de chargement d'une partie
 * 
 * @author Clément Perreau
 *
 */
public class LoadGameMenu extends Stage {

	/**
	 * Jeu
	 */
	protected DwarvesManager game;
	
	/**
	 * Widgets
	 */
	private Button ok;
	private Button cancel;
	protected Table table;
	protected Table scrolltable;
	protected ScrollPane scroll;
	protected List list;
	
	// Charge on une partie
	protected boolean load = false;
	
	/**
	 * Scène précédente
	 */
	protected Stage previousStage;
	
	public LoadGameMenu(Stage previousStage, boolean load){
		super();
		this.load = load;
		this.previousStage = previousStage;
		this.game = DwarvesManager.getInstance();
		init();
		build();
		
		// Transition
		this.addAction(Actions.color(new Color(0,0,0,0)));
		this.addAction(Actions.color(new Color(1,1,1,1), 1f));
	}
	
	public LoadGameMenu(Stage previousStage){
		this(previousStage, true);
	}

	private void init(){
		Skin skin = DwarvesManager.getSkin();
		
		scrolltable = new Table(skin);
		scroll = new ScrollPane(scrolltable, skin);
		scroll.setScrollingDisabled(false, false);
		scroll.setHeight(DwarvesManager.getHeight()/2);
		scroll.setWidth(DwarvesManager.getWidth()*2f/3f);
		
		this.table = new Table(skin);
		
		if(load){
			this.list = new List(Loader.getSavegames(), skin);
		}
		else{
			String[] str = new String[Loader.levels.size()];
			int i = 0;
			for(Object level:Loader.levels){
				str[i++] = level.toString();
			}
			this.list = new List(str, skin);
		}
		
		this.ok = new ImageButton(new TextureRegionDrawable(MainMenu.buttonsImg.getTile(8)),
                  new TextureRegionDrawable(MainMenu.buttonsImg.getTile(10)));
		this.cancel = new ImageButton(new TextureRegionDrawable(MainMenu.buttonsImg.getTile(12)),
                      new TextureRegionDrawable(MainMenu.buttonsImg.getTile(14)));
		this.addActor(this.table);
		
		
		this.cancel.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				DwarvesManager.getInstance().setStage(previousStage);
			}
		});
		
		this.ok.addListener(new GameClickListener(null){
			public void clicked (InputEvent event, float x, float y) {
				if(load) DwarvesManager.getInstance().loadGame(list.getSelection());
				else{
					String selection = list.getSelection();
					for(Class<? extends DWLevel> level:Loader.levels){
						if(level.toString().equals(selection)){
							DwarvesManager.getInstance().newGame(level);
							return;
						}
					}
				}
				
			}
		});
	}
	
	private void build() {
		
		this.scrolltable.clear();
		this.scrolltable.defaults().pad(25);
		this.scrolltable.add(this.list);
		this.scrolltable.pack();
		
		this.table.clear();
		this.table.defaults().space(10);
		
		if(load) this.table.add("Chargement d'une partie");
		else  this.table.add("Choix d'un niveau");

		this.table.row();
		this.table.add(this.scroll).width(DwarvesManager.getWidth()/3).height(DwarvesManager.getHeight()/2);
		this.table.row();
		
		Table buttonTable = new Table(DwarvesManager.getSkin());
		buttonTable.defaults().space(25);
		buttonTable.add(ok);
		buttonTable.add(cancel);

		this.table.add(buttonTable);
		this.table.row();
		
		this.table.pack();
		
		this.table.setPosition(DwarvesManager.getWidth()/2-table.getWidth()/2, DwarvesManager.getHeight()/2-table.getHeight()/2);
	}
	
	
}
