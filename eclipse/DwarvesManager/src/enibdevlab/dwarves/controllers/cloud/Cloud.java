package enibdevlab.dwarves.controllers.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Gestion des sauvegardes d'un joueur sur un cloud
 * @author Yannick
 *
 */
public class Cloud {
	
	/**
	 * Instance unique
	 */
	public static Cloud instance;
	
	/**
	 * gestion des accès sftp sur le serveur du cloud
	 */
	protected  MySftp sftp;
	
	/**
	 * nom de l'utilisateur
	 */
	protected String userName="";
	
	/**
	 * Instancie un cloud
	 */
	public Cloud(){
		
		instance = this;
		
		XmlReader reader = new XmlReader();
		FileHandle file = Gdx.files.internal("data/sftpConf.xml");
		try {
			Element tree = reader.parse(file);
			String host = tree.getChildByName("host").getText();
			String user = tree.getChildByName("user").getText();
			String pass = tree.getChildByName("pass").getText();
			
			sftp = new MySftp(host, user, pass);
			
			System.out.println(host);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void init(){
		new Cloud();
	}
	
	/**
	 * parse les données reçu en http
	 * @param param paramètre de service
	 * @return l'http brute
	 * @throws Exception
	 */
	protected  Vector<String> urlParse(String param)throws Exception{
		URL data = new URL("http://rezid.org/dwarves/index.php?"+param);
		URLConnection yc = data.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		
		Vector<String> lines = new Vector<String>();
		
		while((inputLine = in.readLine()) != null){
			lines.add(inputLine);
		}
		in.close();
		
		return lines;
	}
	
	
	/**
	 * récupère l'identifiant de l'utilisateur
	 * @param user
	 * @return retourne son id si l'utilisateur existe, 0 sinon
	 * @throws Exception
	 */
	protected  int getIdUser(String user) throws Exception{
		Vector<String> lines = urlParse("service=id&name="+user);
		
		int id = Integer.parseInt(lines.get(0).trim());
		
		if(id != 0){
			return id;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * récupère le mot de passe 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected  String getPassUser(String user) throws Exception{
		Vector<String> lines = urlParse("service=pass&id="+getIdUser(user));
		
		if(lines.get(0).trim().equals("0") ){
			return "";
		}
		else{
			return lines.get(0).trim();
		}
	}
	
	/**
	 * applique l'algorithme de cryptage sha1 sur le mot de passe en clair
	 * @param plaintext mot de passe en clair
	 * @return le mot de passe crypté
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
    protected  String hash(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	MessageDigest crypt = MessageDigest.getInstance("SHA-1");
    	crypt.reset();
    	crypt.update(plaintext.getBytes());
    	return new BigInteger(1, crypt.digest()).toString(16);
    }
	
	/**
	 * sauvegarde un fichier dans le cloud
	 * @param file nom de la sauvegarde
	 * @param user nom du propriètaire de la sauvegarde
	 * @return
	 * @throws Exception
	 */
	public boolean save(FileHandle file, String user)  {
		
		if(!sftp.isConnected()) return false;
		
		int id = 0;
		try {
			id = getIdUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id != 0){
			sftp.upload(file.file().getAbsoluteFile().toString(), "saves/"+Integer.toString(id)+"/"+file.name());	
		}
		
		return true;
	}
	
	/**
	 * authentification de l'utilisateur
	 * @param user nom de l'utilisateur voulant se connecter
	 * @return retourne faux, si l'utilisateur n'existe pas sinon retourne vrai
	 * @throws Exception
	 */
	public  boolean connection(String user, String password){
		//Si l'utilisateur existe et a le bon mot de passe
		try {
			if(getIdUser(user)!=0 && hash(password).endsWith(getPassUser(user))){
				this.userName=user;
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return false;
	}
	
	
	/**
	 * sauvegarde une partie contenue dans le cloud
	 * @param file
	 * @param user
	 * @return retourne faux si le cloud est innacessible
	 * @throws Exception
	 */
	public boolean load(String file, String user){
		
		if(!sftp.isConnected()) return false;
		
		int id = 0;
		try {
			id = getIdUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		String src = "saves/"+Integer.toString(id)+"/"+file;
		String dest =  Gdx.files.external("DwarvesManager/Saves/"+file).file().getAbsoluteFile().toString();
		try {
			sftp.download(dest, src);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	
	/**
	 * enregistre un utilisateur sur le cloud
	 * @param name nom choisi par l'utilisateur
	 * @return vrai si l'inscription s'est correctement déroulée, faux s'il y a eu une erreur
	 * @throws Exception
	 */
	public  boolean suscribe(String name, String password){
		
		Vector<String> lines = new Vector<String>();
		try {
			lines = urlParse("service=subscribe&name="+name+"&password="+hash(password));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(lines.isEmpty()) return false;
		
		if(Integer.parseInt(lines.get(0).trim()) == 1){
			return false;
		}
		else{
			this.userName=name;
			return true;
		}
	}
	
	/**
	 * récupère la liste des savegarde associée à l'utilisateur sur le cloud
	 * @param user
	 * @return retourne la liste des sauvegardes de l'utilisateur
	 * @throws Exception
	 */
	public Vector<String> getSaves(String user){
		
		Vector<String> lines = new Vector<String>();
		try {
			lines = urlParse("service=listsaves&id="+Integer.toString(getIdUser(user)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(lines.isEmpty()) return lines;
		
		lines= new Vector<String>(Arrays.asList(lines.get(0).split("<br />")));
		
		return lines;
	}
	
	/**
	 * permet de vérifier si le cloud est en ligne
	 * @return
	 */
	public boolean isOnline(){
		return sftp.isConnected();
	}
	
	/**
	 * retourne le nom de l'utilisateur connecté
	 * @return s'il n'est pas connecté renvoie une chaîne vide
	 */
	public String getUserName(){
		return userName;
	}
	
	public void close(){
		sftp.disconnect();
	}

}