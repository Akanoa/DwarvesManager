package enibdevlab.dwarves.models.levels;

import enibdevlab.dwarves.models.Difficulty;
import enibdevlab.dwarves.models.Game;

public class Level1 extends DWLevel {

	protected boolean win = false;
	
	public Level1(Game game) {
		super(game);
	}

	@Override
	public String getMapFileName() {
		return "level1.tmx";
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
		return "Ce niveau d'introduction vous permettra d'appr�hender les m�caniques de jeu de Dwarves Manager";
	}
	
	@Override
	public int getDiamondObjective() {
		return 7;
	}
	
	@Override
	public void setup(){
		Difficulty.setNormalDifficulty();
		subventions = 100; // Tous les  minutes, le royaume offre 100 pi�ces
	};

	@Override
	public void begin() {
		// Argent de d�part
		addMoney(3000);
		log("Niveau 1");
		subventions = 100; // Tous les  minutes, le royaume offre 100 pi�ces
	}

}
