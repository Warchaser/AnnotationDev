package com.warchaser.annotations_ui.bindclick;

public interface ClickBinder<T> {
    void bindClick(T host, Object object);

    void unBindClick(T host);
}
