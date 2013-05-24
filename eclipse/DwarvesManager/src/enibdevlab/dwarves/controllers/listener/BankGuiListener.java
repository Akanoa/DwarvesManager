package enibdevlab.dwarves.controllers.listener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import enibdevlab.dwarves.views.scenes.game.BankGui;

public class BankGuiListener extends ChangeListener {

	
	/**
	 * Gui
	 */
	protected BankGui gui;
	
	public BankGuiListener(BankGui gui){
		this.gui = gui;
	}
	
	@Override
	public void changed(ChangeEvent event, Actor actor) {
		if(actor == gui.getButton()){
			gui.remove();
		}
	}


}
