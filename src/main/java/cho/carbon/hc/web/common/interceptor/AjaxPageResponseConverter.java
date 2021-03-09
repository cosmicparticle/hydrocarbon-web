package cho.carbon.hc.web.common.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cho.carbon.hc.web.dto.ajax.AjaxPageResponse;


public class AjaxPageResponseConverter implements HttpMessageConverter<AjaxPageResponse>{

	private Charset defaultCharset = Charset.forName("UTF-8"); 

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		boolean assignableFrom = AjaxPageResponse.class.isAssignableFrom(clazz);
		return assignableFrom;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		boolean assignableFrom = AjaxPageResponse.class.isAssignableFrom(clazz);
		return assignableFrom;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		
		List<MediaType> asList = new ArrayList<MediaType>();
		asList.add(MediaType.APPLICATION_JSON);
		return asList;
	}

	@Override
	public AjaxPageResponse read(Class<? extends AjaxPageResponse> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		AjaxPageResponse ajaxPageResponse = new AjaxPageResponse();
		return ajaxPageResponse;
	}

	@Override
	public void write(AjaxPageResponse t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		outputMessage.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
		OutputStream body = outputMessage.getBody();
		StreamUtils.copy(toJson(t), defaultCharset, body);
		body.close();
	}
	
	
	
	public String toJson(AjaxPageResponse t){
		SerializeWriter out = new SerializeWriter();
		String result = null;
        try {  
            JSONSerializer serializer = new JSONSerializer(out);  
            serializer.config(SerializerFeature.WriteEnumUsingName, false);
            serializer.config(SerializerFeature.WriteEnumUsingToString, true);
            serializer.write(t);
            result = out.toString();
        } finally {  
            out.close();  
        }  
        return result;
	}
	
}
