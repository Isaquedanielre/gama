/*********************************************************************************************
 *
 *
 * 'SwitchPerspectiveHandler.java', in plugin 'msi.gama.application', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.gui.swt.commands;

import org.eclipse.core.commands.*;
import msi.gama.runtime.GAMA;

public class SwitchPerspectiveHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		GAMA.getGui().togglePerspective(true);
		return null;
	}
}
