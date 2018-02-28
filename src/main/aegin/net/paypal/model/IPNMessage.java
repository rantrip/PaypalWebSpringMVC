package aegin.net.paypal.model;

public class IPNMessage {
	
	private static final long serialVersionUID = 3L;

	private Long id;
	 //private Date date = new Date();
	 private boolean validated = false;
	// private Text fullMessage;
	 private TransactionType transactionType;
	 private PaymentStatus paymentStatus;
	 private String mcGross;
	 private String mcCurrency;
	 private String custom;
	 private String itemNumber;
	 private String txnId;
	 private String subscrId;
	 private String payerEmail;

	private IPNMessage() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getMcGross() {
		return mcGross;
	}

	public void setMcGross(String mcGross) {
		this.mcGross = mcGross;
	}

	public String getMcCurrency() {
		return mcCurrency;
	}

	public void setMcCurrency(String mcCurrency) {
		this.mcCurrency = mcCurrency;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getSubscrId() {
		return subscrId;
	}

	public void setSubscrId(String subscrId) {
		this.subscrId = subscrId;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	
	

}
