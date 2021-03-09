package cho.carbon.hc.web.common.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import cho.carbon.vo.BytesInfoVO;

public class RequestParameterMapCompositeResolver implements HandlerMethodArgumentResolver{

	Logger logger = LoggerFactory.getLogger(RequestParameterMapCompositeResolver.class);
	
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> paramClass = parameter.getParameterType();
		if(RequestParameterMapComposite.class.isAssignableFrom(paramClass)){
			return true;
		}
		return false;
	}

	@Override
	public RequestParameterMapComposite resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		RequestParameterMapComposite composite = new RequestParameterMapComposite();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String[]> pMap = webRequest.getParameterMap();
		logger.debug("提交请求数据");
		logger.info(pMap.toString());
		if(pMap != null) {
			pMap.forEach((name, val)->{
				if(val != null) {
					if(val.length == 1) {
						map.put(name, val[0]);
					}else if(val.length > 1) {
						if(name.endsWith(".唯一编码")) {
							map.put(name, val[0]);
						}else {
							map.put(name, val);
						}
					}else {
						map.put(name, null);
					}
				}else {
					map.put(name, null);
				}
			});
		}
		try {
			Map<String, BytesInfoVO> fileMap = exactFileMap(webRequest.getNativeRequest(HttpServletRequest.class));
			map.putAll(fileMap);
		} catch (Exception e) {
			logger.error("解析上传文件时发生错误", e);
		}
		composite.setMap(map);
		return composite;
	}
	
	@Resource
	MultipartResolver resolver;
	
	private Map<String, BytesInfoVO> exactFileMap(HttpServletRequest request) throws FileUploadException {
		Map<String, BytesInfoVO> result = new HashMap<>();
		if(request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = mreq.getFileMap();
			fileMap.forEach((key, file)->{
				try {
					result.put(key, new BytesInfoVO(file.getOriginalFilename(),null,Double.valueOf(file.getSize())/1000,file.getBytes()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		return result;
	}

}
