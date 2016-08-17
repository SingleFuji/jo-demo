package com.jo.demo.utils;

/**
 * 
 * @copyrights 新国都技术股份有限公司
 * @author hang_zhou
 * @Date 2013-12-17
 * @desc
 * @version
 * @tosee
 * 
 */
public class StringByteConvertUtil
{
	/**
	 * 将二进制字符串转换为byte数组
	 * 
	 * @param binary
	 * @return
	 */
	public static byte[] binaryString2ByteArr(String binary)
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
	
	public static void main(String []args)
	{
		String binary = "0110000000100000000001000000000000110000110000000001000000000101";
		byte[] a = binaryString2ByteArr(binary);
		for(byte temp : a)
		{
			System.out.println(temp);
		}
	}

	/**
	 * 16进制数组数转化
	 * 
	 * @param ss
	 * @return
	 */
	public static byte[] convertHexString(String ss)
	{
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++)
		{
			String byteString = ss.substring(2 * i, 2 * i + 2);
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
	public static String toHexString(byte b[])
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
	 * 十六进制数转化
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String toHexString(byte b)
	{
		String plainText = Integer.toHexString(0xff & b);
		if (plainText.length() < 2)
			plainText = "0" + plainText;
		return plainText;
	}

}
