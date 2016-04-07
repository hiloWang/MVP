package com.user.hilo.others;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/30.
 * 尽量不要使用setImageBitmap或setImageResource或BitmapFactory.decodeResource直接使用图片路径来设置一张大图，
 * 因为这些函数在完成decode后，最终都是通过java层的createBitmap来完成的，需要消耗更多内存。
 * 改用先通过BitmapFactory.decodeStream方法，创建出一个bitmap，再调用上述方法将其设为ImageView的 source。
 * decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，
 * 无需再使用java层的createBitmap，从而节省了java层的空间。下面是使用InputStream加载图片的几种方法
 */
public class TestBitmapOOM {

    /**
     * 示例1：只为讲解，其实并没有实际解决卡顿效果
     * @param context
     * @param imgResource
     * @return
     */
    public static Bitmap loaderResourceImage(Context context, int imgResource) {

//        1）加载Raw下载图片
//        InputStream inputStream = MyApplication.mContext.getResources().openRawResource(R.raw.test_bitmap);

//        2）加载Asset目录下的图片
//        AssetManager asMgr = MyApplication.mContext.getAssets();
//        InputStream inputStream = asMgr.open(fileName)

//        3）加载SD卡目录下的图片
//        String path = Environment.getExternalStorageDirectory().toString() + "/DCIM/device.png";
//        InputStream inputStream = new FileInputStream(path);

        /*// 缩放比例
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_bitmap);
        int width = tempBitmap.getWidth();
        int height = tempBitmap.getHeight();
        float scaleWidth = (float) 120 / width;
        float scaleHeight = (float) 120 / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap scaleBitmap = Bitmap.createBitmap(tempBitmap, 0, 0, width, height, matrix, true);
        tempBitmap.recycle();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaleBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());*/

        // 1、转位图 {decodeStream（inputStream）}
        InputStream inputStream = context.getResources().openRawResource(imgResource);
        // 2、为位图设置100K缓存
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTempStorage = new byte[100 * 1024];
        // 3、设置位图颜色显示以及优化方式
        //ALPHA_8：每个像素占用1byte内存（8位）
        //ARGB_4444:每个像素占用2byte内存（16位）
        //ARGB_8888:每个像素占用4byte内存（32位）
        //RGB_565:每个像素占用2byte内存（16位）
        // Android默认的颜色模式为ARGB_8888，这个颜色模式色彩最细腻，显示质量最高。但同样的，占用的内存//也最大。也就意味着一个像素点占用4个字节的内存。
        // 我们来做一个简单的计算题(图片分辨率3200*2400)：3200*2400*4 bytes =30M。如此惊人的数字！哪怕生命周期超不过10s，Android也不会答应的。
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // 4、设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
        options.inPurgeable = true;
        // 5、设置位图缩放比例
        // width，hight设为原来的四分一（该参数请使用2的整数倍）,这也减小了位图占用的内存大小；
        // 例如，一张//分辨率为2048*1536px的图像使用inSampleSize值为4的设置来解码，产生的Bitmap大小约为//512*384px。
        // 相较于完整图片占用12M的内存，这种方式只需0.75M内存(假设Bitmap配置为//ARGB_8888)。
        options.inSampleSize = 12   ;
        // 6、设置解码位图的尺寸信息
        options.inInputShareable = true;
        // 7、解码位图
        Bitmap newBitmap = BitmapFactory.decodeStream(inputStream, null, options);
        return newBitmap;
    }

    /**
     * 示例2：可以直接使用的工具类
     */
    public static Bitmap resizeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 示例3：可以直接使用的工具类，调用api封装好的转换
     * @param bitmap
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);

    }

    public static int calculateSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
