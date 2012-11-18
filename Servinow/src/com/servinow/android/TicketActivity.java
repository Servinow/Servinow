package com.servinow.android;

import java.util.LinkedList;
import java.util.List;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.servinow.android.R.id;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.dao.RestaurantCache;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Restaurant;
import com.servinow.android.widget.PurchasedItemAdapter;
import com.servinow.payment.IPaymentCallback;
import com.servinow.payment.Payment;
import com.servinow.payment.Payment.Method;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TicketActivity extends SherlockListActivity implements IPaymentCallback {
  private int restaurantID;
  private Restaurant restaurant;
  private  List<Pedido> pedidos;
private Menu menuActionBar;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle parameters = getIntent().getExtras();
    if (parameters != null) {
      
      restaurantID = parameters.getInt("restaurantID");
      pedidos = new PedidoCache(this).getPedidosNoPagados(restaurantID);
      restaurant = new RestaurantCache(this).getRestaurantFromCache(restaurantID);
      
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
      double taxAmount = (tax/100)*subtotal;
      taxView.setText(res.getString(R.string.activity_ticket_tax, tax, taxAmount));
      
      totalView.setText(res.getString(R.string.activity_ticket_total, subtotal+taxAmount));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getSupportMenuInflater().inflate(R.menu.activity_ticket, menu);
    menuActionBar = menu;
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      	case R.id.itemPay:
      		Payment payment = new Payment(this, restaurant, pedidos);
      		payment.setPaymentMethod();
      		break;
      	case R.id.itemBack:
      	  this.finish();
      	  break;
      }
      return false;
  }
  
  private void enableMenuOptions(boolean enabled){
	  menuActionBar.findItem(R.id.itemBack).setEnabled(enabled);
	  menuActionBar.findItem(R.id.itemPay).setEnabled(enabled);
  }

  @Override
  public void onPaymentSuccesful(Method method) {
  	switch(method){
  	case NORMAL:
  		break;
  	case PAYPAL:
  		Intent i = new Intent(TicketActivity.this, MainActivity.class);
      	startActivity(i);

      	finish();
  		break;
  	}

  }

  @Override
  public void onPaymentProcess(Method method) {
  	switch(method){
	    	case NORMAL:
	    		enableMenuOptions(false);
	    		Toast.makeText(this, R.string.activity_ticket_normalpaymentinprocess, Toast.LENGTH_LONG).show();
	    		break;
	    	case PAYPAL:
	    		enableMenuOptions(false);
	    		break;
  	}
  }

  @Override
  public void onPaymentCanceled(Method method) {
	  Toast.makeText(this, R.string.activity_ticket_paymentcancelled, Toast.LENGTH_LONG).show();
  }

  @Override
  public void onPaymentFailure(Method method) {
	  Toast.makeText(this, R.string.activity_ticket_paymentfailure, Toast.LENGTH_LONG).show();
  }
}
