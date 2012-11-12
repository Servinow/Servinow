/**
 * 
 */
package com.servinow.android.dao;

import java.util.HashMap;
import java.util.List;

import com.servinow.android.domain.Pedido;

import android.content.Context;

/**
 * @author KoxAlen
 *
 */
public class PedidoCache extends ServinowDAOBase<Pedido, Integer> {

  /**
   * @param context
   */
  public PedidoCache(Context context) {
    super(context, Pedido.class);
  }
  
  public List<Pedido> getPedidosNoPagados(int restaurantID) {
    HashMap<String, Object> sqlFieldsToMatch = new HashMap<String, Object>();
    sqlFieldsToMatch.put("restaurant_id", restaurantID);
    sqlFieldsToMatch.put("pagado", false);
    sqlFieldsToMatch.put("confirmado", true);
    
    List<Pedido> pedidosList = getDAO().queryForFieldValues(sqlFieldsToMatch);
    return pedidosList;
    
  }
}
