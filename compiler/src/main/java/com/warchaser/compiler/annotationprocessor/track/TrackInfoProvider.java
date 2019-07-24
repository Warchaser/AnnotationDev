package com.warchaser.compiler.annotationprocessor.track;

public interface TrackInfoProvider {

    String getTrackNameByClass(String className);

    String getAllPath();
}
