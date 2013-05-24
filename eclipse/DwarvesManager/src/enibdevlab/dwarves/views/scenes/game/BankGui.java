package enibdevlab.dwarves.views.scenes.game;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.controllers.listener.BankGuiListener;
import enibdevlab.dwarves.models.Bank;

public class BankGui extends Window {

	/**
	 * Compte à afficher
	 */
	protected Bank model;
	
	/**
	 * Bouton de fin
	 */
	protected Button button;
	
	/**
	 * Skin
	 */
	protected Skin skin;
	
	/**
	 * 
	 * @param title
	 * @param skin
	 * @param model
	 */
	public BankGui(Bank model) {
		super("Finances", DwarvesManager.getSkin());
		this.skin = DwarvesManager.getSkin();
		this.model = model;
		this.create();
		this.addListener(new BankGuiListener(this));
	}
	
	
	private void create(){
		
		this.button = new TextButton("Ok", skin);
		
		ArrayList<Label> incomes = new ArrayList<Label>();
		ArrayList<Label> expenses = new ArrayList<Label>();
		Label incomesLabel = new Label("Revenus", skin);
		Label expensesLabel = new Label("Depenses", skin);
		incomesLabel.setFontScale(1.25f);
		expensesLabel.setFontScale(1.25f);

		for(String name:model.getIncome().keySet()){
			incomes.add(new Label(name, skin));
			incomes.add(new Label(Integer.toString(model.getIncome().get(name)), skin));
		}
		for(String name:model.getExpense().keySet()){
			expenses.add(new Label(name, skin));
			expenses.add(new Label(Integer.toString(model.getExpense().get(name)), skin));
		}
		
		// Première ligne
		this.defaults().minWidth(150);
		this.defaults().space(10);
		
		this.add(incomesLabel).center();
		this.add().width(1);
		this.add(expensesLabel).center();
		this.add().width(1);
		this.row();
		
		// Séparation
		this.row();
		
		// Tableau
		int i = 0;
		while(true){
			
			if(i*2+1 >= incomes.size() && i*2+1 >= expenses.size()){
				break;
			}
			
			if(i*2+1 < incomes.size()){
				this.add(incomes.get(i*2)).left();;
				this.add(incomes.get(i*2+1)).center();
			}
			else{
				this.add();
				this.add();
			}
			if(i*2+1 < expenses.size()){
				this.add(expenses.get(i*2)).left();;
				this.add(expenses.get(i*2+1)).center();;
			}
			else{
				this.add();
				this.add();
			}
			this.row();
			i++;
		}
		
		this.add();
		this.add();
		this.add(button);
		this.add();
		this.row();
		
		this.pack();
		
	}


	public Button getButton() {
		return button;
	}

	
	
	

}
