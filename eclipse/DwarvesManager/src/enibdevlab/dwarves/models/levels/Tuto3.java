package enibdevlab.dwarves.models.levels;

import enibdevlab.dwarves.models.Difficulty;
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.scenes.game.GameGui;

public class Tuto3 extends DWLevel {

	public Tuto3(Game game) {
		super(game);
	}

	protected boolean win = false;
	
	@Override
	public String getMapFileName() {
		return "tuto3.tmx";
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
		return "Tutoriel 3";
	}
	
	@Override
	public int getDiamondObjective() {
		return 1;
	}
	
	@Override
	public void setup() {
		Difficulty.setTutorial2Settings();
		GameGui.setObjectButton(true);
		GameGui.setRoomButton(true);
		GameGui.setRecruitmentButton(true);
		subventions = 100; // Tous les  minutes, le royaume offre 100 pièces
	}

	@Override
	public void begin() {
		// Argent de départ
		game.getBank().addMoney(3000);
		
		game.log("Tutorial Niveau 3");
		
		popup("Tutorial 3", "Certains blocs sont plus difficiles \n" +
				"a miner que d'autres.\n" +
				"Choissisez le chemin le plus rapide !\n");
	}

}
