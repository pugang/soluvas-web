package org.soluvas.web.site.widget;

import java.math.BigDecimal;
import java.util.Locale;

import javax.annotation.Nullable;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

/**
 * Formats a {@link CurrencyUnit} code as symbol in current {@link Locale},
 * optionally with a {@link BigDecimal} amount.
 * @author agus
 */
@SuppressWarnings("serial")
public class CurrencyLabel extends Label {

	@Nullable
	private IModel<BigDecimal> amountModel;

	public CurrencyLabel(String id, IModel<?> model) {
		super(id, model);
	}

	public CurrencyLabel(String id, CurrencyUnit currency) {
		super(id, currency);
	}
	
	/**
	 * @param id
	 * @param currencyModel Can be either a {@link CurrencyUnit} or currency code {@link String}.
	 * @param amountModel
	 */
	public CurrencyLabel(String id, IModel<?> currencyModel, IModel<BigDecimal> amountModel) {
		super(id, currencyModel);
		this.amountModel = amountModel;
	}

	public CurrencyLabel(String id, CurrencyUnit currency, BigDecimal amount) {
		super(id, currency);
		this.amountModel = new Model<>(amount);
	}
	
	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		replaceComponentTagBody(markupStream, openTag, getDefaultModelObjectAsFormattedString());
	}

	private String getDefaultModelObjectAsFormattedString() {
		final Object currencyObj = getDefaultModelObject();
		if (currencyObj != null) {
			final CurrencyUnit currency = currencyObj instanceof CurrencyUnit ? 
					(CurrencyUnit) currencyObj : CurrencyUnit.of((String) currencyObj);
			final BigDecimal amount = amountModel != null ? amountModel.getObject() : null;
			final Locale locale = getLocale();
			if (amount != null) {
				final MoneyFormatter formatter = new MoneyFormatterBuilder()
					.appendAmountLocalized().toFormatter(locale);
				return "<small class=\"muted\">" + currency.getSymbol(locale) + "</small>" + formatter.print(BigMoney.of(currency, amount));
			} else {
				return currency.getSymbol(locale);
			}
//			return "IDR";
		} else {
			return "";
		}
	}
	
}
