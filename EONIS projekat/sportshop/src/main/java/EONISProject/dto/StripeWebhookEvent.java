package EONISProject.dto;

public class StripeWebhookEvent {
	 private Integer paymentId;
	 private String event; 

	    public Integer getPaymentId() { return paymentId; }
	    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }

	    public String getEvent() { return event; }
	    public void setEvent(String event) { this.event = event; }
}
