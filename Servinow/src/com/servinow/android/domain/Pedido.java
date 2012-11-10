package com.servinow.android.domain;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "order")
public class Pedido {

	//Local ID.
	@DatabaseField(generatedId = true) //AUTOINCREMENT
	private int id; //localID
	
	@DatabaseField(canBeNull = true)
	private Integer onlineID;
	
	@DatabaseField(canBeNull = false)
	private Date fecha;
	
	@DatabaseField(canBeNull = false, defaultValue="false")
	private boolean pagado;
	
	@DatabaseField(canBeNull = false, defaultValue="false")
	private boolean confirmado;
	
	@ForeignCollectionField(eager = true) // (eager = false) equivale a lazy
	private Collection<LineaPedido> orderLines;
	
	//No getter (or setter) for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	protected Restaurant restaurant;

	public Pedido() {
	}
	
	public Pedido(Date fecha){
		this.fecha = fecha;
		this.pagado = false;
		this.confirmado = false;
	}

	//TODO remove
	public Pedido(Date fecha, boolean pagado, boolean confirmado,
			ForeignCollection<LineaPedido> lineas) {
		super();
		this.fecha = fecha;
		this.pagado = pagado;
		this.confirmado = confirmado;
		this.orderLines = lineas;
	}

	public int getId() {
		return id;
	}

	//TODO remove.
	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	//TODO remove.
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public Collection<LineaPedido> getLineas() {
		return orderLines;
	}

	//TODO rethink
	public void setLineas(Collection<LineaPedido> lineas) {
		this.orderLines = lineas;
	}
	
	public double getTotal() {
		double total = 0.0;
		
		Iterator<LineaPedido> itr = orderLines.iterator();
		while (itr.hasNext()) {
			LineaPedido lp = itr.next();
			total += lp.getTotal();
		}

		return total;
	}
}
