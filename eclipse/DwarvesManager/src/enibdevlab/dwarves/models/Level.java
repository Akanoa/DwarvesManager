package enibdevlab.dwarves.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.utils.XmlReader.Element;

import enibdevlab.dwarves.DwarvesManager;
import enibdevlab.dwarves.models.levels.DWLevel;
import enibdevlab.dwarves.models.misc.IPersistent;
import enibdevlab.dwarves.views.world.TileMap;

/**
 * 
 * Un niveau du jeu
 * 
 * @author Clément Perreau
 *
 */
public class Level implements IPersistent {
	
	/**
	 * Nom du niveau
	 */
	protected String name; 
	
	/**
	 * 'Map' du monde
	 */
	protected TileMap tilemap;

	/**
	 * Jeu
	 */
	protected Game game;
	
	/**
	 * Script
	 */
	protected DWLevel dwScript;
	
	/**
	 * Constructeur
	 * @param path Chemin du fichier à charger
	 */
	public Level(Game game, Class<? extends DWLevel> script){
		this.game = game;
		try {
			dwScript = (DWLevel)(script.getConstructor(Game.class).newInstance(game));
			DwarvesManager.timer.scheduleTask(dwScript, .5f, 1f);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
	}


	public Level(Game game, Element xmlElement) {
		this.game = game;
		this.name = xmlElement.getName();
		this.loadFromXmlElement(xmlElement);
		DwarvesManager.timer.scheduleTask(dwScript, .5f, 1f);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TileMap getTilemap() {
		return tilemap;
	}

	public void setTilemap(TileMap tilemap) {
		this.tilemap = tilemap;
	}

	public Game getGame() {
		return game;
	}

	public float getTileXSize() {
		return this.tilemap.getTileWidth();
	}
	
	public float getTileYSize() {
		return this.tilemap.getTileHeight();
	}

	public DWLevel getDwScript() {
		return dwScript;
	}

	@Override
	public Element saveAsXmlElement() {
		Element output = new Element("Level", null);
		output.setAttribute("name", name);
		
		Element script = new Element("script", null);
		script.setAttribute("level", dwScript.getClass().getName());
		script.setAttribute("progression", Integer.toString(dwScript.getProgression()));
		script.setAttribute("elapsedTime", Float.toString(dwScript.getElapsedTime()));
		script.setAttribute("dayCounter", Float.toString(dwScript.getDayCounter()));
		
		output.addChild(script);
		output.addChild(this.tilemap.saveAsXmlElement());
		return output;
	}

	@Override
	public void loadFromXmlElement(Element xmlElement) {
		
		Element script = xmlElement.getChildByName("script");
		Class<?> classType;
		try {
			classType = Class.forName(script.getAttribute("level"));
			try {
				Constructor<?> constructor = classType.getConstructor(new Class<?>[]{Game.class});
				this.dwScript = (DWLevel) constructor.newInstance(game);
				this.dwScript.setElapsedTime(Float.parseFloat(script.getAttribute("elapsedTime")));
				this.dwScript.setDayCounter(Float.parseFloat(script.getAttribute("dayCounter")));
				this.dwScript.setProgression(Integer.parseInt(script.getAttribute("progression")));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
}
