package enibdevlab.dwarves.models.levels;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer.Task;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.models.Game;
import enibdevlab.dwarves.views.Loader;
import enibdevlab.dwarves.views.actors.APriceEffect;
import enibdevlab.dwarves.views.audio.SoundManager;
import enibdevlab.dwarves.views.scenes.GameOver;
import enibdevlab.dwarves.views.scenes.Win;

/**
 * 
 * Classe qui représente un niveau du jeu DwarvesManager
 * 
 * On avait à l'origine décidé d'utiliser Jython ou JavaLua pour faire les scripts des niveau en Python
 * ou en Lua.
 * Mais à cause d'Android on est limité (pas de support fiable de Jython,JavaLua ou d'un autre langage
 * de script compatible JVM) sur Android. 
 * Du coup on se contente de l'écrire directement en dur dans des fichiers java.
 * 
 * Ce fichier sert de base pour les classes "scripts" des niveaux
 * On y met aussi quelques fonctions bien pratique pour essayer de former une sorte de mini API pour
 * interagir avec le jeu, qu'on pourra sans doute réutiliser si on arrive à passer plus tard sur un 
 * vrai langage de script.
 * 
 * @author Clément Perreau
 *
 */
public abstract class DWLevel extends Task {

	public abstract String getMapFileName();
	public abstract int    getDifficultyRating();
	public abstract String getMusicName();
	public abstract String getDescription();
	public abstract int getDiamondObjective();
	
	/**
	 * Référence vers le jeu
	 */
	protected Game game;
	
	/**
	 * Interrupteur de victoire/defaite
	 * -1 defaite
	 * 0 en cours
	 * 1 victoire
	 */
	protected int victory;
	
	/**
	 * Subventions journalières du royaume
	 */
	protected int subventions = 0;
	
	/**
	 * Durée d'une journée dans le jeu en secondes
	 */
	protected static int dayDuration = 120;
	
	/**
	 * Progression
	 */
	protected float elapsedTime = 0;
	protected float dayCounter = 0;
	protected boolean finished = false;
	protected int progression = 0;
	protected boolean win = false;
	protected boolean setup = false;
	
	
	// Crée la partie
	public DWLevel(Game game){
		// L'instanciation d'un niveau force le basculement vers la scène
		this.game = game;	
	}
	
	@Override
	public final void run() {
		if(!setup){
			setup();
			setup = true;
		}
		if(elapsedTime == 0){
			begin();
		}
		act();
		elapsedTime ++;
		dayCounter ++;
		testDefeatWin();
		if(dayCounter>=dayDuration){
			this.payday();
			dayCounter = 0;
		}
		if(finished){
			end();
			cancel();
		}
	}
		
	private void testDefeatWin() {
		if(money() < 0){
			finished = true;
		}
		else if(diamonds() >= getDiamondObjective()){
			win = true;
			finished = true;
		}
	}

	/**
	 * Jour de paye, Versement des salaires
	 */
	private void payday() {
		int wages = game.getCharacters().getPayroll();
		
		SoundManager.play("cash");
		this.game.log("--------------------");
		this.game.log("Fin de la journée");
		this.game.log("Versement des salaires : " + Integer.toString(wages));
		this.game.log("Subventions du royaume : +" + Integer.toString(subventions));
		this.game.log("Total : " + Integer.toString(subventions-wages));
		
		this.game.getView().addActor(new APriceEffect(DwarvesManager.getWidth()/2, DwarvesManager.getHeight()/2, subventions-wages));
		this.game.getBank().addMoney(subventions - wages);
	}
	
	
	/**
	 * Choix difficulté / Gui / ...etc (toujours effectué que ce soit au chargement ou au lancement)
	 */
	public void setup(){
		
	}
	
	/**
	 * Exécuté au lancement
	 */
	public void begin(){
		
	}
	
	/**
	 * Executé toutes les secondes
	 */
	public void act(){
		 
	}
	
	public void end() {
		if(win) DwarvesManager.getInstance().setStage(new Win(this.game));
		else DwarvesManager.getInstance().setStage(new GameOver(this.game));
	}
	
	
	/** 
	 * FONCTIONS "API DWM" (Dwarves Manager API)
	 */
	
	public int getSubventions() {
		return subventions;
	}
	public void setSubventions(int subventions) {
		this.subventions = subventions;
	}
	public float getDayCounter() {
		return dayCounter;
	}
	public void setDayCounter(float dayCounter) {
		this.dayCounter = dayCounter;
	}
	public int getProgression() {
		return progression;
	}
	public void setProgression(int progression) {
		this.progression = progression;
	}
	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	/**
	 * Temps écoulé
	 */
	public float getElapsedTime() {
		return elapsedTime;
	}
	
	/**
	 * Retour du temps écoulé sous forme de chaine de caractère
	 */
	public String getFormattedElapsedTime() {
		int seconds = (int) elapsedTime;
		int minutes = (int) Math.floor(elapsedTime/60);
		
		seconds-= minutes*60;
		int hours = minutes/60;
		minutes -= hours*60;
		if(hours > 0){
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
		else return String.format("%02d:%02d", minutes, seconds);
		
	}
	
	/**
	 * Temps avant les prochains salaires formaté
	 */
	public String getFormattedWageTimer() {
		int seconds = (int) (dayDuration - dayCounter);
		int minutes = (int) Math.floor((dayDuration - dayCounter)/60);
		
		seconds-= minutes*60;
		int hours = minutes/60;
		minutes -= hours*60;
		if(hours > 0){
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
		else return String.format("%02d:%02d", minutes, seconds);
	}
	
	/**
	 * Classe du niveau suivant s'il en est un
	 */
	public Class<? extends DWLevel> nextLevel() {
		int index = Loader.levels.indexOf(this.getClass());
		if(index+1 <  Loader.levels.size()){
			return Loader.levels.get(index+1);
		}
		else return Loader.levels.get(0);
	}
	
	/**
	 * Log
	 */
	public void log(String msg){
		game.log(msg);
	}
	
	/**
	 * Efface le Log
	 */
	public void clearLog(String msg){
		game.clearLog();
	}
	
	public void popup(String title, String text){
		game.getView().spawnTextMessageWindow(title, text);
	}
	
	/**
	 * Nb de diamants
	 */
	public int diamonds(){
		return this.game.getBank().getDiamonds();
	}
	
	/**
	 * Argent
	 */
	public int money(){
		return this.game.getBank().getMoney();
	}
	
	public void addMoney(int amount){
		this.game.getBank().addMoney(amount);
	}
	
	/**
	 * Déplace la caméra vers le tile ciblé
	 */
	public void moveCamera(Vector2 position, float zoom){
		System.out.println(position);
		this.game.getView().getGameplayLayer().addAction(
				Actions.scaleTo(zoom, zoom, .5f)
				);
		this.game.getView().getGameplayLayer().addAction(
				Actions.moveTo(-position.x*game.getLevel().getTileXSize()*zoom+DwarvesManager.getWidth()/2,
						   -position.y*game.getLevel().getTileYSize()*zoom+DwarvesManager.getHeight()/2,.5f)
						);
	}
}	
