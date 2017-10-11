package cn.don9cn.blog.autoconfigs.filepath;

/**
 * @Author: liuxindong
 * @Description: 记录应用中各种文件的保存位置
 * @Create: 2017/10/11 9:45
 * @Modify:
 */
public class FileSavePathConfig {

    /**
     * 服务器地址
     */
    private String serverUrl;

    /**
     * 服务器根目录
     */
    private String serverRoot;

    /**
     * 图片保存位置
     */
    private String images;

    /**
     * 上传文件保存位置
     */
    private String uploadFiles;

    public FileSavePathConfig() {

    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerRoot() {
        return serverRoot;
    }

    public void setServerRoot(String serverRoot) {
        this.serverRoot = serverRoot;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(String uploadFiles) {
        this.uploadFiles = uploadFiles;
    }
}
