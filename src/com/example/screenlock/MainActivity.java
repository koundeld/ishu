package com.example.screenlock;


import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;
	 private static final String description = "ADMIN";
	 private static final int ADMIN_INTENT = 15;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button lock=(Button) findViewById(R.id.button1);
		Button exit=(Button) findViewById(R.id.button2);
		//Button enable=(Button) findViewById(R.id);
		 
		mDevicePolicyManager = (DevicePolicyManager)getSystemService(  
                Context.DEVICE_POLICY_SERVICE);
		 mComponentName = new ComponentName(this, MyAdminReceiver.class);
		
		
		lock.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
	            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,description);
	            startActivityForResult(intent, ADMIN_INTENT);
	            
				boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);  
	            if (isAdmin) {  
	                mDevicePolicyManager.lockNow();  
	                finish();
	            }else{
	                Toast.makeText(getApplicationContext(), "configuring....", Toast.LENGTH_SHORT).show();
	            }
				
			}
		});
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDevicePolicyManager.removeActiveAdmin(mComponentName);  
	            Toast.makeText(getApplicationContext(), "Removing configurations", Toast.LENGTH_SHORT).show();
			System.exit(0);	
			}
		});
		
		
	  
	}
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == ADMIN_INTENT) {
	            if (resultCode == RESULT_OK) {
	                Toast.makeText(getApplicationContext(), "configured....", Toast.LENGTH_SHORT).show();
	            }else{
	                Toast.makeText(getApplicationContext(), "Failed to register as Admin", Toast.LENGTH_SHORT).show();
	            }
	        }
	    }
}
