package com.servinow.android.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;

public class ProductCache extends ServinowDAOBase<Producto, Integer> {

	public ProductCache(Context context) {
		super(context, Producto.class);
	}
	
	void setProductCache(Restaurant restaurant, Collection<Producto> productsList){
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		
		for(Producto product: productsList){
			product.restaurant = restaurant;
			productDAO.createOrUpdate(product);
		}
	}
	
	public void setProductCacheByCategory(Categoria category, List<Integer> productIDsList){
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		
		for(Integer productID: productIDsList){
			Producto product = productDAO.queryForId(productID);
			product.category = category;
			
			productDAO.createOrUpdate(product);
		}
	}

	public Producto getProducto(int productoID) {
		RuntimeExceptionDao<Producto, Integer> productDAO = getDAO();
		Producto producto = productDAO.queryForId(productoID);
		
		return producto;
	}
}
