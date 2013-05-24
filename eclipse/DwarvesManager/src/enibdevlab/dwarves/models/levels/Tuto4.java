package enibdevlab.dwarves.models.levels;

import enibdevlab.dwarves.models.Difficulty;
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.scenes.game.GameGui;

public class Tuto4 extends DWLevel {

	public Tuto4(Game game) {
		super(game);
	}

	protected boolean win = false;
	
	@Override
	public String getMapFileName() {
		return "tuto4.tmx";
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
		return "Tutoriel 4";
	}
	
	@Override
	public int getDiamondObjective() {
		return 1;
	}
	
	@Override
	public void setup() {
		Difficulty.setTutorialSettings();
		GameGui.setObjectButton(true);
		GameGui.setRoomButton(true);
		GameGui.setRecruitmentButton(true);
		subventions = 100; // Tous les  minutes, le royaume offre 100 pièces
	}

	@Override
	public void begin() {
		// Argent de départ
		addMoney(2000);
		log("Tutorial Niveau 4");
		
		popup("Tutorial 4", "Vous pouvez gagner de l'argent en \n" +
				"minant certains blocs.\n" +
				"L'argent sert a acheter des meubles, a recruter\n"+
				"et a payer vos nains");
		
		subventions = 100; // Tous les  minutes, le royaume offre 100 pièces
	}

}
