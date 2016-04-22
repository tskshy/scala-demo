package dsl

import dsl.Currency.{Rate, USD, EUR, GBP}

object T {
	def run = {
		val rate_0: Rate = Map(
			(GBP, EUR) -> 1.39,
			(EUR, USD) -> 1.08,
			(GBP, USD) -> 1.5
		)

		val r0 = (Money(USD, 42)(rate_0) + Money(EUR, 35)(rate_0)) to GBP
		println("r0: " + r0.currency.name + " " + r0.currency.code + " " + r0.amount)

		//==========================
		implicit val rate_1: Rate = Map(
			(GBP, EUR) -> 1.39,
			(EUR, USD) -> 1.08,
			(GBP, USD) -> 1.5
		)//for r1 and r2

		import Currency.IntExt
		val r1 = (42(USD) + 35(EUR)) to GBP
		println("r1: " + r1.currency.name + " " + r1.currency.code + " " + r1.amount)

		//==========================

		import Currency.CurrIntExt
		val r2 = (USD(42) + EUR(35)) to GBP
		println("r2: " + r2.currency.name + " " + r2.currency.code + " " + r2.amount)

	}
}

object Currency {
	type Rate = Map[(Currency, Currency), BigDecimal] //first_currency / second_currency = rate: BigDecimal

	def apply (code: String, name: String) = new Currency(code, name)

	/*
	def apply (code: String) = code.toLowerCase match {
		case "USD" => USD
		case "EUR" => EUR
		case "GBP" => GBP
	}
	*/

	lazy val USD: Currency = Currency("USD", "美元")
	lazy val EUR: Currency = Currency("EUR", "欧元")
	lazy val GBP: Currency = Currency("GBP", "英镑")

	def convert (from: Currency, to: Currency, rate: Rate): BigDecimal = {
		if (from.code.equalsIgnoreCase(to.code))
			1
		else
			rate.getOrElse((from, to), 1 / rate((to, from)))
	}

	implicit class BigDecimalExt (value: BigDecimal) {
		def apply(currency: Currency)(implicit rate: Rate): Money = Money(currency, value)
	}

	implicit class IntExt (value: Int) {
		def apply(currency: Currency)(implicit rate: Rate): Money = (value: BigDecimal).apply(currency)
	}

	implicit class CurrIntExt (c: Currency) {
		def apply(value: Int)(implicit rate: Rate): Money = Money(c, value)(rate)
	}

}


class Currency (val code: String, val name: String = "未知")

case class Money (currency: Currency,amount: BigDecimal)(implicit rate: Rate) {
	def to (to: Currency): Money = {
		val rates = Currency.convert(currency, to, rate)
		Money(to, rates * amount)
	}

	def operation (that: Money, operate: (BigDecimal, BigDecimal) => BigDecimal): Money = {
		that match {
			case Money(curr, am) if (currency.code equalsIgnoreCase curr.code) => Money(currency, operate(amount, am))
			case Money(curr, am) => operation(that.to(currency), operate)
		}
	}

	def + (that: Money): Money = {
		operation(that, (a, b)=> a + b) //占位符写法 operation(that, _ + _)
	}
}




