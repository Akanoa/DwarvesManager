package enibdevlab.dwarves;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;

public class MainApplet extends LwjglApplet
{
    private static final long serialVersionUID = 1L;
    public MainApplet()
    {
        super(new DwarvesManager(), false);
    }
    
}