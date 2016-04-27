package skyKiller.events;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by ahmad on 4/21/16.
 */
public class ExplodeJetEvent extends ComponentEvent {
	private int x;
	private int y;

	public ExplodeJetEvent(Component component, int i, int x, int y) {
		super(component, i);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
