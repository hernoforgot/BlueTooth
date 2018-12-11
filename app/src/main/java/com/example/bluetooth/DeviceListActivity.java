package com.example.bluetooth;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.bluetoothassist.R;
/**
 * 蓝牙设备列表活动，列出当前已匹配设备
 * 及搜周围未匹配设备
 * @author Administrator
 */
public class DeviceListActivity extends Activity
{
	private static final String TAG = "DeviceList";
	private static final boolean DEBUG = true;

	public static String DEVICE_ADDRESS = "device address";

	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevices;
	private ArrayAdapter<String> mNewDevices;
	private Button scanButton;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 设置标题栏类型为进程条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		//设置默认活动返回值
		setResult(Activity.RESULT_CANCELED);
		
		scanButton = (Button) findViewById(R.id.button_scan);
		//设置搜索键监听方法
		scanButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//搜索蓝牙
				BtDiscovery();
			}
		});

		// 设置列表布局文件
		mPairedDevices = new ArrayAdapter<String>(this,
				R.layout.device_name);
		mNewDevices = new ArrayAdapter<String>(this,
				R.layout.device_name);

		ListView pairedList = (ListView) findViewById(R.id.paired_devices);
		ListView newDevicesList = (ListView) findViewById(R.id.new_devices);
		
		pairedList.setAdapter(mPairedDevices);
		newDevicesList.setAdapter(mNewDevices);
		pairedList.setOnItemClickListener(mDeviceClickListener);
		newDevicesList.setOnItemClickListener(mDeviceClickListener);

		// 注册蓝牙搜索广播
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// 获取匹配设备列表
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// 将匹配的设备添加到列表显示
		if (pairedDevices.size() > 0)
		{
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			for (BluetoothDevice device : pairedDevices)
			{
				mPairedDevices.add(device.getName() + "\n"
						+ device.getAddress());
			}
		}
		else
		{
			mPairedDevices.add("未搜到任何设备");
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		// 取消可 能存在的搜索
		if (mBtAdapter != null)
		{
			mBtAdapter.cancelDiscovery();
		}
		// 注销蓝牙广播
		this.unregisterReceiver(mReceiver);
	}

	/**
	 * 蓝牙搜索方法
	 */
	private void BtDiscovery()
	{
		if (DEBUG) Log.d(TAG, "doDiscovery()");

		// 设置标题栏进程条可见
		setProgressBarIndeterminateVisibility(true);
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		// 当正在搜索，则停止搜索
		if (mBtAdapter.isDiscovering())
		{
			mBtAdapter.cancelDiscovery();
			scanButton.setText("搜索");
		}
		// 否则开启蓝牙搜索
		else{
			scanButton.setText("正在搜索(点击停止)");
			//清空新搜索到设备的列表以免重复添加
			mNewDevices.clear();
			// 开始搜索设备
			mBtAdapter.startDiscovery();
		}
	}

	// 列表项点击监听方法
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
		{
			// 关闭蓝牙搜索以便进行蓝牙连接
			mBtAdapter.cancelDiscovery();

			// 列表项名称的最后17个字符为MAC地址
			String str = ((TextView) v).getText().toString();
			String address = str.substring(str.length() - 17);

			// 将MAC地址通过意图返回UI
			Intent intent = new Intent();
			intent.putExtra(DEVICE_ADDRESS, address);

			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	// 蓝牙广播
	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();

			// 发现设备
			if (BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device ;
				// 获取蓝牙设备
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// 已匹配的跳过
				if (device.getBondState() != BluetoothDevice.BOND_BONDED)
				{
					mNewDevices.add(device.getName() + "\n" + device.getAddress());
				}
			}
			//蓝牙搜索完成
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				setProgressBarIndeterminateVisibility(false);
				scanButton.setText("搜索");
				if (mNewDevices.getCount() == 0)
				{
					mNewDevices.add("未搜到任何设备");
				}
			}
		}
	};
}
