package enibdevlab.dwarves.views.scenes;

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
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.actors.AImage;
import enibdevlab.dwarves.views.audio.MusicManager;
import enibdevlab.dwarves.views.scenes.game.GameScene;

public class Win extends Stage{

	protected Game game;
	protected static TextureRegion img = new TextureRegion(new Texture("data/sprites/victory.png"));

	public Win(Game game){
		this.game = game;
		
		MusicManager.stop();
		
		AImage win = new AImage(img, DwarvesManager.getWidth()/2,
				  					 DwarvesManager.getHeight()/2);
		win.setScale(0.1f);
		win.addAction(Actions.sequence(
				Actions.parallel(
						Actions.scaleTo(2f,2f,1f),
						Actions.rotateTo(360, 1f)
						),
				Actions.parallel(
						Actions.scaleTo(1f, 1f, .5f),
						Actions.rotateTo(375, .5f)
						),
				Actions.rotateTo(345, 1f),
				Actions.rotateTo(360, .5f)
				));
		this.addActor(win);
		
		Table table = new Table(DwarvesManager.getSkin());
		
		ImageButton menu =      new ImageButton(new TextureRegionDrawable(MainMenu.buttonsImg.getTile(13)),
                				new TextureRegionDrawable(MainMenu.buttonsImg.getTile(15)));
		ImageButton next = 		new ImageButton(new TextureRegionDrawable(MainMenu.buttonsImg.getTile(9)),
                				new TextureRegionDrawable(MainMenu.buttonsImg.getTile(11)));
		
		table.defaults().space(30);
		table.add("Votre temps : " + game.getLevel().getDwScript().getFormattedElapsedTime());
		table.row();
		table.add(menu);
		table.add(next);
		table.pack();
		table.setPosition(DwarvesManager.getWidth()/2-table.getWidth()/2, table.getHeight());
		this.addActor(table);
		
		menu.addListener(new GameClickListener(game) {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dw.setStage(new MainMenu(DwarvesManager.getInstance()));
				super.clicked(event, x, y);
			}
		});
		
		next.addListener(new GameClickListener(game) {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dw.setStage(new GameScene(dw, game.getLevel().getDwScript().nextLevel()));
				super.clicked(event, x, y);
			}
		});
		
		this.addAction(Actions.color(new Color(0,0,0,0)));
		this.addAction(Actions.color(new Color(1,1,1,1), 2f));
		
	}
	
	
	
}
