package com.david.dshopapi.web.controller;

import com.david.dshopapi.core.Result;
import com.david.dshopapi.core.ResultGenerator;
import com.david.dshopapi.model.Main;
import com.david.dshopapi.model.MainModel;
import com.david.dshopapi.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 * <p>
 * 首页。包含。广告banner， 新闻内容
 */
@RestController
@RequestMapping("/main")
public class MainController {


    @Autowired
    private MainService mainService;


    @GetMapping("/main")
    public Result main() {
        List<Main> dbModels = mainService.findAll();

        return ResultGenerator.genSuccessResult(dbModels);
    }

    @GetMapping("/listMain")
    public Result listMain() {

        List<MainModel> models = new ArrayList<>();

        List<Main> dbModels = mainService.findAll();
        // 查询出所有的数据
        if (dbModels!=null && dbModels.size()>0){
            for(Main main : dbModels){
                String bannerTitle = main.getBannerTitle();
                String bannerImage = main.getBannerImage();
                String contentTitle = main.getContentTitle();
                String contentContent = main.getContentContent();
                // 然后以逗号分隔，取出list
                MainModel mainModel = new MainModel();
                List<String> bannerLists = new ArrayList<>();
                String[] bannerTitles = bannerTitle.split(",");
                for (int i = 0;i<bannerTitles.length;i++){
                    bannerLists.add(bannerTitles[i]);
                }

                List<String> bannerImageLists = new ArrayList<>();
                String[] bannerImages = bannerImage.split(",");
                for (int i = 0;i<bannerImages.length;i++){
                    bannerImageLists.add(bannerImages[i]);
                }
                List<String> contentTitleLists = new ArrayList<>();
                String[] contentTitles = contentTitle.split(",");
                for (int i = 0;i<contentTitles.length;i++){
                    contentTitleLists.add(contentTitles[i]);
                }
                List<String> contentContentList = new ArrayList<>();
                String[] contentContents = contentContent.split(",");
                for (int i = 0;i<contentContents.length;i++){
                    contentContentList.add(contentContents[i]);
                }

                mainModel.setBannerTitles(bannerLists);
                mainModel.setBannerImages(bannerImageLists);
                mainModel.setContentTitles(contentTitleLists);
                mainModel.setContentContents(contentContentList);
                models.add(mainModel);
            }
        }
        return ResultGenerator.genSuccessResult(models);
    }
}
