package uk.co.jacobusmeulen.minerunner.html;

import uk.co.jacobusmeulen.minerunner.core.Run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class RunHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Run();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
