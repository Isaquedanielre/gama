/*********************************************************************************************
 *
 *
 * 'IGamaView.java', in plugin 'msi.gama.core', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.common.interfaces;

import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.outputs.IDisplayOutput;

/**
 * @author drogoul
 */
public interface IGamaView {

	public void update(IDisplayOutput output);

	public void addOutput(IDisplayOutput output);

	IDisplayOutput getOutput();

	public void close();

	// public void outputReloaded(IDisplayOutput output);

	public void removeOutput(IDisplayOutput putput);

	/**
	 * For some views (esp. the ones based on Swing), it is necessary to wait a
	 * while after opening it, in order for the view to be fully realized
	 */
	public void waitToBeRealized();

	public void changePartNameWithSimulation(SimulationAgent agent);

	// /**
	// *
	// */
	// public void hideToolbar();
	//
	// /**
	// *
	// */
	// public void showToolbar();

}
