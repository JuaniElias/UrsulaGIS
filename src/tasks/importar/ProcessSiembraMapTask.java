package tasks.importar;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.FeatureReader;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import dao.siembra.SiembraItem;
import dao.siembra.SiembraLabor;
import gov.nasa.worldwind.render.ExtrudedPolygon;
import gui.Messages;
import tasks.ProcessMapTask;
import utils.ProyectionConstants;

public class ProcessSiembraMapTask extends ProcessMapTask<SiembraItem,SiembraLabor> {	
//	private int featureCount;
//	private int featureNumber;

//	private FileDataStore store = null;
//	Quadtree featureTree = null;

	private Double precioPasada;
	private Double precioBolsaSemilla;
	
	//ArrayList<ArrayList<Object>> pathTooltips = new ArrayList<ArrayList<Object>>();
	
	//public Group map = new Group();

	public ProcessSiembraMapTask(SiembraLabor siembra) {
		super(siembra);
//		this.layer=map1;
//		this.store = store;
//		
//		precioPasada = precioPasada1;
//		this.precioBolsaSemilla = precioBolsaSemilla;
		
	}


	public void doProcess() throws IOException {
//		System.out.println("doProcess(); "+System.currentTimeMillis());

			FeatureReader<SimpleFeatureType, SimpleFeature> reader =null;
			//	CoordinateReferenceSystem storeCRS =null;
			if(labor.getInStore()!=null){
				if(labor.outCollection!=null)labor.outCollection.clear();
				reader = labor.getInStore().getFeatureReader();
				//		 storeCRS = labor.getInStore().getSchema().getCoordinateReferenceSystem();
				//convierto los features en cosechas
				featureCount=labor.getInStore().getFeatureSource().getFeatures().size();
			} else{//XXX cuando es una grilla los datos estan en outstore y instore es null
				reader = labor.outCollection.reader();
				//	 storeCRS = labor.outCollection.getSchema().getCoordinateReferenceSystem();
				//convierto los features en cosechas
				featureCount=labor.outCollection.size();
			}


			//initCrsTransform(storeCRS);

			int divisor = 1;

			while (reader.hasNext()) {

				SimpleFeature simpleFeature = reader.next();
				SiembraItem ci = labor.constructFeatureContainer(simpleFeature);
				

				featureNumber++;

				updateProgress(featureNumber/divisor, featureCount);
				Object geometry = ci.getGeometry();

				/**
				 * si la geometria es un point procedo a poligonizarla
				 */
				if (geometry instanceof Point) {
//					Point longLatPoint = (Point) geometry;
	//
//				
//					if(	lastX!=null && labor.getConfiguracion().correccionDistanciaEnabled()){
//						
//						double aMetros=1;// 1/ProyectionConstants.metersToLongLat;
//					//	BigDecimal x = new BigDecimal();
//						double deltaY = longLatPoint.getY()*aMetros-lastX.getY()*aMetros;
//						double deltaX = longLatPoint.getX()*aMetros-lastX.getX()*aMetros;
//						if(deltaY==0.0 && deltaX ==0.0|| lastX.equals(longLatPoint)){
//							puntosEliminados++;
//						//	System.out.println("salteando el punto "+longLatPoint+" porque tiene la misma posicion que el punto anterior "+lastX);
//							continue;//ignorar este punto
//						}
//					
//						double tan = deltaY/deltaX;//+Math.PI/2;
//						rumbo = Math.atan(tan);
//						rumbo = Math.toDegrees(rumbo);//como esto me da entre -90 y 90 le sumo 90 para que me de entre 0 180
//						rumbo = 90-rumbo;
	//
//						/**
//						 * 
//						 * deltaX=0.0 ;deltaY=0.0
//						 *	rumbo1=NaN
//						 *	rumbo0=310.0
//						 */
//					
//						if(rumbo.isNaN()){//como el avance en x es cero quiere decir que esta yerndo al sur o al norte
//							if(deltaY>0){
//								rumbo = 0.0;
//							}else{
//								rumbo=180.0;
//							}
//						}
	//
//						if(deltaX<0){//si el rumbo estaba en el cuadrante 3 o 4 sumo 180 para volverlo a ese cuadrante
//							rumbo = rumbo+180;
//						}
//						ci.setRumbo(rumbo);
	//
//					}
//				
//					lastX=longLatPoint;
//					Double alfa  = Math.toRadians(rumbo) + Math.PI / 2;
	//
//					// convierto el ancho y la distancia a verctores longLat poder
//					// operar con la posicion del dato
//					Coordinate anchoLongLat = constructCoordinate(alfa,ancho);
//					Coordinate distanciaLongLat = constructCoordinate(alfa+ Math.PI / 2,distancia);
	//
	//
//					if(labor.getConfiguracion().correccionDemoraPesadaEnabled()){
//						Double corrimientoPesada =	labor.getConfiguracion().getCorrimientoPesada();
//						Coordinate corrimientoLongLat =constructCoordinate(alfa + Math.PI / 2,corrimientoPesada);
//						// mover el punto 3.5 distancias hacia atras para compenzar el retraso de la pesada
	//
//						longLatPoint = longLatPoint.getFactory().createPoint(new Coordinate(longLatPoint.getX()+corrimientoPesada*distanciaLongLat.x,longLatPoint.getY()+corrimientoPesada*distanciaLongLat.y));
//						//utmPoint = utmPoint.getFactory().createPoint(new Coordinate(utmPoint.getX()-corrimientoLongLat.x,utmPoint.getY()-corrimientoLongLat.y));
//					}
	//
//					/**
//					 * creo la geometria que corresponde a la feature tomando en cuenta si esta activado el filtro de distancia y el de superposiciones
//					 */				
//					//				Geometry utmGeom = createGeometryForHarvest(anchoLongLat,
//					//						distanciaLongLat, utmPoint,pasada,altura,ci.getRindeTnHa());		
//					Geometry longLatGeom = createGeometryForHarvest(anchoLongLat,
//							distanciaLongLat, longLatPoint,pasada,altura,ci.getRindeTnHa());
//					if(longLatGeom == null 
//							//			|| geom.getArea()*ProyectionConstants.A_HAS*10000<labor.config.supMinimaProperty().doubleValue()
//							){//con esto descarto las geometrias muy chicas
//						//System.out.println("geom es null, ignorando...");
//						continue;
//					}
	//
//					/**
//					 * solo ingreso la cosecha al arbol si la geometria es valida
//					 */
//					boolean empty = longLatGeom.isEmpty();
//					boolean valid = longLatGeom.isValid();
//					boolean big = (longLatGeom.getArea()*ProyectionConstants.A_HAS>supMinimaHas);
//					if(!empty
//							&&valid
//							&&big//esta fallando por aca
//							){
	//
//						//Geometry longLatGeom =	crsAntiTransform(utmGeom);//hasta aca se entrega la geometria correctamente
	//
//						ci.setGeometry(longLatGeom);//FIXME aca ya perdio la orientacion pero tiene la forma correcta
//					//	corregirRinde(ci);
	//
//						labor.insertFeature(ci);//featureTree.insert(geom.getEnvelopeInternal(), cosechaFeature);
//					} else{
//						//	System.out.println("no inserto el feature "+featureNumber+" porque tiene una geometria invalida empty="+empty+" valid ="+valid+" area="+big+" "+geom);
//					}

				} else { // no es point. Estoy abriendo una cosecha de poligonos.
					labor.insertFeature(ci);
				}

			}// fin del for que recorre las cosechas por indice
			reader.close();
		
		List<SiembraItem> itemsToShow = new ArrayList<SiembraItem>();
		SimpleFeatureIterator it = labor.outCollection.features();
		while(it.hasNext()){
			SimpleFeature f=it.next();
			itemsToShow.add(labor.constructFeatureContainerStandar(f,false));
		}
		it.close();
		labor.constructClasificador();
		runLater(itemsToShow);
		updateProgress(0, featureCount);

	}
	
//	public ExtrudedPolygon  getPathTooltip( Geometry poly,SiembraItem siembraFeature) {		
//		double area = poly.getArea() *ProyectionConstants.A_HAS();// 30224432.818;//pathBounds2.getHeight()*pathBounds2.getWidth();
//		DecimalFormat df = new DecimalFormat("#.00"); 
//		String tooltipText = new String(
//				"Densidad: "+ df.format(siembraFeature.getDosisHa()) + " Kg/Ha\n\n"
//				+"Costo: " + df.format(siembraFeature.getImporteHa()) + " U$S/Ha\n"				
//			//	+"Sup: " +  df.format(area*ProyectionConstants.METROS2_POR_HA) + " m2\n"
//		//		+"feature: " + featureNumber						
//		);
//		
//		if(area<1){
//			tooltipText=tooltipText.concat( "Sup: "+df.format(area * ProyectionConstants.METROS2_POR_HA) + "m2\n");
//		} else {
//			tooltipText=tooltipText.concat("Sup: "+df.format(area ) + "Has\n");
//		}
//		return super.getExtrudedPolygonFromGeom(poly, siembraFeature,tooltipText);	
//	}
	
