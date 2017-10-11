package cn.don9cn.blog.support.vue;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 文件上传后的返回结果
 * @Create: 2017/10/11 10:04
 * @Modify:
 */
public class VueImageUploadMsg {

    /**
     * 上传结果状态码, 0 为成功, 1 为失败(WangEditor中定义的)
     */
    private final int errno;

    /**
     * 图片链接
     */
    private final List<String> data;

    public VueImageUploadMsg(int errno,List<String> data){
        this.errno = errno;
        this.data = data;
    }


    public int getErrno() {
        return errno;
    }

    public List<String> getData() {
        return data;
    }
}
