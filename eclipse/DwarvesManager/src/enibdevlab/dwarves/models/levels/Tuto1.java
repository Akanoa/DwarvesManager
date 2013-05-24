package enibdevlab.dwarves.models.levels;

//import com.badlogic.gdx.math.Vector2;

//import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.models.Difficulty;
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.scenes.game.GameGui;

public class Tuto1 extends DWLevel {

	private boolean popup2 = false;
	
	public Tuto1(Game game) {
		super(game);
	}
	
	@Override
	public String getMapFileName() {
		return "tuto1.tmx";
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
		return "Tutoriel 1";
	}
	
	@Override
	public int getDiamondObjective() {
		return 2;
	}

	
	@Override
	public void setup() {
		Difficulty.setTutorialSettings();
		GameGui.setObjectButton(false);
		GameGui.setRoomButton(false);
		GameGui.setRecruitmentButton(true);
	}
	
	@Override
	public void begin() {
		// Argent de départ
		addMoney(2000);
		log("Tutorial Niveau 1");
		popup("Tutorial 1", "Bienvenue dans Dwarves Manager. \n" +
							"Le but de ce jeu est de miner tous les diamants\n" +
							"d'un niveau.\n" +
							"Pour cela vous devez recruter un nain et lui donner\n" +
							"l'ordre de miner");
		subventions = 100; // Tous les  minutes, le royaume offre 100 pièces
		
	}

	@Override
	public void act() {

		if(diamonds() == 1 && !popup2){
			popup("Tutorial 1", "Bravo ! \n" +
					"Vous avez pris votre premier diamant !\n" +
					"Maintenant il faut aller chercher le second ! \n" +
					"");
			popup2 = true;
		}
		
		//moveCamera(new Vector2(DwarvesManager.random.nextInt(25), DwarvesManager.random.nextInt(25)), (DwarvesManager.random.nextFloat()*3f+0.3f));
			
	}




}
