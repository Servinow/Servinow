package com.servinow.android.domain;

import java.util.Date;
import java.util.Iterator;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pedido")
public class Pedido {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private Date fecha;
	
	@DatabaseField(canBeNull = false)
	private boolean pagado;
	
	@DatabaseField(canBeNull = false)
	private boolean confirmado;
	
	@ForeignCollectionField(eager = true) // (eager = false) equivale a lazy
	private ForeignCollection<LineaPedido> lineas;

	public Pedido() {
		super();
	}

	public Pedido(Date fecha, boolean pagado, boolean confirmado,
			ForeignCollection<LineaPedido> lineas) {
		super();
		this.fecha = fecha;
		this.pagado = pagado;
		this.confirmado = confirmado;
		this.lineas = lineas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

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

	public ForeignCollection<LineaPedido> getLineas() {
		return lineas;
	}

	public void setLineas(ForeignCollection<LineaPedido> lineas) {
		this.lineas = lineas;
	}
	
	public double getTotal() {
		double total = 0.0;
		
		Iterator<LineaPedido> itr = lineas.iterator();
		while (itr.hasNext()) {
			LineaPedido lp = itr.next();
			total += lp.getTotal();
		}

		return total;
	}
}
