package cn.net.wangsu.dhvideo;

import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.*;

/**
 * @author 王苏
 * @time 2013-1-22
 *       功能说明:大华netsdk的dll的java调用，采用jna制作，必须包含jna.jar才可使用，相应jni版本未制作，再写一遍c
 *       ++调用有点无聊 version:0.1 仅仅完成了播放和断开
 */
public interface DHNetSDK extends Library
{
	/**
	 * 实例，所有的功能调用都要通过它
	 */
	DHNetSDK INSTANCE = (DHNetSDK) Native.loadLibrary("dhnetsdk",
			DHNetSDK.class);

	static boolean initialized = false;

	/**
	 * 断线回调的对象指针接口，用来提供断线回调,Long可能要写成NativeLong
	 */
	public static interface fDisConnect extends Callback
	{
		/**
		 * 设备的断线回调
		 * 
		 * @param lLoginID
		 *            设备id
		 * @param ip
		 *            设备ip
		 * @param port
		 *            设备端口
		 * @param dwUser
		 *            设备用户 0
		 * @return
		 */
		int invoke(Long lLoginID, String ip, Long port, short dwUser);
	}

	/**
	 * 回调函数的对象指针接口的实现，此处不可用，建议在程序中生成
	 * 
	 * @author 王苏 通过断线回调函数，这其实是一个逆向调用，有dll调用它，可以通过它来进行断线重连等操作
	 */
	public static class fDisConnect_real implements fDisConnect
	{
		@Override
		public int invoke(Long lLoginID, String ip, Long port, short dwUser)
		{
			// TODO Auto-generated method stub
			System.out.println("断线了！！");
			return 0;
		}

	}

	/**
	 * 设备初始化
	 * 
	 * @param fdisconnect
	 *            断线回调函数的实现
	 * @param dwUser
	 *            用户名
	 * @return 成功为true，失败为false
	 */
	public boolean CLIENT_Init(fDisConnect fdisconnect, short dwUser);

	/**
	 * 设备登录
	 * 
	 * @param pchDVRIP
	 *            ip地址
	 * @param wDVRPort
	 *            端口号
	 * @param pchUserName
	 *            用户名
	 * @param pchPassword
	 *            密码
	 * @param lpDeviceInfo
	 *            设备信息
	 * @param error
	 *            错误号错误码 代表含义 1 密码不正确 2 用户名不存在 3 登录超时 4 帐号已登录 5 帐号已被锁定 6
	 *            帐号被列为黑名单 7 资源不足，系统忙 8 子连接失败 9 主连接失败 10 超过最大用户连接数
	 * @return 失败返回0，成功返回设备ID，登录成功之后对设备的操作都可以通过此值(设备句柄)对应到相应的设备。
	 *         提示：在初始化后就可以调用本接口注册到指定的设备，成功后将返回设备句柄，给相关的函数调用。（必须先初始化）
	 */
	public int CLIENT_Login(String pchDVRIP, int wDVRPort, String pchUserName,
			String pchPassword, NET_DEVICEINFO.ByReference lpDeviceInfo,
			int error);

	/**
	 * 设备
	 * 
	 * @author 王苏 其中DVRType对应相应的功能，详见大华的skd开发文档
	 */
	public static class NET_DEVICEINFO extends Structure
	{
		public byte[] sSerialNumber = new byte[48];
		public byte byAlarmInPortNum;// 报警输入个数
		public byte byAlarmOutPortNum;// 报警输出格式
		public byte byDiskNum;// 硬盘个数
		public byte byDVRType;// 类型，详见大华开发文档的DHDEV_SYSTEM_ATTR_CFG
		public byte byChanNum;// 通道个数

		/**
		 * Structure 类有两个内部接口 Structure.ByReference 和 Structure.ByValue。
		 * 这两个接口仅仅是标记。 如果一个类实现 Structure.ByReference 接口， 就表示这个类代表结构体指针 。 如果一个类实现
		 * Structure.ByValue 接口，就表示这个类代表结构体本身。
		 * 示例中没有找到具体的实现的例子，应该是类似于更加复杂的接口提的时候才用的到
		 * 
		 * @author Administrator
		 * 
		 */
		public static class ByReference extends NET_DEVICEINFO implements
				Structure.ByReference
		{}

		public static class ByValue extends NET_DEVICEINFO implements
				Structure.ByValue
		{}

		@Override
		/**
		 * 这里用来标记结构体中各个字段的顺序
		 */
		protected List getFieldOrder()
		{
			// TODO Auto-generated method stub
			return Arrays.asList(new String[] { "sSerialNumber",
					"byAlarmInPortNum", "byAlarmOutPortNum", "byDiskNum",
					"byDVRType", "byChanNum" });
			// return null;
		}
	}

	/**
	 * 实时监控功能，hwnd为窗口句柄，请调用long com.sun.jna.Native.getComponentID(Component c)得到
	 * 
	 * @param lLoginID
	 * @param nChannelID
	 * @param hWnd
	 * @return
	 */
	public int CLIENT_RealPlay(int lLoginID, int nChannelID, long hWnd);

	/**
	 * 停止实时监控，其中设备通道号为CLIENT_RealPlay返回的id
	 * 
	 * @param lRealHandle
	 * @return
	 */
	public boolean CLIENT_StopRealPlay(int lRealHandle);

	/**
	 * 停止实时监控，其中设备通道号为CLIENT_RealPlay返回的id
	 * 
	 * @param lRealHandle
	 * @return
	 */
	public boolean CLIENT_StopRealPlayEx(int lRealHandle);

	/**
	 * 清空SDK, 释放占用的资源，在所有的SDK函数之后调用
	 */
	public void CLIENT_Cleanup();

	/**
	 * 注销设备用户。
	 * 
	 * @param lLoginID
	 *            设备用户登录时得到的返回值
	 * @return true:成功;false:失败
	 */
	public boolean CLIENT_Logout(int lLoginID);

	/**
	 * 返回函数执行失败代码
	 * 
	 * @return
	 */
	public int CLIENT_GetLastError();

	/**
	 * 设置与设备的连接等待时间
	 * 
	 * @param nWaitTime
	 *            连接等待时间[单位:毫秒]
	 * @param nTryTimes
	 *            连接次数
	 */
	public void CLIENT_SetConnectTime(int nWaitTime, int nTryTimes);

	/**
	 * 不知道这个有没有用，就没写 设置设备消息回调函数, 用来得到设备当前状态信息
	 * 
	 * @param o
	 * @param dwUser
	 */
	public void CLIENT_SetDVRMessCallBack(Object o, int dwUser);

	/**
	 * 启动监听服务, 目前只实现了报警监听功能
	 * 
	 * @param wPort
	 *            启动监听的端口
	 * @param pIp
	 *            绑定的IP，为NULL时绑定本机所有合法IP
	 * @param pfscb
	 *            服务器的消息回调接口
	 * @param dwTimeOut
	 *            服务器维护连 接的超时时间
	 * @param dwUserData
	 *            用户回调的自定义数据
	 * @return 成功返回服务器句柄，失败返回0
	 */
	public int CLIENT_StartService(int wPort, String pIp, Object pfscb,
			int dwTimeOut, int dwUserData);

	/**
	 * 停止端口监听服务
	 * 
	 * @param lHandle
	 *            要关闭的服务器的句柄,CLIENT_StartService的返回值
	 * @return true:成功;false:失败
	 */
	public boolean CLIENT_StopService(int lHandle);
}
