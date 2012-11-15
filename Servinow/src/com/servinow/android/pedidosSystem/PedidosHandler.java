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
	
	private PedidoCache pedidoCache;
	private LineaPedidoCache lineaPedidoCache;
	private RestaurantCache restaurantCache;
	private PlaceCache placeCache;
	
	public PedidosHandler(Context context){
		this.context = context;
		
		pedidoCache = new PedidoCache(this.context);
		lineaPedidoCache = new LineaPedidoCache(this.context);
		restaurantCache = new RestaurantCache(this.context);
		placeCache = new PlaceCache(this.context);
	}
	
	public IdPedidoAndIdLinea insertarProducto(Producto producto, int cantidad, int mesaId, int restauranteId){
		
		// Coger el pedido no confirmado de la base de datos
		Pedido pedidoNoConfirmado = pedidoCache.getPedidoNotConfirmed(mesaId, restauranteId);
		
		// Si no existe pedido
		if(pedidoNoConfirmado == null){
			// Coger place de la base de datos a partir de su id
			Place place = placeCache.getPlaceFromCache(mesaId);
			// Coger restaurant de la base de datos a partir de su id
			Restaurant restaurant = restaurantCache.getRestaurantFromCache(restauranteId);
			
			Pedido pedido = new Pedido(new Date());
			pedido.setPlace(place);
			pedido.setRestaurant(restaurant);
			// Insertar pedido en la base de datos
			pedidoNoConfirmado = pedidoCache.insertPedidoIfNotExists(pedido);
		}
		
		// Crear LineaPedido a partir de la cantidad y el producto
		LineaPedido lineaPedido = new LineaPedido(cantidad, Estado.EN_COLA, producto);
		lineaPedido.setOrder(pedidoNoConfirmado);
		
		// Insertar LineaPedido en la base de datos
		lineaPedido = lineaPedidoCache.insertLineaPedidoIfNotExists(lineaPedido);
		
		// Devolver el identificador de la l’nea de pedido creada y el identificador del pedido
		IdPedidoAndIdLinea idPedidoAndIdLinea = new IdPedidoAndIdLinea();
		idPedidoAndIdLinea.idPedido = pedidoNoConfirmado.getId();
		idPedidoAndIdLinea.idLinea = lineaPedido.getId();
		
		return idPedidoAndIdLinea;
	}

	public void cancelarPedido(int pedidoId){
		
		// Borrar todas las lineas pedido que apunten a pedido
		lineaPedidoCache.deleteLineaPedidoByPedido(pedidoId);
		
		// Borrar pedido
		pedidoCache.deletePedido(pedidoId);
	}
}
