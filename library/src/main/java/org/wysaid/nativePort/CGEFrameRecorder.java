package org.wysaid.nativePort;

import java.nio.ByteBuffer;

/**
 * Created by wangyang on 15/7/29.
 */

//A wrapper for native class FrameRecorder
public class CGEFrameRecorder {

    static {
        NativeLibraryLoader.load();
    }

    private ByteBuffer mNativeAddress;

    public CGEFrameRecorder() {
        mNativeAddress = nativeCreate();
    }

    //srcWidth&srcheight stands for the external_texture's width&height
    //dstWidth&dstHeight stands for the recording resolution (default 640*480)
    //dstWidth/dstHeight should not be changed after "init()" is called.
    //srcWidth/srcHeight could be changed by calling "srcResize" function.
    public boolean init(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
        if(mNativeAddress != null)
            return nativeInit(mNativeAddress, srcWidth, srcHeight, dstWidth, dstHeight);
        return false;
    }

    //Will effect the framebuffer
    public void update(int externalTexture, float[] transformMatrix) {
        if(mNativeAddress != null)
            nativeUpdate(mNativeAddress, externalTexture, transformMatrix);
    }

    //Won't effect the framebuffer
    //the arguments means the viewport.
    public void render(int x, int y, int width, int height) {
        if(mNativeAddress != null)
            nativeRender(mNativeAddress, x, y, width, height);
    }

    public void drawCache() {
        if(mNativeAddress != null)
            nativeDrawCache(mNativeAddress);
    }

    //set the rotation of the camera texture
    public void setSrcRotation(float rad) {
        if(mNativeAddress != null)
            nativeSetSrcRotation(mNativeAddress, rad);
    }

    //set the flip/scaling for the camera texture
    public void setSrcFlipScale(float x, float y) {
        if(mNativeAddress != null)
            nativeSetSrcFlipScale(mNativeAddress, x, y);
    }

    //set the render result's rotation
    public void setRenderRotation(float rad) {
        if(mNativeAddress != null)
            nativeSetRenderRotation(mNativeAddress, rad);
    }

    //set the render result's flip/scaling
    public void setRenderFlipScale(float x, float y) {
        if(mNativeAddress != null)
            nativeSetRenderFlipScale(mNativeAddress, x, y);
    }

    //initialize the filters width config string
    public void setFilterWidthConfig(final String config) {
        if(mNativeAddress != null)
            nativeSetFilterWidthConfig(mNativeAddress, config);
    }

    //set the mask rotation (radian)
    public void setMaskRotation(float rot) {
        if(mNativeAddress != null)
            nativeSetMaskRotation(mNativeAddress, rot);
    }

    //set the mask flipscale
    public void setMaskFlipScale(float x, float y) {
        if(mNativeAddress != null)
            nativeSetMaskFlipScale(mNativeAddress, x, y);

    }


    //set the intensity of the filter
    public void setFilterIntensity(float value) {
        if(mNativeAddress != null)
            nativeSetFilterIntensity(mNativeAddress, value);
    }

    public void srcResize(int width, int height) {
        if(mNativeAddress != null)
            nativeSrcResize(mNativeAddress, width, height);
    }

    public  void release() {
        if(mNativeAddress != null) {
            nativeRelease(mNativeAddress);
            mNativeAddress = null;
        }
    }

    public void enableFaceDetectWithDefaultFilter(boolean shouldDetect) {
        if(shouldDetect) {
            nativeSetTrackedFilter(mNativeAddress, "@beautify bilateral 100 6.8 1 @adjust brightness 0.14 @adjust contrast 1.12 ");
        } else {
            nativeSetTrackedFilter(mNativeAddress, "");
        }
    }

    public void setTrackedFilterIntensity(float intensity) {
        nativeSetTrackedFilterIntensity(mNativeAddress, intensity);
    }

    public void setFaceArea(float x, float y, float gx, float gy) {
        nativeSetFaceArea(mNativeAddress, x, y, gx, gy);
    }

