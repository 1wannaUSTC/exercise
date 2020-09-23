package com.one.exercise.enums;

public enum ImageFormat {
    JPG("JPG"),JPEG("JPEG"),PNG("PNG");

    private String format;

    private ImageFormat(String format){
        this.format = format;
    }

    public static boolean isFormat(String fileName){
        try{
            ImageFormat[] values = ImageFormat.values();
            for (ImageFormat value : values) {
                if (value.getFormat().equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".")+1))){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public String getFormat() {
        return format;
    }
}
