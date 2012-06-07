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
 * 金额：包括数量和货币。
 */
public class Money {
	/**
	 * 数量。
	 */
	private BigDecimal amount;

	/**
	 * 货币。
	 */
	private Currency currency;

	/**
	 * 使用特定数量和默认货币构造对象。
	 * 
	 * @param amount
	 *            数量
	 */
	public Money(final BigDecimal amount) {
		this(amount, Currency.getInstance(Locale.getDefault()));
	}

	/**
	 * 使用特定数量和货币构造对象。
	 * 
	 * @param amount
	 *            数量
	 * @param currency
	 *            货币
	 */
	public Money(final BigDecimal amount, final Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * @return 数量
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @return 货币
	 */
	public Currency getCurrency() {
		return currency;
	}
}
