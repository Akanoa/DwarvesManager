package enibdevlab.dwarves.models.levels;

import enibdevlab.dwarves.models.Difficulty;
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.scenes.game.GameGui;

public class Tuto2 extends DWLevel {

	protected boolean win = false;
	
	public Tuto2(Game game) {
		super(game);
	}

	@Override
	public String getMapFileName() {
		return "tuto2.tmx";
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
		return "Tutoriel 2";
	}
	
	@Override
	public int getDiamondObjective() {
		return 2;
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
		
		addMoney(10000);
		
		log("Tutorial Niveau 2");
		
		popup("Tutorial 2", "Au bout d'une certaine distance parcourue\n" +
							"dans la roche, il peut arriver que la pioche\n" +
							"d'un mineur se casse. Vous devrez alors recruter\n" +
							"un nouveau mineur, ou construire un atelier et\n" +
							"recruter un nain artisan pour lui fabriquer une\n" +
							"nouvelle pioche.");
		
	}



}
