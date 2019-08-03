package cn.com.webxml;

public class RunWeather {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		WeatherWS wt=new WeatherWS();
		WeatherWSSoap wsoap=wt.getWeatherWSSoap();
		ArrayOfString tianqi=wsoap.getWeather("","");
		for(String s: tianqi.getString()){
			System.out.println(s);
		}

	}

}
