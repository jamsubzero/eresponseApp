package com.chmsc.eresponse;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.chmsc.eresponse", "com.chmsc.eresponse.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.chmsc.eresponse", "com.chmsc.eresponse.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.chmsc.eresponse.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static double _xlat = 0;
public static double _xlon = 0;
public anywheresoftware.b4a.objects.LabelWrapper _wealocatiolbl = null;
public anywheresoftware.b4a.objects.LabelWrapper _templbl = null;
public anywheresoftware.b4a.objects.LabelWrapper _weatherlbl = null;
public anywheresoftware.b4a.objects.LabelWrapper _wdesclbl = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _wicon = null;
public com.rootsoft.locationmanager.LocationManager1 _lm = null;
public static String _myurl = "";
public anywheresoftware.b4a.objects.LabelWrapper _lat_lbl = null;
public anywheresoftware.b4a.objects.LabelWrapper _lon_lbl = null;
public static String _phonenum = "";
public static int _helptype = 0;
public static String _policenum = "";
public static String _firenum = "";
public static String _lddrmonum = "";
public static String _healthnum = "";
public anywheresoftware.b4a.objects.ButtonWrapper _regbtn = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _weblocation = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.chmsc.eresponse.starter _starter = null;
public com.chmsc.eresponse.register _register = null;
public com.chmsc.eresponse.imagedownloader _imagedownloader = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="Activity.LoadLayout(\"info\")";
mostCurrent._activity.LoadLayout("info",mostCurrent.activityBA);
 //BA.debugLineNum = 61;BA.debugLine="If Starter.kvs.ContainsKey(\"currentUser\") Then";
