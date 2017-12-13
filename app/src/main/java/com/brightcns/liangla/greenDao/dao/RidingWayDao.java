package com.brightcns.liangla.greenDao.dao;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.greenDao.entity.Table_RidingWay;
import com.brightcns.liangla.greenDao.entity.Table_RidingWayDao;

import java.util.List;

/**
 * Created by zhangfeng on 4/12/17.
 * data access object
 * 闪屏页面初始化数据
 */

public class RidingWayDao {

    /**
     * 增
     * @param table_ridingWay
     */
    public static void insertRidingway(Table_RidingWay table_ridingWay){
        MyApplication.getDaoInstant().getTable_RidingWayDao().insertOrReplace(table_ridingWay);
    }
    /**
     * 批量增加
     * @param list
     */
    public static void insertRidingway(List<Table_RidingWay> list){
            MyApplication.getDaoInstant().getTable_RidingWayDao().insertOrReplaceInTx(list);
    }
    /**
     * 删
     * @param id
     */
    public static void deleteRidingWay(long id){
        MyApplication.getDaoInstant().getTable_RidingWayDao().deleteByKey(id);
    }

    /**
     * 更新
     * @param table_ridingWay
     */
    public static void updateRidingWay(Table_RidingWay table_ridingWay){
        MyApplication.getDaoInstant().getTable_RidingWayDao().update(table_ridingWay);
    }

    /**
     * 条件查询
     * @param areaCode
     * @param businessType
     * @return
     */
    public static String queryBleType(String areaCode,String businessType){
        return MyApplication.getDaoInstant().getTable_RidingWayDao().queryBuilder().where(Table_RidingWayDao.Properties.AreaCode.eq(areaCode)
                , Table_RidingWayDao.Properties.BusinessType.eq(businessType)).unique().getBleType();
    }
}
