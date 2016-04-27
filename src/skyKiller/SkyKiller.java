package skyKiller;

import skyKiller.forms.*;
import skyKiller.forms.Menu;
import java.awt.*;

/**
 * Created by ahmad on 4/16/16.
 */
public class SkyKiller {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Form form = new Menu();
				form.showForm();
			}
		});

	}
}
