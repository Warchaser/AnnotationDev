package com.warchaser.annotations_ui;

public interface IViewBinder<T> {

    void bindView(T target, Object object, IFinder finder);

    void unbind(T target);
}
