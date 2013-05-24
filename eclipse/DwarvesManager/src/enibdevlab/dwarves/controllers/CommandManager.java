package enibdevlab.dwarves.controllers;

import java.util.ArrayList;

import enibdevlab.dwarves.models.world.MapArea;


/**
 * 
 * Cette classe contr�le la liste des commandes donn�es par le joueur 
 * Liste des zones � miner ... etc
 * 
 * @author Cl�ment Perreau
 *
 */
public class CommandManager {
	
	/**
	 * Liste des zones � miner
	 */
	protected ArrayList<MapArea> toMine; 
	
	/**
	 * Initialise l'objet qui g�re les commandes
	 */
	public CommandManager(){
		this.toMine = new ArrayList<MapArea>();
	}
	
	/**
	 * Ajoute une zone � miner pour les nains
	 */
	public void addAreaToMine(MapArea area){
		this.toMine.add(area);
	}
	
	/**
	 * Enl�ve une des zones � miner
	 */
	public void removeAreaToMine(int i){
		this.toMine.remove(i);
	}
	
}
