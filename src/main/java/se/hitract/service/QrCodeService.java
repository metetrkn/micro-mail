package se.hitract.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class QrCodeService {

	MultiFormatWriter writer = new MultiFormatWriter();

	public String createQrCode(String content) {

		String image = "";
		try {
			int imageSize = 250;
	        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, imageSize, imageSize);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        MatrixToImageWriter.writeToStream(matrix, "png", bos);
	        image = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode
		} catch(Exception e) {
			
		}
        
        return image;
	}
	

}
