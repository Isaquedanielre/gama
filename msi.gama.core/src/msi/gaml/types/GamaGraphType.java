/*
 * GAMA - V1.4 http://gama-platform.googlecode.com
 * 
 * (c) 2007-2011 UMI 209 UMMISCO IRD/UPMC & Partners (see below)
 * 
 * Developers :
 * 
 * - Alexis Drogoul, UMI 209 UMMISCO, IRD/UPMC (Kernel, Metamodel, GAML), 2007-2012
 * - Vo Duc An, UMI 209 UMMISCO, IRD/UPMC (SWT, multi-level architecture), 2008-2012
 * - Patrick Taillandier, UMR 6228 IDEES, CNRS/Univ. Rouen (Batch, GeoTools & JTS), 2009-2012
 * - Beno�t Gaudou, UMR 5505 IRIT, CNRS/Univ. Toulouse 1 (Documentation, Tests), 2010-2012
 * - Phan Huy Cuong, DREAM team, Univ. Can Tho (XText-based GAML), 2012
 * - Pierrick Koch, UMI 209 UMMISCO, IRD/UPMC (XText-based GAML), 2010-2011
 * - Romain Lavaud, UMI 209 UMMISCO, IRD/UPMC (RCP environment), 2010
 * - Francois Sempe, UMI 209 UMMISCO, IRD/UPMC (EMF model, Batch), 2007-2009
 * - Edouard Amouroux, UMI 209 UMMISCO, IRD/UPMC (C++ initial porting), 2007-2008
 * - Chu Thanh Quang, UMI 209 UMMISCO, IRD/UPMC (OpenMap integration), 2007-2008
 */
package msi.gaml.types;

import java.util.Map;

import msi.gama.common.interfaces.IValue;
import msi.gama.metamodel.topology.graph.GamaSpatialGraph;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.*;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.*;
import msi.gama.util.graph.*;
import msi.gaml.expressions.VariableExpression;
import msi.gaml.operators.Cast;

@type(value = IType.GRAPH_STR, id = IType.GRAPH, wraps = { IGraph.class }, kind = IVariableKind.CONTAINER)
public class GamaGraphType extends GamaType<IGraph> {

	@Override
	public IGraph cast(final IScope scope, final Object obj, final Object param)
		throws GamaRuntimeException {
		return staticCast(scope, obj, param);
	}

	public static IGraph staticCast(final IScope scope, final Object obj, final Object param) {
		// param = true : spatial.

		if ( obj == null ) { return null; }
		if ( obj instanceof IGraph ) { return (IGraph) obj; }
		boolean spatial = param != null && Cast.asBool(scope, param);
		
		if ( obj instanceof IList ) { return from((IList) obj, spatial); }
		// List of agents, geometries...
		
		if ( obj instanceof VariableExpression) { // this may be a variable ?
			// in this case, attempt to decode it !
			return (IGraph)(((VariableExpression)obj).value(scope)); 
		}
		
		if ( obj instanceof Map ) { return from((Map) obj, spatial); }
		// TODO Matrix, Pair ?
		
	
		//throw new GamaRuntimeException("Unable to cast "+obj+" as a graph");
		return null;
	}

	public static IGraph from(final Map<?, ?> obj, final boolean spatial) {
		IGraph result =
			spatial ? new GamaSpatialGraph(new GamaList(), false, false, null) : new GamaGraph(
				new GamaList(), false, false);
		GamaPair p = new GamaPair(null, null);
		for ( Map.Entry<?, ?> k : obj.entrySet() ) {
			p.key = k.getKey();
			p.value = k.getValue();
			result.addEdge(p);
		}
		return result;
	}

	public static IGraph from(final IList obj, final boolean spatial) {
		return spatial ? new GamaSpatialGraph(obj, false, false, null) : new GamaGraph(obj, false,
			false);
	}

	@Override
	public IGraph getDefault() {
		return null;
	}

	@Override
	public IType defaultContentType() {
		return Types.get(NONE);
	}

	public static IGraph asDirectedGraph(final IGraph source) {
		source.setDirected(true);
		return source; // TODO Clone ?
	}

	public static IGraph asUndirectedGraph(final IGraph source) {
		source.setDirected(false);
		return source; // TODO Clone ?
	}

	// public static IGraph asSpatialGraph(final IGraph source) {
	// if ( source.isSpatial() ) { return source; }
	// IGraph destination = new GamaSpatialGraph(GamaList.EMPTY_LIST, true, false, null);
	// Graphs.addGraph(destination, source);
	// return destination;
	// }
	//
	// public static IGraph asRegularGraph(final IGraph source) {
	// if ( !source.isSpatial() ) { return source; }
	// IGraph destination = new GamaGraph(GamaList.EMPTY_LIST, true, false);
	// Graphs.addGraph(destination, source);
	// return destination;
	// }

}
