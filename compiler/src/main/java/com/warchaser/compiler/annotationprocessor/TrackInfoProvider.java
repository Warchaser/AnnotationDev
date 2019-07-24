package com.warchaser.compiler.annotationprocessor;

public interface TrackInfoProvider {

    String getTrackNameByClass(String className);

    String getAllPath();
}
