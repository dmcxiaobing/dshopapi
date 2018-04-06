package com.david.dshopapi.service.impl;

import com.david.dshopapi.core.AbstractService;
import com.david.dshopapi.model.Main;
import com.david.dshopapi.service.MainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * app首页
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Service
@Transactional
public class MainServiceImpl extends AbstractService<Main> implements MainService {
}
