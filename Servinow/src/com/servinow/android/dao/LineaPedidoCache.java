package com.servinow.android.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;

import android.content.Context;

public class LineaPedidoCache extends ServinowDAOBase<LineaPedido, Integer> {

	public LineaPedidoCache(Context context) {
		super(context, LineaPedido.class);
	}
	
	public List<LineaPedido> getAllListaPedido(){
		RuntimeExceptionDao<LineaPedido, Integer> pedidoDAO = getDAO();
		
		List<LineaPedido> lineaPedidoList = pedidoDAO.queryForAll();
		
		return lineaPedidoList;
	}
	
	public void insertLineaPedido(LineaPedido lineaPedido){
		RuntimeExceptionDao<LineaPedido, Integer> pedidoDAO = getDAO();
		pedidoDAO.create(lineaPedido);
	}
	
	public void deleteAll(){
		RuntimeExceptionDao<LineaPedido, Integer> lineaPedidoDAO = getDAO();
		List<LineaPedido> pedidoList = lineaPedidoDAO.queryForAll();
		lineaPedidoDAO.delete(pedidoList);
	}
	
	public void deleteLineaPedido(int id){
		RuntimeExceptionDao<LineaPedido, Integer> lineaPedidoDAO = getDAO();
		// Conseguir la lineaPedido a partir del id
		LineaPedido lineaPedido = lineaPedidoDAO.queryForId(id);
		
		// Borrar lineaPedido
		lineaPedidoDAO.delete(lineaPedido);
	}
	
	
}