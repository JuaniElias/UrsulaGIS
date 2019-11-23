package tasks.crear;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.geotools.data.FeatureReader;
import org.geotools.data.shapefile.shp.JTSUtilities;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.BoundingBox;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.precision.EnhancedPrecisionOp;

import dao.Labor;
import dao.LaborItem;
import dao.Poligono;
import dao.cosecha.CosechaConfig;
import dao.cosecha.CosechaItem;
import dao.cosecha.CosechaLabor;
import dao.fertilizacion.FertilizacionItem;
import dao.fertilizacion.FertilizacionLabor;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.ExtrudedPolygon;
import javafx.geometry.Point2D;
import tasks.ProcessMapTask;
import utils.ProyectionConstants;

public class CrearFertilizacionMapTask extends ProcessMapTask<FertilizacionItem,FertilizacionLabor> {
	Double amount = new Double(0);
	Poligono poli=null;

	public CrearFertilizacionMapTask(FertilizacionLabor labor,Poligono _poli,Double _amount){//RenderableLayer layer, FileDataStore store, double d, Double correccionRinde) {
		super(labor);
		amount=_amount;
		poli=_poli;

	}

	public void doProcess() throws IOException {
		FertilizacionItem ci = new FertilizacionItem();
		ci.setDosistHa(amount);
//		ci.setPrecioInsumo(labor.precioInsumoProperty.get());
//		ci.setCostoLaborHa(labor.precioLaborProperty.get());
		labor.setPropiedadesLabor(ci);
		GeometryFactory fact = new GeometryFactory();
		List<? extends Position> positions = poli.getPositions();
		Coordinate[] coordinates = new Coordinate[positions.size()];
		for(int i=0;i<positions.size();i++){
			Position p = positions.get(i);	
			Coordinate c = new Coordinate(p.getLongitude().getDegrees(),p.getLatitude().getDegrees(),p.getElevation());
			
			coordinates[i]=c;
		}
		coordinates[coordinates.length-1]=coordinates[0];//en caso de que la geometria no este cerrada
		Polygon poly = fact.createPolygon(coordinates);	

		ci.setGeometry(poly);
		
		labor.insertFeature(ci);
				
		labor.constructClasificador();

		List<FertilizacionItem> itemsToShow = new ArrayList<FertilizacionItem>();
		itemsToShow.add(ci);
		runLater(itemsToShow);
		updateProgress(0, featureCount);

	}


	@Override
	protected ExtrudedPolygon getPathTooltip(Geometry poly, FertilizacionItem fertFeature) {

		double area = poly.getArea() * ProyectionConstants.A_HAS();// 30224432.818;//pathBounds2.getHeight()*pathBounds2.getWidth();
		//double area2 = cosechaFeature.getAncho()*cosechaFeature.getDistancia();
		DecimalFormat df = new DecimalFormat("0.00");//$NON-NLS-2$

		String tooltipText = new String(// TODO ver si se puede instalar un
				// boton
				// que permita editar el dato
				"Densidad: " + df.format(fertFeature.getDosistHa())
				+ " Kg/Ha\n" + "Costo: "
				+ df.format(fertFeature.getImporteHa()) + " U$S/Ha\n"
				//+ "Sup: "
				//+ df.format(area * ProyectionConstants.METROS2_POR_HA)
				//+ " m2\n"
				// +"feature: " + featureNumber
				);
		if(area<1){
			tooltipText=tooltipText.concat( "Sup: "+df.format(area * ProyectionConstants.METROS2_POR_HA) + "m2\n");
			//	tooltipText=tooltipText.concat( "SupOrig: "+df.format(area2 ) + "m2\n");
		} else {
			tooltipText=tooltipText.concat("Sup: "+df.format(area ) + "Has\n");
		}

		//List  paths = 
	 return	getExtrudedPolygonFromGeom(poly, fertFeature,tooltipText);

		//return null;
	}

	protected int getAmountMin() {
		return 3;
	}

	protected int gerAmountMax() {
		return 15;
	}
}// fin del task