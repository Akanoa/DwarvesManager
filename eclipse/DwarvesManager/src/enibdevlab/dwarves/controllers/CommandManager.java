package enibdevlab.dwarves.controllers;

import java.util.ArrayList;

import enibdevlab.dwarves.models.world.MapArea;


/**
 * 
 * Cette classe contrôle la liste des commandes données par le joueur 
 * Liste des zones à miner ... etc
 * 
 * @author Clément Perreau
 *
 */
public class CommandManager {
	
	/**
	 * Liste des zones à miner
	 */
	protected ArrayList<MapArea> toMine; 
	
	/**
	 * Initialise l'objet qui gère les commandes
	 */
	public CommandManager(){
		this.toMine = new ArrayList<MapArea>();
	}
	
	/**
	 * Ajoute une zone à miner pour les nains
	 */
	public void addAreaToMine(MapArea area){
		this.toMine.add(area);
	}
	
	/**
	 * Enlève une des zones à miner
	 */
	public void removeAreaToMine(int i){
		this.toMine.remove(i);
	}
	
}
