package com.zhou.common.support.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 周泽
 * @date Create in 15:02 2020/11/2
 * @Description 导出excel, 不知道为什么不生效,暂时还用老办法,之后再说
 */
public class ResponseExcelReturnValueHandler implements HandlerMethodReturnValueHandler, Ordered {

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ResponseExcel.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws IOException {

        // 检查返回值是否合格
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        ResponseExcel responseExcel = methodParameter.getMethodAnnotation(ResponseExcel.class);
        Assert.state(responseExcel != null, "No @ResponseExcel");
        mavContainer.setRequestHandled(true);

        Map<String, Object> map = new HashMap<>();
        map.put("list", returnValue);
        TemplateExportParams params = new TemplateExportParams(responseExcel.templateUrl(), true);
        params.setColForEach(true);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        String fileName = responseExcel.fileName() + responseExcel.fileSuffix();

        // 直接把文件吐出去
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE-10;
    }
}
