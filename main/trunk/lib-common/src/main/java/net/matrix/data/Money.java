/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * 金额：包括数量和货币
 */
public class Money {
	private BigDecimal amount;

	private Currency currency;

	public Money(BigDecimal amount) {
		this(amount, Currency.getInstance(Locale.getDefault()));
	}

	public Money(BigDecimal amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * 将一个字符串表示为货币额。
	 */
	public Money(String amount)
		throws NumberFormatException {
		this.amount = new BigDecimal(amount);
		this.currency = Currency.getInstance(Locale.getDefault());
	}

	public Money(String amount, Currency currency) {
		this.amount = new BigDecimal(amount);
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}
}
