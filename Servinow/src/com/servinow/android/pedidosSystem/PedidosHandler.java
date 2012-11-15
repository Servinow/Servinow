package com.servinow.android.pedidosSystem;

import java.util.Date;

import android.content.Context;

import com.servinow.android.domain.Estado;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Place;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;
import com.servinow.android.pedidosSystem.IdPedidoAndIdLinea;
import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.dao.PlaceCache;
import com.servinow.android.dao.RestaurantCache;

public class PedidosHandler {
	
	private Context context;
	
	public PedidosHandler(Context context){
		this.context = context;
	}
	
	public IdPedidoAndIdLinea insertarProducto(Producto producto, int cantidad, int mesaId, int restauranteId){
		
		PedidoCache pedidoCache = new PedidoCache(context);
		
		// Coger el pedido no confirmado de la base de datos
		Pedido pedidoNoConfirmado = pedidoCache.getPedidoNotConfirmed(mesaId, restauranteId);
		
		// Si no existe pedido
		if(pedidoNoConfirmado == null){
			// Coger place de la base de datos a partir de su id
			Place place = new PlaceCache(context).getPlaceFromCache(mesaId);
			// Coger restaurant de la base de datos a partir de su id
			Restaurant restaurant = new RestaurantCache(context).getRestaurantFromCache(restauranteId);
			
			Pedido pedido = new Pedido(new Date());
			pedido.setPlace(place);
			pedido.setRestaurant(restaurant);
			// Insertar pedido en la base de datos
			pedidoNoConfirmado = pedidoCache.insertPedidoIfNotExists(pedido);
		}
		
		LineaPedidoCache lineaPedidoCache = new LineaPedidoCache(context);
		LineaPedido lineaPedido = lineaPedidoCache.getLineaPedido(producto.getId());
		
		if(lineaPedido == null){
			// Crear LineaPedido a partir de la cantidad y el producto
			lineaPedido = new LineaPedido(cantidad, Estado.EN_COLA, producto);
			lineaPedido.setOrder(pedidoNoConfirmado);
			
			// Insertar LineaPedido en la base de datos
			lineaPedido = lineaPedidoCache.insertLineaPedidoIfNotExists(lineaPedido);
		}
		else{
			lineaPedidoCache.updateQuantityLineaPedido(lineaPedido.getId(), lineaPedido.getCantidad() + cantidad);
		}
		
		// Devolver el identificador de la l�nea de pedido creada y el identificador del pedido
		IdPedidoAndIdLinea idPedidoAndIdLinea = new IdPedidoAndIdLinea();
		idPedidoAndIdLinea.idPedido = pedidoNoConfirmado.getId();
		idPedidoAndIdLinea.idLinea = lineaPedido.getId();
		
		return idPedidoAndIdLinea;
	}

	public void cancelarPedido(int pedidoId){
		
		LineaPedidoCache lineaPedidoCache = new LineaPedidoCache(context);
		PedidoCache pedidoCache = new PedidoCache(context);
		
		// Borrar todas las lineas pedido que apunten a pedido
		lineaPedidoCache.deleteLineaPedidoByPedido(pedidoId);
		
		// Borrar pedido
		pedidoCache.deletePedido(pedidoId);
	}
}
