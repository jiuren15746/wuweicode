import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class CdnDownload {


    public static void main(String[] args) throws Exception {

        String urlstrs = "https://sg-production-cdn.zoloz.com/page/portal-uiconfig-web/static/card.97f8018d.png\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-uiconfig-web/static/faceguide.e4594d56.gif\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-uiconfig-web/static/scan-bg-image.53361ae3.png\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-uiconfig-web/umi.912f0019.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-uiconfig-web/umi.d509d530.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-uiconfig-web/zoloz.ico\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-master-web/umi.3ec3b9be.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-master-web/umi.52a40a32.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-master-web/zoloz.ico\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-login-web/static/bg.54730cfc.jpg\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-login-web/umi.b8ba2bbd.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-login-web/umi.c4bb5e03.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-admin-web/umi.82783f47.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-admin-web/umi.a968b4fb.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-admin-web/zoloz.ico\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-developer-web/umi.b9fe4467.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-developer-web/umi.de7821e7.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-developer-web/zoloz.ico\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-case-web/umi.9991fdc2.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-case-web/umi.a8170dcf.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-case-web/zoloz.ico\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-dashboard-web/umi.3fd26eeb.css\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-dashboard-web/umi.7c7dab02.js\n" +
                "https://sg-production-cdn.zoloz.com/page/portal-dashboard-web/zoloz.ico";

        int count = 0;
        for (String urlstr : urlstrs.split("\n")) {
            if (urlstr != null) {
                download(urlstr.trim());
                count++;
            }
        }
        System.out.println("total count: " + count);
    }


    private static boolean download(String urlstr) throws Exception {
        // create local file
        String uri = urlstr.split("https://sg-production-cdn.zoloz.com")[1];
        String filePath = "/Users/will/Downloads" + uri;

        // !!! 走到上级目录。直接用new File(filePath)，java会认为这是一个目录。
        new File(filePath).getParentFile().mkdirs();

        FileOutputStream outputStream = new FileOutputStream(filePath, false);
        InputStream inputStream = new URL(urlstr).openStream();
        byte[] buffer = new byte[1024];

        try {
            for (; ; ) {
                int readBytes = inputStream.read(buffer);
                if (readBytes == -1) {
                    break;
                }
                outputStream.write(buffer, 0, readBytes);
            }
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        System.out.println("download success: " + urlstr);
        return true;
    }
}
