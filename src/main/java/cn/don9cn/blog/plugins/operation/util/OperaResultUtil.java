package cn.don9cn.blog.plugins.operation.util;

import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.operation.core.OperaResult;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

/**
 * @Author: liuxindong
 * @Description: action层返回信息工具类
 * @Create: 2017/10/9 15:29
 * @Modify:
 */
public class OperaResultUtil {

    /**
     * 基础保存
     * @param optional
     * @return
     */
    public static OperaResult baseInsert(OptionalInt optional){
        if(optional.isPresent() && optional.getAsInt()>0){
            return new OperaResult(true,"添加成功");
        }
        return new OperaResult(false,"添加失败");
    }

    /**
     * 基础批量保存
     * @param optional
     * @return
     */
    public static OperaResult baseInsertBatch(OptionalInt optional){
        if(optional.isPresent() && optional.getAsInt()>0){
            return new OperaResult(true,"添加成功");
        }
        return new OperaResult(false,"添加失败");
    }

    /**
     * 基础更新
     * @param optional
     * @return
     */
    public static OperaResult baseUpdate(OptionalInt optional){
        if(optional.isPresent() && optional.getAsInt()>0){
            return new OperaResult(true,"更新成功");
        }
        return new OperaResult(false,"更新失败");
    }

    /**
     * 基础删除
     * @param optional
     * @return
     */
    public static OperaResult baseRemove(OptionalInt optional){
        if(optional.isPresent()){
            return new OperaResult(true,"删除成功,"+optional.getAsInt()+"条数据被删除");
        }
        return new OperaResult(false,"删除失败");
    }

    /**
     * 基础批量删除
     * @param optional
     * @return
     */
    public static OperaResult baseRemoveBatch(OptionalInt optional){
        if(optional.isPresent()){
            return new OperaResult(true,"删除成功,"+optional.getAsInt()+"条数据被删除");
        }
        return new OperaResult(false,"删除失败");
    }

    /**
     * 基础单体查询
     * @param optional
     * @return
     */
    public static <T extends BaseModel> OperaResult baseFindOne(Optional<T> optional){
        return optional.map(t -> new OperaResult(true, "查询成功").setObj(t))
                        .orElseGet(() -> new OperaResult(false, "查询失败"));
    }

    /**
     * 基础集合查询
     * @param optional
     * @return
     */
    public static <T extends BaseModel> OperaResult baseFindAll(Optional<List<T>> optional){
        return optional.map(ts -> new OperaResult(true, "查询成功").setObj(ts))
                        .orElseGet(() -> new OperaResult(false, "查询失败"));
    }

    /**
     * 基础条件查询
     * @param optional
     * @return
     */
    public static <T extends BaseModel> OperaResult baseFindListByParams(Optional<List<T>> optional){
        return optional.map(ts -> new OperaResult(true, "查询成功").setObj(ts))
                        .orElseGet(() -> new OperaResult(false, "查询失败"));
    }

    /**
     * 基础分页查询
     * @param optional
     * @return
     */
    public static <T extends BaseModel> OperaResult baseFindByPage(Optional<PageResult<T>> optional){
        return optional.map(tPageResult -> new OperaResult(true, "分页数据查询成功").setObj(tPageResult))
                        .orElseGet(() -> new OperaResult(false, "分页数据查询失败"));
    }

    /**
     * 自定义实现
     * @param optional
     * @param function
     * @return
     */
    public static <T extends BaseModel> OperaResult apply(Optional<T> optional,Function<Optional<T>,OperaResult> function){
        return function.apply(optional);
    }


}
