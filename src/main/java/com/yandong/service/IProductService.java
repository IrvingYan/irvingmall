package com.yandong.service;

import com.github.pagehelper.PageInfo;
import com.yandong.common.ServerResponse;
import com.yandong.pojo.Product;
import com.yandong.vo.ProductDetailVo;

/**
 * Created by yandong on 2017/5/22.
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse setSaleStatus(Integer productId,Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);
}