if (mostCurrent._starter._kvs._containskey("currentUser")) { 
 //BA.debugLineNum = 62;BA.debugLine="lm.Initialize(\"Location\")";
mostCurrent._lm.Initialize(processBA,"Location");
 //BA.debugLineNum = 63;BA.debugLine="lm.requestMobileLocation";
mostCurrent._lm.requestMobileLocation();
 //BA.debugLineNum = 64;BA.debugLine="lm.requestGPSLocation";
mostCurrent._lm.requestGPSLocation();
 //BA.debugLineNum = 65;BA.debugLine="regBtn.Enabled = False ' Already registered";
mostCurrent._regbtn.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 66;BA.debugLine="regBtn.Text = Starter.kvs.Get(\"currentNameOfUser";
mostCurrent._regbtn.setText(BA.ObjectToCharSequence(mostCurrent._starter._kvs._get("currentNameOfUser")));
 }else {
 //BA.debugLineNum = 70;BA.debugLine="StartActivity(Register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 166;BA.debugLine="If Starter.kvs.ContainsKey(\"currentUser\") Then";
if (mostCurrent._starter._kvs._containskey("currentUser")) { 
 //BA.debugLineNum = 167;BA.debugLine="regBtn.Enabled = False ' Already registered";
mostCurrent._regbtn.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 168;BA.debugLine="regBtn.Text = Starter.kvs.Get(\"currentNameOfUser";
mostCurrent._regbtn.setText(BA.ObjectToCharSequence(mostCurrent._starter._kvs._get("currentNameOfUser")));
 }else {
 //BA.debugLineNum = 170;BA.debugLine="regBtn.Enabled = True ' Not yet registered";
mostCurrent._regbtn.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 171;BA.debugLine="regBtn.Text = \"Register User\"";
mostCurrent._regbtn.setText(BA.ObjectToCharSequence("Register User"));
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _callhelp() throws Exception{
String _pnumber = "";
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 207;BA.debugLine="Sub callHelp";
 //BA.debugLineNum = 208;BA.debugLine="Dim pnumber As String";
_pnumber = "";
 //BA.debugLineNum = 210;BA.debugLine="If helpType=1 Then";
if (_helptype==1) { 
 //BA.debugLineNum = 211;BA.debugLine="pnumber = policeNum";
_pnumber = mostCurrent._policenum;
 }else if(_helptype==2) { 
 //BA.debugLineNum = 213;BA.debugLine="pnumber = fireNum";
_pnumber = mostCurrent._firenum;
 }else if(_helptype==3) { 
 //BA.debugLineNum = 215;BA.debugLine="pnumber = lddrmoNum";
_pnumber = mostCurrent._lddrmonum;
 }else if(_helptype==4) { 
 //BA.debugLineNum = 217;BA.debugLine="pnumber = healthNum";
_pnumber = mostCurrent._healthnum;
 };
 //BA.debugLineNum = 220;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 221;BA.debugLine="i.Initialize(i.ACTION_VIEW, \"tel:\"&pnumber)";
_i.Initialize(_i.ACTION_VIEW,"tel:"+_pnumber);
 //BA.debugLineNum = 222;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 32;BA.debugLine="Private wealocatiolbl As Label";
mostCurrent._wealocatiolbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private templbl As Label";
mostCurrent._templbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private weatherlbl As Label";
mostCurrent._weatherlbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private wdesclbl As Label";
mostCurrent._wdesclbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private wicon As ImageView";
mostCurrent._wicon = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim lm As LocationManager";
mostCurrent._lm = new com.rootsoft.locationmanager.LocationManager1();
 //BA.debugLineNum = 38;BA.debugLine="Dim lm As LocationManager";
mostCurrent._lm = new com.rootsoft.locationmanager.LocationManager1();
 //BA.debugLineNum = 39;BA.debugLine="Dim myurl As String";
mostCurrent._myurl = "";
 //BA.debugLineNum = 40;BA.debugLine="Private lat_lbl As Label";
mostCurrent._lat_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lon_lbl As Label";
mostCurrent._lon_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim phoneNum As String";
mostCurrent._phonenum = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim helpType As Int";
_helptype = 0;
 //BA.debugLineNum = 46;BA.debugLine="Private policeNum As String = \"09054790111\"";
mostCurrent._policenum = "09054790111";
 //BA.debugLineNum = 47;BA.debugLine="Private fireNum As String = \"09054790111\"";
mostCurrent._firenum = "09054790111";
 //BA.debugLineNum = 48;BA.debugLine="Private lddrmoNum As String = \"09054790111\"";
mostCurrent._lddrmonum = "09054790111";
 //BA.debugLineNum = 49;BA.debugLine="Private healthNum As String = \"09054790111\"";
mostCurrent._healthnum = "09054790111";
 //BA.debugLineNum = 52;BA.debugLine="Private regBtn As Button";
mostCurrent._regbtn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private weblocation As Spinner";
mostCurrent._weblocation = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _health_btn_click() throws Exception{
 //BA.debugLineNum = 248;BA.debugLine="Sub health_btn_Click";
 //BA.debugLineNum = 249;BA.debugLine="helpType=4";
_helptype = (int) (4);
 //BA.debugLineNum = 250;BA.debugLine="help";
_help();
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _help() throws Exception{
int _i = 0;
 //BA.debugLineNum = 256;BA.debugLine="Sub help()";
 //BA.debugLineNum = 258;BA.debugLine="If Starter.kvs.ContainsKey(\"currentUser\") Then";
if (mostCurrent._starter._kvs._containskey("currentUser")) { 
 //BA.debugLineNum = 260;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 261;BA.debugLine="i = Msgbox2(\"Please select help method\", \"Emereg";
_i = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Please select help method"),BA.ObjectToCharSequence("Emeregency"),"Cancel","Call","Send SOS Online",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 262;BA.debugLine="If i = DialogResponse.POSITIVE Then ' Cancel";
if (_i==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 }else if(_i==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 265;BA.debugLine="callHelp";
_callhelp();
 }else if(_i==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
 //BA.debugLineNum = 268;BA.debugLine="SOSHelp(helpType)";
_soshelp(BA.NumberToString(_helptype));
 };
 }else {
 //BA.debugLineNum = 273;BA.debugLine="Msgbox(\"You must register first.\",\"Not yet regis";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You must register first."),BA.ObjectToCharSequence("Not yet registered"),mostCurrent.activityBA);
 //BA.debugLineNum = 274;BA.debugLine="StartActivity(Register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 };
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _jsonres = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _res = "";
 //BA.debugLineNum = 92;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 94;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 95;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"WE")) {
case 0: {
 //BA.debugLineNum = 97;BA.debugLine="parserWeatherjson(Job.GetString)";
_parserweatherjson(_job._getstring());
 break; }
}
;
 };
 //BA.debugLineNum = 101;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 102;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"SOSHelp")) {
case 0: {
 //BA.debugLineNum = 105;BA.debugLine="Dim jsonres As String";
_jsonres = "";
 //BA.debugLineNum = 106;BA.debugLine="jsonres = Job.GetString";
_jsonres = _job._getstring();
 //BA.debugLineNum = 107;BA.debugLine="Log(\"Back from Job:\" & Job.JobName )";
anywheresoftware.b4a.keywords.Common.Log("Back from Job:"+_job._jobname);
 //BA.debugLineNum = 108;BA.debugLine="Log(\"Response from server: \" & jsonres)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_jsonres);
 //BA.debugLineNum = 110;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 111;BA.debugLine="parser.Initialize(jsonres)";
_parser.Initialize(_jsonres);
 //BA.debugLineNum = 112;BA.debugLine="Dim map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 113;BA.debugLine="map1  = parser.NextObject";
_map1 = _parser.NextObject();
 //BA.debugLineNum = 114;BA.debugLine="Log(map1)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_map1));
 //BA.debugLineNum = 115;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 116;BA.debugLine="res = map1.Get(\"code\")";
_res = BA.ObjectToString(_map1.Get((Object)("code")));
 //BA.debugLineNum = 117;BA.debugLine="If res = \"0\" Then";
if ((_res).equals("0")) { 
 //BA.debugLineNum = 118;BA.debugLine="Msgbox(\"SOS Successfully Sent\",\"Success\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("SOS Successfully Sent"),BA.ObjectToCharSequence("Success"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 120;BA.debugLine="Msgbox(\"Please make sure you are connected to";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Please make sure you are connected to the internet"+anywheresoftware.b4a.keywords.Common.CRLF+"Code: -1"),BA.ObjectToCharSequence("Failed"),mostCurrent.activityBA);
 };
 break; }
}
;
 }else {
 //BA.debugLineNum = 126;BA.debugLine="Msgbox(\"Please make sure you are connected to the";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Please make sure you are connected to the internet"),BA.ObjectToCharSequence("Failed"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _ldrr_btn_click() throws Exception{
 //BA.debugLineNum = 240;BA.debugLine="Sub LDRR_btn_Click";
 //BA.debugLineNum = 241;BA.debugLine="helpType=3";
_helptype = (int) (3);
 //BA.debugLineNum = 242;BA.debugLine="help";
_help();
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static String  _location_locationchanged(double _longitude,double _latitude,double _altitude,float _accuracy,float _bearing,String _provider,float _speed,long _time) throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Sub Location_LocationChanged (Longitude As Double,";
 //BA.debugLineNum = 185;BA.debugLine="xlat=Latitude";
_xlat = _latitude;
 //BA.debugLineNum = 186;BA.debugLine="xlon=Longitude";
_xlon = _longitude;
 //BA.debugLineNum = 187;BA.debugLine="lm.stopGPSListening";
mostCurrent._lm.stopGPSListening();
 //BA.debugLineNum = 188;BA.debugLine="lm.stopMobileListening";
mostCurrent._lm.stopMobileListening();
 //BA.debugLineNum = 189;BA.debugLine="Start_get";
_start_get();
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _mnustoplistening_click() throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub mnuStopListening_Click";
 //BA.debugLineNum = 180;BA.debugLine="lm.stopGPSListening";
mostCurrent._lm.stopGPSListening();
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
public static String  _parserweatherjson(String _jsontext) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _maestro = null;
anywheresoftware.b4a.objects.collections.Map _mylocation = null;
anywheresoftware.b4a.objects.collections.Map _xmain = null;
anywheresoftware.b4a.objects.collections.List _wea = null;
anywheresoftware.b4a.objects.collections.Map _colweather = null;
String _icon = "";
String _description = "";
String _smain = "";
anywheresoftware.b4a.objects.collections.Map _links = null;
String _mycountry = "";
String _name = "";
double _temp = 0;
 //BA.debugLineNum = 131;BA.debugLine="Sub parserWeatherjson(jsontext As String)";
 //BA.debugLineNum = 132;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 133;BA.debugLine="parser.Initialize(jsontext)";
_parser.Initialize(_jsontext);
 //BA.debugLineNum = 134;BA.debugLine="Dim Maestro As Map = parser.NextObject";
_maestro = new anywheresoftware.b4a.objects.collections.Map();
_maestro = _parser.NextObject();
 //BA.debugLineNum = 135;BA.debugLine="Log(jsontext)";
anywheresoftware.b4a.keywords.Common.Log(_jsontext);
 //BA.debugLineNum = 137;BA.debugLine="Dim mylocation As Map=Maestro.Get(\"sys\")   ' MAP";
_mylocation = new anywheresoftware.b4a.objects.collections.Map();
_mylocation.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_maestro.Get((Object)("sys"))));
 //BA.debugLineNum = 138;BA.debugLine="Dim xmain As Map=Maestro.Get(\"main\")";
_xmain = new anywheresoftware.b4a.objects.collections.Map();
_xmain.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_maestro.Get((Object)("main"))));
 //BA.debugLineNum = 139;BA.debugLine="Dim  wea As List=Maestro.Get(\"weather\")";
