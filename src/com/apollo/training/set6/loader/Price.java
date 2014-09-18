package com.apollo.training.set6.loader;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("price")
public class Price {

	@XStreamAlias("amount")
	@XStreamConverter(value = ToAttributedValueConverter.class, strings = "amount")
	public class Amount {
		@XStreamAlias("currency")
		@XStreamAsAttribute
		private String currency;

		private BigDecimal amount;

		public Amount(String currency, BigDecimal amount) {
			super();
			this.currency = currency;
			this.amount = amount;
		}

		public String getCurrency() {
			return currency;
		}

		public BigDecimal getAmount() {
			return amount;
		}

	}

	@XStreamAlias("id")
	private BigInteger id;

	private Amount amount;

	@XStreamAlias("fromDate")
	private String fromDate;

	@XStreamAlias("thruDate")
	private String thruDate;

	@XStreamAlias("productId")
	private String productId;

	public Price(BigInteger id, Amount amount, String fromDate,
			String thruDate, String productId) {
		super();
		this.id = id;
		this.amount = amount;
		this.fromDate = fromDate;
		this.thruDate = thruDate;
		this.productId = productId;
	}

	public BigInteger getId() {
		return id;
	}

	public Amount getAmount() {
		return amount;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getThruDate() {
		return thruDate;
	}

	public String getProductId() {
		return productId;
	}

}
