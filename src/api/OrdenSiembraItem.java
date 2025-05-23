package api;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

import dao.ordenCompra.Producto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import utils.JsonUtil.Exclude;

@Getter
@Setter(value = AccessLevel.PUBLIC)
@Entity @Access(AccessType.FIELD)
//@NamedQueries({
//	@NamedQuery(name=Ndvi.FIND_ALL, query="SELECT c FROM Ndvi c") ,
//	@NamedQuery(name=Ndvi.FIND_NAME, query="SELECT o FROM Ndvi o where o.nombre = :name") ,
//	@NamedQuery(name=Ndvi.FIND_ACTIVOS, query="SELECT o FROM Ndvi o where o.activo = true") ,
//}) 
public class OrdenSiembraItem {

	public static final String FIND_ALL="OrdenSiembraItem.findAll";
	public static final String FIND_NAME = "OrdenSiembraItem.findName";

	@javax.persistence.Id @GeneratedValue
	private Long id=null;
	
	@ManyToOne
	@Exclude
	private OrdenSiembra ordenSiembra =null;
	
	@ManyToOne//(cascade=CascadeType.DETACH)
	private Producto producto =null;
	
	private Double dosisHa = 0.0;
	private String observaciones=null;
	@Column(name="cantidad32", precision = 32, scale = 2)// columnDefinition="DECIMAL(32,15)")
	private Double cantidad=null;
	
	public OrdenSiembraItem() {
		
	}
	
	public OrdenSiembraItem(Producto producto2, Double _dosisHa) {
		this.producto=producto2;
		this.dosisHa=_dosisHa;
	}
}