_wea = new anywheresoftware.b4a.objects.collections.List();
_wea.setObject((java.util.List)(_maestro.Get((Object)("weather"))));
 //BA.debugLineNum = 141;BA.debugLine="For Each colweather As Map In wea";
_colweather = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group8 = _wea;
final int groupLen8 = group8.getSize()
;int index8 = 0;
;
for (; index8 < groupLen8;index8++){
_colweather.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group8.Get(index8)));
 //BA.debugLineNum = 142;BA.debugLine="Dim icon As String = colweather.Get(\"icon\")";
_icon = BA.ObjectToString(_colweather.Get((Object)("icon")));
 //BA.debugLineNum = 143;BA.debugLine="Dim description As String = colweather.Get(\"desc";
_description = BA.ObjectToString(_colweather.Get((Object)("description")));
 //BA.debugLineNum = 144;BA.debugLine="Dim smain As String = colweather.Get(\"main\")";
_smain = BA.ObjectToString(_colweather.Get((Object)("main")));
 //BA.debugLineNum = 146;BA.debugLine="weatherlbl.text=smain";
mostCurrent._weatherlbl.setText(BA.ObjectToCharSequence(_smain));
 //BA.debugLineNum = 147;BA.debugLine="wdesclbl.Text=description";
mostCurrent._wdesclbl.setText(BA.ObjectToCharSequence(_description));
 //BA.debugLineNum = 148;BA.debugLine="Dim links As Map";
