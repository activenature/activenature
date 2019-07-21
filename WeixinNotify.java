import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.servlet.http.*;


public class WeixinNotify {

static Logger logger= Logger.getLogger(WeixinNotify.class.getName());
	
public void weixin_notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		InputStream inputStream ;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s ;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null){
			sb.append(s);
		}
		in.close();
		inputStream.close();
 
		//parse xml-->map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());
		
		// TreeMap
		SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();		
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);
			
			String v = "";
			if(null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
		
        String key = PayConfigUtil.API_KEY; // key
 
        logger.info(packageParams.toString());
	    //sign is correct?
	    if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {
	        //------------------------------
	        //begin to deal with business
	        //------------------------------
	        String resXml = "";
	        if("SUCCESS".equals((String)packageParams.get("result_code"))){
	        	
	        	String mch_id = (String)packageParams.get("mch_id");
	        	String openid = (String)packageParams.get("openid");
	        	String is_subscribe = (String)packageParams.get("is_subscribe");
	        	String out_trade_no = (String)packageParams.get("out_trade_no");
	        	
	        	String total_fee = (String)packageParams.get("total_fee");
	        	
	        	logger.info("mch_id:"+mch_id);
	        	logger.info("openid:"+openid);
	        	logger.info("is_subscribe:"+is_subscribe);
	        	logger.info("out_trade_no:"+out_trade_no);
	        	logger.info("total_fee:"+total_fee);
	            
	            //////////business process////////////////
	        	
	        	logger.info("payment is successful.");
	            //notify weixin
	            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
	                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
	            
	        } else {
	        	logger.info("payment fails：" + packageParams.get("err_code"));
	            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
	                    + "<return_msg><![CDATA[message is null]]></return_msg>" + "</xml> ";
	        }
	        
	        BufferedOutputStream out = new BufferedOutputStream(
	                response.getOutputStream());
	        out.write(resXml.getBytes());
	        out.flush();
	        out.close();
	    } else{
	    	logger.info("sign is wrong.");
	    }
	    
	}


}
