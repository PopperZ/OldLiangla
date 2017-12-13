package com.brightcns.liangla.service.view;


import com.brightcns.liangla.service.entity.Book;

/**
 * Created by 巩贺 on 2017/10/30.
 */

public interface BookBaseView extends BaseView {
    void onSuccess(Book mBook);
    void onError(String result);
}
