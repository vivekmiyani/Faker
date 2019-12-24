package com.faker;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class Faker implements IXposedHookZygoteInit {
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        final Class<?> PackageParser = XposedHelpers.findClass("android.content.pm.PackageParser", null);
        XposedBridge.hookAllMethods(PackageParser, "generatePackageInfo", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try{
                    PackageInfo pi = (PackageInfo) param.getResult();
                    if (pi != null) {
                        String packageName = pi.packageName;
                        Bundle b = FileIO.read();
                        String signature = b.getString(packageName);
                        if(signature == null){
                            return;
                        }
                        pi.signatures = new Signature[]{new Signature(signature)};
                        param.setResult(pi);
                    }
                }catch(Exception e){e.printStackTrace();}
            }
        });
    }
}
