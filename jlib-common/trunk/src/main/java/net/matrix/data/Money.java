/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * 金额：包括数量和货币
 */
public class Money
{
	private BigDecimal amount;

	private Currency currency;

	public Money(BigDecimal amount)
	{
		this(amount, Currency.getInstance(Locale.getDefault()));
	}

	public Money(BigDecimal amount, Currency currency)
	{
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * 将一个字符串表示为货币额。
	 */
	public Money(String amount)
		throws NumberFormatException
	{
		this.amount = new BigDecimal(amount);
		this.currency = Currency.getInstance(Locale.getDefault());
	}

	public Money(String amount, Currency currency)
	{
		this.amount = new BigDecimal(amount);
		this.currency = currency;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	/**
	 * 将一个字符串表示的货币额转换为 long 型，其值为货币额乘以 100。
	 */
	public static long formatInputMoney(String amount)
	{
		if(amount == null){
			return 0L;
		}
		try{
			Money m = new Money(amount);
			return m.getAmount().longValue() * 100;
		}catch(NumberFormatException ne){
			return 0L;
		}
	}

	public static String getMoneyDisplay(long amount, int floatSize)
	{
		NumberFormat format = NumberFormat.getInstance();
		if(floatSize == 2){
			long m = amount / 100L;
			long f = amount - m * 100L;
			return format.format(m) + "." + (f <= 9L ? "0" + f : Long.toString(f));
		}
		return format.format(amount);
	}

	public static String getMoneyDisplayForInput(long amount, int floatSize)
	{
		NumberFormat format = NumberFormat.getInstance();
		if(floatSize == 2){
			long m = amount / 100L;
			long f = amount - m * 100L;
			return m + "." + (f <= 9L ? "0" + f : Long.toString(f));
		}
		return format.format(amount);
	}
}
