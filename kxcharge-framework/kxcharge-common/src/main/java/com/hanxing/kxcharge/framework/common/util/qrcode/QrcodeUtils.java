package com.hanxing.kxcharge.framework.common.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Hashtable;

/**
 * 12  * 配置图像写入器
 * 13  *
 * 14  * @author 18316
 * 15  *
 * 16
 */
public class QrcodeUtils {
    /**
     * 保存二维码图片
     *
     * @param text     二维码内容
     * @param width    宽度
     * @param height   高度
     * @param filePath 文件保存路径
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * 返回二维码字节数组
     *
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);//去除多余白边
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
    }


    public static String mergeImageToBase64(@NotNull(message = "电枪编号不能为空") String connectorCode, BufferedImage originalImage, String qrCodeBase64, int x, int y) throws IOException {
        // decode the base64 string to BufferedImage object
        byte[] qrCodeBytes = Base64.getDecoder().decode(qrCodeBase64);
        BufferedImage qrCodeImage = ImageIO.read(new ByteArrayInputStream(qrCodeBytes));

        // create a new BufferedImage object with the same size as the original image
        BufferedImage mergeImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        // get the Graphics2D object for drawing
        Graphics2D g2 = mergeImage.createGraphics();

        // copy the original image to the new image
        g2.drawImage(originalImage, 0, 0, null);

        // draw the qr code image on the new image
        g2.drawImage(qrCodeImage, x, y, null);


        // draw the text on the new image
        g2.setColor(Color.BLACK); // set the color for the text
        g2.drawString("No:"+connectorCode, x+10, y + qrCodeImage.getHeight() + 20); // adjust the position as needed

        // release the graphics object
        g2.dispose();

        // convert the merged image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(mergeImage, "png", baos);
        byte[] mergedImageBytes = baos.toByteArray();

        // return the merged image base64 string
        return Base64.getEncoder().encodeToString(mergedImageBytes);
    }

    /**
     * 将二维码数组进行Base64编码返回
     * 然后把编码后图片在前端页面直接取值即可
     * <img id="cashier_page_p_w_picpath" src='data:img/jpg;base64,${base64Img }'/>
     *
     * @return
     */
    public static void main(String[] args) {
        try {
            // 加载原始图片和二维码图片
            File originalImageFile = new File("F:\\code\\fcf584e1a4375d3166773a21eb939aaf_351x503a0a15.png");
            File qrCodeImageFile = new File("F:\\code\\code.png");


                // 读取原始图片
                BufferedImage originalImage = ImageIO.read(originalImageFile);

                // 读取二维码图片
                BufferedImage qrCodeImage = ImageIO.read(qrCodeImageFile);

                // 指定二维码在原始图片上的位置（左上角坐标）
                int x = 55;
                int y = 225;

                // 在原始图片上绘制二维码
                Graphics2D graphics = originalImage.createGraphics();
                graphics.drawImage(qrCodeImage, x, y, null);
                graphics.dispose();

                // 输出合成后的图片
                File outputFile = new File("output_image.jpg");
                ImageIO.write(originalImage, "jpg", outputFile);
                System.out.println("合成图片成功: " + outputFile.getAbsolutePath());



            //System.out.println(base64Str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
