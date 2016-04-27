package skyKiller.events;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by ahmad on 4/21/16.
 */
public class ExplodeBombEvent extends ComponentEvent {
	private int x;
	private int y;
	public ExplodeBombEvent(Component component, int i,int x, int y){
		super(component,i);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
