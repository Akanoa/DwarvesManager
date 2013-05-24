package enibdevlab.dwarves.controllers.actions.animations;

import com.badlogic.gdx.scenes.scene2d.Action;

import enibdevlab.dwarves.DwarvesManager;


public abstract class VAction extends Action {

	protected DwarvesManager dwarvesManager;

	public VAction(DwarvesManager dwarvesManager){
		this.dwarvesManager = dwarvesManager;
	}
	
}
