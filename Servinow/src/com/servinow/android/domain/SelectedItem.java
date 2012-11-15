package com.servinow.android.domain;

import android.view.View;

public class SelectedItem {
	
	  private int id;
		
	  private String name;
	  
	  private int productoId;
	  
	  private int categoriaId;
	  
	  private int placeId;
	  
	  private int restaurantId;
	  
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
	  
	  public SelectedItem(LineaPedido lineaPedido, int restaurantId, int placeId) {
		Producto producto = lineaPedido.getProducto();
		this.id = lineaPedido.getId();
		this.name = producto.getNombre();
	    this.quantity = lineaPedido.getCantidad();
	    this.unitPrice = producto.getPrecio();
	    this.urlImage = producto.getUrlImage();
	    this.productoId = producto.getId();
	    this.categoriaId = producto.getCategoria().getId();
	    this.placeId = placeId;
	    this.restaurantId = restaurantId;
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
	  
	  public void setQuantity(int quantity) {
	    this.quantity = quantity;
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
	  
	  public int getCategoriaId(){
		  return categoriaId;
	  }
	  
	  public void setCategoriaId(int categoriaId){
		  this.categoriaId = categoriaId;
	  }
	  
	  public int getProductoId(){
		  return this.productoId;
	  }
	  
	  public void setProductoId(int productoId){
		  this.productoId = productoId; 
	  }
	  
	  public int getRestaurantId(){
		  return this.restaurantId;
	  }
	  
	  public void setRestaurantId(int restaurantId){
		  this.restaurantId = restaurantId;
	  }
	  
	  public void setPlaceId(int placeId){
		  this.placeId = placeId;
	  }
	  
	  public int getPlaceId(){
		  return placeId;
	  }
	  
	  /**
	   * @return the Total Price
	   */
	  public double getTotalPrice() {
	    return unitPrice*quantity;
	  }
}
