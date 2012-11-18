package com.servinow.android;

import java.util.LinkedList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.servinow.android.R.id;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.dao.RestaurantCache;
import com.servinow.android.domain.Estado;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;
import com.servinow.android.payment.IPaymentCallback;
import com.servinow.android.payment.Payment;
import com.servinow.android.payment.Payment.Method;
import com.servinow.android.widget.PurchasedItemAdapter;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketActivity extends SherlockFragmentActivity implements IPaymentCallback {
	private int restaurantID;
	private Restaurant restaurant;
	private  List<Pedido> pedidos;
	private Menu menuActionBar;
	private Payment payment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle parameters = getIntent().getExtras();


		if (parameters != null) {

		//	restaurantID = parameters.getInt("restaurantID");
			restaurantID = parameters.getInt(Param.RESTAURANT.toString());
			Log.d("restaurantID: ",""+restaurantID+"---3---");
			pedidos = new PedidoCache(this).getPedidosNoPagados(restaurantID);
			restaurant = new RestaurantCache(this).getRestaurantFromCache(restaurantID);
			
			List<LineaPedido> lineasPedido = new LinkedList<LineaPedido>();
			double subtotal = 0;
			for(Pedido p : pedidos) {
				lineasPedido.addAll(p.getLineas());
				subtotal += p.getTotal();
			}


			setContentView(R.layout.activity_ticket);
			
			ListView listView = (ListView) findViewById(R.id.activity_ticket_list);
			
			PurchasedItemAdapter adapter = new PurchasedItemAdapter(this, R.layout.ticket_list_item, lineasPedido);
			listView.setAdapter(adapter);

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
			payment = new Payment(this, restaurant, pedidos);
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
			finish();
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		payment.onActivityResult(requestCode, resultCode, data);
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
