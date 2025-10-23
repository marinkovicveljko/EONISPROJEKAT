package EONISProject.dto;

import java.math.BigDecimal;

public class PaymentRequest {
	private int orderId;
	private BigDecimal amount;
	private String currency;
	private String paymentMethod;
	public int getUserId() {
		return orderId;
	}
	public void setUserId(int orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	

}