	@Override
	public ExtrudedPolygon  getPathTooltip( Geometry poly,SiembraItem siembraFeature) {		
		double area = poly.getArea() *ProyectionConstants.A_HAS();// 30224432.818;//pathBounds2.getHeight()*pathBounds2.getWidth();
		DecimalFormat df = new DecimalFormat("0.00");//$NON-NLS-2$
		
		String tooltipText = new String(Messages.getString("ProcessSiembraMapTask.1")+ df.format(siembraFeature.getDosisML()) + Messages.getString("ProcessSiembraMapTask.2")); //$NON-NLS-1$ //$NON-NLS-2$
		tooltipText=tooltipText.concat(Messages.getString("ProcessSiembraMapTask.3") + df.format(siembraFeature.getDosisHa()) + Messages.getString("ProcessSiembraMapTask.4")); //$NON-NLS-1$ //$NON-NLS-2$
		
		tooltipText=tooltipText.concat( Messages.getString("ProcessSiembraMapTask.5") + df.format(siembraFeature.getDosisFertLinea()) + Messages.getString("ProcessSiembraMapTask.6")		); //$NON-NLS-1$ //$NON-NLS-2$
		tooltipText=tooltipText.concat( Messages.getString("ProcessSiembraMapTask.7") + df.format(siembraFeature.getImporteHa()) + Messages.getString("ProcessSiembraMapTask.8")		); //$NON-NLS-1$ //$NON-NLS-2$
		
//		String tooltipText = new String(
//				"Densidad: "+ df.format(siembraFeature.getDosisHa()) + " Kg/Ha\n"								
//				);
//		tooltipText=tooltipText.concat( "Fert: " + df.format(siembraFeature.getDosisFertLinea()) + " Kg/Ha\n"		);
//		tooltipText=tooltipText.concat( "Costo: " + df.format(siembraFeature.getImporteHa()) + " U$S/Ha\n"		);

		if(area<1){
			tooltipText=tooltipText.concat( Messages.getString("ProcessSiembraMapTask.9")+df.format(area * ProyectionConstants.METROS2_POR_HA) + Messages.getString("ProcessSiembraMapTask.10")); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			tooltipText=tooltipText.concat(Messages.getString("ProcessSiembraMapTask.11")+df.format(area ) + Messages.getString("ProcessSiembraMapTask.12")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return super.getExtrudedPolygonFromGeom(poly, siembraFeature,tooltipText);	
	}
	
	protected  int getAmountMin(){return 0;} 
	protected  int gerAmountMax() {return 1;} 
	
}// fin del task

