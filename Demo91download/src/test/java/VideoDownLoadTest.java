import com.liujun.utils.ApiServiceUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoDownLoadTest {


    @Test
    public void test(){
        Document doc = Jsoup.parse(ApiServiceUtils.getHtml("http://91porn.com/view_video.php?viewkey=9caed099a9b3ed8479de"));

        Element elementById = doc.getElementById("viewvideo-title");
        System.out.println(ApiServiceUtils.isNull(elementById,0));
    }
    @Test
    public void videoDownLoad() throws IOException {

        String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //输入你要保存的链接文件的地址
        FileOutputStream fis = new FileOutputStream("/Users/mac/Documents/windowsDocument/url/"+fileName+".txt",true);
        for (int i = 22; i < 100; i++) {

            String html = ApiServiceUtils.getHtml("http://91porn.com/video.php?category=rf&page="+i);
            System.out.println("成功获取第"+ i +"页面");
            Document document = Jsoup.parse(html);
            Elements select = document.select("div.listchannel");
            int j = 1;

            for (Element element : select) {
                //获取视频页面
                Element aTag = element.select("a").first();
                String aUrl = ApiServiceUtils.isNull(aTag, 1, "href");
                Document doc = Jsoup.parse(ApiServiceUtils.getHtml(aUrl));

                //获取标题
                Element elementById = doc.getElementById("viewvideo-title");
                String titleName = ApiServiceUtils.isNull(elementById, 0);

                //获取视频资源地址
                System.out.println("成功获取第"+((i-1)*20+j)+"个");
                Elements video = doc.select("video.video-js");
                String url = ApiServiceUtils.isNull(video.select("source").first(), 1, "src");

                //写进文件中
                fis.write(("第"+((i-1)*20+j)+"个 " + titleName + " ").getBytes());
                fis.write(url.getBytes());
                fis.write("\n".getBytes());
                System.out.println("成功写进第"+((i-1)*20+j)+"个");
                j++;
            }
        }

        fis.close();

    }
}