_links = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 149;BA.debugLine="links.Initialize";
_links.Initialize();
 //BA.debugLineNum = 150;BA.debugLine="links.Put(wicon,\"http://openweathermap.org/img/w";
_links.Put((Object)(mostCurrent._wicon.getObject()),(Object)("http://openweathermap.org/img/w/"+_icon+".png"));
 //BA.debugLineNum = 151;BA.debugLine="CallSubDelayed2(ImageDownloader, \"Download\", lin";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"Download",(Object)(_links));
 //BA.debugLineNum = 152;BA.debugLine="CallSub(ImageDownloader, \"ActivityIsPaused\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"ActivityIsPaused");
 }
};
 //BA.debugLineNum = 157;BA.debugLine="Dim mycountry As String = mylocation.Get(\"country";
_mycountry = BA.ObjectToString(_mylocation.Get((Object)("country")));
 //BA.debugLineNum = 158;BA.debugLine="Dim name As String =Maestro.Get(\"name\")";
_name = BA.ObjectToString(_maestro.Get((Object)("name")));
 //BA.debugLineNum = 159;BA.debugLine="Dim temp As Double = xmain.Get(\"temp\")";
_temp = (double)(BA.ObjectToNumber(_xmain.Get((Object)("temp"))));
 //BA.debugLineNum = 160;BA.debugLine="wealocatiolbl.Text=name&\", \"&mycountry";
mostCurrent._wealocatiolbl.setText(BA.ObjectToCharSequence(_name+", "+_mycountry));
 //BA.debugLineNum = 161;BA.debugLine="templbl.Text= temp&\"C\"";
