/**
 * 
 */
package com.servinow.android.widget;

import java.util.List;

import com.servinow.android.R;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Producto;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author KoxAlen
 *
 */
public class PurchasedItemAdapter extends ArrayAdapter<LineaPedido> {
  /* ViewHolder pattern
   * Se usa en los ListView para reducir el numero de findById (optimizacion)
   */
  static class ViewHolder {
    TextView name;
    TextView unitPrice;
    TextView qty;
    TextView price;
  }
  
  private int layoutResourceId;

  public PurchasedItemAdapter(Context context, int layoutResourceId, List<LineaPedido> objects) {
    super(context, layoutResourceId, objects);
    this.layoutResourceId = layoutResourceId;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    ViewHolder holder = null;
    
    if(row == null) {      
      LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
      row = inflater.inflate(layoutResourceId, parent, false);
      //ViewHolder pattern
      holder = new ViewHolder();
      holder.name = (TextView)row.findViewById(R.id.ticket_list_item_textViewName);
      holder.unitPrice = (TextView)row.findViewById(R.id.ticket_list_item_textViewUnitPrice);
      holder.qty = (TextView)row.findViewById(R.id.ticket_list_item_textViewQty);
      holder.price = (TextView)row.findViewById(R.id.ticket_list_item_textViewPrice);
      row.setTag(holder);
    } else {
      holder = (ViewHolder) row.getTag();
    }
    
    LineaPedido lineaPedido = getItem(position);
    Producto producto = lineaPedido.getProducto();
    holder.name.setText(producto.getNombre());
    holder.unitPrice.setText(String.format("%.2f€", producto.getPrecio()));
    holder.qty.setText(""+lineaPedido.getCantidad());
    holder.price.setText(String.format("%.2f€", lineaPedido.getTotal()));
    
    return row;
  }
}
