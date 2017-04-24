package com.radicalpeas.radguidelines;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by huanx on 4/19/2017.
 */

public class Utilities extends Activity
{

    public static final int IMAGE_SIZE = 500;

    /**
     * Sets ImageView picture from file
     * @param mImageView: Layout ImageView that will display the picture
     * @param mCurrentPhotoPath: full file path of image to be displayed
     * @param size: scale down size of displayed picture (square)
     * @return boolean: false if no image is set
     */

    static public boolean setPic(ImageView mImageView, String mCurrentPhotoPath)
    {
        // if size not specified, use default value
        return setPic(mImageView, mCurrentPhotoPath, IMAGE_SIZE);
    }

    static public boolean setPic(ImageView mImageView, String mCurrentPhotoPath, int size)
    {
        if(mCurrentPhotoPath== null || mCurrentPhotoPath.isEmpty())
        {
            mImageView.setImageBitmap(null);
            return false;
        }

        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        if (targetW == 0 || targetH == 0)
        {
            targetW = targetH = size;
        }

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);

        return true;
    }

    /**
     * Sets ImageView picture from file
     * @param mImageView: Layout ImageView that will display the picture
     * @param mDrawableResource: @drawable resource ID of image
     * @param size: scale down size of displayed picture (square)
     * @return boolean: false if no image is set
     */

    static public boolean setPic(Context context, ImageView mImageView, int mDrawableResource)
    {
        // if size not specified, use default value
        return setPic(context, mImageView, mDrawableResource, IMAGE_SIZE);
    }

    static public boolean setPic(Context context, ImageView mImageView, int mDrawableResource, int size)
    {
        if(mDrawableResource == 0)
        {
            mImageView.setImageBitmap(null);
            return false;
        }

        // Get the dimensions of the View
        int targetW = mImageView.getWidth() * 2;
        int targetH = mImageView.getHeight() * 2;

        if (targetW == 0 || targetH == 0)
        {
            targetW = targetH = size;
        }

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), mDrawableResource, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), mDrawableResource, bmOptions);
        mImageView.setImageBitmap(bitmap);

        return true;
    }

}
