package com.servinow.android;

import java.util.LinkedList;
import java.util.List;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.servinow.android.R.id;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.dao.RestaurantCache;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Restaurant;
import com.servinow.android.widget.PurchasedItemAdapter;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class TicketActivity extends SherlockListActivity {
  private int restaurantID;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle parameters = getIntent().getExtras();
    if (parameters != null) {
      
      restaurantID = parameters.getInt("restaurantID");
      List<Pedido> pedidos = new PedidoCache(this).getPedidosNoPagados(restaurantID);
      Restaurant restaurant = new RestaurantCache(this).getRestaurantFromCache(restaurantID);
      
      List<LineaPedido> lineasPedido = new LinkedList<LineaPedido>();      
      double subtotal = 0;
      for(Pedido p : pedidos) {
        lineasPedido.addAll(p.getLineas());
        subtotal += p.getTotal();
      }
      
      PurchasedItemAdapter adapter = new PurchasedItemAdapter(this, R.layout.ticket_list_item, lineasPedido);
      setListAdapter(adapter);
      
      setContentView(R.layout.activity_ticket);
      
      TextView subtotalView = (TextView) findViewById(R.id.activity_ticket_textViewSubTotal);
      TextView taxView = (TextView) findViewById(R.id.activity_ticket_textViewTax);
      TextView totalView = (TextView) findViewById(id.activity_ticket_textViewTotal);
      
      Resources res = getResources();
      subtotalView.setText(res.getString(R.string.activity_ticket_subtotal, subtotal));
      
      float tax = restaurant.getTax();
      double taxAmount = tax*subtotal;
      taxView.setText(res.getString(R.string.activity_ticket_tax, tax, taxAmount));
      
      totalView.setText(res.getString(R.string.activity_ticket_total, subtotal+taxAmount));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getSupportMenuInflater().inflate(R.menu.activity_ticket, menu);
    return true;
  }
}
