/*********************************************************************************************
 * 
 * 
 * 'GamaAgentConverter.java', in plugin 'ummisco.gama.communicator', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package ummisco.gama.serializer.gamaType.converters;

import msi.gama.kernel.experiment.ExperimentAgent;
import msi.gama.metamodel.agent.GamlAgent;
import msi.gama.metamodel.shape.GamaShape;
import ummisco.gama.serializer.gamaType.reduced.RemoteAgent;

import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class GamaAgentConverter implements Converter {

	@Override
	public boolean canConvert(final Class arg0) {
		return (arg0.equals(GamaShape.class));
	}

	@Override
	public void marshal(final Object arg0, final HierarchicalStreamWriter writer, final MarshallingContext context) {
		GamaShape agt = (GamaShape) arg0;
		writer.startNode("shape shape");
		
		System.out.println("ConvertAnother : AgentConverter " + agt.getClass());		
	// 	context.convertAnother(new RemoteAgent(agt));
		System.out.println("===========END ConvertAnother : GamaShape");
		
		writer.endNode();
	}

	@Override
	public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext arg1) {

		reader.moveDown();
		// RemoteAgent rmt = (RemoteAgent) arg1.convertAnother(null, RemoteAgent.class);
		reader.moveUp();
		return new GamaShape();// rmt; // ragt;
	}

}