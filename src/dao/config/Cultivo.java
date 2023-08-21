package dao.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.Data;


//La informaci�n acerca del cultivo modelado debe contener:
//o Fecha de siembra
//o Fecha estimada de cosecha
//o Duraci�n aproximada de cada etapa fenol�gica
//o Profundidad radicular en cada etapa fenol�gica
//o Consumo h�drico en cada etapa fenol�gica
@Data
@Entity //@Access(AccessType.PROPERTY)
@NamedQueries({
	@NamedQuery(name=Cultivo.FIND_ALL, query="SELECT c FROM Cultivo c ORDER BY lower(c.nombre)") ,
	@NamedQuery(name=Cultivo.FIND_NAME, query="SELECT o FROM Cultivo o where o.nombre = :name") ,
	@NamedQuery(name=Cultivo.COUNT_ALL, query="SELECT COUNT(o) FROM Cultivo o") ,
	
}) 
public class Cultivo implements Comparable<Cultivo>{
	public static final String COUNT_ALL="Cultivo.countAll";
	public static final String FIND_ALL="Cultivo.findAll";
	public static final String FIND_NAME = "Cultivo.findName";
	
	public static final String GIRASOL = "Girasol";
	public static final String SOJA = "Soja";
	public static final String TRIGO = "Trigo";
	public static final String MAIZ = "Maiz";
	public static final String SORGO = "Sorgo";
	public static final String CEBADA = "Cebada";
	
	@Id @GeneratedValue
	private Long id=null;
	
	private String nombre =new String();
	
	//es lo que absorve (kg) la planta para producir una tonelada de grano seco
	private Double absN=new Double(0);
	private Double absP=new Double(0);
	private Double absK=new Double(0);
	private Double absS=new Double(0);
	
	//mm absorvidos de agua por tn de grano producido
	private Double absAgua=new Double(0);
	private Double aporteMO=new Double(0);
	
	//es lo que se lleva el grano por cada TN 
	private Double extN=new Double(0);
	private Double extP=new Double(0);
	private Double extK=new Double(0);
	private Double extS=new Double(0);

	private Double rindeEsperado=new Double(0);
	
	private Boolean estival = true;
	private Double semPorBolsa = 1.0;
	
//	private Double tasaCrecimientoPendiente=new Double(0);
//	private Double tasaCrecimientoOrigen=new Double(0);
	

	public static Map<String,Cultivo> getCultivosDefault(){
	 Map<String,Cultivo> cultivos = new HashMap<String,Cultivo>();
						//String _nombre, Double _absP, Double _extP,Double rinde
			cultivos.put(MAIZ, getMaiz());
			cultivos.put(TRIGO,getTrigo());
			cultivos.put(SOJA, getSoja());
			cultivos.put(CEBADA,getCebada());
			cultivos.put(SORGO,getSorgo());
			cultivos.put(GIRASOL,getGirasol());
		return cultivos;
	}
	public Cultivo() {
		aporteMO=new Double(0);
		estival = true;
	}
	
	public Cultivo(String _nombre) {
		super();
		this.nombre=_nombre;
	}

