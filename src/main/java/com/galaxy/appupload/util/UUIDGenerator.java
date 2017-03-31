package com.galaxy.appupload.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * ����32λuuid
 * @author liuli
 * @create date 2017-03-19
 * 
 */
public class UUIDGenerator {
	List<String> list = new ArrayList<String>(); // �������

	public UUIDGenerator() {
	}

	/**
	 * �������uuid, ȥ��"-"����
	 * @param   
	 * @return ���� String
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// ȥ��"-"����
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	// ���ָ��������UUID
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	/**
	 * �������һ��10λ��������
	 * @param   
	 * @return ���� String
	 */
	public static String getRandomNumber() {
		Random r = new Random();
		String str = "";
		for (int i = 0; i < 10; i++) {// ѭ��10��
			Integer x = r.nextInt(10); // 0-9�������
			str += x.toString(); // ƴ��10λ�� ��Ϊint����ֻ�ܴ��200000000+�����ݣ�����ֻ�����ַ���ƴ��
		}
		
		return str;
	}

	/**
	 * �������һ��10λ�������ֺ���ĸ���
	 * @param   
	 * @return ���� String
	 */
	public static String GetRandomString(int Len) {
        String[] baseString={"0","1","2","3",
                "4","5","6","7","8","9",
                "a","b","c","d","e",
                "f","g","h","i","j",
                "k","l","m","n","o",
                "p","q","r","s","t",
                "u","v","w","x","y",
                "z","A","B","C","D",
                "E","F","G","H","I",
                "J","K","L","M","N",
                "O","P","Q","R","S",
                "T","U","V","W","X","Y","Z"};
        Random random = new Random();
        int length=baseString.length;
        String randomString="";
        for(int i=0;i<length;i++){
            randomString+=baseString[random.nextInt(length)];
        }
        random = new Random(System.currentTimeMillis());
        String resultStr="";
        for (int i = 0; i < Len; i++) {
            resultStr += randomString.charAt(random.nextInt(randomString.length()-1));
        }
        return resultStr;
    }

}

