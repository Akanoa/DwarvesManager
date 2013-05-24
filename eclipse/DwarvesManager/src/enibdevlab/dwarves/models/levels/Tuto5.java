package enibdevlab.dwarves.models.levels;

import enibdevlab.dwarves.models.Difficulty;
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.scenes.game.GameGui;

public class Tuto5 extends DWLevel {

	public Tuto5(Game game) {
		super(game);
	}

	protected boolean win = false;
	
	@Override
	public String getMapFileName() {
		return "tuto5.tmx";
	}

	@Override
	public int getDifficultyRating() {
		return 0;
	}

	@Override
	public String getMusicName() {
		return "music1.mp3";
	}

	@Override
	public String getDescription() {
		return "Tutoriel 5";
	}
	
	@Override
	public int getDiamondObjective() {
		return 3;
	}

	
	@Override
	public void setup() {
		Difficulty.setNormalDifficulty();
		GameGui.setObjectButton(true);
		GameGui.setRoomButton(true);
		subventions = 100; // Tous les  minutes, le royaume offre 100 pièces
	}
	
	@Override
	public void begin() {
		
		game.getBank().addMoney(4500);
		game.log("Tutorial Niveau 5");
		
		popup("Tutorial 5", "Les choses sérieuses commencent !\n" +
							"sachez que chaque nain a des besoins\n"+
							"naturels qu'il faut satisfaire !\n"+
							"Votre mine devra disposer d'un dortoir\n"+
							"et d'une taverne si vous voulez que vos"+
							"nains continuent a travailler.");
		
	}

}
