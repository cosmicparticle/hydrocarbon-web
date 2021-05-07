package cho.carbon.hc.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import cho.carbon.panel.Discoverer;
import cho.carbon.panel.PanelFactory;
import cho.carbon.vo.BytesInfoVO;

/**
 * 文件发布器
 * 
 * @author wnq
 * @date 2020年7月25日 下午3:32:13
 */
public class FilePublisher {

	private static FilePublisher instance;

	static Logger logger = LoggerFactory.getLogger(FilePublisher.class);
	//private NumberFormat kbSizeFormat = new DecimalFormat("0.00");
	private Cache<String, BytesInfoVO> bytesInfoVOs;
	private Discoverer discoverer;

	public FilePublisher() {
		bytesInfoVOs = Caffeine.newBuilder().maximumSize(100).build();
		discoverer = PanelFactory.getBytesInfoVODiscoverer();
	}

	public static FilePublisher getContextInstance() {
		return getInstance();

	}

	public static FilePublisher getInstance() {
		synchronized (FilePublisher.class) {
			if (instance == null) {
				instance = new FilePublisher();
			}
		}
		return instance;
	}

	public void putBytesInfoVO(String fileCode, BytesInfoVO bytesInfoVO){
		bytesInfoVOs.put(fileCode,bytesInfoVO);
	}

	public BytesInfoVO getBytesInfoVO(String recordName, String fileCode, String fileName) {
		BytesInfoVO result = bytesInfoVOs.getIfPresent(fileCode);
		if (result == null) {// 加载
			byte[] body = discoverer.discoverBytesInfo(recordName, fileCode);
			String suffix=null;
			if(fileName.contains(".")) {
				suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			} 
			
			if (body != null) {
				result = new BytesInfoVO(recordName, fileCode, fileName, suffix, Double.valueOf(body.length) / 1000,
						body);
				bytesInfoVOs.put(fileCode, result);
			}
		}
		return result;
	}

}
