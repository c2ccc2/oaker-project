package com.oaker.web.controller.prototype;

import com.oaker.common.core.controller.BaseController;
import com.oaker.prototype.doman.entity.PrProtoRecord;
import com.oaker.prototype.doman.entity.PrPrototype;
import com.oaker.prototype.service.impl.PrProtoRecordServiceImpl;
import com.oaker.prototype.service.impl.PrPrototypeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @Description : 原型页面访问
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/25 13:59
 */
@Controller
@RequestMapping("/profile/pr/ui")
public class PrototypeUiController extends BaseController {

    @Resource
    private PrPrototypeServiceImpl prPrototypeService;

    @Resource
    private PrProtoRecordServiceImpl prProtoRecordService;

    @GetMapping("/{pubId}")
    public String index(@NotBlank @PathVariable("pubId") String pubId) throws UnsupportedEncodingException {
        PrPrototype byPubId = prPrototypeService.getByPubId(pubId);
        if (Objects.isNull(byPubId) || Objects.isNull(byPubId.getRecordId())) {
            return redirect("/static/404.html");
        }
        PrProtoRecord record = prProtoRecordService.selectById(byPubId.getRecordId());
        String prototypeUrl = record.getPrototypeUrl();
        String[] split = prototypeUrl.split("/");
        StringJoiner joiner = new StringJoiner("/");
        for (String uri : split) {
            joiner.add(URLEncoder.encode(uri, "UTF-8"));
        }
        return redirect(joiner.toString());
    }


}
