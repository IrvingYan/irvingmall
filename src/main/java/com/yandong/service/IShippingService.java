package com.yandong.service;

import com.github.pagehelper.PageInfo;
import com.yandong.common.ServerResponse;
import com.yandong.pojo.Shipping;

/**
 * Created by yandong on 2017/7/8.
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}