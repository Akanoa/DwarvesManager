package enibdevlab.dwarves.models.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Une bi�re 
 * 
 * @author Cl�ment Perreau
 *
 */
public class Beer extends Item {
	
	/**
	 * Quantit� restante dans la bi�re
	 */
	protected int quantity = 4;

	public Beer(Vector2 pos) {
		super(pos);
	}

	@Override
	public String getName() {
		return "Bi�re";
	}

	@Override
	public int getTextureId() {
		return 8;
	}

	public boolean isEmpty() {
		return(quantity<=0);
	}
	
	public void onUse(){
		this.quantity --;
	}



}
