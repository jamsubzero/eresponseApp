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

public class register extends Activity implements B4AActivity{
	public static register mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.chmsc.eresponse", "com.chmsc.eresponse.register");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (register).");
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
		activityBA = new BA(this, layout, processBA, "com.chmsc.eresponse", "com.chmsc.eresponse.register");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.chmsc.eresponse.register", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (register) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (register) Resume **");
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
		return register.class;
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
        BA.LogInfo("** Activity (register) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (register) Resume **");
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
public static String _sfname = "";
public static String _smname = "";
public static String _slname = "";
public anywheresoftware.b4a.objects.EditTextWrapper _uname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _fname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _mname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _lname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _age = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _gender = null;
public anywheresoftware.b4a.objects.EditTextWrapper _cont = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _address = null;
public static String _username = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.chmsc.eresponse.main _main = null;
public com.chmsc.eresponse.starter _starter = null;
public com.chmsc.eresponse.imagedownloader _imagedownloader = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"RegisterUserInfo\")";
mostCurrent._activity.LoadLayout("RegisterUserInfo",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="gender.Add(\"Gender\")";
mostCurrent._gender.Add("Gender");
 //BA.debugLineNum = 33;BA.debugLine="gender.Add(\"Male\")";
mostCurrent._gender.Add("Male");
 //BA.debugLineNum = 34;BA.debugLine="gender.Add(\"Female\")";
mostCurrent._gender.Add("Female");
 //BA.debugLineNum = 36;BA.debugLine="address.Add(\"City/Municipality\")";
mostCurrent._address.Add("City/Municipality");
 //BA.debugLineNum = 37;BA.debugLine="address.Add(\"Bacolod\")";
mostCurrent._address.Add("Bacolod");
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _cancel_click() throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub cancel_Click";
 //BA.debugLineNum = 156;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private uname As EditText";
mostCurrent._uname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private fname As EditText";
mostCurrent._fname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mname As EditText";
mostCurrent._mname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lname As EditText";
mostCurrent._lname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private age As EditText";
mostCurrent._age = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private gender As Spinner";
mostCurrent._gender = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private cont As EditText";
mostCurrent._cont = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private address As Spinner";
mostCurrent._address = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private username As String";
mostCurrent._username = "";
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _jsonres = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _res = "";
 //BA.debugLineNum = 114;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 115;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 116;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 118;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"InsertNewP")) {
case 0: {
 //BA.debugLineNum = 122;BA.debugLine="Dim jsonres As String";
_jsonres = "";
 //BA.debugLineNum = 123;BA.debugLine="jsonres = Job.GetString";
_jsonres = _job._getstring();
 //BA.debugLineNum = 124;BA.debugLine="Log(\"Back from Job:\" & Job.JobName )";
anywheresoftware.b4a.keywords.Common.Log("Back from Job:"+_job._jobname);
 //BA.debugLineNum = 125;BA.debugLine="Log(\"Response from server: \" & jsonres)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_jsonres);
 //BA.debugLineNum = 127;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 128;BA.debugLine="parser.Initialize(jsonres)";
_parser.Initialize(_jsonres);
 //BA.debugLineNum = 129;BA.debugLine="Dim map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 130;BA.debugLine="map1  = parser.NextObject";
_map1 = _parser.NextObject();
 //BA.debugLineNum = 131;BA.debugLine="Log(map1)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_map1));
 //BA.debugLineNum = 132;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 133;BA.debugLine="res = map1.Get(\"code\")";
_res = BA.ObjectToString(_map1.Get((Object)("code")));
 //BA.debugLineNum = 134;BA.debugLine="If res = \"0\" Then";
if ((_res).equals("0")) { 
 //BA.debugLineNum = 135;BA.debugLine="Msgbox(\"User Info successfully saved\",\"Succes";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("User Info successfully saved"),BA.ObjectToCharSequence("Success"),mostCurrent.activityBA);
 //BA.debugLineNum = 136;BA.debugLine="Starter.kvs.Put(\"currentUser\", username)";
mostCurrent._starter._kvs._put("currentUser",(Object)(mostCurrent._username));
 //BA.debugLineNum = 137;BA.debugLine="Starter.kvs.Put(\"currentNameOfUser\", sfname &";
mostCurrent._starter._kvs._put("currentNameOfUser",(Object)(_sfname+" "+_smname+" "+_slname));
 //BA.debugLineNum = 138;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_res).equals("1062")) { 
 //BA.debugLineNum = 140;BA.debugLine="Msgbox(\"User already Exists\" & CRLF & \"Code:\"";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("User already Exists"+anywheresoftware.b4a.keywords.Common.CRLF+"Code:"+_res),BA.ObjectToCharSequence("Failed"),mostCurrent.activityBA);
 }else if((_res).equals("1048")) { 
 //BA.debugLineNum = 142;BA.debugLine="Msgbox(\"All fields are required\" & CRLF & \"Co";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("All fields are required"+anywheresoftware.b4a.keywords.Common.CRLF+"Code:"+_res),BA.ObjectToCharSequence("Failed"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 144;BA.debugLine="Msgbox(\"All fields are required\" & CRLF & \"Co";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("All fields are required"+anywheresoftware.b4a.keywords.Common.CRLF+"Code: -1"),BA.ObjectToCharSequence("Failed"),mostCurrent.activityBA);
 };
 break; }
}
;
 }else {
 //BA.debugLineNum = 150;BA.debugLine="Msgbox(\"Please make sure you are connected to th";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Please make sure you are connected to the internet"),BA.ObjectToCharSequence("Failed"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 152;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim sfname As String";
_sfname = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim smname As String";
_smname = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim slname As String";
_slname = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _saveuserinfo_click() throws Exception{
String _suname = "";
String _sage = "";
String _sgender = "";
String _scont = "";
String _saddress = "";
String[] _args = null;
anywheresoftware.b4a.samples.httputils2.httpjob _insertlocation = null;
String _eserver = "";
 //BA.debugLineNum = 55;BA.debugLine="Sub saveUserInfo_Click";
 //BA.debugLineNum = 56;BA.debugLine="Dim suname As String = uname.Text";
_suname = mostCurrent._uname.getText();
 //BA.debugLineNum = 57;BA.debugLine="username = suname ' pass to global";
mostCurrent._username = _suname;
 //BA.debugLineNum = 58;BA.debugLine="sfname = fname.Text";
_sfname = mostCurrent._fname.getText();
 //BA.debugLineNum = 59;BA.debugLine="smname = mname.Text";
_smname = mostCurrent._mname.getText();
 //BA.debugLineNum = 60;BA.debugLine="slname = lname.text";
_slname = mostCurrent._lname.getText();
 //BA.debugLineNum = 61;BA.debugLine="Dim sage As String = age.text";
_sage = mostCurrent._age.getText();
 //BA.debugLineNum = 62;BA.debugLine="Dim sgender As String = gender.SelectedItem";
_sgender = mostCurrent._gender.getSelectedItem();
 //BA.debugLineNum = 63;BA.debugLine="Dim scont As String = cont.text";
_scont = mostCurrent._cont.getText();
 //BA.debugLineNum = 64;BA.debugLine="Dim saddress As String = address.SelectedItem";
_saddress = mostCurrent._address.getSelectedItem();
 //BA.debugLineNum = 66;BA.debugLine="Dim  args(16) As String";
_args = new String[(int) (16)];
java.util.Arrays.fill(_args,"");
 //BA.debugLineNum = 68;BA.debugLine="Dim InsertLocation As HttpJob";
_insertlocation = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 69;BA.debugLine="InsertLocation.Initialize(\"InsertNewP\", Me)";
_insertlocation._initialize(processBA,"InsertNewP",register.getObject());
 //BA.debugLineNum = 70;BA.debugLine="If suname <> \"\" Then";
if ((_suname).equals("") == false) { 
 //BA.debugLineNum = 71;BA.debugLine="args(0) = \"user\"";
_args[(int) (0)] = "user";
 //BA.debugLineNum = 72;BA.debugLine="args(1) = suname";
_args[(int) (1)] = _suname;
 };
 //BA.debugLineNum = 74;BA.debugLine="If sfname <> \"\" Then";
if ((_sfname).equals("") == false) { 
 //BA.debugLineNum = 75;BA.debugLine="args(2) = \"fname\"";
_args[(int) (2)] = "fname";
 //BA.debugLineNum = 76;BA.debugLine="args(3) = sfname";
_args[(int) (3)] = _sfname;
 };
 //BA.debugLineNum = 78;BA.debugLine="If smname <> \"\" Then";
if ((_smname).equals("") == false) { 
 //BA.debugLineNum = 79;BA.debugLine="args(4) = \"mname\"";
_args[(int) (4)] = "mname";
 //BA.debugLineNum = 80;BA.debugLine="args(5) = smname";
_args[(int) (5)] = _smname;
 };
 //BA.debugLineNum = 82;BA.debugLine="If slname <> \"\" Then";
if ((_slname).equals("") == false) { 
 //BA.debugLineNum = 83;BA.debugLine="args(6) = \"lname\"";
_args[(int) (6)] = "lname";
 //BA.debugLineNum = 84;BA.debugLine="args(7) = slname";
_args[(int) (7)] = _slname;
 };
 //BA.debugLineNum = 86;BA.debugLine="If sage <> \"\" Then";
if ((_sage).equals("") == false) { 
 //BA.debugLineNum = 87;BA.debugLine="args(8) = \"age\"";
_args[(int) (8)] = "age";
 //BA.debugLineNum = 88;BA.debugLine="args(9) = sage";
_args[(int) (9)] = _sage;
 };
 //BA.debugLineNum = 90;BA.debugLine="If sgender <> \"Gender\" Then";
if ((_sgender).equals("Gender") == false) { 
 //BA.debugLineNum = 91;BA.debugLine="args(10) = \"gender\"";
_args[(int) (10)] = "gender";
 //BA.debugLineNum = 92;BA.debugLine="args(11) = sgender";
_args[(int) (11)] = _sgender;
 };
 //BA.debugLineNum = 94;BA.debugLine="If scont <> \"\" Then";
if ((_scont).equals("") == false) { 
 //BA.debugLineNum = 95;BA.debugLine="args(12) = \"cont\"";
_args[(int) (12)] = "cont";
 //BA.debugLineNum = 96;BA.debugLine="args(13) = scont";
_args[(int) (13)] = _scont;
 };
 //BA.debugLineNum = 98;BA.debugLine="If saddress <> \"City/Municipality\" Then";
if ((_saddress).equals("City/Municipality") == false) { 
 //BA.debugLineNum = 99;BA.debugLine="args(14) = \"add\"";
_args[(int) (14)] = "add";
 //BA.debugLineNum = 100;BA.debugLine="args(15) = saddress";
_args[(int) (15)] = _saddress;
 };
 //BA.debugLineNum = 104;BA.debugLine="Dim eserver As String";
_eserver = "";
 //BA.debugLineNum = 106;BA.debugLine="If saddress = \"Bacolod\" Then";
if ((_saddress).equals("Bacolod")) { 
 //BA.debugLineNum = 107;BA.debugLine="eserver = \"http://eresponse.tk/EmergencyServer/r";
_eserver = "http://eresponse.tk/EmergencyServer/registerUser.php";
 };
 //BA.debugLineNum = 111;BA.debugLine="InsertLocation.download2(eserver, args )";
_insertlocation._download2(_eserver,_args);
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
}