    /////////////////视频录制相关////////////////////
//
//    public boolean startRecording(int fps, String filename) {
//        return nativeStartRecording(mNativeAddress, fps, filename);
//    }
//
//    public boolean isRecordingStarted() {
//        return nativeIsRecordingStarted(mNativeAddress);
//    }
//
//    public boolean endRecording(boolean shouldSave) {
//        return nativeEndRecording(mNativeAddress, shouldSave);
//    }

//    public void pauseRecording() {
//        nativePauseRecording(mNativeAddress);
//    }
//
//    public boolean isRecordingPaused() {
//        return nativeIsRecordingPaused(mNativeAddress);
//    }
//
//    public boolean resumeRecording() {
//        return nativeResumeRecording(mNativeAddress);
//    }
//
//    public double getTimestamp() {
//        return nativeGetTimestamp(mNativeAddress);
//    }
//
//    public void setTempDir(String dir) {
//        nativeSetTempDir(mNativeAddress, dir);
//    }

    //需要置于GPU绘制线程
//    public void recordImageFrame() {
//        nativeRecordImageFrame(mNativeAddress);
//    }

    public void setMaskTexture(int texID, float aspectRatio) {
        if(mNativeAddress != null)
            nativeSetMaskTexture(mNativeAddress, texID, aspectRatio);
    }

    public void setMaskTextureRatio(float aspectRatio) {
        if(mNativeAddress != null)
            nativeSetMaskTextureRatio(mNativeAddress, aspectRatio);
    }

    public int queryBufferTexture() {
        if(mNativeAddress != null)
            return nativeQueryBufferTexture(mNativeAddress);
        return 0;
    }

    /////////////////      private         ///////////////////////

    private native ByteBuffer nativeCreate();
    private native boolean nativeInit(ByteBuffer holder, int srcWidth, int srcHeight, int dstWidth, int dstHeight);
    private native void nativeUpdate(ByteBuffer holder, int externalTexture, float[] transformMatrix);

    private native void nativeRender(ByteBuffer holder, int x, int y, int width, int height);
    private native void nativeDrawCache(ByteBuffer holder);

    private native void nativeSetSrcRotation(ByteBuffer holder, float rad);
    private native void nativeSetSrcFlipScale(ByteBuffer holder, float x, float y);
    private native void nativeSetRenderRotation(ByteBuffer holder, float rad);
    private native void nativeSetRenderFlipScale(ByteBuffer holder, float x, float y);
    private native void nativeSetFilterWidthConfig(ByteBuffer holder, String config);
    private native void nativeSetFilterIntensity(ByteBuffer holder, float value);
    private native void nativeSetMaskRotation(ByteBuffer holder, float value);
    private native void nativeSetMaskFlipScale(ByteBuffer holder, float x, float y);

    private native void nativeSrcResize(ByteBuffer holder, int width, int height);

    //人脸美化相关

    private native void nativeSetTrackedFilter(ByteBuffer holder, String config);
    private native void nativeSetTrackedFilterIntensity(ByteBuffer holder, float intensity);
    private native void nativeSetFaceArea(ByteBuffer holder, float x, float y, float gx, float gy);

    /////////////////视频录制相关////////////////////
//    private native boolean nativeStartRecording(ByteBuffer holder, int fps, String filename);
//    private native boolean nativeIsRecordingStarted(ByteBuffer holder);
//    private native boolean nativeEndRecording(ByteBuffer holder, boolean shouldSave);
//    private native void nativePauseRecording(ByteBuffer holder);
//    private native boolean nativeIsRecordingPaused(ByteBuffer holder);
//    private native boolean nativeResumeRecording(ByteBuffer holder);
//    private native double nativeGetTimestamp(ByteBuffer holder);

//    private native void nativeSetTempDir(ByteBuffer holder, String dir);

//    private native void nativeRecordImageFrame(ByteBuffer holder);

    private native void nativeSetMaskTexture(ByteBuffer holder, int texID, float aspectRatio);
    private native void nativeSetMaskTextureRatio(ByteBuffer holder, float aspectRatio);

    private native void nativeRelease(ByteBuffer holder);

    private native int nativeQueryBufferTexture(ByteBuffer holder);
}
