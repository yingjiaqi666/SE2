package com.seecoder.BlueWhale.exception;

/**
 * @Author: DingXiaoyu
 * @Date: 0:26 2023/11/26
 * 你可以在这里自定义Exception
*/
public class BlueWhaleException extends RuntimeException{

    public BlueWhaleException(String message){
        super(message);
    }

    public static BlueWhaleException notLogin(){
        return new BlueWhaleException("未登录!");
    }
    public static BlueWhaleException fileUploadFail(){
        return new BlueWhaleException("图片上传失败");
    }
    public static BlueWhaleException genFail(){return new BlueWhaleException("生成URL失败");
    }

    public static BlueWhaleException LoginError(){return new BlueWhaleException("用户不存在/用户密码错误");
    }
    public static BlueWhaleException userNameAlreadyExists(){return new BlueWhaleException("用户名已存在");}

    public static BlueWhaleException userNameNotFound(){return new BlueWhaleException("用户名不存在");}

    public static BlueWhaleException productNotFound(){return  new BlueWhaleException("商品不存在");}

    public static BlueWhaleException overStock(){return new BlueWhaleException("超过库存数量");}

    public static BlueWhaleException productNotInCart(){return new BlueWhaleException("购物车商品不存在");}

}
