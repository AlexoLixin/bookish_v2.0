package cn.don9cn.blog.plugins.actionmsg.util;

import cn.don9cn.blog.plugins.actionmsg.core.ActionMsg;

import java.util.Optional;
import java.util.OptionalInt;
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
    public static ActionMsg baseSave(OptionalInt optional){
        if(optional.isPresent() && optional.getAsInt()>0){
            return new ActionMsg(true,"添加成功");
        }
        return new ActionMsg(false,"添加失败");
    }

    /**
     * 基础批量保存
     * @param optional
     * @return
     */
    public static ActionMsg baseSaveBatch(OptionalInt optional){
        if(optional.isPresent() && optional.getAsInt()>0){
            return new ActionMsg(true,"添加成功");
        }
        return new ActionMsg(false,"添加失败");
    }

    /**
     * 基础更新
     * @param optional
     * @return
     */
    public static ActionMsg baseUpdate(OptionalInt optional){
        if(optional.isPresent() && optional.getAsInt()>0){
            return new ActionMsg(true,"更新成功");
        }
        return new ActionMsg(false,"更新失败");
    }

    /**
     * 基础删除
     * @param optional
     * @return
     */
    public static ActionMsg baseRemove(OptionalInt optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"删除成功,"+optional.getAsInt()+"条数据被删除");
        }
        return new ActionMsg(false,"删除失败");
    }

    /**
     * 基础批量删除
     * @param optional
     * @return
     */
    public static ActionMsg baseRemoveBatch(OptionalInt optional){
        if(optional.isPresent()){
            return new ActionMsg(true,"删除成功,"+optional.getAsInt()+"条数据被删除");
        }
        return new ActionMsg(false,"删除失败");
    }

    /**
     * 基础单体查询
     * @param optional
     * @return
     */
    public static ActionMsg baseFindById(Optional optional){
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
    public static ActionMsg baseFindAll(Optional optional){
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
    public static ActionMsg baseFindListByParams(Optional optional){
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
    public static ActionMsg baseFindByPage(Optional optional){
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
