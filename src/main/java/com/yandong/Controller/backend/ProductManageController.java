package com.yandong.Controller.backend;

import com.google.common.collect.Maps;
import com.yandong.common.Const;
import com.yandong.common.ServerResponse;
import com.yandong.pojo.Product;
import com.yandong.pojo.User;
import com.yandong.service.IFileService;
import com.yandong.service.IProductService;
import com.yandong.service.IUserService;
import com.yandong.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by yandong on 2017/5/21.
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse SetSaleStatus(HttpSession session, Integer productId, Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //添加动态分页
            return iProductService.getProductList(pageNum,pageSize);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,
                                        String productName,
                                        Integer productId,
                                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    //文件上传
    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path =  request.getSession().getServletContext().getRealPath("upload");
            String tartgetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+tartgetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",tartgetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "richtext_img_upload.do",method = RequestMethod.POST)
    @ResponseBody
    public Map richtextImgUpload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //富文本中对于返回值有自己的要求，我们使用simditor，按照simditor格式返回
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","用户未登录");
            return resultMap;
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path =  request.getSession().getServletContext().getRealPath("upload");
            String tartgetFileName = iFileService.upload(file,path);

            if(StringUtils.isBlank(tartgetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败了");
                return resultMap;
            }

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+tartgetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }
        else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            resultMap.put("file_path","");
            return resultMap;
        }
    }
}
