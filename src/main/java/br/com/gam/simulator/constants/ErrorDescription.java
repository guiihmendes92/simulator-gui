package br.com.gam.simulator.constants;

import java.util.Optional;

public enum ErrorDescription {
	
	OK,
	SYSERR_DB("sys.error.db"),
	SYSERR_TXN("sys.error.txn"),
	INVALID_REQUEST("invalid.request"),
	INVALID_CONTEXT("1801"),
	MISSING_FIELDS("missing.fields"),
	EXTRA_FIELDS("extra.fields"),
	INVALID_CARD("invalid.card"),
	INVALID_AMOUNT("invalid.amount"),
	AMOUNT_HIGH("amount.high"),
	CARD_NOT_FOUND("card.not.found"),
	CARD_NOT_ACTIVE("card.not.active"),
	CARD_EXPIRED("card.expired"),
	CARD_NOT_CONFIGURED("card.not.configured"), //
	CARD_EMV("emv.card.without.cryptography"),
	CARD_SUSPICIOUS("card.suspicious"),
	CARD_SUSPENDED("card.suspended"),
	CARD_STOLEN("card.stolen"),
	CARD_LOST("card.lost"),
	CARDHOLDER_NOT_ACTIVE("cardholder.not.active"),
	CARDHOLDER_NOT_CONFIGURED("cardholder.not.configured"),
	CARDHOLDER_NOT_FOUND("cardholder.not.found"),
	CARDHOLDER_EXPIRED("cardholder.expired"),
	ACCOUNT_NOT_FOUND("account.not.found"),
	DESTINATION_NOT_FOUND("destination.not.found"),
	NOT_SUFFICIENT_FUNDS("not.sufficient.funds"),  //
	ORIGINAL_NOT_FOUND("original.not.found"), //
	ORIGINAL_DENIED("original.denied"),
	NOT_FOUND("not.found"),
	PREVIOUSLY_CONFIRMED("previously.confirmed"), //
	PREVIOUSLY_PROBED("previously.probed"),
	PREVIOUSLY_REVERSED("previously.reversed"),
	PREVIOUSLY_VOIDED("previously.voided"),
	USAGE_LIMIT_REACHED("usage.limit.reached"),
	SYSTEM_ERROR("system.error"),
	ACTIVITY_PREVENTS_REVERSAL("activity.prevents.reversal"),
	ACTIVITY_PREVENTS_VOID("activity.prevents.void"),
	INVALID_TERMINAL("invalid.terminal"),
	INACTIVE_TERMINAL("inactive.terminal"),
	INVALID_MERCHANT("invalid.merchant"),
	INACTIVE_MERCHANT("inactive.merchant"),
	ACQUIRER_MISMATCH("acquirer.mismatch"),
	INACTIVE_ACQUIRER_CAPTURE_NETWORK("inactive.acquirer.capture.network"),
	INACTIVE_ACQUIRER_TRANSACTION("inactive.acquirer.transaction"),
	INACTIVE_BIN("inactive.bin"),
	INACTIVE_DESTINATION("inactive.destination"),
	INACTIVE_FLAG_PRODUCT("inactive.flag.product"),
	INACTIVE_FLAG("inactive.flag"),
	INVALID_ACQUIRER_CAPTURE_NETWORK("invalid.acquirer.capture.network"),
	INVALID_ACQUIRER_TRANSACTION("invalid.acquirer.transaction"),
	INVALID_BIN("invalid.bin"),
	DUPLICATE_ENTITY("duplicate.entity"),
	BAD_DATA("bad.data"),
	UNKNOWN_CARDPRODUCT("unknown.cardproduct"),
	UNCONFIGURED_POINT("unconfigured.point"),
	HOST_UNREACHABLE("host.unreachable"),
	INVALID_PINBLK("invalid.pinblk"),
	INVALID_CRYPTOGRAM("invalid.cryptogram");
	
	private String value;
	
	ErrorDescription(final String value) {
		this.value = value;
	}
	
	ErrorDescription() {
	}
	
	@Override
	public String toString() {
		return Optional.ofNullable(value) //
		               .orElse(name());
	}
	
}