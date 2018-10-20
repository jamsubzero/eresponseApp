B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7.3
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	'Activity.LoadLayout("Typhoon")
	'Activity.LoadLayout("flood")
	'Activity.LoadLayout("tsunami")
	'Activity.LoadLayout("storm")
	'Activity.LoadLayout("")
	'Activity.LoadLayout("earthquake")
	'Activity.LoadLayout("landslide")
	'Activity.LoadLayout("info")
End Sub

Sub Activity_Resume
	'Activity.LoadLayout("flood")
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub SOS_btn_Click
	
	'Activity.LoadLayout("info")
End Sub