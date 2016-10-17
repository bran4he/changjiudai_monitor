import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestGetContent {

	private static final String MONITOR_URL = "http://www.changjiudai.com/index.php?invest";
	private static Properties props = new Properties();
	private static final String PROP_PREFIX = "P.";
	private static final String TARGET_MONTH = "monitor.month";
	private static final int[] monthArr = new int[] { 0, 1, 2, 3, 4, 0, 6, 0, 0, 9, 0, 11, 12};
	private static final String UPDATE_MINUTES = "update.minutes";
	private static final int DEFAULT_UPDATE_MINUTES= 10;
	private static boolean isContinue = true;
	
	static{
		try {
			props.load(TestGetContent.class.getResourceAsStream("init.properties"));
			Properties prop = System.getProperties();
			
			Enumeration<Object> keys = props.keys();
			while(keys.hasMoreElements()){
				String key = (String) keys.nextElement();
				String value = props.getProperty(key);
				if(key.startsWith("P.")){
					System.out.println("get program param with key:"+key+" and value:"+value);
				}else{
					prop.setProperty(key, value);
					System.out.println("set system property with key:"+key+" and value:"+value);
				}
			}
			
		} catch (IOException e) {
			System.out.println("��ȡ init.properties �ļ�����");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		
		String tempMinutes = (String) props.get(PROP_PREFIX + UPDATE_MINUTES);
		int waitMinutes = DEFAULT_UPDATE_MINUTES;
		if(null == tempMinutes || tempMinutes ==""){
			System.out.println("init.propertiesû������["+PROP_PREFIX + UPDATE_MINUTES+"], ʹ��Ĭ�ϸ���ʱ�䣺"+DEFAULT_UPDATE_MINUTES+"���ӣ�");
		}else{
			waitMinutes = Integer.valueOf(tempMinutes);
			System.out.println("���ø���ʱ��Ϊ��"+waitMinutes+"���ӣ�");
		}
		
		
		int count = 1;
		while(isContinue){
			
			Document doc = Jsoup.connect(MONITOR_URL).timeout(10000).userAgent("Mozilla").get();
			Elements contents = doc.select(".cont1");
			for(Element ele : contents){
				System.out.println("--------------bacth " + count + " start---------------");
				System.out.println(ele.select(".fleft a").attr("title"));
				String text = ele.select(".table li").get(1).text();
//				String text = ele.select(".table li").get(1).html();
				System.out.println(text);
				int month = getMonth(text);
				if(String.valueOf(month).equals(props.get(PROP_PREFIX + TARGET_MONTH))){
					System.out.println("+++++++++++++++++++\n��������±귢���ˣ�"+month+"�±�\n+++++++++++++++++++");
					//JOptionPane.showMessageDialog(null, text, TARGET_MONTH+" Month Target Notification",JOptionPane.WARNING_MESSAGE); 
					int value = JOptionPane.showConfirmDialog(null, text, month+"�±귢������" ,JOptionPane.YES_NO_OPTION);
					if (value == JOptionPane.OK_OPTION) {
						isContinue = true;
						break;
					}else if(value == JOptionPane.NO_OPTION || value == JOptionPane.CLOSED_OPTION){
						isContinue = false;
						break;
					}
				}
			}
			System.out.println("--------------bacth " + count + " end---------------");
			if(isContinue){
				count++;
				try {
					TimeUnit.MINUTES.sleep(waitMinutes);
//					TimeUnit.SECONDS.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("--------------final end---------------");
		System.exit(0);
		
	}

	// 1,2,3,4,5,6,9,12
	private static int getMonth(String text) {
		int month = 0;
		for (int i = monthArr.length - 1; i >= 0; i--) {
			if (text.contains(" " + monthArr[i] + "")) {
				month = i;
				break;
			}
		}
		return month;
	}


}
