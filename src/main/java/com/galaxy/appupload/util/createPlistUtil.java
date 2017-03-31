package com.galaxy.appupload.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;

public class createPlistUtil {
    public static String createPlist(HttpServletRequest request) throws IOException{
        System.out.println("==========��ʼ����plist�ļ�");
        //�����ַӦ���Ǵ����ķ�������ַ�������������ɵ����ش��̵�ַ
        final String path = request.getSession().getServletContext().getRealPath("/");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String plistFile = "a.plist";
        final String PLIST_PATH = path + plistFile;
        file = new File(PLIST_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String plist = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                 + "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
                 + "<plist version=\"1.0\">\n" + "<dict>\n"
                 + "<key>items</key>\n" 
                 + "<array>\n" 
                 + "<dict>\n"
                 + "<key>assets</key>\n" 
                 + "<array>\n" 
                 + "<dict>\n"
                 + "<key>kind</key>\n"
                 + "<string>software-package</string>\n"
                 + "<key>url</key>\n"
                 //��֮ǰ���ϴ���ipa�ļ�·��
                 + "<string>http://127.0.0.1/project/upload/files/20160504201048174_7836_19.ipa</string>\n" 
                 + "</dict>\n" 
                 + "</array>\n"
                 + "<key>metadata</key>\n"
                 + "<dict>\n"
                 + "<key>bundle-identifier</key>\n"
                 //����ǿ������˺��û�����Ҳ����Ϊ�գ�Ϊ�հ�װʱ������ͼ�꣬���֮����Կ���
                 + "<string>cn.vrv.im-inhouse</string>\n"
                 + "<key>bundle-version</key>\n"
                 + "<string>1.0.7</string>\n"
                 + "<key>kind</key>\n"
                 + "<string>software</string>\n"
                 + "<key>subtitle</key>\n"
                 + "<string>����</string>\n"
                 + "<key>title</key>\n"
                 + "<string></string>\n" 
                 + "</dict>\n" 
                 + "</dict>\n"
                 + "</array>\n"
                 + "</dict>\n"
                 + "</plist>";
        try {
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(output, "UTF-8");
            writer.write(plist);
            writer.close();
            output.close();
        } catch (Exception e) {
            System.err.println("==========����plist�ļ��쳣��" + e.getMessage());
        }
        System.out.println("==========�ɹ�����plist�ļ�");
        return PLIST_PATH;
    }

    public static String createHtml(String plistPath,HttpServletRequest request) {
        System.out.println("==========��ʼ����html�ļ�");

        //�����ַӦ�������ɵķ�������ַ�������������ɵ����ش��̵�ַ
        final String path = request.getSession().getServletContext().getRealPath("/");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String plistFile = "a.plist";
        final String PLIST_PATH = path + plistFile;
        file = new File(PLIST_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>����</title>\n"
                + "<script type=\"text/javascript\">\n"
                + "var url = '" + plistPath + "';\n"
                + "window.location.href = \"itms-services://?action=download-manifest&url=\" + url;\n"
                + "</script>\n" 
                + "</head>\n" 
                + "<body></body>\n" 
                + "</html>";

        try {
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
            writer.write(html);
            writer.close();
            output.close();
        } catch (IOException e) {
            System.err.println("==========����html�ļ��쳣��" + e.getMessage());
        }
        System.out.print("==========�ɹ�����html�ļ�");

        return "success";
    }

    /*public static void main(String[] args) throws IOException {
        String plistPath = createPlistUtil.createPlist();
        createHtml(plistPath);
    }*/
}