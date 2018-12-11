package com.example.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.bluetoothassist.R;

public class defineButtonDialog extends Dialog{

	private Button cancelButton;
	private Button okButton;
	private TextView errorTip;
	private EditText buttonName;
	private EditText Str2Send;
	private Button callButton;
	private StringBuffer codeString;
	/**
	 * 对话框构造方法
	 * @param context UI上下文
	 * @param callButton 触发该对话框的按键
	 * @param Str2Send 按键对应发送的字符串
	 */
	protected defineButtonDialog(Context context,Button callButton,StringBuffer Str2Send) {
		super(context);
		// TODO Auto-generated constructor stub
		this.callButton=callButton;
		codeString=Str2Send;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_dialog);
		this.setTitle("按键配置");
		setCanceledOnTouchOutside(false);
		// 链接各组件对象
		errorTip= (TextView)findViewById(R.id.errorTip);
		buttonName= (EditText)findViewById(R.id.buttonName);
		Str2Send  = (EditText)findViewById(R.id.Str2Send);
		cancelButton = (Button)findViewById(R.id.cancelButton);
		okButton = (Button)findViewById(R.id.okButton);
		cancelButton.setOnClickListener(buttonDialogListener);
		okButton.setOnClickListener(buttonDialogListener);
		buttonName.setText(callButton.getText());
		Str2Send.setText(codeString);
	}
	/**
	 * 按键监听对象
	 */
	private View.OnClickListener buttonDialogListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(view.getId() == R.id.okButton){
				String str=buttonName.getText().toString();
				//输入文本格式规定
				if(str.length()==0){
					errorTip.setVisibility(View.VISIBLE);
					errorTip.setText("按键名称不能为空");
					return;
				}
				else if(str.length()>2){
					errorTip.setVisibility(View.VISIBLE);
					errorTip.setText("按键名称需少于2个字");
					return;
				}
				str=Str2Send.getText().toString();
				if(str.length()==0){
					errorTip.setVisibility(View.VISIBLE); 
					errorTip.setText("发送文本不能为空");
					return;
				}
				else if(MainActivity.isHEXsend){
					for(char c:str.toCharArray()){
						if(!((c>='0'&&c<='9')||(c>='a'&&c<='f')||(c>='A'&&c<='F')||c==' ')){
							errorTip.setVisibility(View.VISIBLE);
							errorTip.setText("发送文本含非法字符(当前为HEX发送)");
							return;
						}
					}
				}
				callButton.setText(buttonName.getText());
				codeString.setLength(0);
				codeString.append(str);
				cancel();//自动调用dismiss();
			}
			else dismiss();
		}
	};
}
