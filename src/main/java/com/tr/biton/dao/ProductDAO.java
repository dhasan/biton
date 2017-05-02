package com.tr.biton.dao;

import java.util.List;

import com.tr.biton.orm.Category;
import com.tr.biton.orm.Location;
import com.tr.biton.orm.Product;
import com.tr.biton.orm.User;

public interface ProductDAO {
	public List<Product> getProductsLikeNameOrDescription(String name);
	void addProduct(Product p);
	void updateProduct(Product p);
	void removeProduct(Product p);
	List<Product> getProducts(int first, int max);
	List<Product> getProductsByUser(User u, int first, int max);
	List<Product> getProductsByCategory(Category cat, int first, int max);
	List<Product> getProductsByUserLocation(Location loc, int first, int max);
	List<Product> getProductsByShipsto(Location loc, int first, int max);
	List<Product> getProductsByShipstos(List<Location> locs, int first, int max);
}
