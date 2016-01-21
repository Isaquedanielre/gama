package ummisco.gama.serializer.gaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import msi.gama.runtime.IScope;
import ummisco.gama.serializer.gamaType.converters.GamaAgentConverter;
import ummisco.gama.serializer.gamaType.converters.GamaMapConverter;
import ummisco.gama.serializer.gamaType.converters.GamaPairConverter;
import ummisco.gama.serializer.gamaType.converters.GamaPointConverter;
import ummisco.gama.serializer.gamaType.converters.GamaShapeConverter;
import ummisco.gama.serializer.gamaType.converters.GamaShapeFileConverter;
import ummisco.gama.serializer.gamaType.converters.LogConverter;
import msi.gama.common.util.FileUtils;
import msi.gama.kernel.experiment.ExperimentAgent;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.metamodel.agent.SavedAgent;
import msi.gama.metamodel.population.SimulationPopulation;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.operator;

public class ReverseOperators {
	@operator(value = "serializeSimulation")
	@doc("")
	public static String serializeSimulation(IScope scope, int i) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new LogConverter());
		xstream.registerConverter(new GamaShapeFileConverter());		
	//	xstream.registerConverter(new GamaShapeConverter());
	//	xstream.registerConverter(new GamaAgentConverter());
	//	xstream.registerConverter(new GamaScopeConverter());
//		xstream.registerConverter(new GamaPointConverter());
//		xstream.registerConverter(new GamaPairConverter());
//		xstream.registerConverter(new GamaMapConverter());
	//	xstream.registerConverter(new GamaSimulationAgentConverter());
		
		ExperimentAgent expAgt = (ExperimentAgent) scope.getExperiment();
		SimulationAgent simAgt = expAgt.getSimulation();
		
		return (String) xstream.toXML(new SavedAgent(scope,simAgt));
	}
	
	@operator(value = "unSerializeSimulation")
	@doc("")
	public static int unSerializeSimulation(IScope scope, String simul) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new LogConverter());	
		xstream.registerConverter(new GamaShapeFileConverter());				
	//	xstream.registerConverter(new GamaShapeConverter());		
	//	xstream.registerConverter(new GamaAgentConverter());
	//	xstream.registerConverter(new GamaScopeConverter());
//		xstream.registerConverter(new GamaPointConverter());
//		xstream.registerConverter(new GamaPairConverter());
//		xstream.registerConverter(new GamaMapConverter());
	//	xstream.registerConverter(new GamaSimulationAgentConverter());
		
		SavedAgent agt = (SavedAgent) xstream.fromXML(simul);

		ExperimentAgent exp = (ExperimentAgent) scope.getExperiment();
		exp.getSimulation().dispose();
		
		SimulationPopulation simPop = exp.getSimulationPopulation();
		agt.restoreTo(scope, simPop);
		
		return 1;
	}
	
	@operator(value = "saveSimulation")
	@doc("")
	public static int saveSimulation(IScope scope, String pathname) {
		String path = FileUtils.constructAbsoluteFilePath(scope, pathname, false);
		//checkValidity(scope);
		
		String simulation = serializeSimulation(scope, 0);
		
		FileWriter fw = null;
		try {
			File f = new File(path);
			if(!f.exists()){
				f.createNewFile();
			}
			fw = new FileWriter(f);
			fw.write(simulation);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return 0;
	}

}