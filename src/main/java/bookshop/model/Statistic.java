package bookshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

public class Statistic extends InventoryItem{
	
	private final Product product;
	private final Quantity quantity;
	
	public Statistic(Product product, Quantity quantity){
		this.product = product;
		this.quantity = quantity;
	}
	public Statistic(){
		this.product = null;
		this.quantity = null;
	}
	
	/*
	public Quantity updateArticleQuantity(Quantity quantity1){
		quantity.add(quantity1);
		return quantity;
	}
	
	public Quantity decreaseQuantity(Quantity quantity1){
		quantity.subtract(quantity1);
		return quantity;
	}
	*/
	
	public List<Statistic> getAll(List<Statistic> test){
		return test;
	}
}
