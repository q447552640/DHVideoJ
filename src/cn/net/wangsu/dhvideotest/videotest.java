package cn.net.wangsu.dhvideotest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.net.wangsu.dhvideo.DHNetSDK;
import cn.net.wangsu.dhvideo.DHNetSDKUtil;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class videotest extends JFrame
{
	private JPanel centerpanel;
	private Panel[] videopanel;
	private JPanel leftpanel;
	private JButton btnNewButton_1;

	private String ipaddress;
	private int portnum;
	private String username;
	private String userpassword;
	private int pLoginID = 0;
	private int[] pRealPlayHandle = new int[16];
	private DHNetSDK.NET_DEVICEINFO.ByReference infovalue = new DHNetSDK.NET_DEVICEINFO.ByReference();

	public videotest()
	{
		this.setSize(new Dimension(1000, 800));
		// 添加主面板
		centerpanel = new JPanel();
		centerpanel.setPreferredSize(new Dimension(800, 800));
		centerpanel.setLayout(new GridLayout(4, 4, 0, 0));
		// 16个界面
		videopanel = new Panel[16];
		for (int i = 0; i < videopanel.length; i++)
		{
			videopanel[i] = new Panel();
			videopanel[i].setPreferredSize(new Dimension(200, 200));
			centerpanel.add(videopanel[i]);
		}

		getContentPane().add(centerpanel, BorderLayout.CENTER);

		// 左侧面板
		leftpanel = new JPanel();
		leftpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		leftpanel.setPreferredSize(new Dimension(100, 600));
		JButton btnNewButton = new JButton("增加设备");
		btnNewButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable()
				{

					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						AddDevice add = new AddDevice();
						add.setVisible(true);
					}
				});
			}
		});
		leftpanel.add(btnNewButton);

		btnNewButton_1 = new JButton("实时显示");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable()
				{

					@Override
					public void run()
					{
						if (btnNewButton_1.getText().equals("实时显示"))
						{
							for (int i = 0; i < infovalue.byChanNum; i++)
							{
								pRealPlayHandle[i] = DHNetSDKUtil
										.CLIENT_RealPlay(pLoginID, i,
												videopanel[i]);
							}
							btnNewButton_1.setText("停止实时显示");
						}
						else
						{
							for (int i = 0; i < infovalue.byChanNum; i++)
							{
								DHNetSDK.INSTANCE
										.CLIENT_StopRealPlay(pRealPlayHandle[i]);
							}
							btnNewButton_1.setText("实时显示");
						}
					}
				});
			}
		});
		leftpanel.add(btnNewButton_1);
		getContentPane().add(leftpanel, BorderLayout.WEST);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				DHNetSDK.INSTANCE.CLIENT_Logout(pLoginID);
				System.exit(0);
			}

		});
	}

	private class AddDevice extends JDialog
	{

		private JTextField jt_ip = new JTextField("192.168.0.25"),
				jt_port = new JTextField("8600"),
				jt_username = new JTextField("admin"),
				jt_userpassword = new JTextField("888888");

		public AddDevice()
		{
			this.setSize(new Dimension(400, 300));
			this.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
			this.add(new JLabel("IP:"));
			this.add(jt_ip);
			this.add(new JLabel("端口:"));
			this.add(jt_port);
			this.add(new JLabel("用户名:"));
			this.add(jt_username);
			this.add(new JLabel("密码:"));
			this.add(jt_userpassword);
			JButton button = new JButton("确定");
			button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					// TODO Auto-generated method stub
					EventQueue.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							ipaddress = jt_ip.getText();
							portnum = Integer.parseInt(jt_port.getText());
							username = jt_username.getText();
							userpassword = jt_userpassword.getText();
							// 注册
							short user = 0;
							DHNetSDK.fDisConnect_real fd_R = new DHNetSDK.fDisConnect_real();
							DHNetSDK.INSTANCE.CLIENT_Init(fd_R, user);

							// 登录
							int error = 0;
							infovalue.write();
							pLoginID = DHNetSDK.INSTANCE.CLIENT_Login(
									ipaddress, portnum, username, userpassword,
									infovalue, error);
							if (pLoginID != 0)
							{
								JOptionPane
										.showMessageDialog(null, "登录成功!",
												"系统信息",
												JOptionPane.INFORMATION_MESSAGE);
								btnNewButton_1.setEnabled(true);
							}
							else
							{
								JOptionPane.showMessageDialog(
										null,
										"登录失败!"
												+ DHNetSDKUtil
														.VIDEOGetLastErrorName(error),
										"系统信息", JOptionPane.INFORMATION_MESSAGE);
							}
							dispose();
						}
					});
				}
			});
			this.add(button);
		}
	}

	public static void main(String[] args)
	{
		videotest t = new videotest();
		t.setVisible(true);
	}
}
