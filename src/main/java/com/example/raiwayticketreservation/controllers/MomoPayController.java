package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.config.MomoConfig;
import com.example.raiwayticketreservation.entities.CTHD;
import com.example.raiwayticketreservation.entities.HoaDon;
import com.example.raiwayticketreservation.entities.KhachDatVe;
import com.example.raiwayticketreservation.entities.VeTau;
import com.example.raiwayticketreservation.service.*;
import com.example.raiwayticketreservation.constants.MoMoConstant;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.HoanTatThanhToanInfoRequest;
import com.example.raiwayticketreservation.dtos.responses.ThanhToanResponse;
import com.example.raiwayticketreservation.utils.MomoEncoderUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@RestController
@RequestMapping(value = "/v1/khachhang/thanhtoan")
public class MomoPayController {

	@Autowired
	private HoaDonService hoaDonService;

	@Autowired
	private CTHDService cthdService;

	@Autowired
	private VeTauService veTauService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TrangThaiGheService trangThaiGheService;

	// tạo thanh toán, response trả về pay url
	@CrossOrigin(origins = "http://localhost:4200")
	@Operation(summary = "Tạo thanh toán MoMo",
			description = "Sau khi thực thi API mua vé sẽ có thực thi API này để trả về link QR MoMo để thanh toán",
			tags = "API Thanh toán")
	@GetMapping(value = "/thanhtoanmomo/{amount}/{orderId}")
	@JsonCreator
	public Map<String, Object> taoThanhToanMoMo(@PathVariable Long amount, @PathVariable Long orderId)
			throws InvalidKeyException, NoSuchAlgorithmException, ClientProtocolException, IOException {

		JSONObject json = new JSONObject();
		String requestId = "MM" + System.currentTimeMillis();
		String partnerCode = MomoConfig.PARTNER_CODE;
		String accessKey = MomoConfig.ACCESS_KEY;
		String secretKey = MomoConfig.SECRET_KEY;
		String returnUrl = MomoConfig.REDIRECT_URL;
		String notifyUrl = MomoConfig.NOTIFY_URL;
		String extraData = "";
		String orderInfo = "Thanh toan ve tau MoMo";
		String requestType = MomoConfig.REQUEST_TYPE;
		json.put("orderInfo", orderInfo);
		json.put("partnerCode", partnerCode);
		json.put("partnerName", MomoConfig.PARTNER_NAME);
		json.put("requestId", requestId);
		json.put("amount", amount);
		json.put("extraData", extraData);
		json.put("orderId", orderId.toString());
		json.put("storeId", "railwayvn");
		json.put("autoCapture", true);
		json.put("redirectUrl", returnUrl);
		json.put("ipnUrl", notifyUrl);
		json.put("requestType", requestType);
		json.put("lang", MomoConfig.LANG);
		json.put("startTime", System.currentTimeMillis());

		String requestRawData = new StringBuilder()
				.append(MoMoConstant.ACCESS_KEY).append("=").append(accessKey).append("&")
				.append(MoMoConstant.AMOUNT).append("=").append(amount).append("&")
				.append(MoMoConstant.EXTRA_DATA).append("=").append(extraData).append("&")
				.append(MoMoConstant.IPN_URL).append("=").append(notifyUrl).append("&")
				.append(MoMoConstant.ORDER_ID).append("=").append(orderId).append("&")
				.append(MoMoConstant.ORDER_INFO).append("=").append(orderInfo).append("&")
				.append(MoMoConstant.PARTNER_CODE).append("=").append(partnerCode).append("&")
				.append(MoMoConstant.REDIRECT_URL).append("=").append(returnUrl).append("&")
				.append(MoMoConstant.REQUEST_ID).append("=").append(requestId).append("&")
				.append(MoMoConstant.REQUEST_TYPE).append("=").append(requestType)
				.toString();
		System.out.println(requestRawData);
		String hashData = MomoEncoderUtils.signHmacSHA256(requestRawData, secretKey);
		json.put("signature", hashData);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(MomoConfig.CREATE_ORDER_URL);
		StringEntity stringEntity = new StringEntity(json.toString());
		post.setHeader("content-type", "application/json");
		post.setEntity(stringEntity);

		CloseableHttpResponse res = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
		StringBuilder resultJsonStr = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			resultJsonStr.append(line);
		}
		JSONObject result = new JSONObject(resultJsonStr.toString());
		return result.toMap();
	}
	@PostMapping("/trangthai")
	public ResponseEntity trangThaiThanhToan(@RequestBody HoanTatThanhToanInfoRequest hoanTatThanhToanInfoRequest) {
		final DecimalFormat df = new DecimalFormat("###,###,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		Long orderId = Long.valueOf(hoanTatThanhToanInfoRequest.getOrderId());
		Long resultCode = Long.valueOf(hoanTatThanhToanInfoRequest.getResultCode());
		if(resultCode == 0) {
			String maDatVe = "";
			Random random = new Random();
			int numRand = random.nextInt(999999999);
			maDatVe = 1 + String.format("%09d", numRand);
			String trangThai = SystemConstant.DA_THANH_TOAN;

			Set<VeTau> veTaus = veTauService.getVeTauByMaDatCho(orderId);
			veTaus.forEach(veTau -> {
				veTauService.capNhatTrangThaiTinhTrangVeTau(veTau.getId(), SystemConstant.DA_MUA);
				trangThaiGheService.capNhatThoiHanGiuGheTheoMaVe(veTau.getMaVe());
			});

			List<VeTau> veTauList = veTaus.stream().toList();
			String emailKhachDat = veTauList.get(0).getKhachDatVe().getEmail();
			Date ngayLap = veTauList.get(0).getNgayMua();
			KhachDatVe khachDatVeID = KhachDatVe.builder().id(veTauList.get(0).getKhachDatVe().getId()).build();

			Set<VeTau> veTauDaCapNhat = veTauService.getVeTauByMaDatCho(orderId);

			ThanhToanResponse thanhToanResponse = ThanhToanResponse.builder()
					.maDatVe(maDatVe)
					.veTaus(veTauDaCapNhat)
					.build();

			double tongGia = 0;
			for (int i = 0; i < veTauList.size(); i++) {
				tongGia += veTauList.get(i).getDonGia();
			}

			//Thêm hóa đơn và chi tiết hóa đơn
            HoaDon hoaDon = HoaDon.builder()
                    .hinhThucThanhToan(SystemConstant.THANH_TOAN_MOMO)
                    .ngayLap(ngayLap)
                    .khachDatVe(khachDatVeID)
                    .maDatVe(maDatVe)
                    .tinhTrang(trangThai)
                    .trangThai(1)
                    .build();

            HoaDon hoaDonReturn = hoaDonService.themHoaDon(hoaDon);

			Set<CTHD> cthds = new HashSet<>();
            veTauList.forEach(veTau -> {
                VeTau veTauID = VeTau.builder().id(veTau.getId()).build();
                CTHD cthd = CTHD.builder().donGia(veTau.getDonGia())
                        .hoaDon(hoaDonReturn)
                        .veTau(veTauID)
                        .build();
                cthds.add(cthd);
            });
            cthdService.themCTHD(cthds);
			df.format(tongGia).replace(",", ".");
			Map<String, Object> variables = new HashMap<>();
			variables.put("maDatVe", maDatVe);
			variables.put("soVe", veTauList.size());
			variables.put("tongTien", tongGia);
			variables.put("veTaus", veTauList);
			try {
				emailService.guiVeTauMessage(emailKhachDat,
						"Railway VN - Thanh toán vé thành công", variables);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return new ResponseEntity(thanhToanResponse, HttpStatus.OK);
		} else {
			String maDatCho = String.valueOf(hoanTatThanhToanInfoRequest.getOrderId());
			Set<VeTau> veTaus = veTauService.getVeTauByMaDatCho(orderId);

			List<VeTau> veTauList = veTaus.stream().toList();
			String emailKhachDat = veTauList.get(0).getKhachDatVe().getEmail();
			ThanhToanResponse thanhToanResponse = ThanhToanResponse.builder()
					.veTaus(veTaus)
					.maDatCho(maDatCho)
					.build();
			double tongGia = 0;

			for (int i = 0; i < veTauList.size(); i++) {
				tongGia += veTauList.get(i).getDonGia();
			}
			Map<String, Object> variables = new HashMap<>();
			variables.put("maDatCho", maDatCho);
			variables.put("soVe", veTauList.size());
			variables.put("tongTien", tongGia);

			try {
				emailService.thanhToanVeKhongThanhCongMessage(emailKhachDat,
						"Railway VN - Thanh toán vé không thành công", variables);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return new ResponseEntity(thanhToanResponse, HttpStatus.OK);
		}
	}

	// truy vấn lại trạng thái thanh toán
	@PostMapping(value = "/transactionStatus")
	public Map<String, Object> transactionStatus(HttpServletRequest request, @RequestParam String requestId,
			@RequestParam String orderId)
			throws InvalidKeyException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		JSONObject json = new JSONObject();
		String partnerCode = MomoConfig.PARTNER_CODE;
		String accessKey = MomoConfig.ACCESS_KEY;
		String secretKey = MomoConfig.SECRET_KEY;
		json.put("partnerCode", partnerCode);
		json.put("accessKey", accessKey);
		json.put("requestId", requestId);
		json.put("orderId", orderId);
		json.put("requestType", "transactionStatus");

		String data = "partnerCode=" + partnerCode + "&accessKey=" + accessKey + "&requestId=" + json.get("requestId")
				+ "&orderId=" + json.get("orderId") + "&requestType=" + json.get("requestType");
		String hashData = MomoEncoderUtils.signHmacSHA256(data, secretKey);
		json.put("signature", hashData);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(MomoConfig.CREATE_ORDER_URL);
		StringEntity stringEntity = new StringEntity(json.toString());
		post.setHeader("content-type", "application/json");
		post.setEntity(stringEntity);

		CloseableHttpResponse res = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
		StringBuilder resultJsonStr = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			resultJsonStr.append(line);
		}
		JSONObject result = new JSONObject(resultJsonStr.toString());
		Map<String, Object> kq = new HashMap<>();
		kq.put("requestId", result.get("requestId"));
		kq.put("orderId", result.get("orderId"));
		kq.put("extraData", result.get("extraData"));
		kq.put("amount", Long.parseLong(result.get("amount").toString()));
		kq.put("transId", result.get("transId"));
		kq.put("payType", result.get("payType"));
		kq.put("errorCode", result.get("errorCode"));
		kq.put("message", result.get("message"));
		kq.put("localMessage", result.get("localMessage"));
		kq.put("requestType", result.get("requestType"));
		kq.put("signature", result.get("signature"));	
		return kq;
	}

}
