package com.example.raiwayticketreservation.Config;

public class MomoConfig {
//	public static String PARTNER_CODE = "MOMOLRJZ20181206";
//	public static String ACCESS_KEY = "mTCKt9W3eU1m39TW";
//	public static String SECRET_KEY = "SetA5RDnLHvt51AULf51DyauxUo3kDU6";
	public static String PARTNER_CODE = "MOMONPMB20210629";
	public static String ACCESS_KEY = "Q2XhhSdgpKUlQ4Ky";
	public static String SECRET_KEY = "k6B53GQKSjktZGJBK2MyrDa7w9S6RyCf";
	public static String PARTNER_NAME = "Railway VN";
	public static String REQUEST_TYPE = "captureWallet";
	public static String LANG = "vi";

	public static String PAY_QUERY_STATUS_URL = "https://test-payment.momo.vn/pay/query-status";
	public static String PAY_CONFIRM_URL = "https://test-payment.momo.vn/pay/confirm";
	public static String RETURN_URL = "http://localhost:8080/api/momo/test";
	public static String NOTIFY_URL = "http://localhost:8080/failure/payment";
	public static String IPN_URL = "https://fcf6-123-24-233-164.ngrok.io";
	public static String CREATE_ORDER_URL = "https://test-payment.momo.vn/v2/gateway/api/create";
	public static String REDIRECT_URL = "http://localhost:8080/success/payment";
}