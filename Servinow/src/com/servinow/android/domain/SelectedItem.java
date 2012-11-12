package com.servinow.android.domain;

import com.servinow.android.R;

import android.view.View;

//import android.graphics.drawable.Drawable;

public class SelectedItem {
	
	  private int id;
		
	  private String name;
	  
	  private int quantity;
	  
	  private double unitPrice;
	  
	  private String urlImage;
	  
	  private boolean checked = false;
	  
	  private int checkBoxVisible = View.INVISIBLE;
	  
	  private int imageVisible = View.VISIBLE;
	  
	  /**
	   * 
	   */
	  public SelectedItem(String name, int quantity, double unitPrice, String urlImage) {
		this.name = name;
	    this.quantity = quantity;
	    this.unitPrice = unitPrice;
	    this.urlImage = urlImage;
	  }
	  
	  public SelectedItem(String name, int quantity, double unitPrice, String urlImage, boolean checked) {
		this.name = name;
	    this.quantity = quantity;
	    this.unitPrice = unitPrice;
	    this.urlImage = urlImage;
	    this.checked = checked;
	  }
	  
	  public SelectedItem(LineaPedido lineaPedido) {
		Producto producto = lineaPedido.getProducto();
		this.id = lineaPedido.getId();
		this.name = producto.getNombre();
	    this.quantity = lineaPedido.getCantidad();
	    this.unitPrice = producto.getPrecio();
	    this.urlImage = producto.getUrlImage();
	  }
	  
	  /**
	   * @return the name
	   */
	  public String getName() {
	    return name;
	  }
	  /**
	   * @return the quantity
	   */
	  public int getQuantity() {
	    return quantity;
	  }
	  /**
	   * @return the unit Price
	   */
	  public double getUnitPrice() {
	    return unitPrice;
	  }
	  
	  /**
	   * @return the image
	   */
	  public String geturlImage() {
		  return urlImage;
	  }
	  
	  
	  public void toggleChecked() {
		  checked = !checked ;
	  }
	  
	  public boolean isChecked() {
		  return checked;
	  }
	  
	  public void setChecked(boolean checked) {
		  this.checked = checked;
	  }

	  public int getCheckBoxVisibility(){
		  return checkBoxVisible;
	  }
	  
	  public void setCheckBoxVisibility(int checkBoxVisible){
		  this.checkBoxVisible = checkBoxVisible;
	  }
	  
	  public int getImageVisibility(){
		  return imageVisible;
	  }
	  
	  public void setImageVisibility(int imageVisible){
		  this.imageVisible = imageVisible;
	  }
	  
	  public int getId(){
		  return id;
	  }
	  
	  /**
	   * @return the Total Price
	   */
	  public double getTotalPrice() {
	    return unitPrice*quantity;
	  }
}
