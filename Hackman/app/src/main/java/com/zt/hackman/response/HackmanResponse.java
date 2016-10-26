package com.zt.hackman.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/10.
 */
public class HackmanResponse implements Serializable {
    public String hackName;
    public String idenNo;
    public File idenFrontImage;
    public File idenReserveImage;
    public File driveImage;
    public String companyNo;
    public File driveImage2;
    public File doBusinessImage;

    public File vehicleImg;  //车辆照片
    public File vehicleLicenseImg; //行驶证照片
    public File vehicleLicenseImg2; //行驶证副本
    public File operationLicenseImg;



    public static class FileParams{
        public File file;
        public String name;
    }
}
