package com.seecoder.TomatoMall.exception;

/**
 * @Author: DingXiaoyu
 * @Date: 0:26 2023/11/26
 * 你可以在这里自定义Exception
*/
public class TomatoMallException extends RuntimeException{

    public TomatoMallException(String message){
        super(message);
    }

    public static TomatoMallException notLogin(){
        return new TomatoMallException("未登录!");
    }
    public static TomatoMallException fileUploadFail(){
        return new TomatoMallException("图片上传失败");
    }
    public static TomatoMallException genFail(){return new TomatoMallException("生成URL失败");
    }

    public static TomatoMallException LoginError(){return new TomatoMallException("用户不存在/用户密码错误");
    }
    public static TomatoMallException userNameAlreadyExists(){return new TomatoMallException("用户名已存在");}

    public static TomatoMallException userNameNotFound(){return new TomatoMallException("用户名不存在");}

    public static TomatoMallException productNotFound(){return  new TomatoMallException("商品不存在");}

    public static TomatoMallException overStock(){return new TomatoMallException("超过库存数量");}

    public static TomatoMallException productNotInCart(){return new TomatoMallException("购物车商品不存在");}

    public static TomatoMallException orderNotFound() {return new TomatoMallException("订单不存在");}

    public static TomatoMallException tagNameAlreadyExists(){return new TomatoMallException("tag已存在");}

    public static TomatoMallException tagNotFound(){return  new TomatoMallException("Id不存在");}

    public static TomatoMallException unpaidOrderOversized() {return new TomatoMallException("未支付订单过多");}

    public static TomatoMallException commentNotFound(){return new TomatoMallException("评论不存在");};
}
