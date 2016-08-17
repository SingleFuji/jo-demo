package com.jo.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.jo.demo.common.Constants;

/**
 * 字符串处理
 * @author wujun
 *
 */
public class StringUtils {
	
	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	private static String hexString = "0123456789ABCDEF";
	public static String fromHexString2String(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
	
	/**
	 * 是否中文
	 * @param str
	 * @return
	 */
	public static boolean isChina(String str) {
		if(StringUtils.isEmpty(str)) {
			return false;
		}
		boolean flag = false;
		char[] array = str.toCharArray();
		if(null == array || array.length == 0) {
			return false;
		}
		for(char arr : array) {
			// 汉字
	        if (Character.getType(arr) == Character.OTHER_LETTER) {
	           flag = true;
	           break;
	        }
		}
		return flag;
	}
	
	/**
	 * 是否为空
	 * @param str
	 * @return
	 * {@link String#trim()}
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 ? true : false;
	}
	
	/**
	 * 是否不为空
	 * @param str
	 * @return
	 * {@link StringUtils#isEmpty(String)}
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	public static String toString(Object value){
		return value==null||"null".equals(value.toString())?"":value.toString(); 
	}
	public static String to19Date(Object value){
		return value==null||"null".equals(value.toString())?"":(value.toString().length()>=19?value.toString().substring(0, 19):value.toString());
	}
	public static String to10Date(Object value){
		return value==null||"null".equals(value.toString())?"":(value.toString().length()>=19?value.toString().substring(0, 10):value.toString());
	}
	public static float toFloat(Object value){
		return (value==null||"null".equals(value.toString()))?0:Float.parseFloat(value.toString().trim()); 
	}
	public static Double toDouble(Object value){
		return (value==null||"null".equals(value.toString()))?0:Double.parseDouble(value.toString().trim()); 
	}
	public static long toLong(Object value){
		return (value==null||"null".equals(value.toString()))?0:Long.parseLong(value.toString().trim()); 
	}
	public static long toShort(Object value){
		return (value==null||"null".equals(value.toString()))?0:Short.parseShort(value.toString().trim()); 
	}
	public static long toByte(Object value){
		return (value==null||"null".equals(value.toString()))?0:Byte.parseByte(value.toString().trim()); 
	}
	public static int toInt(Object value){
		return (value==null||"null".equals(value.toString()))?0:Integer.parseInt(value.toString().trim()); 
	}
	public static String firstFillZero(int value,int length){
		String rt = String.valueOf(value);
		while(rt.length()<length){
			rt = "0"+rt;
		}
		return rt;
	}
	public static boolean isNumeric(String str){ 
		if(null==str ||"".equals(str.trim())){
			return false;
		}
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 

	/**
	 * 16进制数组转化
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] from16HexString(String str) throws Exception
	{
		byte digest[] = new byte[str.length() / 2];
		for (int i = 0; i < digest.length; i++)
		{
			String byteString = str.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}
	
	/**
	 * 十六进制数转化
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String to16HexString(byte b[]) throws Exception
	{
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++)
		{
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}
	
	/**
	 * 将二进制字符串转换为byte数组
	 * 
	 * @param binary
	 * @return
	 */
	public static byte[] string2Bytes(String binary)
	{
		StringBuffer str = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		int length = binary.length();
		str.append(binary);
		byte[] result = null;
		if (length >= 8 && (length % 8 == 0))
		{
			result = new byte[length / 8];
		}
		else
		{
			int zero = (((length / 8) + 1) * 8) - length;
			for (int i = 0; i < zero; i++)
			{
				str.append("0");
			}
			result = new byte[length / 8 + 1];
		}

		int tempVar = 0;
		int r = 0;
		for (int i = 0; i < str.length(); i++)
		{
			temp.append(str.charAt(i));
			if ((i + 1) % 8 == 0)
			{
				r = ((i + 1) / 8) - 1;
				String eight = temp.toString();
				tempVar = Integer.parseInt(eight, 2);
				byte var = (byte) tempVar;
				result[r] = var;
				temp.delete(0, temp.length());
			}
		}
		return result;
	}
	
	/**
	 * 头字母小写
	 * 
	 * @param name
	 * @return
	 */
	public static String firstCharLowCase(final String name)
	{
		return name.substring(0, 1).toLowerCase().concat(name.substring(1));
	}

	/**
	 * 头字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String firstCharUpperCase(final String name)
	{
		return name.substring(0, 1).toUpperCase().concat(name.substring(1));
	}
	
	/**
	 * 首写字母变大写
	 * @param src
	 * @return
	 */
	public static String firstToUpper(String src)
	{
		String first = src.substring(0,1);
		String upper = first.toUpperCase();
		return src.replaceFirst(first,upper);
	}
	
	public static String strNumIncrease(String str, int n){
		int totalSize = str.length();
		int num = Integer.parseInt(str);
		num = num + n;
		String newNum = num + "";
		int curNum = newNum.length();
		for(int i=0;i<(totalSize-curNum);i++){
			newNum = "0" + newNum;
		}
		
		return newNum;
	}
	
	public static final byte[] hex2byte(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }
	
	public static final String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
	
	public static String convertStringToHex(String str){

		  char[] chars = str.toCharArray();

		  StringBuffer hex = new StringBuffer();
		  for(int i = 0; i < chars.length; i++){
		    hex.append(Integer.toHexString((int)chars[i]));
		  }

		  return hex.toString();
	}
	
	/**
	 * 汉字转为十六进制
	 * @param str
	 * @return
	 */
	public static String convertToHex(String str){
		try{
			byte[] resByte = str.getBytes(Constants.ENCODING);
//			byte[] resByte = str.getBytes("gbk");
			String resHex = byte2hex(resByte);
			return resHex;
		}catch(Exception e){
		}
		
		return null;
		
	}
	
	public static String AsciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result += d;
        }
        return result;
    }
	
	public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }
	
	private static byte asc_to_bcd(byte asc) {
	    byte bcd;

	    if ((asc >= '0') && (asc <= '9'))
	      bcd = (byte) (asc - '0');
	    else if ((asc >= 'A') && (asc <= 'F'))
	      bcd = (byte) (asc - 'A' + 10);
	    else if ((asc >= 'a') && (asc <= 'f'))
	      bcd = (byte) (asc - 'a' + 10);
	    else
	      bcd = (byte) (asc - 48);
	    return bcd;
	  }

	  public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
	    byte[] bcd = new byte[asc_len / 2];
	    int j = 0;
	    for (int i = 0; i < (asc_len + 1) / 2; i++) {
	      bcd[i] = asc_to_bcd(ascii[j++]);
	      bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
	    }
	    return bcd;
	  }

	  public static String bcd2Str(byte[] bytes) {
	    char temp[] = new char[bytes.length * 2], val;

	    for (int i = 0; i < bytes.length; i++) {
	      val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
	      temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

	      val = (char) (bytes[i] & 0x0f);
	      temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
	    }
	    return new String(temp);
	  }
	  
	  
	  /**
		 * 将数据按指定长度输出 不足右补0x20
		 * @param bytes
		 * @param len
		 * @return
		 */
		public static byte[] specifiedLengthBytes(byte[] bytes,int len)
		{
			if(bytes.length == len)
				return bytes;
			
			byte[] data = new byte[len];
			if(bytes.length > len)
			{
				System.arraycopy(bytes, 0, data, 0, len);
			}else{
				System.arraycopy(bytes, 0, data, 0, bytes.length);
				byte[] b = initBytes(len - bytes.length,(byte)0x20);
				System.arraycopy(b, 0, data, bytes.length, b.length);
			}
			return data;
		}
		
		/**
		 * 用指定数据初始化bytes
		 * @param len
		 * @param value
		 * @return
		 */
		public static byte[] initBytes(int len,byte value)
		{
			byte[] data = new byte[len];
			for(int i=0;i<len;i++)
			{
				data[i] = value;
			}
			return data;
		}
	  
	  
	  /**
		 * 使用zip进行压缩
		 * 
		 * @param str
		 *            压缩前的文本
		 * @return 返回压缩后的文本
		 */
		public static final String zip(String str) {
			if (str == null)
				return null;
			byte[] compressed;
			ByteArrayOutputStream out = null;
			ZipOutputStream zout = null;
			String compressedStr = null;
			try {
				out = new ByteArrayOutputStream();
				zout = new ZipOutputStream(out);
				zout.putNextEntry(new ZipEntry("0"));
				zout.write(str.getBytes());
				zout.closeEntry();
				compressed = out.toByteArray();
				compressedStr = /*new sun.misc.BASE64Encoder()
						.encodeBuffer(compressed)*/StringByteConvertUtil.toHexString(compressed);
			} catch (IOException e) {
				compressed = null;
			} finally {
				if (zout != null) {
					try {
						zout.close();
					} catch (IOException e) {
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
			return compressedStr;
		}

		/**
		 * 使用zip进行解压缩
		 * 
		 * @param compressed
		 *            压缩后的文本
		 * @return 解压后的字符串
		 */
		public static final String unzip(String compressedStr) {
			if (compressedStr == null) {
				return null;
			}

			ByteArrayOutputStream out = null;
			ByteArrayInputStream in = null;
			ZipInputStream zin = null;
			String decompressed = null;
			try {
				byte[] compressed = /*new sun.misc.BASE64Decoder()
						.decodeBuffer(compressedStr)*/StringByteConvertUtil.convertHexString(compressedStr);
				out = new ByteArrayOutputStream();
				in = new ByteArrayInputStream(compressed);
				zin = new ZipInputStream(in);
				zin.getNextEntry();
				byte[] buffer = new byte[1024];
				int offset = -1;
				while ((offset = zin.read(buffer)) != -1) {
					out.write(buffer, 0, offset);
				}
				decompressed = out.toString();
			} catch (IOException e) {
				decompressed = null;
			} finally {
				if (zin != null) {
					try {
						zin.close();
					} catch (IOException e) {
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
			return decompressed;
		}
		
		public static String algorismToHEXString(int algorism, int maxLength) {
	        String result = "";
	        result = Integer.toHexString(algorism);

	        if (result.length() % 2 == 1) {
	            result = "0" + result;
	        }
	        return patchHexString(result.toUpperCase(), maxLength);
	    }
		
		static public String patchHexString(String str, int maxLength) {
	        String temp = "";
	        for (int i = 0; i < maxLength - str.length(); i++) {
	            temp = "0" + temp;
	        }
	        str = (temp + str).substring(0, maxLength);
	        return str;
	    }
		
		/**
		 * 将数字number用“0”左边补齐直到length位数的长度的字符串
		 * @param length
		 * @param number
		 * @return
		 */
		public static String lpad(int length,long number){
			String f = "%0"+length+"d";
			return String.format(f, number);
		}
		
		/**
		 * 字符串左补齐
		 * @param length
		 * @param number
		 * @return
		 */
		//左补齐
		 public static String leftPad(String str,String pad,int len){
		  String newStr=(str==null?"":str);
		  while(newStr.length()<len){
		   newStr=pad+newStr;
		  }
		  if(newStr.length()>len){
		   newStr=newStr.substring(newStr.length()-len);
		  }
		  return newStr;
		 }
		 /**
		  * 字符串右补齐
		  * @param str
		  * @param pad
		  * @param len
		  * @return
		  */
		 public static String rightPad(String str,String pad,int len){
		  String newStr=(str==null?"":str);
		  while(newStr.length()<len){
		   newStr=newStr+pad;
		  }
		  if(newStr.length()>len){
		   newStr=newStr.substring(0, len);
		  }
		  return newStr;
		 }
		/** *//**
		    * @函数功能: BCD码转ASC码
		    * @输入参数: BCD串
		    * @输出结果: ASC码
		    */
		public static String BCD2ASC(byte[] bytes) {
			char[] BToA = "0123456789abcdef".toCharArray() ; 
		    StringBuffer temp = new StringBuffer(bytes.length * 2);
		
		    for (int i = 0; i < bytes.length; i++) {
		     int h = ((bytes[i] & 0xf0) >>> 4);
		     int l = (bytes[i] & 0x0f);   
		     temp.append(BToA[h]).append( BToA[l]);
		    }
		    return temp.toString() ;
		}	
		
		/**
		 * 生成指定位数的随机码
		 * @param number
		 * @return
		 */
		public static String createRandom(int number) {
			long min = (long)Math.pow(10, number - 1) + 1;
			long max = (long)Math.pow(10, number) - 1;
			long result = (long)ThreadLocalRandom.current().nextDouble(min, max);
			return result + "";
		}
		
		
}
