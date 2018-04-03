package com.ciwong.drawimprove;

/**
 * 下载状态
 * Created by res-mingyang on 2015/7/28.
 */
public class DownloadStatus
{

    // 无状态
    public final static int STATUS_NO_STATU = -1;

    // 未下载
    public final static int STATUS_NORMAL = 0;

    /**
     * 连接服务器
     */
    public final static int STATUS_LOADING = 1;

    /**
     * 下载中
     */
    public final static int STATUS_DOWNLOADING = 2;

    /**
     * 下载完成
     */
    public final static int STATUS_COMPLETED = 3;

    /**
     * 暂停下载
     */
    public final static int STATUS_PAUSE = 4;

    /**
     * 下载失败
     */
    public final static int STATUS_FAILED = 5;

    // 由未下载到连接服务器的过程
    public final static int STATUS_NORMAL_TO_LOADING = 6;

    // 由未下载到下载中的过程
    public final static int STATUS_NORMAL_TO_DOWNLOADING = 7;

    // 由连接服务器到未下载的过程（即取消下载）
    public final static int STATUS_LOADING_TO_NORMAL = 8;

    // 由下载中到未下载的过程（即取消下载）
    public final static int STATUS_DOWNLOADING_TO_NORMAL = 9;

    // 由下载中到下载成功的过程
    public final static int STATUS_DOWNLOADING_TO_COMPLETED = 10;

    // 由下载完成到未下载的过程（即删除文件）
    public final static int STATUS_COMPLETED_TO_NORMAL = 11;

    // 由暂停到未下载的过程（即取消下载）
    public final static int STATUS_PAUSE_TO_NORMAL = 12;

    // 由下载失败到未下载的过程（即取消下载）
    public final static int STATUS_FAILED_TO_NORMAL = 13;

    // 由连接服务器到正在下载的过程
    public final static int STATUS_LOADING_TO_DOWNLOADING = 14;

    // 由连接服务器到下载失败的过程（即连接服务器失败）
    public final static int STATUS_LOADING_TO_FFILED = 15;

    // 由下载失败到连接服务器的过程（即重试）
    public final static int STATUS_FAILED_TO_LOADING = 16;

    // 由正在下载到暂停的过程
    public final static int STATUS_DOWNLOADING_TO_PAUSE = 17;

    // 由正在下载到失败的过程
    public final static int STATUS_DOWNLOADING_TO_FAILED = 18;

    // 由暂停到正在下载的过程
    public final static int STATUS_PAUSE_TO_DOWNLOADING = 19;

    // 由失败到正在下载的过程
    public final static int STATUS_FAILED_TO_DOWNLOADING = 20;

    // 已经安装
    public final static int STATUS_INSTALLED = 21;

    /**
     * 解压中
     */
    public final static int STATUS_UPZIPING = 22;

    //准备下载
    public final static int STATUS_PENDING=23;



}
