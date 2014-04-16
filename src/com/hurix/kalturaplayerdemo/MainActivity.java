package com.hurix.kalturaplayerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.kaltura.playersdk.PlayerViewController;
import com.kaltura.playersdk.events.OnToggleFullScreenListener;

public class MainActivity extends ActionBarActivity implements OnToggleFullScreenListener {
	boolean _isFullScreen;
	PlayerViewController _playerViewConroller = null;
	FrameLayout _frameLayoutContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}
	
	public void onButtonClick(View view){
		if(_frameLayoutContainer == null){
			_frameLayoutContainer = (FrameLayout) this.findViewById(R.id.frameLayoutContainer);	
		}
		
		_frameLayoutContainer.setVisibility(View.VISIBLE);
		
		_isFullScreen = true;
		_playerViewConroller = new PlayerViewController(this);
	
		_frameLayoutContainer.addView(_playerViewConroller);
		_playerViewConroller.addComponents("PARTNER_ID", "ENTRY_ID", MainActivity.this);
		_playerViewConroller.setOnFullScreenListener(this);
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(_frameLayoutContainer.getVisibility() == View.VISIBLE){
                	_frameLayoutContainer.setVisibility(View.GONE);
                	_playerViewConroller.stop();
                	_playerViewConroller = null;
                }
                else
                {
                	finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
	public void onToggleFullScreen() {
		// TODO Auto-generated method stub
		_isFullScreen = !_isFullScreen;
		FrameLayout.LayoutParams mNewParams;
		if(_isFullScreen)
		{
			mNewParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mNewParams.gravity = Gravity.TOP | Gravity.LEFT;
		}else
		{
			mNewParams = new FrameLayout.LayoutParams(400, 400);
			mNewParams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
		}
		
		_playerViewConroller.setLayoutParams(mNewParams);
	}

}
