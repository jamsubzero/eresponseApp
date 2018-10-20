package com.chmsc.eresponse.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_info{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="health_lbl.Bottom = 93%y"[info/General script]
views.get("health_lbl").vw.setTop((int)((93d / 100 * height) - (views.get("health_lbl").vw.getHeight())));
//BA.debugLineNum = 3;BA.debugLine="health_btn.HorizontalCenter = 70%x"[info/General script]
views.get("health_btn").vw.setLeft((int)((70d / 100 * width) - (views.get("health_btn").vw.getWidth() / 2)));
//BA.debugLineNum = 4;BA.debugLine="health_lbl.Top = health_btn.Bottom - 7dip"[info/General script]
views.get("health_lbl").vw.setTop((int)((views.get("health_btn").vw.getTop() + views.get("health_btn").vw.getHeight())-(7d * scale)));
//BA.debugLineNum = 5;BA.debugLine="health_lbl.Width = health_btn.Width"[info/General script]
views.get("health_lbl").vw.setWidth((int)((views.get("health_btn").vw.getWidth())));
//BA.debugLineNum = 6;BA.debugLine="health_lbl.HorizontalCenter = 70%x"[info/General script]
views.get("health_lbl").vw.setLeft((int)((70d / 100 * width) - (views.get("health_lbl").vw.getWidth() / 2)));
//BA.debugLineNum = 8;BA.debugLine="SOS_lbl.Bottom = health_btn.Top + 5dip"[info/General script]
views.get("sos_lbl").vw.setTop((int)((views.get("health_btn").vw.getTop())+(5d * scale) - (views.get("sos_lbl").vw.getHeight())));
//BA.debugLineNum = 9;BA.debugLine="SOS_btn.HorizontalCenter = 70%x"[info/General script]
views.get("sos_btn").vw.setLeft((int)((70d / 100 * width) - (views.get("sos_btn").vw.getWidth() / 2)));
//BA.debugLineNum = 10;BA.debugLine="SOS_lbl.HorizontalCenter = 70%x"[info/General script]
views.get("sos_lbl").vw.setLeft((int)((70d / 100 * width) - (views.get("sos_lbl").vw.getWidth() / 2)));
//BA.debugLineNum = 11;BA.debugLine="SOS_btn.Bottom = SOS_lbl.Top + 5dip"[info/General script]
views.get("sos_btn").vw.setTop((int)((views.get("sos_lbl").vw.getTop())+(5d * scale) - (views.get("sos_btn").vw.getHeight())));
//BA.debugLineNum = 14;BA.debugLine="LDRR_lbl.Bottom = 93%y"[info/General script]
views.get("ldrr_lbl").vw.setTop((int)((93d / 100 * height) - (views.get("ldrr_lbl").vw.getHeight())));
//BA.debugLineNum = 15;BA.debugLine="LDRR_btn.HorizontalCenter = 30%x"[info/General script]
views.get("ldrr_btn").vw.setLeft((int)((30d / 100 * width) - (views.get("ldrr_btn").vw.getWidth() / 2)));
//BA.debugLineNum = 16;BA.debugLine="LDRR_lbl.Top = LDRR_btn.Bottom - 7dip"[info/General script]
views.get("ldrr_lbl").vw.setTop((int)((views.get("ldrr_btn").vw.getTop() + views.get("ldrr_btn").vw.getHeight())-(7d * scale)));
//BA.debugLineNum = 17;BA.debugLine="LDRR_lbl.Width = LDRR_btn.Width"[info/General script]
views.get("ldrr_lbl").vw.setWidth((int)((views.get("ldrr_btn").vw.getWidth())));
//BA.debugLineNum = 18;BA.debugLine="LDRR_lbl.HorizontalCenter = 30%x"[info/General script]
views.get("ldrr_lbl").vw.setLeft((int)((30d / 100 * width) - (views.get("ldrr_lbl").vw.getWidth() / 2)));
//BA.debugLineNum = 20;BA.debugLine="PLS_lbl.Bottom = LDRR_btn.Top + 5dip"[info/General script]
views.get("pls_lbl").vw.setTop((int)((views.get("ldrr_btn").vw.getTop())+(5d * scale) - (views.get("pls_lbl").vw.getHeight())));
//BA.debugLineNum = 21;BA.debugLine="PLS_btn.HorizontalCenter = 30%x"[info/General script]
views.get("pls_btn").vw.setLeft((int)((30d / 100 * width) - (views.get("pls_btn").vw.getWidth() / 2)));
//BA.debugLineNum = 22;BA.debugLine="PLS_lbl.HorizontalCenter = 30%x"[info/General script]
views.get("pls_lbl").vw.setLeft((int)((30d / 100 * width) - (views.get("pls_lbl").vw.getWidth() / 2)));
//BA.debugLineNum = 23;BA.debugLine="PLS_btn.Bottom = PLS_lbl.Top + 5dip"[info/General script]
views.get("pls_btn").vw.setTop((int)((views.get("pls_lbl").vw.getTop())+(5d * scale) - (views.get("pls_btn").vw.getHeight())));
//BA.debugLineNum = 25;BA.debugLine="regBtn.Top = Label10.Bottom - 5dip"[info/General script]
views.get("regbtn").vw.setTop((int)((views.get("label10").vw.getTop() + views.get("label10").vw.getHeight())-(5d * scale)));

}
}