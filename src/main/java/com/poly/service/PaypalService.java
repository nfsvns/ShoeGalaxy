package com.poly.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalService {

	@Autowired
	private APIContext apiContext;

	public Payment createPayment(Double total, String currency, String method, String intent, String description,
			String recipientName, String shippingAddressLine1, String cancelUrl, String successUrl)
			throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

//		if (items != null && !items.isEmpty()) {
		ItemList itemList = new ItemList();
//		itemList.setItems(items);

		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setRecipientName(recipientName);
		shippingAddress.setLine1(shippingAddressLine1);
		shippingAddress.setCity("Hồ Chí Minh");
		shippingAddress.setState("TP.HCM");
		shippingAddress.setPostalCode("700000");
		shippingAddress.setCountryCode("VN");
		itemList.setShippingAddress(shippingAddress);

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		transaction.setItemList(itemList);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

// Gửi yêu cầu tạo thanh toán đến PayPal API và trả về đối tượng Payment được tạo
		return payment.create(apiContext);
	}
	public Payment createPayment1(Double total, String currency, String method, String intent, String description,
			String recipientName, String shippingAddressLine1, List<Item> items, String cancelUrl, String successUrl)
			throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

//		if (items != null && !items.isEmpty()) {
		ItemList itemList = new ItemList();
		itemList.setItems(items);

		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setRecipientName(recipientName);
		shippingAddress.setLine1(shippingAddressLine1);
		shippingAddress.setCity("Hồ Chí Minh");
		shippingAddress.setState("TP.HCM");
		shippingAddress.setPostalCode("700000");
		shippingAddress.setCountryCode("VN");
		itemList.setShippingAddress(shippingAddress);

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		transaction.setItemList(itemList);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

// Gửi yêu cầu tạo thanh toán đến PayPal API và trả về đối tượng Payment được tạo
		return payment.create(apiContext);
	}


	public Payment createPayment2(Double total, String currency, String method, String intent, String description,
			String name, String recipientName, String shippingAddressLine1, String cancelUrl, String successUrl)
			throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Item item = new Item();
		item.setName(name);
//	    item.setCurrency(currency);
//	    item.setPrice(String.format("%.2f", total));
//	    item.setQuantity("1");
//	    item.setSku("SKU");

		List<Item> items = new ArrayList<>();
		items.add(item);
		ItemList itemList = new ItemList();
		itemList.setItems(items);

		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setRecipientName(recipientName);
		shippingAddress.setLine1(shippingAddressLine1);
		shippingAddress.setCity("Hồ Chí Minh");
		shippingAddress.setState("TP.HCM");
		shippingAddress.setPostalCode("700000");
		shippingAddress.setCountryCode("VN");
		itemList.setShippingAddress(shippingAddress);

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		transaction.setItemList(itemList);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

// Gửi yêu cầu tạo thanh toán đến PayPal API và trả về đối tượng Payment được tạo
		return payment.create(apiContext);
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}

}
