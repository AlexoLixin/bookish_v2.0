package cn.don9cn.blog.plugins.actionmsg.util;

import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;

import java.util.Optional;
import java.util.function.Function;

/**
 * @Author: liuxindong
 * @Description: action层返回信息工具类
 * @Create: 2017/10/9 15:29
 * @Modify:
 */
public class ActionMsgUtil {

    /**
     * 基础保存
     * @param optional
     * @return
     */
    public static ActionMsg doSave(Optional<Integer> optional){
        if(optional.isPresent() && optional.get()>0){
            return new ActionMsg(true,"添加成功");
        }
        return new ActionMsg(false,"添加失败");
    }

    /**
     * 基础更新
     * @param optional
     * @return
     */
    public static ActionMsg doUpdate(Optional<Integer> optional){
        if(optional.isPresent() && optional.get()>0){
            return new ActionMsg(true,"更新成功");
        }
        return new ActionMsg(false,"更新失败");
    }

    /**
     * 基础删除
     * @param optional
     * @return
     */
    public static ActionMsg doRemove(Optional<Integer> optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"删除成功,"+optional.get()+"条数据被删除");
        }
        return new ActionMsg(false,"删除失败");
    }

    /**
     * 基础单体查询
     * @param optional
     * @return
     */
    public static ActionMsg doFindById(Optional optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"查询成功").setObj(optional.get());
        }
        return new ActionMsg(false,"查询失败");
    }

    /**
     * 基础集合查询
     * @param optional
     * @return
     */
    public static ActionMsg doFindAll(Optional optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"查询成功").setObj(optional.get());
        }
        return new ActionMsg(false,"查询失败");
    }

    /**
     * 基础条件查询
     * @param optional
     * @return
     */
    public static ActionMsg doFindListByParams(Optional optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"查询成功").setObj(optional.get());
        }
        return new ActionMsg(false,"查询失败");
    }

    /**
     * 基础分页查询
     * @param optional
     * @return
     */
    public static ActionMsg doFindByPage(Optional optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"分页数据查询成功").setObj(optional.get());
        }
        return new ActionMsg(false,"分页数据查询失败");
    }

    /**
     * 自定义实现
     * @param optional
     * @param function
     * @return
     */
    public static Object apply(Optional optional,Function<Optional,Object> function){
        return function.apply(optional);
    }


}
