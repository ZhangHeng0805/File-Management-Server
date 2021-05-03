package com.zhangheng.file.controller3;

import com.zhangheng.file.repository.SubmitGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("OrderList")
public class FindOrderController {
    @Autowired
    private SubmitGoodsRepository submitGoodsRepository;

}
