package com.galaxy.appupload.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * 生成32位uuid
 * @author liuli
 * @create date 2017-03-19
 * 
 */
public class UUIDGenerator {
	List<String> list = new ArrayList<String>(); // 存放数据

	public UUIDGenerator() {
	}

	/**
	 * 随机生成uuid, 去掉"-"符号
	 * @param   
	 * @return 返回 String
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	// 获得指定数量的UUID
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
	 * 随机生成一个10位数的数字
	 * @param   
	 * @return 返回 String
	 */
	public static String getRandomNumber() {
		Random r = new Random();
		String str = "";
		for (int i = 0; i < 10; i++) {// 循环10次
			Integer x = r.nextInt(10); // 0-9的随机数
			str += x.toString(); // 拼成10位数 因为int类型只能存放200000000+的数据，所以只能用字符串拼接
		}
		
		return str;
	}

	/**
	 * 随机生成一个10位数的数字和字母组合
	 * @param   
	 * @return 返回 String
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

