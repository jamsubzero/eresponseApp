B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=7.3
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	Public rp As RuntimePermissions
	Public GPS1 As GPS
	Private gpsStarted As Boolean
	Public kvs As KeyValueStore

End Sub

Sub Service_Create
	GPS1.Initialize("GPS")
	
	Dim folder As String
	If File.ExternalWritable Then folder = File.DirDefaultExternal Else folder = File.DirInternal
	kvs.Initialize(folder, "datastore2")


End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Public Sub StartGps
	If gpsStarted = False Then
		GPS1.Start(0, 0)
		gpsStarted = True
	End If
End Sub

Public Sub StopGps
	If gpsStarted Then
		GPS1.Stop
		gpsStarted = False
	End If
End Sub

Sub GPS_LocationChanged (Location1 As Location)
	CallSub2(Main, "LocationChanged", Location1)
End Sub


Sub GPS_GpsStatus (Satellites As List)
	CallSub2(Main, "GpsStatus", Satellites)
End Sub

Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub

