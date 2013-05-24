


import enibdevlab.dwarves.controllers.cloud.MySftp;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MySftp sftp = new MySftp("rezid.org", "dwarf", "dwarf");
		
		try {
			sftp.download(System.getProperty("user.home")+"\\Downloads\\", "saves/1/PartieEnCours.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("fin");
		
		
	}
	

}
