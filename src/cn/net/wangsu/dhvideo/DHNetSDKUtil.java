package cn.net.wangsu.dhvideo;

import java.awt.Component;
import java.io.UnsupportedEncodingException;

import com.sun.jna.Native;

public class DHNetSDKUtil
{

	/**
	 * 字符数组转换为字符串
	 * 
	 * @param byteArray
	 *            字符数组
	 * @return 字符串
	 */
	public static String DHByteArrayToString(byte[] byteArray)
	{
		// string result = Encoding.GetEncoding("gb2312").GetString(byteArray);
		// string result = Encoding.GetEncoding(936).GetString(byteArray);
		String result = new String(byteArray);
		return result;
	}

	/**
	 * 字符数组转换为字符串[仅适用于字符数据长度为16的字符数组对象]
	 * 
	 * @param byteArray
	 *            字符数组
	 * @param formatStyle
	 *            字符串格式[不区分大小写]
	 *            IP1:IP地址的第一部分;IP2:IP地址第二部分;IP3:IP地址的第三部分;IP4:IP地址的第四部分
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String DHByteArrayToString(byte[] byteArray,
			String formatStyle, String sEncodeing)
			throws UnsupportedEncodingException
	{
		// string result = Encoding.GetEncoding("gb2312").GetString(byteArray);
		// string result = Encoding.GetEncoding(936).GetString(byteArray);
		if (sEncodeing.equals("")) sEncodeing = "gb2312";
		String result = new String(byteArray, sEncodeing);
		if (result.length() == 16)
		{
			String sPart1 = result.substring(0, 4);
			String sPart2 = result.substring(4, 4);
			String sPart3 = result.substring(8, 4);
			String sPart4 = result.substring(12, 4);
			String strTemp = formatStyle.toUpperCase();
			// IP地址格式处理
			if (strTemp.indexOf("IP1") != -1)
			{
				strTemp = strTemp.replace("IP1", Integer.parseInt(sPart1) + "");
			}
			if (strTemp.indexOf("IP2") != -1)
			{
				strTemp = strTemp.replace("IP2", Integer.parseInt(sPart2) + "");
			}
			if (strTemp.indexOf("IP3") != -1)
			{
				strTemp = strTemp.replace("IP3", Integer.parseInt(sPart3) + "");
			}
			if (strTemp.indexOf("IP4") != -1)
			{
				strTemp = strTemp.replace("IP4", Integer.parseInt(sPart4) + "");
			}
			result = strTemp;
		}
		return result;
	}

	/**
	 * 字符串转换为字符数组
	 * 
	 * @param strValue
	 * @param byteArry
	 * @return
	 */
	public static boolean DHStringToByteArry(String strValue, byte[] byteArry)
	{
		try
		{
			// byte[] byteTemp =
			// Encoding.GetEncoding("gb2312").GetBytes(strValue);
			byte[] byteTemp = strValue.getBytes();
			int maxLen = (byteTemp.length > byteArry.length ? byteArry.length
					: byteTemp.length);
			for (int i = 0; i < byteArry.length; i++)
			{
				if (i < maxLen)
				{
					byteArry[i] = byteTemp[i];
				}
				else
				{
					byteArry[i] = 0;
				}
			}
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 * 错误代码转换为标准备的错误信息描述
	 * 
	 * @param errorCode
	 *            错误代码
	 * @return 标准备错误信息描述
	 */
	public static String VIDEOGetLastErrorName(int errorCode)
	{
		switch (errorCode)
		{
			case 0x80000000 | 1:
				return "Windows系统出错";
			case 0x80000000 | 2:
				return "网络错误，可能是因为网络超时";
			case 0x80000000 | 3:
				return "设备协议不匹配";
			case 0x80000000 | 4:
				return "句柄无效";
			case 0x80000000 | 5:
				return "打开通道失败";
			case 0x80000000 | 6:
				return "关闭通道失败";
			case 0x80000000 | 7:
				return "用户参数不合法";
			case 0x80000000 | 8:
				return "SDK初始化出错";
			case 0x80000000 | 9:
				return "SDK清理出错";
			case 0x80000000 | 10:
				return "申请render资源出错";
			case 0x80000000 | 11:
				return "打开解码库出错";
			case 0x800000 | 12:
				return "关闭解码库出错";
			case 0x80000000 | 13:
				return "多画面预览中检测到通道数为0";
			case 0x80000000 | 14:
				return "录音库初始化失败";
			case 0x80000000 | 15:
				return "录音库未经初始化";
			case 0x80000000 | 16:
				return "发送音频数据出错";
			case 0x80000000 | 17:
				return "实时数据已经处于保存状态";
			case 0x80000000 | 18:
				return "未保存实时数据";
			case 0x80000000 | 19:
				return "打开文件出错";
			case 0x80000000 | 20:
				return "启动云台控制定时器失败";
			case 0x80000000 | 21:
				return "对返回数据的校验出错";
			case 0x80000000 | 22:
				return "没有足够的缓存";
			case 0x80000000 | 23:
				return "当前SDK未支持该功能";
			case 0x80000000 | 24:
				return "查询不到录像";
			case 0x80000000 | 25:
				return "无操作权限";
			case 0x80000000 | 26:
				return "暂时无法执行";
			case 0x80000000 | 27:
				return "未发现对讲通道";
			case 0x80000000 | 28:
				return "未发现音频";
			case 0x80000000 | 29:
				return "CLientSDK未经初始化";
			case 0x80000000 | 30:
				return "下载已结束";
			case 0x80000000 | 31:
				return "查询结果为空";
			case 0x80000000 | 32:
				return "获取配置失败位置：系统属性";
			case 0x80000000 | 33:
				return "获取配置失败位置：序列号";
			case 0x80000000 | 34:
				return "获取配置失败位置：常规属性";
			case 0x80000000 | 35:
				return "获取配置失败位置：DSP能力描述";
			case 0x80000000 | 36:
				return "获取配置失败位置：网络属性";
			case 0x80000000 | 37:
				return "获取配置失败位置：通道名称";
			case 0x80000000 | 38:
				return "获取配置失败位置：视频属性";
			case 0x80000000 | 39:
				return "获取配置失败位置：录像定时配置";
			case 0x80000000 | 40:
				return "获取配置失败位置：解码器协议名称";
			case 0x80000000 | 41:
				return "获取配置失败位置：232串口功能名称";
			case 0x80000000 | 42:
				return "获取配置失败位置：解码器属性";
			case 0x80000000 | 43:
				return "获取配置失败位置：232串口属性";
			case 0x80000000 | 44:
				return "取配置失败位置：外部报警输入属性";
			case 0x80000000 | 45:
				return "获取配置失败位置：图像检测报警属性";
			case 0x80000000 | 46:
				return "获取配置失败位置：设备时间";
			case 0x80000000 | 47:
				return "获取配置失败位置：预览参数";
			case 0x80000000 | 48:
				return "获取配置失败位置：自动维护配置";
			case 0x80000000 | 49:
				return "获取配置失败位置：视频矩阵配置";
			case 0x80000000 | 55:
				return "设置配置失败位置：常规属性";
			case 0x80000000 | 56:
				return "设置配置失败位置：网络属性";
			case 0x80000000 | 57:
				return "设置配置失败位置：通道名称";
			case 0x80000000 | 58:
				return "设置配置失败位置：视频属性";
			case 0x80000000 | 59:
				return "设置配置失败位置：录像定时配置";
			case 0x80000000 | 60:
				return "设置配置失败位置：解码器属性";
			case 0x80000000 | 61:
				return "设置配置失败位置：232串口属性";
			case 0x80000000 | 62:
				return "设置配置失败位置：外部报警输入属性";
			case 0x80000000 | 63:
				return "设置配置失败位置：图像检测报警属性";
			case 0x80000000 | 64:
				return "设置配置失败位置：设备时间";
			case 0x80000000 | 65:
				return "设置配置失败位置：预览参数";
			case 0x80000000 | 66:
				return "设置配置失败位置：自动维护配置";
			case 0x80000000 | 67:
				return "设置配置失败位置：视频矩阵配置";
			case 0x80000000 | 70:
				return "音频编码接口没有成功初始化";
			case 0x80000000 | 71:
				return "数据过长";
			case 0x80000000 | 72:
				return "设备不支持该操作";
			case 0x80000000 | 73:
				return "设备资源不足";
			case 0x80000000 | 74:
				return "服务器已经启动";
			case 0x80000000 | 75:
				return "服务器尚未成功启动";
			case 0x80000000 | 80:
				return "输入序列号有误";
			case 0x80000000 | 100:
				return "密码不正确";
			case 0x80000000 | 101:
				return "帐户不存在";
			case 0x80000000 | 102:
				return "等待登录返回超时";
			case 0x80000000 | 103:
				return "帐号已登录";
			case 0x80000000 | 104:
				return "帐号已被锁定";
			case 0x80000000 | 105:
				return "帐号已被列为黑名单";
			case 0x80000000 | 106:
				return "资源不足，系统忙";
			case 0x80000000 | 107:
				return "连接主机失败";
			case 0x80000000 | 108:
				return "网络连接失败";
			case 0x80000000 | 120:
				return "Render库打开音频出错";
			case 0x80000000 | 121:
				return "Render库关闭音频出错";
			case 0x80000000 | 122:
				return "Render库控制音量出错";
			case 0x80000000 | 123:
				return "Render库设置画面参数出错";
			case 0x80000000 | 124:
				return "Render库暂停播放出错";
			case 0x80000000 | 125:
				return "Render库抓图出错";
			case 0x80000000 | 126:
				return "Render库步进出错";
			case 0x80000000 | 127:
				return "Render库设置帧率出错";
			case 0x80000000 | 999:
				return "暂时无法设置";
			case 0x80000000 | 1000:
				return "配置数据不合法";
			default:
				return "未知错误";
		}
	}

	/**
	 * RealPlay的包装，接受控件的，以控件句柄的形式调用。
	 * 注意：轻量级控件是java绘制的，无法得到句柄，所以swing的控件会报错，提示必须是重量级控件
	 * 
	 * @param lLoginID
	 * @param nChannelID
	 * @param c
	 *            继承自awt的控件
	 * @return 同DHNetSDK.CLIENT_RealPlay(...)
	 */
	public static int CLIENT_RealPlay(int lLoginID, int nChannelID, Component c)
	{
		return DHNetSDK.INSTANCE.CLIENT_RealPlay(lLoginID, nChannelID,
				Native.getComponentID(c));
	}

}
