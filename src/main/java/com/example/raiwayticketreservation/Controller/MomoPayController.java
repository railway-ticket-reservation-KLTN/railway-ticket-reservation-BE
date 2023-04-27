package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Config.MomoConfig;
import com.example.raiwayticketreservation.Entity.CTHD;
import com.example.raiwayticketreservation.Entity.HoaDon;
import com.example.raiwayticketreservation.Entity.KhachDatVe;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Service.CTHDService;
import com.example.raiwayticketreservation.Service.EmailService;
import com.example.raiwayticketreservation.Service.HoaDonService;
import com.example.raiwayticketreservation.Service.VeTauService;
import com.example.raiwayticketreservation.constants.MoMoConstant;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.responses.ThanhToanResponse;
import com.example.raiwayticketreservation.utils.MomoEncoderUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/v1/thanhtoan")
public class MomoPayController {

	@Autowired
	private HoaDonService hoaDonService;

	@Autowired
	private CTHDService cthdService;

	@Autowired
	private VeTauService veTauService;

	@Autowired
	private EmailService emailService;

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
	@GetMapping("/trangthai")
	public Object trangThaiThanhToan(@RequestParam String partnerCode, @RequestParam Long orderId, @RequestParam Long amount, @RequestParam String orderInfo,
									 @RequestParam String orderType, @RequestParam String message, @RequestParam Long resultCode) {
		final DecimalFormat df = new DecimalFormat("###,###,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		if(resultCode == 0) {
			String maDatVe = "";
			Random random = new Random();
			int numRand = random.nextInt(999999999);
			maDatVe = 1 + String.format("%09d", numRand);
			String trangThai = SystemConstant.DA_THANH_TOAN;

			Set<VeTau> veTaus = veTauService.getVeTauByMaDatCho(orderId);
			veTaus.forEach(veTau -> {
				veTauService.capNhatTrangThaiTinhTrangVeTau(veTau.getId(), SystemConstant.DA_MUA);
			});
			Set<VeTau> veTauDaCapNhat = veTauService.getVeTauByMaDatCho(orderId);

			List<VeTau> veTauList = veTaus.stream().toList();
			String emailKhachDat = veTauList.get(0).getKhachDatVe().getEmail();
			Date ngayLap = veTauList.get(0).getNgayMua();
			KhachDatVe khachDatVeID = KhachDatVe.builder().id(veTauList.get(0).getKhachDatVe().getId()).build();

			ThanhToanResponse thanhToanResponse = ThanhToanResponse.builder()
					.maDatVe(maDatVe)
					.veTauSet(veTauDaCapNhat)
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

			emailService.sendMessage(emailKhachDat, "Railway VN - Thanh toán vé thành công", "Kính gửi quý Khách hàng,\n" +
					"\n" +
					"Xin trân trọng cảm ơn quý khách đã lựa chọn sử dung dịch vụ của Railway VN\n" +
					"Chúng tôi xin thông báo quý khách đã thực hiện đặt vé thành công với thông tin như sau:\n" +
					"\n" +
					"Mã đặt vé là: "+ maDatVe +"\n" +
					"Tổng số vé: "+ veTaus.size() +"\n" +
					"Tổng số tiền: "+  df.format(tongGia).replace(",", ".")  +"VNĐ\n" +
					"\n"+
					"Quý khách vui lòng vào trang Railway VN vào phần kiểm tra vé nhập các thông tin cần thiết để kiểm tra thông tin vé."+
					"\n" +
					"\n"+
					"Đây là email gửi tự động. Xin vui lòng không trả lời email này.\n" +
					"Quý khách có thể liên hệ với trung tâm hỗ trợ khách hàng 1900 0009 để được trợ giúp.\n" +
					"Xin Trân trọng cảm ơn!\n" +
					"\n" +
					"Railway VN.");
			return thanhToanResponse;
		} else {
			String maDatCho = String.valueOf(orderId);
			Set<VeTau> veTaus = veTauService.getVeTauByMaDatCho(orderId);

			List<VeTau> veTauList = veTaus.stream().toList();
			String emailKhachDat = veTauList.get(0).getKhachDatVe().getEmail();
			ThanhToanResponse thanhToanResponse = ThanhToanResponse.builder()
					.veTauSet(veTaus)
					.maDatCho(maDatCho)
					.build();
			double tongGia = 0;

			for (int i = 0; i < veTauList.size(); i++) {
				tongGia += veTauList.get(i).getDonGia();
			}
			emailService.sendMessage(emailKhachDat, "Railway VN - Thanh toán vé không thành công", "Kính gửi quý Khách hàng,\n" +
					"\n" +
					"Xin trân trọng cảm ơn quý khách đã lựa chọn sử dung dịch vụ của Railway VN\n" +
					"Chúng tôi xin thông báo quý khách đã thực hiện đặt vé không thành công với thông tin như sau:\n" +
					"\n" +
					"Mã đặt chỗ là: "+ maDatCho +"\n" +
					"Tổng số vé: "+ veTaus.size() +"\n" +
					"Tổng số tiền: "+ df.format(tongGia).replace(",", ".") +" VNĐ\n" +
					"\n"+
					"Quý khách vui lòng đến trực tiếp nhà ga để thanh toán vé. Quý khách lưu ý vé chỉ được giữ trong vòng 24 giờ kể từ khi đặt vé."+
					"\n" +
					"\n"+
					"Đây là email gửi tự động. Xin vui lòng không trả lời email này.\n" +
					"Quý khách có thể liên hệ với trung tâm hỗ trợ khách hàng 1900 0009 để được trợ giúp.\n" +
					"Xin Trân trọng cảm ơn!\n" +
					"\n" +
					"Railway VN.");
			return thanhToanResponse;
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
