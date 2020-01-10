package common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HttpUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static int TIME_OUT=30000;
	static{
		 String timeoutStr=PropertiesUtils.getProperty("HTTP_CLIEBT_TIMEOUT");
		 if(timeoutStr!=null){
			 TIME_OUT=Integer.parseInt(timeoutStr) ;
		 }
	}

	public static String get(String url){
				
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT).build();
        httpGet.setConfig(requestConfig);

        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

               logger.warn("request url failed, http code={}, url={}" , response.getStatusLine().getStatusCode(), url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "utf-8");
              
                return result;
            }
        } catch (IOException e) {
        	logger.error("request url={}", url, e);
           
        } finally {
             	
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
           /* httpGet.releaseConnection();  //释放连接
            
            if(httpClient!=null){
            	try {
					httpClient.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
            }*/
            
        }

        return null;
    }
	
	public static String post(String url, Object data) {
		
		String jsondata = JSON.toJSONString(data);
		logger.info("{}==post调用参数==：{}",url,jsondata);
		
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
        		setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");
        
        try {
        	StringEntity requestEntity = new StringEntity(jsondata, "utf-8");
//        	requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);
            
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

               logger.warn("request url failed, http code={},, url={}", response.getStatusLine().getStatusCode(),url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return resultStr;
            }
        } catch (IOException e) {
            logger.error("请求异常:{}", url , e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        }

        return null;
    }
	
	
	/*public static JSONObject uploadMedia(String url, File file) throws OApiException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);

        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",
        		new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
        httpPost.setEntity(requestEntity);

        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                                   + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
                    // 成功
                	result.remove("errcode");
                	result.remove("errmsg");
                    return result;
                } else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                    throw new OApiException(errCode, errMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
	
	
	public static JSONObject downloadMedia(String url, String fileDir) throws OApiException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);

        try {
            HttpContext localContext = new BasicHttpContext();

            response = httpClient.execute(httpGet, localContext);

            RedirectLocations locations = (RedirectLocations) localContext.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
            if (locations != null) {
                URI downloadUrl = locations.getAll().get(0);
                String filename = downloadUrl.toURL().getFile();
                System.out.println("downloadUrl=" + downloadUrl);
                File downloadFile = new File(fileDir + File.separator + filename);
                FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
                JSONObject obj = new JSONObject();
                obj.put("downloadFilePath", downloadFile.getAbsolutePath());
                obj.put("httpcode", response.getStatusLine().getStatusCode());
                
               
                
                return obj;
            } else {
                if (response.getStatusLine().getStatusCode() != 200) {

                    System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                                       + ", url=" + url);
                    return null;
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultStr = EntityUtils.toString(entity, "utf-8");

                    JSONObject result = JSON.parseObject(resultStr);
                    if (result.getInteger("errcode") == 0) {
                        // 成功
                    	result.remove("errcode");
                    	result.remove("errmsg");
                        return result;
                    } else {
                        System.out.println("request url=" + url + ",return value=");
                        System.out.println(resultStr);
                        int errCode = result.getInteger("errcode");
                        String errMsg = result.getString("errmsg");
                        throw new OApiException(errCode, errMsg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }*/
	
	public static void main(String[] args) {
		
		String str = "{\"password\":\"test2\",\"userId\":\"test2\"}";
//		String res = HttpUtils.post("http://180.1.34.47:8080/gyksp/login",str);
		String res = HttpUtils.post("http://180.1.34.47:8080/gyksp/login", str);
		System.out.println(res);
	}
}