mostCurrent._templbl.setText(BA.ObjectToCharSequence(BA.NumberToString(_temp)+"C"));
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _pls_btn_click() throws Exception{
 //BA.debugLineNum = 225;BA.debugLine="Sub PLS_btn_Click";
 //BA.debugLineNum = 227;BA.debugLine="helpType=1";
_helptype = (int) (1);
 //BA.debugLineNum = 228;BA.debugLine="help";
_help();
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
b4a.example.dateutils._process_globals();
main._process_globals();
starter._process_globals();
register._process_globals();
imagedownloader._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim xlat,xlon As Double";
_xlat = 0;
_xlon = 0;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _regbtn_click() throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Sub regBtn_Click";
 //BA.debugLineNum = 284;BA.debugLine="StartActivity(Register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return "";
}
public static String  _sos_btn_click() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Sub SOS_btn_Click";
 //BA.debugLineNum = 234;BA.debugLine="helpType=2";
_helptype = (int) (2);
 //BA.debugLineNum = 235;BA.debugLine="help";
_help();
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _soshelp(String _needtype) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _insertlocation = null;
String _username = "";
 //BA.debugLineNum = 193;BA.debugLine="Sub SOSHelp(needType As String )";
 //BA.debugLineNum = 196;BA.debugLine="If xlat <> 0 And xlon <> 0 Then";
if (_xlat!=0 && _xlon!=0) { 
 //BA.debugLineNum = 197;BA.debugLine="Dim InsertLocation As HttpJob";
_insertlocation = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 198;BA.debugLine="InsertLocation.Initialize(\"SOSHelp\", Me)";
_insertlocation._initialize(processBA,"SOSHelp",main.getObject());
 //BA.debugLineNum = 199;BA.debugLine="Dim username As String  = Starter.kvs.Get(\"curre";
_username = BA.ObjectToString(mostCurrent._starter._kvs._get("currentUser"));
 //BA.debugLineNum = 200;BA.debugLine="InsertLocation.download2(\"http://eresponse.tk/Em";
_insertlocation._download2("http://eresponse.tk/EmergencyServer/insertEmergencyBebers.php",new String[]{"clientUN",_username,"lati",BA.NumberToString(_xlat),"longi",BA.NumberToString(_xlon),"type",_needtype});
 }else {
 //BA.debugLineNum = 202;BA.debugLine="Msgbox(\"Location Unkown. Please make sure you en";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Location Unkown. Please make sure you enabled your location"),BA.ObjectToCharSequence("SOS Failed!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _start_get() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 75;BA.debugLine="Sub Start_get";
 //BA.debugLineNum = 76;BA.debugLine="lm.requestGPSLocation";
mostCurrent._lm.requestGPSLocation();
 //BA.debugLineNum = 77;BA.debugLine="lm.requestMobileLocation";
mostCurrent._lm.requestMobileLocation();
 //BA.debugLineNum = 78;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 79;BA.debugLine="job.Initialize(\"WE\",Me)";
_job._initialize(processBA,"WE",main.getObject());
 //BA.debugLineNum = 80;BA.debugLine="myurl=\"http://api.openweathermap.org/data/2.5/wea";
mostCurrent._myurl = "http://api.openweathermap.org/data/2.5/weather?lat="+BA.NumberToString(_xlat)+"&lon="+BA.NumberToString(_xlon)+"&units=metric&&APPID=cd75a2766b95cfff092441363cc8dc9e";
 //BA.debugLineNum = 81;BA.debugLine="Log(myurl)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._myurl);
 //BA.debugLineNum = 82;BA.debugLine="job.Download(myurl)";
_job._download(mostCurrent._myurl);
 //BA.debugLineNum = 83;BA.debugLine="lat_lbl.Text=xlat";
mostCurrent._lat_lbl.setText(BA.ObjectToCharSequence(_xlat));
 //BA.debugLineNum = 84;BA.debugLine="lon_lbl.Text=xlon";
mostCurrent._lon_lbl.setText(BA.ObjectToCharSequence(_xlon));
 //BA.debugLineNum = 85;BA.debugLine="Log(xlat)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_xlat));
 //BA.debugLineNum = 86;BA.debugLine="Log(xlon)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_xlon));
 //BA.debugLineNum = 87;BA.debugLine="lm.stopGPSListening";
mostCurrent._lm.stopGPSListening();
 //BA.debugLineNum = 88;BA.debugLine="lm.stopMobileListening";
mostCurrent._lm.stopMobileListening();
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
}
