import java.net.URLEncoder;


public class QR {
	
	public static String QRfromGoogle(String chl) throws Exception {
		int widhtHeight = 300;
		String EC_level = "L";
		int margin = 0;
		chl = UrlEncode(chl);
		String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight
				+ "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;
 
		return QRfromGoogle;
	}

	public static String UrlEncode(String src)  throws Exception {
		return URLEncoder.encode(src, "UTF-8").replace("+", "%20");
	}



}