	@Override
	public int compareTo(Cultivo arg0) {
		return this.nombre.compareTo(arg0.nombre);
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
//	-	(kg ton-1)	(%)
//	N	22	0.68
//	P	4	0.76
//	K	19	0.21
//	Ca	3	0.07
//	Mg	3	0.53
//	S	4	0.35
//	B	0.02	0.25
//	Cl	0.444	0.06
//	Cu	0.013	0.29
//	Fe	0.125	0.36
//	Mn	0.189	0.17
//	Mo	0.001	0.63
//	Zn	0.053	0.5
//	Ni	-	-
	private static Cultivo getMaiz(){
		Cultivo c = new Cultivo(MAIZ);
		c.setAbsN(22d);
		c.setAbsP(4d);
		c.setAbsK(19d);
		c.setAbsS(5d);
		
		c.setExtN(c.getAbsN()*0.68);
		c.setExtP(c.getAbsP()*0.76);
		c.setExtK(c.getAbsK()*0.21);
		c.setExtS(c.getAbsS()*0.35);
		
		c.setRindeEsperado(10d);
		c.setAbsAgua(1000/12d);
		c.setAporteMO(1000*(-1+c.getAbsN()/c.getExtN()));//kg por tn;  estimacion en base a la extraccion de n vs absorcion de n
		c.setEstival(true);// se puede usar el porcentaje de los grados dias de la campania que el cultivo esta activo
		c.setSemPorBolsa(80000.0);
		return c;
	}
	
//	NUTRIENTE	REQUERIMIENTO	IC
//	-	(kg ton-1)	(%)
//	N	30	0.66
//	P	4.4	0.82
//	K	20.8	0.19
//	Ca	-	-
//	Mg	4.5	0.29
//	S	3.75	0.57
//	B	-	-
//	Cl	-	-
//	Cu	-	-
//	Fe	-	-
//	Mn	-	-
//	Mo	-	-
//	Zn	-	-
//	Ni	-	-
	private static Cultivo getSorgo(){
		Cultivo c = new Cultivo(SORGO);
		c.setAbsN(30d);
		c.setAbsP(4.4d);
		c.setAbsK(20.8d);
		c.setAbsS(3.75d);
		
		c.setExtN(c.getAbsN()*0.66);
		c.setExtP(c.getAbsP()*0.82);
		c.setExtK(c.getAbsK()*0.19);
		c.setExtS(c.getAbsS()*0.57);
		c.setRindeEsperado(10d);
		c.setAbsAgua(1000/12d);
		c.setAporteMO(1000*(-1+c.getAbsN()/c.getExtN()));//kg por tn;  estimacion en base a la extraccion de n vs absorcion de n
		return c;
	}
	
//	N	30	0.69
//	P	5	0.8
//	K	19	0.21
//	Ca	3	0.14
//	Mg	4	0.63
//	S	5	0.34
//	B	0.025	0.5
//	Cl	-	-
//	Cu	0.01	0.75
//	Fe	0.137	0.99
//	Mn	0.07	0.17
//	Mo	-	-
//	Zn	0.052	0.5
//	Ni	-	-
	private static Cultivo getTrigo(){
		Cultivo c = new Cultivo(TRIGO);
		c.setAbsN(30d);
		c.setAbsP(5d);
		c.setAbsK(19d);
		c.setAbsS(5d);
		
		c.setExtN(c.getAbsN()*0.69);
		c.setExtP(c.getAbsP()*0.8);
		c.setExtK(c.getAbsK()*0.21);
		c.setExtS(c.getAbsS()*0.34);
		c.setRindeEsperado(4d);
		c.setAbsAgua(1000/12d);
		c.setEstival(false);
		c.setAporteMO(1000*(-1+c.getAbsN()/c.getExtN()));//kg por tn;  estimacion en base a la extraccion de n vs absorcion de n
		return c;
	}
	
//	NUTRIENTE	REQUERIMIENTO	IC
//	-	(kg ton-1)	(%)
//	N	26.3	0.68
//	P	4	0.76
//	K	19	0.21
//	Ca	19.7	0.07
//	Mg	-	-
//	S	4.15	0.48
//	B	-	-
//	Cl	-	-
//	Cu	-	-
//	Fe	-	-
//	Mn	-	-
//	Mo	-	-
//	Zn	-	-
//	Ni	-	-
	private static Cultivo getCebada(){
		Cultivo c = new Cultivo(CEBADA);
		c.setAbsN(26d);
		c.setAbsP(4d);
		c.setAbsK(19d);
		c.setAbsS(4.15d);
		
		c.setExtN(c.getAbsN()*0.68);
		c.setExtP(c.getAbsP()*0.76);
		c.setExtK(c.getAbsK()*0.21);
		c.setExtS(c.getAbsS()*0.48);
		c.setRindeEsperado(4d);
		c.setAbsAgua(1000/12d);
		c.setEstival(false);
		c.setAporteMO(1000*(-1+c.getAbsN()/c.getExtN()));//kg por tn;  estimacion en base a la extraccion de n vs absorcion de n
		return c;
	}
	
//	NUTRIENTE	REQUERIMIENTO	IC
//	-	(kg ton-1)	(%)
//	N	30	0.69
//	P	5	0.8
//	K	19	0.21
//	Ca	3	0.14
//	Mg	4	0.63
//	S	5	0.34
//	B	0.025	0.5
//	Cl	-	-
//	Cu	0.01	0.75
//	Fe	0.137	0.99
//	Mn	0.07	0.17
//	Mo	-	-
//	Zn	0.052	0.5
//	Ni	-	-
	private static Cultivo getSoja(){
		Cultivo c = new Cultivo(SOJA);
		c.setAbsN(30d);
		c.setAbsP(5d);
		c.setAbsK(19d);
		c.setAbsS(5d);
		
		c.setExtN(c.getAbsN()*0.69);
		c.setExtP(c.getAbsP()*0.8);
		c.setExtK(c.getAbsK()*0.21);
		c.setExtS(c.getAbsS()*0.34);
		c.setRindeEsperado(4d);
		//para producir 1 Tn de grano se necesita una l�mina de agua 125 � 160 mm (Ing. Marta Castiglione)
		c.setAbsAgua(142.5);
		c.setEstival(true);
		c.setAporteMO(1000*(-1+c.getAbsN()/c.getExtN()));//kg por tn;  estimacion en base a la extraccion de n vs absorcion de n
		//135 dias de desarrollo para una Soja P
		return c;
	}
	
//	NUTRIENTE	REQUERIMIENTO	IC
//	-	(kg ton-1)	(%)
//	N	22.2	0.66
//	P	4	0.84
//	K	26.2	0.1
//	Ca	2.8	0.04
//	Mg	2.4	0.42
//	S	0.94	0.64
//	B	0.016	0.5
//	Cl	-	-
//	Cu	0.027	0.92
//	Fe	0.35	0.57
//	Mn	0.37	0.16
//	Mo	-	-
//	Zn	0.04	0.5
//	Ni	-	-
	private static Cultivo getGirasol(){
		Cultivo c = new Cultivo(GIRASOL);
		c.setAbsN(22.2);
		c.setAbsP(4d);
		c.setAbsK(26.2);
		c.setAbsS(0.94);
		
		c.setExtN(c.getAbsN()*0.66);
		c.setExtP(c.getAbsP()*0.84);
		c.setExtK(c.getAbsK()*0.1);
		c.setExtS(c.getAbsS()*0.64);
		c.setRindeEsperado(6d);
		c.setAbsAgua(1000/12d);
		c.setEstival(true);
		c.setAporteMO(1000*(-1+c.getAbsN()/c.getExtN()));//kg por tn;  estimacion en base a la extraccion de n vs absorcion de n
		return c;
	}

	public boolean isEstival() {
		return this.estival;
	}


}

