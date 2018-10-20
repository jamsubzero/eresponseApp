B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim sfname As String
	Dim smname As String
	Dim slname As String
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private uname As EditText
	Private fname As EditText
	Private mname As EditText
	Private lname As EditText
	Private age As EditText
	Private gender As Spinner
	Private cont As EditText
	Private address As Spinner
	Private username As String
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("RegisterUserInfo")

	gender.Add("Gender")
	gender.Add("Male")
	gender.Add("Female")
	
	address.Add("City/Municipality")
	address.Add("Bacolod")

	
	'fetch this value
	'Log(DateTime.Time(Starter.kvs.Get("time")))

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub



Sub saveUserInfo_Click
	Dim suname As String = uname.Text
	username = suname ' pass to global
	sfname = fname.Text
	smname = mname.Text
	slname = lname.text 
	Dim sage As String = age.text
	Dim sgender As String = gender.SelectedItem
	Dim scont As String = cont.text
	Dim saddress As String = address.SelectedItem
	
	Dim  args(16) As String
	
	Dim InsertLocation As HttpJob
	InsertLocation.Initialize("InsertNewP", Me)
	If suname <> "" Then
		args(0) = "user"
		args(1) = suname 
	End If
	If sfname <> "" Then
		args(2) = "fname"
		args(3) = sfname
	End If	
	If smname <> "" Then
		args(4) = "mname"
		args(5) = smname
	End If	
	If slname <> "" Then
		args(6) = "lname"
		args(7) = slname
	End If
	If sage <> "" Then
		args(8) = "age"
		args(9) = sage
	End If
	If sgender <> "Gender" Then
		args(10) = "gender"
		args(11) = sgender
	End If
	If scont <> "" Then
		args(12) = "cont"
		args(13) = scont
	End If
	If saddress <> "City/Municipality" Then
		args(14) = "add"
		args(15) = saddress
	End If
	
	
	Dim eserver As String
	
	If saddress = "Bacolod" Then
		eserver = "http://eresponse.tk/EmergencyServer/registerUser.php"
	End If
	'TODO Add more City
	
	InsertLocation.download2(eserver, args )
End Sub

Sub JobDone(Job As HttpJob)
	ProgressDialogHide
	If Job.Success Then
		
		Select Job.JobName
             
			Case "InsertNewP"
				'Do nothing
				Dim jsonres As String
				jsonres = Job.GetString
				Log("Back from Job:" & Job.JobName )
				Log("Response from server: " & jsonres)
             
				Dim parser As JSONParser
				parser.Initialize(jsonres)
				Dim map1 As Map
				map1  = parser.NextObject
				Log(map1)
				Dim res As String
				res = map1.Get("code")
				If res = "0" Then
					Msgbox("User Info successfully saved","Success")				
					Starter.kvs.Put("currentUser", username) 
					Starter.kvs.Put("currentNameOfUser", sfname & " " & smname & " " & slname )
					Activity.Finish
				Else If res = "1062" Then
					Msgbox("User already Exists" & CRLF & "Code:" & res , "Failed")
				Else if res = "1048" Then
					Msgbox("All fields are required" & CRLF & "Code:" & res , "Failed")
				Else
					Msgbox("All fields are required" & CRLF & "Code: -1" , "Failed")
				 
				End If
				
		End Select
	Else
		Msgbox("Please make sure you are connected to the internet","Failed")
	End If
	Job.Release
End Sub

Sub cancel_Click
Activity.Finish	
End Sub