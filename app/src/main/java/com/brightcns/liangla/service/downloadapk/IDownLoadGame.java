package com.brightcns.liangla.service.downloadapk;

/**
 */
public interface IDownLoadGame {

    void downloadFile(String name, final String down_url);

    void updataQM(final String down_url);
}
