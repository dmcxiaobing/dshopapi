package com.david.dshopapi.model;

import java.io.Serializable;
import java.util.List;

/**
 * 简单处理后的首页数据。Javabean封装
 *
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class MainModel implements Serializable {


    private List<String> bannerTitles;
    private List<String> bannerImages;
    private List<String> contentTitles;
    private List<String> contentContents;

    public List<String> getBannerTitles() {
        return bannerTitles;
    }

    public void setBannerTitles(List<String> bannerTitles) {
        this.bannerTitles = bannerTitles;
    }

    public List<String> getBannerImages() {
        return bannerImages;
    }

    public void setBannerImages(List<String> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public List<String> getContentTitles() {
        return contentTitles;
    }

    public void setContentTitles(List<String> contentTitles) {
        this.contentTitles = contentTitles;
    }

    public List<String> getContentContents() {
        return contentContents;
    }

    public void setContentContents(List<String> contentContents) {
        this.contentContents = contentContents;
    }
}
