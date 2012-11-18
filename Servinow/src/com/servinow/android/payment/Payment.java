package com.servinow.android.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;
import com.servinow.android.R;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


public class Payment {	
	public static enum Method {
		NORMAL,
		PAYPAL
	};
	
	private IPaymentCallback iPaymentActivity;
	private SherlockFragmentActivity activity;
	private Payment.Method paymentMethod;
	private Restaurant restaurante;
	private List<Pedido> pedidos;
	
	public Payment(IPaymentCallback activity, Restaurant restaurante, List<Pedido> pedidos) {
		this.activity =  (SherlockFragmentActivity) activity;
		this.iPaymentActivity = activity;
		this.restaurante = restaurante;
		this.pedidos = pedidos;
	}
	
	public void setPaymentMethod(){
		DialogFragment newFragment = new selectPaymentMethodDialog();
	    newFragment.show(activity.getSupportFragmentManager(), "PAYMENTMETHODDIALOG");
	}
	
	public void pay(){
		switch (paymentMethod) {
		case PAYPAL:
			usePaypalMethod();
			break;
		case NORMAL:
			useNormalMethod();
			break;
		default:
			break;
		}
	}
	
	private void usePaypalMethod(){
		PayPalPayment payment = createPayPalPayment();

		Intent checkoutIntent = PayPal.getInstance().checkout(payment, activity);
		
		iPaymentActivity.onPaymentProcess(paymentMethod);
		activity.startActivityForResult(checkoutIntent, 1); 
	}
	
	private PayPalPayment createPayPalPayment() {
		Iterator<Pedido> iter;
		PayPal pp = PayPal.getInstance();
		if (pp == null) {  // Test to see if the library is already initialized
			// This main initialization call takes your Context, AppID, and target server
			pp = PayPal.initWithAppID(activity, "APP-80W284485P519543T", PayPal.ENV_SANDBOX);

			// Set the language for the library
			pp.setLanguage("es_ES");
		}
		
		PayPalPayment payment = new PayPalPayment();
		
		// Calculate subtotal from all orders
		iter = pedidos.iterator();
		float subTotal = 0;
		while(iter.hasNext()){
			subTotal += iter.next().getTotal();
		}
		
		payment.setSubtotal(new BigDecimal(subTotal)); //Subtotal

		payment.setCurrencyType("EUR");

		payment.setRecipient(restaurante.getEmailPayPal());

		payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
		
		// PayPalInvoiceData can contain tax and shipping amounts, and an
		// ArrayList of PayPalInvoiceItem that you can fill out.
		// These are not required for any transaction.
		PayPalInvoiceData invoice = new PayPalInvoiceData();

		// Set the tax amount
		invoice.setTax(new BigDecimal(((restaurante.getTax()/100)*subTotal)));
		
		//Set products information for all orders
		iter = pedidos.iterator();
		while(iter.hasNext()){
			Pedido pedido = iter.next();
			Iterator<LineaPedido> iter2 = pedido.getLineas().iterator();
			while(iter2.hasNext()){
				LineaPedido lineapedido = iter2.next();
				Producto producto = lineapedido.getProducto();
				
				PayPalInvoiceItem item = new PayPalInvoiceItem();
				item.setName(producto.getNombre());
				item.setID(""+producto.getId());
				item.setTotalPrice(new BigDecimal(lineapedido.getTotal()));
				item.setUnitPrice(new BigDecimal(producto.getPrecio()));
				item.setQuantity(lineapedido.getCantidad());
				
				invoice.getInvoiceItems().add(item);
			}
		}
		
		
		payment.setInvoiceData(invoice);
		
		return payment;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		   switch(resultCode) {
		      case Activity.RESULT_OK:
		    	  PedidoCache pdc = new PedidoCache(activity);
		    	  Iterator<Pedido> iter = pedidos.iterator();
		    	  while(iter.hasNext()){
		    		  pdc.setPagado(iter.next());
		    	  }
		    	  iPaymentActivity.onPaymentSuccesful(paymentMethod);
		          break;
		       case Activity.RESULT_CANCELED:
		    	   iPaymentActivity.onPaymentCanceled(paymentMethod);
		           break;

		       case PayPalActivity.RESULT_FAILURE:
		    	   iPaymentActivity.onPaymentFailure(paymentMethod);
		  }

	}
	
	@SuppressWarnings("unchecked")
	private void useNormalMethod(){
		ArrayList<Object> params = new ArrayList<Object>();
		new checkInNormalPayment().execute(params);
	}
	
	private class selectPaymentMethodDialog extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	        builder.setTitle(R.string.paymentmethoddialog_title).setItems(R.array.paymentmethoddialog, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   switch(which){
		            	   case 0:
		            		   paymentMethod = Payment.Method.NORMAL;
		            		   break;
		            	   case 1:
		            		   paymentMethod = Payment.Method.PAYPAL;
		            		   break;
	            	   }
	            	   pay();
	               }
	        });
	        return builder.create();
	    }
	}
	
	private class checkInNormalPayment extends AsyncTask<ArrayList<Object>, Void, Void>{

		protected void onPreExecute() {
		}
		
		@Override
		protected Void doInBackground(ArrayList<Object>... params) {
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				iPaymentActivity.onPaymentFailure(paymentMethod);
			}
			return null;
		}
		
		protected void onPostExecute(Void a) {
			iPaymentActivity.onPaymentProcess(paymentMethod);
		}
	}
	
	
	
}
