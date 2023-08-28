package com.poly.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.entity.Product;


@Service
@SessionScope
public class ShoppingCartImp implements ShoppingCartDAO {
	public Map<Integer, Product> map = new HashMap<Integer, Product>();

	// CN
	@Override
	public void add(Product item) {
		Product existedItem = map.get(item.getId());
		if (existedItem != null) {
			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
		} else {
			map.put(item.getId(), item);
		}

	}

	@Override
	public void update(int productId, int quantity) {
		Product item = map.get(productId);
		item.setQuantity(quantity);
		if (item.getQuantity() <= 0) {
			map.remove(item.getId());
		}
	}

	@Override
	public void remove(int productId) {
		map.remove(productId);
	}
	
	@Override
	public Collection<Product> getAll(){
		return map.values();
	}
	

	@Override
	public int getCount() {
		int count = 0;
		for (Product item : map.values()) {
			count += item.getQuantity();
		}
		return count;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public double getAmount() {
		double amount = 0;
		for (Product item : map.values()) {
			amount += item.getPrice() * item.getQuantity();
		}
		return amount;
	}
}
